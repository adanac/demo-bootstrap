package com.adanac.demo.bootstrap.action.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.adanac.demo.bootstrap.entity.common.CommonDto;
import com.adanac.demo.bootstrap.entity.common.CommonExcelDto;
import com.adanac.demo.bootstrap.service.common.CommonService;
import com.adanac.demo.bootstrap.utils.UploadUtil;
import com.adanac.framework.web.controller.BaseResult;
import com.adanac.tool.e2e.ExcelHelper;
import com.adanac.tool.e2e.exception.ExcelContentInvalidException;
import com.adanac.tool.e2e.exception.ExcelParseException;
import com.adanac.tool.e2e.exception.ExcelRegexpValidFailedException;

/**
 * 从Excel导入到数据中
 */
@Controller
@RequestMapping("import")
public class ImportController extends MultiActionController {

	@Autowired
	private CommonService commonService;

	@RequestMapping("toExcel")
	public String toExcel() {
		return "pages/excel/excel.ftl";
	}

	/**
	 * 1、文件上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView uploadFiles(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得上传的文件（根据前台的name名称得到上传的文件）
		MultiValueMap<String, MultipartFile> multiValueMap = multipartRequest.getMultiFileMap();
		List<MultipartFile> file = multiValueMap.get("clientFile");
		// MultipartFile file = multipartRequest.getFile("clientFile");
		if (!file.isEmpty()) {
			// 在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹
			System.out.println("=================" + file.get(0).getName() + file.get(0).getSize());
		}

		return mav;
	}

	/**
	 *
	 * @param name
	 * @param file
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fileName") String fileName, @RequestParam("clientFile") MultipartFile clientFile,
			HttpSession session) {
		if (!clientFile.isEmpty()) {
			// 在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹
			System.out.println("=================" + clientFile.getSize());
			try {
				// 上传到项目根目录下,返回上传后的文件名
				String uploadPath = UploadUtil.upload(request, response, fileName);
				// 解析excel写入数据库
				ExcelHelper eh = ExcelHelper.readExcel(uploadPath);
				String[][] datas = eh.getDatas();

				List<CommonExcelDto> entitys = null;
				List<CommonDto> dtoList = new ArrayList<CommonDto>();
				entitys = eh.toEntitys(CommonExcelDto.class);
				for (CommonExcelDto dd : entitys) {
					CommonDto dto = new CommonDto();
					dto.setId(dd.getId());
					dto.setUserName(dd.getUserName());
					dto.setDeptCode(dd.getDeptCode());
					dto.setSex(dd.getSex() == "男" ? 1 : 0);
					dto.setAge(dd.getAge());
					dto.setPasswd(dd.getPasswd());
					dtoList.add(dto);
				}
				BaseResult result = commonService.addCommonDto(dtoList);
				if (result.getStatus() == "1") {
					return "导入成功";
				} else {
					return "导入失败";
				}

			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (ExcelParseException e) {
				e.printStackTrace();
			} catch (ExcelContentInvalidException e) {
				e.printStackTrace();
			} catch (ExcelRegexpValidFailedException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
