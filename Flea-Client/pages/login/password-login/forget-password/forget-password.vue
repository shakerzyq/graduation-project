<template>
	<view class="login_box">
		<view class="textarea">
			<putCode :info="info"></putCode>
			<button @click="nextStep" :disabled="button_judge">下一步</button>
		</view>
	</view>
</template>

<script>
	import putCode from '../../../../components/putCode.vue'
	export default {
		data() {
			return {
				verify_judge:false, //判断验证码是否可以点击
				button_judge: true, //button是否可以点击
				judge_type: 'email_right',//邮箱相关信息的表现class
				detection: null,//邮箱相关信息
				format_judge: false,//输入邮箱的格式判断
				email_user_judge: null,
				verify_code:'获 取 验 证 码',//验证码文本显示内容
				count_down:5,//获取验证码的间隔时间
				email:null,//输入的email
				verify_input_judge:true,//是否可以输入验证码
				auth_code:'',//验证码
				code_result:null,
				info:{//putCode组件需要的信息
					type:'login',
					detection:'该邮箱没有注册'
				},
				flea_id:null
			}
		},
		components:{putCode},
		methods: {
			async nextStep(){
				console.log("id为："+this.flea_id)
				// 将输入的验证码拿到后台验证	
				uni.showLoading({
					title:"验证中"
				})
				
				const result = await this.$myRequest({
					url:'/register/email/'+this.auth_code+'/'+this.email+'/topwd'
				})
				console.log("忘记密码验证结果为"+result.data)
				if(result.data !== null&&result.data.length===7){
						uni.hideLoading()
						uni.navigateTo({
							url:'/pages/login/password-login/forget-password/changepwd?email='+this.email+'&flea_id='+this.flea_id
						})
					}else{
						uni.showToast({
							icon:'none',
							title:"验证码错误",
							duration:3000
						})
					}
			}
		},
		
		
		onLoad:function(options) {
			this.flea_id = options.flea_id
			uni.$on('auth_code',(e)=>{
				this.email = e.email
				this.auth_code=e.auth_code
				this.button_judge = e.button_judge
			})
			
		},
		onUnload() {
			uni.$off('auth_code')
		}	
	}
</script>

<style lang="scss">
	.textarea {
		position: relative;
		width: 80%;
		margin: 0rpx auto;
		button{
			background-color: $uni-color-orange;
			color: #fff;
		}
	}
</style>
