<template>
	<view class="body">
		<!-- 提示 -->
		<view class="hint">
			<view class="hint-icon iconfont">&#xe7f0;</view>
			<view class="hint-content">
				请和和商家商量好之后再修改，如果频繁修改，商家有理由拒绝订单
			</view>
		</view>
		<!-- 修改内容 -->
		<view class="updateContent">
			<view class="site">
				<view class="title">交易地点</view>
				<input class="input"  v-model="order.trading_place"/>
			</view>
			<view class="date">
				<view class="title">交易时间</view>
				<input class="input"  v-model="order.order_date"/>
			</view>
		</view>
		<!-- 确认修改 -->
		<button class="confirm" @click="updateOrderInfo()">修 改</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderId:null,
				flea_id:null,
				status:null,
				order:{},
			}
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			this.orderId=options.orderId
			this.status=options.status
			this.getOrderInfo()
		},
		methods: {
			async getOrderInfo(){
				const result = await this.$myRequest({
					url:'/order/getOrderDetailInfo/'+this.orderId+'/'+this.status
				})
				this.order=result.data
			},
			async updateOrderInfo(){
				uni.showModal({
					title:"提示",
					content:"确认修改订单信息？修改订单将会扣除2.5信誉值",
					cancelText:"取消",
					confirmText:"确认",
					success: (res) => {
						if(res.confirm){
							const result = this.$myRequest({
								url:'/order/updateOrder/'+this.flea_id+'/'+this.order.order_id+'/'+this.order.trading_place+'/'+this.order.order_date,
								method:'POST'
							})
							if(result.data){
								uni.showToast({
									icon:'none',
									title:'修改订单成功'
								})
								uni.navigateBack({
									delta:1
								})
							}
						}
					}
				})
				
			}
			
		}
	}
</script>

<style lang="scss">
	.body{
		display: flex;
		flex-direction: column;
		background-color: $uni-color-lightgray;
		height: 1250rpx;
		.hint{
			display: flex;
			flex-direction: row;
			width: 100%;
			padding: 30rpx 30rpx;
			margin: 0 auto;
			align-items: center;
			background-color: #fdffca;
			color: #d4d442;
			.hint-icon{
				
			}
			.hint-content{
				width: 660rpx;
				margin-left: 20rpx;
			}
		}
		.updateContent{
			display: flex;
			flex-direction: column;
			width: 93%;
			margin: 0 auto;
			margin-top:50rpx ;
			background-color: #fff;
			border-radius: 20rpx;
			.site,.date{
				display: flex;
				flex-direction: row;
				margin: 20rpx;
				.title{
					color: $uni-color-midgray;
					
					margin-right: 20rpx;
					line-height: 70rpx;
				}
				.input{
					width: 70%;
					border-bottom: 1px solid $uni-color-midgray;
					padding: 10rpx;
				}
			}
		}
		.confirm{
			position: fixed;
			background-color: $uni-color-orange;
			border-radius:30rpx;
			bottom: 30rpx;
			right: 30rpx;
			height: 70rpx;
			line-height: 70rpx;
			color: #fff;
			width: 160rpx;
		}
	}
	
</style>
