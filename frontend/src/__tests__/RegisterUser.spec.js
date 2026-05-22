import { describe, it, expect, vi, beforeEach } from 'vitest';
import { mount } from '@vue/test-utils';
import RegisterUser from '../components/RegisterUser.vue';

describe('RegisterUser.vue', () => {
  let wrapper;

  // Set up a fresh component instance before each test
  beforeEach(() => {
    wrapper = mount(RegisterUser);
  });

  // Test that the component renders with the correct header text
  it('renders the registration header', () => {
    const header = wrapper.find('h1');
    expect(header.exists()).toBe(true);
    expect(header.text()).toBe('Create account');
  });

  // Test that all required form input fields are present in the DOM
  it('renders all input fields', () => {
    const fields = ['firstName', 'lastName', 'email', 'username', 'password'];
    fields.forEach(field => {
      const input = wrapper.find(`input#${field}`);
      expect(input.exists()).toBe(true);
    });
  });

  // Test that the two-way data binding works correctly for all input fields
  it('binds input values correctly', async () => {
    await wrapper.find('#firstName').setValue('Ada');
    await wrapper.find('#lastName').setValue('Lovelace');
    await wrapper.find('#email').setValue('ada@example.com');
    await wrapper.find('#username').setValue('London');
    await wrapper.find('#password').setValue('securepass123');

    expect(wrapper.find('#firstName').element.value).toBe('Ada');
    expect(wrapper.find('#lastName').element.value).toBe('Lovelace');
    expect(wrapper.find('#email').element.value).toBe('ada@example.com');
    expect(wrapper.find('#username').element.value).toBe('London');
    expect(wrapper.find('#password').element.value).toBe('securepass123');
  });

  // Test that clicking the register button properly sends user data to the register function
  it('logs the correct values on register button click', async () => {
    const consoleSpy = vi.spyOn(console, 'log');

    await wrapper.find('#firstName').setValue('Grace');
    await wrapper.find('#lastName').setValue('Hopper');
    await wrapper.find('#email').setValue('grace@navy.mil');
    await wrapper.find('#username').setValue('New York');
    await wrapper.find('#password').setValue('COBOL4life');

    await wrapper.find('.register-button').trigger('click');

    // Verify the register function was called with the correct user data
    expect(consoleSpy).toHaveBeenCalledWith({
      password: 'COBOL4life',
      email: 'grace@navy.mil',
      firstName: 'Grace',
      lastName: 'Hopper',
      username: 'New York'
    });

    consoleSpy.mockRestore();
  });
});
