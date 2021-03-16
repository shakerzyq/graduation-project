<template>
	<view class="websockets">

		<input type="text" placeholder="输入昵称" v-model="username" />
		<button type="primary" @tap="connectSocketInit">进入聊天室</button>
		<input type="text" placeholder="输入内容" v-model="content" />
		<button type="primary" @tap="clickPut">点击发送输入内容</button>
		<button type="primary" @tap="clickRequest">点击发送请求</button>
		<button type="primary" @tap="leave">离开页面</button>
		<button type="primary" @tap="login">登录</button>
	</view>
</template>

<script>
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
				content: null,
				username: null,
				loken: null
			}
		},
		// 关闭websocket【必须在实例销毁之前关闭,否则会是underfined错误】
		beforeDestroy() {
			this.closeSocket();
		},
		methods: {
			// 进入这个页面的时候创建websocket连接【整个页面随时使用】
			connectSocketInit() {
				// 创建一个this.socketTask对象【发送、接收、关闭socket都由这个对象操作】
				this.socketTask = uni.connectSocket({
					// 【非常重要】必须确保你的服务器是成功的,如果是手机测试千万别使用ws://127.0.0.1:9099【特别容易犯的错误】
					url: "ws://192.168.0.104:8081/login/" + this.$uuid(),
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
						console.log("被占线了")
						// const arr = res.data.
						// if(res.data.length>12){
						// 	console.log("被占线了")
						// }
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
							console.log("成功登录");
						},
					});
				}
			},
			leave() {
				this.$uniReLaunch("/pages/tabbar/wallet/wallet")
			}
		}
	}
</script>
