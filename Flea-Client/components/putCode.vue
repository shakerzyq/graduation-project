<template>
	<view>
		<view class="email_area">
			<input @input="aa" class="input-account" placeholder="请输入邮箱" focus />
			<view :class="judge_type"><text>{{detection}}</text></view>
			<view class="code-view">
				<input @input="minitor_code" class="input-code" id="a" placeholder="输入验证码" v-model="auth_code" :disabled="verify_input_judge" />
				<text @click="getVerifyCode">{{verify_code}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:['info'],
		data() {
			return {
				verify_judge: false, //判断验证码是否可以点击
				button_judge: true, //button是否可以点击
				judge_type: 'email_right', //邮箱相关信息的表现class
				detection: null, //邮箱相关信息
				format_judge: false, //输入邮箱的格式判断
				email_user_judge: null,
				verify_code: '获 取 验 证 码', //验证码文本显示内容
				count_down: 10, //获取验证码的间隔时间
				email: null, //输入的email
				verify_input_judge: true, //是否可以输入验证码
				auth_code: '', //验证码
				code_result: null
			};
		},
		filters:{
			changeBorder(focus){
				if(focus){
					return 'border_orange'
				}
			}
		},
		methods: {
			
			aa(e) {
				const format_judge = RegExp(/^\w{3,}(.\w+)*@[A-z0-9]+(.[A-z]{2,5}){1,2}$/).test(e.detail.value)
				if (!format_judge) {
					this.detection = '邮箱格式错误'
					this.judge_type = 'email_wrong'
					console.log(this.judge_type)
				} else {
					this.detection = '邮箱格式正确'
					this.judge_type = 'email_right'
					this.email = e.detail.value
					this.verify_judge = true // 让获取验证码能够点
				}
			},
			//获取验证码相关
			async getVerifyCode() {
				if (this.verify_judge) {
					//发送邮箱
					this.verify_judge=false
					const result = await this.$myRequest({
						url: '/register/putemail/'+this.info.type+'/' + this.email,
					})
					console.log("result为：" + result.data)
					if (result.data === true) {
						uni.showToast({
							title: '已发送邮件',
							icon: 'none',
							duration: 2000
						})
						//this.format_judge=false
						this.verify_input_judge = false //可以输入验证码
						this.reGetCode()
						this.verify_code = '重新获取' + ' (' + this.count_down + ')'
					} else {
						// this.detection = '该邮箱没有注册'
						this.detection = this.info.detection
						this.judge_type = 'email_wrong'
						this.verify_judge=true
					}
				}
			},
			//定时器
			reGetCode() {
				const timer = setInterval(() => {
					this.count_down = this.count_down - 1
					this.verify_code = '重新获取' + ' (' + this.count_down + ')'
					if (this.count_down === 0) {
						clearInterval(timer)
						this.count_down = 10
						this.verify_judge=true
					}
				}, 1000)
			},
			// 监听输入的验证码 ,为6位数button才能点击button
			minitor_code(e) {
				if ((e.detail.value).length === 6) {
					this.button_judge = false
					uni.$emit('auth_code', {
						email:this.email,
						auth_code: this.auth_code,
						button_judge:this.button_judge
					})
				} else {
					this.button_judge = true
					//alert("验证验错误")
					clearInterval()
				}
			},
		},


	}
</script>

<style lang="scss">
	input:focus{
	    background: #000;
	    border: 1px solid orange;
	     /* 解决input按钮自动填充后 浏览器渲染的默认背景问题 */
	    box-shadow: inset 0 0 0 1000px #FFFFFF!important;
	}
	.email_area {
		position: relative;
		width: 100%;
		margin: 0rpx auto;

		.border_orange{
			border: 1px solid orange;
		}
		.code-view {
			width: 100%;
			display: flex;
			height: 150rpx;
			justify-content: space-between;
			flex-direction: row;
			align-items: center;

			.input-code {
				width: 60%;
				border: 1px solid black;
				border-radius: 10rpx;
				height: 80rpx;
				padding: 5rpx 15rpx;
			}

			text {
				font-size: 32rpx;
				line-height: 150rpx;
				color: $uni-color-blue;
			}

		}

		.input-account {
			border: 1px solid black;
			height: 80rpx;
			margin: 60rpx auto 0rpx;
			border-radius: 10rpx;
			padding: 5rpx 15rpx;
		}


		view {
			display: flex;
			flex-direction: row;
			justify-content: flex-start;
		}

		button {
			margin-top: 40rpx;
			border: 1px solid black;
		}

		.email_right {
			color: #84ED7C;
			font-size: 20rpx;
			// background-color: #84ED7C;
		}

		.email_wrong {
			color: red;
			font-size: 20rpx;
			// background-color: red;
		}





	}
</style>
