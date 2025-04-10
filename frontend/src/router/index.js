import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '../views/HomeView.vue'
import RegistrationView from '../views/RegistrationView.vue'
import ItemListingView from '../views/ItemListingView.vue';
import ItemView from '../views/ItemView.vue';
import SellerView from '../views/SellerView.vue';
import UserProfileView from "@/views/UserProfileView.vue";
import { useSellerInformationStore } from '@/stores/SellerInformationStore';
import { useCategoryStore } from '@/stores/CategoryStore';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/register',
    name: 'Registration',
    component: RegistrationView
  },
  {
    path: '/search',
    name: 'Search',
    component: ItemListingView,
    beforeEnter: async (to, from, next) => {
      const categoryStore = useCategoryStore();
      try {
        if (categoryStore.categories.length === 0) {
          await categoryStore.fetchCategories();
        }
        next();
      } catch (error) {
        console.error('Error fetching categories:', error);
        next(false);
      }
    }
  },
  {
    path: '/item',
    name: 'Item',
    component: ItemView,
    beforeEnter: async (to, from, next) => {
      const sellerStore = useSellerInformationStore();
      try {
        await sellerStore.getSellerInformation();
        next();
      } catch (error) {
        console.error('Error fetching seller information:', error);
        next(false);
      }
    },
  },
  {
    path: '/sell',
    name: 'Sell',
    component: SellerView
  },
  {
    path: '/user-profile',
    name: 'UserProfile',
    component: UserProfileView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
