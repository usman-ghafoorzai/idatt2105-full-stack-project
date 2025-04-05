<script setup>
    import FilterSidebar from '../components/FilterSidebar.vue';
    import ItemListingComponent from '../components/ItemListingComponent.vue';
</script>

<template>
    <div id="item-listing-view-container">
        <FilterSidebar></FilterSidebar>
        <ItemListingComponent></ItemListingComponent>
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
    }
</style>