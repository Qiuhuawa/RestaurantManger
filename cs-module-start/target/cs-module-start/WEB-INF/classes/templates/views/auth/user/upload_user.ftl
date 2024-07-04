<link rel="stylesheet" type="text/css" href="${ctx}/assets/fonts/iconfont/iconfont.css">
<div class="modal fade" id="user-upload" data-keyboard="false" data-backdrop="static" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title pull-left" id="user-upload-modal-title">上传用户</h3>
            </div>
            <div class="modal-body">
            	<div class="step_one">
	                <form id="user-upload-form">
	                		<div class="user-upload-setp">
							    <div class="row">
							      <div class="align-middle text-primary">
							      <span class="icon-de_cir icon_box">
							        <span class="icon_nums text-white">1</span>
							      </span>
							      </div>
							      <span class="col border mx-3 my-auto"></span>
							      <div class="align-middle">
							        <span class="icon-de_cir icon_box">
							          <span class="icon_nums text-white">2</span>
							        </span>
							      </div>
							    </div>
							</div>
		  					<!--  第一步  -->
							<div class="step_box">
								<div class="field_box">
									<div class="up_box">
									    <input type="file" class="upload_file" id="uploadFile">
									    <span class="choose_file_btn">
									      	选择文件
									    </span>
									    <div class="upload_name">上传excel文件</div>
									    <div class="progress" style="display:none;" id="progress_div">
									      <div class="progress-bar upload-progress-bar" id="upper" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
									    </div>
									</div>
								</div>
								<div class="upload-bottom">
									<ul class="upload_notes">
									    <li><div class="import_basic">导入的excel文件需符合以下标准：</div></li>
									    <li>1.下载 &nbsp;<a class="down_demo" href="${ctx}/excel/downloadtemplate?fileName=userinfo.xlsx">成员导入模板</a></li>
									    <li>2.数据请勿放在合并的单元格中</li>
									    <li>3.文件大小请勿超过2.5M</li>
									</ul>
								</div>
							</div>
	                </form>
                </div>
                <!--  第二步  -->
                <div class="upload_table_box" style="display:none;">
					<div class="user-upload-setp">
						<div class="row">
						  <div class="align-middle">
						  <span class="icon-de_cir icon_box">
						    <span class="icon_nums text-mu ">1</span>
						  </span>
						  </div>
						  <span class="col border mx-3 my-auto"></span>
						  <div class="align-middle text-primary">
						    <span class="icon-de_cir icon_box">
						      <span class="icon_nums text-white">2</span>
						    </span>
						  </div>
						</div>
					</div>
					<div class="table_con">
						<p class="successResult">数据导入完成，全部成功！</p>
                  		<p class="failRusult">数据导入完成，<span>成功 <span class="success-count red">5</span> 条数据， 失败 <span class="failed-count red">0</span> 条数据</span>，以下信息存在问题，请修改后重新上传！</p>
						<table class="table table-bordered upload_table">
						  <thead>
						    <tr>
						      	<th>EXCEL行序列</th>
	                        	<th>用户名</th>
	                        	<th>姓名</th>
	                        	<th>失败信息</th>
						    </tr>
						  </thead>
						  <tbody id="err_body">
						    <tr>
						        <td role="rowIndex"></td>
		                        <td role="loginName"></td>
		                        <td role="realName"></td>
		                        <td role="note"></td>
						    </tr>
						  </tbody>
						</table>
					</div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-save" id="user-upload-button" name="uplad">上传</button>
                <button type="button" class="btn btn-save" id="user-reupload-button" name="reuplad" style="display:none;">重新导入</button>
                <button type="button" class="btn btn-secondary btn-close">关闭</button>
                <button class="btn-hide"></button>
            </div>
        </div>
    </div>
</div>