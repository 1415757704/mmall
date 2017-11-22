package com.aia.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aia.common.pojo.EasyUIDataGridResult;
import com.aia.pojo.TbItem;
import com.aia.service.ItemService;

import cn.e3mall.common.utils.E3Result;

@Controller
public class ItemController {

	@Resource
	private ItemService itemService;
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	private Destination topicDestination;
	
	private Logger logger = Logger.getLogger(ItemController.class);
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		logger.error("getItemById :["+item.toString()+"]");
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {//这里没有在url的后面使用/item/list/{page}，所以不用使用@PathVariable
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		logger.error("getItemById :["+itemList+"]");
		return itemList;
	}
	
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public E3Result saveItem(final TbItem tbItem,String desc){
		E3Result saveItem = itemService.saveItem(tbItem, desc);
		//在controller层调用主要是因为这样service的事务commit了。这样后面去查询数据库的时候才不会出现空指针异常
		//发送一个消息，携带对应增加的商品的id
				jmsTemplate.send(topicDestination,new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(tbItem.getId()+"");
					}
				});
		return saveItem;
	}
}
