//选择行显示
var rowSelect={};
/**
 * 初始化上下文
 */
rowSelect.paramTbName='';
rowSelect.tableId='';
rowSelect.pageType='';//1 是查询页  2是新增页
rowSelect.columnIndex = 0;//页面中第一个可选列的columnIndex
rowSelect.init=function(paramTbName,tableId){
	rowSelect.paramTbName=paramTbName;
	rowSelect.tableId=tableId;
	rowSelect.pageType=1;
	initColumn();
};

function getSelRow(){
	 if(rowSelect.pageType==1){
		 setRowset("rowTree",rowSelect.tableId);
	 }else{
		 setRowsetZDY("rowTree",rowSelect.tableId);
	 }
	 
}
function initColumn(){
	var zNodes;
    $.ajax({
			   type : 'POST',
			   url : basePath + "/queryDb/getColumnNames.do?tableName="+rowSelect.paramTbName,
			   data : null,
			   async : false,
			   dataType : "json",
			   success : function(data) {
			     if(data.status == "1"){
					zNodes = data.content;
				  }else{
					layer.msg("基础节点获取失败！");
				  }
			   },
			   error : function(data) {
				   layer.msg("基础节点获取失败！");
				}
			   });
    getRowset("rowTree",zNodes,rowSelect.tableId);
    // 显示列事件
     $("#showCol").click(function(){
        $('#showColumn').modal('show');
     });
}

//树设置
var setting = {
				check: {
					enable: true,
					chkDisabledInherit: true
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
//初始化zTree节点
function getRowset(zTreeName,zNodes,tableId){
	$.fn.zTree.init($("#"+zTreeName), setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj(zTreeName);
	zTree.expandAll(true);
}
//适用于bootstrap-table
function setRowset(zTreeName,tableId){
	var zTree = $.fn.zTree.getZTreeObj(zTreeName);
	var nodes = zTree.getCheckedNodes(false);
	var showNodes = zTree.getCheckedNodes(true);
	for(i=0;i<showNodes.length;i++)
	{
		var node = showNodes[i];
		$('#'+tableId).bootstrapTable('showColumn', node.cid);
	}
	//$('#'+tableId).bootstrapTable('showAllColumns');
	for(i=0;i<nodes.length;i++)
	{
		var node = nodes[i];
		$('#'+tableId).bootstrapTable('hideColumn', node.cid);
	}
}
//适用于自定义table
function setRowsetZDY(zTreeName,tableId){
	var zTree = $.fn.zTree.getZTreeObj(zTreeName);
	var nodes = zTree.getCheckedNodes(false);
	$('#'+tableId+' tr').find('td').show();
	$('#'+tableId+' tr').find('th').show();
	for(i=0;i<nodes.length;i++)
	{
		var node = nodes[i];
		$('#'+tableId+' tr').find('th:eq('+(node.id+rowSelect.columnIndex)+')').hide();
		$('#'+tableId+' tr').find('td:eq('+(node.id+rowSelect.columnIndex)+')').hide();
	}	
}

/*START cookie操作*/
//=======================================================================================================

function setCookie(name,value) 
{ 
  var Days = 30; 
  var exp = new Date(); 
  exp.setTime(exp.getTime() + Days*365*24*60*60*1000); 
  document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

//读取cookies 
function getCookie(name)
{
  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
  
  if(arr=document.cookie.match(reg))
      return unescape(arr[2]); 
  else 
      return null; 
} 

//删除cookies 
function delCookie(name)
{
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval=getCookie(name);
  if(cval!=null)
     document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
}
/*END cookie操作*/
//=======================================================================================================
