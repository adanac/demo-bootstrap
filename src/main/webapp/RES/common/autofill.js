var basePath = '${base}';
var autofill = {

	// 截取逗号
	split : function(val) {
		return val.split(/,\s*/);
	},

	// 返回截取类容
	extractLast : function(term) {
		return autofill.split(term).pop();
	},

	// 单个部门选择
	singleDept : function(id) {
		$(id).typeahead({
			source : function(query, process) {
				$(this).val(query.trim());
				$.get(basePath + "/common/queryDept.do", {
					"deptCode" : query.trim()
				}, function(data) {
					if (data.status == "1") {
						var content = data.content;
						process(content);
					} else {
						alert("获取部门信息失败!");
					}
				}, "json");
			},
			updater : function(item) {
				// 进一步处理

				return item;
			}
		});
	},

	

	// 多个部门选择
	multipleDept : function(id) {
		// 当选择一个条目时不离开文本域
		$(id).bind(
				"keydown",
				function(event) {
					if (event.keyCode === $.ui.keyCode.TAB
							&& $(this).data("ui-autocomplete").menu.active) {
						event.preventDefault();
					}
				}).autocomplete(
				{
					minLength : 0,
					source : function(request, response) {
						// 回到 autocomplete，但是提取最后的条目
						$.get(basePath + "/common/queryDept.do", {
							"deptCode" : autofill.extractLast(request.term)
						}, function(data) {
							if (data.status == "1") {
								var content = data.content;
								response($.ui.autocomplete.filter(content,
										autofill.extractLast(request.term)));
							} else {
								alert("获取部门信息失败!");
							}
						}, "json");
					},
					focus : function() {
						// 防止在获得焦点时插入值
						return false;
					},
					select : function(event, ui) {
						var terms = autofill.split(this.value);
						// 移除当前输入
						terms.pop();
						// 添加被选项
						terms.push(ui.item.value);
						// 添加占位符，在结尾添加逗号+空格
						terms.push("");
						this.value = terms.join(",");
						return false;
					}
				});
	}
	
};
