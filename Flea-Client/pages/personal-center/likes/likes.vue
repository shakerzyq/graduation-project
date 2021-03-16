<template>
	<view>
		<view class="likes">
			<view class="like" v-for="(like,index) in likes" :key="index">
					<view class="user-hint">
						<view class="user" @click="goToPersonalCenter(like.userId)" >
							<image class="user-icon" :src="like.userIcon"></image>
							<view class="user-name">like.userName</view>
						</view>
						<view class="hint">
							<text >{{flea_id===user_id?'给你的宝贝一个赞':'给他/她的宝贝一个赞'}}</text>
							<view class="line"></view>
							<view class="date">{{like.likeDate}}</view>
						</view>
					</view>
					<image class="goods" :src="like.goodsIcon" @click="goGoodsDetail(like.userId,like.goodsId)"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flea_id:null,
				user_id:null,
				likes:[],
			}
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			this.user_id=options.user_id
			this.getLikesInfo(options.user_id)
		},
		methods: {
			
			async getLikesInfo(user_id){
				const result = await this.$myRequest({
					url:'/personal/likesInfo/'+user_id
				})
				this.likes = result.data
			},
			
			goToPersonalCenter(userId){
				uni.navigateTo({
					url:'/pages/personal-center/personal-center?flea_id='+this.flea_id+'&userId='+userId
				})
			},
			
			goGoodsDetail(userId,goodsId){
				console.log("跳转需要的id"+goodsId,userId)
				uni.navigateTo({
					url:'/pages/index/goods-detail?goodsId='+goodsId+'&'+'userId='+userId
				})
			},
		}
	}
</script>

<style lang="scss">
	.likes{
		width: 93%;
		margin: 0 auto;
		display: flex;
		flex-direction: column;
		.like{
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			margin-top:20rpx;
			border-bottom: 1px solid $uni-color-lightgray;
			padding-bottom:10rpx ;
			
			.user-hint{
				display: flex;
				flex-direction: column;
				.user{
					display: flex;
					flex-direction: row;
					align-items: center;
					.user-icon{
						width: 60rpx;
						height: 60rpx;
						border-radius: 50%;
						background-color: red;
						margin-right: 20rpx;
					}
					.user-name{
						
					}
				}
				.hint{
					display: flex;
					flex-direction: row;
					align-items: center;
					font-size: 30rpx;
					text{
						color: $uni-color-gray;
					}
					.line{
						margin: 0 10rpx;
						width: 1px;
						height: 30rpx;
						margin-top: 5rpx;
						margin-left: 13rpx;
						background-color: $uni-color-gray;
						
					}
					.date{
						color: $uni-color-gray;
					}
				}
				
			}
			.goods{
				width: 100rpx;
				height: 100rpx;
				border-radius: 10rpx;
				background-color: red;
			}
		}
	}
</style>
