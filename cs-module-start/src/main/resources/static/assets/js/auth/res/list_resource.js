$(function () {
    initTreeTable();
});

function initTreeTable() {
    var $menuTableForm = $(".menu-table-form");
    var setting = {
        id: 'srId',
        code: 'srId',
        parentCode : 'parentSrId',
        url: ctx + '/res/listresourcedata',
        methodType : 'POST',
        expandAll: true,
        expandColumn: "2",
        ajaxParams: {
        	resourceName: $menuTableForm.find("input[name='resourceName']").val().trim(),
        	resourceType: $menuTableForm.find("select[name='resourceType']").val()
        },
        columns: [
            {
            	title: '选择',
                field: 'selectItem',
                radio: true
            },
            {
                title: '编号',
                field: 'resourceCode'
            },
            {
                title: '名称',
                field: 'resourceName'
            },
            {
                title: '图标',
                field: 'icon',
                formatter: function (item, index) {
                    return '<i class="zmdi ' + item.icon + '"></i>';
                }
            },
            {
                title: '类型',
                field: 'resourceType',
                formatter: function (item, index) {
                    if (item.resourceType === 'RESOURCE_TYPE_MENU') return '<span class="badge badge-success">菜单</span>';
                    if (item.resourceType === 'RESOURCE_TYPE_BUTTON') return '<span class="badge badge-warning">按钮</span>';
                }
            },
            {
                title: '地址',
                field: 'url',
                formatter: function (item, index) {
                    return item.url === 'null' ? '' : item.url;
                }
            },
            {
                title: '权限标识',
                field: 'component',
                formatter: function (item, index) {
                    return item.component === 'null' ? '' : item.component;
                }
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

    $MB.initTreeTable('menuTable', setting);
}

function search() {
    initTreeTable();
}

function refresh() {
    $(".menu-table-form")[0].reset();
    initTreeTable();
    $MB.refreshJsTree("menuTree", createMenuTree());
}

function deleteMenu() {
    var ids = $("#menuTable").bootstrapTreeTable("getSelections");
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的菜单或按钮！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中菜单或按钮？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + '/res/removeresource', {srId: ids_arr}, function (result) {
            if (result.meta.code === 1000) {
                $MB.n_success(result.meta.msg);
                refresh();
            } else {
                $MB.n_danger(result.meta.msg);
            }
        });
    });
}