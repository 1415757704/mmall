

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {

	@Test
	public void addDocument() throws Exception{
		//添加jar包
		//创建一个solrserver对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//添加域、必须有id
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		document.addField("item_price", "199");
		solrServer.add(document);
		//提交
		solrServer.commit();
		System.out.println("插入成功");
	}
	
	@Test
	public void getDocument() throws Exception{
		//创建服务对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
		//创建查询对象
		SolrQuery solrQuery = new SolrQuery();
		//添加过滤条件
		solrQuery.setQuery("*:*");
		//执行查询
		QueryResponse response = solrServer.query(solrQuery);
		//	取出查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		//打印
		System.out.println("查询结果是：---->>>>");
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
	
	@Test
	public void deleteDocument() throws Exception{
		//创建服务对象
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr");
		//根据id删除
		server.deleteById("test001");
		//根据条件删除
//		server.deleteByQuery("item_title:测试商品");
		//提交
		server.commit();
		System.out.println("删除成功");
	}
	
	//文本高亮
	@Test
	public void queryWithHighLine() throws Exception{
		SolrServer server = new HttpSolrServer("http://192.168.25.128:8080/solr");
		SolrQuery query = new SolrQuery();
		//指定默认搜索域
		query.set("df", "item_keywords");
		//开启高亮显示
		query.setHighlight(true);
		//高亮显示的域
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		// 第四步：执行查询。得到一个Response对象。
		QueryResponse response = server.query(query);
		// 第五步：取查询结果。
		SolrDocumentList solrDocumentList = response.getResults();
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
		// 第六步：遍历结果并打印。
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = null;
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			} else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			System.out.println(itemTitle);
			System.out.println(solrDocument.get("item_price"));
		}
	}
}
