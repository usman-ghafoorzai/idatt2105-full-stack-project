<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useScroll } from '@vueuse/core';

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
    default: 290
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
  height: `${props.containerHeight}px`,
  background: props.bgColor
}));

const scrollbarStyle = computed(() => ({
  background: props.scrollbarTrackColor
}));

const thumbStyle = computed(() => ({
  background: props.scrollbarColor
}));

// Calculate scrollbar height based on container height
const scrollbarHeight = computed(() => {
  return props.containerHeight - 80;
});

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
  const thumbPosition = scrollPercentage * (scrollbarHeight.value - thumb.clientHeight);

  thumb.style.top = `${thumbPosition + SCROLLBAR_THUMB_OFFSET}px`;

  emit('scroll', { scrollPercentage });

  if (scrollPercentage > 0.95) {
    emit('scroll-end');
  }
};

// Watch for scroll changes
watch(y, updateScrollbarPosition);

watch(() => arrivedState.bottom, (isAtBottom) => {
  if (isAtBottom) {
    emit('scroll-end');
  }
});

onMounted(updateScrollbarPosition);
</script>

<template>
  <div class="base-section" :style="containerStyle">
    <h1 class="base-title">{{ title }}</h1>
    <div class="items-container">
      <div class="items" ref="itemsContainer">
        <div class="items-row">
          <slot></slot>
        </div>
      </div>
      <div class="scrollbar" :style="[scrollbarStyle, { height: scrollbarHeight + 'px' }]">
        <div class="scrollbar-thumb" ref="scrollbarThumb" :style="thumbStyle"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.base-section {
  position: relative;
  border-radius: 10px;
  overflow: hidden;
}

.base-title {
  font-family: 'Inter', sans-serif;
  font-weight: 600;
  font-size: 20px;
  color: #4D4D4D;
  margin: 10px 0 0 20px;
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
  padding: 0 28px;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.items::-webkit-scrollbar {
  display: none;
}

.items-row {
  display: flex;
  flex-wrap: wrap;
  gap: 40px;
  padding-bottom: 10px;
}

.scrollbar {
  position: absolute;
  right: 10px;
  top: 5px;
  width: 8px;
  border-radius: 10px;
  opacity: 0.8;
}

.scrollbar-thumb {
  position: absolute;
  width: 8px;
  height: 40px;
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

  .base-title {
    font-size: 32px;
  }
}
</style>
