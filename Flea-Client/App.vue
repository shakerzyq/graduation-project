<script></script>
<script>
	export default {
		onLaunch: () => {
			const mark = false
			console.log('App Launch')
			  uni.onTabBarMidButtonTap(()=>{
			        uni.navigateTo({
			        	url:'/pages/index/addProduct/addProduct'
			        })
			    })
			 //#ifdef APP-PLUS  
						 var info = plus.push.getClientInfo();
						console.log( "hello unipush",JSON.stringify( info ) );
						 /* 5+  push 消息推送 ps:使用:H5+的方式监听，实现推送*/  
						plus.push.addEventListener("click", function(msg) {  
									console.log("click:"+JSON.stringify(msg));  
									console.log(msg.payload);  
									console.log(JSON.stringify(msg));  
									//这里可以写跳转业务代码
								}, false);  
									// 监听在线消息事件    
								plus.push.addEventListener("receive", function(msg) {  
									// plus.ui.alert(2);  
										//这里可以写跳转业务代码
									console.log("recevice:"+JSON.stringify(msg))  
						   }, false);  
			
					   //#endif 
			// uni.getStorage({
			// 	key: 'login_mark',
			// 	success: res => {
			// 		//mark = res.data
			// 		console.log('name 异步获取 APP = ' + res.data)
			// 		if (res.data) {
			// 			uni.navigateTo({
			// 				url: '/pages/index/index'
			// 			})
			// 		}
			// 	}
			// })
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		onTabItemTap : function(e) {
		    console.log(e);
		    // e的返回格式为json对象： {"index":0,"text":"首页","pagePath":"pages/index/index"}
		},
	}
</script>
<style >
	/*每个页面公共css */
	/* @import url("http://at.alicdn.com/t/font_2348889_zkkd0te2c0c.css"); */
	@import url("./static/fonts/iconfont.css");
	/* @import url("//at.alicdn.com/t/font_2348889_zkkd0te2c0c.css"); */
.icon {
	  width: 1em;
	  height: 1em;
	  vertical-align: -0.15em;
	  fill: currentColor;
	  overflow: hidden;
	}
</style>
