import Vue from 'vue'
import App from './App'
import {myRequest,uuid,sendEmail} from './util/api.js'
import store from './store/store.js'

Vue.prototype.$store = store
Vue.prototype.$myRequest = myRequest
Vue.prototype.$uuid=uuid
Vue.prototype.$sendEmail=sendEmail
Vue.filter('formatDate',(date)=>{

	const nDate = new Date(date)
	return nDate.getFullYear()+"-"+nDate.getMonth().toString().padStart(2,0)+"-"+nDate.getDay().toString().padStart(2,0)
	nDate.getFullYear()
})

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App,
	store
})
app.$mount()
