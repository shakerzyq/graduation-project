<template>
	<view>
		<view class="upload" @click="chooseImg(0)">
			<button class="upload_icon iconfont" type="primary">&#xe8a1;</button>
			<image class="img" :src="this.imgArr[0]"></image>
			<text>上传头像</text>
		</view>
		<view class="personal_info">
			<view class="person">昵称</view>
			<input class="info_input" placeholder="请输入昵称"  v-model="user.nickname"/>
			<view class="person">性别</view>
			<radio-group class="radios" @change="radioChange">
				<radio class="radio" value="man" name="sex" checked="true">男</radio>
				<radio class="radio" value="woman" name="sex">女</radio>
			</radio-group>

			<view class="person">姓名</view>
			<input class="info_input" placeholder="请输入姓名" v-model="user.name"/>
			<view class="person">学号</view>
			<input class="info_input" placeholder="请输入学号" v-model="user.stu_num"/>
			<view class="person">微信号</view>
			<input class="info_input" placeholder="输入微信号" v-model="user.weixin"></input>
			<view class="person">学院</view>
			<input class="info_input" placeholder="输入微信号" v-model="user.academy"></input>
			<view class="person">专业班级</view>
			<input class="info_input" placeholder="输入微信号" v-model="user.grade"></input>
		</view>
		<view class="info">
			<view class="province">省份</view>
			<picker mode="selector" @change="choiceProvince" :range="provinces">{{user.province}}</picker>
			<view class="province">学校</view>
			<picker mode="selector" @change="choiceCollege" :range="schools">{{user.college}}</picker>
			<view class="province">校区</view>
			<picker mode="selector" @change="choiceArea" :range="areas">{{user.area}}</picker>
		</view>
		<view class="contact">
			
		</view>
		<view class="introduce" >
			<textarea placeholder="说点什么？" v-model="user.self_introduction">
			</textarea>
		</view>
		<view class="uploadPhoto" @click="chooseImg(1)">
			<button class="upload_icon iconfont" @click="chooseImg" type="primary">&#xe8a1;</button>
			<image class="img" :src="this.photoArr[0]"></image>
			<text>上传学生证照片</text>
		</view>
		<button @click="allFinsh" class="finish" :disabled="button_status">注册</button>
		<view class="demo"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				/* 黑龙江省、吉林省、辽宁省、河北省、河南省、山东省、
				山西省、安徽省、江西省、江苏省、浙江省、福建省、
				台湾省、广东省、湖南省、湖北省、海南省、
				云南省、贵州省、四川省、青海省、甘肃省、陕西省 */
				provinces: ['安徽', '北京', '重庆', '福建', '广东', '甘肃',
					'广西', '贵州', '河北', '黑龙江', '河南', '海南', '湖北', '湖南',
					'吉林', '江苏', '江西', '辽宁', '宁夏', '内蒙古', '青海', '四川', '山东', '上海',
					'陕西', '山西', '天津', '新疆', '西藏', '云南', '浙江'
				],
				province: '安徽',
				list:[],
				schools: [],
				areas:[],
				school: null,
				imgArr: [],
				photoArr: [],
				img: 'upload_icon iconfont',
				button_status: true,
				user: {
					flea_id:null,
					email:null,
					sex: 'man',
					nickname: null,
					name: null,
					stu_num: null,
					province: '安徽',
					college: null,
					area:null,
					academy:null,
					grade:null,
					user_icon:null,
					testify_img:null,
					self_introduction:null,
					status:'待审核',
					credit:100,
					weixin:null
				},
				account:{
					flea_id:null,
					email:null,
					password:null,
				},
				password:null,
				email:null
			}
		},
		methods: {
			// 监听radio
			radioChange(e){
				this.user.sex=e.detail.value
			},
			chooseImg(num) {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						if (num === 0) {
							this.imgArr = res.tempFilePaths
							
						} else {
							this.photoArr = res.tempFilePaths	
						}
					}
				})
			},
			// 上传图片到服务器
			uploadImage(filepath,type) {
				uni.uploadFile({
					url: 'http://127.0.0.1:8083/register/upload/user_photos',
					filePath: filepath,
					name: 'file',
					formData: {
						'user': 'test'
					},
					success: (uploadFileRes) => {
						if(type===0){
							this.user.user_icon=uploadFileRes.data//上传的是头像
						}else{
							this.user.testify_img = uploadFileRes.data//上传的是证明照片
						}
						
						console.log("上传图片结果"+uploadFileRes.data);
					}
				})
			},
			//选择省份
			choiceProvince(e) {
				this.user.province = this.provinces[e.detail.value]
				
				console.log(this.province)
				this.getColleges(this.user.province)

			},
			//选择大学
			choiceCollege(e) {
				this.user.college = this.schools[e.detail.value]
				this.user.area=this.list[e.detail.value].area[0]
				this.areas=this.list[e.detail.value].area
				console.log("user的college为"+this.user.college)
			},
			//选择校区
			choiceArea(e){
				this.user.area = this.areas[e.detail.value]
			},
			//通过省份获取大学
			async getColleges(province) {
				const list = await this.$myRequest({
					url: '/register/college/' + province
				})
				this.list = list.data
				this.list.forEach((a)=>{
					console.log("学校：",a.college)
					this.schools.push(a.college);
				})
				console.log("schools",this.schools)
				
				this.areas=this.list[0].area
				this.user.college = this.schools[0]
				this.user.area=this.list[0].area[0]
			},
			allFinsh(){
				this.uploadImage(this.imgArr[0],0)
				this.uploadImage(this.photoArr[0],1)
				console.log("user为："+this.user)
				console.log("account为："+this.account)
				this.user.email=this.email
				 setTimeout(()=> {
					const result =  this.$myRequest({
						url:"/register/putInfo",
						data:{
							user:this.user,
							account:this.account,
						},
						method:'POST'
					})
				}, 1000);
				
				
				uni.showToast({
					title:"已发送注册申请等待管理员审核",
					duration:5000
				})

				this.navigator()
				
			},			
			navigator(){
				setTimeout(()=>{
					uni.reLaunch({
						url:'../../login/login'
					})
				},5000)
			}
		},
		onLoad:function(option){
			console.log("密码为",option.pwd,"账号为",option.email)
			this.account.email = option.email
			this.account.password = option.pwd
			this.getColleges(this.provinces[0])
		},
		onReachBottom(){
			console.log(this.user)
			
			if(this.user.name===null||this.user.nickname===null||this.user.sex===null||this.user.stu_num===null||this.user.province===null||this.user.college===null){
				this.button_status=true
			}else{
				this.button_status=false
			}
			console.log("已经到达最底部了")
		}
	}
