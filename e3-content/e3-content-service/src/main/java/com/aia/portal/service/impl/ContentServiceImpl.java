package com.aia.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aia.common.pojo.Constain;
import com.aia.mapper.TbContentMapper;
import com.aia.pojo.TbContent;
import com.aia.pojo.TbContentExample;
import com.aia.portal.redis.JedisClient;
import com.aia.portal.service.ContentService;
import com.sun.tools.corba.se.idl.constExpr.And;

import cn.e3mall.common.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

	@Resource
	private TbContentMapper tbContentMapper;
	@Resource
	private JedisClient jedisClient;
	
	/**
	 * 在获取内容的是使用redis缓存
	 */
	@Override
	public List queryAll() {
		List<TbContent> contentList = new ArrayList<>();
		//查询redis缓存中是否有
		String strContentList = jedisClient.get(Constain.CONTENT_LIST);
		if (strContentList!=null && strContentList.length()!=0) {
			return JsonUtils.jsonToList(strContentList, TbContent.class);
		}
		TbContentExample example = new TbContentExample();
		contentList = tbContentMapper.selectByExample(example);
		//放置到缓存中
		jedisClient.set(Constain.CONTENT_LIST, JsonUtils.objectToJson(contentList));
		return contentList;
	}

}
