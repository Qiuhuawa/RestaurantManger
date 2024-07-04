<#assign ctx = request.contextPath />
<#include "/auth/user/edit_user.ftl">
<#include "/auth/user/upload_user.ftl">
<div class="card">
    <div class="card-block">
        <div class="table-responsive">
            <div id="data-table_wrapper" class="dataTables_wrapper">
                <div class="dataTables_buttons hidden-sm-down actions">
                    <span class="actions__item zmdi zmdi-search" onclick="search()" title="搜索" /> 
                    <span class="actions__item zmdi zmdi-refresh-alt" onclick="refresh()" title="刷新" />
                    <span class="actions__item zmdi zmdi-print" onclick="exportUserAccountExcel()" title="打印账号信息" />
                    <span class="actions__item zmdi zmdi-upload" data-toggle="modal" data-target="#user-upload" title="上传" />
                    <span class="actions__item zmdi zmdi-download" onclick="exportUserExcel()" title="导出Excel (.xlsx)" />
                    <div class="dropdown actions__item">
                        <i data-toggle="dropdown" class="zmdi zmdi-more-vert"></i>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0)" class="dropdown-item" data-toggle="modal" data-target="#user-add">新增用户</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="updateUser()">修改用户</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="deleteUsers()">删除用户</a>
                        </div>
                    </div>
                </div>
                <div id="data-table_filter" class="dataTables_filter">
                    <form class="user-table-form">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon"> 用户名： </span>
                                    <div class="form-group">
                                        <input type="text" name="loginName" class="form-control"> <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon"> 姓名： </span>
                                    <div class="form-group">
                                        <input type="text" name="realName" class="form-control"> <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon"> 状态： </span>
                                    <div class="form-group">
                                        <select class="form-control" name="userStatus">
                                            <option value="" selected>全部</option>
										  	<#list userStatusSelectMap?keys as key>
											 	<option value="${key}">${userStatusSelectMap['${key}']}</option>
										  	</#list>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <table id="userTable" data-mobile-responsive="true" class="mb-bootstrap-table text-nowrap"></table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
  var currentUserId = '${currentUserId}';
</script>
<script type="text/javascript" src="${ctx}/assets/js/auth/user/list_user.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/auth/user/edit_user.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/auth/user/upload_user.js"></script>