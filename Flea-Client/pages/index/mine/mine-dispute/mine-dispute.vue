<template>
	<view @click="leaveMask()">
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

		<uni-popup ref="reportpop" type="center">
			<view class="complain">
				<view class="id-type">
					<view class="user-id">
						<strong class="title">用户id</strong>
						<input placeholder="输入要举报人的id" v-model="complainUserId" />
					</view>
					<view class="complaintype">
						<strong class="title">标签</strong>
						<checkbox-group @change="checkboxChange">
							<label>
								<checkbox value="商品" color="#FFCC33" style="transform:scale(0.7)" />商品
							</label>
							<label>
								<checkbox value="用户" color="#FFCC33" style="transform:scale(0.7)" />用户
							</label>
						</checkbox-group>
					</view>
				</view>

				<view class="photoView">
					<view class="upload_photo" @longpress="showMask" @click="leaveMask" v-for="(photo,index) in photos" :key="index">
						<image class="upload_img" @click.stop="previewImg(photo)" :src="photo"></image>
						<view v-if="isshow" class="cpt-mask"></view>
						<view class="iconfont icon_del" @click.stop="delIcon(index)" v-if="isshow">&#xe854;</view>
					</view>

					<view class="upload_icon iconfont icon_add" @click="chooseImg">&#xe8a1;</view>
				</view>
				<view class="complain-content">
					<strong class="title">举报内容</strong>
					<textarea placeholder="输入举报内容" v-model="complainContent"></textarea>
				</view>

				<button class="complaining" @click="putComplain">投 诉</button>
			</view>
		</uni-popup>
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

				complainUserId: null, //举报人id
				complainContent: null, //举报内容
				complainType: [], //举报类型
				photos: [], //举报图片
				pictures:[],//存储上传之后的路径
				
				complains: [],


				type: [], //举报类型

				photoFlag: 0,

				/* 图片相关属性 */
				isshow: false, //显示图片的删除图标

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

			/* 跟举报有关的方法 */

			// 设置删除图片
			showMask() {

				this.isshow = true

			},
			leaveMask() {

				this.isshow = false
			},

			// 删除picture
			delIcon(index) {

				this.photos.splice(index, 1)
			},
			
			//选择举报类型
			checkboxChange(e){
				this.complainType=e.detail.value
			},

			// 预览图片
			previewImg(current) {
				const res = getCurrentPages() //获取当前页面层级

				const urls = this.photos.map(item => {
					return item
				})
				uni.previewImage({

					current,
					urls,

					longPressActions: {
						itemList: ["删除"],
						success: (data) => {
							this.photos.splice(data.index, 1)
						},
						fail: function(err) {
							console.log(err.errMsg);
						}
					}
				})
			},

			// 选择图片
			chooseImg() {
				uni.chooseImage({
					count: 9,
					success: (res) => {
						this.photos = this.photos.concat(res.tempFilePaths)
					}
				})
			},

			//上传图片
			// 上传图片到服务器
			async uploadImage(filepath) {

				uni.uploadFile({
					url: 'http://10.19.31.49:8083/register/upload/report_photos',
					filePath: filepath,
					name: 'file',
					formData: {
						'user': 'test'
					},
					success: (uploadFileRes) => {
						// this.pictures.push(uploadFileRes.data)
						// console.log("this.pictures为" + this.pictures);
						if (uploadFileRes.data !== null){
								// this.pictures = this.pictures + "," + uploadFileRes.data
								this.pictures.push(uploadFileRes.data)	
								// console.log("this.pictures为" + this.pictures);
								// this.pictures = this.photos.concat(res.tempFilePaths)
							// if(this.pictures!==null){
							// 	this.pictures=this.pictures+","+uploadFileRes.data
							// }else{
							// 	this.pictures=uploadFileRes.data
							// }
						}
					}
				})
			},

			//上传举报信息
			async putComplain() {
				if (
					this.complainUserId!==null&&
					this.complainContent!==null&&
					this.complainType.length!==0&&
					this.photos.length!==0
				){
					for (this.photoFlag = 0; this.photoFlag < this.photos.length; this.photoFlag++) {
							this.uploadImage(this.photos[this.photoFlag])
					}
					setTimeout(()=>{
						const obj = {
							complain_id:this.$uuid(),
							complain_userid: this.flea_id,
							complained_userid: this.complainUserId,
							complain_content: this.complainContent,
							complain_type: this.complainType.join(","),
							complain_photos: this.pictures.join(","),
						}
						const content="用户:"+this.username+"(跳蚤ID:"+this.flea_id+")  "+"举报了你"
						const result = this.$myRequest({
							url: '/mine/putUserComplain',
							method: "PUT",
							data: {
								complain_id:obj.complain_id,
								complain_userid:obj.complain_userid,
								complained_userid:obj.complained_userid,
								complain_content:obj.complain_content,
								complain_type:obj.complain_type,
								complain_photos:obj.complain_photos,
								email:content
								// complain_id:this.$uuid(),
								// complain_userid: this.flea_id,
								// complained_userid: this.complainUserId,
								// complain_content: this.complainContent,
								// complain_type: this.complainType.join(","),
								// complain_photos: this.pictures.join(","),
							}
						})
						// if(result.data){
							this.complainUserId=null //举报人id
							this.complainContent=null //举报内容
							this.complainType=[]//举报类型
							this.photos=[] //举报图片
							this.pictures=[] //存储上传之后的路径
							this.complains.push(obj)
							this.$refs.reportpop.close()
							
						// }
					},1000)
					/* const content="用户:"+this.username+"(跳蚤ID:"+this.flea_id+")  "+"举报了你"
					// this.sendEmail(content)
					const res = this.$myRequest({
						url:'/notify/sendemail/'+order.userEmail+'/'+content,
						method:"PUT"
					})	 */

					
				}else{
					console.log("hello world")
					uni.showToast({
						icon:'none',
						title:'请将信息填写完整',
					})
				}

					
			},

			openComplain() {
				this.$refs.reportpop.open()
			},

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
	$photolength : 216rpx;

	page {
		background-color: $uni-color-lightgray;
	}

	.disputing {
		width: 100rpx;
		height: 100rpx;
		background-color: $uni-color-blue;
		border-radius: 50%;
		text-align: center;
		font-size: 70rpx;
		color: #fff;
		line-height: 100rpx;
		position: fixed;
		bottom: 40rpx;
		right: 40rpx;
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

	.complain {
		
		width: 700rpx;
		height: 900rpx;
		background-color: #fff;
		padding: 20rpx;
		border-radius: 20rpx;
		display: flex;
		flex-direction: column;
		justify-content: space-evenly;

		.id-type{
			
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			
			.user-id{
				
			}
			
			.complaintype{
				
			}
		}
		.title {}

		.photoView {
			width: 100%;
			display: flex;
			flex-direction: row;
			
			flex-wrap: wrap;

			.upload_photo {
				margin:0 5rpx;
				margin-top: 20rpx;
				position: relative;

				border-radius: 5px;
				height: $photolength;
				width: $photolength;

				background-color: #eee; //transparent
				color: $uni-color-orange;
				font-size: 100rpx;

				.upload_img {
					position: absolute;
					left: 0;
					width: 100%;
					height: 100%;
					border-radius: 5px;
				}

				.icon_del {

					height: 50rpx;
					width: 50rpx;
					position: absolute;
					z-index: 999;
					font-size: 55rpx;
					text-align: center;
					line-height: 50rpx;
					right: 0;
					color: #F88000;


				}

				.cpt-mask {
					position: absolute;
					height: $photolength;
					width: $photolength;
					background-color: #000000;
					opacity: 0.6;
					z-inde: 998
				}
			}

			.upload_icon {

				margin-top: 20rpx;
				position: relative;
				margin-right: 14rpx;
				text-align: center;
				border-radius: 5px;
				height: $photolength;
				width: $photolength;
				line-height: 200rpx;
				background-color: #eee; //transparent
				color: $uni-color-orange;
				font-size: 100rpx;
			}



			.icon_add {
				text-align: center;
				line-height: $photolength;

			}
		}

		.complain-content {

			textarea {
				margin-top: 10rpx;
				width: 95%;
				height: 200rpx;
				background-color: $uni-color-lightgray;
				padding: 10rpx;
				border-radius: 20rpx;
			}
		}

		.complaining {
			width: 150rpx;
			height: 60rpx;
			line-height: 60rpx;
			background-color: $uni-color-orange;
			border-radius: 30rpx;
			color: #fff;

		}
	}
</style>
