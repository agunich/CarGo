package com.alexgunich.cargo.order.application;

import com.alexgunich.cargo.order.domain.order.aggregate.*;
import com.alexgunich.cargo.order.domain.order.repository.OrderRepository;
import com.alexgunich.cargo.order.domain.order.service.CartReader;
import com.alexgunich.cargo.order.domain.order.service.OrderCreator;
import com.alexgunich.cargo.order.domain.order.service.OrderReader;
import com.alexgunich.cargo.order.domain.order.service.OrderUpdater;
import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.infrastructure.secondary.service.stripe.StripeService;
import com.alexgunich.cargo.product.application.ProductsApplicationService;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing order applications, including creating and updating orders,
 * retrieving cart details, and fetching orders for users and admins.
 */
@Service
public class OrderApplicationService {

  private final ProductsApplicationService productsApplicationService;
  private final CartReader cartReader;
  private final UsersApplicationService usersApplicationService;
  private final OrderCreator orderCreator;
  private final OrderUpdater orderUpdater;
  private final OrderReader orderReader;

  /**
   * Constructs an instance of OrderApplicationService.
   *
   * @param productsApplicationService the service for managing products
   * @param usersApplicationService the service for managing users
   * @param orderRepository the repository for accessing order data
   * @param stripeService the service for handling Stripe payments
   */
  public OrderApplicationService(ProductsApplicationService productsApplicationService,
                                 UsersApplicationService usersApplicationService,
                                 OrderRepository orderRepository,
                                 StripeService stripeService) {
    this.productsApplicationService = productsApplicationService;
    this.usersApplicationService = usersApplicationService;
    this.cartReader = new CartReader();
    this.orderCreator = new OrderCreator(orderRepository, stripeService);
    this.orderUpdater = new OrderUpdater(orderRepository);
    this.orderReader = new OrderReader(orderRepository);
  }

  /**
   * Retrieves the details of the cart based on the provided request.
   *
   * @param detailCartRequest the request containing the cart details
   * @return a response containing the detailed information of the cart
   */
  @Transactional(readOnly = true)
  public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
    List<PublicId> publicIds = detailCartRequest.items().stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return cartReader.getDetails(productsInformation);
  }

  /**
   * Creates a new order based on the items provided in the request.
   *
   * @param items the list of items to be included in the order
   * @return a Stripe session ID for processing the payment
   */
  @Transactional
  public StripeSessionId createOrder(List<DetailCartItemRequest> items) {
    User authenticatedUser = usersApplicationService.getAuthenticatedUser();
    List<PublicId> publicIds = items.stream().map(DetailCartItemRequest::productId).toList();
    List<Product> productsInformation = productsApplicationService.getProductsByPublicIdsIn(publicIds);
    return orderCreator.create(productsInformation, items, authenticatedUser);
  }

  /**
   * Updates an existing order based on the information from a Stripe session.
   *
   * @param stripeSessionInformation the Stripe session information used for updating the order
   */
  @Transactional
  public void updateOrder(StripeSessionInformation stripeSessionInformation) {
    List<OrderedProduct> orderedProducts = this.orderUpdater.updateOrderFromStripe(stripeSessionInformation);
    List<OrderProductQuantity> orderProductQuantities = this.orderUpdater.computeQuantity(orderedProducts);
    this.productsApplicationService.updateProductQuantity(orderProductQuantities);
    this.usersApplicationService.updateAddress(stripeSessionInformation.userAddress());
  }

  /**
   * Finds all orders for the currently connected user with pagination.
   *
   * @param pageable the pagination information
   * @return a page of orders for the authenticated user
   */
  @Transactional(readOnly = true)
  public Page<Order> findOrdersForConnectedUser(Pageable pageable) {
    User authenticatedUser = usersApplicationService.getAuthenticatedUser();
    return orderReader.findAllByUserPublicId(authenticatedUser.getUserPublicId(), pageable);
  }

  /**
   * Finds all orders for admin users with pagination.
   *
   * @param pageable the pagination information
   * @return a page of all orders
   */
  @Transactional(readOnly = true)
  public Page<Order> findOrdersForAdmin(Pageable pageable) {
    return orderReader.findAll(pageable);
  }
}
