<div class="modal fade" id="role-add" data-keyboard="false" data-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title pull-left" id="role-add-modal-title">新增角色</h3>
            </div>
            <div class="modal-body">
                <form id="role-add-form">
                	<div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	角色编码：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="roleCode" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	角色名称：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="roleName" class="form-control"><i class="form-group__bar"></i>
                                    <input type="text" name="srId" hidden class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 角色描述：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="description" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 角色权重：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="priority" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon" style="justify-content: flex-start;margin-top: .5rem;">
					               	菜单权限：
					             </span>
                                <div class="form-group">
                                    <div id="menuTree"></div>
                                </div>
                                <input type="hidden" name="resourceIdStr">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-save" id="role-add-button" name="save">保存</button>
                <button type="button" class="btn btn-secondary btn-close">关闭</button>
                <button class="btn-hide"></button>
            </div>
        </div>
    </div>
</div>
