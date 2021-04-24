const BASE_URL = 'http://127.0.0.1:8083' /* 因为以后域名可能会更换 */
export const myRequest =(options)=>{
	// 异步的方法使用Promise来封装
	return new Promise((resolve,reject)=>{
		uni.request({
			url:BASE_URL+options.url,
			method:options.method || 'GET',
			data:options.data || {},
			success:(res)=>{
				console.log(res)
				resolve(res)
			},
			fail:(err)=>{
				uni.showToast({
					title:'请求接口失败'
				})
				console.log(err)
				reject(err)
			},
		})
	})
}
// 生成uuid
export const uuid = function uuid() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}

export const sendEmail = function sendEmail(email,content){
	console.log("in",email,content)
	/* 发送订单消息给卖家 */
	const res = this.$myRequest({
		url:'/notify/sendemail/'+email+'/'+content,
		method:"PUT"
	})
	return res.data
	// uni.request({
	// 	url:BASE_URL+'/notify/sendemail/'+email+'/'+content,
	// 	method:"PUT",
	// 	success: (res) => {
	// 		return res.data
	// 	},
	// 	fail: () => {
	// 		uni.showToast({
	// 			title:'请求接口失败'
	// 		})
	// 	}
	// })
		
	
	
}




