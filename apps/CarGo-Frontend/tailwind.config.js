const { createGlobPatternsForDependencies } = require('@nx/angular/tailwind');
const { join } = require('path');

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    join(__dirname, 'src/**/!(*.stories|*.spec).{ts,html}'),
    ...createGlobPatternsForDependencies(__dirname),
  ],
  theme: {
    fontFamily: {
      sans: 'Inter var, ui-sans-serif, system-ui',
      serif: 'Inter var, ui-sans-serif, system-ui',
    },
    fontSize: {
      sm: '0.875rem',
      base: '1.3rem',
      xl: '1rem',
      '2xl': '1.363rem',
      '3xl': '1.753rem',
      '4xl': '2.441rem',
      '5xl': '3.52rem',
    },
    extend: {},
  },
  daisyui: {
    themes: [
      {
        fantasy: {
          primary: '#0000ff',
          'primary-content': '#ffffff',
          secondary: '#f6f6f6',
          neutral: '#e8e8e8',
        },
      },
    ],
    base: true,
    styled: true,
    utils: true,
    prefix: '',
    logs: true,
    themeRoot: ':root'
  },
  plugins: [
    require('@tailwindcss/typography'),
    require('daisyui'),
  ],
};
