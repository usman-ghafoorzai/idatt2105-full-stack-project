<script setup>
    import { ref, onMounted, watch } from 'vue';
    import { useCategoryStore } from '../stores/CategoryStore.js';
    import { useSearchStore } from '../stores/SearchStore.js';
    import 'leaflet/dist/leaflet.css';
    import L from 'leaflet';
    import { getAddress } from '../utils/reverseGeoLocation.js';
    
    let map;
    let marker;
    const searchStore = useSearchStore();
    const categoryStore = useCategoryStore();
    const categories = categoryStore.categories;
    const categoryVisibility = ref(false);
    const selectedSubcategories = ref([]);
    const minPrice = ref(0);
    const maxPrice = ref(Infinity);
    const maxDistance = ref(10); // in km
    const userLocation = ref({ lat: 63.427029, lng: 10.396700 });

    onMounted(async () => {
        map = L.map('map').setView([userLocation.value.lat, userLocation.value.lng], 13);
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
        }).addTo(map);

        marker = L.marker([userLocation.value.lat, userLocation.value.lng]).addTo(map)

        map.on('click', (e) => {
            userLocation.value = { lat: e.latlng.lat, lng: e.latlng.lng };
            marker.setLatLng([e.latlng.lat, e.latlng.lng]);
        });

        try {
            await categoryStore.fetchCategories();
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    });

    // funksjon hentet fra nett
    function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
        const R = 6371; // Radius of the earth in km
        const dLat = deg2rad(lat2 - lat1);
        const dLon = deg2rad(lon2 - lon1);
        const a =
            Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
            Math.sin(dLon / 2) * Math.sin(dLon / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }

    function deg2rad(deg) {
        return deg * (Math.PI / 180);
    }

    watch([selectedSubcategories, minPrice, maxPrice, maxDistance, userLocation], async ([newSelectedCategories, newMinPrice, newMaxPrice, newMaxDistance, newUserLocation]) => {
        if (newSelectedCategories.length === 0 && 
            newMinPrice === 0 && 
            (newMaxPrice === Infinity || newMaxPrice === 0 || newMaxPrice === '') &&
            newMaxDistance === 0
        ) {
            // no categories selected causes the search result to be rest
            searchStore.setSearchResults(searchStore.originalResults);
            return;
        }

        // filter items locally based on selected categories instead of doing api calls
        const filteredResults = searchStore.originalResults.filter(item => {
            const matchesCategory = newSelectedCategories.length === 0 || item.categories.some(category => newSelectedCategories.includes(category.id));
            const matchesPrice = item.price >= newMinPrice && item.price <= newMaxPrice;
            const matchesDistance = getDistanceFromLatLonInKm(
                newUserLocation.lat,
                newUserLocation.lng,
                item.locationLatitude,
                item.locationLongitude
            ) <= newMaxDistance;

            return matchesCategory && matchesPrice && matchesDistance;
        });

        console.log('Filtered results:', filteredResults);
        searchStore.setSearchResults(filteredResults);
    });
</script>

<template>
    <div class="filter-sidebar">
        <div class="category">
            <h3>Location within 10km</h3>
        </div>
        <div id="map"></div>
        <div class="category">
            <span></span>
            <h3>Price range</h3>
            <div id="price-container">
                <div id="from-container">
                    <div>From</div>
                    <input type="number" id="from-price" min="0" v-model.number="minPrice"></input>
                </div>
                <div id="to-container">
                    <div>To</div>
                    <input type="number" id="to-price" min="0" v-model.number="maxPrice"></input>
                </div>
            </div>
        </div>
        <div class="category">
            <fa icon="chevron-right" v-if="!categoryVisibility" @click="categoryVisibility = !categoryVisibility"></fa>
            <fa icon="chevron-down" v-if="categoryVisibility" @click="categoryVisibility = !categoryVisibility"></fa>
            <h3 @click="categoryVisibility = !categoryVisibility">Categories</h3>
            <ul v-if="categoryVisibility">
                <li v-for="category in categories" :key="category.id">
                    <input
                        type="checkbox"
                        :value="category.id"
                        v-model="selectedSubcategories"
                    />
                    {{ category.name }}
                </li>
            </ul>
        </div>
    </div>
</template>

<style scoped>
    .filter-sidebar {
        display: flex;
        flex-direction: column;
        gap: 10px;
        padding: 20px;
        width: 350px;
        min-height: 100vh;
        background-color: var(--color-light-bg);
        border-right: 2px solid black;
        align-content: center;
        overflow-y: auto;
    }
    #map {
        height: 400px;
        width: 100%;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-top: 10px;
    }
    .category {
        display: grid;
        grid-template-columns: 16px 3fr;
        grid-template-rows: auto;
        grid-template-areas: 
            'chevron category'
            'chevron list';
        align-items: baseline;
        min-height: auto;
        column-gap: 3px;
        margin: 10px;
    }
    .category span {
        grid-area: chevron;
    }
    #price-container {
        display: grid;
        width: 100%;
        grid-template-areas: 'from to';
        grid-template-columns: auto auto;
        padding-left: 15px;
        width: fit-content; 
    }
    #from-container, #to-container {
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
        align-items: center;
        height: 100%;
        gap: 10px;
    }
    #from-price, #to-price {
        border: none;
        border-bottom: 2px solid black;
        width: 50px;
    }
    #from-price:focus, #to-price:focus {
        outline:none;
        border-bottom: 2px solid black;
    }
    ul {
        grid-area: list;
        list-style-type: none;
        padding-left: 15px;
        margin: 10px 0;
    }
    li {
        margin-bottom: 10px;
        font-size: 18px;
    }
    h3 {
        grid-area: category;
        font-size: 24px;
        margin-bottom:0;
        margin-top: 10px;
        border-bottom: 2px solid black;
    }

    @media (max-width: 1410px) {
        .filter-sidebar  {
            width: 280px;
        }
    }
</style>