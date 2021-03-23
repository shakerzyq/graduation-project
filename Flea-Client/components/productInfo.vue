<!-- 修改和发布商品的模板 -->
<template>
	<view class="product_info" @click="leaveMask()">
		<view class="father-box">
			<input class="title" v-model="add_info.title" placeholder="标题,简短的写出商品信息,例如:康师傅方便面"></input>
			<textarea class="description" placeholder="说说转手原因,使用感受,入手渠道..." v-model="add_info.description"></textarea>
			<view class="photo-area">
				<view class="headline2">
					<view class="price_title">
						<view class="type_icon iconfont">&#xe855;</view>
						<view class="title1">图片</view>
					</view>
					<view class="classify_icon" @click="open_photoarea">
						<view class="iconfont icon" v-if="photo_icon_style">&#xe8a4;</view>
						<view v-else class="iconfont icon">&#xe748;</view>
					</view>
				</view>
			</view>

			<!-- 图片 -->
			<view class="photoView" v-if="photo_area_status">
				<view class="upload_photo" @longpress="showMask" @click="leaveMask" v-for="(photo,index) in add_info.photos" :key="index">
					<image class="upload_img" @click.stop="previewImg(photo)" :src="photo"></image>
					<view v-if="isshow" class="cpt-mask"></view>
					<view class="iconfont icon_del" @click.stop="delIcon(index)" v-if="isshow">&#xe854;</view>
				</view>
				
				<view class="upload_icon iconfont icon_add" @click="chooseImg">&#xe8a1;</view>
			</view>
			
			
			<view class="attribute">
				<view class="headline">
					<view class="classify_title">
						<view class="type_icon iconfont">&#xe81c;</view>
						<view class="title1">分类/成色</view>
					</view>

					<view class="classify_icon" @click="open_setclassify">
						<view class="iconfont icon" v-if="class_icon_style">&#xe8a4;</view>
						<view v-else class="iconfont icon">&#xe748;</view>
					</view>
				</view>
				<view v-if="setclassify_status">
					<view class="classify">
						<text class="title_class">成色</text>

						<scroll-view scroll-x="true" class="content_class">
							<text :style="{backgroundColor:quality.bgcolor}" v-for="(quality,index) in add_info.qualities" :key="index" @click="selectQuality(quality)">{{quality.name}}</text>
						</scroll-view>
					</view>
					<!-- 分类 -->
					<view class="classify">
						<text class="title_class">主类</text>
						<scroll-view scroll-x="true" class="content_class">
							<!-- 设置diy分类标签 -->
							<!-- <text  class="iconfont first" @click="diyClass">&#xe8a1;</text> -->
							<!-- 遍历分类标签数组 -->
							<text :style="{backgroundColor:item.bgcolor}" :class="class_style" v-for="(item,index) in aclass" :key="index"
							 @click="addTypeA(item,index)">{{item.type}}</text>
						</scroll-view>
						<!-- <view v-if="diyClass_status" class="traingle"></view> -->
					</view>
					<view class="classify" v-if="bclass.length===0?false:true">
						<text class="title_class">次类</text>
						<scroll-view scroll-x="true" class="content_class">
							<!-- 设置diy分类标签 -->
							<!-- <text  class="iconfont first" @click="diyClass">&#xe8a1;</text> -->
							<!-- 遍历分类标签数组 -->
							<text :style="{backgroundColor:item.bgcolor}" :class="class_style" v-for="(item,index) in bclass" :key="index"
							 @click="addTypeB(item,index)">{{item.type}}</text>
						</scroll-view>
						<!-- <view v-if="diyClass_status" class="traingle"></view> -->
						
					</view>
					<view class="classify" v-if="cclass.length===0?false:true">
						<text class="title_class">终类</text>
						<scroll-view scroll-x="true" class="content_class">
							<!-- 设置diy分类标签 -->
							<!-- <text  class="iconfont first" @click="diyClass">&#xe8a1;</text> -->
							<!-- 遍历分类标签数组 -->
							<text :style="{backgroundColor:item.bgcolor}" :class="class_style" v-for="(item,index) in cclass" :key="index"
							 @click="addTypeC(item,index)">{{item.type}}</text>
						</scroll-view>
						<!-- <view v-if="diyClass_status" class="traingle"></view> -->
						
					</view>
					
					
					<!-- 品牌 -->
					<view class="brand">
						<text class="title-class">品牌</text>
						<input class="input-class" type="text" placeholder="输入品牌" v-model="add_info.brand"/>
						
					</view>
					<!-- 自定义添加区域 -->
					
				</view>

				<view class="headline2">
					<view class="price_title">
						<view class="type_icon iconfont">&#xe883;</view>
						<view class="title1">价格</view>
					</view>
					<view class="price_title2" @click="open_setprice">
						<view >￥{{add_info!=null?add_info.nowprice:price_show}}</view>
						<view class="iconfont icon" v-if="price_icon_style">&#xe8a4;</view>
						<view v-else class="iconfont icon">&#xe748;</view>
					</view>
				</view>
				<view v-if="setprice_status" :class="inputprice_class">
					<view class="PopPrice">
						<text>价格</text>
						<view>￥</view>
						<input @input="monitor_price" type="number" placeholder="0.00" foucs  v-model="add_info.nowprice"/>
					</view>
					<view class="PopPrice">
						<text>入手价</text>
						<view>￥</view>
						<input  type="number"  placeholder="0.00" v-model="add_info.oldprice" />
					</view>
				</view>

			</view>

		</view>
		<view class="blank"></view>
		
		
	</view>
