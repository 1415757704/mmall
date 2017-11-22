package com.aia.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aia.common.pojo.EasyUITreeNode;
import com.aia.mapper.TbContentCategoryMapper;
import com.aia.pojo.TbContentCategory;
import com.aia.pojo.TbContentCategoryExample;
import com.aia.pojo.TbContentCategoryExample.Criteria;
import com.aia.portal.service.ContentCatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.utils.E3Result;
import net.sf.jsqlparser.statement.select.Select;

@Service
public class ContentCatServiceImpl implements ContentCatService{

	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	
	@Override
	public List getContentCatList(Long parentId) {
		// TODO Auto-generated method stub
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> catList = tbContentCategoryMapper.selectByExample(example);
		//转换成EasyUITreeNode的列表
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : catList) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			nodeList.add(node);
		}
		return nodeList;
	}

	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		if (parentId!=null && name!=null) {
			TbContentCategory tbContentCategory = new TbContentCategory();
			tbContentCategory.setCreated(new Date());
			tbContentCategory.setIsParent(false);
			tbContentCategory.setName(name);
			tbContentCategory.setParentId(parentId);
			//默认排序就是1
			tbContentCategory.setSortOrder(1);
			//1(正常),2(删除)
			tbContentCategory.setStatus(1);
			tbContentCategory.setUpdated(new Date());
			//添加节点
			tbContentCategoryMapper.insertSelective(tbContentCategory);
			//查询是否有父节点、有的话修改状态
			TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
			if (parent!=null) {
				parent.setIsParent(true);//不管之前是不是父节点都设置为true
				tbContentCategoryMapper.updateByPrimaryKey(parent);
			}else {
				tbContentCategory.setIsParent(true);//设置追加的节点为父节点
				tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory);
			}
		}
		return E3Result.ok();
	}

	public E3Result updateContentCategory(Long id, String name) {
		//根据id查询到对应的商品
		TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
		tbContentCategory.setName(name);
		tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCategory(Long id) {
		deleteNode(id);
		return E3Result.ok();
	}
	
	public void deleteNode(Long id) {
		//查询是否为父节点，是的话删除下面的所有节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(id);//查询是否parentId是当前节点的
		List<TbContentCategory> queryList = tbContentCategoryMapper.selectByExample(example);
		if (queryList != null && queryList.size() != 0) {
			for (TbContentCategory tbContentCategory : queryList) {
				deleteNode(tbContentCategory.getId());
			}
		}
		//删除当前节点，退出
		TbContentCategoryExample deleteExample = new TbContentCategoryExample();
		Criteria deleteCreateCriteria = deleteExample.createCriteria();
		deleteCreateCriteria.andIdEqualTo(id);
		tbContentCategoryMapper.deleteByExample(deleteExample);
	}

	@Override
	public List getContentCatList(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		List childNodes = selectChildNode(categoryId);
		PageInfo<TbContentCategory> pageInfo = new PageInfo<>(childNodes);
		return childNodes;
	}

	private List selectChildNode(Long categoryId) {
		// TODO Auto-generated method stub
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(categoryId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		return list;
	}

}
