var validator;
var $menuAddForm = $("#menu-add-form");
var $menuCode = $menuAddForm.find("input[name='resourceCode']");
var $menuName = $menuAddForm.find("input[name='resourceName']");
var $url = $menuAddForm.find("input[name='url']");
var $icon = $menuAddForm.find("input[name='icon']");
var $icon_drop = $menuAddForm.find("div.icon-drop");

var $menuUrlListRow = $menuAddForm.find(".menu-url-list-row");
var $menuIconListRow = $menuAddForm.find(".menu-icon-list-row");
var $menuPermsListRow = $menuAddForm.find(".menu-perms-list-row");

$(function () {
    $icon_drop.hide();
    initUrlAndPermsList();
    validateRule();
    createMenuTree();

    $menuAddForm.find("input[name='resourceType']").change(function () {
        var $value = $menuAddForm.find("input[name='resourceType']:checked").val();
        if ($value === "RESOURCE_TYPE_MENU") {
        	$menuCode.parent().prev().text("菜单编码：");
            $menuName.parent().prev().text("菜单名称：");
            $menuUrlListRow.show();
            $menuIconListRow.show();
        } else {
        	$menuCode.parent().prev().text("按钮编码：");
            $menuName.parent().prev().text("按钮名称：");
            $menuUrlListRow.hide();
            $menuIconListRow.hide();
        }
    });

    $menuAddForm.find("input[name='icon']").focus(function () {
        $icon_drop.show();
    });

    $("#menu-add").click(function (event) {
        var obj = event.srcElement || event.target;
        if (!$(obj).is("input[name='icon']")) {
            $icon_drop.hide();
        }
    });

    $icon_drop.find(".menu-icon .col-sm-6").on("click", function () {
        var icon = "zmdi " + $(this).find("i").attr("class").split(" ")[1];
        $icon.val(icon);
    });

    $("#menu-add .btn-save").click(function () {
        $menuPermsListRow.find("input[name='component']").val(
            $menuPermsListRow.find(".autocomplete-input").val()
        );
        $menuUrlListRow.find("input[name='url']").val(
            $menuUrlListRow.find(".autocomplete-input").val()
        );
        var name = $(this).attr("name");
        getMenu();
        validator = $menuAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "/res/saveorupdateresource", $menuAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        refresh();
                        closeModal();
                        $MB.n_success(result.meta.msg);
                    } else $MB.n_danger(result.meta.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "/res/saveorupdateresource", $menuAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        refresh();
                        closeModal();
                        $MB.n_success(result.meta.msg);
                    } else $MB.n_danger(result.meta.msg);
                });
            }
        }
    });

    $("#menu-add .btn-close").click(function () {
        $("input:radio[value='RESOURCE_TYPE_MENU']").trigger("click");
        closeModal();
    });

});

function closeModal() {
	$menuCode.parent().prev().text("菜单编码：");
    $menuName.parent().prev().text("菜单名称：");
    $("#menu-add-modal-title").html('新增菜单/按钮');
    $("#menu-add-button").attr("name", "save");
    $menuUrlListRow.show();
    $menuIconListRow.show();
    validator.resetForm();
    $MB.closeAndRestModal("menu-add");
    $MB.refreshJsTree("menuTree", createMenuTree());

}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $menuAddForm.validate({
        rules: {
        	resourceCode: {
        		required: true,
        		checkResourceCode: true,
                minlength: 4,
                maxlength: 32
        	},
        	resourceName: {
                required: true,
                minlength: 1,
                maxlength: 32
            }
        },
        messages: {
        	resourceCode: {
        		required: icon + "请输入资源编码",
                minlength: icon + "资源编码长度4到32个字符",
                maxlength: icon + "资源编码长度4到32个字符"
        	},
        	resourceName: {
                required: icon + "请输入资源名称",
                minlength: icon + "资源名称长度3到32个字符",
                maxlength: icon + "资源名称长度3到32个字符"
            }
        }
    });
    
    //自定义正则表达示验证方法
	$.validator.addMethod("checkResourceCode", function(value, element, params){
			var ret = /^[a-zA-Z0-9_]{4,32}$/;
			return this.optional(element)||(ret.test(value));
		},"资源编码格式不正确！");
}

function createMenuTree() {
    $.post(ctx + "/res/treeresource", {}, function (result) {
        if (result.meta.code === 1000) {
            var data = result.data.tree;
            $('#menuTree').jstree({
                "core": {
                    'data': data,
                    'multiple': false
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
    })

}

function getMenu() {
    var ref = $('#menuTree').jstree(true);
    $("[name='parentSrId']").val(ref.get_checked()[0]);
}

function updateMenu() {
    var selected = $("#menuTable").bootstrapTreeTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的菜单或按钮！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个菜单或按钮！');
        return;
    }
    var srId = selected[0].id;
    $.post(ctx + "/res/getresource", {srId : srId}, function (result) {
        if (result.meta.code === 1000) {
            var $form = $('#menu-add');
            var $menuTree = $('#menuTree');
            $form.modal();
            var res = result.data.res;
            $("#menu-add-modal-title").html('修改菜单/按钮');
            $("input:radio[value='" + res.resourceType + "']").trigger("click");
            $form.find("input[name='resourceCode']").val(res.resourceCode);
            $form.find("input[name='resourceName']").val(res.resourceName);
            $form.find("input[name='oldResouceName']").val(res.resourceName);
            $form.find("input[name='srId']").val(res.srId);
            $form.find("input[name='icon']").val(res.icon);
            $('#menu-url-list').find(".autocomplete-input").val(res.url == null ? "" : res.url);
            $('#menu-perms-list').find(".autocomplete-input").val(res.component == null ? "" : res.component);
            $form.find("input[name='description']").val(res.description);
            $form.find("input[name='priority']").val(res.priority);
            var isAdmin = res.isAdmin?1:0;
            var isAvailable = res.isAvailable?1:0;
            $form.find("input[name='isAdmin'][value='" + isAdmin + "']").prop("checked", true);
            $form.find("input[name='isAvailable'][value='" + isAvailable + "']").prop("checked", true);
            $menuTree.jstree('select_node', res.parentSrId, true);
            $menuTree.jstree('disable_node', res.srId);
            $("#menu-add-button").attr("name", "update");
        } else {
            $MB.n_danger(result.meta.msg);
        }
    });
}

function initUrlAndPermsList() {
    $.getJSON(ctx + "/res/listurl", function (result) {
    	// urls complete
        $('#menu-url-list').autocomplete({
            hints: result,
            keyname: 'url',
            // width: 300,
            // height: 29,
            valuename: 'url',
            showButton: false,
            onSubmit: function (text) {
                $('#menu-url').val(text);
            }
        });
        // perms complete
        $('#menu-perms-list').autocomplete({
            hints: result,
            keyname: 'perms',
            // width: 300,
            // height: 29,
            valuename: 'perms',
            showButton: false,
            onSubmit: function (text) {
                $('#menu-perms').val(text);
            }
        });
    });
}