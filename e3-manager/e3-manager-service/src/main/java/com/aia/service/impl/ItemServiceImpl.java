package com.aia.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.aia.common.pojo.EasyUIDataGridResult;
import com.aia.mapper.TbItemDescMapper;
import com.aia.mapper.TbItemMapper;
import com.aia.pojo.TbItem;
import com.aia.pojo.TbItemDesc;
import com.aia.pojo.TbItemExample;
import com.aia.pojo.TbItemExample.Criteria;
import com.aia.service.ItemService;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource
	private Destination topicDestination;
	
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	
	@Override
	public TbItem getItemById(Long itemId) {
				//根据主键查询
				//TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
				TbItemExample example = new TbItemExample();
				Criteria criteria = example.createCriteria();
				//设置查询条件
				criteria.andIdEqualTo(itemId);
				//执行查询
				List<TbItem> list = itemMapper.selectByExample(example);
				if (list != null && list.size() > 0) {
					return list.get(0);
				}
				return null;
	}

//	@Override
//	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
//		if (page == null) {
//			page = new Integer(1);
//		}
//		if (rows == null) {
//			rows = new Integer(10);
//		}
//		PageHelper.startPage(page, rows);
//		TbItemExample example = new TbItemExample();
//		List<TbItem> countByExample = itemMapper.selectByExample(example );
//		PageInfo<TbItem> pageInfo = new PageInfo<>(countByExample);
//		EasyUIDataGridResult result = new EasyUIDataGridResult();
//		result.setTotal(pageInfo.getTotal());
//		result.setRows(countByExample);
//		return result;
//	}
	
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		if (page == null) {
			page = new Integer(0);
		}
		if (rows == null) {
			rows = new Integer(30);
		}
		List<TbItem> list = itemMapper.selectListByPage((page-1)*rows,rows);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		int count = itemMapper.selectCount();
		result.setTotal(count);
		result.setRows(list);
		return result;
	}
	
	public E3Result saveItem(TbItem tbItem, String desc) {
		//生成商品id
		long id = IDUtils.genItemId();
		final String sId = id+"";
		tbItem.setId(id);
		//1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		//插入到表中
		int insert = itemMapper.insert(tbItem);
		//插入到商品描述表中
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setItemId(id);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		return tbItemDescMapper.selectByPrimaryKey(itemId);
	}
}
