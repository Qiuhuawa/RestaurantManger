var validator;
var $dictAddForm = $("#dict-add-form");

$(function () {
    validateRule();
    createDictTree();

    $("#dict-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getDict();
        validator = $dictAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "/dict/saveorupdatedict", $dictAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        refresh();
                        $MB.n_success(result.meta.msg);
                    } else {
                    	$MB.n_danger(result.meta.msg);
                    }
                });
            }
            if (name === "update") {
                $.post(ctx + "/org/saveorupdatedict", $dictAddForm.serialize(), function (result) {
                    if (result.meta.code === 1000) {
                        closeModal();
                        refresh();
                        $MB.n_success(result.meta.msg);
                    } else {
                    	$MB.n_danger(result.meta.msg);
                    }
                });
            }
        }
    });

    $("#dict-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#dict-add-button").attr("name", "save");
    $("#dict-add-modal-title").html('新增字典');
    validator.resetForm();
    $MB.closeAndRestModal("dict-add");
    $MB.refreshJsTree("dictTree", createDictTree());
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $dictAddForm.validate({
        rules: {
        	dictCode: {
                required: true,
                minlength: 3,
                maxlength: 32
            },
            dictName: {
                required: true,
                minlength: 3,
                maxlength: 32
            }
        },
        messages: {
        	dictCode: {
                required: icon + "请输入字典编码",
                minlength: icon + "字典编码长度3到32个字符",
                maxlength: icon + "字典编码长度3到32个字符"
            },
            dictName: {
                required: icon + "请输入字典名称",
                minlength: icon + "字典名称长度3到32个字符",
                maxlength: icon + "字典名称长度3到32个字符"
            }
        }
    });
}

function createDictTree() {
    $.post(ctx + "/dict/treedict", {}, function (result) {
        if (result.meta.code === 1000) {
            var data = result.data.dict;
            $('#dictTree').jstree({
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

function getDict() {
    var ref = $('#dictTree').jstree(true);
    var dictIds = ref.get_checked();
    $("[name='parentSdId']").val(dictIds[0]);
}

function updateDict() {
    var selected = $("#dictTable").bootstrapTreeTable("getSelections");
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
    $.post(ctx + "/dict/getdict", {sdId: orgId}, function (result) {
        if (result.meta.code === 1000) {
            var $form = $('#dict-add');
            var $dictTree = $('#dictTree');
            $form.modal();
            var dict = result.data.dict;
            $("#dict-add-modal-title").html('修改字典');
            $form.find("input[name='dictCode']").val(dict.dictCode);
            $form.find("input[name='dictName']").val(dict.dictName);
            $form.find("input[name='sdId']").val(dict.sdId);
            $form.find("input[name='dictRemark']").val(dict.dictRemark);
            $form.find("input[name='priority']").val(dict.priority);
            var isAvailable = dict.isAvailable?1:0;
            $form.find("input[name='isAvailable'][value='" + isAvailable + "']").prop("checked", true);
            $dictTree.jstree('select_node', org.parentSdId, true);
            $dictTree.jstree('disable_node', org.sdId);
            $("#dict-add-button").attr("name", "update");
        } else {
            $MB.n_danger(result.meta.msg);
        }
    });
}