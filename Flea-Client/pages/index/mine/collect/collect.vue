<template>
	<view>
		<!-- <view>
			
		</view> -->
		<goodsList v-if="goods.length>0" @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
		<view class="hint" v-else>快去收藏一些心仪的宝贝</view>
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
		//监听是否触底了
		onReachBottom() {
			console.log()
			if(this.goods.length<this.pagenum*8) return this.flag=true
			console.log("触底了")
			this.pagenum++
			this.getMinePublish()
		},
		onLoad(options) {
			this.flea_id = options.flea_id
			
			this.getCollects()
		},
		methods: {
			async getCollects(){
				const result = await this.$myRequest({
					url:'/mine/collectInfo/'+this.pagenum+'/'+this.flea_id,
					
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
		text-align: center;
		font-size: 50rpx;
		color: $uni-color-gray;
		margin-top: 375rpx;
	}
</style>
