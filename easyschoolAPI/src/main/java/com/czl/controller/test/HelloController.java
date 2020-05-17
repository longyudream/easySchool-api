package com.czl.controller.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.czl.base.constants.SystemConstants;
import com.czl.entity.ResultEntity;
import com.czl.exception.ServiceException;
import com.czl.service.common.ComDataDS1Service;
import com.czl.service.common.ComDataService;
import com.czl.utils.JsonSchemaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@SuppressWarnings({ "rawtypes", "unchecked" })
@RequestMapping(value = "/test")
public class HelloController {

	@Autowired
	private ComDataService comDataService;

	@Autowired
	private ComDataDS1Service comDataDS1Service;

	@RequestMapping(value = "/hello")
	public Object hello() {
		log.debug("访问成功");
		log.info("访问成功");
		log.error("访问成功");
		return "Welcome to visit!";
	}

	/**
	 * 测试用功能：从主从数据库中分别读取数据。<br>
	 * 请求报文样例: { "table_name": "TB_SYS_SEQUENCEMANAGER" }<br>
	 * 
	 * @param param JSON请求报文
	 * @return
	 */
	@RequestMapping(value = "/hello_data")
	public Object helloData(@RequestBody String param) {
		log.debug("helloData 处理开始，参数: " + param);
		try {
			String schemaResult = JsonSchemaUtil.validJson("validHelloData", param);
			if (schemaResult != null) {
				log.debug("验证失败" + schemaResult);
				throw new ServiceException("ApiMsg3001");
			}
			JSONObject jsonParam = JSONObject.parseObject(param);
			String tableName = jsonParam.getString("table_name");
			Integer paramPageIndex = jsonParam.getInteger("page");
			Integer paramPageSize = jsonParam.getInteger("limit");
			if (paramPageIndex == null) {
				paramPageIndex = SystemConstants.DEFAULT_PAGE_INDEX;
			}
			if (paramPageSize == null) {
				paramPageSize = SystemConstants.DEFAULT_PAGE_SIZE;
			}
			Map<String, Object> rowLimit = new HashMap<String, Object>();
			int row_start = 1 + (paramPageIndex - 1) * paramPageSize;
			int row_end = paramPageIndex * paramPageSize;
			rowLimit.put("row_start", row_start);
			rowLimit.put("row_end", row_end);

			int rowCnt = 0;

			List<Map> dataList = comDataService.getTableData(tableName, null, null, rowLimit);
			JSONObject json = new JSONObject(true);
			if (dataList != null && dataList.size() > 0) {
				rowCnt = dataList.size();
				json.put("rows", dataList);
			}
			json.put("row_cnt", rowCnt);

			List<Map> dataListDs1 = comDataDS1Service.getTableData(tableName, null, null, rowLimit);
			JSONObject jsonDs1 = new JSONObject(true);
			rowCnt = 0;
			if (dataListDs1 != null && dataListDs1.size() > 0) {
				rowCnt = dataListDs1.size();
				json.put("rows", dataListDs1);
			}
			jsonDs1.put("row_cnt", rowCnt);

			JSONObject tableData = new JSONObject(true);
			tableData.put("data_default", json);
			tableData.put("data_ds1", jsonDs1);

			ResultEntity result = new ResultEntity(ResultEntity.RESULT_CODE_OK, "ApiMsg0000", null, tableData);
			log.debug("返回结果: " + JSONObject.toJSONString(result));
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e.fillInStackTrace());
			ResultEntity result = new ResultEntity(ResultEntity.RESULT_CODE_ERROR, "ApiMsg1111", null, e.getMessage());
			return result;
		} finally {
			log.debug("helloData 处理完成！");
		}

	}
}
