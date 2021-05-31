<template>
	<view class="login_box">
		<view class="textarea">
			<input class="input-account" placeholder="请输入账号" focus v-model="account.email" />
			<input class="input-password" id="a" placeholder="请输入密码" password="true" v-model="account.password" />
			<view><text @click="findPassword">忘记密码?</text></view>
			<button @click="loginFlow">登录</button>
		</view>
		<!-- <input class="uni-input-input" placeholder="test"/> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				account: {
					flea_id: null,
					email: null,
					password: null,
					status: null,
				},
				serviceStatus: true,
			}
		},
		methods: {
			findPassword() {
				uni.navigateTo({
					url: '/pages/login/password-login/forget-password/forget-password'
				})
			},
			loginFlow() {
				this.goToIndex().then(result => {
					// if (result) {
					console.log("hello", result)
					if (result != null && result.length === 9) {
						uni.hideLoading()
						try {
							this.account.flea_id = result;
							uni.setStorageSync('flea_id', result);
							console.log("存储了flea_id" + result)

						} catch (e) {
							// error
						}
						this.getCollegeArea(result).then(result2 => {
							console.log("hellow", result2)
							this.inquireServiceStatus(result2).then(result3 => {
								console.log("res3為", result3)
								if (result3) {
									uni.reLaunch({
										url: '/pages/index/index'
									})
								} else {
									uni.showToast({
										icon: 'none',
										title: "该校区已关闭服务",
										duration: 3000
									})
								}
							})
						});
					} else {
						uni.showToast({
							title: "账号或密码错误",
							icon: "none",
							duration: 3000
						})
					}

					// } else {
					// 	uni.showToast({
					// 		title: "账号或密码错误",
					// 		icon: "none",
					// 		duration: 3000
					// 	})
					// }
				})
			},
			async goToIndex() {
				//this.setStorageAsync()
				uni.showLoading({
					title: "登录中"
				})

				const result = await this.$myRequest({
					url: '/login/pwdlogin',
					data: {
						email: this.account.email,
						password: this.account.password

					},
					method: 'POST',

				})
				return Promise.resolve(result.data);
				// if (result.data != null && result.data.length === 9) {
				// 	uni.hideLoading()
				// 	try {
				// 		this.account.flea_id = result.data;
				// 		uni.setStorageSync('flea_id', result.data);
				// 		console.log("存储了flea_id" + result.data)

				// 	} catch (e) {
				// 		// error
				// 	}
				// 	return Promise.resolve(result.data);
				// } else {

				// }


				// if(result.data!=null&&result.data.length===9){
				// 	uni.hideLoading()
				// 	try {
				// 		this.account.flea_id=result.data;
				// 	    uni.setStorageSync('flea_id', result.data);
				// 		console.log("存储了flea_id"+result.data)

				// 	} catch (e) {
				// 	    // error
				// 	}

				// 	this.getCollegeArea();
				// 	this.inquireServiceStatus(this.$store.state.addPlace);
				// 	if(this.serviceStatus===true){
				// 		uni.reLaunch({
				// 			url: '/pages/index/index'
				// 		})
				// 	}else{
				// 		uni.showToast({
				// 			icon:'none',
				// 			title:"该校区已关闭服务",
				// 			duration:3000
				// 		})
				// 	}

				// }else{
				// 	uni.showToast({
				// 		title:"账号或密码错误",
				// 		icon:"none",
				// 		duration:3000
				// 	})
				// }


			},
			async getCollegeArea(userId) {
				console.log("userId为：", userId)
				const result = await this.$myRequest({
					url: "/product/getAddPlace/" + userId
				})
				console.log("dizhiwei:", result.data)
				this.$store.state.addPlace = result.data;
				console.log("dizhiwei2:", this.$store.state.addPlace)
				if (result.data != null) {
					return Promise.resolve(result.data);
				} else {

				}
			},
			async inquireServiceStatus(address) {
				console.log("address为：", address)
				const result = await this.$myRequest({
					url: "/login/inquireServiceStatus/" + address
				})
				this.serviceStatus = result.data
				if (result) {
					return Promise.resolve(result.data);
				} else {

				}
			}
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
