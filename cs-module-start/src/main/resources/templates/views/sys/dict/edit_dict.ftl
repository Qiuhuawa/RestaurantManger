<div class="modal fade" id="dict-add" data-keyboard="false" data-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title pull-left" id="dict-add-modal-title">新增字典</h3>
            </div>
            <div class="modal-body">
                <form id="dict-add-form">
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	字典编码：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="dictCode" class="form-control"><i class="form-group__bar"></i>
                                    <input type="text" name="sdId" hidden class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	字典名称：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="dictName" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	字典备注：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="dictRemark" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	字典权重：
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
					               	上级字典：
					             </span>
                                <div class="form-group">
                                    <div id="dictTree"></div>
                                </div>
                                <input type="hidden" name="parentSdId">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-save" id="dict-add-button" name="save">保存</button>
                <button type="button" class="btn btn-secondary btn-close">关闭</button>
                <button class="btn-hide"></button>
            </div>
        </div>
    </div>
</div>
