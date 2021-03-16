<template>
	<view>
		<!-- <scroll-view scroll-y="true" :scroll-top="commentScrollCount" class="comment-scroll-body">
			。。。。。。
		</scroll-view> -->
		<view v-for="(item,index) in items" :key="item.id" @longpress="deleteItem(index)">{{item.value}}</view>
		<button @click="add">添加</button>
	</view>
</template>
<script>
	import diyMask from '../components/mask.vue'
	export default {
		data() {
			return {
				time:null, 
				timestamp: 1614336285448,
				commentList: [], //获取的评论列表
				commentValue: '', //用户评论内容
				isSocketOpen: false, //socket是否打开
				pingpangTimes: null, //socket心跳计时器
				dateText: null,
				items:[{value:'a',id:1},{value:'b',id:2},{value:'c',id:3},{value:'e',id:4}]

			}
		},
		onLoad(option) {
			this.chatInit() //socket相关操作				
		},
		filters: {
			// 时间戳处理
			formatDate: function(value, spe = '/') {
				let time = Math.floor(((new Date()).valueOf() - value) / 86400000)
				console.log("time为:"+time)
				if (time === 0) {
					let time2 = Math.round(((new Date()).valueOf() - value) / 3600000)
					console.log("time2为："+time2)
					if(time2>0){
						
						return time2+'小时前'
					}else{
						let time3 = Math.ceil(((new Date()).valueOf() - value)/60000)
						console.log("time3为："+time3)
						return time3+'分钟前'
					}
					
				} else if (time > 0 && time < 30) {
					return time+'天前'
				} else if(time>=30){
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

			}
		},


		beforeDestroy() {
			clearInterval(this.pingpangTimes) //清除socket心跳定时器
			uni.closeSocket() //关闭socket
		},
		methods: {
			
			deleteItem(index){
				uni.showModal({
				    // title: '提示',
				    content: '是否删除评论？',
					cancelText:"取消",
					confirmText:"确定",
				    success: (res) =>{
				        if (res.confirm) {
							this.items.splice(index,1)
				            /* if(index_2===null){
								this.comments.splice(index_1,1)
							}else{
								this.comments[index_1].child_comment.splice(index_2,1)
							} */
				        } else if (res.cancel) {
				            console.log('用户点击取消');
							/* uni.getSystemInfo({
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
							}) */
				        }
				    }
				});
			},
			
			switchDate() {
				console.log("当前时间戳为", Date.parse(new Date()))

				this.time = Math.floor(((new Date()).valueOf() - this.timestamp) / 86400000)

				if (this.time > 30) {

					const date = this.formatDate2(this.timestamp, '-')
					console.log("date：" + date)
					this.dateText = date
				}
				/* switch(this.time){
					case:0:
						
						break;
					case:
				} */
			},
			//链接socket以进行直播实时评论发送获取等相关操作
			chatInit() {
				uni.request({
					url: `${this.$baseUrl}/api-live/checkLiveStatus?lid=${this.lid}&initial=1`, //此接口返回socket请求地址
					method: 'GET',
					success: res => {
						if (res.data.code == 200) {
							let socketlink = null
							if (this.userToken) { //已登录，携带token
								socketlink = `${res.data.msg}?token=${this.userToken}`
							} else { //未登录
								socketlink = res.data.msg
							}
							this.commentList = [] //创建新的socket连接前先清除之前的实时聊天记录
							uni.closeSocket() //创建新的socket连接前确保旧的已关闭
							//创建一个socket连接
							uni.connectSocket({
								url: socketlink,
								success: res => {}
							})
							//监听socket打开
							uni.onSocketOpen(() => {
								this.isSocketOpen = true
								console.log('WebSocket连接已打开！');
							})
							//监听socket关闭
							uni.onSocketClose(() => {
								this.isSocketOpen = false
								console.log('WebSocket连接已关闭！');
							})
							//监听socket错误
							uni.onSocketError(() => {
								this.isSocketOpen = false
								console.log('WebSocket连接打开失败');
							})
							//监听socket消息
							uni.onSocketMessage((res) => {
								let infos = JSON.parse(res.data) //socket信息是字符串，需要先转成json形式再去解析内容
								//后端规定cadmin为0--用户发言，为1--管理员发言，为2--系统提示，为3--需登录，为5--心跳信息
								if (typeof(infos.cadmin) == "undefined") { //infos.cadmin不存在说明返回的是数组，说明是第一次获取的之前的所有聊天记录
									this.commentList = this.commentList.concat(infos)
									//设置定时器，到时间后滚动到最底部。使用scroll-view组件的scroll-top属性。只要scroll-top的值大于滚动区域的最大高度即可实现滚动到最底部
									let scrolltimes = setTimeout(() => {
										this.commentScrollCount = this.commentList.length * 200
										clearTimeout(scrolltimes)
									}, 600)

									//后续每次更新一条有效的新聊天（心跳信息pong不是有效聊天），就会触发这个
								} else if (infos.cadmin == 0 || infos.cadmin == 1 || infos.cadmin == 2) {
									this.commentList = this.commentList.concat(infos)
									let scrolltimes = setTimeout(() => {
										this.commentScrollCount = this.commentList.length * 200
										clearTimeout(scrolltimes)
									}, 150)
								} else if (infos.cadmin == 3) { //未登录
									this.loginPopRemind = "您尚未登录或您的登录已过期，请重新登录后发言"
									uni.removeStorageSync('kusername')
									uni.removeStorageSync('kuserid')
									uni.removeStorageSync('kuserimg')
									uni.removeStorageSync('kusertoken')
									this.$refs.noLoginPopup.open()
								}
							})
							//先确保清除了之前的心跳定时器
							clearInterval(this.pingpangTimes)
							//每过一段时间发送一次心跳，发送Ping,服务器会反馈pong，这样操作以保持socket一直是连接状态，防止断开连接，心跳停止
							this.pingpangTimes = setInterval(() => {
								uni.sendSocketMessage({
									data: "ping",
									success: () => {},
									fail: () => {
										this.isSocketOpen = false
									}
								});
							}, 60000)
						}
					},
				});
			},

			//发表评论
			sendComment(val) {
				if (val == "") {
					uni.showToast({
						title: '说点什么吧 ~',
						icon: 'none'
					})
				} else if (val.length > 300) {
					uni.showToast({
						title: `评论字数请勿超过300个字符，当前 ${val.length}`,
						icon: 'none'
					})
				} else {
					if (this.userToken) { //已登录
						if (this.isSocketOpen) { //socket连接正常
							this.commentValue = val
							let theValue = {
								"ccontent": this.commentValue
							}
							let transedValue = JSON.stringify(theValue) //后端规定的评论数据格式:json转字符串
							uni.sendSocketMessage({
								data: transedValue,
								success: () => {
									this.commentValue = ""
									uni.showToast({
										title: '发送成功',
										icon: 'none'
									})
								},
								fail: () => {
									uni.showToast({
										title: '发送失败，请稍后再试或重新进入此页面尝试',
										icon: 'none'
									})
								}
							});
						} else { //socket已断开
							uni.showToast({
								title: '聊天断开啦，请重新进入此页面尝试 ~',
								icon: 'none'
							})
						}
					} else { //未登录
						this.$refs.needLoginPopup.open()
					}
				}
			},
		}
	}
</script>
<style lang="scss">
	.a {
		width: 400rpx;
		height: 400rpx;
		margin-bottom: 50rpx;
		background-color: transparent;
		position: relative;

		.cpt-mask {
			position: absolute;
			width: 400rpx;
			height: 400rpx;
			background-color: #000000;
			opacity: 0.5;
			z-inde: 998
		}

		.icon_del {
			position: absolute;
			z-index: 999;
			font-size: 100rpx;
			color: #F88000;

		}

		image {
			position: absolute;
			width: 100%;
			height: 100%
		}
	}

	// .cpt-mask {
	//     position: fixed;  
	//     top: 0;  
	//     left: 0;  
	//     width: 100rpx;  
	//     height: 100rpx;  
	//     background-color: #000000;  
	//     opacity: 0.5;  
	//     z-index: 99;  
	// }
</style>
