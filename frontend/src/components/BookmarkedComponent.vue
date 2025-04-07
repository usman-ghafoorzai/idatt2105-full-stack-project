<script setup>
import { ref } from 'vue';
import { FontAwesomeIcon as fa } from '@fortawesome/vue-fontawesome';
import BaseItemListComponent from './BaseItemListComponent.vue';

// This will be replaced with actual API call later
const bookmarkedItems = ref([
  { id: 1, title: 'Item 1', imageUrl: 'https://via.placeholder.com/150x100', price: '200', details: 'Item details here' },
  { id: 2, title: 'Item 2', imageUrl: 'https://via.placeholder.com/150x100', price: '350', details: 'Item details here' },
  { id: 3, title: 'Item 3', imageUrl: 'https://via.placeholder.com/150x100', price: '175', details: 'Item details here' },
  { id: 4, title: 'Item 4', imageUrl: 'https://via.placeholder.com/150x100', price: '420', details: 'Item details here' },
  { id: 5, title: 'Item 5', imageUrl: 'https://via.placeholder.com/150x100', price: '300', details: 'Item details here' },
  { id: 6, title: 'Item 6', imageUrl: 'https://via.placeholder.com/150x100', price: '250', details: 'Item details here' },
  { id: 7, title: 'Item 7', imageUrl: 'https://via.placeholder.com/150x100', price: '190', details: 'Item details here' },
]);

// Function to remove bookmark
const removeBookmark = (itemId) => {
  bookmarkedItems.value = bookmarkedItems.value.filter(item => item.id !== itemId);
  console.log("Removed from bookmarks");
  // However, In real implementation a call will be made to API to remove bookmark
};
</script>

<template>
  <BaseItemListComponent title="Bookmarked" bgColor="var(--color-coral)" class="elegant-card">
    <div v-for="item in bookmarkedItems" :key="item.id" class="bookmarked-item item-base">
      <div class="image-container">
        <img :src="item.imageUrl" alt="Bookmarked item">
        <div class="price-tag">{{ item.price }} kr</div>
        <fa :icon="['fas','bookmark']" class="bookmark-icon" @click="removeBookmark(item.id)"/>
      </div>
    </div>
  </BaseItemListComponent>
</template>

<style scoped>
.bookmark-icon {
  color: yellow;
  font-size: 20px;
  position: absolute;
  z-index: 10;
  top: 5px;
  right: 10px;
}

.bookmark-icon:hover {
  cursor: pointer;
}
</style>
