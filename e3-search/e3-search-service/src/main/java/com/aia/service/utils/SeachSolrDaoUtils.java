package com.aia.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import com.aia.common.pojo.SeachItem;
import com.aia.common.pojo.SeachResult;

@Repository
public class SeachSolrDaoUtils {

	@Resource
	private SolrServer solrServer;
	
	/**
	 * 根据条件对于solr进行查询、这里的查询总页数还未知，需要在上一层设置
	 * @param solrQuery
	 * @return
	 * @throws Exception
	 */
	public SeachResult seachFormSolr(SolrQuery solrQuery) throws Exception {
		SeachResult seachResult = new SeachResult();
		List<SeachItem> seachItemList = new ArrayList<>();
		QueryResponse query = solrServer.query(solrQuery);
		SolrDocumentList solrDocumentList = query.getResults();
		Long recourdCount = solrDocumentList.getNumFound();
		seachResult.setRecourdCount(recourdCount);
		//取高亮显示结果
		Map<String, Map<String, List<String>>> highlightMap = query.getHighlighting();
		for (SolrDocument solrDocument : solrDocumentList) {
			SeachItem seachItem = new SeachItem();
			seachItem.setId((String) solrDocument.get("id"));
			seachItem.setPrice((Long) solrDocument.get("item_price"));
			seachItem.setImage((String) solrDocument.get("item_image"));
			seachItem.setSellPoint((String) solrDocument.get("item_sell_point"));
			//title需要高亮显示
			//先通过id获取集合，在获取想要高亮的字段
			List<String> titleHightLineList = highlightMap.get(solrDocument.get("id")).get("item_title");
			String title;
			if (titleHightLineList!=null && titleHightLineList.size()!=0) {
				title = titleHightLineList.get(0);
			}else {//空的话还是取原来的结果中的数据，不取高亮部分
				title = (String) solrDocument.get("item_title");
			}
			seachItem.setTitle(title);
			seachItemList.add(seachItem);
		}
		seachResult.setItemList(seachItemList);
		return seachResult;
	}
}
