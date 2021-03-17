<template>
	<view>
		<view class="search-top">
			<view class="search-back iconfont" @click="goback">&#xe749;</view>
			<view class="search-father">
				<input @focus="readySearch" @input="inputMonitor2" class="search-text "  placeholder="搜你想搜的" readonly="readonly" v-model="value" />
				<view class="iconfont search-icon"  @click="goSearch(value,1)">&#xe8aa;</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:['searchValue'],
		data() {
			return {
				value:this.searchValue
			}
		},
		watch: {
		    searchValue(newValue, oldValue) {
				this.value=newValue
		        console.log("hello world"+newValue)
		    }
		}, 
		methods:{
			//监听输入，使用es检索
			inputMonitor2(e){
				// console.log("监听到的值为："+e.detail.value)
				// if(e.detail.value){
				// 	const result = await this.$myRequest({
				// 		url:"/elasticsearch/searchTitle/"+e.detail.value,
				// 	})
				// 	this.searchList=result.data
				// }
				this.$emit(
				'inputMonitor',e.detail.value
				)
			},
			goSearch(value){
				this.$emit(
				'goSearch',value,1
				)
			},
			readySearch(){
				this.$emit(
				'readySearch'
				)
			},
			// goSearch(){
			// 	uni.navigateTo({
			// 		url:'/pages/index/search/searchResult?value='+
			// 	})
			// }
			goback(){
				uni.navigateBack();
			},
		}
	}
</script>

<style lang="scss">
.search-top{
			display: flex;
			flex-direction: row;
			align-items: center;
			
			height: 80rpx;
			margin-top: 80rpx;
			.search-back{
				font-size: 60rpx;
			}
			.search-father {
				height: 50rpx;
				border-radius: 20rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;
				width: 95%;
				margin: 10rpx;
				height: 80rpx;
				margin-left: 15rpx;
			
				.search-text {
					width: 85%;
					border: 2px solid $uni-color-orange;
					text-align: center;
					height: 80rpx;
					border-radius: 40rpx;
					
				}
			
				.search-icon {
					line-height: 50rpx;
					text-align: center;
					font-size: 60rpx;
					margin-right: 20rpx;
				}
			}
		}
</style>
