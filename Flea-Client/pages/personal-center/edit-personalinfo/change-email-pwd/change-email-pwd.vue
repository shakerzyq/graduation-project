<template>
	<view>
		<view class="email-pwd">
			<view class="email">
				<view class="title">
					修改账号
				</view>
				<view class="iconfont icon" @click="changeEmail">&#xe748;</view>
			</view>
			<view class="pwd">
				<view class="title">修改密码</view>
				<view class="iconfont icon" @click="changePwd(flea_id)">&#xe748;</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				flea_id:null,
			}
		},
		onLoad(options){
			this.flea_id=options.flea_id
			
		},
		methods: {
			/* 修改账号 */
			changeEmail(flea_id){
				uni.navigateTo({
					url:'/pages/personal-center/edit-personalinfo/change-email/change-email?flea_id'+flea_id
				})
			},
			
			/* 修改密码 */
			changePwd(flea_id){
				uni.navigateTo({
					url:'/pages/login/password-login/forget-password/forget-password?flea_id'+flea_id
				})
			}
		}
	}
</script>

<style lang="scss">
	.email-pwd{
		display: flex;
		flex-direction: column;
		width: 93%;
		margin: 0 auto;
		.email,.pwd{
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;
			margin: 10rpx 0;
			.title{
				
			}
		}
	}

</style>
