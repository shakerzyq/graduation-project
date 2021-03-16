<template>
	<view>
		<goodsList v-if="goods.length>0" @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
		<view class="hint" v-else>没有浏览什么</view>
	</view>
</template>

<script>
	import goodsList from '../../../../components/goods_list.vue'
	export default {
		data() {
			return {
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
			this.getUserBrowseInfo()
		},
		methods: {
			async getUserBrowseInfo(flea_id){
				const result = await this.$myRequest({
					url:'/browselog/getUserBrowseLog/'+this.flea_id,		
				})
				this.goods=result.data
				for (this.i = 0; this.i < this.goods.length; this.i++) {
					const number = Math.round(Math.random() * (4 - 1)) + 1
					this.goods[this.i].classtype = "img_goods" + number
					if(this.i%2===1){
						this.goodsb.push(this.goods[this.i])
					}else{
						this.goodsa.push(this.goods[this.i])
					}
				}
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
