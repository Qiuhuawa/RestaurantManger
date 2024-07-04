<div class="modal fade" id="user-add" data-keyboard="false" data-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title pull-left" id="user-add-modal-title">新增用户</h3>
            </div>
            <div class="modal-body">
                <form id="user-add-form">
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 用户名：
					            </span>
                                <div class="form-group">
                                    <input type="text" id="account" name="loginName" class="form-control"><i class="form-group__bar"></i>
                                    <input type="text" name="suId" hidden class="form-control">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 姓名：
					            </span>
                                <div class="form-group">
                                    <input type="text" id="name" name="realName" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 编号：
					            </span>
                                <div class="form-group">
                                    <input type="text" id="userid" name="userNumber" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 邮箱：
					            </span>
                                <div class="form-group">
                                    <input type="text" id="email" name="email" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 手机：
					            </span>
                                <div class="form-group">
                                    <input type="text" id="telephone" name="mobile" class="form-control"><i class="form-group__bar"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon">
					               	 角色：
					             </span>
                                <div class="form-group">
                                    <select multiple="multiple" name="rolesSelect"></select>
                                    <input name="roles" hidden/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group" style="margin-top:10px;">
                                <span class="input-group-addon">
					               	性别：
					             </span>
                                <div class="form-group" id="gender">
                                    <#list sexTypeMap?keys as key>
	                                    <label class="custom-control custom-radio">
	                                        <input name="sex" type="radio" id="${key}" value="${key}" class="custom-control-input">
	                                        <span class="custom-control-indicator"></span>
	                                        <span class="custom-control-description">${sexTypeMap['${key}']}</span>
	                                    </label>
								    </#list>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-11">
                            <div class="input-group">
                                <span class="input-group-addon" style="justify-content: flex-start;margin-top: .5rem;">
					               	组织：
					             </span>
                                <div class="form-group">
                                    <div id="orgTree"></div>
                                </div>
                                <input type="hidden" name="schoolId">
                                <input type="hidden" name="teamId">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" id="usersave" class="btn btn-save" id="user-add-button" name="save">保存</button>
                <button type="button" id="userclose" class="btn btn-secondary btn-close">关闭</button>
                <button class="btn-hide"></button>
            </div>
        </div>
    </div>
</div>