</script>

<style lang="scss">
	.introduce{
		width: 80%;
		height: 150rpx;
		border: 1px solid black;
		margin: 0 auto;
		margin-top: 30rpx;
		border-radius: 5rpx;
		
	}
	
	.img {
		position: absolute;
		height: 100%;
		width: 100%;
		border-radius: 5px;
		top: 0rpx;
		left: 0rpx;
		z-index: 2;
	}

	.upload {
		position: relative;
		width: 200rpx;
		margin: 0 auto;
		top: 30rpx;

		.upload_icon {
			border-radius: 5px;
			height: 200rpx;
			width: 200rpx;
			line-height: 200rpx;
			background-color: transparent;
			color: black;
			font-size: 100rpx;
			border: 1px solid black;
		}

		text {

			display: inline;
			position: absolute;
			font-size: 30rpx;
			bottom: 0;
			margin-bottom: 10rpx;
			left: 40rpx;
		}
	}

	.uploadPhoto {
		position: relative;

		width: 80%;
		left: 75rpx;
		top: 50rpx;

		.upload_icon {
			height: 300rpx;
			width: 100%;
			line-height: 200rpx;
			background-color: transparent;
			color: black;
			font-size: 100rpx;
			border: 1px solid black;
		}

		text {
			display: inline;
			position: absolute;
			font-size: 35rpx;
			bottom: 50rpx;
			margin-bottom: 10rpx;
			left: 180rpx;
		}
	}

	.info {
		width: 80%;
		margin:0 auto;
		margin-top: 20rpx;

		.province,
		.school {
			border-bottom: 1px solid gray;
			font-size: 50rpx;
		}

		picker {
			text-align: center;
			font-size: 40rpx;
		}
	}

	.personal_info {
		position: relative;

		width: 80%;
		margin: 0rpx auto 0;

		.person {
			font-size: 50rpx;
			padding: 20rpx 0;
		}

		.radios {
			position: absolute;
			left: 150rpx;
			bottom: 1010rpx;

			.radio {
				padding-right: 20rpx;
			}
		}

		.info_input {
			padding: 5rpx 15rpx;
			border-radius: 10rpx;
			height: 80rpx;
			border: 1px solid black;
		}
	}

	.finish {
		width: 80%;
		height: 80rpx;
		line-height: 80rpx;
		margin-top: 80rpx;
	}

	.demo {
		height: 50rpx;
	}
</style>
