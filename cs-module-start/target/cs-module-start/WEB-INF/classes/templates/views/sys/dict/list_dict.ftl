<#assign ctx = request.contextPath />
<#include "/sys/dict/edit_dict.ftl">
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
                            <a href="javascript:void(0)" class="dropdown-item" data-toggle="modal" data-target="#dict-add">新增字典</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="updateDict()">修改字典</a>
                            <a href="javascript:void(0)" class="dropdown-item" onclick="deleteDict()">删除字典</a>
                        </div>
                    </div>
                </div>
                <div id="data-table_filter" class="dataTables_filter">
                    <form class="dict-table-form">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon">
					               	字典编码：
					            </span>
                                    <div class="form-group">
                                        <input type="text" name="dictCode" class="form-control">
                                        <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="input-group">
                                    <span class="input-group-addon">
					               	字典名称：
					            </span>
                                    <div class="form-group">
                                        <input type="text" name="dictName" class="form-control">
                                        <i class="form-group__bar"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <table id="dictTable" data-mobile-responsive="true" class="mb-bootstrap-table text-nowrap"></table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/assets/js/sys/dict/list_dict.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/sys/dict/edit_dict.js"></script>