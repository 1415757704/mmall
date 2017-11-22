package com.aia.portal.pojo;

public class PageContent {

	private Long index;
	private String url;
	private String title;
	private String pic;
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	@Override
	public String toString() {
		return "PageContent [index=" + index + ", url=" + url + ", title=" + title + ", pic=" + pic + "]";
	}
	
}
