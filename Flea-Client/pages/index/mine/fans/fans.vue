<template>
	<view>
		<view class="attentions">
			<view class="attention" v-for="(user,index) in users" :key="user.userId">
				<image :src="user.userIcon" @click="goToPersonalCenter(user.userId)"></image>
				<view class="name-credit">
					<view class="user-name">{{user.userName}}</view>
					<!-- <view class="user-credit">信誉值：{{user.userCredit}}</view> -->
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flea_id:null,
				users:[],
			}
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			console.log("store为a："+this.$store.state.greetings)
			this.getAttentions()
		},
		methods: {
			/**
			 * 跳转到商品详情页面
			 * @param {Object} userId
			 */
			goToPersonalCenter(userId){
				uni.navigateTo({
					url:'/pages/personal-center/personal-center?flea_id='+this.flea_id+'&userId='+userId
				})
			},
			async getAttentions(){
				const result = await this.$myRequest({
					url:'/mine/fansInfo/'+this.flea_id
				}) 
				this.users=result.data
			},
			
		}
	}
</script>

<style lang="scss">
.attentions{
	display: flex;
	flex-direction: column;
	width: 93%;
	margin: 0 auto;
	margin-top: 50rpx;
	.attention{
		display: flex;
		flex-direction: row;
		align-items: center;
		border-bottom: 1px solid $uni-color-gray;
		padding-bottom: 20rpx;
		
		image{
			width: 100rpx;
			height:100rpx;
			border-radius: 50%;
			background-color: red;
			margin-right: 40rpx;
		}
		.name-credit{
			display: flex;
			flex-direction: column;
			
			.user-name{
				
			}
			.user-credit{
				
			}
		}
	}
}

</style>
