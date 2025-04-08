import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '../views/HomeView.vue'
import RegistrationPage from '../views/RegistrationPage.vue'
import ItemListingView from '../views/ItemListingView.vue';
import ItemView from '../views/ItemView.vue';
import SellerView from '../views/SellerView.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView
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
  },
  {
    path: '/sell',
    name: 'Sell',
    component: SellerView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
