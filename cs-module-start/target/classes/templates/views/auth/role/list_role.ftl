<#assign ctx = request.contextPath />
<#include "/auth/role/edit_role.ftl">
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
                            <a href="javascript:void(0)" class="dropdown-item" data-toggle="modal" data-target="#role-add">新增角色</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="updateRole()">修改角色</a>
                        </div>
                    </div>
                </div>
                <div id="data-table_filter" class="dataTables_filter">
                    <form class="role-table-form">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon">
					               	角色：
					            </span>
                                    <div class="form-group">
                                        <input type="text" name="roleName" class="form-control">
                                        <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <table id="roleTable" data-mobile-responsive="true" class="mb-bootstrap-table text-nowrap"></table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/assets/js/auth/role/list_role.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/auth/role/edit_role.js"></script>