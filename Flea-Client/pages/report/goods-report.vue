<template>
	<view>
		<uni-popup ref="reportpop" type="center" >
			<view class="report-pop">
				<scroll-view class="pop-content" scroll-y="true">
					<view v-for="(items,index) in list" :key="index">
						
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
							
							<view class="option-c-item" v-for="(item,index2) in items.values" :style="{color:item.color}" :key="index2" v-if="items.values.length===0?false:true"  @click="selctReportContent(item)">
								{{item.name}}
							</view>
						</view>
					</view>
				</scroll-view>
				<view class="pop-button" @click="reportGoods">确定举报</view>
			</view>
		</uni-popup>
		
	</view>
</template>

<script>
	export default{
		data(){
			return{
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
								name:'违禁图书,媒体',
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
								name:'挂名辱骂，诋毁他人',
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
			}
		},
		methods:{
			openReportPop(){
				this.$refs.reportpop.open()
				// this.reportGoodsAction()
			},
			unflodAction(items){
				items.foldJudge=!items.foldJudge
			},
			selctReportContent(item){
				if(item.selectJudge){
					item.color='#000000'
					item.selectJudge=false
				}else{
					item.color='#3EB1D7'
					item.selectJudge=true
				}
			}
		}
	}
</script>

<style lang="scss">
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
			position: absolute;
			width: 160rpx;
			height: 60rpx;
			border-radius: 15rpx;
			background-color: $uni-color-blue;
			color: #fff;
			text-align: center;
			line-height: 60rpx;
			bottom: 30rpx;
			right: 30rpx;
		}
	}
</style>
