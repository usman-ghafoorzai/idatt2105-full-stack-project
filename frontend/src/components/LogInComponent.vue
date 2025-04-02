<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');

const login = () => {
  // TODO: implementer API logikk her etterhvert
  console.log({ //Foreløpig bare logge verdiene for dev og testing
    username: username.value,
    password: password.value
  });
}
</script>

<template>
  <div class="login-container">
    <p class="login-header">Login to have more access to other features!</p>

    <div class="login-form">
      <div class="form-group">
        <label class="username-label">Username:</label>
        <div class="input-field">
          <input type="text" v-model="username">
        </div>
      </div>

      <div class="form-group">
        <label class="password-label">Password:</label>
        <div class="input-field">
          <input type="password" v-model="password">
        </div>
      </div>

      <div class="login-button" @click="login">Login!</div>
    </div>
  </div>
</template>


<style scoped>

</style>
