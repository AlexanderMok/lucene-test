/**
 * 
 */
package com.company.lucene.search;

import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Administrator, 2016年6月3日 下午4:52:39
 *
 */
public class SearchFiles {
	
	public static void search(String indexDir,String queryParam) throws Exception {
		//打开索引目录，读取索引目录
		Directory directory = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(directory);
		
		//索引查询
		IndexSearcher searcher = new IndexSearcher(reader);
		
		//查询
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("content", analyzer);
		
		Query query = parser.parse(queryParam);
		
		long start = System.currentTimeMillis();
		//查10条记录
		TopDocs hits = searcher.search(query, 10);
		long end = System.currentTimeMillis();
		
		System.out.println("查询使用" + (end - start) + "毫秒,记录" + hits.totalHits+ "条");
		
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			//通过scoreDoc.doc获得docId
			Document doc = searcher.doc(scoreDoc.doc);
			
			System.out.println(doc.get("absolutePath"));
		}
		
		//关闭
		reader.close();
		directory.close();
	}
}
