$(function () {
    var settings = {
        url: ctx + "/role/listroledata",
        contentType: "application/x-www-form-urlencoded",
        method: 'POST',
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                roleName: $(".role-table-form").find("input[name='roleName']").val().trim()
            };
        },
        columns: [
        	{
	            checkbox: true
	        }, 
	        {
	            field: 'roleName',
	            title: '角色'
	        }, 
	        {
	            field: 'description',
	            title: '描述'
	        }, 
	        {
	            field: 'createTime',
	            title: '创建时间',
	            formatter: function (item, row, index) {
                	return moment(item).format("YYYY-MM-DD HH:mm:ss") === 'Invalid date' ? '-' : moment(item).format("YYYY-MM-DD HH:mm:ss");
                }
	        },
	        {
	        	field: 'operate',
	        	title: '操作',
	        	formatter: function (item, row, index) {
	        		var id = row.srId;
				    var result = '<div class="btn-group btn-group-xs">';
				    result += '<button type="button" class="btn btn-primary" onclick=editById("'+id+'") title="编辑">编辑</button>';
				    result += '<button type="button" class="btn btn-primary" onclick=deleteById("'+id+'") title="删除">删除</button>';
				    result += '</div>';
				    return result;
	            }
	        }
	   ]
    };

    $MB.initTable('roleTable', settings);
});

function search() {
    $MB.refreshTable('roleTable');
}

function refresh() {
    $(".role-table-form")[0].reset();
    search();
}

function deleteRoles() {
    var selected = $("#roleTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的角色！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].srId;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "删除选中角色将导致该角色对应账户失去相应的权限，确定删除？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + '/role/removerole', {srIds: ids}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function editById(id){
	$.post(ctx + "/role/getrole", {srId: id}, function (result) {
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

function deleteById(id){
	$MB.confirm({
        text: "确定删除角色？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + '/role/removerole', {srId: id}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

