$(function () {
    var $userTableForm = $(".user-table-form");
    var settings = {
        url: ctx + "/user/listuserdata",
        contentType: "application/x-www-form-urlencoded",
        method: 'POST',
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                loginName: $userTableForm.find("input[name='loginName']").val().trim(),
                realName: $userTableForm.find("input[name='realName']").val().trim(),
                userStatus: $userTableForm.find("select[name='userStatus']").val()
            };
        },
        columns: [
	        {
	            checkbox: true
	        },
	        {
			    field: 'number',
			    title: '序号',
			    width: '5%',
			    align:'center',
			    switchable:false,
			    formatter: function (value, row, index) {
			    	return $MB.getTableIndex('userTable', index);
			    }
			},
	        {
	            field: 'suId',
	            visible: false
	        }, 
	        {
	            field: 'loginName',
	            title: '用户名'
	        }, 
	        {
	            field: 'initialPassword',
	            title: '初始密码'
	        }, 
	        {
	            field: 'realName',
	            title: '姓名'
	        }, 
	        {
	        	field: 'sex',
	        	title: '性别',
	        	formatter: function (value, row, index) {
	        		if (value === 'SEX_TYPE_MALE') return '男';
	        		else if (value === 'SEX_TYPE_FEMALE') return '女';
	        		else return '保密';
	        	}
	        }, 
	        {
	        	field: 'teamName',
	        	title: '组织',
	        	formatter: function (item, row, index) {
	            	if (item != null) {
	            		return item;
	            	} else {
	            		return row.schoolName;
	            	}
	            }
	        }, 
	        {
	            field: 'createTime',
	            title: '创建时间',
	            formatter: function (item, row, index) {
                	return moment(item).format("YYYY-MM-DD HH:mm:ss") === 'Invalid date' ? '-' : moment(item).format("YYYY-MM-DD HH:mm:ss");
                }
	        },
	        {
	            field: 'userStatus',
	            title: '状态',
	            formatter: function (value, row, index) {
	                if (value === 'USER_STATUS_NORMAL') return '<span class="badge badge-success">有效</span>';
	                if (value === 'USER_STATUS_LOCK') return '<span class="badge badge-danger">锁定</span>';
	                if (value === 'USER_STATUS_DISABLED') return '<span class="badge badge-warning">禁用</span>';
	            }
	        },
	        {
	        	field: 'operate',
	        	title: '操作',
	        	formatter: function (value, row, index) {
	        		var id = row.suId;
				    var result = '<div class="btn-group btn-group-xs">';
				    result += '<button type="button" class="btn btn-primary" onclick=editById("'+id+'") title="编辑">编辑</button>';
				    result += '<button type="button" class="btn btn-primary" onclick=restPwdById("'+id+'") title="重置密码">重置密码</button>';
				    if (row.userStatus=='USER_STATUS_NORMAL') {
				    	result += '<button type="button" class="btn btn-primary" onclick=disabledById("'+id+'") title="停用">停用</button>';
				    } else if (row.userStatus=='USER_STATUS_LOCK') {
				    	result += '<button type="button" class="btn btn-primary" onclick=unlockById("'+id+'") title="解锁">解锁</button>';
				    } else {
				    	result += '<button type="button" class="btn btn-primary" onclick=enableById("'+id+'") title="启用">启用</button>';
				    }
				    result += '<button type="button" class="btn btn-primary" onclick=deleteById("'+id+'") title="删除">删除</button>';
				    result += '</div>';
				    return result;
	            }
	        }
        ]
    };
    $MB.initTable('userTable', settings);
});

function search() {
    $MB.refreshTable('userTable');
}

function refresh() {
    $(".user-table-form")[0].reset();
    $MB.refreshTable('userTable');
}

function deleteUsers() {
    var selected = $("#userTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    var contain = false;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的用户！');
        return;
    }
    var id = "";
    for (var i = 0; i < selected_length; i++) {
        id = selected[i].suId;
        // if (i !== (selected_length - 1)) ids += ",";
        if (currentUserId === selected[i].suId) contain = true;
    }
    if (contain) {
        $MB.n_warning('勾选用户中包含当前登录用户，无法删除！');
        return;
    }
    $MB.confirm({
        text: "确定删除选中用户？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + '/user/removeuser', {suIds: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function exportUserExcel() {
    $.exportExcel(
		 ctx + '/excel/exportuser', 
		 { 
			 loginName: $(".user-table-form").find("input[name='loginName']").val().trim(),
             realName: $(".user-table-form").find("input[name='realName']").val().trim(),
             userStatus: $(".user-table-form").find("select[name='userStatus']").val()
		 }, 
		 'post'
	); 
}

function exportUserAccountExcel() {
    $.exportExcel(
		 ctx + '/excel/exportuseraccount', 
		 { 
			 loginName: $(".user-table-form").find("input[name='loginName']").val().trim(),
             realName: $(".user-table-form").find("input[name='realName']").val().trim(),
             userStatus: $(".user-table-form").find("select[name='userStatus']").val()
		 }, 
		 'post'
	); 
}

//自定义导出excel
$.exportExcel = function (url, data, method) {  
    // 获得url和data  
    if (url && data) {  
        // data 是 string 或者 array/object  
        data = typeof data == 'string' ? data : $.param(data);  
        // 把参数组装成 form的  input  
        var inputs = '';  
        $.each(data.split('&'), function () {  
            var pair = this.split('=');  
            inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';  
        });  
        // request发送请求  
        $('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>').appendTo('body').submit().remove();  
    } 
};

function editById(id){
	$.post(ctx + "/user/getuser", {suId: id}, function (result) {
		if (result.meta.code === 1000) {
            var $form = $('#user-add');
            var $orgTree = $('#orgTree');
            $form.modal();
            var user = result.data.user;
            $form.find(".user_password").hide();
            $("#user-add-modal-title").html('修改用户');
            $form.find("input[name='loginName']").val(user.loginName).attr("readonly", true);
            $form.find("input[name='realName']").val(user.realName);
            $form.find("input[name='userNumber']").val(user.userNumber);
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

function restPwdById(id){
	$MB.confirm({
        text: "确定重置用户密码？",
        confirmButtonText: "确定重置"
    }, function () {
        $.post(ctx + '/user/resetpasswd', {suId: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function disabledById(id){
	$MB.confirm({
        text: "确定禁用此用户？",
        confirmButtonText: "确定禁用"
    }, function () {
        $.post(ctx + '/user/disableuser', {suId: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function unlockById(id){
	$MB.confirm({
        text: "确定解锁此用户？",
        confirmButtonText: "确定解锁"
    }, function () {
        $.post(ctx + '/user/unlockuser', {suId: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function enableById(id){
	$MB.confirm({
        text: "确定启用此用户？",
        confirmButtonText: "确定启用"
    }, function () {
        $.post(ctx + '/user/enableuser', {suId: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function deleteById(id){
	$MB.confirm({
        text: "确定删除用户？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + '/user/removeuser', {suId: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}