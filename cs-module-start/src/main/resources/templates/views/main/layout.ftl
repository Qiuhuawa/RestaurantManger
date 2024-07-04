<#assign ctx = request.contextPath />
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <meta http-equiv="pragma" content="no-cache"> 
	<meta http-equiv="cache-control" content="no-cache"> 
	<meta http-equiv="expires" content="0">   
    <title>${sysSystemSetting.systemName!'餐厅管理系统'}</title>
    <link rel="shortcut icon" type="images/x-icon" href="${ctx}/assets/images/favicon.ico" />
    <!-- Vendor styles -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/material-design-iconic-font/css/material-design-iconic-font.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/jquery.scrollbar/jquery.scrollbar.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/css/common.css">
    <!-- Bootstrap Table -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/bootstrap-table/bootstrap-table.css">
    <!-- animate -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/animate/animate.min.css">
    <!-- multiple-select -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/multiple-select/multiple-select.css">
    <!-- dropzone -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/dropzone/dropzone.min.css">
    <!-- jsTree -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/jsTree/style.min.css">
    <!-- treeTable -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/jqTreeGrid/jquery.treegrid.css">
    <!-- sweetalert2 -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/sweetalert2/sweetalert2.css">
    <!-- shuttle-multi-select -->
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/shuttle-multi-select/css/multi-select.css">
    <#macro css>
        <#nested>
    </#macro>
    <script>
       var ctx = '${ctx}';
	   var theme = "blue";
	   var avatar = ctx + "/assets/images/avatar/1.jpg";
    </script>
</head>
<body data-ma-theme="blue">
<div id="wrapper">
    <main class="main">
	    <#include "/main/header.ftl">
	    <#include "/main/sidebar.ftl">
    	<#include "/main/main.ftl">
    	<#macro layout>
	        <#nested>
	    </#macro>
    </main>
</div>
</body>
    <script type="text/javascript" src="${ctx}/assets/lib/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/tether/tether.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/waves/waves.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery.scrollbar/jquery.scrollbar.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery-scrollLock/jquery-scrollLock.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/bootstrap-notify/bootstrap-notify.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/multiple-select/multiple-select.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/dropzone/dropzone.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/sweetalert2/sweetalert2.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery-validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery-validate/additional-methods.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery-validate/localization/messages_zh.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jsTree/jstree.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/bootstrap-table/bootstrap-table.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jqTreeGrid/jquery.treegrid.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jqTreeGrid/jquery.treegrid.extension.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/datepicker/moment.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/laydate/laydate.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/sortable/sortable.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/shuttle-multi-select/js/jquery.multi-select.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/shuttle-multi-select/js/jquery.quicksearch.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery-migrate/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="${ctx}/assets/lib/jquery.jqprint/jquery.jqprint-0.3.js"></script>
	<script type="text/javascript" src="${ctx}/assets/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/assets/js/index.js"></script>
    <#macro js>
        <#nested>
    </#macro>
</html>

