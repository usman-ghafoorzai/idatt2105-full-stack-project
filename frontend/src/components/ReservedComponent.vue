<script setup>
import { ref, onMounted } from 'vue';
import BaseItemListComponent from './BaseItemListComponent.vue';
import { getReservedItems, deleteReservation } from '../api/reservationAPI.js';
import { getFirstImage } from '../api/itemAPI.js';
import { getLoggedInUser } from '../api/userAPI.js';
import { useRouter } from 'vue-router';
import { useItemStore } from '../stores/ItemStore.js';
import { useSellerInformationStore } from '../stores/SellerInformationStore.js';


const router = useRouter();
const itemStore = useItemStore();
const sellerStore = useSellerInformationStore();
const reservedItems = ref([]);
const loading = ref(true);
const error = ref(null);
const userId = ref(null);

const handleItemClick = (item) => {
  itemStore.setSelectedItem(item);
  sellerStore.setSellerId(item.seller.id);
  sellerStore.getSellerInformation();
  router.push('/item');
};

onMounted(async () => {
  try {
    loading.value = true;

    // Get current user
    const user = await getLoggedInUser();
    userId.value = user.id;

    // Fetch reserved items
    const items = await getReservedItems(userId.value);

    // For each item, get the first image
    for (const item of items) {
      try {
        item.imageUrl = await getFirstImage(item.id);
      } catch (imageErr) {
        item.imageUrl = 'https://via.placeholder.com/150x100';
      }
    }

    reservedItems.value = items;
  } catch (fetchErr) {
    error.value = 'Failed to load reserved items';
    console.error(fetchErr);
  } finally {
    loading.value = false;
  }
});

// Function to cancel reservation
const cancelReservation = async (item) => {
  try {
    // Use the correct ID format
    const itemId = item.itemId || item.item?.id || item.id;

    await deleteReservation(userId.value, itemId);
    reservedItems.value = reservedItems.value.filter(i => i.id !== item.id);
  } catch (cancelErr) {
    console.error("Failed to cancel reservation:", cancelErr);
    alert("Failed to cancel reservation. Please try again.");
  }
};
</script>

<template>
<BaseItemListComponent title="Reserved" bgColor="var(--color-light-bg)" class="elegant-card">
<div v-if="loading" class="loading-message">Loading reserved items...</div>
<div v-else-if="error" class="error-message">{{ error }}</div>
<div v-else-if="reservedItems.length === 0" class="empty-message">No reserved items</div>
<div v-else class="items-container">
  <div v-for="item in reservedItems" :key="item.id" class="item-card">
    <div class="image-container" @click="handleItemClick(item)">
      <img :src="item.imageUrl" alt="Reserved item">
      <button class="cancel-button" @click.stop="cancelReservation(item)">Cancel</button>
    </div>
  </div>
</div>
</BaseItemListComponent>
</template>

<style scoped>
.items-container {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  padding: 12px;
}

.item-card {
  width: 120px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.image-container {
  position: relative;
  width: 120px;
  height: 120px;
  cursor: pointer;
}

.image-container img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cancel-button {
  position: absolute;
  top: 5px;
  right: 5px;
  padding: 2px 6px !important;
  font-size: 11px !important;
  background-color: var(--color-red, #ff4757);
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  z-index: 10;
}

.cancel-button:hover {
  background-color: var(--color-gray-darker, #d32f2f);
}

.loading-message, .error-message, .empty-message {
  padding: 20px;
  text-align: center;
}

.error-message {
  color: var(--color-red, #ff4757);
}
</style>
