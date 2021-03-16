<template>
	<view>

	</view>
</template>

<script>
	export default {

		data() {
			return {
				h: 60,

			}
		},
		methods: {
			navigator(id) {
				this.$emit('goodsItemClick', id)
			}
		},
		onLoad() {
			uni.request({
				url: 'https://api.yonyoucloud.com/apis/dst/APILinkHashEncode/MD5',
				data:{
					text:'13725568256_zhou'
				}
				success(res) {
					console.log(res.data)
				}
			})
		}
	}
</script>

<style lang="scss">
	// .goods_list{
	// 	display: flex;
	// 	flex-direction: row;
	.goods_list {
		padding: 0 15rpx;
		display: flex;
		flex-direction: column;
		justify-content: flex_end;

		align-items:center .goods_item {
			background: #fff;
			width: 355rpx;
			display: flex;
			flex-direction: column;
			margin: 10rpx 0;
			padding: 5rpx 10rpx;
			box-sizing: border-box;
			/* 不让格子撑大 */
			border-radius: 5rpx;

			.img_goods {
				width: 100%;
				height: 170px;
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
				height: 130px;
				margin: 0 auto;
				border-radius: 12rpx;
			}

			.img_goods3 {
				width: 100%;
				height: 190px;
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
					color: #cdcdcd;
				}
			}

			.name {
				overflow: hidden;
				text-overflow: ellipsis;
				/* white-space:nowrap; */
				font-size: 28rpx;
				line-height: 40rpx;
				height: 80rpx;
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

	// }
</style>
