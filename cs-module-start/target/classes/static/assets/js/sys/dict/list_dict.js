$(function() {
    initTreeTable();
});

function initTreeTable() {
	var $menuTableForm = $(".dict-table-form");
    var setting = {
        id: 'sdId',
        code: 'sdId',
        parentCode : 'parentSdId',
        url: ctx + '/dict/listdictdata',
        methodType : 'POST',
        expandAll: false,
        expandColumn: "2",
        ajaxParams: {
        	dictCode: $menuTableForm.find("input[name='dictCode']").val().trim(),
        	dictName: $menuTableForm.find("input[name='dictName']").val().trim()
        },
        columns: [
        	{
        		title: '选择',
                field: 'selectItem',
                radio: true
            },
            {
                title: '编码',
                field: 'dictCode',
            },
            {
                title: '名称',
                field: 'dictName'
            },
            {
                title: '备注',
                field: 'dictRemark'
            },
            {
                title: '创建时间',
                field: 'createTime',
                formatter: function (item, row, index) {
                	return moment(item.createTime).format("YYYY-MM-DD HH:mm:ss");
                }
            }
        ]
    };

    $MB.initTreeTable('dictTable', setting);
}

function search() {
    initTreeTable();
}

function refresh() {
    $(".dict-table-form")[0].reset();
    search();
    $MB.refreshJsTree("dictTree", createDictTree());
}

function deleteDict() {
    var ids = $("#dictTable").bootstrapTreeTable("getSelections");
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的字典！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中字典？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + '/dict/removedict', {sdId: ids_arr}, function(result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}
