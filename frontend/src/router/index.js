import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/Home.vue'
import RegistrationPage from '../views/RegistrationPage.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/register',
    name: 'Registration',
    component: RegistrationPage
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
