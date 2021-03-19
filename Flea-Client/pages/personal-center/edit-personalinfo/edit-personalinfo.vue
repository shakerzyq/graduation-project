<template>
	<view>
		<view class="info">
			<view class="alterable">
				<text >可修改</text>
				<view class="user-icon">
					<strong>头像:</strong>
					
					<image @click="chooseImg" class="icon" v-for="(item,index) in photo" :key="index" :src="item"  ></image>
				</view>
				<view class="user-name">
					<strong>昵称:</strong>
					<input class="name" v-model="userInfo.nickname"/>
				</view>
				
				<view class="user-weixin">
					<strong>微信:</strong>
					<input class="weixin" v-model="userInfo.weixin"/>
				</view>
				<view class="user-introduce">
					<strong>自我介绍:</strong>
					<textarea class="introduce" v-model="userInfo.self_introduction"></textarea>
				</view>
				<!-- <view class="account-pwd">
					
				</view> -->
			</view>
			
			<view class="immutable">
				<text >禁修改</text>
				<view class="user-id">
					<strong>用户ID:</strong>
					<view class="id">{{userInfo.flea_id}}</view>
				</view>
				<view class="user-site">
					<strong>学籍信息:</strong>
					<view class="site">{{userInfo.province}}-{{userInfo.college}}-{{userInfo.academy}}-{{userInfo.grade}}</view>
				</view>
				<view class="user-credit">
					<strong>信用值:</strong>
					<view class="credit">{{userInfo.credit}}</view>
				</view>
				<view class="user-sex">
					<strong>性别:</strong>
					<view class="sex">{{userInfo.sex==='man'?'男':'女'}}</view>
				</view>
			</view>
			<view class="update-account-pwd" @click="goToChangeAccountPwd">
				<view class="text">修改账号密码</view>
				<view class="iconfont icon">&#xe748;</view>
			</view>
			
			<view class="actions">
				<view class="update-info" @click="updateUserInfo">保 存</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flea_id:null,
				userInfo:{},
				photo:[],
				picture:null,
			}
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			this.getUserInfo(this.flea_id)
		},
		methods: {
			
			/* 获取用户信息 */
			async getUserInfo(flea_id){
				
				const result = await this.$myRequest({
					url:'/personal/getUserInfo/'+flea_id
				})
				this.userInfo = result.data
				this.photo[0]=result.data.user_icon
			},
			
			/* 修改用户信息 */
			async updateUserInfo(){
				console.log("保存后的icon为：",this.userInfo.user_icon)
				if(this.photo[0]!==this.userInfo.user_icon){
					this.uploadImage(this.photo[0])
				}
				setTimeout(()=>{
					const result = this.$myRequest({
						url:'/personal/updateUserInfo',
						method:'PUT',
						data:{
							flea_id:this.flea_id,
							nickname:this.userInfo.nickname,
							self_introduction:this.userInfo.self_introduction,
							user_icon:this.userInfo.user_icon,
							weixin:this.userInfo.weixin,
						}
					})
					if(result.data){
						uni.showToast({
							icon:'none',
							title:'修改成功'
						})
					}
				},1000)
				
			},
			
			//上传图片
			// 上传图片到服务器
			async uploadImage(filepath) {
				uni.uploadFile({
					url: 'http://192.168.43.199:8083/register/upload/user_photos',
					filePath: filepath,
					name: 'file',
					formData: {
						'user': 'test'
					},
					success: (uploadFileRes) => {
						if (uploadFileRes.data !== null){
							this.userInfo.user_icon=uploadFileRes.data	
							// this.photo[0]=uploadFileRes.data
							console.log("上传后的路径为："+this.userInfo.user_icon)
							
						}
					}
				})
				
				
			},
			
			// 选择图片
			chooseImg() {
				console.log("user_icon为："+this.photo[0])
				uni.chooseImage({
					count: 1,
					success: (res) => {
						this.photo = this.photo.concat(res.tempFilePaths)
						this.photo.splice(0,1)
						console.log("this.photo[0] : "+this.photo[0])
					}
				})
			},
			
			/* 前往修改密码页面 */
			goToChangeAccountPwd(){
				console.log("safdsafsdfs"+this.flea_id)
				uni.navigateTo({
					url:'/pages/personal-center/edit-personalinfo/change-email-pwd/change-email-pwd?flea_id='+this.flea_id
				})
			}
		}
	}
</script>

<style lang="scss">
	$margintop:10rpx;
	.info{
		display: flex;
		flex-direction: column;
		width: 93%;
		margin:0 auto;
		
		.alterable{
			display: flex;
			flex-direction: column;
			margin-top: 30rpx;
			text{
				color: $uni-color-green;
			}
			.user-icon{
				display: flex;
				flex-direction: row;
				align-items: center;
				justify-content: space-between;
				margin: $margintop 0;
				.icon{
					width: 150rpx;
					height: 150rpx;
					border-radius: 10rpx;
				}
			}
			.user-name{
				display: flex;
				flex-direction: row;
				
				margin: $margintop 0;
				.name{
					margin-left: 30rpx;
				}
			}
			.user-weixin{
				display: flex;
				flex-direction: row;
				
				margin: $margintop 0;
				.weixin{
					margin-left: 30rpx;
				}
			}
			.user-introduce{
				display: flex;
				flex-direction: column;
				margin: $margintop 0;
				
				strong{
					margin:$margintop 0;
				}
				
				.introduce{
					width:97%;
					height: 200rpx;
					background-color: $uni-color-lightgray;
					padding: 10rpx;
					border-radius: 15rpx;
				}
			}
		}

		.immutable{
			display: flex;
			flex-direction: column;
			border-top: 1px solid gray;
			margin-top: 30rpx;
			padding-top: 30rpx;
			text{
				color: $uni-color-red;
			}
			.user-id{
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin: $margintop 0;
				.id{
					
				}
			}
			.user-site{
				margin: $margintop 0;
				
				.site{
					
				}
			}
			.user-credit{
				display: flex;
				flex-direction: row;
				margin: $margintop 0;
				justify-content: space-between;
				.credit{
					
				}
			}
			.user-sex{
				display: flex;
				flex-direction: row;
				margin: $margintop 0;
				justify-content: space-between;
				.sex{
					
				}
			}
		}
	
		.update-account-pwd{
			display: flex;
			flex-direction: row;
			justify-content: flex-end;
			margin-top: 20rpx;
			.text{
				
			}
			.icon{
				
			}
		}
	
		.actions{
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			position: fixed;
			bottom: 20rpx;
			right: 30rpx;
			
			.update-info{
				width: 150rpx;
				height: 70rpx;
				background-color: $uni-color-orange;
				border-radius: 30rpx;
				line-height: 70rpx;
				text-align: center;
				color: #fff;
			}
		}
	}
</style>
