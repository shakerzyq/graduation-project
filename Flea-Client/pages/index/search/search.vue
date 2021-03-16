<template>
	<view>
		<view class="search-body">
			<!-- <view class="search-top">
				<view class="search-back iconfont">&#xe749;</view>
				<view class="search-father">
					<input @input="inputMonitor" class="search-text "  placeholder="搜你想搜的" readonly="readonly" />
					<view class="iconfont search-icon"  @click="goSearch">&#xe8aa;</view>
				</view>
			</view> -->
			<searchComponents  :searchValue="searchValue" @readySearch="readySearch" @inputMonitor="inputMonitor" @goSearch="getGoodsList"></searchComponents>
			<view class="reslut-list" v-if="searchList">
				<view v-for="(item,index) in searchList" :key="index" class="result-item" v-html="item.htmlResult" @click="getGoodsList(item.result,1)"></view>
				
			</view>
			<!-- 内容区 -->
			<view class="content" v-if="contentShowJudge">
				<view class="sort-class" >
					<view v-for="(item,index) in sortType" :key="index" :style="{color:item.color}" @click="selectSortType(item,index)">{{item.type}}</view>
				</view>
				<view v-if="(typeof goods)==='undefined'||goods.length===0?true:false" class="hint">
					<text>还没有人发布该类商品</text>
					<text>或</text>
					<text>相关商品</text>
					</view>
				<goodsList  @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
				
			</view>
			
			
		</view>
		
	</view>
</template>

<script>
	import searchComponents from '../../../components/searchComponents.vue'
	import goodsList from '../../../components/goods_list.vue'
	export default {
		data() {
			return {
				flea_id:null,//用户的跳蚤id
				searchList:[],
				goods: [],
				goodsa: [],
				goodsb: [],
				pagenum:1,//当前页数
				searchValue:null,//当前搜索字段，用于排序		
				contentShowJudge:false,//判断是否显示内容区
				sortType:[
					{
						type:'综合',
						color:'#e10e03',
						code:1
					},
					{
						type:'点赞数',
						color:'#cdcdcd',
						code:2
					},
					{
						type:'收藏数',
						color:'#cdcdcd',
						code:3
					},
					{
						type:'价格降序',
						color:'#cdcdcd',
						code:4
					},
					{
						type:'价格升序',
						color:'#cdcdcd',
						code:5
					}	
				],
				sortIndex:0//记录当前sortType的index
			}
		},
		components:{
			searchComponents,goodsList
		},
		onLoad(options) {
			console.log("传递过来的value为："+options.value)
			this.searchValue=options.value
			this.getGoodsList(this.searchValue,1)
		},
		methods: {
			
			
			async inputMonitor(value){
				if(value){
				const result = await this.$myRequest({
						url:"/elasticsearch/searchTitle/"+value,
					})
					this.searchList=result.data
				}
			},
			goSearch(value){
				
				// uni.navigateTo({
				// 	url:'/pages/index/search/searchResult?value='+value
				// })
			},
			// goToSearchGood(){
			// 	uni.navigateTo({
			// 		url:''
			// 	})
			// }
			readySearch(){
				console.log("readySearch")
				this.goodsa=[]
				this.goodsb=[]
				this.goods=[]
				this.contentShowJudge=false
			},
			selectSortType(item,index){
				this.sortType[this.sortIndex].color =  '#cdcdcd'
				this.sortIndex=index
				item.color='#e10e03'
				this.getGoodsList(this.searchValue,item.code)
			},
			
			
			async getGoodsList(value,sortType){
				this.contentShowJudge=true
				this.searchValue=value
				this.goodsa=[];
				this.goodsb=[];
				this.goodsa=[];
				this.searchList=[];
				console.log("value为"+value)
				const result = await this.$myRequest({
					url:'/elasticsearch/searchGoodsList/'+this.pagenum+"/"+value+'/'+sortType
				})
				console.log("服务器的商品数据为"+result.data.length)
				
				
				this.goods=result.data
				
				//分为ab两列，用于做出不同长度处理
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
	.search-body{
		display: flex;
		flex-direction: column;
		// .search-top{
		// 	display: flex;
		// 	flex-direction: row;
		// 	align-items: center;
			
		// 	height: 80rpx;
		// 	margin-top: 80rpx;
		// 	.search-back{
		// 		font-size: 60rpx;
		// 	}
		// 	.search-father {
		// 		height: 50rpx;
		// 		border-radius: 20rpx;
		// 		display: flex;
		// 		flex-direction: row;
		// 		justify-content: space-between;
		// 		align-items: center;
		// 		width: 95%;
		// 		margin: 10rpx;
		// 		height: 80rpx;
		// 		margin-left: 15rpx;
			
		// 		.search-text {
		// 			width: 85%;
		// 			border: 2px solid $uni-color-orange;
		// 			text-align: center;
		// 			height: 80rpx;
		// 			border-radius: 40rpx;
					
		// 		}
			
		// 		.search-icon {
		// 			line-height: 50rpx;
		// 			text-align: center;
		// 			font-size: 60rpx;
		// 			margin-right: 20rpx;
		// 		}
		// 	}
		// }
		.reslut-list{
			width: 93%;
			
			margin: 0 auto;
			margin-top: 20rpx;
			.result-item{
				font-size: 35rpx;
				border-bottom: 1px solid $uni-color-gray;
				padding-bottom: 20rpx;
			}
		}
		
		.content{
			display: flex;
			flex-direction: column;
			.sort-class{
				display: flex;
				flex-direction: row;
				justify-content: space-evenly;
			}
		}
		.hint{
			display: flex;
			flex-direction: column;
			margin-top: 100rpx;
			color: $uni-color-midgray;
			line-height: 100rpx;
			text-align: center;
			font-size: 40rpx;
		}
	}
	
</style>
