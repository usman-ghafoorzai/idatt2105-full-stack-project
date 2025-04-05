import './assets/main.css'

import { createApp } from 'vue'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
import { far } from '@fortawesome/free-regular-svg-icons'
import { fas } from '@fortawesome/free-solid-svg-icons'
import App from './App.vue'
import router from './router'

library.add(fas, far)

createApp(App)
  .component('fa', FontAwesomeIcon)
  .use(router)
  .mount('#app')
