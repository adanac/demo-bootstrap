<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>zTree</title>
	<#include "pages/common/base.ftl">
</head>
<body style="padding:0">  
<div style="padding:10px;clear: both;">  
    <div id="psLine" style="height:600px;"></div>  
</div>  
</body>  
<script src="${resRoot}/plugins/echarts/echarts-all.js"></script>  
<script type="text/javascript">  
    //图表  
    var psLineChar = echarts.init(document.getElementById('psLine'));  
  
    //查询  
    function loadDrugs() {  
        psLineChar.clear();  
        psLineChar.showLoading({text: '正在努力的读取数据中...'});  
        $.getJSON('${base}/common/list.do', function (data) {  
            if (data.success) {  
                psLineChar.setOption(data.data, true);  
                psLineChar.hideLoading();  
            } else {  
                alert('提示', data.msg);  
            }  
        });  
    }  
    //载入图表  
    loadDrugs();  
</script>
</html>