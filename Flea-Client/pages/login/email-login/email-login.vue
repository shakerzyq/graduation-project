<template>
	<view class="login_box">
		<view class="textarea">
			<putCode :info="info"></putCode>
			<button @click="inputpwd" :disabled="button_judge">登录</button>
		</view>
	</view>
</template>

<script>
	import putCode from '../../../components/putCode.vue'
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
					detection:'该邮箱没有注册',
				},
			}
		},
		
		components:{putCode},
		/* uni-app常用到的几个提示框
		https://yanyan.blog.csdn.net/article/details/108950967?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.control
		 */
		
		/* uni-app获取input框的value中（@input）
		https://blog.csdn.net/Xl4277/article/details/97753121?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control
		 */
		
		/* uniApp正则表达式校验手机、邮箱
		https://blog.csdn.net/qq_32958797/article/details/89032432
		 */
		methods: {
			async inputpwd() {
				
				// 将输入的验证码拿到后台验证	
				uni.showLoading({
					title:"登录中"
				})
				console.log("点击了登录button,验证码为："+this.auth_code)
				const result = await this.$myRequest({
					url:'/register/email/'+this.auth_code+'/'+this.email+'/login'
				})
				console.log("登录返回的值为"+result.data)
				if(result.data!=null&&result.data.length===9){
						uni.hideLoading()
						this.setStorage()
						try {
						    uni.setStorageSync('flea_id', result.data);
							
						} catch (e) {
						    // error
						}
						uni.reLaunch({
							url: '/pages/index/index'
						})
					}else{
						uni.showToast({
							icon:'none',
							title:"验证码错误",
							duration:3000
						})
					}
			},	
			/* setStorage() {
				uni.setStorage({
					key: 'login_mark',
					data: true,
					success() {
						console.log('存储成功')
					}
				})
			} */
		},
		onLoad:function() {
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
			background-color: $uni-color-blue;
			color: #fff;
		}
	}
	
</style>
