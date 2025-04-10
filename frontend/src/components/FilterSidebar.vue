<script setup>
    import { ref, onMounted, watch } from 'vue';
    import { useCategoryStore } from '../stores/CategoryStore.js';
    import { useSearchStore } from '../stores/SearchStore.js';
    import { fetchSearchResults, fetchSearchResultsByCategory } from '@/api/searchItemsAPI.js';
    const place = ref(false);
    const searchStore = useSearchStore();
    const categoryStore = useCategoryStore();
    const categories = categoryStore.categories;
    const categoryVisibility = ref(false);
    const selectedSubcategories = ref([]);
    const minPrice = ref(0);
    const maxPrice = ref(Infinity);

    onMounted(async () => {
        try {
            await categoryStore.fetchCategories();
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    });

    watch([selectedSubcategories, minPrice, maxPrice], async ([newSelectedCategories, newMinPrice, newMaxPrice]) => {
        if (newSelectedCategories.length === 0 && newMinPrice === 0 && newMaxPrice === Infinity) {
            // no categories selected causes the search result to be rest
            searchStore.setSearchResults(searchStore.originalResults);
            return;
        }

        // filter items locally based on selected categories instead of doing api calls
        const filteredResults = searchStore.originalResults.filter(item => {
            const matchesCategory = newSelectedCategories.length === 0 || item.categories.some(category => newSelectedCategories.includes(category.id));
            const matchesPrice = item.price >= newMinPrice && item.price <= newMaxPrice;
            return matchesCategory && matchesPrice;
        });

        console.log('Filtered results:', filteredResults); // Debugging
        searchStore.setSearchResults(filteredResults); // Update the search results
    });

    /*for (let categoryKey in categories) {
        if (categoryKey === item.value) {
            for (let subcategoryKey in categories[categoryKey]) {
                visibility.value[subcategoryKey] = false;
            }
        }
    }

    function toggleVisibility(subcategoryKey) {
        visibility.value[subcategoryKey] = !visibility.value[subcategoryKey];
    }*/
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
                    <input type="number" id="from-price" min="0" v-model.number="minPrice"></input>
                </div>
                <div id="to-container">
                    <div>To</div>
                    <input type="number" id="to-price" min="0" v-model.number="maxPrice"></input>
                </div>
            </div>
        </div>

       <!-- <template v-for="(subcategories, subcategoryKey) in categories[item]" :key="subcategoryKey">
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
        </template> -->

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
        height: 100vh;
        background-color: var(--color-light-bg);
        border-right: 2px solid black;
        align-content: center;
        overflow-y: auto;
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