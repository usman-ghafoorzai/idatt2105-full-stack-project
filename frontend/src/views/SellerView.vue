<template>
    <div id="seller-container">
        <form id="seller-form" class="elegant-card" @submit="submitForm">
                <label for="title">Title:</label>
                <input type="text" name="title" id="title" v-model="formData.title"/>
                <label for="price">Price:</label>
                <div id="input-with-currency">
                    <input type="number" name="price" id="price" min="0" v-model="formData.price"/>
                    <span id="currency">kr</span>
                </div>
                <label for="description">Description:</label>
                <textarea name="description" id="description" cols="30" rows="10" v-model="formData.description"></textarea>
                <!--TODO: endre til å hente data fra backend-->
                <label for="category">Main category:</label>
                <select name="category" id="category" v-model="formData.category">
                    <option value="electronics">Electronics</option>
                    <option value="clothing">Clothing</option>
                    <option value="home">Home</option>
                    <option value="toys">Toys</option>
                    <option value="books">Books</option>
                    <option value="sports">Sports</option>
                    <option value="automotive">Automotive</option>
                    <option value="health">Health</option>
                    <option value="beauty">Beauty</option>
                    <option value="other">Other</option>
                </select>
                <label for="image">Upload image/images of item:</label>
                <input 
                    type="file"
                    name="image" 
                    id="image" 
                    accept="image/jpeg, image/png"
                    multiple 
                    @change="imageUpload"/>
                <label for="adress">Chosen address:</label>
                <input type="text" name="adress" id="adress" disabled placeholder="click on the map" :value="address" />
                <div id="map"></div>
            <button type="submit" @click="submitForm">Submit</button>
        </form>
    </div>
</template>

<script setup>
    import { onMounted, ref } from 'vue';
    import 'leaflet/dist/leaflet.css';
    import L from 'leaflet';
    import { useGeolocation } from '@vueuse/core';
    import axios from 'axios';

    let map;
    let marker;
    const address = ref('');
    const formData = ref({
        title: '',
        price: 0,
        description: '',
        category: '',
        images: [],
        longitude: 0,
        latitude: 0,
    });

    onMounted(() => {
        map = L.map('map').setView([63.427029, 10.396700], 13);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
        }).addTo(map);

        map.on('click', setLocation);
        marker = L.marker([63.427029, 10.396700]).addTo(map);
    });
    function setLocation(e) {

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
        getAddress(lat, lng);
    }

    async function getAddress(lat, lng) {
        const response = await axios.get(
            // Gratis API for reverse geocoding, men grunnet derfor har vi ingen garanti for at den alltid fungerer
            `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`
        );
        if (response.data && response.data.display_name) {
            address.value = response.data.display_name;
            console.log(`Address: ${address.value}`);
        } else {
            console.error('No address found');
        }
    }

    function imageUpload(event) {
        const files = event.target.files;
        if (files.length > 6) {
            alert('You can only upload a maximum of 6 images.');
            event.target.value = '';
            return;
        }
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            formData.value.images.push(file);
        };
        console.log('Uploaded images:', formData.value.images);
    }

    const submitForm = async (event) => {
        event.preventDefault();
        if (!formData.value.latitude || !formData.value.longitude) {
            alert('Please select a location on the map.');
            return;
        }
        if (!formData.value.title || !formData.value.price || !formData.value.description || !formData.value.category) {
            alert('Please fill in all required fields.');
            return;
        }
        console.log('Form submitted:', formData.value);
    }
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
        align-self: center;
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


    @media (max-width: 768px) {
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
            padding: 10px 0 10px 10px;
        }
        #seller-form label {
            text-align: left;
        }
        #map {
            width: 100%;
        }
    }
</style>