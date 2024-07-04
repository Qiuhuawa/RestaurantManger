<#assign ctx = request.contextPath />
<#include "/auth/org/edit_organization.ftl">
<#include "/auth/org/upload_organization.ftl">
<div class="card">
    <div class="card-block">
        <div class="table-responsive">
            <div id="data-table_wrapper" class="dataTables_wrapper">
                <div class="dataTables_buttons hidden-sm-down actions">
                    <span class="actions__item zmdi zmdi-search" onclick="search()" title="搜索" />
                    <span class="actions__item zmdi zmdi-refresh-alt" onclick="refresh()" title="刷新" />
                    <span class="actions__item zmdi zmdi-upload" data-toggle="modal" data-target="#org-upload" title="上传" />
                    <span class="actions__item zmdi zmdi-download" onclick="exportOrgExcel()" title="导出Excel (.xlsx)" />
                    <div class="dropdown actions__item">
                        <i data-toggle="dropdown" class="zmdi zmdi-more-vert"></i>
                        <div class="dropdown-menu dropdown-menu-right">
                            <a href="javascript:void(0)" class="dropdown-item" data-toggle="modal" data-target="#org-add">新增组织</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="updateOrg()">修改组织</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="deleteOrg();">删除组织</a>
                        </div>
                    </div>
                </div>
                <div id="data-table_filter" class="dataTables_filter">
                    <form class="org-table-form">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon">
					               	名称：
					            </span>
                                    <div class="form-group">
                                        <input type="text" name="orgName" class="form-control">
                                        <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <table id="orgTable" data-mobile-responsive="true" class="mb-bootstrap-table text-nowrap"></table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/assets/js/auth/org/list_organization.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/auth/org/edit_organization.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/auth/org/upload_organization.js"></script>