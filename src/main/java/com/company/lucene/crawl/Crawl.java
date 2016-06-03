/**
 * 
 */
package com.company.lucene.crawl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator, 2016年6月3日 上午9:59:36
 *
 */
public class Crawl {
	
	
	public static void main(String[] args) throws Exception {
		Crawl crawl = new Crawl();
		//给定URL，编码，返回网页源码
		String html = crawl.getHtml("http://www.qq.com","utf-8");
		System.out.println(html);
		
		//解析源码，并下载
//		parseHtml(html,"img","src");
	}

	/**
	 * @param html
	 * @param tag
	 * @param attr
	 * @return
	 * @throws Exception 
	 */
	private static Collection parseHtml(String html, String tag,
			String attr) throws Exception {
		//解析html，放到集合
		Document document = Jsoup.parse(html);
		
		Elements elements = document.getElementsByTag(tag);
		//遍历集合，拿出符合条件的URL
		for(Element element : elements){
			String imgUrl = element.attr("src");
			//download
//			downLoad("fileName","遍历出来的url");
			downLoad("filePath",imgUrl);
		}
		
		return null;
	}

	/**
	 * @param filePath
	 * @param imgUrl
	 * @throws Exception 
	 */
	private static void downLoad(String filePath, String imgUrl) throws Exception {
		//根据URL下载，并写入磁盘
		
		URL url = new URL(imgUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		//先读到缓存，再写入磁盘
		InputStream inputStream = connection.getInputStream();
		
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
		//创建文件保存
		File files = new File(filePath);
		if(!files.exists()){
			files.mkdirs();
		}
		
		//写入磁盘，需要创建文件
		File imgFile = new File(filePath + fileName);
		
		FileOutputStream outputStream = new FileOutputStream(imgFile);
		int temp = 0;
		while((temp = inputStream.read()) > 0){
			outputStream.write(temp);
		}
		
		inputStream.close();
		outputStream.close();
	}

	/**
	 * @param url
	 * @param encode
	 * @return
	 * @throws Exception 
	 */
	private String getHtml(String url, String encode) throws Exception {
		
		URL urlObj = new URL(url);
		
		URLConnection connection = urlObj.openConnection();
		//获取输入流，转换为字符流读取
		InputStream inputStream = connection.getInputStream();
		
		InputStreamReader reader = new InputStreamReader(inputStream, encode);
		
		//利用缓冲
		BufferedReader bufferedReader = new BufferedReader(reader);
		String temp = null;
		StringBuffer buffer = new StringBuffer();
		while((temp = bufferedReader.readLine())!=null) {
			buffer.append(temp + " \n");
		}
		//释放资源
		bufferedReader.close();
		reader.close();
		inputStream.close();
		
		return buffer.toString();
	}
}
