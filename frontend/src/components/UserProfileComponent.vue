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
    <div class="account-box elegant-card">
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
    </div>
  </div>
</template>

<style scoped>
.user-profile-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  width: 100%;
  height: 100%;
}

.account-box {
  position: relative;
  width: 100%;
  max-width: 600px;
  height: 600px;
  background: var(--color-salmon);
}

.account-content {
  padding: 40px;
}

.profile-picture-wrapper {
  position: relative;
  width: 160px;
  height: 160px;
  margin-bottom: 32px;
}

.profile-picture-wrapper :deep(.upload-picture-container) {
  padding: 0;
  margin: 0;
  max-width: 160px;
}

.profile-picture-wrapper :deep(.upload-text) {
  display: none;
}

.profile-picture-wrapper :deep(.image-container) {
  width: 160px;
  height: 160px;
  margin-bottom: 0;
}

.user-details {
  margin-top: 30px;
}

.user-name {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 300;
  font-size: 24px;
  line-height: 29px;
  color: #4D4D4D;
  margin: 0 0 5px 0;
}

.username {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 600;
  font-size: 32px;
  line-height: 39px;
  color: #4D4D4D;
  margin: 0 0 35px 0;
}

.user-email, .user-phone, .user-location {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 24px;
  line-height: 29px;
  color: #4D4D4D;
  margin: 0 0 8px 0;
}


@media (max-width: 768px) {
  .account-box {
    height: auto;
    min-height: 500px;
  }

  .account-content {
    padding: 25px;
  }

  .bottom-box {
    height: 120px;
  }
}
</style>
