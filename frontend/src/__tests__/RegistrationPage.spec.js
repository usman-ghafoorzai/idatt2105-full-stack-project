import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import RegistrationPage from '../views/RegistrationPage.vue'
import Header from '../components/Header.vue'
import RegisterUser from '../components/RegisterUser.vue'
import UploadPicture from '../components/UploadPicture.vue'
import LogInComponent from '../components/LogInComponent.vue'

describe('RegistrationPage.vue', () => {
  let wrapper

  beforeEach(() => {
    wrapper = mount(RegistrationPage, {
      global: {
        stubs: {
          Header,
          RegisterUser,
          UploadPicture,
          LogInComponent,
        }
      }
    })
  })
});
