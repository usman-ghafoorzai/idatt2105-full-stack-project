import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import Header from '../components/Header.vue';

describe('Header', () => {
  it('renders properly', () => {
    const wrapper = mount(Header);

    // Check if the logo is rendered
    expect(wrapper.find('#logo').text()).toBe('LOGO');

    // Check if navigation items are rendered
    expect(wrapper.find('#home').text()).toBe('HOME');
    expect(wrapper.find('#categories').text()).toBe('CATEGORIES');
    expect(wrapper.find('#sell').text()).toBe('SELL');

    // Check if the inbox and username are rendered
    expect(wrapper.find('#inbox').text()).toBe('Inbox');
    expect(wrapper.find('#username').text()).toBe('Log in/Sign up');

    // Check if the search bar is rendered
    expect(wrapper.find('#searchbar').exists()).toBe(true);
  });

  it('hides notification dot when mail = 0', () => {
    const wrapper = mount(Header, {
      props: {
        mail: 0,
      },
    });

    // Check if the notification dot is not rendered
    expect(wrapper.find('#notification-dot').exists()).toBe(false);
  });

  it('shows notification dot when mail > 0', () => {
    const wrapper = mount(Header, {
      props: {
        mail: 5,
      },
    });

    // Check if the notification dot is rendered
    expect(wrapper.find('#notification-dot').exists()).toBe(true);
  });
});