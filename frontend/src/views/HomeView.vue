<script setup>
import Header from '../components/Header.vue'
import UserProfileComponent from '../components/UserProfileComponent.vue'
</script>

<template>
  <div class="home-content">
    <h1>Welcome to our marketplace</h1>

    <!-- Temporary display of UserProfileComponent for development -->
    <div class="profile-preview">
      <UserProfileComponent />
    </div>
  </div>
</template>

<style scoped>
.home-content {
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.profile-preview {
  margin-top: 30px;
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>
