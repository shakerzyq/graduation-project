<template>
	<view>
		<view class="complains-class">
			<view v-for="(complain,index) in complains" :key="complain.complain_id" class="complain-class">
				<!-- <view></view>
				<view>{{complain.complained_userid}}</view>
				<view></view> -->
				<view class="user-info">
					<view class="userid">举报人id：{{complain.complain_userid}}</view>
					<view class="userid">被举报人id：{{complain.complained_userid}}</view>
				</view>
				<view class="content">
					{{complain.complain_content}}
				</view>
				<view class="complan-type-status">
					<view class="type" v-if="complain.complain_userid===flea_id">举报</view>
					<view v-else>被举报</view>
					<view class="status" v-if="complain.complain_status==='2'" style="color:yellow">处理中</view>
					<view class="status" v-if="complain.complain_status==='0'" style="color:red">拒绝</view>
					<view class="status" v-if="complain.complain_status==='1'" style="color:#00ff00">通过</view>
				</view>
			
				<view v-if="complain.complain_userid===flea_id" class="buttons">
					<view class="details-button" @click="goToComplainDetail(complain.complain_id,complain.complain_status)">详 情</view>
					<view class="delete-button" @click="delComplain(complain.complain_id,index)">删 除</view>
				</view>
				<view class="buttons" v-else >
					<view class="details-button" @click="goToComplainDetail(complain.complain_id,complain.complain_status)">详 情</view>
				</view>
				
			</view>
		</view>
	</view>
</template>

<script>
	
	export default {
		props:['complains','flea_id'],
		data() {
			return {
				/* 前往举报单详细页 */
				async goToComplainDetail(complain_id,status){
					this.$emit('goToComplainDetail',complain_id,status)
				},
				
				/* 删除举报单 */
				async delComplain(complain_id,index){
					this.$emit('delComplain',complain_id,index)
				}
			};
		}
	}
</script>

<style lang="scss">
	.complains-class{
		
		.complain-class{
			width: 93%;
			height: 200rpx;
			margin: 0 auto;
			margin-top: 20rpx;
			background-color: #fff;
			padding: 10rpx;
			border-radius: 20rpx;
			display: flex;
			flex-direction: column;
		
			.user-info{
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				border-bottom: 1px solid $uni-color-midgray;
				padding-bottom: 5rpx;
				.userid{
					
				}
			}
			.content{
				font-size: 32rpx;
				line-height: 25rpx;
				padding: 5rpx;
				//height: 80rpx;
				overflow: hidden;
				-webkit-line-clamp: 2;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-box-orient: vertical;
			}
			.complan-type-status{
				
				display: flex;
				flex-direction: row;
				justify-content: flex-end;
				.type{
					margin-right: 30rpx;
				}
				.status{
					width:160rpx;
					text-align: right;
				}
				
			}
			.buttons{
				display: flex;
				flex-direction: row;
				justify-content: flex-end;
				text-align: center;
				margin-top: 20rpx;
				.details-button,.delete-button{
					margin-left: 20rpx;
					background-color: red;
					width: 160rpx;
					height: 60rpx;
					line-height: 60rpx;
					border-radius: 30rpx;
					color: #fff;
				}
				.details-button{
					background-color: $uni-color-blue;
				}
				.delete-button{
					background-color: $uni-color-orange;
				}
				
			}
		}
	}
	
</style>
