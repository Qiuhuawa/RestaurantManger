$(function() {
    initTreeTable();
});

function initTreeTable() {
	var $orgTableForm = $(".org-table-form");
    var setting = {
        id: 'soId',
        code: 'soId',
        parentCode : 'parentSoId',
        url: ctx + '/org/listorganizationdata',
        methodType : 'POST',
        expandAll: true,
        expandColumn: "2",
        ajaxParams: {
        	orgName: $orgTableForm.find("input[name='orgName']").val().trim()
        },
        columns: [
        	{
        		title: '选择',
                field: 'selectItem',
                radio: true
            },
            {
                title: '编码',
                field: 'orgCode',
            },
            {
                title: '名称',
                field: 'orgName'
            },
            {
                title: '描述',
                field: 'description'
            },
            {
                title: '创建时间',
                field: 'createTime',
                formatter: function (item, index) {
                    return moment(item.createTime).format("YYYY-MM-DD HH:mm:ss");
                }
            }
        ]
    };

    $MB.initTreeTable('orgTable', setting);
}

function search() {
    initTreeTable();
}

function refresh() {
    $(".org-table-form")[0].reset();
    search();
    $MB.refreshJsTree("orgTree", createOrgTree());
}

function deleteOrg() {
    var ids = $("#orgTable").bootstrapTreeTable("getSelections");
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的部门！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中组织？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + '/org/removeorganization', {soId: ids_arr}, function(result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}

function exportOrgExcel(){
	$.post(ctx + "/excel/org",$(".org-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}
