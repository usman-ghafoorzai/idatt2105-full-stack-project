<script setup>
import BookmarkedComponent from "@/components/BookmarkedComponent.vue";
import ReservedComponent from "@/components/ReservedComponent.vue";
import UserProfileComponent from "@/components/UserProfileComponent.vue";
</script>

<template>
  <div class="page-container">
    <div class="profile-layout">
      <!-- Left section with darker background -->
      <div class="profile-section">
        <UserProfileComponent />
      </div>

      <!-- Right section with lighter background -->
      <div class="items-section">
        <div class="items-content">
          <BookmarkedComponent class="content-section" />
          <ReservedComponent class="content-section" />
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

.profile-layout {
  display: flex;
  flex: 1;
}

.profile-section {
  width: 33.33%;
  background-color: var(--color-medium-bg);
  padding: 40px 20px;
}

.items-section {
  width: 66.67%;
  background-color: var(--color-light-bg);
  display: flex;
  justify-content: center;
  padding: 40px 20px;
}

.items-content {
  display: flex;
  flex-direction: column;
  gap: 30px;
  width: 90%;
}

.content-section {
  width: 100%;
}

@media (max-width: 992px) {
  .profile-layout {
    flex-direction: column;
  }

  .profile-section,
  .items-section {
    width: 100%;
  }
}
</style>
