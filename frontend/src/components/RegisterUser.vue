<script setup>
import { ref, computed } from 'vue';
import { useAsyncValidator } from '@vueuse/integrations/useAsyncValidator';

const firstName = ref('');
const lastName = ref('');
const location = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref(''); // Fixed variable name
const attempted = ref(false);

// Form object for validation
const form = computed(() => ({
  firstName: firstName.value,
  lastName: lastName.value,
  location: location.value,
  email: email.value,
  password: password.value,
  confirmPassword: confirmPassword.value
}));

// Validation rules
const rules = {
  firstName: [
    { required: true, message: 'First name is required', trigger: 'blur' },
    { pattern: /^[A-Za-z\- ]+$/, message: 'First name can only contain letters and hyphens', trigger: 'blur' }
  ],
  lastName: [
    { required: true, message: 'Last name is required', trigger: 'blur' },
    { pattern: /^[A-Za-z\- ]+$/, message: 'Last name can only contain letters and hyphens', trigger: 'blur' }
  ],
  location: [
    { required: true, message: 'Location is required', trigger: 'blur' }
  ],
  email: [
    { required: true, message: 'Email is required', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: 'Invalid email address', trigger: 'blur' }
  ],
  password: [
    { required: true, message: 'Password is required', trigger: 'blur' },
    { min: 6, message: 'Password must be at least 6 characters', trigger: 'blur' }
  ],
  confirmPassword: [
    {
      validator: (rule, value, callback) => {
        if (value !== password.value) {
          callback(new Error('Passwords do not match'));
        } else {
          callback();
        }
      },
      trigger: 'blur'
    }
  ]
};

// Set up validator
const { errorFields, execute } = useAsyncValidator(form, rules, {
  immediate: false,
  manual: true
});

const register = async () => {
  attempted.value = true;
  const result = await execute();

  if (!result.pass) return;

  // TODO: implement API logic
  console.log({
    firstName: firstName.value,
    lastName: lastName.value,
    location: location.value,
    email: email.value,
    password: password.value
  });
}
</script>

<template>
  <div class="create-account">
    <div class="form-header">
      <h1>Create account</h1>
    </div>

    <div class="form-field first-name">
      <label for="firstName">FIRST NAME</label>
      <div class="textbox">
        <input id="firstName" type="text" v-model="firstName" placeholder="Enter first name">
      </div>
      <span class="error-message" v-if="attempted && errorFields?.firstName?.[0]?.message">
        {{ errorFields.firstName[0].message }}
      </span>
    </div>

    <div class="form-field last-name">
      <label for="lastName">LAST NAME</label>
      <div class="textbox">
        <input id="lastName" type="text" v-model="lastName" placeholder="Enter last name">
      </div>
      <span class="error-message" v-if="attempted && errorFields?.lastName?.[0]?.message">
        {{ errorFields.lastName[0].message }}
      </span>
    </div>

    <div class="form-field location">
      <label for="location">LOCATION</label>
      <div class="textbox">
        <input id="location" type="text" v-model="location" placeholder="Enter location">
      </div>
      <span class="error-message" v-if="attempted && errorFields?.location?.[0]?.message">
        {{ errorFields.location[0].message }}
      </span>
    </div>

    <div class="form-field email">
      <label for="email">EMAIL</label>
      <div class="textbox">
        <input id="email" type="email" v-model="email" placeholder="Enter email">
      </div>
      <span class="error-message" v-if="attempted && errorFields?.email?.[0]?.message">
        {{ errorFields.email[0].message }}
      </span>
    </div>

    <div class="form-field password">
      <label for="password">PASSWORD</label>
      <div class="textbox">
        <input id="password" type="password" v-model="password" placeholder="Enter password">
      </div>
      <span class="error-message" v-if="attempted && errorFields?.password?.[0]?.message">
        {{ errorFields.password[0].message }}
      </span>
    </div>

    <div class="form-field confirm-password">
      <label for="confirmPassword">CONFIRM PASSWORD</label>
      <div class="textbox">
        <input id="confirmPassword" type="password" v-model="confirmPassword" placeholder="Confirm password">
      </div>
      <span class="error-message" v-if="attempted && errorFields?.confirmPassword?.[0]?.message">
        {{ errorFields.confirmPassword[0].message }}
      </span>
    </div>

    <button class="register-button" @click="register">Register</button>
  </div>
</template>

<style scoped>
.create-account {
  width: 400px;
  height: auto;
  display: grid;
  grid-template-areas:
    "header"
    "firstname"
    "lastname"
    "location"
    "email"
    "password"
    "confirmpassword"
    "button";
  gap: 15px;
}

.form-header {
  grid-area: header;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 10px;
  gap: 10px;
  width: 300px;
  height: 60px;
}

.form-header h1 {
  width: 302px;
  height: 48px;
  font-family: 'Inter', sans-serif;
  font-style: normal;
  font-weight: 700;
  font-size: 40px;
  line-height: 48px;
  color: #4D4D4D;
}

.form-field {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 0;
  gap: 4px;
  width: 300px;
  height: 60px;
}

.first-name {
  grid-area: firstname;
}

.last-name {
  grid-area: lastname;
}

.location {
  grid-area: location;
}

.email {
  grid-area: email;
}

.password {
  grid-area: password;
}

.confirm-password {
  grid-area: confirmpassword;
}

label {
  height: 11px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 9px;
  line-height: 11px;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: #000000;
}

.textbox {
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 8px;
  gap: 10px;
  width: 300px;
  height: 30px;
  background: #FFFFFF;
  border: 1px solid #A6A6A6;
  border-radius: 4px;
  margin-bottom: 10px;
}

input {
  width: 284px;
  height: 14px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 14px;
  color: #A6A6A6;
  border: none;
  outline: none;
  flex-grow: 1;
}

.register-button {
  grid-area: button;
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
  margin-top: 5px;
}

.register-button:hover {
  background: #3D3D3D;
}

.error-message {
  color: red;
  font-size: 12px;
  text-align: center;
  margin-top: -10px;
}
</style>
