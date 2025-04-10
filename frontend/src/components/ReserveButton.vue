<script setup>
import { getLoggedInUser } from '../api/userAPI.js';

const emit = defineEmits(['click']);

async function handleReserve() {
  try {
    // Check if user is logged in
    await getLoggedInUser();
    emit('click'); // Trigger the modal in parent component
  } catch (error) {
    alert("Please log in to reserve items");
  }
}
</script>

<template>
  <button @click="handleReserve">
    Reserve now!
  </button>
</template>

<style scoped>
    button {
        text-align: center;
        display: inline-block;
        font-size: 20px;
    }

</style>
