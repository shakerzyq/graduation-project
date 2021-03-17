<template>
	<view>
		<message-list @goGoodsDetail="goGoodsDetail" :messages="comments"></message-list>
	</view>
</template>

<script>
	import messageList from '../../../../components/comments-list.vue'
	export default {
		data() {
			return {
				flea_id:null,
				comments:[],
			}
		},
		components:{messageList},
		onLoad(options) {
			this.flea_id=options.flea_id
			this.getGoodsComment(this.flea_id)
		},
		methods: {
			
			/* 获取评论 */
			async getGoodsComment(flea_id){
				const result = await this.$myRequest({
					url:'/message/getGoodsComments/'+flea_id+'/comment_1'
				})
				this.comments=result.data
			},
			
			/* 将评论变为已看过 */
			async updateMessageViewStatus(commentId){
				const res = await this.$myRequest({
					url:'/message/updateMessageViewStatus/'+commentId+'/comment',
					method:'PUT'
				})
			},
			
			//导航到商品详情页
			goGoodsDetail(message,index){
				console.log("sdaf"+message.goods_id,message.merchant_id,message.comment_id,index)
				if(message.view_status===0){
					this.updateMessageViewStatus(message.comment_id)
					this.comments[index].view_status=1
				}
				
				uni.navigateTo({
					url:'/pages/index/goods-detail?goodsId='+message.goods_id+'&'+'userId='+message.merchant_id
				})
			},
		}
	}
</script>

<style lang="scss">
	page{
		background-color: $uni-bg-color-gray;
	}

</style>
