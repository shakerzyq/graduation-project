<template>
	<view>
		<product-info @delIcon="delIcon" class="product_info" :aClass="aclass" :bClass="bclass" :cClass="cclass" fromType="addProduct"
		 :addInfo="add_info"></product-info>
		<view class="update-operation">
			<view class="delete" @click="deleteProduct">删除</view>
			<view class="put" @click="putProduct">修改</view>
		</view>

	</view>
</template>

<script>
	import productInfo from '../../../components/productInfo.vue'

	export default {
		data() {
			return {
				test: null,
				isget: false, //标识 
				add_info: {
					description: null,
					photos: [],
					quality_choice: null, //选中的品质
					brand: null, //选中的品牌
					qualities: [{
							name: '全新',
							choice: false,
							bgcolor: '#eee'
						},
						{
							name: '9成新',
							choice: false,
							bgcolor: '#eee'
						},
						{
							name: '轻微使用痕迹',
							choice: false,
							bgcolor: '#eee'
						},
						{
							name: '明显使用痕迹',
							choice: false,
							bgcolor: '#eee'
						},
						{
							name: '部分有磨损',
							choice: false,
							bgcolor: '#eee'
						}
					],
					brand: null,
					atype: null,
					btype: null,
					ctype: null,
					nowprice: null,
					oldprice: null,
					atypeTag: null, //记录atype的索引位置
					btypeTag: null, //记录btype的索引位置
					ctypeTag: null, //记录ctype的索引位置
				},
				add_info2: this.add_info, //add_info 的备份用于刷新操作
				aclass: [],
				bclass: [],
				cclass: [],
				i: 0,
				obj: null,
				pictures: [],
				flea_id: null, //跳蚤id
				class_choice: null,
				arr: null,
				add_info_judge: false, //判断add_info中的属性是全不为null
				photoFlag: 0, //遍历photos的标签
				newPhotos: [], //存储新上传的图片
			}
		},
		components: {
			productInfo,
		},
		onLoad: function(options) {
			this.goodsId = options.goodsId
			uni.request({
				url: 'http://1.15.138.161:9200/types_a/_search',
				// url:'http://1.15.138.161:9200/bank/account/1',
				method: "POST",
				data: {
					"query": {
						"match_all": {}
					},
					"from": 0,
					"size": 20
				},
				success: (res) => {
					this.aclass = res.data.hits.hits
					// const list = res.data.hits.hits

					// console.log("你好"+list.length)
					// list.forEach((a)=>{
					// 	this.obj={
					// 		'type':a._source.atype,
					// 		'choice':false,
					// 		'bgcolor':'#eee'
					// 	}
					// 	this.aclass[this.i]=this.obj
					// 	// this.aclass[this.i].type=
					// 	// this.aclass[this.i].choice=false
					// 	// this.aclass[this.i].bgcolor='#eee'
					// 	this.i++
					// 	console.log(this.aclass[this.i].type)
					// })
					// console.log("你好"+this.aclass.length)
					// this.i=0
					// this.obj=null
				}
			})
			this.getGoodsInfo()

			// uni.$on('add_info',(e)=>{
			// 	this.add_info.description = e.description
			// 	this.add_info.photos=e.photos
			// 	this.add_info.quality_choice = e.quality_choice
			// 	this.add_info.qualities = e.qualities
			// 	this.add_info.classes = e.classes
			// 	this.add_info.nowprice = e.nowprice
			// 	this.add_info.oldprice = e.oldprice
			// 	this.add_info.brand = e.brand
			// 	this.add_info.atype=e.atype
			// 	this.add_info.btype=e.btype
			// 	this.add_info.ctype=e.ctype
			// })
			console.log("quality为：" + this.add_info.quality_choice)
			uni.getStorage({
				key: 'flea_id',
				success: (res) => {
					this.flea_id = res.data
				}
			});
			// uni.getStorage({
			// 	key:'add_info2',
			// 	success:(res)=> {
			// 		this.add_info.description = res.data.description
			// 		this.add_info.photos=res.data.photos
			// 		this.add_info.quality_choice = res.data.quality_choice
			// 		this.add_info.qualities = res.data.qualities
			// 		this.add_info.brand = res.data.brand
			// 		this.add_info.atype=res.data.atype
			// 		this.add_info.btype=res.data.btype
			// 		this.add_info.ctype=res.data.ctype
			// 		this.add_info.nowprice = res.data.nowprice
			// 		this.add_info.oldprice = res.data.oldprice
			// 		this.add_info.title = res.data.title
			// 	}
			// })

			//查询商品信息，用于渲染商品修改页面



			// if(this.add_info.atype===null||this.add_info.ctype===null){
			// 	this.getTypesB()
			// 	this.getTypesC()
			// }
			//this.getTypesA()





		},
		onShow() {
			console.log(this.add_info.photos)
		},
		onUnload() {
			uni.$off('add_info')
		},
		/**
		 * 监听刷新页面
		 */
		onPullDownRefresh() {
			// this.add_info=this.add_info2
			this.add_info.qualities = [{
						name: '全新',
						choice: false,
						bgcolor: '#eee'
					},
					{
						name: '9成新',
						choice: false,
						bgcolor: '#eee'
					},
					{
						name: '轻微使用痕迹',
						choice: false,
						bgcolor: '#eee'
					},
					{
						name: '明显使用痕迹',
						choice: false,
						bgcolor: '#eee'
					},
					{
						name: '部分有磨损',
						choice: false,
						bgcolor: '#eee'
					}
				],
				this.getGoodsInfo(() => {
					uni.stopPullDownRefresh()
				})
		},
		onBackPress() {
			//如果有值
			// if(!this.isget){
			// 	uni.showModal({
			// 		content:"是否要保存为草稿？",
			// 		cancelText:"不保存",
			// 		confirmText:"保存",
			// 		success: res => {
			// 			if(res.confirm){
			// 				console.log("保存")
			// 				uni.setStorage({
			// 					key: 'add_info2',
			// 					data: {
			// 						"title":this.add_info.title,
			// 						"description":this.add_info.description,
			// 						"photos":this.add_info.photos,
			// 						"quality_choice":this.add_info.quality_choice,
			// 						"qualities":this.add_info.qualities,
			// 						"brand":this.add_info.brand,
			// 						"atype":this.add_info.atype,
			// 						"btype":this.add_info.btype,
			// 						"ctype":this.add_info.ctype,
			// 						"nowprice":this.add_info.nowprice,
			// 						"oldprice":this.add_info.oldprice,

			// 					},
			// 					success() {
			// 						console.log('商品信息存储成功')
			// 					}
			// 				})
			// 			}else{
			// 				console.log("不保存")
			// 			}
			// 			this.isget=true;
			// 			uni.navigateBack({
			// 				delta:1
			// 			});
			// 		},
			// 	});
			// 	return true;
			// }
		},
		methods: {

			// 删除picture
			delIcon(index) {

				this.add_info.photos.splice(index, 1)
				this.pictures.splice(index, 1)
			},

			async getGoodsInfo(callBack) {
				const res = await this.$myRequest({
					url: '/product/update/getGoods/' + this.goodsId
				})
				// this.add_info=result.data
				console.log("获取到的结果为" + res.data)
				this.add_info.description = res.data.product_des
				this.pictures = res.data.photos.split(',')
				this.add_info.photos = res.data.photos.split(',')
				console.log("photos为：" + res.data.photos)
				this.add_info.quality_choice = res.data.quality
				// this.add_info.qualities = res.data.qualities
				this.add_info.qualities.forEach((a) => {
					if (a.name === res.data.quality) {
						console.log("quality匹配成功：a为" + a)
						a.bgcolor = '#F88000'
						a.choice = true
					}
				})
				this.add_info.brand = res.data.brand
				this.add_info.atype = res.data.atype
				this.add_info.btype = res.data.btype
				this.add_info.ctype = res.data.ctype
				this.add_info.nowprice = res.data.now_price
				this.add_info.oldprice = res.data.old_price
				this.add_info.title = res.data.product_title

				if ((typeof this.add_info.atype) === 'undefined' || this.add_info.atype === null) {
					console.log("1")
					this.getTypesA2()
				} else {
					console.log("2:" + this.add_info.atype)
					this.drawTypes()
				}

				callBack && callBack() //通过&&判断，有callback就调用，没有就不调用
			},
			//检测是否存在type，存在就渲染
			drawTypes() {

				if (this.add_info.atype) {
					this.getTypesA()
				}
				if (this.add_info.btype) {
					this.getTypesB(this.add_info.atype)
				}
				if (this.add_info.ctype) {
					this.getTypesC(this.add_info.btype)
				}
			},

			//获取es数据
			async getTypesA2() {
				const atypes = await this.$myRequest({
					url: '/elasticsearch/getTypesA'
				})
				this.aclass = atypes.data
			},
			async getTypesA() {
				const atypes = await this.$myRequest({
					url: '/elasticsearch/getTypesA'
				})
				this.aclass = atypes.data
				this.add_info.atypeTag = 0
				this.aclass.forEach((a) => {
					console.log("aclass中的" + a.type)
					if (a.type === this.add_info.atype) {
						a.choice = true
						a.bgcolor = '#F88000'
						throw Error
					}
					this.add_info.atypeTag++
				})

			},
			//查询btype
			async getTypesB(atype) {
				const result = await this.$myRequest({
					url: '/elasticsearch/getTypesB/' + atype,
				})
				this.bclass = result.data
				this.add_info.btypeTag = 0
				this.bclass.forEach((a) => {
					if (a.type === this.add_info.btype) {
						a.choice = true
						a.bgcolor = '#F88000'
						throw Error
					}
					this.add_info.btypeTag++
				})
			},
			//查询ctype
			async getTypesC(btype) {
				const result = await this.$myRequest({
					url: '/elasticsearch/getTypesC/' + btype,
				})
				this.cclass = result.data
				this.add_info.ctypeTag = 0
				console.log("获取到的ctype为" + this.add_info.ctype)
				this.cclass.forEach((a) => {
					if (a.type === this.add_info.ctype) {
						a.choice = true
						a.bgcolor = '#F88000'
						throw Error
					}
					this.add_info.ctypeTag++
				})
			},

			// 上传图片到服务器
			uploadImage(filepath, index) {
				if (filepath.indexOf('blob') >= 0) {
					uni.uploadFile({
						url: 'http://127.0.0.1:8083/register/upload/goods_photos',
						filePath: filepath,
						name: 'file',
						formData: {
							'user': 'test'
						},
						success: (uploadFileRes) => {
							/* if(type===0){
								this.user.user_icon=uploadFileRes.data//上传的是头像
							}else{
								this.user.testify_img = uploadFileRes.data//上传的是证明照片
							} */
							if (uploadFileRes.data !== null) {
								// this.pictures = this.pictures + "," + uploadFileRes.data
								this.pictures.push(uploadFileRes.data)
							}

							// if (this.pictures !== null) {
							// 	this.pictures = this.pictures + "," + uploadFileRes.data
							// } else {
							// 	this.pictures = this.+uploadFileRes.data
							// }

							// this.add_info.photos[index]=uploadFileRes.data


							console.log("this.pictures为" + this.pictures);
						}
					})
				}

			},

			async putProductInfo() {
				const result = await this.$myRequest({
					url: '/product/update',
					data: {
						product_id: this.goodsId,
						merchant_id: this.flea_id,
						product_title: this.add_info.title,
						product_des: this.add_info.description,
						quality: this.add_info.quality_choice,
						brand: this.add_info.brand,
						atype: this.add_info.atype,
						btype: this.add_info.btype,
						ctype: this.add_info.ctype,
						now_price: this.add_info.nowprice,
						old_price: this.add_info.oldprice,
						photos: this.pictures.join(","),
						views: 0
					},
					method: "POST"
				})
				if (result.data) {
					uni.showToast({
						icon: "none",
						title: '修改成功',
						duration: 2000
					})
				}
			},

			putProduct() {

				console.log(this.add_info.description, this.add_info.brand, this.add_info.atype, this.add_info.btype, this.add_info
					.ctype, this.add_info.atypeTag, this.add_info.btypeTag, this.add_info.ctypeTag, this.add_info.photos,
					this.add_info.quality_choice,
					this.add_info.qualities,
					this.add_info.nowprice,
					this.add_info.oldprice)
				// if ((this.arr = Object.keys(this.add_info).filter(v=>!this.add_info[v]))===null) {
				//   this.add_info_judge=true
				// }else{
				//   this.add_info_judge=false
				// }
				if (this.add_info.description === null ||
					this.add_info.brand === null ||
					this.add_info.atype === null ||
					this.add_info.btype === null ||
					this.add_info.ctype === null ||
					this.add_info.atypeTag === null ||
					this.add_info.btypeTag === null ||
					this.add_info.ctypeTag === null ||
					this.add_info.photos === null ||
					this.add_info.quality_choice === null ||
					this.add_info.qualities === null ||
					this.add_info.nowprice === null ||
					this.add_info.oldprice === null

				) {
					this.add_info_judge = false
				} else {
					this.add_info_judge = true
				}
				if (!this.add_info_judge) {
					this.class_choice = null
					console.log("选中的品质为：" + this.add_info.quality_choice)
					uni.showToast({
						title: "请将信息填写完整",
						icon: 'none',
						duration: 2000
					})
				} else {
					for (this.photoFlag = 0; this.photoFlag < this.add_info.photos.length; this.photoFlag++) {
						this.uploadImage(this.add_info.photos[this.photoFlag], this.photoFlag)
					}
					this.isget = true
					console.log("最后检查图片" + this.pictures)

					this.putProductInfo()

					setTimeout(() => {
						uni.navigateBack();
					}, 2000)


					//移除记录
					// uni.removeStorage({
					// 	key: 'add_info2',
					// 	success: function(res) {}
					// })
					//页面跳转，返回
					// uni.navigateBack();
				}
			},

			deleteProduct() {
				uni.showModal({
					title: '警告',
					content: '确定删除？',
					cancelText: "取消",
					confirmText: "确定",
					success: (res) => {
						if (res.confirm) {
							const result = this.$myRequest({
								url: '/product/delete/' + this.goodsId
							})

							uni.navigateBack({
								delta: 2
							})

						} else if (res.cancel) {

						}
					}
				});

			}

		}
	}
</script>

<style lang="scss">
	.update-operation {
		position: fixed;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		bottom: 30rpx;
		right: 30rpx;
		width: 690rpx;

		.put {
			border-radius: 20rpx;
			background-color: $uni-color-blue;
			width: 140rpx;
			text-align: center;
			padding: 10rpx;
			color: #fff;
		}

		.delete {
			border-radius: 20rpx;
			background-color: orange;
			width: 140rpx;
			text-align: center;
			padding: 10rpx;
			color: #fff;
		}
	}
</style>
