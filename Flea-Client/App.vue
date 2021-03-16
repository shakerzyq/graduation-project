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
