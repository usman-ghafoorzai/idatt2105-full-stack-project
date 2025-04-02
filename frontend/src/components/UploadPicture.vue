<script setup>
let profilePic = document.getElementById("profile-pic");
let inputFile = document.getElementById("input-file");

inputFile.onchange = function() {
  profilePic.src = URL.createObjectURL(inputFile.files[0]);

}
</script>

<template>
  <div class="profile-picture-container">
    <div class="generic-avatar">
      <img src="https://static.vecteezy.com/system/resources/previews/009/266/930/non_2x/user-icon-design-free-png.png"
           alt="Generic avatar" class="avatar-image" id="profile-pic" />
      <label for ="input-file">Upload image</label>
      <input type="file" accept="image/jpeg, image/png, image/jpg" id="input-file">
    </div>
  </div>
</template>

<style scoped>
.profile-picture-container {
  position: absolute;
  width: 261px;
  height: 256px;
  left: 589px;
  top: 208px;
}

.generic-avatar {
  position: absolute;
  width: 200px;
  height: 200px;
  left: 620px;
  top: 208px;
  background: #EADDFF;
  border-radius: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
}

.avatar-image {
  max-width: 75%;
  max-height: 75%;
  object-fit: contain;
}

label {
  display: block;
  width: 200px;
  background: cornflowerblue;
  color: #fff;
  padding: 12px;
  margin: 10px auto;
  border-radius: 5px;
  cursor: pointer;
}

input {
  display: none;}

</style>
