package com.company.lucene.index;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Administrator, 2016年6月3日 下午3:03:43
 *
 */
public class IndexFiles {
	//写索引实例
	private IndexWriter writer;
	
	/**
	 * 实例化IndexWriter
	 * @param indexDir
	 * @throws Exception
	 */
	public IndexFiles(String indexDir) throws Exception{
		System.out.println("Indexing to directory '" + indexDir + "'...");
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(dir, config);
	}
	
	/**
	 * 关闭写索引
	 * @throws Exception
	 */
	public void close() throws Exception {
		writer.close();
	}
	
	/**
	 * 将dataDir目录下的文件索引
	 * @param dataDir
	 * @throws Exception
	 * @return 索引的文件数量
	 */
	public int index(String dataDir) throws Exception {
		File[] files = new File(dataDir).listFiles();
		for (File file : files) {
			IndexFile(file);
		}
		
		return writer.numDocs();
	}

	/**
	 * 索引指定文件
	 * @param file
	 * @throws IOException 
	 */
	private void IndexFile(File file) throws IOException {
		System.out.println("indexing file: " + file.getCanonicalPath());
		Document document = getDocument(file);
		//为文档写入索引
		writer.addDocument(document);
	}

	/**
	 * 获取文档，文档里设置索引。相当于数据库一行及记录
	 * @param file
	 * @throws IOException 
	 */
	private Document getDocument(File file) throws IOException {
		Document doc = new Document();
		
		doc.add(new TextField("content",new FileReader(file)));
		//直接存到索引
		doc.add(new TextField("fileName", file.getName(), Field.Store.YES));
		
		doc.add(new TextField("absolutePath", file.getCanonicalPath(),Field.Store.YES));
		
		return doc;
	}
}