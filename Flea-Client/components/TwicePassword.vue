<template>
	<view>
		<view class="textarea">
			<input @input="first_verify" class="input-account" placeholder="请输入密码" focus password="true" />
			<view class="pwd_prompt" v-if="pwd_judge">{{pwd_prompt}}</view>
			<input @input="second_verify" class="input-password" id="a" placeholder="确认密码" password="true" />
			<text>{{detail}}</text>
		</view>
		<!-- <input class="uni-input-input" placeholder="test"/> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				firstpwd: null,
				secondpwd: null,
				detail: null,
				button_judge: true,
				pwd_prompt: '必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-16之间',
				pwd_judge: false,
				email: 'hello world',
			}
		},

		methods: {
			first_verify(e) {
				this.firstpwd = e.detail.value
				const result = RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$/).test(e.detail.value)
				if (!result) {
					this.pwd_judge = true
				} else {
					this.pwd_judge = false
				}
				console.log("密码是否合格" + result)
			},
			second_verify(e) {
				this.secondpwd = e.detail.value
				if (this.firstpwd != this.secondpwd) {
					this.detail = "两次密码不一致"
					this.button_judge = true
				} else {
					this.detail = ""
					this.button_judge = false
					uni.$emit('pwd',{
						password:this.secondpwd,
						button_judge:this.button_judge
					})
				}
			}
		},
		onLoad: function(option) {
			console.log("emial:" + option.email + "this.email" + this.email)
			this.email = option.email
		}
	}
</script>

<style lang="scss">
	.textarea {
		position: relative;
		width: 100%;
		margin: 0rpx auto;

		input {
			border: 1px solid black;
			height: 80rpx;
			margin: 60rpx auto 0rpx;
			border-radius: 10rpx;
			padding: 5rpx 15rpx;
		}

		text {
			font-size: 16rpx;
			color: red;
		}

		button {
			margin-top: 80rpx;
		}

		.pwd_prompt {
			font-size: 16rpx;
			color: red;
		}
	}
</style>
