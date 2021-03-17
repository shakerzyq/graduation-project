<template>
	<view>
		<view class="content" >
			<view class="title">
				<view class="trading" :class="tradingClass" @click="getGoodsInfomiddleware('living')">上架中</view>
				<view class="sold" :class="soldClass" @click="getGoodsInfomiddleware('dead')">成交</view>
			</view>
			
			<goodsList @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
			<view v-if="goods.length===0" class="hint" >
				<text v-if="status==='dead'">还没有成交商品，多发布些吧！</text> 
				<text v-else>没有正在上架的商品</text>
				<button class="add-products" @click="goToAddProducts">发布商品</button>
			</view>
		</view>
		
	</view>
</template>

<script>
	import goodsList from '../../../../components/goods_list.vue'
	export default {
		data() {
			return {
				pagenum:1,//当前页数
				flag:false,
				
				flea_id:null,
				goods:[],
				goodsa: [],
				goodsb: [],
				
				tradingClass:'trading2',
				soldClass:'sold',
				status:'living',//查询类型
			}
		},
		components: {
			goodsList
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			console.log("fleaId为："+this.flea_id)
			this.getGoodsInfo(this.status)
		},

		
		//监听是否触底了
		onReachBottom() {
			console.log()
			if(this.goods.length<this.pagenum*8) return this.flag=true
			console.log("触底了")
			this.pagenum++
			this.getGoodsInfo(this.status)
		},
		methods: {
			
			getGoodsInfomiddleware(status){
				if(status==='dead'){
					this.soldClass='sold2'
					this.tradingClass='trading'
				}else{
					this.soldClass='sold'
					this.tradingClass='trading2'
				}
				this.goods=[]
				this.goodsa=[]
				this.goodsb=[]
				this.status = status
				
				this.getGoodsInfo(status)
				
			},
			/* 查询商品信息 */
			async getGoodsInfo(status){
				const result = await this.$myRequest({
					url:'/personal/getGoodsInfo/'+this.pagenum+'/'+this.flea_id+'/'+status
				})
				
				this.goods = result.data
				
				for (this.i = 0; this.i < this.goods.length; this.i++) {
					const number = Math.round(Math.random() * (4 - 1)) + 1
					
					console.log("i为",this.i)
					console.log("this.i取模为："+this.i%2)
					this.goods[this.i].classtype = "img_goods" + number
					if(this.i%2===1){
						this.goodsb.push(this.goods[this.i])
					}else{
						this.goodsa.push(this.goods[this.i])
					}
				}
			},
			
			/* 发布商品 */
			goToAddProducts(){
				uni.navigateTo({
					url:'/pages/index/addProduct/addProduct'
				})
			},
			
			//导航到商品详情页
			goGoodsDetail(userId,goodsId){
				console.log("跳转需要的id"+goodsId,userId)
				uni.navigateTo({
					url:'/pages/index/goods-detail?goodsId='+goodsId+'&'+'userId='+userId
				})
			},
			
		}
	}
</script>

<style lang="scss">
	
	.content{
		display: flex;
		flex-direction: column;
		.title{
			display: flex;
			flex-direction: row;
			color: $uni-color-midgray;
			width: 93%;
			margin: 0 auto;
			border-top: 1px solid $uni-color-midgray;
			padding-top: 10rpx;
			align-items: cneter;
			.trading{
				margin-right: 20rpx;
				
			}
			.trading2{
				color: black;
				margin-right: 20rpx;
				font-size: 40rpx;
			}
			.sold{
				
			}
			.sold2{
				color: black;
				font-size: 40rpx;
			}
		}
	}
	.hint{
		display: flex;
		flex-direction: column;
		text{
			text-align: center;
			font-size: 50rpx;
			color: $uni-color-gray;
			margin-top: 375rpx;
		}
		.add-products{
			background-color: $uni-color-orange;
			margin-top: 50rpx;
			height: 80rpx;
			line-height: 80rpx;
			color: #fff;
			border-radius: 30rpx;
		}
		
		
	}

</style>
