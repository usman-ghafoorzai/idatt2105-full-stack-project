import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import RegistrationPage from '../views/RegistrationPage.vue'
import Header from '../components/Header.vue'
import RegisterUser from '../components/RegisterUser.vue'
import UploadPicture from '../components/UploadPicture.vue'
import LogInComponent from '../components/LogInComponent.vue'
import {createRouter, createWebHistory} from "vue-router";

describe('RegistrationPage.vue', () => {
  let wrapper
  let router

  beforeEach(() => {
    router = createRouter({
      history: createWebHistory(),
      routes: []
    })
    wrapper = mount(RegistrationPage, {
      global: {
        plugins: [router],
        stubs: {
          Header,
          RegisterUser,
          UploadPicture,
          LogInComponent,
          fa: true
        }
      }
    })
  })

  it('mounts the view without crashing', () => {
    expect(wrapper.exists()).toBe(true)
  })

  it('renders all layout sections', () => {
    expect(wrapper.find('.login-section').exists()).toBe(true)
    expect(wrapper.find('.registration-section').exists()).toBe(true)
    expect(wrapper.find('.upload-section').exists()).toBe(true)
    expect(wrapper.find('.register-section').exists()).toBe(true)
  })

  it('renders child components', () => {
    expect(wrapper.findComponent(Header).exists()).toBe(true)
    expect(wrapper.findComponent(LogInComponent).exists()).toBe(true)
    expect(wrapper.findComponent(UploadPicture).exists()).toBe(true)
    expect(wrapper.findComponent(RegisterUser).exists()).toBe(true)
  })
})

