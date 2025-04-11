<script setup>
import { ref, onMounted } from 'vue';
import { useCategoryStore } from '../stores/CategoryStore.js';
import { useSearchStore } from '../stores/SearchStore.js';
import { useRouter } from 'vue-router';

const emit = defineEmits(['close']);
const categoryStore = useCategoryStore();
const searchStore = useSearchStore();
const categories = ref([]);
const isLoading = ref(false);
const error = ref(null);
const router = useRouter();

// Fetch categories from the store or API
const fetchCategories = async () => {
  try {
    isLoading.value = true;
    error.value = null;

    // Check if categories exist in the store
    if (categoryStore.categories.length > 0) {
      categories.value = categoryStore.categories.filter(
        (category) => category.parentCategory === null
      );
    } else {
      // Fetch from API if not in the store
      await categoryStore.fetchCategories();
      categories.value = categoryStore.categories.filter(
        (category) => category.parentCategory === null
      );
    }
  } catch (err) {
    error.value = err.message || 'Failed to load categories.';
  } finally {
    isLoading.value = false;
  }
};

// Close the popup
const closePopup = () => {
  emit('close');
};

const navigateToSearch = (category) => {
    closePopup();
    searchStore.fetchSearchResultsByCategory(category);
    router.push('/search');
};

// Fetch categories when the component is mounted
onMounted(fetchCategories);
</script>

<template>
  <div class="popup-overlay" @click.self="closePopup">
    <div class="popup">
      <button class="close-button" @click="closePopup">X</button>
      <h2>Categories</h2>
      <div v-if="isLoading">Loading...</div>
      <div v-else-if="error">{{ error }}</div>
      <div v-else>
        <ul>
          <li v-for="category in categories" :key="category.id">
            <strong @click="navigateToSearch(category.name)">{{ category.name }}</strong>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  background-color: --var(--color-coral);
}

.popup {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  max-height: 80%;
  overflow-y: auto;
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: black;
}

h2 {
  margin-top: 0;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  margin: 5px 0;
}

li:hover {
  cursor: pointer;
  text-decoration: underline;
  color: var(--color-coral)
}
</style>