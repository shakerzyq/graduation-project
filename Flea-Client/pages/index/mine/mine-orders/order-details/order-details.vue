<template>
	<view class="body" v-cloak>
		<view class="blank"></view>
		<!-- 订单信息 -->
		<view class="order-info">
			<view class="order-id">
				<strong>订单号:</strong>
				<text>{{order.order_id}}</text>
			</view>
			<view class="order-id">
				<strong>下单时间:</strong>
				<text>{{order.order_time}}</text>
			</view>
			<view class="status">
				<strong>交易状态:</strong>
				<text>{{order.order_status}}</text>
			</view>
			<view class="merchant">
				<strong>卖方id:</strong>
				<text>{{order.merchant_id}}</text>
			</view>
			<view class="consumer">
				<strong>买方id:</strong>
				<text>{{order.consumer_id}}</text>
			</view>
			<view class="trading-place">
				<strong>交易地点:</strong>
				<text>{{order.trading_place}}</text>
			</view>
			<view class="trading-date">
				<strong>交易时间:</strong>
				<text>{{order.order_date}}</text>
			</view>
			
			<view class="evaluate" v-if="order.order_status==='交易完成'">
				<view class="evaluate-level">
					<view class="title">满意度：</view>
					<view class="level">{{order.evaluate_level}}</view>
				</view>
				<view class="evaluate-remark">
					<view class="title">评价：</view>
					<view class="remark">{{order.evaluate_content}}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				order_id:null,
				status:null,
				order:{},
				
			}
		},
		onLoad(options) {
			this.order_id=options.order_id
			this.status=options.status
			this.getOrderDetails(this.order_id,this.status)
		},
		methods: {
			async getOrderDetails(orderId,status){
				const result = await this.$myRequest({
					url:'/order/getOrderDetailInfo/'+orderId+'/'+status
				})
				this.order = result.data
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color:  $uni-color-lightgray;
	}
	.body{
		height: 1240rpx;
		
		.blank{
			height: 30rpx;
		}
		
		.order-info{
			display: flex;
			flex-direction: column;
			width: 83%;
			margin: 0 auto;
			background-color: #fff;
			padding: 20rpx;
			border-radius: 20rpx;

			.status,.merchant,.consumer,.trading-place,.trading-date,.order-id{
				display: flex;
				flex-direction: row;
				margin: 20rpx 0;
				padding-bottom: 10rpx;
				border-bottom: 1px solid $uni-color-midgray;
				
				strong{
					
					width: 200rpx;
				}
				text{
					text-align: center;
				}
			}
			
			.evaluate{
				display: flex;
				flex-direction: column;
				padding: 10rpx;
				border-radius: 15rpx;
				
				.title{
					font-size: 32rpx;
					font-weight: 550;
					color: $uni-color-blue;
				}
				
				.evaluate-level{
					margin-bottom: 10rpx;
					display: flex;
					flex-direction: row;
					color: gray;
				}
				
				.evaluate-remark{
					.remark{
						width: 97%;
						height: 100rpx;
						padding: 10rpx;
						background-color: $uni-color-lightergray;
						border-radius: 15rpx;
						color: gray;
					}
				}
			}
		}
	}
</style>
