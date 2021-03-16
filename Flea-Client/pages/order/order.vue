<template>
	<view>
		<!-- 订单信息 -->
		<view class="order-info">
			<view class="status">
				<strong>交易状态</strong>
				<text>sfsaf</text>
			</view>
			<view class="merchant">
				<strong>卖方id</strong>
				<text>sfadsfa</text>
			</view>
			<view class="consumer">
				<strong>买方id</strong>
				<text>dfasdsdf</text>
			</view>
			<view class="trading-place">
				<strong>交易地点</strong>
				<text>sfsda</text>
			</view>
			<view class="trading-date">
				<strong>交易时间</strong>
				<text>sfsa</text>
			</view>
			<view class="order-id">
				<strong>订单号</strong>
				<text>safdsf</text>
			</view>
			
		</view>
		<view class="buttons">
			<button>取消订单</button>
			<button></button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.order-info{
		display: flex;
		flex-direction: column;
		width: 93%;
		margin: 0 auto;
		
		.status,.merchant,.consumer,.trading-place,.trading-date,.order-id{
			display: flex;
			flex-direction: row;
			margin: 20rpx 0;
			
			strong{
				
				width: 200rpx;
			}
			text{
				text-align: center;
			}
		}
	}
</style>
