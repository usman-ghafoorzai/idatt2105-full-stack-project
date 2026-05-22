<script setup>
import { ref } from 'vue';
import { getLoggedInUser } from '../api/userAPI.js';

const emit = defineEmits(['click']);
const isProcessing = ref(false);

async function handleReserve() {
  if (isProcessing.value) return;

  isProcessing.value = true;
  try {
    // Check if user is logged in
    const user = await getLoggedInUser();
    if (user) {
      emit('click'); // Only emit if we have a valid user
    }
  } catch (error) {
    console.error("Authentication error:", error.message);
    alert("Please log in to reserve items");
  } finally {
    isProcessing.value = false;
  }
}
</script>

<template>
  <button @click="handleReserve" :disabled="isProcessing">
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
