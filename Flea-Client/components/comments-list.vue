<template>
	<view class="messages">
		<view class="message" v-for="(message,index) in messages" :key="index" @click="goGoodsDetail(message,index)">
			<view class="message-tag">
				<view class="user-info">
					<view v-if="message.view_status===0" class="remind-icon"></view>
					<strong class="message-username">{{message.observer_username}}</strong>
					<text>对你的商品留言</text>
					
				</view>
				<view class="message-putdate">{{message.put_time|formatDate('-')}}</view>
			</view>
			<view class="message-content">{{message.comment}}</view>
		</view>
	</view>
</template>

<script>
	
	export default {
		props:['messages'],
		data() {
			return {
				
			};
		},
		filters: {
			// 时间戳处理
			formatDate: function(value, spe = '/') {
				let time = Math.floor(((new Date()).valueOf() - value) / 86400000)
				if (time === 0) {
					let time2 = Math.round(((new Date()).valueOf() - value) / 3600000)
					if (time2 > 0) {
						return time2 + '小时前'
					} else {
						let time3=((new Date()).valueOf() - value) / 60000
						if(time3>1){
							return Math.ceil(time3) + '分钟前'
						}else{
							return Math.ceil(((new Date()).valueOf() - value)/1000)+"秒前"
						}					
					}
				} else if (time > 0 && time < 30) {
					return time + '天前'
				} else if (time >= 30) {
					let data = new Date(value);
					let year = data.getFullYear();
					let month = data.getMonth() + 1;
					let day = data.getDate();
					let h = data.getHours();
					let mm = data.getMinutes();
					let s = data.getSeconds();
					month = month > 10 ? month : "0" + month;
					day = day > 10 ? day : "0" + day;
					return `${year}${spe}${month}${spe}${day}`;
				}
			},
		},
		methods:{
			goGoodsDetail(message,index){
				console.log("index为",index)
				this.$emit('goGoodsDetail',message,index)
			}
		}
	}
</script>

<style lang="scss">
	.messages{
		width: 93%;
		margin: 0 auto;
		
		.message{
			background-color: #fff;
			border-radius: 20rpx;
			margin-top: 20rpx;
			padding: 10rpx;
			.message-tag{
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-bottom: 10rpx;
				.user-info{
					display: flex;
					flex-direction: row;
					align-items: center;
					.remind-icon{
						border-radius: 50%;
						width: 10rpx;
						height: 10rpx;
						margin-right: 10rpx;
						background-color: red;
					}
					
					.message-username{
						color: $uni-color-blue;
						margin-right: 30rpx;
					}
					text{
						white-space :nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}
					
				}
				
				.message-putdate{
					
				}
			}
			.message-content{
				margin-top: 5rpx;
				border-top: 1px solid $uni-color-midgray;
			}
		}
	}
</style>
