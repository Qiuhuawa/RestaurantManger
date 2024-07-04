$(function () {
	$('#uploadFile').change(function(event) {
		var uploadFile = $("#uploadFile").get(0).files[0];
 		$('.upload_name').html(uploadFile.name);
 		$('.choose_file_btn').html('上传excel文件');
	});
	
    $("#org-upload .btn-save").click(function () {
        var name = $(this).attr("name");
        if (name === "uplad") {
        	var upFile = $("#uploadFile").get(0).files[0];
    	   	if($('.upload_name').html() != '上传excel文件' && typeof(upFile) != 'undefined' 
    	   		&& (($('#uploadFile').get(0).files[0].size).toFixed(2)) <= (2.5*1024*1024)){
    	   		var fileName = upFile.name;
    	   		var fileType = fileName.substring(fileName.lastIndexOf('.'),fileName.length).toLowerCase();
    	   		if(fileType!='.xlsx' && fileType!='.xls'){ //设置上传文件类型
    	   			$MB.n_warning("请上传xlsx,xls格式的文件!");
    	   			return;
    	   		}else{
    	   			uploadFile();
    	   		}
    	   	}else{
    	   		$MB.n_warning('上传文件未选择或选择文件大不能超过2.5M');
    	   		return;
    	   	}
        } else if (name === "reuplad") {
    	    $('.step_one').show();
    	    $('.upload_table_box').hide();
    	    $('#org-upload-button').show();//显示上传按钮
    	    $('#org-reupload-button').hide();//隐藏重新上传按钮
    	    $('#progress_div').hide();
    	    $("#upper").css("width", "0%").text("0%");
    	    $('.upload_name').html('上传excel文件');
    	    $("#uploadFile").val('');
    	    $('.showWrong').hide();
    	    $('.successResult').hide();
    	    $('.failRusult').hide();
        }
    });
 	
 	uploadFile = function(){
 		$('#progress_div').show();
 		var uploadFile = $('#uploadFile').get(0).files[0];
 		var formData = new FormData();
 		formData.append("uploadFile", uploadFile);
 		$.ajax({
 			type: "POST",
 			url: ctx + "/excel/uploadorg",
 			data: formData ,
 			dataType: 'json',
 			processData : false,
 			//必须false才会自动加上正确的Content-Type
 			contentType : false,
 			xhr: function(data){
 				var xhr = $.ajaxSettings.xhr();
 				if(onprogress && xhr.upload) {
 					xhr.upload.addEventListener("progress", onprogress, false);
 					return xhr;
 				}
 			},
 			error: function(data){
 				$MB.n_danger("出错了！请联系管理员");	 
 				return false;
 			},
 			success: function(data) {
 				if (data.meta.code == 1000) {
 					var resultDate = data.data.result;
 					var passedCount = resultDate.PASSED_COUNT;
 					var offedCount = resultDate.OFFED_COUNT;
 					var rowCount = resultDate.ROW_COUNT;
 					var offedData = resultDate.OFFED_DATA;
 					if(resultDate.FLAG){
 						if(passedCount == rowCount){
 							$('.upload_table_box').show();
 							$('.step_one').hide();
 							$('#org-upload-button').hide();//隐藏上传按钮
 							$('#org-reupload-button').show();//显示重新上传按钮
 							$('.upload_name').html('上传excel文件');
 							$('.successResult').html('数据导入完成，全部成功！');
 							$('.successResult').show();
 						}else{
 							$('.upload_table_box').show();
 							$('.step_one').hide();
 							$('#org-upload-button').hide();//隐藏上传按钮
 							$('#org-reupload-button').show();//显示重新上传按钮
 							$('.upload_name').html('上传excel文件');
 							$('.failRusult').find('.success-count').html(passedCount);//成功数量
 							$('.failRusult').find('.failed-count').html(offedCount);//失败数量
 							$('.failRusult').show();
 							if(0 != offedCount){
 								$('#err_body').find('tr').remove();
 								var errBody = $('#err_body');
 								for(var i = 0; i < offedData.length; i++){
 									var tr = createTrObj();
 									var rowData = offedData[i];
 									var rowIndex = rowData.rowIndex;
 									var cells = rowData.cells;
 									$(tr).find('td[class="rowIndex"]').text(rowIndex);
 									for(var j = 0; j < cells.length; j++){
 										var cell = cells[j];
 										var cellValue = cell.cellValue;
 										var extraInfo = cell.extraInfo;
 										var td;
 										switch(cell.key){
 										case 'orgCode':
 											td = $(tr).find('td[class="orgCode"]').text(cellValue);
 											break;
 										case 'orgName':
 											td = $(tr).find('td[class="orgName"]').text(cellValue);
 											break;
 										case 'note':
 											td = $(tr).find('td[class="note"]').text(cellValue);
 											break;
 										default:
 											
 										}
 										if(null != extraInfo && '' != extraInfo && 'undefined' != extraInfo){
 											if('orgName' == cell.key){
 												td.attr('class','bggray');
 											}
 											if('orgCode' == cell.key && '' == cellValue){
 												td.attr('class','bggray');
 											}
 											td.attr('title',extraInfo);
 											td = $(tr).find('td[class="note"]').text(extraInfo);
 										}
 									}
 									$(tr).appendTo(errBody);
 								}
 							}
 						}
 					}else{
 						$('.step_one').hide();
 						$('#org-upload-button').hide();//隐藏上传按钮
 						$('#org-reupload-button').show();//显示重新上传按钮
 						$('.upload_name').html('上传excel文件');
 						$('.layui-table').hide();
 						$('.successResult').html('导入出错了，请联系管理员！');
 						$('.successResult').show();
 					}
 				} else {
 					$('#org-upload-button').hide();//隐藏上传按钮
 					$('#org-reupload-button').show();//显示重新上传按钮
 				}
 			}
 		});
 		return false;
 	}
 	
 	//侦查附件上传情况,这个方法大概0.05-0.1秒执行一次
 	onprogress = function (evt){
 		var loaded = evt.loaded; //已经上传大小情况
 		var tot = evt.total; //附件总大小
 		var per = Math.floor(100 * loaded / tot); //已经上传的百分比
 		$("#upper").css("per", per + "%").text(per + "%");
 	}

    $("#org-upload .btn-close").click(function () {
        closeUploadModal();
    });
});

//创建tr
function createTrObj(){
	var trObj = document.createElement("tr");
	var tdObj = createTd();
	for(var j = 0; j < 4 ; j++){
		tdObj = createTd(j);
		trObj.appendChild(tdObj);
	}
	return trObj;
}

//创建td
function createTd(flag){
	var tdObj = document.createElement("td");
	if(flag == '0'){
		tdObj.className = "rowIndex";
    }else if(flag == '1'){
    	tdObj.className = "orgCode";
    }else if(flag == '2'){
    	tdObj.className = "orgName";
    }else if(flag == '3'){
    	tdObj.className = "note";
    }
	return tdObj;
}

function closeUploadModal() {
    $MB.closeAndRestModal("org-upload");
    $MB.refreshJsTree("orgTree", createOrgTree());
}