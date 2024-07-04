<div class="modal fade" id="org-add" data-keyboard="false" data-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title pull-left" id="org-add-modal-title">新增组织</h3>
            </div>
            <div class="modal-body">
                <form id="org-add-form">
                	<div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	组织编码：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="orgCode" class="form-control"><i class="form-group__bar"></i>
                                    <input type="text" name="soId" hidden class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	组织名称：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="orgName" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	组织描述：
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
					               	组织权重：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="priority" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top:20px;margin-bottom:20px;">
                        <div class="col-sm-11">
                            <div class="input-group" style="margin-top:10px;">
                                <span class="input-group-addon">
					               	是否可用：
					            </span>
                                <div class="form-group">
                                    <label class="custom-control custom-radio">
                                        <input name="isAvailable" type="radio" value="1" checked class="custom-control-input">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">可用</span>
                                    </label>
                                    <label class="custom-control custom-radio">
                                        <input name="isAvailable" type="radio" value="0" class="custom-control-input">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">不可用</span>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon" style="justify-content: flex-start;margin-top: .5rem;">
					               	上级组织：
					             </span>
                                <div class="form-group">
                                    <div id="orgTree"></div>
                                </div>
                                <input type="hidden" name="parentSoId">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-save" id="org-add-button" name="save">保存</button>
                <button type="button" class="btn btn-secondary btn-close">关闭</button>
                <button class="btn-hide"></button>
            </div>
        </div>
    </div>
</div>
