<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

// Replace with API calls later
const reservedItems = ref([
  { id: 1, title: 'Item 1', imageUrl: 'https://via.placeholder.com/150x100', price: '200', details: 'Item details here' },
  { id: 2, title: 'Item 2', imageUrl: 'https://via.placeholder.com/150x100', price: '350', details: 'Item details here' },
  { id: 3, title: 'Item 3', imageUrl: 'https://via.placeholder.com/150x100', price: '175', details: 'Item details here' },
  { id: 4, title: 'Item 4', imageUrl: 'https://via.placeholder.com/150x100', price: '420', details: 'Item details here' },
  { id: 5, title: 'Item 5', imageUrl: 'https://via.placeholder.com/150x100', price: '300', details: 'Item details here' },
]);

const itemsContainer = ref(null);
const scrollbarThumb = ref(null);

// Function to cancel reservation
const cancelReservation = (itemId) => {
  reservedItems.value = reservedItems.value.filter(item => item.id !== itemId);
  console.log("Reservation canceled");
  // In real implementation: call API to cancel reservation
};

// Update scrollbar position based on container scroll
const updateScrollbarPosition = () => {
  if (!itemsContainer.value || !scrollbarThumb.value) return;

  const container = itemsContainer.value;
  const thumb = scrollbarThumb.value;

  const scrollPercentage = container.scrollTop / (container.scrollHeight - container.clientHeight);
  const scrollbarHeight = 200;
  const thumbPosition = scrollPercentage * (scrollbarHeight - thumb.clientHeight);

  thumb.style.top = `${thumbPosition + 5}px`;
};

onMounted(() => {
  if (itemsContainer.value) {
    itemsContainer.value.addEventListener('scroll', updateScrollbarPosition);
    // Initial position
    updateScrollbarPosition();
  }
});

onUnmounted(() => {
  if (itemsContainer.value) {
    itemsContainer.value.removeEventListener('scroll', updateScrollbarPosition);
  }
});
</script>

<template>

</template>

<style scoped>

</style>
