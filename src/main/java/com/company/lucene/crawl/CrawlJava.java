package com.company.lucene.crawl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Administrator, 2016年6月2日 下午9:56:23
 *
 */
public class CrawlJava {
	
	
	//检索源代码，获取相关信息
	
	//存到数据库，分析
	/**
	 * 根据URL和编码取得网页源代码
	 * @param url 网址 
	 * @param encoding 网址编码
	 * @throws Exception
	 * @return source code
	 */
	public String httpGetHtmlContent(String url, String encoding) throws Exception{
		
		URL urlObj = new URL(url);
		
		URLConnection urlConnection = urlObj.openConnection();
		//字节流
		InputStream inputStream = urlConnection.getInputStream();
		
		//一行一行地读，要用字符流
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encoding);
		
		//缓冲流
		BufferedReader reader = new BufferedReader(inputStreamReader);
		
		StringBuilder stringBuilder = new StringBuilder();
		String temp = null;
		while((temp = reader.readLine()) != null) {
			stringBuilder.append(temp + "\n");
		}
		
		//释放资源
		reader.close();
		inputStreamReader.close();
		inputStream.close();
		
		
		return stringBuilder.toString();
	}
	
	public void downImg (String filePath,String url) throws IOException {
		
		String fileName = url.substring(url.lastIndexOf("/"));
		
		File files = new File(filePath);
		
		if(!files.exists()) {
			files.mkdirs();
		}
		
		//获取图片地址
		URL urlobj = new URL(url);
		
		HttpsURLConnection connection = (HttpsURLConnection) urlobj.openConnection();
		
		//获取连接的输入流
		InputStream inputStream = connection.getInputStream();
		
		//创建图片文件
		File imageFile = new File(filePath + fileName);
		
		//获取输出流，写入磁盘
		FileOutputStream outputStream = new FileOutputStream(imageFile);
		
		//一边读，一边写
		int temp = 0;
		while ((temp = inputStream.read()) > 0) {
			outputStream.write(temp);
		}
		
		//释放资源
		inputStream.close();
		outputStream.close();
	}
	
	
	public Collection parseUrl(String html, String tag,String attr){
		
		Document document = Jsoup.parse(html);
		
		Elements elements = document.getElementsByTag(tag);
		Collection list = new ArrayList();
		for(Element element : elements) {
			String imgSrc = element.attr(attr);
			list.add(imgSrc);
		}
		
		return list;
	}
	public static void main(String[] args) throws Exception {
		CrawlJava crawl = new CrawlJava();
		String html = crawl.httpGetHtmlContent("http://rhwayfun.com/post/jvm-java-memory/", "utf-8");
		System.out.println(html);
		
		Collection collection = crawl.parseUrl(html,"img","src");
		Iterator itr = collection.iterator();
		
		while(itr.hasNext()){
			String url = (String) itr.next();
			if((!"".equals(url) && url.startsWith("http://"))){
				crawl.downImg("",url);
			}
		}
		
	}
}
