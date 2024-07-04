var validator;
var $roleAddForm = $("#role-add-form");

$(function () {
    validateRule();
    createMenuTree();

    $("#role-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getMenu();
        var validator = $roleAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "/role/saveorupdaterole", $roleAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        $MB.n_success(result.meta.msg);
                        $MB.refreshTable("roleTable");
                    } else $MB.n_danger(result.meta.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "/role/saveorupdaterole", $roleAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        $MB.n_success(result.meta.msg);
                        $MB.refreshTable("roleTable");
                    } else $MB.n_danger(result.meta.msg);
                });
            }
        }
    });

    $("#role-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#role-add-button").attr("name", "save");
    $("#role-add-modal-title").html('新增角色');
    validator.resetForm();
    $MB.resetJsTree("menuTree");
    $MB.closeAndRestModal("role-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $roleAddForm.validate({
        rules: {
        	roleCode: {
        		required: true,
        		checkRoleCode: true,
                minlength: 4,
                maxlength: 64
        	},
            roleName: {
                required: true,
                minlength: 3,
                maxlength: 32
            },
            description: {
                maxlength: 100
            },
            resourceIdStr: {
                required: true
            }
        },
        messages: {
        	roleCode: {
        		required: icon + "请输入角色编码",
                minlength: icon + "角色编码长度4到64个字符",
                maxlength: icon + "角色编码长度4到64个字符"
        	},
            roleName: {
                required: icon + "请输入角色名称",
                minlength: icon + "角色名称长度3到32个字符",
                maxlength: icon + "角色名称长度3到32个字符"
            },
            description: icon + "角色描述不能超过100个字符",
            resourceIdStr: icon + "请选择相应菜单权限"
        }
    });
    
    //自定义正则表达示验证方法
	$.validator.addMethod("checkRoleCode", function(value, element, params){
			var ret = /^[a-zA-Z0-9_]{4,64}$/;
			return this.optional(element)||(ret.test(value));
		},"角色编码格式不正确！");

}

function createMenuTree() {
    $.post(ctx + "/role/treeresource", {}, function (result) {
        if (result.meta.code === 1000) {
            var data = result.data.resource;
            $('#menuTree').jstree({
                "core": {
                    'data': data
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": false
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(result.meta.msg);
        }
    });
}

function getMenu() {
    var $menuTree = $('#menuTree');
    var ref = $menuTree.jstree(true);
    var resourceIds = ref.get_checked();
    $menuTree.find(".jstree-undetermined").each(function (i, element) {
        resourceIds.push($(element).closest('.jstree-node').attr("id"));
    });
    $("[name='resourceIdStr']").val(resourceIds);
}

function updateRole() {
    var selected = $("#roleTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的角色！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个角色！');
        return;
    }
    var roleId = selected[0].srId;
    $.post(ctx + "/role/getrole", {srId: roleId}, function (result) {
        if (result.meta.code === 1000) {
            var $form = $('#role-add');
            var $menuTree = $('#menuTree');
            $form.modal();
            var role = result.data.role;
            $("#role-add-modal-title").html('修改角色');
            $form.find("input[name='roleCode']").val(role.roleCode);
            $form.find("input[name='roleName']").val(role.roleName);
            $form.find("input[name='srId']").val(role.srId);
            $form.find("input[name='description']").val(role.description);
            $form.find("input[name='priority']").val(role.priority);
            var resourceIds = role.resourceIds;
            if (resourceIds != null && resourceIds != "") {
            	var menuArr = [];
            	for (var i = 0; i < resourceIds.length; i++) {
            		menuArr.push(resourceIds[i]);
            	}
            }
            $menuTree.jstree('select_node', menuArr, true);
            $menuTree.jstree().close_all();
            $("#role-add-button").attr("name", "update");
        } else {
            $MB.n_danger(result.meta.msg);
        }
    });
}