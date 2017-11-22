package com.aia.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aia.common.utils.FastDFSClient;

import cn.e3mall.common.utils.JsonUtils;

@Controller
public class PictrueController {

	//这个需要在springmvc.xml文件中加载配合文件
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value = "/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String upload(MultipartFile uploadFile) {
		Map result = new HashMap<>();
		try {
			//创建FastDFS对象
			FastDFSClient client = new FastDFSClient("D:/eclipse javaee/eclipse/eclipse javaee workspace/e3-manager-web/src/main/resources/conf/client.conf");
			//获取文件的后缀名，不包含.
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//上传图片
			String url = client.uploadFile(uploadFile.getBytes(), extName);
			url = StringUtils.trimWhitespace(IMAGE_SERVER_URL) + StringUtils.trimWhitespace(url);
			//返回数据
			result.put("error", 0);
			result.put("url", url);
		} catch (Exception e) {
			// TODO: handle exception
			result.put("error", 1);
			result.put("message", "上传失败");
			e.printStackTrace();
		}
		return JsonUtils.objectToJson(result);
	}
}