</template>

<script>
	import uniPopup from '@/uni_modules/uni-popup/components/uni-popup/uni-popup.vue'
	import uniPopupMessage from '@/uni_modules/uni-popup/components/uni-popup-message/uni-popup-message.vue'
	import uniPopupDialog from '@/uni_modules/uni-popup/components/uni-popup-dialog/uni-popup-dialog.vue'
	import diyMask from '../components/mask.vue'
	export default {
		
		props:['addInfo','aClass','bClass','cClass'],
		data() {
			return {	
				price_show:'0.00',//用于让标题表现的价格
				ShowPop: true,
				inputprice_class: 'hidden_input',
				setprice_status: false,
				setclassify_status: true,
				photo_area_status: true,
				setprice_click_time: 0,
				setclassify_click_time: 0,
				setphoto_click_time: 0,
				price_icon_style: false,
				class_icon_style: true,
				photo_icon_style: true,
				bclass:this.bClass,
				cclass:this.cClass,
				aclass:this.aClass,
				add_info:this.addInfo,
				//类型相关
				diyClass_status:false,//是否显示自定义类的装饰角
				diyClassContent:null,//绑定自定义类输入的内容
				
				diybrand_content:null,//绑定自定义class
				isshow:false,//显示图片的删除图标
				class_style: 'class_unchoiced_style',
				
				//atypeTag:null,//用于存储上一个atype的index，将其变为灰色，实现单选
				//btypeTag:null,//用于存储上一个btype的index，将其变为灰色，实现单选
				//ctypeTag:null,//用于存储上一个ctype的index，将其变为灰色，实现单选
		

			}
		},
		onLoad:function(){
			console.log("组件中的info为："+this.add_info.nowprice)
			this.price_show=this.add_info.nowprice
		},
		components: {
			uniPopup,
			uniPopupMessage,
			uniPopupDialog,
			diyMask
		},
		watch: {
			// 正确给 cData 赋值的 方法
			aClass:{
				immediate: true,
				handler(newVal){ 
					console.log("hello world handler")
					//this.add_info2 = newVal; 
					if(newVal) {//如果数据变化则重新创建数据用于渲染 
						// this.drawTypes()//处理数据 
						this.aclass=this.aClass
						console.log("hello world handler")
					} 
				},
			},
			bClass:{
				immediate: true,
				handler(newVal){ 
					console.log("hello world handler")
					//this.add_info2 = newVal; 
					if(newVal) {//如果数据变化则重新创建数据用于渲染 
						// this.drawTypes()//处理数据 
						this.bclass=this.bClass
						console.log("hello world handler")
					} 
				},
			},
			cClass:{
				immediate: true,
				handler(newVal){ 
					console.log("hello world handler")
					//this.add_info2 = newVal; 
					if(newVal) {//如果数据变化则重新创建数据用于渲染 
						// this.drawTypes()//处理数据 
						this.cclass=this.cClass
						console.log("hello world handler")
					} 
				},
			}
		},
		
		

		methods: {
			// drawTypes(){	
			// 	if(this.add_info.atype){
			// 		this.aclass= that.getTypesB()
			// 		this.add_info.atypeTag=0
			// 		this.aclass.forEach((a)=>{
			// 			if(a.type===that.add_info.atype){
			// 				a.choice=true
			// 				a.bgcolor='#F88000'
			// 				throw Error
			// 			}
			// 			this.add_info.atypeTag++
			// 		})
			// 	}
			// 	if(that.add_info.btype){
			// 		this.bclass= that.getTypesB()
			// 		this.add_info.btypeTag=0
			// 		this.bclass.forEach((a)=>{
			// 			if(a.type===that.add_info.btype){
			// 				a.choice=true
			// 				a.bgcolor='#F88000'
			// 				throw Error
			// 			}
			// 			this.add_info.btypeTag++
			// 		})
			// 	}
			// 	if(that.add_info.ctype){
			// 		this.cclass = that.getTypesC()
			// 		this.add_info.ctypeTag=0
			// 		this.cclass.forEach((a)=>{
			// 			if(a.type===that.add_info.ctype){
			// 				a.choice=true
			// 				a.bgcolor='#F88000'
			// 				throw Error
			// 			}
			// 			this.add_info.ctypeTag++
			// 		})
					
			// 	}
				
				
			// },
			
			getaddinfo(){
				uni.showToast({
					title:"hello world"
				})
			},
			
			// 设置删除图片
			showMask() {
				
				this.isshow = true
				
			},
			leaveMask(){
				
				this.isshow=false
			},
			
			delIcon(index){
				this.$emit('delIcon',index)
			},
			
			// 监听价格
			monitor_price(e){	
				if(!this.add_info.nowprice){
					this.price_show ='0.00'
					
				}else{
					this.price_show = this.add_info.nowprice
					
				}
				
				// uni.$emit('add_info2', {
				// 	add_info2:this.add_info
				// })
			},

			//diy分类
			diyClass() {
				if(!this.diyClass_status){
					this.diyClass_status=true
				}else{
					this.diyClass_status=false
				}
			},
			
			//添加自定义类的事件
			diyBrand_add(){
				if(this.diyClassContent){
					
					this.diyClass_status=false
					const obj = {
						name:this.diyClassContent,
						choice:true,
						bgcolor:'#F88000'
					}
					// this.add_info.classes.push(this.diyClassContent)
					// this.add_info.brand=this.diyBrand_content
					
					// this.add_info.classes_choiced=this.add_info.classes_choiced.push(this.diyclass_content)
					this.diyClassContent=null
					
				}else{
					uni.showToast({
						title:'不能为空',
						icon:'none',
						position:'bottom',
						duration:2000
					})
				}
			},
			// diyClass_add(){
			// 	if(this.diyclass_content){
			// 		const obj = {
			// 			name:this.diyclass_content,
			// 			choice:true,
			// 			bgcolor:'#F88000'
			// 		}
			// 		this.add_info.classes.unshift(obj)
			// 		// this.add_info.classes_choiced=this.add_info.classes_choiced.push(this.diyclass_content)
			// 		this.diyclass_content=null
			// 	}else{
			// 		uni.showToast({
			// 			title:'不能为空',
			// 			icon:'none',
			// 			position:'bottom',
			// 			duration:2000
			// 		})
			// 	}
				
			// },
			// 设置成色
			selectQuality(item) {
				if (!item.choice) {
					this.add_info.qualities.forEach((quality) => {
						
						if (quality.choice===true) {
							quality.choice = false
							quality.bgcolor = '#eee'
						}
					})
					this.add_info.quality_choice = item.name
					item.bgcolor = '#FF8000'
					item.choice = true
				}
			},
			//设置分类
			
			addTypeA(item,index) {
				console.log('点击了设置'+item.choice+"为")
				this.add_info.btype=null
				this.add_info.ctype=null
				if (!item.choice) {
					console.log("index为："+index)
					console.log("atypeTag为："+this.add_info.atypeTag)
					item.bgcolor = '#FF8000'
					item.choice = true
					
					if(this.add_info.atypeTag!==null){
						console.log("被转变者")
						this.aclass[this.add_info.atypeTag].bgcolor='#eee'
						this.aclass[this.add_info.atypeTag].choice=false
					}
					this.add_info.atype=item.type
					this.add_info.atypeTag=index
					//通过这个选择的值查询btype
					this.getTypesB(item.type);
					
				} 
			},
			//查询btype
			async getTypesB(atype){
				const result = await this.$myRequest({
					url:'/elasticsearch/getTypesB/'+atype,
				})
				this.bclass=result.data
			},
			addTypeB(item,index){
				this.add_info.ctype=null
				console.log('点击了设置'+item.choice+"为")
				
				if (!item.choice) {
					console.log("index为："+index)
					console.log("atypeTag为："+this.atypeTag)
					item.bgcolor = '#FF8000'
					item.choice = true
					
					if(this.add_info.btypeTag!==null){
						console.log("被转变者")
						this.bclass[this.add_info.btypeTag].bgcolor='#eee'
						this.bclass[this.add_info.btypeTag].choice=false
					}	
					this.add_info.btype=item.type
					this.add_info.btypeTag=index
					//通过这个选择的值查询btype
					this.getTypesC(item.type);
					
				} 
			},
			//查询ctype
			async getTypesC(btype){
				const result = await this.$myRequest({
					url:'/elasticsearch/getTypesC/'+btype,	
				})
				this.cclass=result.data
			},
			addTypeC(item,index){
				console.log('点击了设置'+item.choice+"为")
				
				if (!item.choice) {
					console.log("index为："+index)
					console.log("atypeTag为："+this.ctypeTag)
					item.bgcolor = '#FF8000'
					item.choice = true
					
					if(this.add_info.ctypeTag!==null){
						console.log("被转变者")
						this.cclass[this.add_info.ctypeTag].bgcolor='#eee'
						this.cclass[this.add_info.ctypeTag].choice=false
					}	
					this.add_info.ctype=item.type
					this.add_info.ctypeTag=index
					//通过这个选择的值查询btype	
				} 
			},
			
			open_setprice() {
				// 通过组件定义的ref调用uni-popup方法
				//this.$refs.popup.open()
				if (this.setprice_click_time % 2 === 0) {
					this.setprice_status = true
					this.price_icon_style = true
				} else {
					this.setprice_status = false
					this.price_icon_style = false
				}
				this.setprice_click_time++

			},
			open_setclassify() {
				this.setclassify_click_time++
				if (this.setclassify_click_time % 2 === 0) {
					this.setclassify_status = true
					this.class_icon_style = true
				} else {
					this.setclassify_status = false
					this.class_icon_style = false
				}

			},
			open_photoarea() {
				this.setphoto_click_time++
				if (this.setphoto_click_time % 2 == 0) {
					this.photo_area_status = true
					this.photo_icon_style = true
				} else {
					this.photo_area_status = false
					this.photo_icon_style = false
				}

			},
			chooseImg() {
				uni.chooseImage({
					count: 9,
					success: (res) => {
						
						this.add_info.photos = this.add_info.photos.concat(res.tempFilePaths)
						
					}
				})
			},
			previewImg(current) {
				const res = getCurrentPages()//获取当前页面层级
				
				const urls = this.add_info.photos.map(item => {
					return item
				})
				uni.previewImage({
					
					current,
					urls,
					
					longPressActions: {
						itemList: ["删除"],
						success: (data)=> {
							this.add_info.photos.splice(data.index,1)
						},
						fail: function(err) {
							console.log(err.errMsg);
						}
					}
				})
			}
		},
	}
