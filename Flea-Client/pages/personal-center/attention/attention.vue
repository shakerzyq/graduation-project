<template>
	<view>
		<view class="attentions">
			<view v-if="users.length>0?true:false" class="attention" v-for="(user,index) in users" :key="user.esuser.userId">
				<view class="user-info">
					<image :src="user.esuser.userIcon" @click="goToPersonalCenter(user.esuser.userId)"></image>
					<view class="name-credit" @click="goToPersonalCenter(user.esuser.userId)">
						<view class="user-name">{{user.esuser.userName}}</view>
						<view class="user-credit">信誉值：{{user.esuser.userCredit}}</view>
					</view>
				</view>
				<view v-if="flea_id===user.esuser.userId?false:true" class="attention-action" @click="fansAction(flea_id,user.esuser.userId,index)">
					<view class="attentioning" v-if="user.fansJudge===true?false:true">
						<view class="iconfont icon">&#xe8a1;</view>
						<text>关注</text>
					</view>
					<view v-else class="attentioned" >
						已关注
					</view>
				</view>
				
				<!-- <image :src="user.esuser.userIcon" @click="goToPersonalCenter(user.esuser.userId)"></image>
				<view class="name-credit" @click="goToPersonalCenter(user.esuser.userId)" >
					<view class="user-name">{{user.esuser.userName}}</view>
					<view class="user-credit">信誉值：{{user.esuser.userCredit}}</view>
				</view>
				<view v-if="flea_id===user.esuser.userId?false:true" class="attention-action" @click="fansAction(flea_id,user.esuser.userId,index)">
					<view class="attention" v-if="user.fansJudge?false:true">
						<view class="iconfont icon">&#xe8a1;</view>
						<text>关注</text>
					</view>
					<view v-else class="attentioned" >
						已关注
					</view>
				</view> -->
			</view>
			
		</view>
		<view class="hint" v-if="users.length===0">这个用户太高冷了，谁也不理</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flea_id:null,
				// user_id:null,
				users:[],
			}
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			// this.user_id=options.user_id
			console.log("userId为：eee"+options.user_id+"flea_id为："+this.flea_id)
			this.getAttentions(options.user_id,this.flea_id)	
		},
		methods: {
			goToPersonalCenter(userId){
				uni.navigateTo({
					url:'/pages/personal-center/personal-center?flea_id='+this.flea_id+'&userId='+userId
				})
			},
			/* 关注与取关 */
			async fansAction(flea_id, userId,index) {
				const type = (this.users[index].fansJudge) ? 'unfans' : 'fans'
				
				if(this.users[index].fansJudge===0){
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('incrementAttention')
					}
				}else{
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('decrementAttention')
					}
				}
				
				const result = await this.$myRequest({
					url: '/product/fansAction/' + flea_id + '/' + userId + '/' + type,
				})
				this.users[index].fansJudge = result.data
			},
			
			/* 获取用户关注信息 */
			async getAttentions(user_id,flea_id){
				console.log("userId为：eee"+user_id+"flea_id为："+flea_id)
				const result = await this.$myRequest({
					url:'/personal/attentionInfo/'+user_id+'/'+flea_id
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
		justify-content: space-between;
		border-bottom: 1px solid $uni-color-gray;
		padding-bottom: 20rpx;
		
		.user-info{
			display: flex;
			flex-direction: row;
			align-items: center;
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
		
		.attention-action{
			display: flex;
			flex-direction: row;
			
			.attentioning{
				display: flex;
				flex-direction: row;
				width: 100rpx;
				height: 50rpx;
				text-align: center;
				line-height: 50rpx;
				padding: 0 10rpx;
				border-radius: 30rpx;
				background-color: yellow;
			}
			
			.attentioned{
				display: flex;
				flex-direction: row;
				width: 100rpx;
				height: 50rpx;
				text-align: center;
				line-height: 50rpx;
				padding: 0 10rpx;
				border-radius: 30rpx;
			}
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
