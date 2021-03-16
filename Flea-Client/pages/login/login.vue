<template>
	<view>
		<view class="texts">
			<view class="title">跳蚤</view>
			<view id="tagline">属于大学生的二手物品交易平台</view>
		</view>
		<view class="buttons">
			<button @click="goPassWordLogin">账号密码登录</button>
			<button @click="goEmailLogin">邮箱登录</button>
			<button @click="goRegister">注&emsp;&emsp;册</button>
		</view>
		<view>
			<view class="explain">点击登录即表明您已经阅读并同意<text>《跳蚤用户服务协议》</text></view>
		</view>
		
		<button @click="gotest">ToTest</button>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				mark: false
			}
		},
		methods: {
			gotest(){
				uni.navigateTo({
					url: '/pages/test/test2'
				})
			},
			goPassWordLogin() {
				uni.navigateTo({
					url: '/pages/login/password-login/password-login'
				})
			},
			goEmailLogin() {
				uni.navigateTo({
					url: '/pages/login/email-login/email-login'
				})
			},
			goRegister() {
				uni.navigateTo({
					url: '/pages/register/register'
				})
			}
		},
		onLoad() {
			//console.log('App onLoad++')
			//// #ifdef H5
			// uni.getStorage({
			// 	key: 'login_mark',
			// 	success: res => {
			// 		this.mark = res.data
			// 		console.log('name 异步获取 login= ' + this.mark);
			// 	}
			// })
			//// #endif

			// if(this.mark===true){
			// 	console.log("标识为"+this.mark)
			// 	uni.navigateTo({
			// 		url: '/pages/index/index'
			// 	})
			// }
		}
	}
</script>

<style lang="scss">

	.texts {
		width: 100%;
		margin-top: 100rpx;

		.title {
			font-size: 72px;
			text-align: center;
		}

		#tagline {
			font-size: 30rpx;
			text-align: center;
		}
	}

	.buttons {
		width: 75%;
		height: 600rpx;
		margin: 90rpx auto;
		display: flex;
		flex-direction: column;
		justify-content: space-evenly;

		button {
			width: 100%;
		}

		button:nth-child(1) {
			background-color: $uni-color-orange;
			color: #fff;
		}

		button:nth-child(2) {
			background-color: $uni-color-blue;
			color: #fff;
		}

		button:nth-child(3) {
			background-color: #fff;
			border: 1px solid black;
		}
	}

	.explain {
		width: 100%;
		position: absolute;
		font-size: 25rpx;
		text-align: center;
		bottom: 10rpx;

		text {
			color: #4C9AE7;
		}
	}
</style>
