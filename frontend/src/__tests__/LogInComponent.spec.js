import { describe, it, expect, vi, beforeEach } from 'vitest';
import { mount } from '@vue/test-utils';
import LogInComponent from '../components/LogInComponent.vue';

describe('LoginComponent', () => {
  let wrapper;

  // Before each test case, mount a fresh instance of the component
  // This ensures each test starts with a clean state
  beforeEach(() => {
    wrapper = mount(LogInComponent);
  });

  // Test 1: Verifies that the login header is rendered correctly
  // Checks that the h2 element exists and contains expected text
  it('renders login header', () => {
    const header = wrapper.find('h2');
    expect(header.exists()).toBe(true);
    expect(header.text()).toContain('Login to have more access');
  });

  // Test 2: Verifies that both the username and password input fields
  // are present in the component
  it('renders username and password inputs', () => {
    expect(wrapper.find('input#login-username').exists()).toBe(true);
    expect(wrapper.find('input#login-password').exists()).toBe(true);
  });

  // Test 3: Checks that the submit button exists and has the correct text
  it('renders login button', () => {
    const button = wrapper.find('button[type="submit"]');
    expect(button.exists()).toBe(true);
    expect(button.text()).toBe('Login!');
  });

  // Test 4: Verifies that the component correctly binds input values
  // to the component's data (using v-model)
  it('updates username and password when typing', async () => {
    const usernameInput = wrapper.find('input#login-username');
    const passwordInput = wrapper.find('input#login-password');

    // Simulate user typing into inputs
    await usernameInput.setValue('testuser');
    await passwordInput.setValue('mypassword');

    // Verify input values are updated
    expect(usernameInput.element.value).toBe('testuser');
    expect(passwordInput.element.value).toBe('mypassword');
  });

  // Test 5: Tests the form submission functionality
  // Verifies that the login method logs credentials when form is submitted
  it('calls login and logs credentials on form submit', async () => {
    // Create a spy to monitor console.log calls
    const consoleSpy = vi.spyOn(console, 'log');

    // Set values for username and password fields
    await wrapper.find('#login-username').setValue('usertest');
    await wrapper.find('#login-password').setValue('pass123');

    // Trigger the form submission
    await wrapper.find('form').trigger('submit.prevent');

    // Verify console.log was called with the expected credentials
    expect(consoleSpy).toHaveBeenCalledWith({
      username: 'usertest',
      password: 'pass123'
    });

    // Clean up the spy after test
    consoleSpy.mockRestore();
  });
});
