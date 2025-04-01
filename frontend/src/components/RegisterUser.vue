<script setup>
import { ref } from 'vue';

const password = ref('');
const email = ref('');
const firstName = ref('');
const lastName = ref('');
const location = ref('');

const register = () => {
  // TODO: implementere API logikk etterhvert
  console.log({   // Foreløpig bare logge verdiene, skal erstattes med API-kall
    password: password.value,
    email: email.value,
    firstName: firstName.value,
    lastName: lastName.value,
    location: location.value
  });
}
</script>

<template>
  <div class="create-account">
    <div class="form-header">
      <h1>Create account</h1>
    </div>

    <div class="form-field first-name">
      <label for="firstName">FIRST NAME</label>
      <div class="textbox">
        <input id="firstName" type="text" v-model="firstName" placeholder="Enter first name">
      </div>
    </div>

    <div class="form-field last-name">
      <label for="lastName">LAST NAME</label>
      <div class="textbox">
        <input id="lastName" type="text" v-model="lastName" placeholder="Enter last name">
      </div>
    </div>

    <div class="form-field email">
      <label for="email">EMAIL</label>
      <div class="textbox">
        <input id="email" type="email" v-model="email" placeholder="Enter email">
      </div>
    </div>

    <div class="form-field location">
      <label for="location">LOCATION</label>
      <div class="textbox">
        <input id="location" type="text" v-model="location" placeholder="Enter location">
      </div>
    </div>

    <div class="form-field password">
      <label for="password">PASSWORD</label>
      <div class="textbox">
        <input id="password" type="password" v-model="password" placeholder="Enter password">
      </div>
    </div>
    <button class="register-button" @click="register">Register</button>
  </div>
</template>

<style scoped>
.create-account {
  position: absolute;
  width: 400px;
  height: 425px;
  left: 900px;
  top: 239px;
}

.form-header {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 10px;
  gap: 10px;
  position: absolute;
  width: 300px;
  height: 60px;
  left: 0;
  top: 0;
}

.form-header h1 {
  width: 302px;
  height: 48px;
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 700;
  font-size: 40px;
  line-height: 48px;
  color: #4D4D4D;
}

.form-field {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 0;
  gap: 4px;
  position: absolute;
  width: 300px;
  height: 60px;
}

.password {
  left: 0;
  top: 73px;
}

.last-name {
  left: 0;
  top: 146px;
}

.email {
  left: 0;
  top: 219px;
}

.first-name {
  left: 0;
  top: 292px;
}

.location {
  left: 0;
  top: 365px;
}

label {
  height: 11px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 9px;
  line-height: 11px;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: #000000;
}

.textbox {
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 8px;
  gap: 10px;
  width: 300px;
  height: 30px;
  background: #FFFFFF;
  border: 1px solid #A6A6A6;
  border-radius: 4px;
}

input {
  width: 284px;
  height: 14px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 14px;
  color: #A6A6A6;
  border: none;
  outline: none;
  flex-grow: 1;
}

.register-button {
  position: absolute;
  width: 300px;
  height: 40px;
  left: 0;
  top: 440px;
  background: #4D4D4D;
  color: white;
  border: none;
  border-radius: 4px;
  font-family: 'Roboto', sans-serif;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
}

.register-button:hover {
  background: #3D3D3D;
}
</style>
