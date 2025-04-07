<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { FontAwesomeIcon as fa } from '@fortawesome/vue-fontawesome';

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

const itemsContainer = ref(null);
const scrollbarThumb = ref(null);

// Function to remove bookmark
const removeBookmark = (itemId) => {
  bookmarkedItems.value = bookmarkedItems.value.filter(item => item.id !== itemId);
  console.log("Removed from bookmarks");
  // However, In real implementation a call will be made to  API to remove bookmar??
};

// Update scrollbar position based on container scroll
const updateScrollbarPosition = () => {
  if (!itemsContainer.value || !scrollbarThumb.value) return;

  const container = itemsContainer.value;
  const thumb = scrollbarThumb.value;

  const scrollPercentage = container.scrollTop / (container.scrollHeight - container.clientHeight);
  const scrollbarHeight = 170;
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
  <div class="bookmark-section">
    <div class="bookmark-header">
      <h1 class="bookmark-title">Bookmarked</h1>
    </div>

    <div class="bookmarked-items-container">
      <div class="bookmarked-items" ref="itemsContainer">
        <div class="bookmarked-row">
          <div v-for="item in bookmarkedItems" :key="item.id" class="bookmarked-item">
            <div class="image-container">
              <img :src="item.imageUrl" alt="Bookmarked item">
              <div class="price-tag">{{ item.price }} kr</div>
              <fa :icon="['fas','bookmark']" class="bookmark-icon"
                  @click="removeBookmark(item.id)" style="color: yellow;"/>
            </div>
          </div>
        </div>
      </div>

      <!-- Custom scrollbar -->
      <div class="scrollbar">
        <div class="scrollbar-thumb" ref="scrollbarThumb"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bookmark-section {
  position: relative;
  width: 600px;
  height: 250px;
  background: #FFFFFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  overflow: hidden;
}

.bookmark-header {
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

.bookmark-title {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 700;
  font-size: 38px;
  line-height: 46px;
  color: #4D4D4D;
  margin: 0;
}

.bookmarked-items-container {
  position: relative;
  height: 180px;
  margin-top: 10px;
  overflow: hidden;
}

.bookmarked-items {
  height: 100%;
  overflow-y: auto;
  padding: 0 28px 0 28px;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE and Edge */
}

.bookmarked-items::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}

.bookmarked-row {
  display: flex;
  flex-wrap: wrap;
  gap: 36px;
  padding-bottom: 10px;
}

.bookmarked-item {
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

.bookmark-icon {
  font-size: 20px;
  position: absolute;
  z-index: 10;
  top: 5px;
  right: 10px;
}

.bookmark-icon:hover {
  cursor: pointer;
}

/* Custom scrollbar styling */
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
  .bookmark-section {
    width: 90%;
    margin: 0 auto;
  }

  .bookmark-header {
    width: 50%;
  }

  .bookmark-title {
    font-size: 32px;
  }
}
</style>
