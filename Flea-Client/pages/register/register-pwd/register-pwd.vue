<template>
	<view class="login_box">
		<view class="textarea">
			
			<!-- <input @input="first_verify" class="input-account" placeholder="请输入密码" focus password="true"/>
			<view class="pwd_prompt" v-if="pwd_judge">{{pwd_prompt}}</view>
			<input @input="second_verify" class="input-password" id="a" placeholder="确认密码"  password="true"/>
			<text>{{detail}}</text> -->
			<TwicePassword></TwicePassword>
			<button @click="inputinfo" :disabled="button_judge">下一步</button>
		</view>	
		<!-- <input class="uni-input-input" placeholder="test"/> -->
	</view>
</template>

<script>
	import TwicePassword from '../../../components/TwicePassword.vue'
	export default {
		data() {
			return {
				// firstpwd:null,
				// secondpwd:null,
				// detail:null,
				// button_judge:true,
				// pwd_prompt:'必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-16之间',
				// pwd_judge:false,
				// email:'hello world',
				// password:null
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
			// first_verify(e){
			// 	this.firstpwd = e.detail.value
			// 	const result = RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$/).test(e.detail.value)
			// 	if(!result){
			// 		this.pwd_judge=true
			// 	}else{
			// 		this.pwd_judge=false
			// 	}
			// 	console.log("密码是否合格"+result)
			// },
			// second_verify(e){
			// 	this.secondpwd = e.detail.value
			// 	if(this.firstpwd!=this.secondpwd){
			// 		this.detail="两次密码不一致"
			// 		this.button_judge=true
			// 	}else{
			// 		this.detail=""
			// 		this.button_judge=false
			// 	}
			// }
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
