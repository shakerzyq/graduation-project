<template>
	<view class="login_box">
		<view class="textarea">
			<input class="input-account" placeholder="请输入账号" focus v-model="account.email"/>
			<input class="input-password" id="a" placeholder="请输入密码" password="true" v-model="account.password"/>
			<view><text @click="findPassword" >忘记密码?</text></view>
			<button @click="goToIndex">登录</button>
		</view>
		<!-- <input class="uni-input-input" placeholder="test"/> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				account:{
					flea_id:null,
					email:null,
					password:null,
					status:null,
				}
			}
		},
		methods: {
			findPassword() {
				uni.navigateTo({
					url: '/pages/login/password-login/forget-password/forget-password'
				})
			},
			async goToIndex(){
				//this.setStorageAsync()
				uni.showLoading({
					title:"登录中"
				})
				
				const result =await this.$myRequest({
					url:'/login/pwdlogin',
					data:{
						email:this.account.email,
						password:this.account.password
						
					},
					method:'POST',
					
				})
				
				if(result.data!=null&&result.data.length===9){
					uni.hideLoading()
					//this.setStorage()
					/* uni.setStorage({
						key: 'feal_id',
						data: result.data,
						success() {
							console.log('跳蚤id存储成功')
						}
					}) */
					try {
					    uni.setStorageSync('feal_id', result.data);
						console.log("存储了flea_"+result.data)
						
					} catch (e) {
					    // error
					}
					uni.reLaunch({
						url: '/pages/index/index'
					})
				}else{
					uni.showToast({
						title:"账号或密码错误",
						icon:"none",
						duration:3000
					})
				}
				
				
			},
			/* setStorageAsync() {
				plus.storage.setItemAsync("login_mark", true, function(){
					console.log("setItemAsync success");
				}, function(e){
					console.log("setItemAsync failed: "+JSON.stringify(e));
				});
			},
			setStorage() {
				uni.setStorage({
					key: 'login_mark',
					data: true,
					success() {
						console.log('存储成功')
					}
				})
			} */
		}
	}
</script>

<style lang="scss">
	.textarea {
		position: relative;
		width: 80%;
		margin: 150rpx auto;

		input {
			border: 1px solid black;
			height: 80rpx;
			margin: 60rpx auto 0rpx;
			border-radius: 10rpx;
			padding: 5rpx 15rpx;
		}

		text {
			font-size: 16rpx;
			color: gray;
		}

		view {
			display: flex;
			flex-direction: row;
			justify-content: flex-end;
		}

		button {
			margin-top: 80rpx;
			background-color: $uni-color-orange;
			color: #fff;
		}
	}
</style>
