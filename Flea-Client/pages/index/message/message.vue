<template>
	<view>
		<view class="message">
			<view class="goods">
				<strong>互动消息</strong>
				<view class="comment" @click="goToNext('goods-comment')">
					<view class="iconfont icon" style="background-color:#22BCEF ;">&#xe85a;</view>
					<text>商品留言</text>
				</view>
				<view class="user-attention" @click="goToNext('comment-reply')">
					<view class="iconfont icon" style="background-color:#ffaa00 ;">&#xe884;</view>
					<text>留言回复</text>
				</view>
			</view>
			<view class="user">
				<strong>事务消息</strong>
				<view class="order-message" @click="goToNext('order-notify')">
					<view  class="iconfont icon" style="background-color:#F88000 ;">&#xe847;</view>
					<text>订单通知</text>
				</view>
				<!-- 评论举报，商品举报，纠纷举报都在里面 -->
				<view class="user-report" @click="goToNext('report-notify')">
					<view class="iconfont icon" style="background-color:#e10e03 ;">&#xe876;</view>
					<text>举报通告</text>
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
				
			}
		},
		
		onLoad(options) {
			

			try {
			    const value = uni.getStorageSync('flea_id');
			    if (value) {
			        this.flea_id=value
			    }
			} catch (e) {
			    // error
			}
		},
		methods: {
			goToNext(page){
				uni.navigateTo({
					url:'/pages/index/message/'+page+'/'+page+'?flea_id='+this.flea_id
				})
			}
		}
		// 判断是否登录，暂时不用
		/* onShow() {
			const mark = false
			console.log('App Launch')
			uni.getStorage({
				key: 'login_mark',
				success: res => {
					//mark = res.data
					console.log('name 异步获取 APP = ' + res.data)
					if (res.data) {
						uni.navigateTo({
							url: '/pages/login/login'
						})
					}
				}
			})
		} */
	}
</script>

<style lang="scss">
	.message{
		display: felx;
		flex-direction: column;
		width: 93%;
		margin: 0 auto;
		.goods{
			strong{
				font-size:40rpx ;
			}
		}
		.user{
			strong{
				font-size:40rpx ;
			}
		}
		.comment,.comment-like,.goods-like,.user-attention,.goods-collect,.order-message,.user-report{
			display: flex;
			flex-direction: row;
			align-items: center;
			margin: 20rpx 0;
			.icon{
				width: 110rpx;
				height: 110rpx;
				border-radius: 50%;
				text-align: center;
				line-height: 110rpx;
				color:#fff;
				font-size: 55rpx;
				margin-right:30rpx ;
			}
		}
	}
</style>
