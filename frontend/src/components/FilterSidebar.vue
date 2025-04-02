<script setup>
    import { ref } from 'vue';
    import categoryStore from '../assets/CategoryStore.js';
    let place = ref(false);
    let sizes = ref(false);
    let item = ref('clothing');
    let categories = categoryStore.categories;
    let visibility = ref({});

    for (let categoryKey in categories) {
        if (categoryKey === item.value) {
            for (let subcategoryKey in categories[categoryKey]) {
                visibility.value[subcategoryKey] = false;
            }
        }
    }

    function toggleVisibility(subcategoryKey) {
        visibility.value[subcategoryKey] = !visibility.value[subcategoryKey];
    }

</script>

<template>
    <div class="filter-sidebar">
        <div class="category" @click="place = !place">
            <fa icon="chevron-right" v-if="!place"></fa>
            <fa icon="chevron-down" v-if="place"></fa>
            <h3>Place</h3>
        </div>
        <ul v-if="place">
            <li><input type="checkbox"/>Trondheim</li>
            <li><input type="checkbox"/>Oslo</li>
            <li><input type="checkbox"/>Tromsø</li>
            <li><input type="checkbox"/>Stavanger</li>
            <li><input type="checkbox"/>Gjøvik</li>
            <li><input type="checkbox"/>Telemark</li>
            <li><input type="checkbox"/>Finmark</li>
        </ul>
        <div class="category">
            <span></span>
            <h3>Price range</h3>
        </div>

        <!-- Dynamic rendering of the filters -->
        <template v-for="(subcategories, subcategoryKey) in categories[item]" :key="subcategoryKey">
            <div class="category" @click="toggleVisibility(subcategoryKey)">
                <fa icon="chevron-right" v-if="!visibility[subcategoryKey]"></fa>
                <fa icon="chevron-down" v-if="visibility[subcategoryKey]"></fa>
                <h3>{{ subcategoryKey }}</h3>
            </div>
            <ul v-if="visibility[subcategoryKey]">
                <li v-for="(subcategory, index) in subcategories" :key="index">
                    <input type="checkbox" /> {{ subcategory }}
                </li>
            </ul>
        </template>
    </div>
</template>

<style scoped>
    .filter-sidebar {
        display: flex;
        flex-direction: column;
        padding: 20px;
        width: 250px;
        height: 100vh;
        border-right: 2px solid black;
        align-content: center;
        overflow-y: scroll;
    }
    .category {
        display: grid;
        grid-template-columns: 16px 3fr;
        grid-template-areas: 'chevron category';
        align-items: baseline;
        column-gap: 3px;
    }
    .category span {
        grid-area: chevron;
    }
    ul {
        list-style-type: none;
        padding-left: 15px;
        
    }
    li {
        margin-bottom: 10px;
        font-size: 18px;
    }
    h3 {
        grid-area: category;
        font-size: 24px;
        margin-bottom:0;
        border-bottom: 2px solid black;
    }
</style>