package com.adanac.demo.bootstrap.action.zTree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ZTree
 */
@Controller
@RequestMapping("zTree")
public class ZTreeAction {

	@RequestMapping("toZTree")
	public String toZTree() {
		return "pages/zTree/demo.ftl";
	}
}
