import { describe, it, expect, vi } from 'vitest';
import { mount } from '@vue/test-utils';
import { createTestingPinia } from '@pinia/testing';
import Header from '../components/Header.vue';

describe('Header', () => {
  it('renders properly', () => {
    const wrapper = mount(Header, {
      global: {
        plugins: [createTestingPinia({
          createSpy: vi.fn,
        })],
      },
    });


    expect(wrapper.find('#logo').text()).toBe('LOGO');
    expect(wrapper.find('#home').text()).toBe('HOME');
    expect(wrapper.find('#categories').text()).toBe('CATEGORIES');
    expect(wrapper.find('#sell').text()).toBe('SELL');
    expect(wrapper.find('#inbox').text()).toBe('Inbox');
    expect(wrapper.find('#username').text()).toBe('Log in/Sign up');
    expect(wrapper.find('#searchbar').exists()).toBe(true);
  });

  it('hides notification dot when mail = 0', () => {
    const wrapper = mount(Header, {
      props: {
        mail: 0,
      },
      global: {
        plugins: [createTestingPinia({
          createSpy: vi.fn,
        })],
      },
    });


    expect(wrapper.find('#notification-dot').exists()).toBe(false);
  });

  it('shows notification dot when mail > 0', () => {
    const wrapper = mount(Header, {
      props: {
        mail: 5,
      },
      global: {
        plugins: [createTestingPinia({
          createSpy: vi.fn,
        })],
      },
    });


    expect(wrapper.find('#notification-dot').exists()).toBe(true);
  });
});