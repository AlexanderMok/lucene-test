/**
 * 
 */
package com.company.lucene.search;

import org.junit.Test;

/**
 * @author Administrator, 2016年6月3日 下午5:20:23
 *
 */
public class SearchFilesTest {
	
	
	@Test
	public void testSearchFiles(){
		try {
			SearchFiles.search("E:\\lucene", "prominently");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
