<template>
	<view class="body">


		<view class="classify">
			<scroll-view class="left" scroll-y>
				<view :class="active===index?'active':''" v-for="(item,index) in aClass" @click="leftClickHandle(index,item.type)"
				 :key="item.id">{{item.type}}</view>
			</scroll-view>

			<scroll-view class="right" scroll-y>
				<!-- btype -->
				<view class="bctype-class" v-for="(items,index) in bcClass" :key="index">
					<!-- btype.title -->
					<strong class="btype-class" @click="goSearch(atype+items.btype)">{{items.btype}}</strong>
					<!-- ctype -->
					<view class="ctypes-class">
						<view class="ctype-class" v-for="(item,index2) in items.ctype" :key="index2" @click="goSearch(atype+items.btype+item.type+'')">{{item.type}}</view>
					</view>

				</view>
			</scroll-view>


		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				aClass: [],
				bcClass: [],
				active: 0,
				atype: null,
			}
		},
		onLoad() {
			this.getTypesA()
		},
		methods: {

			async leftClickHandle(index, atype) {
				this.atype = atype
				console.log("type为：" + atype + " index为：" + index)
				this.bcClass = []
				this.active = index
				if ((typeof this.aClass[index].bcClass) === 'undefined') {
					const res = await this.$myRequest({
						url: '/elasticsearch/searchBCType/' + atype,
					})
					console.log(res.data)
					this.aClass[index].bcClass = res.data
				}
				this.bcClass = this.aClass[index].bcClass
			},
			async getTypesA() {
				const atypes = await this.$myRequest({
					url: '/elasticsearch/getTypesA'
				})
				this.aClass = atypes.data
				// console.log("aclass为"+atypes.data)
				this.leftClickHandle(this.active, this.aClass[0].type)
				// this.add_info.atypeTag=0
				// this.aclass.forEach((a)=>{
				// 	console.log("aclass中的"+a.type)
				// 	if(a.type===this.add_info.atype){
				// 		a.choice=true
				// 		a.bgcolor='#F88000'
				// 		throw Error
				// 	}
				// 	this.add_info.atypeTag++
				// })

			},
			//查询btype和ctype
			async getTypesB(atype) {
				const result = await this.$myRequest({
					url: '/elasticsearch/searchBCType/' + atype,
				})
				this.bcClass = result.data

			},
			goSearch(value) {

				console.log("value为:" + value)
				uni.navigateTo({
					url: '/pages/index/search/search?value=' + value
					// url:'/pages/index/search/search'
				})
			}
		}
	}
</script>

<style lang="scss">
	.body{
		
	}
	.classify {
		margin-top: 50rpx;
		background-color: #f1f1f1;
		display: flex;
		flex-direction: row;

		.left {
			width: 180rpx;
			height: 1430rpx;

			background-color: #fff;
			border-right: 3px solid #f1f1f1;

			view {
				height: 60px;
				line-height: 60px;
				color: #333;
				text-align: center;
				font-size: 30rpx;
				border-top: 1px solid #eee;

			}

			.active {
				font-weight: 1000;
				font-size: 34rpx;
				border-right: 1px solid #f1f1f1;
				background-color: #f1f1f1;
				color: $uni-color-orange;
			}

		}

		.right {
			display: flex;
			flex-direction: column;
			height: 1430rpx;

			.bctype-class {
				display: flex;
				flex-direction: column;
				background-color: #fff;
				border-radius: 20rpx;
				margin: 20rpx;

				.btype-class {
					padding: 20rpx;
					font-size: 40rpx;
					border-bottom: 2px solid $uni-color-lightgray;
				}

				.ctypes-class {
					display: flex;
					flex-direction: row;
					flex-wrap: wrap;

					.ctype-class {
						text-align: center;
						// line-height: ;
						width: 100rpx;

						padding: 20rpx;
						margin: 10rpx 0;
						margin-left: 40rpx;

					}
				}
			}
		}
	}
</style>
