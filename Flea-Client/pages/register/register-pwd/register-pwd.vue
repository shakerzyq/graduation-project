<template>
	<view class="login_box">
		<view class="textarea">
			<TwicePassword></TwicePassword>
			<button @click="inputinfo" :disabled="button_judge">下一步</button>
		</view>	
	</view>
</template>

<script>
	import TwicePassword from '../../../components/TwicePassword.vue'
	export default {
		data() {
			return {
				email:null,
				password:null,
				button_judge:true,
			}
		},
		components:{TwicePassword},
		methods: {
			inputinfo() {
				uni.navigateTo({
					url: '/pages/register/register-info/register-info?pwd='+this.password+'&email='+this.email
				})
			},
		},
		onLoad: function(option){
			this.email = option.email
			uni.$on('pwd',(e)=>{
				this.password=e.password
				this.button_judge = e.button_judge
			})
		},
		onUnload() {
			uni.$off('pwd')
		}
	}
</script>

<style lang="scss">
	.textarea{
		position: relative;
		width: 80%;
		margin: 150rpx auto;
		input{
			border: 1px solid black;
			height: 80rpx;
			margin: 60rpx auto 0rpx;
			border-radius: 10rpx;
			padding: 5rpx 15rpx;	
		}
		text{
			font-size: 16rpx;
			color: red;
		}
		button{
			margin-top:80rpx ;
		}
		.pwd_prompt{
			font-size: 16rpx;
			color: red;
		}
	}
</style>
