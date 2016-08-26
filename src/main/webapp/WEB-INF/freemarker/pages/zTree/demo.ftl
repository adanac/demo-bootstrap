<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>zTree</title>
	<#include "pages/common/base.ftl">
	<script type="text/javascript">
		var basePath = '${base}';
	</script>
</head>
<body>
	 <input id="deptCode" class="text-input validate[null]" type="text" />
	 <button id="query" onclick="exeQuery();" class="btn-save" type="button"> <i></i> 查询</button>
	 <br/><br/><br/><br/><br/>
	 <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-comments"></i>列表
        </div>
        <div class="actions">
            <a href="javascript:;" class="btn btn-default btn-sm" data-toggle="modal"  id="showCol">
                <i class="fa fa-cog"></i> 显示列
            </a>
            <a href="javascript:;" onclick="export2Excel();" class="btn btn-default btn-sm">
                <i class="fa fa-upload"></i> 导出
            </a>
        </div>
    </div>
    <div class="portlet-body">
        <!-- 表格开始 -->
        <div id="sample_2_wrapper" class="dataTables_wrapper no-footer">
            <div class="table-scrollable">
                <table id="table" class="table table-striped table-bordered table-hover dataTable no-footer ta_ctable table-striped table-bordered table-hover dataTable no-footer" style="width:auto;max-width:none;border:1px solid #ddd;margin-bottom:0;">
                </table>
            </div>
        </div>
        <!-- 表格结束 -->
    </div>                               
	<!-- 初始化js -->
    <script src="${resRoot}/pages/commData.js"></script>
    <script>
       $(function(){
    	 autofill.multipleDept("#deptCode");
    	 rowSelect.init('tab_common','table');
       });
       
       function exeQuery(){
    	    var url = basePath + "/common/list2.do?t="+Date.parse(new Date());  
		    var deptCode = $("#deptCode").val();
		    if(deptCode!="")
		    {
			   url = url +'&deptCode='+encodeURI(encodeURI(deptCode));
		    }
		    $("#table").bootstrapTable('refresh', {
		        url: url
		    });
       }
       
       //导出查询后的数据
  	 	function export2Excel(){
	  	 	var url='/export/exportInfo.do?tableName=tab_common&t='+Date.parse(new Date());
	  	 	var deptCode = $("#deptCode").val();
	  	 	if(deptCode!="")
	  	 	{
	  	 		url = url +'&deptCode='+encodeURI(encodeURI(deptCode));
	  	 	}
	  	 	window.location = basePath + url;
	  	 }
    </script>
    <#include "/WEB-INF/freemarker/pages/zTree/showColumn.ftl">
    
</body>
</html>