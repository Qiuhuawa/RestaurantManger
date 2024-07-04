<div class="modal fade" id="menu-add" data-keyboard="false" data-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title pull-left" id="menu-add-modal-title">新增菜单/按钮</h3>
            </div>
            <div class="modal-body">
                <form id="menu-add-form">
                   <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	菜单编码：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="resourceCode" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	菜单名称：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="resourceName" class="form-control"><i class="form-group__bar"></i>
                                    <input type="text" name="oldresourceName" hidden class="form-control">
                                    <input type="text" name="srId" hidden class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="margin-top:20px;margin-bottom:20px;">
                        <div class="col-sm-11">
                            <div class="input-group" style="margin-top:10px;">
                                <span class="input-group-addon">
					               	类型选择：
					             </span>
                                <div class="form-group">
                                    <#list resourceTypeSelectMap?keys as key>
										<label class="custom-control custom-radio">
	                                        <input name="resourceType" type="radio" value="${key}" <#if key_index==0>checked</#if> class="custom-control-input">
	                                        <span class="custom-control-indicator"></span>
	                                        <span class="custom-control-description">${resourceTypeSelectMap['${key}']}</span>
	                                    </label>
									</#list>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row menu-icon-list-row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	图标：
					            </span>
                                <div class="form-group">
                                    <input type="text" name="icon" class="form-control">
                                    <div class="ms-parent" style="width: 100%;">
                                        <div class="ms-drop bottom icon-drop animated flipInX" style="display:block;max-height:200px;overflow-y:auto">
                                            <#include "/auth/res/resource_icon.ftl"> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row menu-url-list-row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	URL：
					            </span>
                                <div class="form-group">
                                    <div id="menu-url-list" class="form-control">
                                        <input id="menu-url" hidden type="text" name="url" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row menu-perms-list-row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	权限描述：
					            </span>
                                <div class="form-group">
                                    <div id="menu-perms-list" class="form-control">
                                        <input hidden id="menu-perms" type="text" name="component" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	描述：
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
					               	权重：
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
					               	是否管理：
					             </span>
                                <div class="form-group">
                                    <label class="custom-control custom-radio">
                                        <input name="isAdmin" type="radio" value="1" class="custom-control-input">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">是</span>
                                    </label>
                                    <label class="custom-control custom-radio">
                                        <input name="isAdmin" type="radio" value="0" checked class="custom-control-input">
                                        <span class="custom-control-indicator"></span>
                                        <span class="custom-control-description">不是</span>
                                    </label>
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
					               	上级菜单：
					             </span>
                                <div class="form-group">
                                    <div id="menuTree"></div>
                                </div>
                                <input type="hidden" name="parentSrId">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-save" id="menu-add-button" name="save">保存</button>
                <button type="button" class="btn btn-secondary btn-close">关闭</button>
                <button class="btn-hide"></button>
            </div>
        </div>
    </div>
</div>
