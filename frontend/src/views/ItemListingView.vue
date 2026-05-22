<script setup>
    import FilterSidebar from '../components/FilterSidebar.vue';
    import ItemListingComponent from '../components/ItemListingComponent.vue';
    import { useSearchStore } from '../stores/SearchStore.js';

    const searchStore = useSearchStore();

</script>

<template>
    <div id="item-listing-view-container">
        <FilterSidebar id="filter-sidebar"></FilterSidebar>
        <ItemListingComponent :items="searchStore.searchResults" :searchTerm="searchStore.searchTerm"></ItemListingComponent>
    </div>
</template>

<style scoped>
    #item-listing-view-container {
        display: grid;
        grid-template-columns: auto 3fr;
        justify-content: space-between;
        width: 100%;
    }

    @media (max-width: 900px) {
        #item-listing-view-container {
            grid-template-columns: auto;
            justify-content: center;
        }
        #filter-sidebar {
            display: none;
        }
    }
</style>