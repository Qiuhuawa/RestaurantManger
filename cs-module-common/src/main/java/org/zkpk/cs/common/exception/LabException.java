package org.zkpk.cs.common.exception;

import org.zkpk.cs.common.result.ResultEnum;

public class LabException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private Integer code;
	
    private String msg;
    
    public LabException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public LabException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public LabException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}
	
	public LabException(Integer code, String msg, Throwable e) {
		super(msg, e);
		this.code = code;
		this.msg = msg;
	}
	
    public LabException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }
    
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}

