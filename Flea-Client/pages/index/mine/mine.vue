<template>
	<view class="mine">
		<!-- top
		 userIcon
		 userId
		 username
		 -->
		 <view class="top">
			 <image @click="goToPersonalCenter(flea_id)" class="user-icon" :src="info.userIcon"></image>
			 <view @click="goToPersonalCenter(flea_id)" class="user-name-id">
				 <strong class="user-name">{{info.userName}}</strong>
				 <view class="user-id">跳蚤id：{{flea_id}}</view>
			 </view>
			 <view class="user-credit">信誉值：{{info.userCredit}}</view>
		 </view>
		 
		 <!-- 粉丝收藏浏览信息-->
		 <view class="fans-collect-history-attention">
			 <view class="collect" @click="goToNext('collect')">
				 <strong class="number" >{{$store.state.collectNum}}</strong>
				 <text>收藏</text>
			 </view>
			 <view class="attention" @click="goToNext('attention')">
				 <!-- <strong class="number">{{info.attentionNum}}</strong> -->
				 <strong class="number">{{$store.state.attentionNum}}</strong>
				 <text>关注</text>
			 </view>
			 <view class="fans" @click="goToNext('fans')">
				 <strong class="number">{{info.fansNum}}</strong>
				 <text>粉丝</text>
			 </view>
			 <view class="history" @click="goToNext('history')">
				 <strong  class="number">8</strong>
				 <text>足迹</text>
			 </view>
		 </view>
		 
		 <!-- 我的交易 
			我的发布，，我的订单，我卖出的，我买到的，
		 -->
		 <view class="my-trading">
			<strong>我的交易</strong>
			<view class="issue-order-sold-bought">
				<view class="issue" @click="goToNext('mine-publish')">
					<view class="iconfont icon">&#xe7f6;</view>
					<text>我的发布0</text>
				</view>
				<view class="order" @click="goToNext('mine-sold')">
					<view class="iconfont icon">&#xe801;</view>
					<text>我卖出的0</text>
				</view>
				<view class="sold" @click="goToNext('mine-orders')">
					<view class="iconfont icon">&#xe8f9;</view>
					<text>我的订单0</text>
				</view>
				<view class="bought" @click="goToNext('mine-bought')">
					<view class="iconfont icon">&#xe902;</view>
					<text>我买到的0</text>
				</view>
						 
			</view> 
		 </view>
		 
		 <!-- 事务处理 
			 纠纷处理
		 -->
		 <view class="my-affair">
			 <strong>我的事务</strong>
			 <view class="dispute-handling">
				 <view class="dispute" @click="goToNext('mine-dispute')">
					 <view class="iconfont icon">&#xe798;</view>
					 <text>我的纠纷</text>
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
				info:{}
			}
		},
		//刷新页面
		onPullDownRefresh() {
			this.getMinePagesInfo(()=>{
				uni.stopPullDownRefresh()
			})
		},
		methods: {
			
			/* 前往用户主页 */
			goToPersonalCenter(userId){
				console.log('detail页面中userID为：'+userId+" fleaId为："+this.flea_id)
				uni.navigateTo({
					url:'/pages/personal-center/personal-center?flea_id='+this.flea_id+'&userId='+userId
				})
			},
			
			/* 获取相关信息 */
			async getMinePagesInfo(callBack){
				const result = await this.$myRequest({
					url:'/mine/mineinfo/'+this.flea_id
				})
				this.info=result.data
				this.$store.state.attentionNum=this.info.attentionNum
				this.$store.state.collectNum=this.info.collectNum
				callBack && callBack()
			},
			goToNext(type){
				uni.navigateTo({
					url:'/pages/index/mine/'+type+'/'+type+'?flea_id='+this.flea_id
				})
			},
			/* 跳转到商品收藏页面 */
			/* goToCollects(){
				uni.navigateTo({
					url:'/pages/index/mine/collect/collect?flea_id='+this.flea_id
				})
			}, */
			/* 跳转到关注页面 */
			/* goToAttentions(){
				uni.navigateTo({
					url:'/pages/index/mine/attention/attention?flea_id='+this.flea_id
				})
			}, */
			/* 跳转到粉丝页面 */
			/* goToFans(){
				uni.navigateTo({
					url:'/pages/index/mine/fans/fans?flea_id='+this.flea_id
				})
			}, */
			/* 跳转到浏览历史页面 */
			// goToFans(){
			// 	uni.navigateTo({
			// 		url:'/pages/index/mine/fans/fans?flea_id='+this.flea_id
			// 	})
			// }
			
			/* 我的发布 */
			
			/* 我卖出的 */
			
			/* 我的订单 */
			
			/* 我买到的 */
			
			/* 我的纠纷 */
		},
		onLoad() {
			console.log(this.$store)
			try {
				this.flea_id = uni.getStorageSync('feal_id');
			} catch (e) {
			    // error
			}
			
			this.getMinePagesInfo()
		},
		onShow() {
			// this.getMinePagesInfo()
			this.info.attentionNum=this.$store.attentionNum
		},

	}
