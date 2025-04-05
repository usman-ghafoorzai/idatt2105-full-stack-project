<script setup>
    import { ref } from 'vue';
    import categoryStore from '../assets/CategoryStore.js';
    let place = ref(false);
    let item = ref('clothing');
    let categories = categoryStore.categories;
    let visibility = ref({});
    const selectedSubcategories = ref([]);

    for (let categoryKey in categories) {
        if (categoryKey === item.value) {
            for (let subcategoryKey in categories[categoryKey]) {
                visibility.value[subcategoryKey] = false;
            }
        }
    }

    function toggleVisibility(subcategoryKey) {
        visibility.value[subcategoryKey] = !visibility.value[subcategoryKey];
        console.log(selectedSubcategories.value);
    }
</script>

<template>
    <div class="filter-sidebar">
        <div class="category">
            <fa icon="chevron-right" v-if="!place" @click="place = !place"></fa>
            <fa icon="chevron-down" v-if="place" @click="place = !place"></fa>
            <h3 @click="place = !place">Place</h3>
            <ul v-if="place">
            <li>
                <input type="checkbox" value="Troms og Finnmark" v-model="selectedSubcategories" />
                Troms og Finnmark
            </li>
            <li>
                <input type="checkbox" value="Nordland" v-model="selectedSubcategories" />
                Nordland
            </li>
            <li>
                <input type="checkbox" value="Trøndelag" v-model="selectedSubcategories" />
                Trøndelag
            </li>
            <li>
                <input type="checkbox" value="Møre og Romsdal" v-model="selectedSubcategories" />
                Møre og Romsdal
            </li>
            <li>
                <input type="checkbox" value="Innlandet" v-model="selectedSubcategories" />
                Innlandet
            </li>
            <li>
                <input type="checkbox" value="Oslo" v-model="selectedSubcategories" />
                Oslo
            </li>
            <li>
                <input type="checkbox" value="Viken" v-model="selectedSubcategories" />
                Viken
            </li>
            <li>
                <input type="checkbox" value="Vestlandet" v-model="selectedSubcategories" />
                Vestlandet
            </li>
            <li>
                <input type="checkbox" value="Rogaland" v-model="selectedSubcategories" />
                Rogaland
            </li>
            <li>
                <input type="checkbox" value="Agder" v-model="selectedSubcategories" />
                Agder
            </li>
            <li>
                <input type="checkbox" value="Vestfold og Telemark" v-model="selectedSubcategories" />
                Vestfold og Telemark
            </li>
        </ul>
        </div>
        <div class="category">
            <span></span>
            <h3>Price range</h3>
            <div id="price-container">
                <div id="from-container">
                    <div>From</div>
                    <input type="number" id="from-price"></input>
                </div>
                <div id="to-container">
                    <div>To</div>
                    <input type="number" id="to-price"></input>
                </div>
            </div>
        </div>

        <template v-for="(subcategories, subcategoryKey) in categories[item]" :key="subcategoryKey">
            <div class="category">
                <fa icon="chevron-right" v-if="!visibility[subcategoryKey]" @click="toggleVisibility(subcategoryKey)"></fa>
                <fa icon="chevron-down" v-if="visibility[subcategoryKey]" @click="toggleVisibility(subcategoryKey)"></fa>
                <h3 @click="toggleVisibility(subcategoryKey)">{{ subcategoryKey }}</h3>
                <ul v-if="visibility[subcategoryKey]">
                    <li v-for="(subcategory, index) in subcategories" :key="index">
                        <input 
                            type="checkbox"
                            :value="subcategory"
                            v-model="selectedSubcategories"
                        /> 
                        {{ subcategory }}
                    </li>
                </ul>
            </div>
        </template>
    </div>
</template>

<style scoped>
    .filter-sidebar {
        display: flex;
        flex-direction: column;
        gap: 20px;
        padding: 20px;
        width: 350px;
        min-height: 100%;
        border-right: 2px solid black;
        align-content: center;
        overflow-y: scroll;
    }
    .category {
        display: grid;
        grid-template-columns: 16px 3fr;
        grid-template-rows: auto;
        grid-template-areas: 
            'chevron category'
            'chevron list';
        align-items: baseline;
        min-height: 100px;
        column-gap: 3px;
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

    @media (max-width: 1410px) {
        .filter-sidebar  {
            width: 280px;
        }
    }
    @media (max-width: 900px) {
        .filter-sidebar {
            display: none;
        }
    }
</style>