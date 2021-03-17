<template>
	<view class="body">
		<view class="top-bar">
			<view class="iconfont back-icon" @tap="back() ">&#xe749;</view>
		</view>
		<!-- 用户商品信息区 -->
		<view class="user">
			<image class="user-icon" :src="icon"></image>
			<view class="user-other">
				<view class="user-nickname">{{user.nickname}}</view>
				<view class="user-credit">信用值：{{user.credit}}</view>
			</view>
		</view>
		<!-- 商品价格，联系信息 -->
		<view class="other-info">
			<view class="weixin-class">
				<text>微信号</text>
				<view class="weixin">{{user.weixin}}</view>
			</view>
			<view class="price-class">
				<text>价格</text>
				<view class="price">{{price}}</view>
			</view>
		</view>
		
		<!-- 需要让用户填写的部分 -->
		<view class="input-class">
			<view class="hint-class">
				<text>提示</text>
				<view class="hint">该部分需要和卖家商议成功后再填写，发起订单请求后需要等待对方同意</view>
				
			</view>
			<view class="site-class">
				<text>地点</text>
				<input class="site" v-model="orderDate" placeholder="请输入交易地点"/>
			</view>
			<view class="date-class">
				<text>时间</text>
				<input class="date" v-model="tradingPlace" placeholder="请输入交易时间"/>
			</view>
			<view class="remark-class">
				<text>备注</text>
				<input class="remark" v-model="remark" placeholder="请输入交易备注(可不填)"/>
			</view>
		</view>
		
		<!-- 发起订单按钮 -->
		<button class="put" @click="putOrder">发起交易</button>
	</view>
</template>

<script>
	
	export default{
		data(){
			return{
				fleaId:null,
				merchantId:null,
				goodsId:null,
				credit:null,
				orderDate:null,
				tradingPlace:null,
				goodsTitle:null,
				remark:null,
				username:null,
				user:{
			
					
				},
				icon:null,
				
			}
		},
		onLoad(options) {
			this.price=options.price
			this.fleaId=options.userId
			this.merchantId=options.merchantId
			this.goodsId=options.goodsId
			this.goodsTitle=options.goodsTitle
			this.username=options.username
			
			
			this.getUserInfo()
			/* 获取用户头像，微信号 */
			// setTimeout(
			// 	this.getUserInfo()
			// ,1000)
			
		},
		methods:{
			back() {
				uni.navigateBack();
			},
			/* 发起交易 */
			async putOrder(){
				if(this.tradingPlace!==null&&this.orderDate!==null){
					const result = await this.$myRequest({
						url:'/order/putOrder',
						method:'PUT',
						data:{
							consumer_id:this.fleaId,
							merchant_id:this.merchantId,
							goods_id:this.goodsId,
							order_date:this.orderDate,
							trading_place:this.tradingPlace,
							order_status:'待接单',
							remark:this.remark,
						},
					})
					
					// const emailRes =this.$sendEmail(this.user.email,content)
					console.log("得到的结果为"+result.data)
					if(result.data){
						const content="用户:"+this.username+"(跳蚤ID:"+this.fleaId+")  下单了您的商品("+this.goodsTitle+")，"+"请您尽快处理"
						const res =await this.$myRequest({
							url:'/notify/sendemail/'+this.user.email+'/'+content,
							method:"PUT"
						})
						uni.showToast({
							icon:'none',
							title:'下单成功'
						})
						uni.navigateBack({
							delta:2
						})
					} else{
						uni.showModal({
							// title: '提示',
							content: '该商品已被别人抢先下单,您无法下单，可以先收藏观望一下',
							showCancel: false,
							confirmText: "确定",
							 success: (res) => {
								uni.navigateBack({
									delta:1
								})
							}
						});
					}
					
				}else{
					uni.showToast({
						icon:'none',
						title:'请将信息填写完整'
					})
				}
			},
			
			
			async getUserInfo(){
				console.log("fleaId为："+this.fleaId)
				const result = await this.$myRequest({
					url:'/order/userInfo/'+this.merchantId,
					
				})
				this.user=result.data
				this.icon = this.user.user_icon
				
			}
		}
	}
</script>

<style lang="scss">
	.body{
		display: flex;
		flex-direction: column;
		.top-bar{
			margin-top: 70rpx;
			display: fixed;
			.back-icon{
				font-size: 60rpx;
			}
		}
	
		.user{
			display: flex;
			flex-direction: row;
			width: 93%;
			margin: 0 auto;
			.user-icon{
				width:100rpx;
				height:100rpx;
				border-radius: 50%;
				
			}
			.user-other{
				margin-left: 50rpx;
				display: flex;
				flex-direction: column;
				justify-content: center;
				.user-nickname{
					
				}
				.user-credit{
					
				}
			}
		
		}
	
		.other-info{
			width: 93%;
			display: flex;
			flex-direction: column;
			margin: 0 auto;
			margin-top: 50rpx;
			text{
				color: $uni-color-gray;
				width: 100rpx;
				margin-right: 40rpx;
			}
			.weixin-class{
				display: flex;
				flex-direction: row;
				margin: 30rpx 0;
				.weixin{
					
				}
			}
			.price-class{
				display: flex;
				flex-direction: row;
				.price{
					color: $uni-color-red;
					font-size: 50rpx;
					line-height: 50rpx;
				}
			}
		}
		
		.input-class{
			
			width: 93%;
			margin: 0 auto;
			margin-top: 80rpx;
			display: flex;
			flex-direction: column;
			
			text{
				font-weight: 1000;
				margin-right: 40rpx;
				font-size: 40rpx;
			}
			
			.hint-class{
				border: 1px solid $uni-color-gray;
				border-radius: 20rpx;
				.hint{
					color: gray;
					margin-bottom: 40rpx;
				}
				
			}
			
			.site-class{
				margin: 30rpx 0;
				display: flex;
				flex-direction: row;
				align-items: center;
				margin-top: 30rpx;
				
				.site{
					
				}
				
			}
			.date-class{
				margin: 30rpx 0;
				display: flex;
				flex-direction: row;
				align-items: center;
			}
			.remark-class{
				margin: 30rpx 0;
				display: flex;
				flex-direction: row;
				align-items: center;
			}
		}
	
		.put{
			position: fixed;
			color: #fff;
			width: 200rpx;
			height:80rpx;
			line-height: 80rpx;
			background-color: $uni-color-blue;
			bottom: 30rpx;
			right: 30rpx;
			border-radius: 40rpx;
		}
	}
	
</style>
