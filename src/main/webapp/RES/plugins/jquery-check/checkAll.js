
;(function(window, $){

	var method=function(input){
		this.index=0;
		this.ajaxFF=false;
		this.target=input; 	

	}

	method.prototype={

		//创建提示层
		createBox:function(tipText){
			this.index++;
			var box=document.createElement("div");
			box.className="formErrorIndex"+this.index+" parentFormformID formError";
			box.style.position="absolute";
			box.style.opacity=0;
			box.innerHTML='<div class="formErrorContent">'+tipText+'<br></div><div class="formErrorArrow"><div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div></div></div>';
			return box;
		},
		//添加提示层
		appendBox:function(checkInput,tipText){

			var target=checkInput;
			if(!($(target).nextAll(".parentFormformID")[0])){
				var node=this.createBox(tipText);
				node.style.left=$(target).width()-20+"px";
				node.style.top=-40+"px";
				$(target).parent().append(node);
				this.opacityBox(node,1);
				this.clickBox(node);
			}
		},
		//透明为1
		opacityBox:function(box,num){
			$(box).animate({
				"opacity":num
			},150,function(){
				if(num === 0){
					$(box).remove();
				}
			});
		},
		//点击提示层关闭
		clickBox:function(box){
			box.onclick=function(){
				var that=$(this);
				that.animate({
					"opacity":0
				},150,function(){
					that.remove();
				})
			}
		},

		blurBox:function(input,saveBtn,extend,succes,isAuto){
			
			var options=function(){
				
			}
			var _method=this
			var extBlur = function(target, fun, type){
				$(target).unbind(type);
				switch(type){
					case "blur":
						$(target).on("blur",function(){
							var that=this;

							//执行扩展的自定义方法
							extendCent(that,fun);
						});
					break;
					case "change":
						$(target).on("change",function(){
							var that=this;

							//执行扩展的自定义方法
							extendCent(that,fun);
						});
					break;
				}
			}
			var extendCent = function(that,fun){
				//返回的value，进行判断
				var returnValue=fun(that.value);
				
				if(returnValue === ""){
					var remove=new options().isBox("remove",that);
						//checkIndex++;
					return "jia";
					
				}else{
					
					var append=new options().isBox("append",that,returnValue);
					//firstCheck.push(check[i]);
					return "push";
				}
				
			}
			var NoCheckScroll = function(zHei,firstInput){
				$("body,html").animate({
					scrollTop:zHei-40+"px"
				},250,function(){
					firstInput.focus();
				})
			}
			options.prototype={

				events:function(checkInput){

					var check = checkInput.push ? checkInput : $(checkInput),
						checkIndex=0,
						firstCheck=[],	//第一个校验错误的input框
						_options=this;
					//如果能为空
					var isNull = function(check,value){
						if(check.className.indexOf("validate[null]")>-1){
							if(value === ""){
								var tipBox=$(check).nextAll(".parentFormformID")[0];
								that_All.opacityBox(tipBox,0);
								checkIndex++;
								return true;
							}
						}else return false;
					}

					var checkEach = function(){
						for (var i = 0; i < check.length; i++) {

							var classes=check[i].className,	//获取当前的class
								clasReg=/validate\[\w+\]/g,	//匹配方式
								value=check[i].value,		//当前input的value
								type=classes.match(clasReg);	//根据class来检验input框
							var check_now=check[i];
							if(!type){
								continue;
							}
							var isFF=isNull(check[i],check[i].value);
							if(isFF){
								continue;
							};
							if(check[i].value === ""){
								var append = _options.isBox("append",check[i],"此处不能为空");
								firstCheck.push(check[i]);
								continue;

							}
							var specialCharReg = /[\'\&\<\>\\]/g;
							if(specialCharReg.test(check[i].value)){
								var append = _options.isBox("append",check[i],"不能输入><'\\&特殊字符");
								firstCheck.push(check[i]);
								continue;
							}


							switch(type[0]){
								case "validate[required]":

									//判断如果文本框是否为空

									if(check[i].value === ""){
										var append=_options.isBox("append",check[i],"此处不能为空");
										firstCheck.push(check[i]);

									}
									else{
										_options.isBox("remove",check[i]);
										checkIndex++;
									}
								break;
								case "validate[remove]":

									
									_options.isBox("remove",check[i]);
									checkIndex++;
									
								break;
								case "validate[blank]":
									//不能全为空格
									var spaceReg=/^(\s+)$/g;
									if(spaceReg.test(check[i].value)){
										var append=_options.isBox("append",check[i],"此处不能全为空格");
										firstCheck.push(check[i]);
										
									}else{
										_options.isBox("remove",check[i]);
										checkIndex++;
									}
								break;
								case "validate[phone]":



									//判断手机号码
									var phoneReg=/^1[3|5|6|7|8|9]\d{9}/;
									if(phoneReg.test(value)){
										
										var spaceReg=/\s/g;
										if(spaceReg.test(check[i].value)){
											var append=_options.isBox("append",check[i],"不能输入空格");
											firstCheck.push(check[i]);
											
										}else{
											_options.isBox("remove",check[i]);
											checkIndex++;
										}

									}else{
										_options.isBox("append",check[i],"手机号不正确");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[Email]":



									//检验邮箱
									var EmailReg=/([a-zA-Z0-9])+(\@\w+\.\w+)/g;
									if(EmailReg.test(value)){
										var spaceReg=/\s/g;
										if(spaceReg.test(check[i].value)){
											var append=_options.isBox("append",check[i],"不能输入空格");
											firstCheck.push(check[i]);

										}else{
											_options.isBox("remove",check[i]);
											checkIndex++;
										}
									}else{
										_options.isBox("append",check[i],"Email格式不正确");
										firstCheck.push(check[i]);
									}

								break;
								case "validate[code]":

									var codeReg = /^[0-9a-zA-Z]+$/g;
									if(codeReg.test(value)){

										_options.isBox("remove",check[i]);
										checkIndex++;

									}else{
										_options.isBox("append",check[i],"只能输入英文和数字");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[manID]":

									var manidReg = /^(\d{16,17})([0-9xX])?$/g;
									if(manidReg.test(value)){

										_options.isBox("remove",check[i]);
										checkIndex++;

									}else{
										_options.isBox("append",check[i],"输入正确的身份证号");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[noSymbol]":
									var noSymbolReg = /^[0-9a-zA-Z\u4E00-\u9FA5\'\&\<\>\\]+$/g;
									// var tszfReg = /[`~!#\$%\^\&\*\(\)_\+<>\?:"\{\},\\\/;'\[\]]/im;
									if(noSymbolReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"不能输入特殊字符");
										firstCheck.push(check[i]);
									}
								break;
                                //可为空的情况下校验不能输入特殊字符（一般只能输入数字、英文和汉字）
                                case "validate[noSpecial]":
									var noSpecialReg = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/g;
									// var tszfReg = /[`~!#\$%\^\&\*\(\)_\+<>\?:"\{\},\\\/;'\[\]]/im;
									if(check[i].value === "" || noSpecialReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"不能输入特殊字符");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[eng]":

									var noSymbolReg = /^[a-zA-Z]+$/g;
									if(noSymbolReg.test(value)){

										_options.isBox("remove",check[i]);
										checkIndex++;

									}else{
										_options.isBox("append",check[i],"只能输入英文");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[money]":

									var moneyReg = /^([1-9]{1}(\d+)?(\.\d{1,2})?)$|([0]\.\d{1,2})$/g;
									var splitDian = value.split(".");
									if(splitDian && splitDian[1] && splitDian[1].length > 2){
										_options.isBox("append",check[i],"小数点不要超过两位");
										firstCheck.push(check[i]);
									}else if(moneyReg.test(value)){

										_options.isBox("remove",check[i]);
										checkIndex++;

									}else{
										_options.isBox("append",check[i],"只能输入金钱");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[percentage]":

									var percentageReg = /(^([1-9]{1}(\d)*(\.\d{1,2})?)$)|(^([0](\.\d{1,2})?)$)/g;
									var splitDian = value.split(".");
									if(splitDian && splitDian[1] && splitDian[1].length > 2){
										_options.isBox("append",check[i],"小数点不要超过两位");
										firstCheck.push(check[i]);
									}else if(percentageReg.test(value)){
										if(Number(value) > 100){
											_options.isBox("append",check[i],"不能超过100");
											firstCheck.push(check[i]);
										}else if(Number(value) <= 0){
											_options.isBox("append",check[i],"必须大于0小于100");
											firstCheck.push(check[i]);
										}else{
											_options.isBox("remove",check[i]);
											checkIndex++;
										}
									}else{
										_options.isBox("append",check[i],"只能输入百分比");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[foursix]":

									var foursixReg = /^\s*[0-9]{4,6}\s*$/;
									if(foursixReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"只能输入4-6个数字");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[chineseNumber]":

									var foursixReg = /^[\u4E00-\u9FA50-9]*$/;
									if(foursixReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"只能输入中文和数字");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[twoten]":
									//var twotenReg=/^[0-9a-zA-Z\u4E00-\u9FA5]{2,10}/g;
									// var twotenReg = /^\s*[0-9]{2,10}\s*$/;
									if(value.length >= 2){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"只能输入2-10个字");
										firstCheck.push(check[i]);
									}
						
								break;
								case "validate[tszf]":
									var tszfReg = /^([a-zA-Z0-9\u4E00-\u9FA5\'\&\<\>\\]+)$/g;
									if(tszfReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"不能输入特殊字符");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[no0]":

									var percentageReg = /^[1-9]{1}(\d{1,})?$/g;
									if(percentageReg.test(value)){
										if(value[0] === "0"){
											_options.isBox("append",check[i],"第一位不能输入0");
											firstCheck.push(check[i]);
										}else{
											_options.isBox("remove",check[i]);
											checkIndex++;
										}

									}else{
										_options.isBox("append",check[i],"只能输入整数");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[MaxDate]":

									MaxMin(">",i,"日期应大于当前日期",check_now);

								break;
								case "validate[MinDate]":
									var isFF=isNull(check[i],check[i].value);
									if(isFF){
										continue;
									};
									
									MaxMin("<",i,"日期应小于当前日期",check_now);
									

								break;
								case "validate[tel]":
									//校验电话号码
									var telReg=/^(\d{3,4}\-\d{1,8})(\-\d+)?$/g;


									if(telReg.test(value)){
										var spaceReg=/\s/g;
										if(spaceReg.test(check[i].value)){
											var append=_options.isBox("append",check[i],"不能输入空格");
											firstCheck.push(check[i]);
											
										}else{
											_options.isBox("remove",check[i]);
											checkIndex++;
										}
									}else{
										_options.isBox("append",check[i],"电话号码格式不正确");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[letter]":
									var letterReg=/^[a-zA-Z]+$/g;
									if(letterReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"此处只能输入字母");
										firstCheck.push(check[i]);
									}

								break;
								case "validate[number]":
									var letterReg=/^[0-9a-z]+$/g;
									if(letterReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"此处只能输入数字和字母");
										firstCheck.push(check[i]);
									}

								break;
								case "validate[number2]":
									var letterReg=/^[0-9]+$/g;
									if(letterReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"此处只能输入数字");
										firstCheck.push(check[i]);
									}

								break;
								case "validate[num]":
									var letterReg=/(^[1-9]{1}[0-9]*$)|^[0]{1}$/g;
									if(letterReg.test(value) || value === "0"){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"此处只能输入整数");
										firstCheck.push(check[i]);
									}

								break;
								//小数点后两位
								case "validate[numtwo]":
									var letterReg=/^(0|[1-9][0-9]*)?(\.\d{1,2})?$/g;
									// var letterReg=/^([1-9]{1})(\d{1,})?(\.\d{1,2})?$/g;
									var splitDian = value.split(".");
									if(splitDian && splitDian[1] && splitDian[1].length > 2){
										_options.isBox("append",check[i],"小数点不要超过两位");
										firstCheck.push(check[i]);
									}else if(letterReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"此处只能输入数字");
										firstCheck.push(check[i]);
									}

								break;
								case "validate[http]":
									var httpReg=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\?%&=]*)?/;
									if(httpReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"链接输入不正确");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[ZeroFour]":
									var numReg=/^[0-4]+$/g;
									if(numReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"只能输入0-4");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[custom_tel]":
								var value=check[i].value,
									phoneReg=/^[0-9\\-]{5,20}$/;

								if(phoneReg.test(value)){
									var tipBox=$(check[i]).nextAll(".parentFormformID")[0];
									that_All.opacityBox(tipBox,0);
									checkIndex++;
								}else{
									var isFF=isNull(check[i],value,"电话格式不正确！");
									if(!isFF){
										that_All.appendBox(check[i],"电话格式不正确！");
										firstCheck.push(check[i]);
									}
									
								}
								break;								
								case "validate[letNum]":
									var letterReg=/^[0-9a-zA-Z]+$/g;
									if(letterReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"此处只能输入数字和字母");
										firstCheck.push(check[i]);
									}
								break;
								case "validate[telPhone]":

									var telReg=/^\d{3,4}-\d{1,}((-\d+)?)$/g,
										phoneReg=/^1[3|5|6|7|8|9]{1}\d{9}$/g;
									if(phoneReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else if(telReg.test(value)){
										_options.isBox("remove",check[i]);
										checkIndex++;
									}else{
										_options.isBox("append",check[i],"电话号码格式不正确");
										firstCheck.push(check[i]);
									}
								break;
                                //校验手机号码、固定电话、带区号的电话号码
                                case "validate[areaTelPhone]":

									var telReg=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/,
										phoneReg=/^1[3|5|6|7|8|9]\d{9}/;

									if(telReg.test(value) || phoneReg.test(value)){
										var spaceReg=/\s/g;
										if(spaceReg.test(check[i].value)){
											var append=_options.isBox("append",check[i]);
											firstCheck.push(check[i]);

										}else{
											_options.isBox("remove",check[i]);
											checkIndex++;
										}
									}else{
										_options.isBox("append",check[i],"电话或手机号码格式不正确");
										firstCheck.push(check[i]);
									}
								break;
                                //校验必填真实姓名    
                                case "validate[requiredName]":

									if(check[i].value === ""){
										var append=_options.isBox("append",check[i],"此处不能为空");
										firstCheck.push(check[i]);
									}else{
										//var ChinaNameReg = /^[\u4e00-\u9fa5]*$/;
                                        //var EnglishNameReg = /^[a-zA-Z][\sa-zA-Z]+$/;
										var ChinaEnglishNameReg = /^[\u4E00-\u9FA5A-Za-z_]+$/;
										if(ChinaEnglishNameReg.test(value)){
											_options.isBox("remove",check[i]);
											checkIndex++;

										}else{
								            _options.isBox("append",check[i],"只能输入中文和英文");
								            firstCheck.push(check[i]);
								        }
                                    }
								break;

							}
						}
						function MaxMin(Mtype,i,tip,check){
							var ymd=value.split("-"),
								index=0,
								ff=null,		//如果小于当前日期，变成true
								nowDate=new Date(),
								_date={
									year:nowDate.getFullYear(),
									month:nowDate.getMonth() + 1,
									day:nowDate.getDate()
								};
							for(var attr in _date){


								//min
								if(Mtype === "<"){

									if(_date[attr] > Number(ymd[index])){
										ff=false;
										break;
									}else if(_date[attr] === Number(ymd[index])){
										
									}else if(_date[attr] < Number(ymd[index])){
										ff=true;
										break;
									}
								}
								//max
								else if(Mtype === ">"){
									if(_date[attr] < Number(ymd[index])){
										ff=false;
										break;
									}else if(_date[attr] === Number(ymd[index])){

									}else if(_date[attr] > Number(ymd[index])){
										ff=true;
										break;
									}
								}
								index++;
							};
							if(ff === false){
								_options.isBox("remove",check);
								checkIndex++;
							}else{
								var isFF=isNull(check,value,tip);
								if(!isFF){
									if($(check).nextAll(".parentFormformID")[0]){
										$(check).nextAll(".parentFormformID").find(".formErrorContent").html(tip);
									}else{
										_options.isBox("append",check,tip);
										firstCheck.push(check);
									}
								}else{
									_options.isBox("remove",check);
									//checkIndex++;

								}
							};

						}

					}();
					return {
						index:{
							firstCheck:firstCheck,
							checkIndex:checkIndex
						},
						check:check
					};

				},
				isBox:function(type,check,tip){
					var that=this;
					switch(type){
						case "append":
							if($(check).nextAll(".parentFormformID")[0]){
								$(check).nextAll(".parentFormformID").find(".formErrorContent").html(tip);
							}else{
								that_All.appendBox(check,tip);
							}
							return "append";
						break;
						case "remove":
							var tipBox=$(check).nextAll(".parentFormformID")[0];
							_method.opacityBox(tipBox,0);
							return "remove";
						break;
					}
				}
			};



			var target=input,that_All=this,
				opt=new options();
			for(var o in extend){
				var opt_ext=extend[o]();
				extBlur(opt_ext.target, opt_ext.fun, opt_ext.type);
			};

			//自动校验
			if(isAuto){
				for(var o in extend){
					var opt_new=extend[o]();
					var extType=extendCent(opt_new.target,opt_new.fun);
				};
				opt.events(isAuto);
			}

			for (var i = 0; i < target.length; i++) {
				//离开焦点触发事件
				$(target[i]).unbind();
				var dataEvent = target[i].getAttribute("data-event");
				switch(dataEvent){
					case "change":
						$(target[i]).on("change",function(){
							opt.events(this);
						})
					break;
					default:
						$(target[i]).on("blur",function(){
							opt.events(this);
						})
				}
			}

			saveBtn=saveBtn.push?saveBtn:$(saveBtn);
			//点击保存事件
			saveBtn.unbind();
			saveBtn.on("click",function(){

				var index=opt.events(target),
					extLength=0;			//扩展的文本框数量

				//扩展
				var extIndex=0,
					extCheckArr=[];
				for(var o in extend){
					var opt_new=extend[o]();
					var extType=extendCent(opt_new.target,opt_new.fun);
					extLength++;

					//如果返回的是正确的就++
					if(extType === "jia"){
						index.index.checkIndex++;
					}

				};
				//

				if((index.check.length+extLength) === index.index.checkIndex){
					succes();
				}else{
					var firstTip=$(".parentFormformID")[0];
					if(firstTip){
						var firstInput=$(firstTip).prev();
						var zHei=firstInput.offset().top;
						NoCheckScroll(zHei,firstInput);
					}

				}
			});


		}
	}

	var validation=function(input,save,extend,succes,isAuto){
		var that=this;

		that.blurBox(input,save,extend,succes,isAuto);

	}
	validation.prototype=new method();

	window['validation']=validation;




})(window, jQuery);