package org.zkpk.cs.common.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @Description: 返回对象信息
 * @author HUCHAO
 * @date 2018年4月17日 下午1:37:12
 */
public class ResultMessage {
	
	// 消息头meta 存放状态信息 code message
	private Map<String, Object> meta = new HashMap<String, Object>();

	// 消息内容 存储实体交互数据
	private Map<String, Object> data = new HashMap<String, Object>();

    public Map<String, Object> getMeta() {
        return meta;
    }

    public ResultMessage setMeta(Map<String, Object> meta) {
        this.meta = meta;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ResultMessage setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
    
    public ResultMessage addMeta(String key, Object object) {
        this.meta.put(key,object);
        return this;
    }
    
    public ResultMessage addData(String key, Object object) {
        this.data.put(key, object);
        return this;
    }
    
    public ResultMessage success(ResultEnum resultEnum) {
        this.addMeta("success", Boolean.TRUE);
        this.addMeta("code", resultEnum.getCode());
        this.addMeta("msg", resultEnum.getMsg());
        return this;
    }
    
    public ResultMessage error(ResultEnum resultEnum) {
        this.addMeta("success", Boolean.FALSE);
        this.addMeta("code", resultEnum.getCode());
        this.addMeta("msg", resultEnum.getMsg());
        return this;
    }
    
	public ResultMessage success(int statusCode, String statusMsg) {
		this.addMeta("success", Boolean.TRUE);
		this.addMeta("code", statusCode);
		this.addMeta("msg", statusMsg);
		return this;
	}
	
	public ResultMessage error(int statusCode, String statusMsg) {
		this.addMeta("success", Boolean.FALSE);
		this.addMeta("code", statusCode);
		this.addMeta("msg", statusMsg);
		return this;
	}
    
}
