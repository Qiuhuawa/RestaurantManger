var validator;
var $orgAddForm = $("#org-add-form");

$(function () {
    validateRule();
    createOrgTree();

    $("#org-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getOrg();
        validator = $orgAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "/org/saveorupdateorganization", $orgAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        refresh();
                        $MB.n_success(result.meta.msg);
                    } else $MB.n_danger(result.meta.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "/org/saveorupdateorganization", $orgAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        refresh();
                        $MB.n_success(result.meta.msg);
                    } else $MB.n_danger(result.meta.msg);
                });
            }
        }
    });

    $("#org-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#org-add-button").attr("name", "save");
    $("#org-add-modal-title").html('新增组织');
    validator.resetForm();
    $MB.closeAndRestModal("org-add");
    $MB.refreshJsTree("orgTree", createOrgTree());
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $orgAddForm.validate({
        rules: {
        	orgCode: {
                required: true,
                minlength: 3,
                maxlength: 32
            },
            orgName: {
                required: true,
                minlength: 1,
                maxlength: 50
            }
        },
        messages: {
        	orgCode: {
                required: icon + "请输入组织编码",
                minlength: icon + "组织编码长度3到32个字符",
                maxlength: icon + "组织编码长度3到32个字符"
            },
        	orgName: {
                required: icon + "请输入组织名称",
                minlength: icon + "组织名称长度1到50个字符",
                maxlength: icon + "组织名称长度1到50个字符"
            }
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

function getOrg() {
    var ref = $('#orgTree').jstree(true);
    var orgIds = ref.get_checked();
    $("[name='parentSoId']").val(orgIds[0]);
}

function updateOrg() {
    var selected = $("#orgTable").bootstrapTreeTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的组织！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个组织！');
        return;
    }
    var orgId = selected[0].id;
    $.post(ctx + "/org/getorganization", {soId: orgId}, function (result) {
        if (result.meta.code === 1000) {
            var $form = $('#org-add');
            var $orgTree = $('#orgTree');
            $form.modal();
            var org = result.data.org;
            $("#org-add-modal-title").html('修改组织');
            $form.find("input[name='orgCode']").val(org.orgCode);
            $form.find("input[name='orgName']").val(org.orgName);
            $form.find("input[name='soId']").val(org.soId);
            $form.find("input[name='description']").val(org.description);
            $form.find("input[name='priority']").val(org.priority);
            var isAvailable = org.isAvailable?1:0;
            $form.find("input[name='isAvailable'][value='" + isAvailable + "']").prop("checked", true);
            $orgTree.jstree('select_node', org.parentSoId, true);
            $orgTree.jstree('disable_node', org.soId);
            $("#org-add-button").attr("name", "update");
        } else {
            $MB.n_danger(result.meta.msg);
        }
    });
}