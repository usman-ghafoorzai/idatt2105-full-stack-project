<script setup>
import { ref } from 'vue'
import Header from '../components/Header.vue'
import RegisterUser from '../components/RegisterUser.vue'
import LogInComponent from '../components/LogInComponent.vue'
import UploadPicture from '../components/UploadPicture.vue'

const selecedImage = ref(null) // Shared state for the image
</script>

<template>
  <div class="page-container">
    <div class="registration-layout">
      <!-- Left section with darker background -->
      <div class="login-section">
        <LogInComponent />
      </div>

      <!-- Right section with lighter background -->
      <div class="registration-section">
        <div class="registration-content">
          <div class="upload-section">
            <UploadPicture imageFile="selecedImage" />
          </div>
          <div class="register-section">
            <RegisterUser :profileImage="selecedImage" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.registration-layout {
  display: flex;
  flex: 1;
  position: relative;
}

.login-section {
  width: 33.33%;
  background-color: var(--color-light-bg);
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 40px 0 0 20px;
}


.registration-section {
  width: 66.67%;
  background-color: var(--color-white);
  display: flex;
  justify-content: center;
  align-items: center;
  padding-bottom: 150px;
}

.registration-content {
  display: grid;
  grid-template-areas: 'upload register';
  grid-template-columns: 1fr 1fr;
  width: 90%;
  gap: 20px;
  padding: 20px;
}

.upload-section {
  grid-area: upload;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-section {
  grid-area: register;
  display: flex;
  justify-content: center;
  align-items: center;
}


@media (max-width: 1200px) {
  .registration-content {
    grid-template-areas:
      'upload'
      'register';
    grid-template-columns: 1fr;
  }
}

@media (max-width: 992px) {
  .registration-layout {
    flex-direction: column;
  }

  .login-section {
    width: 100%;
    min-height: 300px;
  }

  .registration-section {
    width: 100%;
  }
}
</style>
