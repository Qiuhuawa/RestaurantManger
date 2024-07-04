package org.zkpk.cs.mapper;

import java.util.List;
import java.util.Map;

import org.zkpk.cs.dto.SysLogDto;
import org.zkpk.cs.entity.SysLog;
import org.zkpk.cs.entity.SysLogWithBLOBs;

public interface SysLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String slId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    int insert(SysLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    int insertSelective(SysLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    SysLogWithBLOBs selectByPrimaryKey(String slId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SysLog record);
    
    /**
     * 
     * @Description: 根据条件查询用户日志信息
     * @author HUCHAO
     * @date 2019-01-17 14:16:07
     * @param parameterMap
     * @return
     */
    List<SysLogDto> selectSysLogListByParameter(Map<String, Object> parameterMap);
    
    /**
     * 
     * @Description: 批量删除日志信息
     * @author HUCHAO
     * @date 2019-01-17 14:19:42
     * @param slIds
     * @return
     */
    int batchDeleteByLogIds(String[] slIds);
}