<template>
	<view >
		<view class="disputing iconfont" @click="openComplain">&#xe8a1;</view>


		<view class="dispute-status">
			<!-- 待处理订单 -->
			<view class="dispute-todo" @click="getToDo('todo')" :style="{color:todobg,borderBottom:todoborder}">
				<view class="iconfont todo">&#xe892;</view>
				<text>待处理</text>
			</view>
			<!-- 历史订单 -->
			<view class="dispute-history" @click="getHistory('history')" :style="{color:historybg,borderBottom:historyborder}">
				<view class="iconfont history">&#xe786;</view>
				<text>历史</text>
			</view>
		</view>

		<dispute-list class="complains-list" :flea_id='flea_id' :complains="complains"
		@goToComplainDetail="goToComplainDetail"
		@delComplain="delComplain" >
			
		</dispute-list>

	</view>
</template>

<script>
	import disputeList from '../../../../components/dispute-list.vue'
	export default {
		data() {
			return {
				flea_id: null,

				todobg: '#F88000',
				todoborder: '1px solid #ccc',
				historybg: '#ccc',
				historyborder: '1px solid #fff',

				orderStatus: 'todo',
				dealType: null,
				pagenum: 1, //当前页数
				flag: false,

				disputes: [],
				
				complains: [],


			}
		},
		components: {
			disputeList
		},
		onLoad(options) {
			this.flea_id = options.flea_id
			this.username = options.username
			this.getComplains('todo')
		},
		methods: {


			getToDo(orderStatus) {
				this.complains=[]
				
				this.orderStatus = 'todo'

				this.todobg = '#F88000';
				this.historybg = '#ccc';
				this.historyborder = '1px solid #fff'
				this.todoborder = '1px solid #ccc'

				console.log(this.todoborder, this.historyborder)

				this.disputes = []
				this.pagenum = 1
				this.getComplains(orderStatus)
			},

			getHistory(orderStatus) {
				this.complains=[]
				this.orderStatus = 'history'

				this.historybg = '#F88000';
				this.todobg = '#ccc';
				this.todoborder = '1px solid #fff'
				this.historyborder = '1px solid #ccc'

				console.log(this.todoborder, this.historyborder)

				this.disputes = []
				this.pagenum = 1
				this.getComplains(orderStatus)
			},

			async getComplains(orderStatus) {
				const result = await this.$myRequest({
					url: '/mine/getUserComplain/' + this.flea_id+'/'+orderStatus,
				})
				this.complains = result.data
			},
		
			/* 跳转到举报单详情页 */
			goToComplainDetail(complain_id,status){
				uni.navigateTo({
					url:'/pages/index/mine/mine-dispute/complain-detail/complain-detail?complain_id='+complain_id+'&status='+status
				})
			},
			/* 删除举报单 */
			delComplain(complain_id,index){
				console.log("index为："+index)
				uni.showModal({
					title:'提示',
					content:'是否取消举报？',
					cancelText:'取消',
					confirmText:'确定',
					success: (res) => {
						if(res.confirm){
							const result = this.$myRequest({
								url:'/mine/deleteComplain/'+complain_id,
								method:'DELETE'
							})
							// if(result.data){
								this.complains.splice(index,1)
								uni.showToast({
									title:'删除成功',
									icon:'none'
								})
							// }
							
						}
					}
				})	
			}
		}
	}
</script>

<style lang="scss">

	page {
		background-color: $uni-color-lightgray;
	}


	
	.complains-list{
		margin-top:200rpx ;
	}

	.dispute-status {
		background-color: #fff;
		display: flex;
		flex-direction: row;
		padding-bottom: 20rpx;
		position: fixed;
		top: 91rpx;
		width: 100%;

		.dispute-todo {
			width: 50%;
			text-align: center;

			// border-bottom: 1px solid transparency;
			.todo {
				font-size: 60rpx;
			}
		}

		.dispute-history {
			text-align: center;
			width: 50%;

			// border-bottom: 1px solid $uni-color-midgray;
			.history {
				font-size: 60rpx;
			}
		}
	}
</style>
