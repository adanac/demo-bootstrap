package com.adanac.demo.bootstrap.service.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.adanac.demo.bootstrap.entity.constant.CodeConst;
import com.adanac.demo.bootstrap.utils.String2MapUtil;
import com.adanac.demo.bootstrap.utils.db.DbTableUtil;
import com.adanac.framework.log.MyLogger;
import com.adanac.framework.log.MyLoggerFactory;

@Service("exportService")
public class ExportServiceImpl implements ExportService {

	// 日志
	private MyLogger log = MyLoggerFactory.getLogger(ExportServiceImpl.class);

	@Value("${exportType}")
	private String exportType;

	@Override
	public String getExportType() {
		return exportType;
	}

	@Override
	public String exportFile(String basePath, String tableName, List<Map<String, Object>> list) {

		String path = "";
		// 读取配置文件，判断导出的文件格式，是excel还是cvs
		String exportType = getExportType();
		path = this.wirteFile(basePath, tableName, list, exportType);
		return path;
	}

	private String wirteFile(String basePath, String tableName, List<Map<String, Object>> logList, String typeName) {
		String exportFilePath = "";

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			String title = ""; // 标题

			String fileName = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStr = sdf.format(new Date());
			basePath = basePath + "exportFile" + File.separator;
			// 如果文件夹不存在则创建
			File file = new File(basePath);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 判断所属参数，执行对应的处理方法
			fileName = CodeConst.PARAM_SHEETNAME;
			title = "部门编码[deptCode],部门名称[deptName],商品编码[goodCode],商品名称[goodName],差异类型[diffType],物流模式[tranMode],配送中心[dcCode],经营状态编码[operStaCode],经营状态名称[operStaName],供应商编码[suppCode],供应商名称[suppName],合同号[contractNo],建档日期[filDate]";
			title = getTitle(tableName);
			fileName = fileName + CodeConst.PARAM_UNDERLINE + timeStr;
			exportFilePath = basePath + fileName + CodeConst.PARAM_DOT + typeName;
			// 定义文件名格式并创建
			// csvFile = File.createTempFile(fileName, ".csv",new
			// File(basePath));
			csvFile = new File(exportFilePath);
			csvFile.createNewFile();
			exportFilePath = csvFile.getPath();

			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
					1024);
			// 写入文件头部
			csvFileOutputStream.write(title);
			csvFileOutputStream.newLine();

			// 拆分title
			String[] titleArray = title.split(",");
			// 写入文件内容
			for (int i = 0; i < logList.size(); i++) {
				Map<String, Object> map = logList.get(i);

				for (int icount = 0; icount < titleArray.length; icount++) {
					String enname = this.getEnname(titleArray[icount]); // 获取对应的表头信息
					String val = map.get(enname) == null ? "," : (map.get(enname).toString().trim() + ",");
					// 判断是不是以"0"开头的，如果是则在前面添加一个"'"
					val = this.startWith0(val);
					csvFileOutputStream.write(val);
				}
				csvFileOutputStream.newLine();
			}

			csvFileOutputStream.flush();

		} catch (Exception e) {
			log.error("ExportServiceImpl==>wirteCvs", new Object[] { e.getMessage() });
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				log.error("ExportServiceImpl==>wirteCvs", new Object[] { e.getMessage() });
			}
		}
		return exportFilePath;
	}

	private String getTitle(String tableName) {
		List<String> tableNameList = new ArrayList<>();
		tableNameList.add(tableName);
		try {
			Map<String, Object> map = DbTableUtil.getColumnCommentByTableName(tableNameList);
			return String2MapUtil.transMapToString(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取标题单元格里面的字段名（用"[]"括起来的部分）
	 * 
	 * @param title
	 * @return
	 */
	private String getEnname(String title) {
		return title.substring(title.indexOf("[") + 1, title.lastIndexOf("]"));
	}

	/**
	 * 判断是不是以"0"开头的，如果是则在前面添加一个"\t"
	 * 
	 * @param val
	 * @return
	 */
	private String startWith0(String val) {
		if (val.startsWith("0")) {
			if (!(val.equals("0,") || val.startsWith("0.") || val.equals("0"))) {
				val = "\t" + val;
			}
		}
		return val;
	}

}
