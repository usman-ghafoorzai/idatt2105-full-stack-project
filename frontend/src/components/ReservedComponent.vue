<script setup>
import { ref } from 'vue';
import BaseItemListComponent from './BaseItemListComponent.vue';

// Mock data - will be replaced with API call
const reservedItems = ref([
  { id: 1, title: 'Item 1', imageUrl: 'https://via.placeholder.com/150x100', price: '200', details: 'Item details here' },
  { id: 2, title: 'Item 2', imageUrl: 'https://via.placeholder.com/150x100', price: '350', details: 'Item details here' },
  { id: 3, title: 'Item 3', imageUrl: 'https://via.placeholder.com/150x100', price: '175', details: 'Item details here' },
  { id: 4, title: 'Item 4', imageUrl: 'https://via.placeholder.com/150x100', price: '420', details: 'Item details here' },
  { id: 5, title: 'Item 5', imageUrl: 'https://via.placeholder.com/150x100', price: '280', details: 'Item details here' },
  { id: 6, title: 'Item 6', imageUrl: 'https://via.placeholder.com/150x100', price: '310', details: 'Item details here' },
  { id: 7, title: 'Item 7', imageUrl: 'https://via.placeholder.com/150x100', price: '195', details: 'Item details here' },
]);

// Function to cancel reservation
const cancelReservation = (itemId) => {
  reservedItems.value = reservedItems.value.filter(item => item.id !== itemId);
  console.log("Reservation canceled for item", itemId);
  // In real implementation this would call an API
};
</script>

<template>
  <BaseItemListComponent title="Reserved" bgColor="var(--color-light-bg)" class="elegant-card">
    <div v-for="item in reservedItems" :key="item.id" class="reserved-item item-base">
      <div class="image-container">
        <img :src="item.imageUrl" alt="Reserved item">
        <button class="cancel-button" @click="cancelReservation(item.id)">Cancel</button>
      </div>
    </div>
  </BaseItemListComponent>
</template>

<style scoped>
.cancel-button {
  position: absolute;
  top: 5px;
  right: 5px;
  padding: 3px 8px !important; /* Override main.css padding */
  font-size: 12px !important; /* Override main.css font-size */
  background-color: var(--color-red); /* Use the variable from main.css */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  z-index: 10;
  height: auto !important; /* Override any height from main.css */
  width: auto !important; /* Override any width from main.css */
  line-height: normal !important; /* Override any line-height */
}

.cancel-button:hover {
  background-color: var(--color-gray-darker); /* Use the hover color from main.css */
}
</style>
