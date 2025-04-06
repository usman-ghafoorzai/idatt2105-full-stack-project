<script setup>
import Header from './components/Header.vue'
import RegisterUser from './components/RegisterUser.vue'
import LogInComponent from './components/LogInComponent.vue'
import ItemFrameComponent from './components/ItemFrameComponent.vue'
import ItemListingComponent from './components/ItemListingComponent.vue'
import FilterSidebar from './components/FilterSidebar.vue'
import ItemDescription from './components/ItemDescription.vue'
</script>

<template>
  <Header></Header>
  <ItemDescription
    title="Bayliner VR 5 Cuddy OB Lite brukt"
    price="10000"
    description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
    image="src/assets/images/boat.jpg"
    location="Oslo"
    :categories="['Boat', 'Used']"
  ></ItemDescription>
</template>

<style>
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
}

</style>
