package com.aia.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SeachResult implements Serializable{

	private Long recourdCount;
	private Long totalPages;
	private List<SeachItem> itemList;
	public Long getRecourdCount() {
		return recourdCount;
	}
	public void setRecourdCount(Long recourdCount) {
		this.recourdCount = recourdCount;
	}
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<SeachItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SeachItem> itemList) {
		this.itemList = itemList;
	}
	@Override
	public String toString() {
		return "SeachResult [recourdCount=" + recourdCount + ", totalPages=" + totalPages + ", itemList=" + itemList
				+ "]";
	}
	
	
}
