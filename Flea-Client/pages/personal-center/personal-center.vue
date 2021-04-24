<template>
	<view >
		<view v-if="loadingJudge" class="body">
		<view class="top-bar">
			<view class="icon-fans-attention-likes-collect" >
				<image   class="usericon" :src="userInfo.userIcon" ></image>
				
				<view class="orters-info">
					<view class="fans-attention-likes-collect">
						<view class="fans" @click="goToNext('fans')">
							<strong>{{userInfo.fansNum}}</strong>
							<text>粉丝</text>
						</view>
						<view class="attention" @click="goToNext('attention')">
							<strong>{{userInfo.attentionNum}}</strong>
							<text>关注</text>
						</view>
						
						<view class="likes" @click="goToNext('likes')">
							<strong>{{userInfo.likesNum}}</strong>
							<text>点赞</text>
						</view>
					</view>
					<view v-if="flea_id===userId?false:true" class="attention-action" @click="fansAction(flea_id,userId)">
						<view class="attention" v-if="userInfo.fansJudge?false:true">
							<view class="iconfont icon">&#xe8a1;</view>
							<text>关注</text>
						</view>
						<view v-else class="attentioned" >
							已关注
						</view>
					</view>
					<!-- 编辑个人信息 -->
					<view v-else class="edit-info" @click="editInfo(flea_id)">
						<view class="edit">编辑资料</view>
					</view>
					
				</view>
				
			</view>
			<view class="nickname-userid-credit-introduce" >
				<view class="nickname-userid">
					<strong class="nickname">{{userInfo.userName}}</strong>
					<view class="userid">跳蚤id：{{userId}}</view>
				</view>
				<view class="credit">信誉值：{{userInfo.userCredit}}</view>
				<view class="introduce">{{userInfo.userIntroduce}}</view>
			</view>
		</view>
	
		<view class="content">
			<view class="title">
				<view class="trading" :class="tradingClass" @click="getGoodsInfomiddleware('living')">上架中{{userInfo.tradingNum}}</view>
				<view class="sold" :class="soldClass" @click="getGoodsInfomiddleware('dead')">成交{{userInfo.deadNum}}</view>
			</view>
			
			<goodsList v-if="goods.length>0" @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
		</view>
		</view>
		<view v-else class="loading">
			
			<view class="loading-content">加载中。。。。。。</view>
		</view>
	</view>
</template>

<script>
	import goodsList from '../../components/goods_list.vue'
	export default {
		data() {
			return {
				pagenum:1,//当前页数
				flag:false,
				
				flea_id:null,
				userId:null,
				goods:[],
				goodsa: [],
				goodsb: [],
				
				status:'living',//查询类型
				
				loadingJudge:false,
				
				soldClass:'sold',
				tradingClass:'trading2',
				
				userInfo:{}
			}
		},
		onLoad(options) {
			this.flea_id = options.flea_id
			this.userId = options.userId
			console.log('userID为：'+this.userId+" fleaId为："+this.flea_id)
			
			this.getGoodsInfo(this.status)
			this.getUserInfo();
		},
		components: {
			goodsList
		},
		methods: {
			/* 跳转至编辑个人信息页面 */
			editInfo(flea_id){
				uni.navigateTo({
					url:'/pages/personal-center/edit-personalinfo/edit-personalinfo?flea_id='+flea_id
				})
			},
			
			goToNext(type){
				console.log("userId为：fff"+this.userId+"flea_id为："+this.flea_id)
				uni.navigateTo({
					url:'/pages/personal-center/'+type+'/'+type+'?flea_id='+this.flea_id+'&user_id='+this.userId
				})
			},
			
			/* 关注与取关 */
			async fansAction(flea_id, userId) {
				this.type = (this.userInfo.fansJudge) ? 'unfans' : 'fans'
				
				if(this.userInfo.fansJudge){
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('decrementAttention')
					}
				}else{
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('incrementAttention')
					}
				}
				
				const result = await this.$myRequest({
					url: '/product/fansAction/' + flea_id + '/' + userId + '/' + this.type,
				})
				console.log("返回的结果为：",result.data)
				this.userInfo.fansJudge = result.data
			},
			
			/* 查询用户信息 */
			async getUserInfo(){
				const result = await this.$myRequest({
					url:'/personal/userinfo/'+this.userId+'/'+this.flea_id
				})
				
				this.userInfo=result.data
				this.loadingJudge=true
			},
			
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
					url:'/personal/getGoodsInfo/'+this.pagenum+'/'+this.userId+'/'+status
				})
				
				this.goods = result.data
				
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
			//导航到商品详情页
			goGoodsDetail(userId,goodsId){
				console.log("跳转需要的id"+goodsId,userId)
				uni.navigateTo({
					url:'/pages/index/goods-detail?goodsId='+goodsId+'&'+'userId='+userId
				})
			},
		},
		//监听是否触底了
		onReachBottom() {
			if(this.goods.length<this.pagenum*8) return this.flag=true
			console.log("触底了")
			this.pagenum++
			this.getGoodsInfo(this.status)
			console.log("hello world")
			console.log("hello",this.goodsa)
			console.log("world",this.goodsb)
		},
	}
</script>

<style lang="scss">
	.body{
		display: flex;
		flex-direction: column;
		.top-bar{
			width: 93%;
			margin: 20rpx auto;
			display: flex;
			flex-direction: column;
			.icon-fans-attention-likes-collect{
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-bottom: 20rpx;

				.usericon{
					width: 150rpx;
					height: 150rpx;
					border-radius: 50%;
					background-color: yellow;
					margin-right: 30rpx;
				}
				.orters-info{
					display: flex;
					flex-direction: column;
					.fans-attention-likes-collect{
						display: flex;
						flex-direction: row;
						justify-content: space-around;
						width: 500rpx;
						.fans,.attention,.likes,.collect{
							display: flex;
							flex-direction: column;
							text-align: center;
						}
					}
					.attention-action{
						margin-top: 10rpx;
						.attention,.attentioned{
							display: flex;
							flex-direction: row;
							justify-content: center;
							align-items: center;
							background-color: yellow;
							width: 410rpx;
							margin: 0 auto;
							border-radius: 30rpx;
							padding: 10rpx 0;
							.icon{
								font-size: 30rpx;
							}
							text{
								
							}
						}
						.attentioned{
							background-color: $uni-color-midgray;
							color: #fff;
						}
					}
					.edit-info{
						display: flex;
						flex-direction: row;
						justify-content: center;
						
						margin-top: 10rpx;
						width: 500rpx;
						.edit{
							padding: 10rpx 0;
							background-color: $uni-color-lightgray;
							width: 410rpx;
							text-align: center;
							border-radius: 30rpx;
							
						}
					}
				}
				
			}
			.nickname-userid-credit-introduce{
				.nickname-userid{
					display: flex;
					flex-direction: row;
					justify-content: space-between;
					.nickname{
						text-align: center;
						margin: 10rpx 0;
					}
					.userid{
						font-size: 30rpx;
						color: $uni-color-midgray;
						margin: 10rpx 0;
					}
				}
				.credit{
					font-size: 30rpx;
					color: $uni-color-blue;
					margin: 10rpx 0;
				}
				.introduce{
					font-size: 30rpx;
					margin: 10rpx 0;
				}
			}
		}
	
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
		
	}
	.loading{
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		height: 1100rpx;
		.loading-content{
			font-size: 40rpx;
			color: $uni-color-midgray;
		}
	}
</style>
