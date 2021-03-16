<template>
	<view class="body">
		<!-- 空：&#xe882;实：&#xe884; -->
		<view class="blank"></view>
		<!-- 评价 -->
		<view class="evaluates">
			<view class="evaluate">
				<view class="evaluate-title">商家态度评价</view>
				<startGrade @clickStar="clickStar" :stars="stars1"></startGrade>
				<!-- <startGrade @clickStar="clickStar" :stars="stars"></startGrade> -->
			</view>
			<view class="evaluate">
				<view class="evaluate-title">商家质量评价</view>
				<startGrade @clickStar="clickStar2" :stars="stars2"></startGrade>
			</view>
			<view class="other">
				<view class="other-title">其它</view>
				<textarea class="other-content" placeholder="输入其它内容" v-model="remark"></textarea>
			</view>
		</view>
		<button class="evaluating" @click="commitEvaluate">提 交</button>
	</view>
</template>

<script>
	import startGrade from '../../../../../components/star-grade.vue'
	// import startGrade from '../../../../../static/icons/star/star.png'
	export default {
		data() {
			return {
				orderId:null,
				remark:null,
				i:0,
				level1:0,
				stars1:[
					{
						star:false
					},
					{
						star:false
					},
					{
						star:false
					},
					{
						star:false
					},
					{
						star:false
					},
				],
				level2:0,
				stars2:[
					{
						star:false
					},
					{
						star:false
					},
					{
						star:false
					},
					{
						star:false
					},
					{
						star:false
					},
				]
				,isget:true
			}
		},
		components:{
			startGrade
		},
		onLoad(options) {
			this.orderId=options.orderId
			this.merchantId=options.merchantId
			this.goodsId=options.goodsId
			
		},
		onBackPress() {
			console.log("返回")
			if(this.isget){
				uni.showModal({
					title:'提示',
					confirmText:'确定',
					cancelText:'取消',
					content:'不提交评价将无法确认收货！',
					success: res => {
						if(res.confirm){
							console.log(222)
							uni.navigateBack({
								delta:1
							})
						}
					}
				})
			}
			
		},
		methods: {
			clickStar(index,stars,level){
				for(this.i=0;this.i<=index;this.i++){
					stars[this.i].star=true
				}
				this.level1=this.i
				for(this.i;this.i<5;this.i++){
					stars[this.i].star=false
				}
				this.i=0
			},
			clickStar2(index,stars,level){
				for(this.i=0;this.i<=index;this.i++){
					stars[this.i].star=true
				}
				this.level2=this.i
				for(this.i;this.i<5;this.i++){
					stars[this.i].star=false
				}
				this.i=0
			},
			/* 提交评价 */
			async commitEvaluate(merchantId){
				console.log(this.level1,this.level2,this.remark)
				if(this.level1===0||this.level2===0){
					uni.showToast({
						icon:'none',
						title:'请对服务进行评级'
					})
				}else{
					uni.showModal({
						title:"提示",
						content:"确认收货并提交评价？",
						cancelText:"取消",
						confirmText:"确认",
						success: (res) => {
							if(res.confirm){
								const result = this.$myRequest({
									url:'/order/accomplishOrder',
									data:{
										merchantEvaluate:this.level1,
										goodsEvaluate:this.level2,
										evaluateContent:this.remark,
										orderId:this.orderId,
										goodsId:this.goodsId,
										merchantId:this.merchantId
									},
									method:'POST',
								})
								uni.navigateBack({
									delta:1
								})
								// if(result.data){
								// 	uni.navigateBack({
								// 		delta:1
								// 	})
								// }
								
							}
						},
					})
				}
				
			}
			
		}
	}
</script>

<style lang="scss">
	.body{
		background-color: $uni-color-lightgray;
		height: 1240rpx;
		.blank{
			height: 70rpx;
		}
		.evaluates{
			width: 83%;
			margin: 0 auto;
			background-color: #fff;
			padding: 20rpx;
			border-radius: 20rpx;
			.evaluate{
				.evaluate-title{
					margin: 10rpx 0;
				}
			}
			.other{
				width: 83%;
				
				.other-title{
					margin: 10rpx 0;
				}
				.other-content{
					background-color: $uni-color-lightergray;
					padding: 30rpx;
					width: 560rpx;
					border-radius: 20rpx;
					height: 150rpx;
				}
			}
		}
		.evaluating{
			width: 180rpx;
			height: 70rpx;
			line-height: 70rpx;
			background-color: $uni-color-orange;
			border-radius: 30rpx;
			color: #fff;
			position: fixed;
			bottom: 30rpx;
			right: 30rpx;
		}
		
	}
	
</style>
