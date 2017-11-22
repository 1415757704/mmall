package com.aia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aia.portal.service.ContentCatService;

@Controller
public class ContentCatController {
	
	@Resource
	private ContentCatService contentCatService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	//根据parentid去查询对应的子项展开树形结构
	public List getContentCatList(@RequestParam(name="id",defaultValue="0")Long id) {
		List contentCatList = contentCatService.getContentCatList(id);
		return contentCatList;
	}
}
