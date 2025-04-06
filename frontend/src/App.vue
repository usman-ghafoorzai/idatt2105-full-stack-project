<script setup>
import Header from './components/Header.vue'
import RegisterUser from './components/RegisterUser.vue'
import LogInComponent from './components/LogInComponent.vue'
import ItemFrameComponent from './components/ItemFrameComponent.vue'
import ItemListingComponent from './components/ItemListingComponent.vue'
import FilterSidebar from './components/FilterSidebar.vue'
import ItemPicture from './components/ItemPicture.vue'

const images = [
      'src/assets/images/boat.jpg',
      'src/assets/images/Thor.png',
    ];
</script>

<template>
  <Header></Header>
  <ItemPicture
    :images="images"
  ></ItemPicture>
</template>

<style>
body {
  margin: 0;
  font-family: 'Roboto', sans-serif;
}

</style>
