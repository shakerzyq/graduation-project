<template>
	<view class="body">
		<!-- 顶部导航栏 -->
		<view class="top-bar">
			<view class="order-status">
				<!-- 待处理订单 -->
				<view class="order-todo" @click="getToDo('todo','buy')" :style="{color:todobg,borderBottom:todoborder}">
					<view class="iconfont todo">&#xe892;</view>
					<text>待处理</text>
				</view>
				<!-- 历史订单 -->
				<view class="order-history" @click="getHistory('history','buy')" :style="{color:historybg,borderBottom:historyborder}">
					<view class="iconfont history">&#xe786;</view>
					<text>历史</text>
				</view>
			</view>
			
			<!-- 买或者卖的订单 -->
			<view class="sell-buy">
				<view class="buy" :style="{color:buybg}" @click="getBuy(orderStatus,'buy')">买入订单</view>
				<view class="sell" :style="{color:sellbg}" @click="getSell(orderStatus,'sell')">卖出订单</view>
			</view>
		</view>
		
		<!-- 内容展示部分 -->
		<view class="content">
			
			<view class="orders-list">
				<order-list :orders="orders" :dealType='dealType' 
				@goToPersonalCenter="goToPersonalCenter" 
				@goGoodsDetail="goGoodsDetail" 
				@deleteOrder="deleteOrder" 
				@updateOrder="updateOrder" 
				@accomplishOrder="accomplishOrder" 
				@acceptOrder="acceptOrder"
				></order-list>
			</view>
			
			
		</view>
	</view>
</template>

