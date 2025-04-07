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
  const scrollbarHeight = 100;
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
  <div class="reserved-section">
    <div class="reserved-header">
      <h1 class="reserved-title">Reserved</h1>
    </div>

    <div class="reserved-items-container">
      <div class="reserved-items" ref="itemsContainer">
        <div class="reserved-row">
          <div v-for="item in reservedItems" :key="item.id" class="reserved-item">
            <div class="image-container">
              <img :src="item.imageUrl" alt="Reserved item">
              <div class="price-tag">{{ item.price }} kr</div>
              <div class="reserved-badge">Reserved</div>
              <button class="cancel-button" @click="cancelReservation(item.id)">Cancel</button>
            </div>
          </div>
        </div>
      </div>

      <div class="scrollbar">
        <div class="scrollbar-thumb" ref="scrollbarThumb"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.reserved-section {
  position: relative;
  width: 600px;
  height: 250px;
  background: #FFFFFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  overflow: hidden;
}

.reserved-header {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 5px 6px;
  gap: 10px;
  width: 280px;
  height: 45px;
  margin: 8px 0 0 28px;
  background: #E2E5F6;
  border-radius: 10px;
}

.reserved-title {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 700;
  font-size: 38px;
  line-height: 46px;
  color: #4D4D4D;
  margin: 0;
}

.reserved-items-container {
  position: relative;
  height: 180px;
  margin-top: 10px;
  overflow: hidden;
}

.reserved-items {
  height: 100%;
  overflow-y: auto;
  padding: 0 28px 0 28px;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE and Edge */
}

.reserved-items::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}

.reserved-row {
  display: flex;
  flex-wrap: wrap;
  gap: 36px;
  padding-bottom: 10px;
}

.reserved-item {
  flex: 0 0 auto;
}

.image-container {
  position: relative;
  width: 150px;
  height: 100px;
  margin-bottom: 10px;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border: 1px solid #000000;
}

.price-tag {
  position: absolute;
  z-index: 10;
  bottom: 0;
  width: fit-content;
  background-color: black;
  opacity: 60%;
  color: aliceblue;
  font-size: 18px;
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

.scrollbar {
  position: absolute;
  width: 14px;
  height: 170px;
  right: 10px;
  top: 5px;
  background: #D9D9D9;
  border-radius: 15px;
}

.scrollbar-thumb {
  position: absolute;
  width: 14px;
  height: 30px;
  right: 0;
  top: 5px;
  background: #000000;
  border-radius: 15px;
}

@media (max-width: 768px) {
  .reserved-section {
    width: 90%;
    margin: 0 auto;
  }

  .reserved-header {
    width: 50%;
  }

  .reserved-title {
    font-size: 42px;
  }
}
</style>
