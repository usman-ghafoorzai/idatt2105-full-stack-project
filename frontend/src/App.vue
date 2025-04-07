<script setup>
import Header from './components/Header.vue'
import RegisterUser from './components/RegisterUser.vue'
import LogInComponent from './components/LogInComponent.vue'
import ItemFrameComponent from './components/ItemFrameComponent.vue'
import ItemListingComponent from './components/ItemListingComponent.vue'
import FilterSidebar from './components/FilterSidebar.vue'
import ItemDescription from './components/ItemDescription.vue'
import { RouterView } from 'vue-router';
</script>

<template>
  <Header></Header>
  <RouterView/>
</template>

<style>
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
}

</style>
