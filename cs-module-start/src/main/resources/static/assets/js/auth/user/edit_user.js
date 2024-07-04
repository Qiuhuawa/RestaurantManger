var validator;
var $userAddForm = $("#user-add-form");
var $rolesSelect = $userAddForm.find("select[name='rolesSelect']");
var $roles = $userAddForm.find("input[name='roles']");

$(function () {
    validateRule();
    initRole();
    createOrgTree();

    $("#user-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getOrg();
        var validator = $userAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "/user/saveorupdateuser", $userAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        $MB.n_success(result.meta.msg);
                        $MB.refreshTable("userTable");
                    } else $MB.n_danger(result.meta.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "/user/saveorupdateuser", $userAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        $MB.n_success(result.meta.msg);
                        $MB.refreshTable("userTable");
                    } else $MB.n_danger(result.meta.msg);
                });
            }
        }
    });

    $("#user-add .btn-close").click(function () {
        closeModal();
    });
});

function closeModal() {
    $("#user-add-button").attr("name", "save");
    validator.resetForm();
    $rolesSelect.multipleSelect('setSelects', []);
    $rolesSelect.multipleSelect("refresh");
    $userAddForm.find("input[name='loginName']").removeAttr("readonly");
    $("#user-add-modal-title").html('新增用户');
    $MB.resetJsTree("orgTree");
    $MB.closeAndRestModal("user-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $userAddForm.validate({
        rules: {
            loginName: {
                required: true,
                minlength: 3,
                maxlength: 32
            },
            email: {
                email: true
            },
            mobile: {
                checkMobile: true
            },
            roles: {
            	required: true
            },
            sex: {
                required: true
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":checkbox") || element.is(":radio")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        messages: {
        	loginName: {
                required: icon + "请输入用户名",
                minlength: icon + "用户名长度3到32个字符",
                maxlength: icon + "用户名长度3到32个字符"
            },
            email: icon + "邮箱格式不正确",
            mobile: icon + "手机号格式不正确",
            roles: icon + "请选择用户角色",
            sex: icon + "请选择性别"
        }
    });
    
    //自定义正则表达示验证方法
	$.validator.addMethod("checkMobile", function(value, element, params){
			var ret = /^1\d{10}$/;
			return this.optional(element)||(ret.test(value));
		},"手机格式不正确！");
}

function initRole() {
    $.post(ctx + "/role/getrolelist", {}, function (result) {
    	if (result.meta.code === 1000) {
            var data = result.data.rolelist;
            var option = "";
            for (var i = 0; i < data.length; i++) {
                option += "<option value='" + data[i].srId + "'>" + data[i].roleName + "</option>"
            }
            $rolesSelect.html("").append(option);
            $rolesSelect.attr('style','display:block')
            var options = {
                formatSelectAll: function () {
                    return '所有角色';
                },
                formatAllSelected: function () {
                    return '所有角色';
                },
                width: '100%',
                onClose: function () {
                    $roles.val($rolesSelect.val());
                    validator.element("input[name='roles']");
                }
            };
            $rolesSelect.multipleSelect(options);
        } else {
            $MB.n_danger(result.meta.msg);
        }
    });
}

function createOrgTree() {
    $.post(ctx + "/org/treeorganization", {}, function (result) {
        if (result.meta.code === 1000) {
            var data = result.data.org;
            $('#orgTree').jstree({
                "core": {
                    'data': data,
                    'multiple': false // 取消多选
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": false // 取消选择父节点后选中所有子节点
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(result.meta.msg);
        }
    })
}

function getOrg() {
	//更新获取全部checked节点
//	$.jstree.core.prototype.get_all_checked = function(full) {
//	    var tmp=new Array;
//	    for(var i in this._model.data){
//	        if(this.is_undetermined(i)||this.is_checked(i)){tmp.push(full?this._model.data[i]:i);}
//	    }
//	    return tmp;
//	};
    var ref = $('#orgTree').jstree(true);
    var currentObj = ref.get_selected(true)[0];
    if (currentObj != null && currentObj != '' && currentObj != 'undefined') {
    	var parent = currentObj.parent;
    	if (parent == '#') {
    		$("[name='schoolId']").val(ref.get_selected()[0]);
    	} else {
    		$("[name='schoolId']").val(parent);
    		$("[name='teamId']").val(ref.get_selected()[0]);
    	}
    }
}

function updateUser() {
    var selected = $("#userTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的用户！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个用户！');
        return;
    }
    var suId = selected[0].suId;
    $.post(ctx + "/user/getuser", {suId: suId}, function (result) {
	    if (result.meta.code === 1000) {
	        var $form = $('#user-add');
	        var $orgTree = $('#orgTree');
	        $form.modal();
	        var user = result.data.user;
	        $("#user-add-modal-title").html('修改用户');
	        $form.find("input[name='loginName']").val(user.loginName).attr("readonly", true);
	        $form.find("input[name='userNumber']").val(user.userNumber);
	        $form.find("input[name='realName']").val(user.realName);
	        $form.find("input[name='suId']").val(user.suId);
	        $form.find("input[name='email']").val(user.email);
	        $form.find("input[name='mobile']").val(user.mobile);
	        var roleArr = [];
	        var userRoleMap = result.data.userRoleMap;
	        $.each(userRoleMap, function(key, values){   
	            roleArr.push(key);
	        }); 
	        $form.find("select[name='rolesSelect']").multipleSelect('setSelects', roleArr);
	        $form.find("input[name='roles']").val($form.find("select[name='rolesSelect']").val());
	        $("input:radio[value='" + user.sex + "']").prop("checked", true);
	        $orgTree.jstree().open_all();
	        $orgTree.jstree('select_node', user.orgId, true);
	        $("#user-add-button").attr("name", "update");
	    } else {
	        $MB.n_danger(result.meta.msg);
	    }
    });
}