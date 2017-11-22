package com.aia.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aia.pojo.Item;
import com.aia.pojo.TbItem;
import com.aia.pojo.TbItemDesc;
import com.aia.service.ItemService;

@Controller
public class ItemDetailController {

	@Resource
	private ItemService ItemService;
	
	@RequestMapping("/item/{itemId}")
	public String showItemDetail(@PathVariable Long itemId, Model model) {
		TbItem tbItem = ItemService.getItemById(itemId);
		Item item = new Item(tbItem);
		TbItemDesc tbItemDesc = ItemService.getItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", tbItemDesc);
		return "item";
	}
}
