<template>
	<view>
	<view v-if="loadingJudge">
		<!-- 
		顶部导航栏：
			返回键，
			搜索跳转
		-->
		<view class="top_bar">
			<view class="iconfont back-icon" @tap="back() ">&#xe749;</view>
			<!--搜索栏-->
			<view class="search-father" @tap="goSearch">
				<view class="iconfont search-icon">&#xe8aa;</view>
				<input class="search-text " placeholder="搜你想搜的" readonly="readonly" />
			</view>
			<view class="iconfont share-icon" @tap="openShare">&#xe859;</view>
			<uni-popup ref="popup" type="share">
			    <uni-popup-share  title="分享到" @select="select"></uni-popup-share>
			</uni-popup>
			
		</view>
		<!-- 内容展示 -->
		<view class="content">
			<!-- 店主信息 -->
			<view class="user">
				<!-- 头像昵称 -->
				<view class="user-info" @click="goToPersonalCenter(goodsUserInfo.userId)">
					<image class="user-icon" :src="goodsUserInfo.userIcon"></image>
					<view class="user-name">{{goodsUserInfo.userName}}</view>
				</view>
				<!-- 关注 -->
				<view v-if="flea_id===merchantId?false:true" class="fans-user" @click="fansAction(flea_id,merchantId)">
					<view class="fans-user-content" v-if="goodsUserInfo.fansJudge?false:true">
						<view class="fans-icon iconfont">&#xe8a1;</view>
						<view class="fans-text">关注</view>
					</view>
					<view v-else class="fans-user-content2">
						已关注
					</view>

				</view>
			</view>

			<!-- 详细信息 -->
			<view class="goods">
				<!-- 商品价格 -->
				<view class="goods-price">
					<view class="goods-price-now">￥<text>{{goodsUserInfo.goodsNowPrice}}</text></view>
					<view class="goods-price-old"><text>{{goodsUserInfo.goodsOldPrice}}</text></view>
				</view>

				<!-- 小标题，用于搜索，不宜过长 -->
				<view class="goods-title">{{goodsUserInfo.goodsTitle}}</view>

				<!-- 商品介绍 -->
				<view class="goods-introduce">{{goodsUserInfo.goodsDes}}</view>

				<!-- 商品成色，类型，品牌 -->
				<view class="tag">
					<view v-if="this.goodsUserInfo.goodsQuality?true:false" class="tag-1">
						<view class="tag-title">成色</view>
						<view class="tag-content">{{goodsUserInfo.goodsQuality}}</view>
					</view>
					<view  class="tag-1"> 
						<view class="tag-title">类别</view>
						<view class="goods-type">
							<view class="tag-content">{{goodsUserInfo.atype}}</view>
							<view class="iconfont midicon">&#xe748;</view>
							<view class="tag-content">{{goodsUserInfo.btype}}</view>
							<view v-if="goodsUserInfo.ctype===null?false:true" class="iconfont midicon">&#xe748;</view>
							<view class="tag-content">{{goodsUserInfo.ctype}}</view>
						</view>
						
					</view>
					<view  class="tag-1">
						<view class="tag-title">品牌</view>
						<view class="tag-content">{{goodsUserInfo.brand}}</view>
					</view>
				</view>

				<!-- 商品图片 -->
				<view class="goods-photos">
					<image v-for="(photo,index) in photos" :key="index" :src="photos[index]" mode="widthFix" @click="previewImg(photo)">

					</image>
				</view>
				<!-- 商品 想要 ，点赞，举报-->
				<view class="goods-else">
					<view>{{goodsUserInfo.addTime|formatDate('-')}}发布</view>
					<view class="segment"></view>
					<view class="else-like">{{goodsUserInfo.goodsLike}}喜欢 </view>
					<view class="segment"></view>
					<view class="else-wanter"> {{goodsUserInfo.goodsCollect}}想要 </view>
					<view class="segment"></view>
					<view class="visit"> 浏览{{goodsUserInfo.goodsViews}} </view>
					<view class="segment"></view>
					
					<!-- 商品举报 -->
					<view v-if="goodsUserInfo.userId!==flea_id" >
						<view class="iconfont report"  v-if="goodsUserInfo.goodsReportJudge" @click="openReportPop"> &#xe876;已举报</view>
						<view class="iconfont report" v-else @click="openReportPop" >&#xe7f0;举报</view>
						<uni-popup ref="reportpop" type="center" >
							<view class="report-pop">
								<scroll-view class="pop-content" scroll-y="true">
									<view v-for="(items,index1) in list" :key="index1">
										
											<view class="option-f"  v-if="items.foldJudge" style="color: #FF8000;" @click="unflodAction(items)">
												<view>{{items.name}}</view>
												<view class="iconfont icon-unfold"  v-if="items.foldJudge">&#xe8a4;</view>
												<!-- <view class="iconfont icon-fold" v-else>&#xe748;</view> -->
											</view>
											<view class="option-f" v-else  @click="unflodAction(items)">
												<view>{{items.name}}</view>
												<view class="iconfont icon-fold">&#xe748;</view>
											</view>
										<view class="option-c" v-if="items.foldJudge">
											
											<view class="option-c-item" v-for="(item,index2) in items.values" :style="{color:item.color}" :key="index2" v-if="items.values.length===0?false:true"  @click="selctReportContent(item,index1,index2)">
												{{item.name}}
											</view>
											
										</view>
										
										
									</view>
								</scroll-view>
								
								<view class="pop-button">
									<view class="pop-button-cancel" @click="unReportGoodsAction">取消举报</view>
									<view class="pop-button-confirm" @click="reportGoodsAction">确定/修改</view>
								</view>
							</view>
						</uni-popup>
					</view >	
				</view>
			</view>
			<!-- 分割线 -->


		</view>
		<view class="segment-line"></view>
		<!-- 评论区 -->
		<view class="comment">
			<view class="comment-title">
				<view class="title-left">
					<text>全部留言</text>
					<view class="segment"></view>
					<view class="comment-num">{{goodsUserInfo.commentNum}}</view>
				</view>

			</view>
			<view class="comment-add">
				<image class="user-icon" :src="goodsUserInfo.observerIcon">

				</image>
				<!-- <input class="comment-input" type="text" v-model="commentVals" confirm-type="send" @confirm="noMultipleClicks2(put)" -->
				 <!-- placeholder="问一下" /> -->
				 <input class="comment-input" type="text" @click="noMultipleClicks2(put)" disabled="true" placeholder="问一下" />
			</view>
			<view class="comment-all" v-if="isShowComment">
				<view v-if="comments===null?true:false">没有评论</view>
				<view v-else>
					<!-- 一级评论框 -->
					<view v-for="(comment_1,index_1) in comments" :key="comment_1.comment_id" class="comment-1">
						<!-- 评论信息 -->

						<view class="comment-1-1" @longpress="deleteComment(index_1,null,comment_1)">
							<view class="comment-1-rank1">
								<view class="comment-1-rank1-user" @click="goToPersonalCenter(comment_1.observer_id)">
									<image class="comment-1-icon" :src="comment_1.observer_icon"></image>
									<view class="comment-1-name">{{comment_1.observer_name}}</view>
								</view>

								<view @click="likeAction(index_1,null,0,comment_1.comment_id)" class="iconfont like-icon" style="color:#ff0000" v-if="comment_1.likeJudge===1?true:false">&#xe831;<text
									 style="font-size: 20rpx;">{{comment_1.comment_like}}</text></view>
								<view @click="likeAction(index_1,null,1,comment_1.comment_id)" class="iconfont like-icon" v-else>&#xe789;<text
									 style="font-size: 20rpx;">{{comment_1.comment_like}}</text></view>

							</view>
							<view class="comment-1-rank2-f" @click.stop="noMultipleClicks(put,comment_1,1,comment_1.comment_id,index_1,null)">
								<!-- <view class="blank"></view> -->
								<view class="comment-1-rank2">
									<view class="comment-1-content">{{comment_1.comment}}</view>
									<view class="comment-1-date">{{comment_1.put_time|formatDate('-')}}</view>
								</view>
							</view>

						</view>

						<!-- 二级评论框 -->

						<!-- <view class="comment-2-f" v-if="comment_1.child_comment===null?false:true"> -->
						<view class="comment-2-f" v-if="comment_1.child_comment===null?false:true">
							<!-- <view class="blank2"></view> -->
							<!-- <view v-for="(comment_2,index_2) in comment_1.child_comment" :key="index_2" class="comment-2" > -->
							<view v-for="(comment_2,index_2) in comment_1.child_comment" @longpress="deleteComment(index_1,index_2,comment_2)"
							 :key="comment_2.comment_id" class="comment-2" v-if="index_2>comment_1.judgeLength?false:true">
								<view class="comment-2-rank1">
									<view class="comment-2-rank1-user" @click="goToPersonalCenter(comment_2.observer_id)">
										<image class="comment-2-icon" :src="comment_2.observer_icon"></image>
										<view class="comment-2-name">{{comment_2.observer_name}}</view>
									</view>
									<view @click="likeAction(index_1,index_2,0,comment_2.comment_id)" class="iconfont like-icon" style="color:#ff557f" v-if="comment_2.likeJudge===1?true:false">&#xe831;<text
										 style="font-size: 20rpx;">{{comment_2.comment_like}}</text></view>
									<view @click="likeAction(index_1,index_2,1,comment_2.comment_id)" class="iconfont like-icon" v-else>&#xe789;<text
										 style="font-size: 20rpx;">{{comment_2.comment_like}}</text></view>

								</view>
								<view class="comment-2-rank2-f" @click.stop="noMultipleClicks(put,comment_2,1,comment_1.comment_id,index_1,index_2)">


									<view class="comment-2-rank2">
										<view class="comment-2-content">@{{comment_2.commented_name}}: {{comment_2.comment}}</view>
										<view class="comment-2-date">{{comment_2.put_time|formatDate('-')}}</view>
									</view>
								</view>
							</view>

							<view class="pull-down" v-if="comment_1.child_comment.length>1&&comment_1.show_all===false?true:false" @click="showAll(index_1)">
								<text>全部{{comment_1.child_comment.length}}条回复</text>
								<view class="iconfont pull-down-icon">&#xe8a4;</view>
							</view>
							<view class="pull-down" v-else-if="comment_1.child_comment.length>1" @click="unShowAll(index_1)">
								<text>收起消息</text>
								<view class="iconfont pull-down-icon">&#xe74c;</view>
							</view>
						</view>
						<view class="blank-comment"></view>
					</view>

				</view>
			</view>
			<view class="blank-bottom"></view>
		</view>
		<view class="sendmessage-f-f" v-if="show">
			<view class="sendmessage-f">
				<!-- <input :style="{bottom:keyboardheight}" @focus="focus" class="sendmessage-i"  :focus="isput" type="text" v-model="commentVals" confirm-type="send" @confirm="send" placeholder="问一下" @blur="blur" /> -->
				<input @blur="blur" @focus="focus" class="sendmessage-i" :focus="isput" type="text" v-model="commentVals" confirm-type="send"
				 @confirm="send()" placeholder="问一下" />
				<text class="sendmessage-b" @click="sendMessage">留言</text>
			</view>
		</view>

		<view class="bottom-bar">
			<view class="observer-view" v-if="flea_id===goodsUserInfo.userId?false:true">
			<!-- <view class="observer-view" v-if="true"> -->
				<view class="observer—left">
					<!-- 喜欢 -->
					<view class="observer-like">
						<view class="iconfont like" style="color:#ffff00" v-if="goodsUserInfo.goodsLikeJudge" @click="goodsLikeAction">&#xe721;</view>
						<view class="iconfont like"  @click="goodsLikeAction" v-else>&#xe8a8;</view>
						<text>喜欢</text>
					</view>
					
					<!-- 收藏 -->
					<view class="observer-collect">
						<view class="iconfont collect" style="color:#ff0000" v-if="goodsUserInfo.goodsCollectJudge" @click="goodsCollectAction">&#xe884;</view>
						<view class="iconfont collect" @click="goodsCollectAction" v-else>&#xe882;</view>
						<text>收藏</text>
					</view>
					<!-- 留言 -->
					<view class="observer-comment">
						<!-- @confirm="send(merchantId,goodsUserInfo.userName,0,'null') -->
						<view class="comment iconfont" @click.stop="noMultipleClicks2(put)">&#xe74f;</view>
						<text>留言</text>
					</view >
					
					
					
				</view>
				<view class="observer-right" v-if="goodsUserInfo.status==='living'">
					<view style="background-color:#ffaa00;border-radius: 30rpx;"  @click.stop="putOrder">下单</view>
				</view>
				<view class="observer-right" v-else>
					<view v-if="goodsUserInfo.status==='trading'">
						<view v-if="isOrder" style="background-color: #fff;color: #808080;">您已下单</view>
						<view v-else style="background-color: #fff;color: #808080;width:160rpx ;">别人已下单</view>
					</view>
					<view v-else>
						<view style="background-color: #fff;color: #808080;">已售卖</view>
					</view>
					
				</view>
			</view>
			<view class="merchant-view" v-else>
				<view ></view>
				<view v-if="goodsUserInfo.status==='living'" class="observer-right" @click="goToUpdateProduct">管理商品</view>
				<view v-else class="observer-right" style="background-color: #fff;color: #808080;" @click="goToUpdateProduct">{{goodsUserInfo.status==='traind'?'已被下单':'已售卖'}}</view>
			</view>					
		</view>
	</view>
	<view v-else class="loading">
		
		<view class="loading-content">加载中。。。。。。</view>
	</view>
	</view>
