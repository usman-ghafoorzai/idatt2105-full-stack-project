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
  <BaseItemListComponent title="Reserved" bgColor="var(--color-coral)" class="elegant-card">
    <div v-for="item in reservedItems" :key="item.id" class="reserved-item item-base">
      <div class="image-container">
        <img :src="item.imageUrl" alt="Reserved item">
        <div class="price-tag">{{ item.price }} kr</div>
        <button class="cancel-button" @click="cancelReservation(item.id)">Cancel</button>
        <div class="reserved-badge">RESERVED</div>
      </div>
    </div>
  </BaseItemListComponent>
</template>

<style scoped>
.cancel-button {
  position: absolute;
  top: 5px;
  right: 5px;
  padding: 3px 8px;
  font-size: 12px;
  background-color: rgba(255, 0, 0, 0.7);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  z-index: 10;
}

.cancel-button:hover {
  background-color: rgba(255, 0, 0, 0.9);
}

.reserved-badge {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 7px 29px;
  gap: 10px;
  position: absolute;
  width: 62px;
  height: 20px;
  bottom: -20px;
  left: calc(50% - 31px);
  background: #87CF2F;
  color: #FFFFFF;
  font-family: 'Inter', sans-serif;
  font-weight: 700;
  font-size: 13px;
  line-height: 16px;
  text-align: center;
}
</style>
