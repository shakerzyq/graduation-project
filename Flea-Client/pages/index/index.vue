<template>
	<view class="content">
		<!-- 搜索栏 -->
		<view class="search-father" @click="goSearch">
			<input class="search-text " disabled="true" placeholder="搜你想搜的" readonly="readonly" />
			<view class="iconfont search-icon">&#xe8aa;</view>
		</view>
		
		<view class="recommend-classify">
			<view :class="newGoods" @click="getNewGoods">最新发布</view>
			<view :class="hotGoods" @click="getHotGoods">热度最高</view>
		</view>
		
		<!-- 商品展示页面 -->
		<goodsList @goodsItemClick="goGoodsDetail" :goodsa="goodsa" :goodsb="goodsb"></goodsList>
		<view class="isOver" v-if="flag">-----我是有底线的-----</view>
		
		

	</view>
</template>

<script>
	import goodsList from '../../components/goods_list.vue'
	export default {
		data() {
			return {
				title: 'Hello',
				goods: [],
				goodsa: [],
				goodsb: [],
				i: 0,
				locken: null,
				socketTask: null,
				// 确保websocket是打开状态
				is_open_socket: false,
				flea_id:null,
				pagenum:1,//当前页数
				flag:false,
				newGoods:"newGoods",
				hotGoods:"hotGoods2",
			}
			
		},
		
		
		components: {
			goodsList
		},

		// onShow:function(){
		// 	//获取后台商品数据，并渲染
		// 	this.getGoodsList()
		// },
		onLoad:function() {
			//获取本地存储的跳蚤id
			this.flea_id
			
			//获取本地存储的flea_id和locken
			try {
			    const value = uni.getStorageSync('locken');
				const value2 = uni.getStorageSync('flea_id');
			    if (value) {
			        this.locken=value
			    }
				if(value2){
					this.flea_id=value2
					console.log("从storage中获取的flea_id为:"+this.flea_id)
				}
			} catch (e) {
			    // error
			}
			this.getCollegeArea();
			
			//获取后台商品数据，并渲染
			
			
			if(this.locken===null){
				this.locken = this.$uuid()
				try {
				    uni.setStorageSync('locken', this.locken);
				} catch (e) {
				    // error
				}
			}
			setTimeout(()=>{ this.getGoodsList("new")},500)
			this.connectSocketInit()
			
			
		},
		
		
		
		//监听是否触底了
		onReachBottom() {
			console.log()
			if(this.goods.length<this.pagenum*8) return this.flag=true
			console.log("触底了")
			this.pagenum++
			this.getGoodsList("new")
		},
		
		//刷新页面
		onPullDownRefresh() {
			this.pagenum = 1
			this.goods=[]
			this.goodsa=[]
			this.goodsb=[]
			this.flag=false
			this.getGoodsList("new",()=>{
				uni.stopPullDownRefresh()
			})
		},

		methods: {
			async getCollegeArea(){
				const result = await this.$myRequest({
					url:"/product/getAddPlace/"+this.flea_id
				})
				console.log("dizhiwei:",result.data)
				this.$store.state.addPlace=result.data;
				console.log("dizhiwei2:",this.$store.state.addPlace)
			},
			// 导航到搜索界面
			goSearch(){
				uni.navigateTo({
					url:"/pages/index/search/search"
				})
			},
			
			// 获取最新商品
			getNewGoods(){
				this.newGoods="newGoods";
				this.hotGoods="hotGoods2";
				this.goods=[]
				this.goodsa=[]
				this.goodsb=[]
				this.getGoodsList("new")
			},
			
			//获取热度最高商品
			getHotGoods(){
				this.newGoods="newGoods2";
				this.hotGoods="hotGoods";
				this.goods=[]
				this.goodsa=[]
				this.goodsb=[]
				this.getGoodsList("hot")
			},
			
			//获取商品数据
			async getGoodsList(type,callBack){
				console.log("type:"+type)
				const result = await this.$myRequest({
					url:'/product/getgoodslist/'+this.pagenum+'/'+this.flea_id+'/'+this.$store.state.addPlace+'/'+type
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
				console.log("goodsa为：",this.goodsa)
				console.log("goodsb为：",this.goodsb)
				/* this.i = 0
				this.goodsb = this.goods.slice(0, this.goods.length / 2)
				this.goodsa = this.goods.slice(this.goods.length / 2, this.goods.length + 1)
				console.log(this.goodsa)
				console.log(this.goodsb) */
				callBack && callBack() //通过&&判断，有callback就调用，没有就不调用
			},
			
			 //导航到商品详情页
			goGoodsDetail(userId,goodsId){
				console.log("跳转需要的id"+goodsId,userId)
				uni.navigateTo({
					url:'/pages/index/goods-detail?goodsId='+goodsId+'&'+'userId='+userId
				})
			},
			
			
			
			// 进入这个页面的时候创建websocket连接【整个页面随时使用】
			connectSocketInit() {

				// 创建一个this.socketTask对象【发送、接收、关闭socket都由这个对象操作】
				this.socketTask = uni.connectSocket({
					// 【非常重要】必须确保你的服务器是成功的,如果是手机测试千万别使用ws://127.0.0.1:9099【特别容易犯的错误】
					url: "ws://127.0.0.1:8081/login/"+this.flea_id+"/"+this.locken,
					success(data) {
						console.log("websocket连接成功");
					},
				});

				// 消息的发送和接收必须在正常连接打开中,才能发送或接收【否则会失败】
				this.socketTask.onOpen((res) => {
					console.log("WebSocket连接正常打开中...！");
					this.is_open_socket = true;
					// 注：只有连接正常打开中 ，才能正常成功发送消息
					this.socketTask.send({
						data: "uni-app发送一条消息",
						async success() {
							console.log("消息发送成功");
						},
					});
					// 注：只有连接正常打开中 ，才能正常收到消息
					this.socketTask.onMessage((res) => {
						console.log("收到服务器内容：" + res.data);

						if (res.data === "up") {
							console.log("服务返回的内容"+res.data)
						} else {
							console.log(res.data+"其它设备登录++++++++++++++++++++++++++++++++++++++++"+this.locken)

							uni.showModal({
							    // title: '提示',
							    content: '该账号在其他设备登录',
								cancelText:"退出",
								confirmText:"重新登陆",
							    success: function (res) {
							        if (res.confirm) {
							            uni.reLaunch({
							            	url:'/pages/login/login'
							            })
							        } else if (res.cancel) {
							            console.log('用户点击取消');
										uni.getSystemInfo({
											success: function(res) {
												// 判断为安卓的手机
												if (res.platform == 'android') {
													// 安卓退出app
													plus.runtime.quit();
												} else {
													// 判断为ios的手机，退出App
													plus.ios.import("UIApplication").sharedApplication().performSelector("exit");
												}
											}
										})
							        }
							    }
							});
						}
					});
				})
				// 这里仅是事件监听【如果socket关闭了会执行】
				this.socketTask.onClose(() => {
					console.log("已经被关闭了")
				})
			},
			// 关闭websocket【离开这个页面的时候执行关闭】
			closeSocket() {
				this.socketTask.close({
					success(res) {
						this.is_open_socket = false;
						console.log("关闭成功", res)
					},
					fail(err) {
						console.log("关闭失败", err)
					}
				})
			},
			clickRequest() {
				if (this.is_open_socket) {
					// websocket的服务器的原理是:发送一次消息,同时返回一组数据【否则服务器会进去死循环崩溃】
					this.socketTask.send({
						data: "请求一次发送一次message",
						async success() {
							console.log("消息发送成功");
						},
					});
				}
			},
			clickPut() {
				if (this.is_open_socket) {
					// websocket的服务器的原理是:发送一次消息,同时返回一组数据【否则服务器会进去死循环崩溃】
					this.socketTask.send({
						data: this.content,
						async success() {
							console.log("消息发送成功");
						},
					});
				}
			},
			login() {
				if (this.is_open_socket) {
					this.loken = this.$uuid()
					// websocket的服务器的原理是:发送一次消息,同时返回一组数据【否则服务器会进去死循环崩溃】
					this.socketTask.send({
						data: this.loken,
						async success() {
							console.log("成功登录")
						},
					})
				}
			},
			leave() {
				this.$uniReLaunch("/pages/tabbar/wallet/wallet")
			}
		}

	}
</script>

<style lang="scss">
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		.search-father{
			height: 80rpx;
			border-radius: 20rpx;
			display: flex;
			flex-direction: row;
			justify-content: center;
			align-items: center;
			width: 95%;
			margin: 10rpx;
			background-color: #efefef;
			
			.search-text{
				text-align: center;
			}
			.search-icon{
				line-height: 80rpx;
				text-align: center;
				font-size: 45rpx;
			}
		}
		
		.recommend-classify{
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			width: 100%;
			.newGoods,.hotGoods{
				font-size: 40rpx;
				color: $uni-color-orange;
			}
			.newGoods2,.hotGoods2{
				
			}
			.hot{
				
			}
		}
		.test {
			background-color: yellow;
		}
		
		.recommend-classify{
			display: flex;
			flex-direction: row;
		}
	}

	.a {
		background-color: yellow;
		height: 100rpx;
		width: 100rpx;
	}
	
	.isOver{
		width:100%;
		height:50px;
		line-height: 50px;
		text-align: center;
		font-size: 28rpx;
		color: $uni-color-orange;
	}
</style>