</template>

<script>
	import goodsReport from '../report/goods-report'
	export default {
		
		data() {
			return {
				goodsId: null, //商品id
				photos: [], //拆解后的photos
				merchantId: null,
				goodsUserInfo: [], //商家信息，商品信息，点赞信息，收藏信息
				CommentInfo: [], //相关评论信息
				flea_id: null, //浏览者id
				type: null,
				isShowComment: false, //comment是否显示,通过底部监听器判断
				show_all: false, //是否显示其他条数，如果为false不显示
				judgeLength: [0, 0, 0, 0, 0], //当点击了展示剩余信息（showAll）后，变为9999那么就展示了
				commentVals: null, //用户输入的评论内容
				comments: [], //评论内容

				/* 弹出输入框相关 */
				isput: false,
				show: false,
				noClick: true,
				keyboardheight: '0px',

				comment_f_id: 'null', //父级评论id
				commented_userid: null, //被评论者id
				commented_username: null, //被评论者用户名
				rank: 0, //评论等级
				commented_id: null, //被评论id
				flag: false, //显示没有评论了,如果为true就不会访问数据库了
				index_1: null, //一级评论索引
				index_2: null, //二级评论索引

				pagenum: 1, //分页编号
				
				communtNum:null,//显示评论数量
				
				/* 举报选择项列表 */
				list:[
					{
						name:'出售违禁品',
						foldJudge:false,
						values:[
							{
								name:'管制刀具',
								color:null,
								selectJudge:false,
							},
							{
								name:'管制药物',
								color:null,
								selectJudge:false,
							},
							{
								name:'违禁图书、媒体',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'低俗或则不适图片',
						foldJudge:false,
						values:[
							{
								name:'色情图片',
								color:null,
								selectJudge:false,
							},
							{
								name:'不适图片',
								color:null,
								selectJudge:false,
							},
							{
								name:'政治宗教敏感图片',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'虚假信息',
						foldJudge:false,
						values:[
							{
								name:'价格虚报',
								color:null,
								selectJudge:false,
							},
							{
								name:'标题内容不符',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'假货盗版',
						foldJudge:false,
						values:[
							{
								name:'假货',
								color:null,
								selectJudge:false,
							},
							{
								name:'盗版',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'不适内容',
						foldJudge:false,
						values:[
							{
								name:'挂名辱骂、诋毁他人',
								color:null,
								selectJudge:false,
							},
							{
								name:'标题内容低俗',
								color:null,
								selectJudge:false,
							},
							{
								name:'包含敏感话题',
								color:null,
								selectJudge:false,
							},
						]
					}
					
				],
				
				reportList:[],//存储选择到的举报内容
				lastReports:null,//最终的举报内容，合并现在和以前的举报内容
				
				isOrder:false,//判断用户是否已经对该商品下单
				
				loadingJudge:false,//判断信息是否加载完毕，加载完毕后再渲染
			}
		},
		filters: {
			// 时间戳处理
			formatDate: function(value, spe = '/') {
				let time = Math.floor(((new Date()).valueOf() - value) / 86400000)
				if (time === 0) {
					let time2 = Math.round(((new Date()).valueOf() - value) / 3600000)
					if (time2 > 0) {
						return time2 + '小时前'
					} else {
						let time3=((new Date()).valueOf() - value) / 60000
						if(time3>1){
							return Math.ceil(time3) + '分钟前'
						}else{
							return Math.ceil(((new Date()).valueOf() - value)/1000)+"秒前"
						}					
					}
				} else if (time > 0 && time < 30) {
					return time + '天前'
				} else if (time >= 30) {
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
			},
		},
		
		methods: {
			/* top——bar */
			
			/* 前往用户主页 */
			goToPersonalCenter(userId){
				console.log('detail页面中userID为：'+userId+" fleaId为："+this.flea_id)
				uni.navigateTo({
					url:'/pages/personal-center/personal-center?flea_id='+this.flea_id+'&userId='+userId
				})
			},

			back() {
				uni.navigateBack();
			},
			goSearch() {
				uni.navigateTo({
					url: ""
				})
			},

			openShare(){
				this.$refs.popup.open()
				
			},
			select(e){
				switch(e.index){
					case 0:
						this.goShare('weixin','WXSceneSession',1)
						break;
					case 1:
						this.goShare(1)
						break;
					case 2:
						this.goShare(qq,"nihao",1)
						break;
					case 3:
						this.goShare(sianweibo,null,1)
						break;
						
					
				}
			},
			
			/**
			 * 分享
			 * @param {Object} provider
			 * @param {Object} scence
			 * @param {Object} type
			 */
			goShare(provider,scence,type) {
				// #ifdef APP-PLUS

				uni.share({
					provider: provider,
					scene: scence,
					type: type,
					title:scence,
					href:"www.baidu.com",
					summary: "我正在使用HBuilderX开发uni-app，赶紧跟我一起来体验！",
					success: function(res) {
						console.log("success:" + JSON.stringify(res));
					},
					fail: function(err) {
						console.log("fail:" + JSON.stringify(err));
					}
				})

				// #endif
				// #ifdef MP-WEIXIN

				console.log('分享')

				// #endif

			},

			/* content */

			/* 关注 / 取消关注*/
			async fansAction(flea_id, merchant_id) {
				this.type = (this.goodsUserInfo.fansJudge) ? 'unfans' : 'fans'
				
				if(this.goodsUserInfo.fansJudge===0){
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('incrementAttention')
					}
				}else{
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('decrementAttention')
					}
				}
				
				const result = await this.$myRequest({
					url: '/product/fansAction/' + flea_id + '/' + merchant_id + '/' + this.type,
				})
				this.goodsUserInfo.fansJudge = result.data
			},

			/* 获取商家信息 */
			async getMerchantInfo() {
				const info = await this.$myRequest({
					url: '/product/getGoodsDetail/' + this.merchantId + '/' + this.goodsId + '/' + this.flea_id //GET
				})

				this.goodsUserInfo = info.data
				console.log("info.data:" + this.goodsUserInfo)
				//将传递过来的照片链接转化为数组
				if (this.goodsUserInfo.photos.indexOf(',') === -1) {
					this.photos[0] = this.goodsUserInfo.photos
				} else {
					this.photos = this.goodsUserInfo.photos.split(",")
				}
				//将举报信息转化为数组
				if(this.goodsUserInfo.goodsReportContent!==null){
					console.log("this.goodsUserInfo.goodsReportContent为："+this.goodsUserInfo.goodsReportContent)
					console.log("进入了修改举报选择页面")
					
					this.goodsUserInfo.goodsReportContent.forEach((a)=>{
						console.log("遍历的每个值1为："+a)
						if(a!==null){
							const arr =  a.split("-")
							console.log("arr值为"+arr[0],arr[1],arr[2])
							this.list[arr[1]].values[arr[2]].selectJudge=true
							this.list[arr[1]].values[arr[2]].color='#3EB1D7'
							this.list[arr[1]].foldJudge=true
						}else{
							
						}
						
					})
				}
				this.loadingJudge=true
			},

			/**
			 * 预览图片
			 * @param {Object} current
			 */
			previewImg(current) {
				const urls = this.photos.map(item => {
					return item
				})
				uni.previewImage({
					current,
					urls,
				})
			},

			/* 发送评论信息 */
			/* 解决click点击两次的问题 */
			/* 评论留言的处理 */
			noMultipleClicks(methods, comment, rank, comment_f_id, index_1, index_2) {

				let that = this
				this.commented_userid = comment.observer_id
				this.commented_username = comment.observer_name
				this.rank = rank
				this.commented_id = comment.comment_id
				this.comment_f_id = comment_f_id
				this.index_1 = index_1
				this.index_2 = index_2

				if (that.noClick) {
					that.noClick = false;
					methods();
					setTimeout(function() {
						that.noClick = true;
					}, 2000)
				} else {
					console.log("请稍后点击")
				}
			},
			//对商品留言的处理
			noMultipleClicks2(methods) {
				let that = this
				this.commented_userid = that.merchantId
				this.commented_username = that.goodsUserInfo.userName
				this.rank = 0
				this.commented_id = 'null'
				this.comment_f_id = 'null'
				if (that.noClick) {
					that.noClick = false;
					methods();
					setTimeout(function() {
						that.noClick = true;
					}, 2000)
				} else {
					console.log("请稍后点击")
				}
			},

			//监听触发，键盘自带按钮
			put() {
				this.show = true
				this.isput = true
				this.keyboardheight
			},
			focus(e) {
				this.keyboardheight = e.detail.height + 'px'
			},
			send(commented_userid, commented_username, rank, commented_id) {
				this.sendMessage()
			},
			//失去焦点时输入界面消失
			blur() {
				//this.isput = false
				setTimeout(()=>{
					this.show = false
				},100)
				
			},

			//发送留言
			async sendMessage() {
				const nowtime = new Date().valueOf()
				let that = this
				const result = await this.$myRequest({
					url: '/comment/sendcomment',
					data: {
						comment_id: nowtime + that.flea_id,
						commented_id: that.commented_id,
						comment_f_id: this.comment_f_id,
						comment_rank: that.rank,
						goods_id: that.goodsId,
						merchant_id: that.merchantId,
						commented_userid: that.commented_userid,
						commented_username: that.commented_username,
						observer_id: that.flea_id,
						observer_username:that.goodsUserInfo.observerName,
						comment: that.commentVals,
						put_time: nowtime

					},
					method: 'POST'
				})

				this.show = false
				const newdata = {
					child_comment: [],
					comment: that.commentVals,
					comment_id: nowtime + that.flea_id,
					comment_id_f: that.comment_f_id,
					comment_like: 0,
					comment_rank: that.rank,
					commented_id: that.commented_id,
					commented_name: that.commented_username,
					judgeLength: 0,
					likeJudge: 0,
					observer_icon: that.goodsUserInfo.observerIcon,
					observer_id: that.flea_id,
					observer_name: that.goodsUserInfo.observerName,
					put_time: nowtime,
					show_all: false,
					user_id: null
				}
				if (that.rank === 0) {
					console.log("进入了")
					this.comments.push(newdata)
				} else {
					console.log("进入了")
					console.log("this.index_1为：" + this.index_1)
					this.comments[this.index_1].child_comment.push(newdata)
				}
				this.goodsUserInfo.commentNum++
				console.log("是否评论成功：" + result.data)
				this.commentVals=null
			},

			//加载评论信息
			async getComments() {
				const info = await this.$myRequest({
					url: '/comment/getcomment/' + this.goodsId + '/' + this.pagenum + '/' + this.flea_id //GET
				})
				if (this.comments === null) {
					this.comments = info.data
				} else {
					this.comments = [...this.comments, ...info.data]
				}


				this.comments.forEach(function(a) {
					a.show_all = false
					//a.judgeLength=0
					// this.judgeLength.push(0)
				})


			},



			/* 
				收起，展开折叠信息逻辑介绍
				isShowComment:false,//comment是否显示,通过底部监听器判断
				show_all:false,//是否显示其他条数，如果为false不显示
				judgeLength:0,//当点击了展示剩余信息（showAll）后，变为9999那么就展示了
				
				首先通过v-if判断，v-if="index_2>judgeLength?false:true"，是否展示剩余评论
				然后 v-if="comment_1.comments_2.length>1&&show_all===false?true:false"
				点击show_all后展示其他内容，那么judgeLength会由0变为9999，被折叠的内容自然就显示了
				
				收起unShowAll，操作相反即可
			 */

			// 点击显示折叠的信息
			showAll(index) {

				this.comments[index].show_all = true
				this.comments[index].judgeLength = 9999
			},
			unShowAll(index) { //点击收起折叠信息
				this.comments[index].show_all = false
				this.comments[index].judgeLength = 0
			},

			/* 
				评论点赞
			 */
			async likeAction(index_1, index_2, type, comment_id) {

				const result = await this.$myRequest({
					url: '/comment/commentlike/' + comment_id + '/' + this.flea_id + '/' + type+"/"+this.goodsId
				})
				console.log("评论点赞结果：", result.data)

				if (index_2 === null) {
					if (type === 1) {
						this.comments[index_1].likeJudge = 1
						this.comments[index_1].comment_like++
					} else {
						this.comments[index_1].likeJudge = 0
						this.comments[index_1].comment_like--
					}
				} else {
					if (type === 1) {
						this.comments[index_1].child_comment[index_2].likeJudge = 1
						this.comments[index_1].child_comment[index_2].comment_like++
					} else {
						this.comments[index_1].child_comment[index_2].likeJudge = 0
						this.comments[index_1].child_comment[index_2].comment_like--
					}
				}

			},

			/**
			 * 删除或举报评论
			 * 比较评论者id是否等于当前用户id，
			 * 如果等于就删除
			 * 不等于就举报
			 * @param {Object} index_1
			 * @param {Object} index_2
			 * @param {Object} comment
			 */
			deleteComment(index_1, index_2, comment) {
				if (comment.observer_id === this.flea_id) {
					uni.showModal({
						// title: '提示',
						content: '是否删除评论？',
						cancelText: "取消",
						confirmText: "确定",
						success: (res) => {
							if (res.confirm) {
								if (index_2 === null) {
									const commentNum = this.comments[index_1].child_comment.length+1
									this.goodsUserInfo.commentNum = this.goodsUserInfo.commentNum-commentNum
									this.comments.splice(index_1, 1)
									const result = this.$myRequest({
										url: '/comment/commentdelete/' + comment.comment_id + '/0'
									})
								} else {
									this.comments[index_1].child_comment.splice(index_2, 1)
									const result = this.$myRequest({
										url: '/comment/commentdelete/' + comment.comment_id + '/1'
									})
									this.goodsUserInfo.commentNum--
								}
							} else if (res.cancel) {
							}
						}
					});
				} else {
					uni.showModal({
						// title: '提示',
						content: '是否举报评论？',
						cancelText: "取消",
						confirmText: "确定",
						success: (res) => {
							if (res.confirm) {
								const result = this.$myRequest({
									url:'/comment/reportComment',
									method:'PUT',
									data:{
										informer_id:this.flea_id,
										rank:index_2===null?0:1,
										violate_comment_id:comment.comment_id,
										violate_user_id:comment.observer_id,
										content:comment.comment,
									}
								})
								if(result){
									uni.showToast({
										title: '举报成功'
									})
								}
								
							} else if (res.cancel) {
							}
						}
					});
				}

			},
		
			//商品点赞
			async goodsLikeAction(){
				const result = await this.$myRequest({
					url:'/product/goodsLike/'+this.flea_id+'/'+this.goodsId+'/'+this.merchantId+'/'+this.goodsUserInfo.goodsLikeJudge
				})
				if(this.goodsUserInfo.goodsLikeJudge){
					this.goodsUserInfo.goodsLike--
				}else{
					this.goodsUserInfo.goodsLike++
				}
				this.goodsUserInfo.goodsLikeJudge=!this.goodsUserInfo.goodsLikeJudge
			},
			//商品收藏
			async goodsCollectAction(){
				
				const result = await this.$myRequest({
					url:'/product/goodsCollect/'+this.flea_id+'/'+this.goodsId+'/'+this.merchantId+'/'+this.goodsUserInfo.goodsCollectJudge
				})
				if(this.goodsUserInfo.goodsCollectJudge){
					this.goodsUserInfo.goodsCollect--
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('decrementCollect')
					}
				}else{
					this.goodsUserInfo.goodsCollect++
					if(this.$store.state.attentionNum!==null){
						this.$store.dispatch('incrementCollect')
					}
				}
				this.goodsUserInfo.goodsCollectJudge=!this.goodsUserInfo.goodsCollectJudge
			},
			
			/**
			 * 开启举报的pop
			 */
			openReportPop(){
				this.$refs.reportpop.open()
				// this.reportGoodsAction()
			},
		
			//取消举报商品
			async unReportGoodsAction(){
				const result = await this.$myRequest({
					url:'/product/goodsUnReport',
					method:'POST',
					data:{
						informer_id:this.flea_id,
						violate_goods_id:this.goodsId,
						violate_user_id:null,
						violate_content:null,
					}
				})
				this.goodsUserInfo.goodsReportJudge=false
				this.$refs.reportpop.close()
				this.list=[
					{
						name:'出售违禁品',
						foldJudge:false,
						values:[
							{
								name:'管制刀具',
								color:null,
								selectJudge:false,
							},
							{
								name:'管制药物',
								color:null,
								selectJudge:false,
							},
							{
								name:'违禁图书、媒体',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'低俗或则不适图片',
						foldJudge:false,
						values:[
							{
								name:'色情图片',
								color:null,
								selectJudge:false,
							},
							{
								name:'不适图片',
								color:null,
								selectJudge:false,
							},
							{
								name:'政治宗教敏感图片',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'虚假信息',
						foldJudge:false,
						values:[
							{
								name:'价格虚报',
								color:null,
								selectJudge:false,
							},
							{
								name:'标题内容不符',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'假货盗版',
						foldJudge:false,
						values:[
							{
								name:'假货',
								color:null,
								selectJudge:false,
							},
							{
								name:'盗版',
								color:null,
								selectJudge:false,
							},
						]
					},
					{
						name:'不适内容',
						foldJudge:false,
						values:[
							{
								name:'挂名辱骂、诋毁他人',
								color:null,
								selectJudge:false,
							},
							{
								name:'标题内容低俗',
								color:null,
								selectJudge:false,
							},
							{
								name:'包含敏感话题',
								color:null,
								selectJudge:false,
							},
						]
					}
					
				]
				this.reportList=[]
			},
			//举报商品
			async reportGoodsAction(){
				console.log("举报信息为："+this.reportList)
				const reports = this.reportList.join(",")
				console.log("举报信息转化为字符串：",reports)
				//const allReports=null
				console.log("this.goodsUserInfo.goodsReportContent为："+this.goodsUserInfo.goodsReportContent)
				if(this.goodsUserInfo.goodsReportContent!==null){
					const historyReports= this.goodsUserInfo.goodsReportContent.join(',')
					this.lastReports=historyReports+','+reports
					
				}else{
					this.lastReports=reports
					//historyReports= this.goodsUserInfo.goodsReportContent.join(',')
					//console.log(" historyReports为："+historyReports)
				}
				
				
				console.log("举报内容为"+this.lastReports)
				const result = await this.$myRequest({
					url:'/product/goodsReport/'+this.goodsUserInfo.goodsReportJudge,
					data:{
						informer_id:this.flea_id,
						violate_goods_id:this.goodsId,
						violate_user_id:this.merchantId,
						// violate_content:historyReports+','+reports,
						violate_content:this.lastReports,
					},
					method:'POST'	
				})
				this.goodsUserInfo.goodsReportJudge=true
				this.$refs.reportpop.close()
			},
			//打开举报弹窗openReportPop
			openReportPop(){
				this.$refs.reportpop.open()
				// this.reportGoodsAction()
			},
			unflodAction(items){
				items.foldJudge=!items.foldJudge
			},
			/**
			 * 选择要举报的内容
			 * @param {Object} item
			 * @param {Object} index1
			 * @param {Object} index2
			 */
			selctReportContent(item,index1,index2){
				if(item.selectJudge){
					item.color='#000000'
					item.selectJudge=false
					this.reportList.splice(this.reportList.indexOf(item.name+'-'+index1+'-'+index2),1) 
				}else{
					item.color='#3EB1D7'
					item.selectJudge=true
					this.reportList.push(item.name+'-'+index1+'-'+index2)
					console.log("reportList为:",this.reportList)
				}
			},
			
			//跳转到商品订单界面
			putOrder(){
				console.log("点击了下单：")
				uni.navigateTo({
					url:'/pages/index/place-order?userId='+this.flea_id+"&goodsId="+this.goodsId+"&merchantId="+this.merchantId+"&price="+this.goodsUserInfo.goodsNowPrice+"&username="+this.goodsUserInfo.observerName+'&goodsTitle='+this.goodsUserInfo.goodsTitle
					
					// url:'/pages/index/place-order'
				})
				console.log("点击了下单2：")
			},
			
			/* 跳转到商品管理页面 */
			goToUpdateProduct(){
				uni.navigateTo({
					url:'/pages/index/addProduct/updateProduct?goodsId='+this.goodsId+'&fleaId='+this.flea_id
				})
			},
			
			/* 请求并判断用户是否对该商品下单 */
			async getOrderStatus(){
				const result = await this.$myRequest({
					url:'/product/judgeGoods/'+this.flea_id+'/'+this.goodsId
				})
				console.log("订单请求结果："+result.data)
				if(result.data===1){
					this.isOrder=true
				
				}else{
					this.isOrder=false
				}
			},
			
			async putBrowseLog(){
				const result = await this.$myRequest({
					url:'/browselog/updatebrowseLog/'+this.flea_id+'/'+this.goodsId
				})
				
			}
			
		},


		/*监听是否触底了，触底了就加载评论 */
		onReachBottom() {
			console.log("pagenum为：" + this.pagenum)
			if (this.comments.length < (this.pagenum - 1) * 4) return this.flag = true //判断是否加载到底了
			if (!this.flag) {
				console.log("加载了")
				this.getComments()
			}
			console.log("触底了")
			this.pagenum++
			this.isShowComment = true
			//加载评论	


		},
		onLoad: function(options) {

			uni.getSystemInfo({
				success: (res) => {
					this.windowHeight = res.windowHeight;
				}
			});
			uni.onWindowResize((res) => {
				if (res.size.windowHeight < this.windowHeight) {
					this.tabbar = false
				} else {
					this.tabbar = true
				}
			})
			//获取跳转过来的userID和goodsId
			this.goodsId = options.goodsId
			this.merchantId = options.userId
			this.userCredit = options.credit
			//获取fleaID
			try {
				const value = uni.getStorageSync('flea_id');
				if (value) {
					this.flea_id = value
					console.log("从storage中获取的flea_id为:" + this.flea_id)
				}
			} catch (e) {
				// error
			}
			//查询用户信息，商品信息
			this.getMerchantInfo()
			
			//判断用户是否已经下单，如果已经下单那么就跳转到管理订单
			this.getOrderStatus()
			
			//浏览记录
			this.putBrowseLog()
		}
	}
</script>

<style lang="scss">
	//顶部
	.top_bar {
		background-color: #fff;
		position: fixed;
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: flex-end;
		height: 140rpx;
		width: 100%;
		z-index: 998;

		.back-icon {
			margin-left: 10rpx;
			margin-bottom: 10rpx;
			font-size: 75rpx;
		}

		.search-father {
			display: flex;
			flex-direction: row;
			margin-right: 30rpx;
			margin-bottom: 10rpx;
			height: 75rpx;
			width: 70%;
			background-color: $uni-color-lightgray;
			border-radius: 35rpx;

			.search-icon {
				margin-left: 20rpx;
				margin-top: 10rpx;
				font-size: 55rpx;
			}

			.search-text {
				margin-left: 10rpx;
				margin-top: 10rpx;
			}

		}

		.share-icon {
			font-size: 55rpx;
			margin-bottom: 15rpx;
		}
	}

	//内容部分
	.content {
		display: flex;
		flex-direction: column;
		margin: 0 auto;
		width: 93%;



		.user {
			display: flex;
			flex-direction: row;
			align-items: flex-end;
			justify-content: space-between;
			height: 120+140rpx;

			.user-info {
				display: flex;
				flex-direction: row;
				margin-bottom: 10rpx;

				.user-icon {

					width: 90rpx;
					height: 90rpx;
					border-radius: 50%;
				}

				.user-name {
					font-weight: 1000;
					margin-left: 20rpx;
					margin-top: 24rpx;
				}
			}

			.fans-user {
				height: 50rpx;
				width: 160rpx;
				margin-right: 25rpx;
				margin-bottom: 30rpx;

				.fans-user-content {
					border-radius: 30rpx;
					background-color: yellow;
					display: flex;
					flex-direction: row;
					justify-content: space-evenly;
					align-items: center;
					margin-top: -2rpx;

					.fans-icon {
						margin-left: 5rpx;
						margin-top: 10rpx;
						font-size: 50rpx;
					}

					.fans-text {
						margin-right: 7rpx;
					}
				}

				.fans-user-content2 {

					background-color: $uni-color-midgray;
					color: $uni-color-lightgray;
					border-radius: 30rpx;

					display: flex;
					flex-direction: row;
					justify-content: space-evenly;
					align-items: center;
					margin-top: -2rpx;
					height: 65rpx;


				}



			}

		}

		//商品内容
		.goods {
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			min-height: 950rpx;

			// background-color: purple;
			//价格
			.goods-price {
				display: flex;
				flex-direction: row;
				margin-bottom: 25rpx;

				.goods-price-now {

					margin-top: 20rpx;
					//background-color: blue;
					font-size: 30rpx;

					color: $uni-color-red;

					text {
						font-size: 60rpx;
					}
				}

				.goods-price-old {
					margin-top: 45rpx;
				}

				.goods-price-old text {

					margin-bottom: 5rpx;
					color: $uni-color-midgray;
					margin-left: 10rpx;
					text-decoration: line-through;
				}

			}

			.goods-title{
				// background-color: red;
				padding: 20rpx 0;
				margin-bottom: 20rpx;
				font-size: 40rpx;
			}
			
			//介绍
			.good-introduce {
				color: $uni-color-gray;
			}

			//tag,品牌，成色等
			.tag {
				display: flex;
				flex-direction: column;
				justify-content: flex-start;
				margin-top: 35rpx;

				.tag-1 {
					margin: 8rpx 0 0;
					display: flex;
					flex-direction: row;
					justify-content: flex-start;

					.tag-title {


						padding-bottom: 5rpx;
						line-height: 60rpx;
						color: $uni-color-midgray;
						border-radius: 20rpx;

					}

					.tag-content {
						font-weight: 1000;
						margin-left: 30rpx;
						margin-right: 10rpx;
						text-align: center;
						line-height: 60rpx;
					}
					.goods-type{
						display: flex;
						flex-direction: row;
						margin-left: 20rpx;
						.midicon{
							line-height: 60rpx;
						}
						.tag-content {
							font-weight: 1000;
							margin-left: 10rpx;
							margin-right: 10rpx;
							text-align: center;
							line-height: 60rpx;
						}
					}
					// <view class="goods-type">
					// 	<view class="tag-content">{{goodsUserInfo.atype}}</view>
					// 	<view class="iconfont midicon">&#xe748;</view>
					// 	<view class="tag-content">{{goodsUserInfo.btype}}</view>
					// 	<view class="iconfont midicon">&#xe748;</view>
					// 	<view class="tag-content">{{goodsUserInfo.ctype}}</view>
					// </view>
					
				}
			}

			//照片
			.goods-photos {
				margin-top: 40rpx;
				width: 100%;

				image {
					width: 100%;
					border-radius: 12rpx;
				}
			}

			//others,点赞，想要（收藏），举报
			.goods-else {
				display: flex;
				flex-direction: row;
				justify-content: flex-end;
				color: #9e9e9e;
				font-size: 30rpx;
				margin-bottom: 10rpx;

				.segment {
					height: 8rpx;
					width: 8rpx;
					background-color: #2C405A;
					margin: 10rpx;
					margin-top: 20rpx;
					border-radius: 50%;
					
				}

				.report {
					font-size: 30rpx;

				}
				
				.report-pop{
					border-radius: 10rpx;
					position: relative;
					background-color: #fff;
					width: 400rpx;
					height: 500rpx;
					padding: 40rpx;
					.pop-content{
						height: 430rpx;
						width: 400rpx;
						
						.option-f{
							display: flex;
							flex-direction: row;
							justify-content: space-between;
							align-items: center;
							margin: 10rpx;
							font-size: 40rpx;
						}
						.option-c{
							margin: 8rpx;
							.option-c-item{
								margin: 5rpx;
							}
						}
					}
					
					.pop-button{
						display: flex;
						flex-direction: row;
						justify-content: space-between;
						.pop-button-cancel{
							width: 160rpx;
							height: 60rpx;
							text-align: center;
							line-height: 60rpx;
							border-radius: 15rpx;
							color: #fff;
							background-color: $uni-color-blue;
						}
						.pop-button-confirm{
							width: 160rpx;
							height: 60rpx;
							text-align: center;
							line-height: 60rpx;
							border-radius: 15rpx;
							color: #fff;
							background-color: $uni-color-orange;
						}
					}
				}
			}
		}
	}

	//一段分割线
	.segment-line {
		width: 100%;
		height: 30rpx;

		background-color: $uni-color-lightgray;
	}

	// 评论部分
	.comment {

		width: 93%;
		margin: 0 auto;


		.comment-title {
			margin-top: 20rpx;
			width: 100%;

			.title-left {


				display: flex;
				flex-direction: row;
				justify-content: flex-start;

				text {}

				.segment {
					height: 8rpx;
					width: 8rpx;
					background-color: #2C405A;
					margin: 20rpx;
					border-radius: 50%;
				}
			}
		}

		.comment-add {


			display: flex;
			flex-direction: row;
			justify-content: space-between;
			margin-top: 20rpx;

			.user-icon {
				border-radius: 50%;
				height: 60rpx;
				width: 60rpx;
			}

			.comment-input {
				background-color: #f1f1f1;
				width: 87%;
				border-radius: 20rpx;
				padding: 10rpx;
				font-size: 30rpx;
				margin-bottom: 30rpx;
			}
		}

		.comment-all {

			width: 100%;

			margin-top: 10rpx;


			.comment-1 {
				//第一层
				display: flex;
				flex-direction: column;

				.comment-1-1 {

					display: flex;
					flex-direction: column;

					.comment-1-rank1 {

						display: flex;
						flex-direction: row;
						justify-content: space-between;

						.comment-1-rank1-user {

							display: flex;
							flex-direction: row;

							.comment-1-icon {
								//用户icon
								border-radius: 50%;
								height: 60rpx;
								width: 60rpx;
							}

							.comment-1-name {
								//用户名
								font-size: 30rpx;
								line-height: 60rpx;
								margin-left: 15rpx;
								font-weight: 900;
							}
						}

						.like-icon {
							//点赞图标
							line-height: 60rpx;
							font-size: 50rpx;
						}

						.rank1-like-num {
							background-color: yellow;
						}

					}


					.comment-1-rank2 {
						margin-left: 65rpx;
						margin-top: 20rpx;

						display: flex;
						flex-direction: column;
						align-items: flex-start;

						.comment-1-content {
							//留言内容

							wdith: 90%;

						}

						.comment-1-date {
							//留言时间
							margin-top: 10rpx;
							font-size: 20rpx;
						}
					}


				}


				.comment-2-f {
					display: flex;
					flex-direction: column;


					.comment-2 {
						margin: 10rpx 0;
						background-color: #f3f3f3;
						border-radius: 10rpx;
						padding: 10rpx;
						//background-color: purple;
						width: 626rpx;
						display: flex;
						flex-direction: column;
						margin-left: 65rpx;

						.comment-2-rank1 {

							width: 100%;
							display: flex;
							flex-direction: row;
							justify-content: space-between;
							margin-top: 15rpx;

							.comment-2-rank1-user {

								display: flex;
								flex-direction: row;

								.comment-2-icon {
									//用户icon
									border-radius: 50%;
									height: 50rpx;
									width: 50rpx;
								}

								.comment-2-name {
									//用户名
									font-size: 30rpx;
									line-height: 50rpx;
									margin-left: 15rpx;
									font-weight: 900;
								}
							}

							.like-icon {
								//点赞图标
								line-height: 60rpx;
								font-size: 50rpx;
								margin-right: -10rpx;
							}
						}

						.comment-2-rank2-f {
							display: flex;
							flex-direction: row;

							.blank {
								height: 60rpx;
								width: 72rpx;
							}

							.comment-2-rank2 {
								margin-top: 20rpx;

								display: flex;
								flex-direction: column;
								align-items: flex-start;

								.comment-2-content {
									//留言内容

									wdith: 90%;

								}

								.comment-2-date {
									//留言时间
									margin-top: 10rpx;
									font-size: 20rpx;
								}
							}
						}

					}

					.pull-down {
						display: flex;
						flex-direction: row;
						justify-content: center;

						text {
							font-size: 30rpx;
						}

						.pull-down-icon {
							line-height: 40rpx;
							font-size: 40rpx;
						}
					}
				}
			}

			.blank-comment {
				height: 3rpx;
				background-color: #C0C0C0;
				margin: 17rpx 0;
				width: 100%;
			}
		}

		.blank-bottom {
			height: 100rpx;
			width: 100%;
			background-color: transparent;
		}


	}
	//发送评论的弹窗
	.sendmessage-f-f {
		background-color: #fff;
		width: 100%;
		position: fixed;
		z-index: 1000;
		bottom: 0rpx;

		.sendmessage-f {
			width: 98%;
			position: relative;
			margin: 0 auto;

			display: flex;
			flex-direction: row;
			justify-content: space-between;
			background-color: #dddddd;
			border-radius: 15rpx;

			height: 80rpx;

			.sendmessage-i {
				height: 80%;
				line-height: 40rpx;
				padding: 10rpx;
				// background-color: #dddddd;
				width: 80%;

			}

			.sendmessage-b {
				background-color: $uni-color-blue;
				line-height: 60rpx;
				text-align: center;
				padding: 10rpx;
				height: 76%;
				border-radius: 0 15rpx 15rpx 0;
				width: 15%;
			}
		}
	}

	//底部 
	.bottom-bar {
		position: fixed;
		bottom: 0;
		width: 97%;
		height: 100rpx;
		background-color: #fff;
		.observer-view{
			width: 100;
			height: 100rpx;
			
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;
			.observer—left{
				margin-left: 20rpx;
				height: 100%;
				width: 30%;
				display: flex;
				flex-direction: row;
				justify-content: space-around;
				
				.observer-like{
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					.like{
						
						font-size: 60rpx;
					}
					text{
						line-height: 20rpx;
						text-align: center;
						font-size: 20rpx;
					}
					
				}
				.observer-collect{
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					.collect{
						font-size: 60rpx;
					}
					text{
						line-height: 20rpx;
						text-align: center;
						font-size: 20rpx;
					}
					
				}
				.observer-comment{
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					.comment{
						font-size: 60rpx;
					}
					text{
						
						line-height: 20rpx;
						text-align: center;
						font-size: 20rpx;
					}
					
				}
			}
			.observer-right{
				width: 20%;
				height: 70rpx;
				
				line-height: 70rpx;
				margin-right: 15rpx;
				text-align: center;
				border-radius: 30rpx;
				color: #f0f0f0;
			}
		}
		.merchant-view{
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			margin-top: 15rpx;
			.observer-right{
				width: 25%;
				height: 70rpx;
				background-color: #ffaa00;
				line-height: 70rpx;
				margin-right: 15rpx;
				text-align: center;
				border-radius: 30rpx;
				color: #fff;
				.put-order{
					background-color: #ffaa00;
					border-radius: 30rpx;
					width: 100%;
					height:100%;
				}
			}
		}
	}
	
	.loading{
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		height: 1100rpx;
		.loading-content{
			font-size: 40rpx;
			color: $uni-color-midgray;
		}
	}
</style>
