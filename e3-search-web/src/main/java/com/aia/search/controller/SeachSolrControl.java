package com.aia.search.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aia.common.pojo.SeachResult;
import com.aia.service.SeachItemService;

@Controller
public class SeachSolrControl {

	@Resource
	private SeachItemService seachItemService;
	
	private Integer rows = 20;
	
	@RequestMapping("/search")
	public String seachSolrByKeyWord(String keyword,@RequestParam(defaultValue="1")  Integer page, Model model) throws Exception {
		keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
		SeachResult seachResult = seachItemService.seachFromSolrBykeyword(keyword, page, rows);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", seachResult.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", seachResult.getRecourdCount());
		model.addAttribute("itemList",seachResult.getItemList());
		return "search";
	}
}
