<template>
	<view>
		<view v-for="(order,index) in orders" class="orders" :key="order.id">
			<view class="merchant" @click="goToPersonalCenter(order.userId)">
				<view class="usericon-usernmae">
					<image class="userIcon" :src="order.usersIcon"></image>
					<view class="userName">{{order.userName}}</view>
				</view>
				<view class="userId">跳蚤id：{{order.userId}}</view>
			</view>
			<view class="goods" @click="goGoodsDetail(order.goodsId,order.userId)">
				<image class="goods-picture" :src="order.goodsPicture"></image>
				<view class="goods-info">
					<view class="goods-des">{{order.goodsContent}}</view>
					<view class="goods-price">{{order.goodsPrice}}</view>
				</view>
			</view>
			<view class="order">
				<view class="status">{{order.orderStatus}}</view>
			</view>
			<!-- 买入 -->
			<view class="order-action" v-if="dealType==='buy'">
				<!-- 历史 -->
				<view class="history-order" v-if="order.orderStatus==='交易完成'">
					<view class="order-details" @click="goToOrderDetails(order.orderId,order.orderStatus)">详情</view>
				</view>
				<!-- 待处理 -->
				<!-- <view v-if="order.orderStatus==='发起'?true:false " class="alter-order">修改订单</view> -->
				
				
				<view class="todo-order" v-else>
					<view class="order-details" @click="goToOrderDetails(order.orderId,order.orderStatus)">详情</view>
					<view  class="delete-order"  @click="deleteOrder(order,'todo',index,0)">取消订单</view> 
					
					<view class="alter-order" @click="updateOrder(order)">修改</view> 
					<view v-if="order.orderStatus==='交易中'" class="affirm-order" @click="accomplishOrder(index,order)">确认收货</view>
				</view>
				
			</view>
			<!-- 卖出 -->
			<view class="order-action" v-else>
				<!-- 历史 -->
				<view class="history-order" v-if="order.orderStatus==='交易完成'">
					<view class="order-details" @click="goToOrderDetails(order.orderId,order.orderStatus)">详情</view>
				</view>
				<!-- <view v-if="order.orderStatus==='发起'?true:false " class="alter-order">修改订单</view> -->
				
				<!-- 待处理 -->
				<view class="todo-order" v-else>
					<view class="order-details" @click="goToOrderDetails(order.orderId,order.orderStatus)">详情</view>
					<view v-if="order.orderStatus==='待接单'" class="delete-order" @click="deleteOrder(order,'todo',index,1)">拒单</view>
					<view v-if="order.orderStatus==='待接单'" class="alter-order" @click="acceptOrder(order,index)">接单</view> 
					<view v-else class="delete-order" @click="deleteOrder(order,'todo',index)">悔单</view>
				</view>
				
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props:['orders','dealType'],
		data() {
			return {
				order:[],
				
			};
		},
		methods:{
			
			/**
			 * 商家接收订单
			 * @param {Object} orderId
			 * @param {Object} index
			 */
			async acceptOrder(order,index){
				this.$emit(
					'acceptOrder',order,index
				)
			},
			
			/**
			 * 商家悔单，用户毁单
			 * @param {Object} orderId
			 * @param {Object} type
			 * @param {Object} goodsId
			 * @param {Object} index
			 */
			deleteOrder(order,type,index,role){
				this.$emit(
				'deleteOrder',order,type,index,role
				)
			},
			/**
			 * 用户修改订单
			 * @param {Object} orderId
			 */
			updateOrder(order){
				this.$emit(
				
				'updateOrder',order
				)
			},
			
			/**
			 * 用户确认收货
			 * @param {Object} orderId
			 */
			accomplishOrder(index,order){
				this.$emit(
				'accomplishOrder',index,order.userId,order.goodsId,order.orderId,order.userEmail,order.goodsTitle
				)
			},
			
			/**
			 * 去往用户主页
			 * @param {Object} userId
			 */
			goToPersonalCenter(userId){
				this.$emit('goToPersonalCenter',userId)
			},
			
			/**
			 * 查看订单详情
			 * @param {Object} orderId
			 */
			goToOrderDetails(orderId,status){
				console.log("订单id为：",orderId," status为：",status)
				uni.navigateTo({
					url:'/pages/index/mine/mine-orders/order-details/order-details?order_id='+orderId+'&status='+status
				})
			},
			
			/**
			 *  去商品主页
			 * @param {Object} goodsId
			 * @param {Object} userId
			 */
			goGoodsDetail(goodsId,userId) {
				this.$emit('goGoodsDetail', userId,goodsId)
			}
		}
	}
</script>

<style lang="scss">
	.orders{
		display: flex;
		flex-direction: column;
		width: 87%;
		margin: 0 auto;
		background-color: #fff;
		padding: 20rpx;
		margin-top: 20rpx;
		border-radius: 20rpx;
		.merchant{
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: space-between;
			margin-bottom: 10rpx;
			.usericon-usernmae{
				display: flex;
				flex-direction: row;
				.userIcon{
					width: 50rpx;
					height: 50rpx;
					border-radius: 50%;
				}
				.userName{
					margin-left: 20rpx;
				}
			}
			
			.userId{
				text-align: right;
				width: 330rpx;
				
				
			}
		}
		.goods{
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			
			.goods-picture{
				width: 100rpx;
				height: 100rpx;
				border-radius: 20rpx;
			}
			.goods-info{
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				width: 80%;
				
				.goods-des{
					
				}
				.goods-price{
					text-align: right;
					
				}
			}
		}
		.order{
			margin-top: 10rpx;
			border-top: 1px solid $uni-color-midgray;
			.status{
				text-align: right;
				color: $uni-color-red;
				margin: 5rpx 0;
				font-size: 35rpx;
			}
		}
		.order-action{
			display: flex;
			flex-direction: row;
			justify-content: flex-end;
			
			
			color: #fff;
			.todo-order{
				display: flex;
				flex-direction: row;
			}
			.order-details{
				margin-left: 20rpx;
				background-color: yellow;
				padding: 10rpx 20rpx;
				color: $uni-color-midgray;
				border-radius: 30rpx;
			}
			.alter-order{
				margin-left: 20rpx;
				background-color: $uni-color-blue;
				padding: 10rpx 20rpx;
				border-radius: 30rpx;
			}
			.delete-order{
				margin-left: 20rpx;
				background-color: $uni-color-orange;
				padding: 10rpx 20rpx;
				border-radius: 30rpx;
			}
			.affirm-order{
				margin-left: 20rpx;
				background-color: $uni-color-red;
				padding: 10rpx 20rpx;
				border-radius: 30rpx;
			}
		}
	}
</style>
