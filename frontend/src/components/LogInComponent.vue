<script setup>
import { ref } from 'vue';

const username = ref('');
const password = ref('');
const errors = ref({
  username: '',
  password: '',
});

const login = () => {
  errors.value = {
    username: '',
    password: '',
  };

  let valid = true;

  if (!username.value.trim()) {
    errors.value.username = 'Brukernavn er påkrevd';
    valid = false;
  }

  if (!password.value.trim()) {
    errors.value.password = 'Passord er påkrevd';
    valid = false;
  }

  if (!valid) return;

  // TODO: implementer API logikk her etterhvert
  console.log({
    username: username.value,
    password: password.value,
  });
};
</script>

<template>
  <div class="login-container">
    <h2 class="login-header">Login to have more access to other features!</h2>

    <form class="login-form" @submit.prevent="login">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username">
      </div>

      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password">
      </div>

      <button type="submit" class="login-button">Login!</button>
    </form>
  </div>
</template>

<style scoped>
.login-container {
  width: 100%;
  max-width: 450px;
  margin: 0;
  padding: 20px;
}

.login-header {
  font-family: 'ABeeZee', sans-serif;
  font-weight: 400;
  font-size: 24px;
  line-height: 1.2;
  color: #000000;
  text-align: center;
  margin-bottom: 30px;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-family: 'ABeeZee', sans-serif;
  font-size: 18px;
  font-weight: 400;
  color: #000000;
}

.form-group input {
  width: 100%;
  padding: 10px 0;
  font-family: 'ABeeZee', sans-serif;
  font-size: 16px;
  background: transparent;
  border: none;
  border-bottom: 1px solid #000000;
  outline: none;
  transition: border-color 0.3s;
}

.form-group input:focus {
  border-color: #555;
}

.login-button {
  align-self: center;
  margin-top: 15px;
  width: 100%;
  max-width: 300px;
  height: 40px;
  background: #4D4D4D;
  color: white;
  border: none;
  border-radius: 4px;
  font-family: 'Roboto', sans-serif;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-button:hover {
  background: #3D3D3D;
}

@media (max-width: 600px) {
  .login-container {
    padding: 15px;
  }

  .login-header {
    font-size: 20px;
  }

  .form-group label {
    font-size: 16px;
  }

  .login-button {
    font-size: 18px;
  }
}
</style>
