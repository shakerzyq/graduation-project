<template>
	<view>
		<view class="attentions">
			<view v-if="users.length>0?true:false" class="attention" v-for="(user,index) in users" :key="user.userId">
				<image :src="user.userIcon" @click="goToPersonalCenter(user.userId)"></image>
				<view class="name-credit" @click="goToPersonalCenter(user.userId)">
					<view class="user-name">{{user.userName}}</view>
					<view class="user-credit">信誉值：{{user.userCredit}}</view>
				</view>
				<button class="un-attention" @click="unAttention(user.userId,index)">已关注</button>
			</view>
			
		</view>
		<view class="hint" v-if="users.length===0">你太高冷了,快去关注一些朋友吧</view>
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
					url:'/mine/attentionInfo/'+this.flea_id
				}) 
				this.users=result.data
			},
			/* 取关 */
			 unAttention(merchantId,index){
				uni.showModal({
					// title: '提示',
					content: '是否取关？',
					cancelText: "取消",
					confirmText: "确定",
					success: (res) => {
						if (res.confirm) {
							const result = this.$myRequest({
								url:'/product/fansAction/'+this.flea_id+'/'+merchantId+'/unfans'
							})
							console.log("index为："+index)
							console.log("users[index]为："+this.users[index])
							this.users.splice(index,1);
							this.$store.dispatch('decrementAttention')
							console.log("现在store中的attrntionNum为："+this.$store.state.attentionNum)
						} else if (res.cancel) {
						}
					}
				});
				
				
			}
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
		.un-attention{
			width: 160rpx;
			height: 50rpx;
			background-color: $uni-color-gray;
			font-size: 30rpx;
			line-height: 50rpx;
			margin-right: 0rpx;
			border-radius: 30rpx;
			color: #fff;
		}
		
	}
	
}
.hint{
		text-align: center;
		font-size: 50rpx;
		color: $uni-color-gray;
		margin-top: 375rpx;
	}

</style>
