<template>
	<view class="websockets">
		<button @click="getESData()">获取es数据</button>
		<view v-for="(type,index) in aclass" :key="index">{{type._source.atype}}</view>
		<!-- <input type="text" placeholder="输入昵称" v-model="username"/>
		<button type="primary" @tap="connectSocketInit">进入聊天室</button>
		<input type="text" placeholder="输入内容" v-model="content"/>
		<button type="primary" @tap="clickPut">点击发送输入内容</button>
		<button type="primary" @tap="clickRequest">点击发送请求</button>
		<button type="primary" @tap="leave">离开页面</button>
		<button type="primary" @tap="goTest3">test3</button> -->
		<!-- <view class="mask" v-if="showmask" @click.stop="all"></view> -->
		<!-- <view @click.stop="noMultipleClicks(put)" >aaaaa</view> -->
		
		<!-- <view class="father-box"> -->
		<!-- <input :style="{bottom:keyboardheight}" @focus="focus" class="comment-input" v-if="show" :focus="isput" type="text" v-model="commentVals" confirm-type="send" @confirm="send" placeholder="问一下" @blur="blur" /> -->
		<!-- </view> -->
		<!-- <button class="button">fdsf</button> -->
	</view>
</template>
 
<script>
	import inputFrame from '../../components/input-frame.nvue'
	export default {
		// onLoad() {
		// 	// 进入这个页面的时候创建websocket连接【整个页面随时使用】
		// 	this.connectSocketInit();
		// },
		data() {
			return {
				socketTask: null,
				// 确保websocket是打开状态
				is_open_socket: false,
				content:null,
				username:null,
				show:false,
				commentVals:null,
				isput:false,
				isall:false,
				showmask:false,
				noClick:true,
				keyboardheight:'0px',
				aclass:[],
			}
		},
		components:{inputFrame},
		// 关闭websocket【必须在实例销毁之前关闭,否则会是underfined错误】
		beforeDestroy() {
			this.closeSocket();
		},
		methods: {
			getESData(){
				//查询aclass中的内容
				uni.request({
					url:'http://1.15.138.161:9200/types_a/_search',
					// url:'http://1.15.138.161:9200/bank/account/1',
					method:"POST",
					data:{
						"query":{
						    "match_all":{}
						  },
						  "from":0,
						  "size":20
					},
					success: (res) => {
						this.aclass=res.data.hits.hits
						console.log("你好"+this.aclass.length)
						
					}
				})
			},
			/* 解决click点击两次的问题 */
			noMultipleClicks(methods) {
			    let that = this;
			    
			    if (that.noClick) {
			        that.noClick= false;
			        methods();
			        setTimeout(function () {
			            that.noClick= true;
			        }, 2000)
			    } else {
			        console.log("请稍后点击")
			    }
			},
			
			put(){
				// this.isall=true
				this.show=true
				this.isput=true
				this.keyboardheight
				//this.send()
			},
			focus(e){
				console.log("获取的软盘的高度为：",e.detail.height)
				this.keyboardheight=e.detail.height+'px'
			},
			send(){
				//this.show=false
			},
			blur(){
				this.isput=false
				this.show=false
			},
			goTest3(){
				uni.navigateTo({
					url:'/pages/test/test3logindown'
				})
			},
			// 进入这个页面的时候创建websocket连接【整个页面随时使用】
			connectSocketInit() {
				// 创建一个this.socketTask对象【发送、接收、关闭socket都由这个对象操作】
				this.socketTask = uni.connectSocket({
					// 【非常重要】必须确保你的服务器是成功的,如果是手机测试千万别使用ws://127.0.0.1:9099【特别容易犯的错误】
					url: "ws://192.168.0.104:8081/chat/"+this.username,
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
			leave() {
				this.$uniReLaunch("/pages/tabbar/wallet/wallet")
			}
		},
		onLoad(){
			uni.request({
				url:'http://1.15.138.161:9200/types_a/_search',
				// url:'http://1.15.138.161:9200/bank/account/1',
				method:"POST",
				data:{
					"query":{
					    "match_all":{}
					  },
					  "from":0,
					  "size":20
				},
				success: (res) => {
					this.aclass=res.data.hits.hits
					console.log("你好"+this.aclass.length)
					
				}
			})
			uni.getSystemInfo({
			        success: (res)=> {
			            this.windowHeight = res.windowHeight;
			        }
			    });    
			uni.onWindowResize((res) => {
				if(res.size.windowHeight < this.windowHeight){
					this.tabbar = false
				}else{
					this.tabbar = true
				}
			})
		}
		
	}
</script>
<style>
	.button{
		position: fixed;
		bottom: 0;
	}
	/* .father-box{
		z-index: 1000;
		position: relative;
		width: 100%;
		height: 100%;
		background-color: yellow;
		display: flex;
		flex-direction: column;
		justify-content: flex-end;
	} */
	.comment-input{
		z-index: 1000;
		position: absolute;
		background-color: yellow;
		bottom: 0;
	}
	.mask{
		width: 500rpx;
		height: 400rpx;
		background-color: #C0C0C0;
		z-index: 999;
	}
</style>
