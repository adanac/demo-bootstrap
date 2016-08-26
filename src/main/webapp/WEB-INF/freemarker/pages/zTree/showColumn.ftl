<!-- 弹出层 -->
<script type="text/javascript" src="${base}/RES/plugins/layer/layer.js"></script>
<script type="text/javascript">
	
	  $(function(){
    	// 全选按钮
    	$("#selectAll").on("click", function() {
    		var zTree = $.fn.zTree.getZTreeObj("rowTree");
    		zTree.checkAllNodes(true)
    	});
    	
    	// 反选按钮
    	$("#unselectAll").on("click", function() {
    			var zTree = $.fn.zTree.getZTreeObj("rowTree");
    			var nodes = zTree.getNodes()[1].children;
    			//alert("nodes.length="+nodes.length+",cnodes.length="+cnodes.length);
    			for(var i = 0 ; i< nodes.length; i++)
    			{
    				var node = nodes[i];
   					node.checked=!node.checked;
   					zTree.updateNode(node);  
    			}
    		
    		});
    	
    	})
	
</script>
<!-- 选择咧Modal -->
<div  id="showColumn" class="modal fade" keyboard="false" data-keyboard="false" data-backdrop="static" tabindex="-1" data-width="400" style="display:none">
  <div class="modal-dialog" role="document" style="width:400px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">请选择要显示的列</h4>
      </div>
      <div class="modal-body">
        <ul id="rowTree" class="ztree myZtree"></ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="selectAll">全选</button>
        <button type="button" class="btn btn-primary"  id="unselectAll">反选</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" onclick="getSelRow();" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div>
  </div>
</div>
	
