package com.aia.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.aia.common.pojo.SeachItem;
import com.aia.common.pojo.SeachResult;
import com.aia.service.SeachItemService;
import com.aia.service.mapper.SeachItemMapper;
import com.aia.service.utils.SeachSolrDaoUtils;

import cn.e3mall.common.utils.E3Result;

@Service
public class SeachItemServiceImpl implements SeachItemService {

	@Resource
	private SeachItemMapper seachItemMapper;
	@Resource
	private SolrServer solrServer;
	@Resource
	private SeachSolrDaoUtils seachSolrDaoUtils;
	
	@Override
	public E3Result importAllSeachIntoSolr() throws Exception{
		//查询商品列表
		List<SeachItem> seachItemList = seachItemMapper.getSeachItemList();
		//插入到solr中
		for (SeachItem seachItem : seachItemList) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", seachItem.getId());
			document.addField("item_title", seachItem.getTitle());
			document.addField("item_sell_point", seachItem.getSellPoint());
			document.addField("item_price", seachItem.getPrice());
			document.addField("item_image", seachItem.getImage());
			document.addField("item_category_name", seachItem.getCategoryName());
			solrServer.add(document);
		}
		solrServer.commit();
		return E3Result.ok();
	}
	
	@Override
	public SeachResult seachFromSolrBykeyword(String keyword, int page, int rows) throws Exception{
		SolrQuery query = new SolrQuery();
		query.setQuery(keyword);
		query.setStart(((page - 1) * rows));
		query.setRows((Integer) rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		SeachResult seachResult = seachSolrDaoUtils.seachFormSolr(query);
		//计算总页数
		long recordCount = seachResult.getRecourdCount();
		int totalPage = (int) (recordCount / rows);
		if (recordCount % rows > 0) 
			totalPage ++;
		//添加到返回结果
		Long lTotalPage = new Long(totalPage);
		seachResult.setTotalPages(lTotalPage);
		return seachResult;
	}

	@Override
	public void addDocument(Long id) {
		try {
			SeachItem searchItem = seachItemMapper.getSearchItemById(id);
			//写入到solr中
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSellPoint());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategoryName());
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

}
