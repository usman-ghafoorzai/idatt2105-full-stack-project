import { createRouter, createWebHistory } from 'vue-router';

import Home from '../views/Home.vue'
import RegistrationPage from '../views/RegistrationPage.vue'
import ItemListingView from '../views/ItemListingView.vue';
import ItemView from '../views/ItemView.vue';

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
  },
  {
    path: '/search',
    name: 'Search',
    component: ItemListingView
  },
  {
    path: '/item',
    name: 'Item',
    component: ItemView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
