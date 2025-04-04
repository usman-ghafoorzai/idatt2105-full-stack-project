import { describe, it, expect, vi, beforeEach } from 'vitest';
import { mount } from '@vue/test-utils';
import LogInComponent from '../components/LogInComponent.vue';

describe('LoginComponent', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(LogInComponent);
  });

  it('renders login header', () => {
    const header = wrapper.find('h2');
    expect(header.exists()).toBe(true);
    expect(header.text()).toContain('Login to have more access');
  });

  it('renders username and password inputs', () => {
    expect(wrapper.find('input#username').exists()).toBe(true);
    expect(wrapper.find('input#password').exists()).toBe(true);
  });

  it('renders login button', () => {
    const button = wrapper.find('button[type="submit"]');
    expect(button.exists()).toBe(true);
    expect(button.text()).toBe('Login!');
  });

  it('updates username and password when typing', async () => {
    const usernameInput = wrapper.find('input#username');
    const passwordInput = wrapper.find('input#password');

    await usernameInput.setValue('testuser');
    await passwordInput.setValue('mypassword');

    expect(usernameInput.element.value).toBe('testuser');
    expect(passwordInput.element.value).toBe('mypassword');
  });

  it('calls login and logs credentials on form submit', async () => {
    const consoleSpy = vi.spyOn(console, 'log');

    await wrapper.find('#username').setValue('usertest');
    await wrapper.find('#password').setValue('pass123');
    await wrapper.find('form').trigger('submit.prevent');

    expect(consoleSpy).toHaveBeenCalledWith({
      username: 'usertest',
      password: 'pass123'
    });

    consoleSpy.mockRestore();
  });
});
