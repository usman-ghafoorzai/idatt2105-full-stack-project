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
.login-container {
  position: relative;
  width: 513.95px;
  height: 271px;
  left: 34px;
  top: 245px;
}

.login-header {
  position: absolute;
  width: 513.95px;
  height: 21.02px;
  left: 0;
  top: 0;
  font-family: 'ABeeZee', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 28px;
  text-align: center;
  color: #000000;
}

.username-label {
  position: absolute;
  width: 124.41px;
  height: 21.02px;
  left: 104.01px;
  top: 74.32px;
  font-family: 'ABeeZee', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 28px;
  text-align: center;
  color: #000000;
}

.password-label {
  position: absolute;
  width: 118.29px;
  height: 21.02px;
  left: 110.13px;
  top: 188.42px;
  font-family: 'ABeeZee', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 28px;
  text-align: center;
  color: #000000;
}

.input-field {
  box-sizing: border-box;
  position: absolute;
  width: 167.24px;
  height: 18.77px;
  background: #FFFFFF;
  border-bottom: 1px solid #000000;
}

.form-group:nth-child(1) .input-field {
  left: 241.68px;
  top: 76.57px;
}

.form-group:nth-child(2) .input-field {
  left: 241.68px;
  top: 190.68px;
}

input {
  width: 100%;
  height: 100%;
  border: none;
  outline: none;
  background: transparent;
  font-family: 'ABeeZee', sans-serif;
  font-size: 16px;
}

.login-button {
  position: absolute;
  width: 132px;
  height: 37px;
  left: 224.35px;
  top: 249.98px;
  background: #E2E5F6;
  border-radius: 15px;
  font-family: 'ABeeZee', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 28px;
  text-align: center;
  color: #000000;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.login-button:hover {
  background: #d2d6f0;
}
</style>
