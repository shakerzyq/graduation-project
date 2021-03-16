import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
const store = new Vuex.Store({
    state: {
			greetings:'Hello World!',
			collectNum:null,
			attentionNum:null,
			history:null,
		},
    mutations: {
		
		//增加collect
		INCREMENT_COLLECT(state){
			state.collectNum++
		},
		//减少collect
		DECREMENT_COLLECT(state){
			state.collectNum--
		},
		//增加attention
		INCREMENT_ATTENTION(state){
			state.attentionNum++
		},
		//减少attention
		DECREMENT_ATTENTION(state){
			state.attentionNum--
		},
		//增加history
		INCREMENT_HISTORY(state){
			state.history++
		},
		//减少history
		DECREMENT_HISTORY(state){
			state.history--
		}
	},
    actions: {
		incrementCollect({commit}){
			commit('INCREMENT_COLLECT')
		},
		decrementCollect({commit}){
			commit('DECREMENT_COLLECT')
		},
		incrementAttention({commit}){
			commit('INCREMENT_ATTENTION')
		},
		decrementAttention({commit}){
			commit('DECREMENT_ATTENTION')
		},
		incrementHistory({commit}){
			commit('INCREMENT_HISTORY')
		},
		decrementHistory({commit}){
			commit('DECREMENT_HISTORY')
		},
		
		
	}
})
export default store