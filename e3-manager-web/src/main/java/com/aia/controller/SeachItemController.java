package com.aia.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aia.common.pojo.SeachItem;
import com.aia.service.SeachItemService;

import cn.e3mall.common.utils.E3Result;

@Controller
public class SeachItemController {

	@Resource
	private SeachItemService seachItemService;
	
	@RequestMapping(value = "/index/item/import",method=RequestMethod.POST)
	@ResponseBody
	public E3Result importSeachItemList() throws Exception {
		 return seachItemService.importAllSeachIntoSolr();
	}
}
