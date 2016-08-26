;(function(){

	var url = basePath + "/common/list2.do?t="+Date.parse(new Date());
    //页面加载完成执行
    $(function(){
        TablesPlugins(url);
        //初始化搜索栏定义方法
        var search = new Searchs();
        //初始化校验
        var validate = new validation(search.searchNode.find(".text-input"), search.searchNode.find(".btn-save"), search.checkAllCallback)
        //初始化清空方法
        search.valueNull(validate);
        //select插件
        $('.select2me').select2({
            placeholder: "Select",
            allowClear: true
        });
    })

    
    /*
     *
     *
     */

    //tables插件定义
    function TablesPlugins(url){
        $("#table").bootstrapTable({
            url: url,
            sidePagination:"server",
            pagination:true,
            pageNumber: 1,
            pageSize: 10,//单页记录数
            pageList: [10, 20, 50],//分页步进值
            columns: [
                {
                    field: 'aaaaa',
                    title: '序号',
                    width: 100,
                    formatter: function (value, row, index) {
                        var num = index+1;
                        if(num < 10){
                            return "0" + num;
                        }
                        return num;
                    }
                },{
                    field: 'id',
                    title: '编号',
                    width: 100
                },{
                    field: 'deptCode',
                    title: '部门',
                    width: 100
                },{
                    field: 'userName',
                    title: '姓名',
                    width: 100
                },{
                    field: 'sex',
                    title: '性别',
                    width: 100,
                    formatter:function(value,row,index){
                    	if(value == 0){
                    		return "女";
                    	}else{
                    		return "男";
                    	}
                    }
                }, {
                    field: 'age',
                    title: '年龄',
                    width: 100
                }, {
                    field: 'passwd',
                    title: '密码',
                    width: 100
                }
            ]

        })
    }
    //搜索栏函数定义
    function Searchs(){
        this.searchNode = $(".search");
    }
    Searchs.prototype = {
        valueNull: function(validate){
            var btn = this.searchNode.find(".btn-warning"),
                inputNode = this.searchNode.find("input[type='text']"),
                _searchs = this;
                fClick = function(){
                    var parentFormformID = _searchs.searchNode.find(".parentFormformID");
                    //清空
                    inputNode.val(null);
                    for (var i = 0; i < parentFormformID.length; i++) {
                        $(parentFormformID[i]).animate({
                            "opacity":0
                        },150,function(){
                                parentFormformID.remove();
                            });
                    }
                }
            btn.bind("click", fClick)
        },
        checkAllCallback: function(){
            //此处写校验成功回调
            url = basePath + "/common/list2.do?t="+Date.parse(new Date());  
		    var deptCode = $("#deptCode").val();
		    if(deptCode!="")
		    {
			   url = url +'&deptCode='+encodeURI(encodeURI(deptCode));
		    }
		    $("#table").bootstrapTable('refresh', {
		        url: url
		    });
        }
    }
    

})();