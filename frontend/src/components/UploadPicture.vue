<script setup>
import { ref } from 'vue';
import genericAvatar from '../assets/generic-avatar.png';

const profilePicSrc = ref(genericAvatar);

function handleFileChange(event) {
  const file = event.target.files[0];
  if (file) {
    profilePicSrc.value = URL.createObjectURL(file);
  }
}
</script>

<template>
  <div class="upload-picture-container">
    <div class="image-container">
      <img :src="profilePicSrc" alt="Profile picture" id="profile-pic">
    </div>
    <label for="input-file" class="upload-button">Upload image</label>
    <input
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
  width: 300px;
  margin-bottom: 20px;
}

.image-container {
  width: 180px;
  height: 180px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 20px;
  border: 1px solid #A6A6A6;
  background: #FFFFFF;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.upload-button {
  width: 300px;
  height: 40px;
  background: #4D4D4D;
  color: white;
  border: none;
  border-radius: 4px;
  font-family: 'Roboto', sans-serif;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
}

.upload-button:hover {
  background: #3D3D3D;
}

input {
  display: none;
}
</style>
