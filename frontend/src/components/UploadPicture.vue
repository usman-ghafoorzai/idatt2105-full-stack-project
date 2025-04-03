<script setup>
import { ref } from 'vue';
import genericAvatar from '../assets/generic-avatar.png';

const profilePicSrc = ref(genericAvatar);
const fileInput = ref(null);

function handleFileChange(event) {
  const file = event.target.files[0];
  if (file) {
    profilePicSrc.value = URL.createObjectURL(file);
  }
}

function triggerFileInput() {
  fileInput.value.click();
}
</script>

<template>
  <div class="upload-picture-container">
    <div class="image-container" @click="triggerFileInput">
      <img :src="profilePicSrc" alt="Profile picture" id="profile-pic">
      <div class="hover-overlay">
        <span>Change picture</span>
      </div>
    </div>
    <p class="upload-text">Click on the profile picture to upload a new image</p>
    <input
      ref="fileInput"
      type="file"
      accept="image/jpeg, image/png, image/jpg"
      id="input-file"
      @change="handleFileChange">
  </div>
</template>

<style scoped>
.upload-picture-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 450px;
  padding: 20px;
  margin: -60px 0 0 0;
}

.image-container {
  width: 180px;
  height: 180px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 30px;
  border: 1px solid #A6A6A6;
  background: #FFFFFF;
  position: relative;
  cursor: pointer;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.hover-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.hover-overlay span {
  color: white;
  font-family: 'Roboto', sans-serif;
  font-size: 16px;
  font-weight: 500;
}

.image-container:hover .hover-overlay {
  opacity: 1;
}

.upload-text {
  color: #4D4D4D;
  font-family: 'Roboto', sans-serif;
  font-size: 14px;
  margin-top: 0;
  margin-bottom: 20px;
  text-align: center;
}

input {
  display: none;
}

@media (max-width: 600px) {
  .upload-picture-container {
    padding: 15px;
  }

  .image-container {
    width: 150px;
    height: 150px;
  }
}
</style>
