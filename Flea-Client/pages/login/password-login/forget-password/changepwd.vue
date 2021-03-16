<template>
	<view class="login_box">
		<view class="textarea2">
			<!-- <input class="input-account" placeholder="请输入新密码" focus/>
			<input class="input-password" id="a" placeholder="确认密码"  password="true" v-model="password"/> -->
			<TwicePwd></TwicePwd>
			<button @click="goToLogin" :disabled="button_judge">确认</button>
		</view>	
		
		<!-- <input class="uni-input-input" placeholder="test"/>-->
	</view> 
</template>

<script>
	import TwicePwd from '../../../../components/TwicePassword.vue'
	export default {
		data() {
			return {
				email:null,
				password:null,
				button_judge:true,
				
			}
		},
		components:{TwicePwd},
		methods: {
			async goToLogin(){
				const result = await this.$myRequest({
					url:"/login/pwdlogin/changePwd",
					method:'POST',
					data:{
						email:this.email,
						password:this.password
					}
				})
				uni.showToast({
					title:'修改密码成功',
					icon:'none',
					duration:3000
				})
					
				setTimeout(function() {
					uni.reLaunch({
						url:'/pages/login/login'
					})
				}, 2000);	
			}
		},
		onLoad:function(option){
			console.log("onLoad的email为"+option.email)
			this.email = option.email
			uni.$on('pwd',(e)=>{
				this.password=e.password
				this.button_judge = e.button_judge
				console.log("从组件中获得的password为："+this.password)
			})
		},
		onUnload() {
			uni.$off('pwd')
		}
	}
</script>

<style lang="scss">
	.textarea2{
		position: relative;
		width: 80%;
		margin: 10rpx auto;
		input{
			border: 1px solid black;
			height: 80rpx;
			margin: 60rpx auto 0rpx;
			border-radius: 10rpx;
			padding: 5rpx 15rpx;	
		}
		
		
		/* text{
			font-size: 16rpx;
			color: gray;
		}
		view{
			display: flex;
			flex-direction: row;
			justify-content: flex-end;
			
		} */
		button{
			margin-top:80rpx ;
			background-color: $uni-color-orange;
			color: #fff;
		}
	}
</style>
