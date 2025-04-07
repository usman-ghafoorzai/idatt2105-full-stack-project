  <script setup>
  import { ref, onMounted, onUnmounted } from 'vue';

  const props = defineProps({
    title: {
      type: String,
      required: true
    },
    bgColor: {
      type: String,
      default: '#E2E5F6'
    }
  });

  const emit = defineEmits(['scroll']);

  const itemsContainer = ref(null);
  const scrollbarThumb = ref(null);

  // Update scrollbar position based on container scroll
  const updateScrollbarPosition = () => {
    if (!itemsContainer.value || !scrollbarThumb.value) return;

    const container = itemsContainer.value;
    const thumb = scrollbarThumb.value;

    const scrollPercentage = container.scrollTop / (container.scrollHeight - container.clientHeight);
    const scrollbarHeight = 170;
    const thumbPosition = scrollPercentage * (scrollbarHeight - thumb.clientHeight);

    thumb.style.top = `${thumbPosition + 5}px`;
    emit('scroll', { container, scrollPercentage });
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
    <div class="base-section">
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
        <div class="scrollbar">
          <div class="scrollbar-thumb" ref="scrollbarThumb"></div>
        </div>
      </div>
    </div>
  </template>

  <style scoped>
  .base-section {
    position: relative;
    width: 600px;
    height: 250px;
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
