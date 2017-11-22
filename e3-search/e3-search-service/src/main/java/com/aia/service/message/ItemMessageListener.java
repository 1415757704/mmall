package com.aia.service.message;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;

import com.aia.common.pojo.SeachItem;
import com.aia.service.SeachItemService;
import com.aia.service.mapper.SeachItemMapper;

/**
 * 在接受到消息的时候执行这个Listener，需要在xml文件中配置
 * @author Administrator
 *
 */
public class ItemMessageListener implements MessageListener{

	@Resource
	private SeachItemMapper seachItemMapper;
	
	@Resource
	private SolrServer solrServer;
	
	@Resource
	private SeachItemService seachItemService;
	
	@Override
	public void onMessage(Message message) {
		try {
			//获取id
			TextMessage textMessage = (TextMessage) message;
			String id = textMessage.getText();
			Long lId = Long.parseLong(id);
			seachItemService.addDocument(lId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
