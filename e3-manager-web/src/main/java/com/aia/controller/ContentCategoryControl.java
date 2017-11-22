package com.aia.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aia.portal.service.ContentCatService;

import cn.e3mall.common.utils.E3Result;

@Controller
public class ContentCategoryControl {

	@Resource
	private ContentCatService contentCatService;
	
	@RequestMapping(name = "/content/category/create", method=RequestMethod.POST)
	@ResponseBody
	public E3Result  addContentCategory(Long parentId, String name) {
		E3Result e3Result = contentCatService.addContentCategory(parentId, name);
		return e3Result;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public E3Result updateContentCategory(Long id, String name) {
		E3Result e3Result = contentCatService.updateContentCategory(id, name);
		return e3Result;
	}
	
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public E3Result deleteContentCategory(Long id){
		E3Result e3Result = contentCatService.deleteContentCategory(id);
		return e3Result;
	}
	
	@RequestMapping(value = "/content/query/list",method = RequestMethod.GET)
	@ResponseBody
	public List getContentCatList(
			@RequestParam(name="categoryId",defaultValue="0")Long categoryId,
			@RequestParam(name="page",defaultValue="0")Integer page,
			@RequestParam(name="rows",defaultValue="20")Integer rows) {
		List contentCatList = contentCatService.getContentCatList(categoryId,page,rows);
		return contentCatList;
	}
}
