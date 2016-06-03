package com.company.lucene.index;


import org.junit.Test;


/**
 * @author Administrator, 2016年6月3日 下午4:33:52
 *
 */
public class IndexFilesTest{
	
	@Test
	public void testIndexFiles(){
		String indexDir = "E:\\lucene";
		String dataDir = "E:\\lucene\\docs";
		IndexFiles indexFiles = null;
		int num = 0;
		long start = System.currentTimeMillis();
		try {
			
			indexFiles = new IndexFiles(indexDir);
			num = indexFiles.index(dataDir);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				indexFiles.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("indexed file: " + num + " took " + (end-start));
	}
}
