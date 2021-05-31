<template>
	<view>
		<view class="complain">
			<view class="evidence">
				<view class="evidence-title">
					<strong class="title">证据</strong>
					<text>聊天截图、商品照片、转账记录等</text>
				</view>
				<view class="photoView">
					<view class="upload_photo" @longpress="showMask" @click="leaveMask" v-for="(photo,index) in photos" :key="index">
						<image class="upload_img" @click.stop="previewImg(photo)" :src="photo"></image>
						<view v-if="isshow" class="cpt-mask"></view>
						<view class="iconfont icon_del" @click.stop="delIcon(index)" v-if="isshow">&#xe854;</view>
					</view>
					<view class="upload_icon iconfont icon_add" @click="chooseImg">&#xe8a1;</view>
				</view>
			</view>
			
			<view class="complain-content">
				<strong class="title">举报内容</strong>
				<textarea placeholder="输入举报内容" v-model="complainContent"></textarea>
			</view>
				
			<button class="complaining" @click="putComplain">投 诉</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				complainUserId: null, //举报人id
				complainContent: null, //举报内容
				complainType: [], //举报类型
				photos: [], //举报图片
				pictures:[],//存储上传之后的路径
				
				/* 图片相关属性 */
				isshow: false, //显示图片的删除图标
				
				photoFlag: 0,
				
				clickJudge:true,
			}
		},
		onLoad(options) {
			this.flea_id=options.flea_id
			this.username=options.username
			this.violatorId=options.violatorId
			this.goodsId=options.goodsId
			this.complain_type=options.complain_type
			this.order_id=options.order_id
			
			console.log(this.flea_id)
			console.log(this.violatorId)
			console.log(this.goodsId)
			console.log(this.complain_type)
			console.log(this.order_id)
		},
		methods: {
			
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
					url: 'http://127.0.0.1:8083/register/upload/report_photos',
					filePath: filepath,
					name: 'file',
					formData: {
						'user': 'test'
					},
					success: (uploadFileRes) => {
						if (uploadFileRes.data !== null){
							this.pictures.push(uploadFileRes.data)	
						}
					}
				})
			},
			
			//上传举报信息
			async putComplain() {
				if(this.clickJudge){
					this.clickJudge=false
					if (
						this.complainContent!==null&&
						this.photos.length!==0
					){
						for (this.photoFlag = 0; this.photoFlag < this.photos.length; this.photoFlag++) {
								this.uploadImage(this.photos[this.photoFlag])
						}
						setTimeout(()=>{
							const content="用户:"+this.username+"(跳蚤ID:"+this.flea_id+")  "+"举报了你"
							const result = this.$myRequest({
								url: '/mine/putUserComplain',
								method: "PUT",
								data: {
									complain_id:this.$uuid(),
									order_id:this.order_id,
									goods_id:this.goodsId,
									complain_userid:this.flea_id,
									complained_userid:this.violatorId,
									complain_content:this.complainContent,
									complain_type:this.complain_type,
									complain_photos:this.pictures.join(","),
									email:content
								}
							})
							if(result){
								uni.showToast({
									icon:'none',
									title:'举报成功',
									duration:2000
								})
								setTimeout(()=>{
									
									uni.navigateBack({
										delta:1
									})
									
								},2000)
							}
								
					
						},1000)				
					}else{
						console.log("hello world")
						uni.showToast({
							icon:'none',
							title:'请将信息填写完整',
						})
					}
								
				}
				
					
			},
			
		}
	}
</script>

<style lang="scss">
	$photolength : 216rpx;
	.complain {
		
		width: 700rpx;
		height: 900rpx;
		background-color: #fff;
		padding: 20rpx;
		border-radius: 20rpx;
		display: flex;
		flex-direction: column;
		
	
		.title {}
	
		.evidence{
			display: flex;
			flex-direction: column;
			margin-top: 20rpx;
			.evidence-title{
				display: row;
				flex-direction: row;
				text{
					font-size: 20rpx;
					color: $uni-color-midgray;
					margin-left: 30rpx;
				}
			}
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
		}
		
	
		.complain-content {
			margin-top: 20rpx;
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
			margin-top: 20rpx;
	
		}
	}

</style>
