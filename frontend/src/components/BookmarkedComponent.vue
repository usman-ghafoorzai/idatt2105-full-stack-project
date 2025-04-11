<script setup>
import { ref, onMounted } from 'vue';
import BaseItemListComponent from './BaseItemListComponent.vue';
import { getBookmarkedItems, deleteBookmark } from '../api/bookmarkAPI.js';
import { getFirstImage } from '../api/itemAPI.js';
import { getLoggedInUser } from '../api/userAPI.js';
import { useRouter } from 'vue-router';
import { useItemStore } from '../stores/ItemStore.js';
import { useSellerInformationStore } from '../stores/SellerInformationStore.js';


const router = useRouter();
const itemStore = useItemStore();
const sellerStore = useSellerInformationStore();
const bookmarkedItems = ref([]);
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


    const user = await getLoggedInUser();
    userId.value = user.id;

    const items = await getBookmarkedItems(userId.value);

    // get first image of each item
    for (const item of items) {
      try {
        item.imageUrl = await getFirstImage(item.id);
      } catch (imageErr) {
        item.imageUrl = 'https://via.placeholder.com/150x100';
      }
    }

    bookmarkedItems.value = items;
  } catch (fetchErr) {
    error.value = 'Failed to load bookmarked items';
    console.error(fetchErr);
  } finally {
    loading.value = false;
  }
});

// remove bookmark
const cancelBookmark = async (itemId) => {
  try {
    await deleteBookmark(userId.value, itemId);
    bookmarkedItems.value = bookmarkedItems.value.filter(item => item.id !== itemId);
  } catch (removeErr) {
    console.error("Failed to remove bookmark:", removeErr);
    alert("Failed to remove bookmark. Please try again.");
  }
};

</script>

<template>
  <BaseItemListComponent title="Bookmarked" bgColor="var(--color-light-bg)" class="elegant-card">
    <div v-if="loading" class="loading-message">Loading bookmarked items...</div>
    <div v-else-if="error" class="error-message">{{ error }}</div>
    <div v-else-if="bookmarkedItems.length === 0" class="empty-message">No bookmarked items</div>
    <div v-else class="items-container">
      <div v-for="item in bookmarkedItems" :key="item.id" class="item-card">
        <div class="image-container" @click="handleItemClick(item)">
          <img :src="item.imageUrl" alt="Bookmarked item">
          <button class="cancel-button" @click.stop="cancelBookmark(item.id)">Remove</button>
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
