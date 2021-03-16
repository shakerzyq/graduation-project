<template>
	<view>
		
		<searchComponents re @inputMonitor="inputMonitor" @goSearch="goSearch"></searchComponents>
		
	</view>
</template>

<script>
	import searchComponents from '../../../components/searchComponents.vue'
	export default{
		data:{
			return(){
				
			}
		},
		components:{
			searchComponents
		},
		methods:{
			inputMonitor2(e){
				this.$emit(
				'inputMonitor',e.detail.value
				)
			},
		}
	}
</script>

<style>
</style>
