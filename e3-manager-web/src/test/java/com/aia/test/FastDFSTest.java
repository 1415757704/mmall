package com.aia.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.aia.common.utils.FastDFSClient;


public class FastDFSTest {

	public void testFast() throws Exception{
		//http://192.168.25.133/group1/M00/00/00/wKgZhVnjDRaAep9DAAAeewAftOw065.png
		//创建配置文件，内容是Tracher服务器的地址
		//创建一个全局对象加载文件
		ClientGlobal.init("D:/eclipse javaee/eclipse/eclipse javaee workspace/e3-manager-web/src/main/resources/conf/client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//创建一个TrackerClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StroageServer，可以为null
		StorageServer storageServer = null;
		//创建一个StorageClient，参数需要是TrackerServer和StorageServer对象
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用storageClient上传文件
		String[] upload_file = storageClient.upload_file("D:/picture/1.png", "png", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	
	public void testFastDFSClient() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
//		FastDFSClient fastDFSClient = new FastDFSClient("D:/eclipse javaee/eclipse/eclipse javaee workspace/e3-manager-web/src/main/resources/conf/client.conf");
		String uploadFile = fastDFSClient.uploadFile("D:/picture/2.png");
		System.out.println(uploadFile);
	}
}
