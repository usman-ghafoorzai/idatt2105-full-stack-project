<script setup>
import Searchbar from './Searchbar.vue';
import { ref, computed, onMounted, onUnmounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { isLoggedIn, logout, getToken } from '../utils/authService.js';
import logoImage from '../assets/logo.jpeg';

const router = useRouter();
// Define props for mail notifications
defineProps({
  mail: {
    type: Number,
    default: 0,
  }
});

const loggedIn = ref(false);
const logoSrc = ref(logoImage);

// Check login status when component mounts
onMounted(() => {
  updateLoginStatus();
  // Add event listener for auth changes
  window.addEventListener('auth-change', updateLoginStatus);
});

onUnmounted(() => {
  // Clean up event listener when component is unmounted
  window.removeEventListener('auth-change', updateLoginStatus);
});

// Watch for token changes to update login status
// Using an anonymous function to avoid the unused parameter warning
watch(() => getToken(), () => {
  updateLoginStatus();
});

const updateLoginStatus = () => {
  loggedIn.value = isLoggedIn();
};

// Computed property for the button text
const buttonText = computed(() =>
  loggedIn.value ? "Logout" : "Log in/Sign up"
);

const handleAuthAction = () => {
  if (loggedIn.value) {
    // Logout functionality
    logout();
    updateLoginStatus();
    router.push('/');
  } else {
    // Navigate to login/register page
    router.push('/register');
  }
};

const navigateToHome = () => {
  router.push('/');
};

const navigateToSell = () => {
  if (isLoggedIn()) {
    router.push('/sell');
  } else {
    alert('Please log in to sell an item.');
    router.push('/register');
  }
};

const navigateToUserProfile = () => {
  if (isLoggedIn()) {
    router.push('/user-profile');
  } else {
    router.push('/register');
  }
};
</script>

<template>
  <div id="header">
    <div id="logo" @click="navigateToHome">
      <img :src="logoSrc" alt="Logo" id="logo-image" />
    </div>
    <div class="container">
      <div id="home" @click="navigateToHome">HOME</div>
      <fa icon="home" class="icons"></fa>
    </div>
    <div class="container">
      <div id="categories">CATEGORIES</div>
      <fa icon="icons" class="icons"></fa>
    </div>
    <div class="container">
      <div id="sell" @click="navigateToSell">SELL</div>
      <fa icon="arrow-up-from-bracket" class="icons"></fa>
    </div>
    <div id="container">
      <div id="profile" @click="navigateToUserProfile">Profile</div>
      <fa icon="user" id="profile-icon" @click="navigateToUserProfile"></fa>
      <div id="username" @click="handleAuthAction">{{ buttonText }}</div>
      <fa icon="right-to-bracket" id="login-icon" @click="handleAuthAction"></fa>
      <Searchbar id="searchbar"/>
    </div>
  </div>
</template>

<style scoped>
#header {
  background-color: var(--color-coral);
  height: 133px;
  width: 100%;
  display: grid;
  grid-template-areas: 'logo home categories sell search';
  grid-template-columns: 2fr 1fr 1fr 1fr 3fr;
  align-items: center;
}

#logo {
  grid-area: logo;
  padding-left: 40px;
  display: flex;
  align-items: center;
}

#logo-image {
  height: 90px;
  width: auto;
  object-fit: contain;
  cursor: pointer;

  mix-blend-mode: multiply;
  border-radius: 50%;
  filter: drop-shadow(0 0 2px rgba(0,0,0,0.1));
}

.container {
  display: flex;
  padding-top:45px;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
#container {
  grid-area: search;
  grid-template-areas: 'profile profile-icon user user-icon'
                             'searchbar searchbar searchbar searchbar';
  display: grid;
  grid-template-rows: auto auto;
  grid-template-columns: 2fr auto 4fr auto;
  align-items: center;
  margin-right: 40px;
  row-gap: 10px;

}
#home, #categories, #sell {
  font-size: 20px;
}
#profile {
  grid-area: profile;
  font-size: 20px;
  justify-self: right;
}
#profile-icon {
  grid-area: profile-icon;
  font-size: 25px;
  justify-self: left;
  padding-left: 3px;
}
#username {
  grid-area: user;
  font-size: 20px;
  justify-self: right;
}
#login-icon {
  grid-area: user-icon;
  font-size: 25px;
  padding-right: 10px;
  justify-self: right;
}

#searchbar {
  grid-area: searchbar;
  align-self: center;
}

.icons {
  font-size: 25px;
}
#home:hover, #categories:hover, #sell:hover, #profile:hover, #username:hover {
  cursor: pointer;
  border-bottom: 2px solid #000;
}

@media (max-width:900px) {
  #home, #categories, #sell {
    font-size: 15px;
  }
  #profile, #username {
    font-size: 15px;
  }
}

@media (max-width: 768px) {
  #home, #categories, #sell, #profile, #username {
    display: none;
  }

  .icons {
    font-size: 20px;
  }

  #logo {
    font-size: 20px;
    padding-left: 20px;
  }
}
</style>
