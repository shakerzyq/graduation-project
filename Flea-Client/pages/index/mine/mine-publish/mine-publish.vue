<template>
	<view>
		<goodsList v-if="goods.length>0" @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
		<view class="hint" v-else>
			<text>还没有发布任何商品</text> 
			<button class="add-products" @click="goToAddProducts">发布商品</button>
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
			}
		},
		components: {
			goodsList
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			console.log("fleaId为："+this.flea_id)
			this.getMinePublish()
		},

		
		//监听是否触底了
		onReachBottom() {
			console.log()
			if(this.goods.length<this.pagenum*8) return this.flag=true
			console.log("触底了")
			this.pagenum++
			this.getMinePublish()
		},
		methods: {
			/* 获取商品信息 */
			async getMinePublish(){
				console.log("进入了")
				const result = await this.$myRequest({
					url:'/mine/publishInfo/'+this.pagenum+'/'+this.flea_id
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
