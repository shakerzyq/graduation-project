<template>
	<view>
		<view class="user-id">
			<strong>您的ID</strong>
			<view class="id">{{complains.complain_userid}}</view>
		</view>
		<view class="compalin">
			<view class="complain-id">
				<strong>举报单ID：</strong>
				<text>{{complains.complain_id}}</text>
			</view>
			<view class="complained-userid">
				<strong>被举报人ID：</strong>
				<text>{{complains.complained_userid}}</text>
			</view>
			<view class="complain-status">
				<strong>状态：</strong>
				<text v-if="complains.complain_status==='2'" style="color:yellow">处理中</text>
				<text v-if="complains.complain_status==='0'" style="color:yellow">拒绝</text>
				<text v-if="complains.complain_status==='1'" style="color:yellow">通过</text>
			</view>
			<view class="complain-status">
				<strong>举报时间：</strong>
				<text >{{complains.start_time}}</text>
			</view>
			<view class="complain-type">
				<strong>类型：</strong>
				<view class="types">
					<view class="type" >{{type===0?'买方举报':'卖方举报'}}</view>
				</view>
			</view>
			<view class="complain-content">
				<strong>举报内容</strong>
				<text>{{complains.complain_content}}</text>
			</view>
			<view class="complain-photos" >
				<strong>举报照片(证据)</strong>
				<view class="photos">
					<image class="photo" v-for="(photo,index) in photos" mode="widthFix" :key="index" :src="photo" @click="previewImg(photo)"></image>
				</view>
			</view>
			<view v-if="status!=='2'">
				<view class="complain-content">
					<strong>管理员留言</strong>
					<text>{{complains.audit_result}}</text>
				</view>
				<view class="complain-status">
					<strong>处理日期</strong>
					<text>{{complains.end_time}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				complain_id:null,
				status:null,
				
				complains:[],
				type:null,
				photos:[],
			}
		},
		onLoad(options) {
			this.complain_id=options.complain_id
			this.status=options.status
			this.getComplainDetail(this.complain_id,this.status)
		},
		methods: {
			
			/**
			 * 预览图片
			 * @param {Object} current
			 */
			previewImg(current) {
				const urls = this.photos.map(item => {
					return item
				})
				uni.previewImage({
					current,
					urls,
				})
			},
			
			async getComplainDetail(complain_id,status){
				console.log('sdfsaf',complain_id,status)
				const result = await this.$myRequest({
					url:'/mine/getComplainDetail/'+complain_id+'/'+status
				})
				
				this.complains=result.data
				console.log(this.complains)
				this.type = this.complains.complain_type
				this.photos = this.complains.complain_photos.split(',')
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $uni-color-lightgray;
	}
	
	.user-id{
		
		width: 90%;
		margin: 0 auto;
		margin-top: 30rpx;
		padding: 15rpx;
		.id{
			
		}
	}
	
	.compalin{
		
		background-color: #fff;
		padding: 15rpx;
		display: flex;
		flex-direction: column;
		width: 85%;
		margin: 0 auto;
		margin-top: 30rpx;
		border-radius: 15rpx;
		.complain-id{
			display: flex;
			flex-direction: column;
			margin:10rpx 0;
		}
		.complained-userid{
			margin: 10rpx 0;
		}
		.complain-status{
			margin: 10rpx 0;
		}
		.complain-type{
			display: flex;
			flex-direction: row;
			margin: 10rpx 0;
			.types{
				display: flex;
				flex-direction: row;
				.type{

				}
			}
		}
		.complain-content{
			margin: 10rpx 0;
			display: flex;
			flex-direction: column;
			text{
				min-height: 200rpx;
				padding: 10rpx;
				border-radius: 15rpx;
				background-color: $uni-color-lightergray;
			}
		}
		.complain-photos{
			display: flex;
			flex-direction: column;
			.photos{
				
				display: flex;
				flex-direction: column;
				align-items: center;
				.photo{
					width: 600rpx;
					margin-top: 20rpx;
					border-radius: 20rpx;
				}
			}
		}
	}

</style>
