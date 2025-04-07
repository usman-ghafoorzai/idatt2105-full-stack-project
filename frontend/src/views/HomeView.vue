<script setup>
import Header from '../components/Header.vue'
import UserProfileComponent from '../components/UserProfileComponent.vue'
import BookmarkedComponent from '../components/BookmarkedComponent.vue'
</script>

<template>
  <div class="home-content">
    <h1>Welcome to our marketplace</h1>

    <!-- Components displayed side by side according to design -->
    <div class="profile-section">
      <div class="profile-component">
        <UserProfileComponent />
      </div>
      <div class="bookmarked-component">
        <BookmarkedComponent />
      </div>
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

.profile-section {
  margin-top: 30px;
  width: 100%;
  display: flex;
  justify-content: center;
  gap: 111px; /* Space between components as per your design (770px - 59px - 600px) */
  flex-wrap: wrap;
}

.profile-component, .bookmarked-component {
  margin-bottom: 20px;
}

@media (max-width: 1400px) {
  .profile-section {
    gap: 50px;
  }
}

@media (max-width: 1280px) {
  .profile-section {
    flex-direction: column;
    align-items: center;
  }
}
</style>
