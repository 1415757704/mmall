package com.aia.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aia.pojo.TbContent;
import com.aia.portal.pojo.PageContent;
import com.aia.portal.service.ContentService;

@Controller
public class PageControl {

	@Resource
	private ContentService contentService;
	/**
	 * 这个方法实现在输入http://localhost:8084的时候跳转到首页
	 * 因为首先会去搜索webapp下是否有index.html文件，这里没有，
	 * 所以会被这个handler拦截，因为是index.html的请求
	 * 这里引导访问/jsp/index.jsp
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		//查询首页数据
		List<TbContent> list = contentService.queryAll();
		List<PageContent> result  = new ArrayList<PageContent>();
		PageContent pageContent ;
		for (TbContent content : list) {
			pageContent = new PageContent();
			pageContent.setIndex(content.getId());
			pageContent.setPic(content.getPic());
			pageContent.setTitle(content.getTitle());
			pageContent.setUrl(content.getContent());
			result.add(pageContent);
		}
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("ad1List", result);
		return modelAndView;
	}
}
