<template>
	<view>
		<!-- 评星 -->
		<view class="stars">
			<view v-for="(star,index) in stars" :key="index">
				<view v-if="star.star" class="iconfont star" @click="clickStar(index,stars)" style="color:#f88000">&#xe884;</view>
				<view v-else class="iconfont star" @click="clickStar(index,stars)" >&#xe882;</view>
			</view>
		</view>
		
	</view>
</template>

<script>

	export default {
		props:['stars'],
		data() {
			return {
				
			};
		},
		
		methods:{
			/* 点击星星评分 */
			clickStar(index,stars){
				this.$emit('clickStar',index,stars)
			}
		}
		
	}
</script>

<style lang="scss">
	
	.stars{
		display: flex;
		flex-direction: row;
		width: 200rpx;
		.star{
			font-size: 50rpx;
		}
	}
</style>
