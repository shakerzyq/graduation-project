<template>
	<view>
		<view class="message">
			<view class="goods">
				<strong>互动消息</strong>
				<view class="comment" >
					<view class="iconfont icon" style="background-color:#22BCEF ;">&#xe85a;</view>
					<text>商品留言</text>
				</view>
				<view class="comment-like">
					<view class="iconfont icon" style="background-color:#EF6818 ;">&#xe831;</view>
					<text>留言点赞</text>
				</view>
				<view class="goods-like">
					<view class="iconfont icon" style="background-color:#ED9495 ;">&#xe721;</view>
					<text>商品点赞</text>
				</view>
				<view class="user-attention">
					<view class="iconfont icon" style="background-color:#ffaa00 ;">&#xe884;</view>
					<text>收藏通知</text>
				</view>
				<view class="goods-collect">
					<view class="iconfont icon" style="background-color:#DBED0F ;">&#xe80d;</view>
					<text>关注通知</text>
				</view>
			</view>
			<view class="user">
				<strong>事务消息</strong>
				<view class="order-message">
					<view class="iconfont icon" style="background-color:#F88000 ;">&#xe847;</view>
					<text>订单通知</text>
				</view>
				<!-- 评论举报，商品举报，纠纷举报都在里面 -->
				<view class="user-report">
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
				
			}
		},
		methods: {
			
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
