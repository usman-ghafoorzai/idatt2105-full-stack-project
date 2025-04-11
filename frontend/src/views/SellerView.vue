<template>
  <div id="seller-container">
    <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

    <form id="seller-form" class="elegant-card" @submit.prevent="submitForm">
      <label for="title">Title:</label>
      <input type="text" name="title" id="title" v-model="formData.title" required/>

      <label for="price">Price:</label>
      <div id="input-with-currency">
        <input 
          type="number" 
          name="price" 
          id="price" 
          min="0" 
          v-model="formData.price" 
          required
          step="0.01"
          />
        <span id="currency">kr</span>
      </div>

      <label for="description">Description:</label>
      <textarea name="description" id="description" cols="30" rows="10" v-model="formData.description" required></textarea>

      <label for="category" id="category-label">Category:</label>
      <div id="category-container">
        <button type="button" @click="toggleCategoryList" id="category-toggle">
          {{ showCategories ? 'Hide Categories' : 'Select Categories' }}
        </button>
        <ul v-if="showCategories" id="category-list">
          <li v-for="category in categories" :key="category.id">
            <input
              type="checkbox"
              :value="category.id"
              v-model="formData.categoryIds"
            />
            {{ category.name }}
          </li>
        </ul>
      </div>

      <label for="image">Upload image/images of item:</label>
      <input
        type="file"
        name="image"
        id="image"
        accept="image/jpeg, image/png"
        multiple
        @change="imageUpload"/>

      <label for="address">Chosen address:</label>
      <input type="text" name="address" id="address" disabled placeholder="click on the map" :value="address" />

      <div id="map"></div>

      <button type="submit" :disabled="isSubmitting">
        {{ isSubmitting ? 'Submitting...' : 'List Item' }}
      </button>
    </form>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import { getAddress } from '../utils/reverseGeoLocation.js';
import { getLoggedInUser } from "@/api/userAPI.js";
import { getAllCategories } from "@/api/categoryAPI.js";
import { createItem, uploadItemImages } from "@/api/itemAPI.js";

let map;
let marker;
const address = ref('');
const isSubmitting = ref(false);
const errorMessage = ref('');
const successMessage = ref('');
const categories = ref([]);
const userId = ref(null);
const showCategories = ref(false);
const toggleCategoryList = () => {
  showCategories.value = !showCategories.value;
};

const formData = ref({
  title: '',
  price: 0,
  description: '',
  categoryIds: [],
  images: [],
  longitude: 0,
  latitude: 0,
});

onMounted(async () => {
  map = L.map('map').setView([63.427029, 10.396700], 13);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
  }).addTo(map);

  map.on('click', setLocation);
  marker = L.marker([63.427029, 10.396700]).addTo(map);

  // Fetch categories from backend
  try {
    categories.value = await getAllCategories();
  } catch (error) {
    console.error('Error fetching categories:', error);
    errorMessage.value = 'Failed to load categories';
  }

  // Get logged in user
  try {
    const user = await getLoggedInUser();
    userId.value = user.id;
  } catch (error) {
    console.error('Error fetching user:', error);
    errorMessage.value = 'You must be logged in to list items';
  }
});

async function setLocation(e) {
  if (!map) {
    console.error('Map is not initialized');
    return;
  }
  const lat = e.latlng.lat;
  const lng = e.latlng.lng;
  marker.removeFrom(map);
  marker = L.marker([lat, lng]).addTo(map);
  console.log(`Latitude: ${lat}, Longitude: ${lng}`);
  formData.value.latitude = lat;
  formData.value.longitude = lng;

  try {
    address.value = await getAddress(lat, lng);
  } catch (error) {
    console.error('Error fetching address:', error);
    address.value = 'Unable to fetch address';
  }
}

function imageUpload(event) {
  const files = event.target.files;
  if (files.length > 6) {
    alert('You can only upload a maximum of 6 images.');
    event.target.value = '';
    return;
  }
  formData.value.images = [];
  for (let i = 0; i < files.length; i++) {
    const file = files[i];
    formData.value.images.push(file);
  }
  console.log('Uploaded images:', formData.value.images);
}

