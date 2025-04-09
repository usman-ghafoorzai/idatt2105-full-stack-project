<script setup>
import { ref, onMounted } from 'vue';
import { isLoggedIn } from '@/utils/authService';
import UploadPicture from './UploadPicture.vue';
import { getLoggedInUser, getUserImage, updateUser} from '@/api/userAPI';

// Reactive variables for user info and image
const user = ref({
  name: '',
  username: '',
  email: '',
});
const profileImage = ref(null);
const showEditModal = ref(false);
const editForm = ref({});
const isSubmitting = ref(false);
const errorMessage = ref('');
const successMessage = ref('');

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

// Open edit modal with current user data
const openEditModal = () => {
  editForm.value = { ...user.value };
  showEditModal.value = true;
};

// Close the edit modal
const closeEditModal = () => {
  showEditModal.value = false;
  errorMessage.value = '';
  successMessage.value = '';
};

// Save updated user information
const saveUserProfile = async () => {
  try {
    isSubmitting.value = true;
    errorMessage.value = '';

    // Only send fields that have been modified
    const fieldsToUpdate = {};
    if (editForm.value.firstName) fieldsToUpdate.firstName = editForm.value.firstName;
    if (editForm.value.lastName) fieldsToUpdate.lastName = editForm.value.lastName;
    if (editForm.value.username) fieldsToUpdate.username = editForm.value.username;
    if (editForm.value.email) fieldsToUpdate.email = editForm.value.email;

    // Use the updateUser function with the user ID
    const updatedUser = await updateUser(user.value.id, fieldsToUpdate);

    // Update the local user data with the response
    user.value = updatedUser;
    successMessage.value = 'Profile updated successfully!';

    // Close modal after successful update
    setTimeout(() => {
      closeEditModal();
      successMessage.value = '';
    }, 2000);
  } catch (error) {
    errorMessage.value = error.message || 'Failed to update profile';
  } finally {
    isSubmitting.value = false;
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
          <h3 class="user-name" v-if="isLoggedIn()">
            {{ `${user.firstName || ''} ${user.lastName || ''}`.trim() || 'User Name' }}
          </h3>
          <h2 class="username" v-if="isLoggedIn()">{{ user.username || 'Username' }}</h2>
          <p class="user-email" v-if="isLoggedIn()">{{ user.email || 'Email' }}</p>

          <!-- Display a message when the user is not logged in -->
          <div v-else>
            <p>Please log in to view your profile.</p>
          </div>

          <!-- Edit Profile Button -->
          <button v-if="isLoggedIn()" @click="openEditModal" class="edit-profile-btn">
            Edit Profile
          </button>
        </div>
      </div>
    </div>

    <!-- Edit Profile Modal -->
    <div v-if="showEditModal" class="modal-overlay">
      <div class="modal-content elegant-card">
        <h2>Edit Profile</h2>

        <form @submit.prevent="saveUserProfile">
          <div class="form-group">
            <label for="firstName">Edit First Name</label>
            <input
              type="text"
              id="firstName"
              v-model="editForm.firstName"
              :placeholder="user.firstName || 'First Name'"
            />
          </div>

          <div class="form-group">
            <label for="lastName">Edit Last Name</label>
            <input
              type="text"
              id="lastName"
              v-model="editForm.lastName"
              :placeholder="user.lastName || 'Last Name'"
            />
          </div>

          <div class="form-group">
            <label for="username">Edit Username</label>
            <input
              type="text"
              id="username"
              v-model="editForm.username"
              :placeholder="user.username || 'Username'"
            />
          </div>

          <div class="form-group">
            <label for="email">Edit Email</label>
            <input
              type="email"
              id="email"
              v-model="editForm.email"
              :placeholder="user.email || 'Email'"
            />
          </div>

          <div class="error-message" v-if="errorMessage">{{ errorMessage }}</div>
          <div class="success-message" v-if="successMessage">{{ successMessage }}</div>

          <div class="modal-actions">
            <button type="button" @click="closeEditModal" class="cancel-btn">Cancel</button>
            <button type="submit" class="save-btn" :disabled="isSubmitting">
              {{ isSubmitting ? 'Saving...' : 'Save Changes' }}
            </button>
          </div>
        </form>
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

.edit-profile-btn {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #4D4D4D;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: 500;
  font-size: 16px;
  transition: background-color 0.3s;
}

.edit-profile-btn:hover {
  background-color: #333333;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 90%;
  max-width: 500px;
  background-color: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.modal-content h2 {
  margin-top: 0;
  margin-bottom: 20px;
  color: #4D4D4D;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
  color: #4D4D4D;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

.modal-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.cancel-btn, .save-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.cancel-btn {
  background-color: #e0e0e0;
  color: #4D4D4D;
}

.save-btn {
  background-color: #4D4D4D;
  color: white;
}

.save-btn:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.error-message {
  color: #d32f2f;
  margin-top: 10px;
}

.success-message {
  color: #388e3c;
  margin-top: 10px;
}
</style>
