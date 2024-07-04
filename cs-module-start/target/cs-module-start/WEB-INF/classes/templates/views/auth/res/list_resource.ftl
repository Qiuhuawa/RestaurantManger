<#assign ctx = request.contextPath />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/autocomplete/autocomplete.css">
<style>
    .autocomplete-input {
        padding: 0;
        font-family: inherit;
        float: initial;
        font-size: 1em;
        border: 0 solid rgba(0, 0, 0, 0.19);
        margin: 0;
    }
    .autocomplete-container {
        position: relative;
        width: 1rem;
        height: 1rem !important;
        margin: 0  ;
    }
    .proposal-box {
        z-index: 1000 !important;
    }
</style>
<#include "/auth/res/edit_resource.ftl">
<div class="card">
    <div class="card-block">
        <div class="table-responsive">
            <div id="data-table_wrapper" class="dataTables_wrapper">
                <div class="dataTables_buttons hidden-sm-down actions">
                    <span class="actions__item zmdi zmdi-search" onclick="search()" title="搜索" />
                    <span class="actions__item zmdi zmdi-refresh-alt" onclick="refresh()" title="刷新" />
                    <div class="dropdown actions__item">
                        <i data-toggle="dropdown" class="zmdi zmdi-more-vert"></i>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0)" class="dropdown-item" data-toggle="modal" data-target="#menu-add">新增资源</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="updateMenu()">修改资源</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="deleteMenu()">删除资源</a>
                        </div>
                    </div>
                </div>
                <div id="data-table_filter" class="dataTables_filter">
                    <form class="menu-table-form">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon">
					               	名称：
					            </span>
                                    <div class="form-group">
                                        <input type="text" name="resourceName" class="form-control">
                                        <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon">
					               	类型：
					            </span>
                                    <select class="form-control" name="resourceType">
                                        <option value="" selected>全部</option>
									  	<#list resourceTypeSelectMap?keys as key>
										 	<option value="${key}">${resourceTypeSelectMap['${key}']}</option>
									  	</#list>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <table id="menuTable" data-mobile-responsive="true" class="mb-bootstrap-table text-nowrap"></table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/assets/js/auth/res/list_resource.js"></script>
<script type="text/javascript" src="${ctx}/assets/lib/autocomplete/autocomplete.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/auth/res/edit_resource.js"></script>