const submitForm = async (event) => {
  event.preventDefault();
  if (!formData.value.latitude || !formData.value.longitude) {
    alert('Please select a location on the map.');
    return;
  }
  if (formData.value.categoryIds.length === 0) {
    alert('Please select at least one category.');
    return;
  }
  if (!formData.value.title || !formData.value.price || !formData.value.description) {
    alert('Please fill in all required fields.');
    return;
  }
  if (!userId.value) {
    alert('You must be logged in to create a listing.');
    return;
  }

  isSubmitting.value = true;
  errorMessage.value = '';
  successMessage.value = '';

  try {
    // Create the item data object
    const itemData = {
      title: formData.value.title,
      description: formData.value.description,
      price: formData.value.price,
      category_ids: formData.value.categoryIds,
      seller_id: userId.value,
      locationLatitude: formData.value.latitude,
      locationLongitude: formData.value.longitude
    };

    // Create the item
    const createdItem = await createItem(itemData);

    // If we have images, upload them separately
    if (formData.value.images.length > 0) {
      await uploadItemImages(createdItem.id, formData.value.images);
    }

    // Show success message and reset form
    successMessage.value = 'Your item has been listed successfully!';
    resetForm();

    // Redirect to item page or show success message
    setTimeout(() => {
      successMessage.value = '';
      // router.push('/items/' + createdItem.id); // Uncomment if you want to redirect
    }, 3000);

  } catch (error) {
    console.error('Error creating item:', error);
    errorMessage.value = error.message || 'Failed to create listing';
  } finally {
    isSubmitting.value = false;
  }
};

const resetForm = () => {
  formData.value = {
    title: '',
    price: 0,
    description: '',
    categoryIds: [],
    images: [],
    longitude: 0,
    latitude: 0
  };
  address.value = '';
  // Remove marker from map
  if (marker) {
    marker.removeFrom(map);
    marker = L.marker([63.427029, 10.396700]).addTo(map);
  }
};
</script>

<style scoped>
    #map {
        grid-area: map;
        height: 400px;
        justify-self: center;
        width: 80%;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-top: 10px;
    }
    #seller-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 20px;
    }
    #seller-form {
        display: grid;
        grid-template-columns: 1fr 2fr;
        grid-template-areas:
            "title-label title"
            "price-label price"
            "description-label description"
            "category-label category"
            "image-label image"
            "address-label address"
            "map map"
            "submit submit";
        gap: 10px 20px;
        margin: 20px;
        padding: 20px;
        width: 600px;
        background-color: var(--color-light-bg);
    }
    #seller-form label {
        font-weight: bold;
        color: #333;
        align-self: start;
        margin-top: 8px;
        text-align: right;
    }

#seller-form input[type="text"],
#seller-form input[type="number"],
#seller-form select {
  width: 80%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
  box-sizing: border-box;
}
#seller-form textarea {
  width: 80%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 14px;
  box-sizing: border-box;
  resize: none;
}

#seller-form textarea {
  resize: none;
}

#price::after {
  content: ' kr';
  position: absolute;
  right: 10px;
}
#seller-form button {
  grid-area: submit;
  width: 50%;
  justify-self: center;
}
input:disabled {
  background-color: #f5f5f5;
  color: #999;
  cursor: not-allowed;
}
.elegant-card:hover {
  transform: none;
}
.success-message {
  background-color: #d4edda;
  color: #155724;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
  text-align: center;
}

.error-message {
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 20px;
  width: 100%;
  max-width: 600px;
  text-align: center;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

#category-list {
  list-style: none;
  padding: 0;
  margin: 10px 0;
}

#category-list li {
  margin-bottom: 5px;
}

#category-container {
  margin-bottom: 20px;
}
@media (max-width: 600px) {
  #seller-form {
    width: 100%;
    grid-template-columns: 1fr;
    grid-template-areas:
                "title-label"
                "title"
                "price-label"
                "price"
                "description-label"
                "description"
                "category-label"
                "category"
                "image-label"
                "image"
                "address-label"
                "address"
                "map"
                "submit";
    padding: 10px 0;
  }
  #seller-form label {
    text-align: left;
  }
  #map {
    width: 100%;
  }
}
</style>
