<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useScroll } from '@vueuse/core';

// Constants for scrollbar
const SCROLLBAR_HEIGHT = 170;
const SCROLLBAR_THUMB_OFFSET = 5;

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  bgColor: {
    type: String,
    default: '#E2E5F6'
  },
  containerHeight: {
    type: Number,
    default: 250
  },
  scrollbarColor: {
    type: String,
    default: '#000000'
  },
  scrollbarTrackColor: {
    type: String,
    default: '#D9D9D9'
  }
});

const emit = defineEmits(['scroll', 'scroll-end']);

const itemsContainer = ref(null);
const scrollbarThumb = ref(null);

// Computed styles
const containerStyle = computed(() => ({
  height: `${props.containerHeight}px`
}));

const scrollbarStyle = computed(() => ({
  background: props.scrollbarTrackColor
}));

const thumbStyle = computed(() => ({
  background: props.scrollbarColor
}));

// Use VueUse's useScroll
const { y, arrivedState } = useScroll(itemsContainer);

// Update scrollbar position based on y value
const updateScrollbarPosition = () => {
  if (!itemsContainer.value || !scrollbarThumb.value) return;

  const container = itemsContainer.value;
  const thumb = scrollbarThumb.value;

  // Hide scrollbar if not enough content to scroll
  if (container.scrollHeight <= container.clientHeight) {
    thumb.style.display = 'none';
    return;
  } else {
    thumb.style.display = 'block';
  }

  const maxScroll = container.scrollHeight - container.clientHeight;
  const scrollPercentage = maxScroll > 0 ? y.value / maxScroll : 0;
  const thumbPosition = scrollPercentage * (SCROLLBAR_HEIGHT - thumb.clientHeight);

  thumb.style.top = `${thumbPosition + SCROLLBAR_THUMB_OFFSET}px`;

  emit('scroll', { scrollPercentage });

  // Emit when scrolled to the end
  if (scrollPercentage > 0.95) {
    emit('scroll-end');
  }
};

// Watch for scroll changes
watch(y, updateScrollbarPosition);

// Watch for arrivedState.bottom
watch(() => arrivedState.bottom, (isAtBottom) => {
  if (isAtBottom) {
    emit('scroll-end');
  }
});

onMounted(() => {
  // Initial position
  updateScrollbarPosition();
});
</script>

<template>
  <div class="base-section" :style="containerStyle">
    <div class="base-header" :style="{ background: bgColor }">
      <h1 class="base-title">{{ title }}</h1>
    </div>

    <div class="items-container">
      <div class="items" ref="itemsContainer">
        <div class="items-row">
          <slot></slot>
        </div>
      </div>

      <!-- Custom scrollbar -->
      <div class="scrollbar" :style="scrollbarStyle">
        <div class="scrollbar-thumb" ref="scrollbarThumb" :style="thumbStyle"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.base-section {
  position: relative;
  width: 600px;
  background: #FFFFFF;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  overflow: hidden;
}

.base-header {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 5px 6px;
  gap: 10px;
  width: 280px;
  height: 45px;
  margin: 8px 0 0 28px;
  border-radius: 10px;
}

.base-title {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 700;
  font-size: 38px;
  line-height: 46px;
  color: #4D4D4D;
  margin: 0;
}

.items-container {
  position: relative;
  height: 180px;
  margin-top: 10px;
  overflow: hidden;
}

.items {
  height: 100%;
  overflow-y: auto;
  padding: 0 28px 0 28px;
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE and Edge */
}

.items::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}

.items-row {
  display: flex;
  flex-wrap: wrap;
  gap: 36px;
  padding-bottom: 10px;
}

/* Custom scrollbar styling */
.scrollbar {
  position: absolute;
  right: 10px;
  top: 5px;
  width: 8px;
  height: 170px;
  border-radius: 10px;
  opacity: 0.8;
}

.scrollbar-thumb {
  position: absolute;
  width: 8px;
  height: 40px; /* Slightly taller thumb */
  right: 0;
  top: 5px;
  border-radius: 10px;
  transition: width 0.2s ease;
}

.scrollbar-thumb:hover {
  width: 10px;
  cursor: pointer;
  opacity: 1;
}

/* Common item styling that can be used by child components */
:deep(.item-base) {
  flex: 0 0 auto;
}

:deep(.image-container) {
  position: relative;
  width: 150px;
  height: 100px;
  margin-bottom: 10px;
}

:deep(.price-tag) {
  position: absolute;
  z-index: 10;
  bottom: 0;
  width: fit-content;
  background-color: black;
  opacity: 60%;
  color: aliceblue;
  font-size: 18px;
}

@media (max-width: 768px) {
  .base-section {
    width: 90%;
    margin: 0 auto;
  }

  .base-header {
    width: 50%;
  }

  .base-title {
    font-size: 32px;
  }
}
</style>