</script>

<style lang="scss">
.mine{
	display: flex;
	flex-direction: column;
	.top{

		display: flex;
		flex-direction: row;
		
		align-items: center;
		width: 93%;
		margin: 0 auto;
		margin-top: 100rpx;
		.user-icon{
			background-color: gray;
			width: 120rpx;
			height: 120rpx;
			border-radius: 50%;
		}
		.user-name-id{
			display: flex;
			flex-direction: column;
			margin-left: 30rpx;
			.user-name{
				font-size: 40rpx;
			}
			.user-id{
				margin-left: 30;
			}
		}
		.user-credit{
			margin-left: 30rpx;
			margin-top: 50rpx;
			color: $uni-color-midgray;
		}
	}
	
	.fans-collect-history-attention{
		width: 100%;
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		margin: 0 auto;
		margin-top: 50rpx;
		padding: 20rpx 0;
		border-bottom: 20rpx solid $uni-color-lightgray;
		.collect,.attention,.history,.fans{
			display: flex;
			flex-direction: column;
			.number{
				font-size: 40rpx;
				text-align: center;
			}
			text{
				text-align: center;
			}
		}
	}
	
	.my-trading{
		display: flex;
		flex-direction: column;
		
		strong{
			
			font-size: 40rpx;
			width: 93%;
			margin: 0 auto;
			margin-top: 50rpx;
		}
		.issue-order-sold-bought{
			display: flex;
			flex-direction: row;
			justify-content: space-evenly;
			margin-top: 40rpx;
			.issue,.order,.sold,.bought{
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				
				.icon{
					width: 100rpx;
					height: 100rpx;
					border-radius: 50%;
					text-align: center;
					line-height: 100rpx;
					font-size: 60rpx;
				}
				text{
					text-align: center;
					color: $uni-color-midgray
				}
			}
			.issue{
				.icon{
					
					background-color: #22BCEF;
				}
			}
			.order{
				.icon{
					background-color: #FF8000;
				}
			}
			.sold{
				.icon{
					background-color: #ED9495;
				}
			}
			.bought{
				.icon{
					background-color: #DBED0F;
					padding-left: 5rpx;
				}
			}
			
		}
	}

	.my-affair{
		display: flex;
		flex-direction: column;
		strong{
			font-size: 40rpx;
			width: 93%;
			margin: 0 auto;
			margin-top: 50rpx;
		}
		.dispute-handling{
			display: flex;
			flex-direction: row;
			// justify-content: space-evenly;
			margin-top: 40rpx;
			.dispute{
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				margin-left: 40rpx;
				.icon{
					width: 100rpx;
					height: 100rpx;
					border-radius: 50%;
					text-align: center;
					line-height: 100rpx;
					font-size: 60rpx;
					background-color: #EF2B2B;
					color: #fff;
				}
				text{
					color: $uni-color-midgray;
				}
			}
			
		}
	}
}	

</style>
