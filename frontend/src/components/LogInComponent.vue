<script setup>
import { ref, computed } from 'vue'
import { useAsyncValidator } from '@vueuse/integrations/useAsyncValidator'
import { login as loginAPI } from '@/api/authAPI.js'

const username = ref('')
const password = ref('')
const attempted = ref(false) // Track if validation has been attempted
const loginError = ref("")

// Values that should be validated
const form = computed(() => ({
  username: username.value,
  password: password.value
}))

// Rules for validation
const rules = {
  username: [
    { required: true, message: 'Username is required', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Password is required', trigger: 'blur' }
  ]
}

// Configure useAsyncValidator to be manual, so it does not immediate:true as soon as mount
const { errorFields, execute } = useAsyncValidator(form, rules, {
  immediate: false,
  manual: true
})

const login = async () => {
  attempted.value = true // Set flag to show validation message
  loginError.value = "" // Reset error message
  const result = await execute()
  if (!result.pass) return

  try {
    const res = await loginAPI(username.value, password.value);
    console.log("Login successful!", res);

  } catch (error) {
    console.error("Login failed:", error.message);
    // Redirect
    loginError.value = error.message;
  }
}
</script>

<template>
  <div class="login-container">
    <h2 class="login-header">Login to have more access to other features!</h2>

    <p class="error-message" v-if="loginError">{{ loginError }}</p>

    <form class="login-form" @submit.prevent="login">
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username" />
        <span class="error-message" v-if="attempted && errorFields?.username?.[0]?.message">
          {{ errorFields.username[0].message }}
        </span>
      </div>

      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password" />
        <span class="error-message" v-if="attempted && errorFields?.password?.[0]?.message">
          {{ errorFields.password[0].message }}
        </span>
      </div>

      <button type="submit" class="login-button">Login!</button>
    </form>
  </div>
</template>
<style scoped>
.login-container {
  width: 100%;
  max-width: 450px;
  margin: 0;
  padding: 20px;
}

.login-header {
  font-family: 'ABeeZee', sans-serif;
  font-weight: 400;
  font-size: 24px;
  line-height: 1.2;
  color: #000000;
  text-align: center;
  margin-bottom: 30px;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-family: 'ABeeZee', sans-serif;
  font-size: 18px;
  font-weight: 400;
  color: #000000;
}

.form-group input {
  width: 100%;
  padding: 10px 0;
  font-family: 'ABeeZee', sans-serif;
  font-size: 16px;
  background: transparent;
  border: none;
  border-bottom: 1px solid #000000;
  outline: none;
  transition: border-color 0.3s;
}

.form-group input:focus {
  border-color: #555;
}

.login-button {
  align-self: center;
  margin-top: 15px;
  width: 100%;
  max-width: 300px;
  height: 40px;
  background: #4D4D4D;
  color: white;
  border: none;
  border-radius: 4px;
  font-family: 'Roboto', sans-serif;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-button:hover {
  background: #3D3D3D;
}

.error-message {
  color: red;
  font-size: 14px;
}

@media (max-width: 600px) {
  .login-container {
    padding: 15px;
  }

  .login-header {
    font-size: 20px;
  }

  .form-group label {
    font-size: 16px;
  }

  .login-button {
    font-size: 18px;
  }
}

.error-message {
  color: red;
  font-size: 14px;
  margin-bottom: 10px;
}

</style>