</script>

<style lang="scss">
	.photo-area {
		width: 90%;
		margin: 0 auto;
		display: flex;
		flex-direction: column;
	}

	.hidden_input {
		width: 100%;
		height: 0rpx;
	}

	.show_input {
		width: 100%;
		height: 200rpx;
	}

	.PopPrice {
		display: flex;
		flex-direction: row;

		view {
			margin-left: 20rpx;
		}
	}

	.father-box {

		display: flex;
		flex-direction: column;
		justify-content: space-around;
		
		// 小标题
		.title{
			width: 90%;
			margin: 0 auto;
			margin-top: 40rpx;
		}
		
		.description {
			background-color: #f0f0f0;
			
			padding: 20rpx;
			height: 200rpx;
			width: 90%;
			margin: 0 auto;
			margin-top: 40rpx;
			border-radius: 10rpx;
		}

		.attribute {
			display: flex;
			flex-direction: column;
			width: 90%;
			margin: 0 auto;

			.classify {
				width: 100%;
				position: relative;
				margin: 0 auto;

				.traingle {
					position: absolute;
					width: 0;
					height: 0;
					background-color: transparent;
					border-right: 8px solid transparent;
					border-bottom: 10px solid #eee;
					border-left: 8px solid transparent;
					left: 148rpx;
					top:71rpx
				}
				.title_class {
					width: 100rpx;
					font-size: 30rpx;
					line-height: 80rpx;
				}

				.content_class {
					position: absolute;
					white-space: nowrap;
					width: 90%;
					height: 80rpx;
					margin: 0 auto;
					top: 0;
					right: -40rpx;

					.class_choiced_style {
						background-color: $uni-color-orange;
					}

					.class_unchoiced_style {
						background-color: $uni-color-lightgray;
					}

					text {
						display: inline-block;
						margin: 8rpx 15rpx;
						font-size: 30rpx;
						padding: 15rpx;
						border-radius: 25rpx;
						background-color: $uni-color-lightgray;
					}

					.first {
						text-align: center;
						width: 70rpx;
					}
				}
			}
		    .diy-class{
				display: flex;
				flex-direction: row;
				.diy-input-class{
					width: 200rpx;
					text-align: center;
					border-radius: 20rpx;
					background-color: $uni-color-lightgray;
					margin-left:100rpx ;
				}
				.add-icon-class{
					text-align: center;
					line-height: 50rpx;
					font-size: 40rpx;
				}
			}
			
			
			.brand{
				// background-color: yellow;
				display: flex;
				flex-direction: row;
				justify-content: flex-start;
				align-items: center;
				margin-top: 10rpx;
				.title-class{
					margin-right:55rpx ;
				}
				.input-class{
					background-color: #f0f0f0;
					width: 200rpx;
					padding: 5rpx;
					border-radius: 20rpx;
					text-align: center;
				}
				
			}
			/* .diy_class {
				width: 30%;
				height: 50rpx;
				background-color: $uni-color-lightgray;
				border-radius: 20rpx;
				margin-top: 10rpx;
				margin-left: 100rpx;
				display: flex;
				flex-direction: row;
				justify-content: flex-start;
				
				input{
					height: 40rpx;
					width: 80%;
					padding: 5rpx;
					font-size: 20rpx;
				}
				view{
					text-align: center;
					line-height: 50rpx;
					margin-right: 12rpx;
				}
			}
 */

		}

		
		


		.photoView {
			width: 96%;
			display: flex;
			flex-direction: row;
			flex-wrap: wrap;
			margin-left: 36rpx;
			
		
			
			.upload_photo{
				
				margin-top: 20rpx;
				position: relative;
				margin-right: 14rpx;
				
				border-radius: 5px;
				height: 218rpx;
				width: 218rpx;
				
				background-color: #eee; //transparent
				color: $uni-color-orange;
				font-size: 100rpx;
				.upload_img{
					position: absolute;
					left: 0;
					width:100%;
					height:100%;
					border-radius: 5px;
				}
				.icon_del{
					
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
				.cpt-mask{
					position: absolute;
					width: 218rpx;
					height: 218rpx;
					background-color: #000000;
					opacity: 0.6;
					z-inde:998
				}
			}
			
			.upload_icon {
				
				margin-top: 20rpx;
				position: relative;
				margin-right: 14rpx;
				text-align: center;
				border-radius: 5px;
				height: 218rpx;
				width: 218rpx;
				line-height: 200rpx;
				background-color: #eee; //transparent
				color: $uni-color-orange;
				font-size: 100rpx;
			}
			
			
			
			.icon_add{
				text-align: center;
				line-height: 218rpx;
				
			}
		}
	}

	.headline2,
	.headline {
		width: 100%;
		height: 50rpx;
		margin: 0 auto;
		margin-top: 30rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;

		.price_title,
		.classify_title {
			
			width: 250rpx;
			display: flex;
			flex-direction: row;

			.type_icon {
				height: 50rpx;
				font-size: 50rpx;
				width: 50rpx;
			}

			.title1 {
				height: 50rpx;
				
				font-size: 35rpx;
				line-height: 50rpx;
			}
		}

		.price_title2 {
			width:150rpx;
			display: flex;
			flex-direction: row;
			justify-content: flex-end;

			.icon {
				line-height: 50rpx;
			}

		}

		.classify_icon {

			.icon {
				line-height: 50rpx;
			}
		}


	}
	.product_info{
		width: 100%;
		height: 100%;
		
		.blank{
			
			height: 355rpx;
			width: 744rpx;
		}
	}
	


</style>
