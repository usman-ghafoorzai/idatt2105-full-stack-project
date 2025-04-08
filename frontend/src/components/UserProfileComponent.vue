<script setup>
import { ref, onMounted } from 'vue';
import { isLoggedIn } from '@/utils/authService';
// Remove the unused apiClient import
import UploadPicture from './UploadPicture.vue';
import { getLoggedInUser, getUserImage } from '@/api/userAPI';

// Reactive variables for user info and image
const user = ref({
  name: '',
  username: '',
  email: '',
  // Removed phone and location properties
});
const profileImage = ref(null); // To hold the user image


// Function to fetch user data
const fetchUserData = async () => {
  try {
    // Fetch user data
    user.value = await getLoggedInUser();

    // Fetch the user image
    const imageDataUrl = await getUserImage(user.value.id);
    profileImage.value = imageDataUrl;
  } catch (error) {
    console.error('Error fetching user data or image:', error);
  }
};

// Check if user is logged in
onMounted(() => {
  if (isLoggedIn()) {
    fetchUserData();
  }
});
</script>

<template>
  <div class="user-profile-container">
    <div class="account-box elegant-card">
      <div class="account-content">
        <div class="profile-picture-wrapper">
          <img v-if="profileImage" :src="profileImage" alt="Profile Picture" class="profile-picture" />
          <UploadPicture v-else />
        </div>
        <div class="user-details">
          <!-- Display the user data only if logged in -->
          <h3 class="user-name" v-if="isLoggedIn()">{{ user.name || 'User Name' }}</h3>
          <h2 class="username" v-if="isLoggedIn()">{{ user.username || 'Username' }}</h2>
          <p class="user-email" v-if="isLoggedIn()">{{ user.email || 'Email' }}</p>
          <!-- Removed phone and location paragraphs -->

          <!-- Display a message when the user is not logged in -->
          <div v-else>
            <p>Please log in to view your profile.</p>
          </div>
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

.profile-picture {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
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

.user-email {
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 24px;
  line-height: 29px;
  color: #4D4D4D;
  margin: 0 0 8px 0;
}

/* Removed user-phone and user-location styles from the combined selector */

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
