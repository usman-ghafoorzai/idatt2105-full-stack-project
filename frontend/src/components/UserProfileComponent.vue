<script setup>
import UploadPicture from './UploadPicture.vue';

// Example user data - replace with actual data later
const user = {
  name: 'Usman Ghafoorzai',
  username: 'usmangha',
  email: 'usmangha@hotmail.com',
  phone: '96803483',
  location: 'Trondheim, Norway'
};
</script>

<template>
  <div class="user-profile-container">
    <div class="account-box">
      <div class="account-content">
        <div class="profile-picture-wrapper">
          <UploadPicture />
        </div>
        <div class="user-details">
          <h3 class="user-name">{{ user.name }}</h3>
          <h2 class="username">{{ user.username }}</h2>
          <p class="user-email">{{ user.email }}</p>
          <p class="user-phone">{{ user.phone }}</p>
          <p class="user-location">{{ user.location }}</p>
        </div>
      </div>
      <div class="bottom-box"></div>
    </div>
  </div>
</template>

<style scoped>

</style>
