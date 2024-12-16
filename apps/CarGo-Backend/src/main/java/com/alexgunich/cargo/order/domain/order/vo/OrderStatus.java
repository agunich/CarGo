package com.alexgunich.cargo.order.domain.order.vo;

/**
 * Enum representing the possible statuses of an order.
 *
 * <p>The {@link OrderStatus} enum defines the lifecycle states of an order within the system:
 * - {@code PENDING}: The order has been created but not yet paid.
 * - {@code PAID}: The order has been successfully paid for.</p>
 */
public enum OrderStatus {

  PENDING,
  PAID;

}
