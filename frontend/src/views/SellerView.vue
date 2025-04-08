<template>
    <div id="seller-container">
        <form id="seller-form">
            <label for="title">Title: </label>
            <input type="text" name="title" id="title" />
            <label for="price">Price: </label>
            <input type="number" name="price" id="price" />
            <label for="description">Description: </label>
            <textarea name="description" id="description" cols="30" rows="10"></textarea>
            <label for="category">Main category:</label>
            <select name="category" id="category">
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
            <label for="image">Upload image/images of item: </label>
            <input type="file" name="image" id="image" accept="image/*" />
            <label for="adress">Chosen adress: </label>
            <input type="text" name="adress" id="adress" disabled placeholder="click on the map"/>
            <div id="map"></div>
            <button type="submit">Submit</button>
        </form>
    </div>
</template>

<script setup>
    import { onMounted } from 'vue';
    import 'leaflet/dist/leaflet.css';
    import L from 'leaflet';

    let map;
    let marker;
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
    }
</script>

<style scoped>
    #map {
        height: 400px;
        width: 400px;
    }
    #seller-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        margin-top: 20px;
    }
    #seller-form {
        display: flex;
        flex-direction: column;
        gap: 10px;
        width: 300px;
    }
</style>