<script setup>
import Header from './components/Header.vue'
import RegisterUser from './components/RegisterUser.vue'
import LogInComponent from './components/LogInComponent.vue'
import ItemFrameComponent from './components/ItemFrameComponent.vue'
import ItemListingComponent from './components/ItemListingComponent.vue'
import FilterSidebar from './components/FilterSidebar.vue'
</script>

<template>
  <Header></Header>
  <div id="filter">
    <FilterSidebar></FilterSidebar>
    <ItemListingComponent></ItemListingComponent>
  </div>
</template>

<style>
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
}
#filter {
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100vh;
}

</style>
