<template>
	<view class="goods_list">
		<view class="goods_list1">
			<view class="goods_item" v-for="(good,index) in goodsa" :key="index" @click="navigator(good.goodsId,good.userId)">
				<image :class="good.classtype" :src="good.goodsPhoto"></image>
				<view class="price">
					<text>￥{{good.goodsPrice}}</text>
					<text>{{good.goodsWanter}}人想要</text>
				</view>
				<text class="name">
					{{good.goodsTitle}}:{{good.goodsDes}}
				</text>
				<view class="user">
					<image :src="good.userIcon"></image>
					<text>{{good.userName}}</text>
				</view>
			</view>		
		</view>
		<view class="goods_list2">
			<view class="goods_item" v-for="(good,index) in goodsb" :key="index" @click="navigator(good.goodsId,good.userId)">
				<image :class="good.classtype" :src="good.goodsPhoto"></image>
				
				<view class="price">
					<text>￥{{good.goodsPrice}}</text>
					<text>{{good.goodsWanter}}人想要</text>
				</view>
				<text class="name">
					{{good.goodsTitle}}:{{good.goodsDes}}
				</text>
				<view class="user">
					<image :src="good.userIcon"></image>
					<text>{{good.userName}}</text>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	export default {
		props:['goodsa','goodsb'],
		data() {
			return {
				h: 60,
				
			}
		},

		methods: {
			navigator(goodsId,userId) {
				this.$emit('goodsItemClick', userId,goodsId)
			}
		},
		onShow() {
			console.log(goods)		
		}
	}
</script>

<style lang="scss">
	.goods_list{
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		width: 100%;
		.goods_list1,.goods_list2 {
			width: 46%;
			display: flex;
			flex-direction:column;
			justify-content: flex_end;
			align-items:center;
		
			.goods_item {
				background: #fff;
				width: 355rpx;
				display: flex;
				flex-direction: column;
				
				margin: 10rpx 0;
				padding: 5rpx 10rpx;
				box-sizing: border-box;
				/* 不让格子撑大 */
				border-radius: 5rpx;
				.img_goods4 {
					width: 100%;
					height: 160px;
					margin: 0 auto;
					border-radius: 12rpx;
				}
				
				.img_goods1 {
					width: 100%;
					height: 150px;
					margin: 0 auto;
					border-radius: 12rpx;
				}
				.img_goods2 {
					width: 100%;
					height: 140px;
					margin: 0 auto;
					border-radius: 12rpx;
				}
				.img_goods3 {
					width: 100%;
					height: 130px;
					margin: 0 auto;
					border-radius: 12rpx;
				}
		
				.price {
					margin: 5rpx 0;
		
					display: flex;
					justify-content: space-between;
					flex-direction: row;
					align-items: center;
		
					text:nth-child(1) {
						font-size: 35rpx;
						color: $uni-color-orange;
					}
		
					text:nth-child(2) {
						font-size: 10px;
						color: $uni-color-gray;
					}
				}
		
				.name {
					// overflow: hidden;
					// text-overflow: ellipsis;
					/* white-space:nowrap; */
					font-size: 28rpx;
					line-height: 40rpx;
					//height: 80rpx;
					overflow: hidden;
					-webkit-line-clamp: 2;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-box-orient: vertical;
				}
		
				.user {
					width: 100%;
					display: flex;
					align-items: center;
					flex-direction: row;
		
		
					image {
						background-color: gray;
						border-radius: 50%;
						width: 40rpx;
						height: 40rpx;
					}
		
					text {
						padding: 10rpx;
						font-size: 25rpx;
					}
		
				}
			}
		
		}
	}
	

</style>