<script>
	import orderList from '../../../../components/ordersList.vue'
	export default {
		data() {
			return {
				flea_id:null,
				username:null,
				todobg:'#F88000',
				todoborder:'1px solid #ccc',
				historybg:'#ccc',
				historyborder:'1px solid #fff',
				
				sellbg:'#ccc',
				buybg:'#3EB1D7',
				
				orderStatus:'todo',
				dealType:'buy',
				
				pagenum:1,//当前页数
				flag:false,
				
				getOrderType:0,
				
				orders:[],
			}
		},
		components: {
			orderList
		},
		onLoad(options) {
			this.flea_id = options.flea_id
			this.username=options.username
			
			console.log("跳蚤id为："+this.flea_id)
			
			this.getOrders('todo','buy')
			
		},
		//监听是否触底了
		onReachBottom() {
			console.log()
			if(this.orders.length<this.pagenum*8) return this.flag=true
			console.log("触底了")
			this.pagenum++
			this.getOrders(this.orderStatus,this.dealType)
		},
		methods: {
			
			//导航到商品详情页
			goGoodsDetail(userId,goodsId){
				console.log("跳转需要的id"+goodsId,userId)
				uni.navigateTo({
					url:'/pages/index/goods-detail?goodsId='+goodsId+'&'+'userId='+userId
				})
			},
			
			/**
			 * 用户主页界面
			 * @param {Object} userId
			 */
			goToPersonalCenter(userId){
				uni.navigateTo({
					url:'/pages/personal-center/personal-center?flea_id='+this.flea_id+'&userId='+userId
				})
			},
			
			
			
			getToDo(orderStatus,dealType){
				this.orderStatus='todo'
				this.dealType=dealType
				
				this.todobg='#F88000';
				this.historybg='#ccc';
				this.historyborder='1px solid #fff'
				this.todoborder='1px solid #ccc'
				
				this.sellbg='#ccc'
				this.buybg='#3EB1D7'
				console.log(this.todoborder,this.historyborder)
				
				this.orders=[]
				this.pagenum=1
				this.getOrders(orderStatus,dealType)
			},
			
			getHistory(orderStatus,dealType){
				this.orderStatus='history'
				this.dealType=dealType
				
				this.historybg='#F88000';
				this.todobg='#ccc';
				this.todoborder='1px solid #fff'
				this.historyborder='1px solid #ccc'
				
				this.sellbg='#ccc'
				this.buybg='#3EB1D7'
				console.log(this.todoborder,this.historyborder)
				
				this.orders=[]
				this.pagenum=1
				this.getOrders(orderStatus,dealType)
			},
			
			getSell(orderStatus,dealType){
				this.dealType=dealType
				
				this.sellbg='#3EB1D7'
				this.buybg='#ccc'
				
				this.orders=[]
				this.pagenum=1
				this.getOrders(orderStatus,dealType)
			},
			
			getBuy(orderStatus,dealType){
				this.dealType=dealType
				
				this.sellbg='#ccc'
				this.buybg='#3EB1D7'
				
				this.orders=[]
				this.pagenum=1
				this.getOrders(orderStatus,dealType)
			},
			
			/**
			 * 获取订单信息
			 * @param {Object} orderStatus
			 * @param {Object} dealType
			 */
			async getOrders(orderStatus,dealType){
				this.orderStatus=orderStatus
				this.dealType=dealType
				console.log("stutus为"+this.orderStatus+" type为"+this.dealType)
				
				const result = await this.$myRequest({
					url:'/order/getOrders/'+this.pagenum+'/'+this.flea_id+'/'+orderStatus+'/'+dealType
				})
				this.orders=result.data.reverse()
			},
			
			// sendEmail(content){
			// 	uni.$emit('sendEmail',content)
			// }
			
			
			
			/* 删除订单,拒绝订单,悔单 */
			async deleteOrder(order,type,index,role,email){
				uni.showModal({
					title:"提示",
					content:"确认取消订单？毁单会扣除5信誉值,如果在交易中毁单会扣除10信誉值",
					cancelText:"取消",
					confirmText:"确认",
					success: (res) => {
						if(res.confirm){
							const result =  this.$myRequest({
								url:'/order/deleteOrder/'+this.flea_id+'/'+order.orderId+'/'+type+'/'+order.goodsId+'/'+order.orderStatus,
								method:'DELETE'
							})
							this.orders.splice(index,1)
							
							const content="用户:"+this.username+"(跳蚤ID:"+this.flea_id+")  "+((role===0)?'取消':'拒绝')+"了商品("+order.goodsTitle+")的订单"
							// this.sendEmail(content)
							const res = this.$myRequest({
								url:'/notify/sendemail/'+order.userEmail+'/'+content,
								method:"PUT"
							})
						}
						
					},
				})
				
			},
			
			/* 修改订单 */
			updateOrder(order){
				uni.navigateTo({
					url:'/pages/index/mine/mine-orders/updateOrder/updateOrder?orderId='+order.orderId+'&flea_id='+this.flea_id+'&status='+order.orderStatus
					+'&username='+this.username+'&goodsTitle='+order.goodsTitle+'&email='+order.userEmail
				})
			},
			
			/* 确认收货 */
			async accomplishOrder(index,userId,goodsId,orderId,email,goodsTitle){
				console.log("goodsid:",goodsId,"merchantId:",userId,"orderId:",orderId)
				uni.showModal({
					title:"提示",
					content:"确认收货？",
					cancelText:"取消",
					confirmText:"确认",
					success: (res) => {
						if(res.confirm){
							this.orders.splice(index,1)
							uni.navigateTo({
								url:'/pages/index/mine/mine-orders/evaluate-trading/evaluate-trading?orderId='+orderId+
								'&merchantId='+userId+'&goodsId='+goodsId+"&email="+email+'&flea_id='+this.flea_id+'&username='+this.username+'&goodsTitle='+goodsTitle
							})
							
							
						}
					},
				})
				
			},
			
			/* 接收订单 */
			async acceptOrder(order,index){
				uni.showModal({
					title:"提示",
					content:"确认接收订单？",
					cancelText:"取消",
					confirmText:"确认",
					success: (res) => {
						if(res.confirm){
							const result =this.$myRequest({
								url:'/order/acceptOrder/'+order.orderId
							})
							this.orders[index].orderStatus='交易中'
							const content="用户:"+this.username+"(跳蚤ID:"+this.flea_id+")  "+"接收了你对商品("+order.goodsTitle+")的订单"
							// this.sendEmail(content)
							const res = this.$myRequest({
								url:'/notify/sendemail/'+order.userEmail+'/'+content,
								method:"PUT"
							})
						}
						
						
					},
				})
			}
			
		}
	}
</script>

<style lang="scss">
	page{background-color:$uni-color-lightgray;}
.body{
	display: flex;
	flex-direction: column;
	.top-bar{
		display: flex;
		flex-direction: column;
		width: 100%;
		margin: 0 auto;
		background-color: #fff;
		justify-content: center;
		
		padding-bottom: 20rpx;
		text{
			font-size: 30rpx;
		}
		.order-status{
			display: flex;
			flex-direction: row;
			.order-todo{
				width: 50%;
				text-align: center;
				// border-bottom: 1px solid transparency;
				.todo{
					font-size: 60rpx;
				}
			}
			.order-history{
				text-align: center;
				width: 50%;
				// border-bottom: 1px solid $uni-color-midgray;
				.history{
					font-size: 60rpx;
				}
			}
		}
		
		.sell-buy{
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			width: 93%;
			margin: 0 auto;
			margin-top: 30rpx;
			background-color: #fff;
			
			
		
			.sell{
				
				text-align: center;
				color:#ccc ;
			}
			.buy{
				text-align: center;
				color:#3EB1D7;
			}
		}
	}
	
	.content{
		display: flex;
		flex-direction: column;
		width: 100%;
		
		margin:0 auto;
		
		
		
	}
}
</style>
