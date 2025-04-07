<script setup>
import Header from './components/Header.vue'
import RegisterUser from './components/RegisterUser.vue'
import LogInComponent from './components/LogInComponent.vue'
import ItemFrameComponent from './components/ItemFrameComponent.vue'
import ItemListingComponent from './components/ItemListingComponent.vue'
import FilterSidebar from './components/FilterSidebar.vue'
import ItemDescription from './components/ItemDescription.vue'
import BuyNowButton from './components/BuyNowButton.vue'
import ReserveButton from './components/ReserveButton.vue'
</script>

<template>
  <Header></Header>
  <router-view/>
</template>

<style>
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
}

</style>
