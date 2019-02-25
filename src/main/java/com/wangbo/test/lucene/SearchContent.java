package com.wangbo.test.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchContent {
	public static final String INDEX_DIR = "C:\\ceshi\\lucene\\index";
	
	public static final Analyzer IK = new IKAnalyzer(true);
	
	public static final Analyzer SIMPLE = new SimpleAnalyzer();
	
	public static final String SEARCH_CONTENT = "无参";
	
	public static void main(String[] args) {
		try {
			// 1.创建索引目录对象，如果不存在该目录，则先创建该目录路径；
			FSDirectory directory = FSDirectory.open(new File(INDEX_DIR).toPath());
			// 2.根据指定索引目录对象和索引写入配置创建写入对象
//			IndexWriter writer = new IndexWriter(directory, (new IndexWriterConfig(IK).setOpenMode(OpenMode.CREATE)));
			/**
			 *  Any changes made to the index via {@link IndexWriter} will not be visible 
			 *  until a new {@code IndexReader} is opened.
			 *  
			 *  1.	@DirectoryReader.open(directory)			==>new StandardDirectoryReader()
			 *  2.	@DirectoryReader.open(writer)				==>new StandardDirectoryReader()
			 *  3.	@DirectoryReader.openIfChanged(oldReader)
			 *  	==> if index has no change return null;
			 *  		if changes,
			 *  		when oldReader has a writer			==>	StandardDirectoryReader.open(this, segmentInfos, applyAllDeletes, writeAllDeletes)
			 *  		when oldReader has not a writer 	==>	StandardDirectoryReader.open(directory, infos, getSequentialSubReaders())
			 */			
			DirectoryReader dirReader1 = DirectoryReader.open(directory);
			
//			DirectoryReader newReader = DirectoryReader.openIfChanged(dirReader1);
//			if (newReader != null) {
//				dirReader1.close();
//				dirReader1 = newReader;
//			}
//			DirectoryReader dirReader2 = DirectoryReader.open(writer);
//			System.out.println(dirReader2.equals(dirReader1));
			/**
			 *	CompositeReader get stored fields from the underlying LeafReaders,
			 *	but it is not possible to directly retrieve postings;Only LeafReader 
			 *	can get positions {@link #postings(Term, int) postings(term, PostingsEnum.FREQS)}
			 */
//			List<LeafReaderContext> leaves = directoryReader.leaves();
//			for (LeafReaderContext leave : leaves) {
//				LeafReader reader = leave.reader();
//			}
			IndexSearcher searcher = new IndexSearcher(dirReader1);
			
			QueryParser parser = new QueryParser("title", SIMPLE);
			Query query = parser.parse(SEARCH_CONTENT);
			TopDocs docs = searcher.search(query, 10);
			
//			TopDocs docs = searcher.search(new TermQuery(new Term("title", SEARCH_CONTENT)), 10);
			System.out.println(docs.totalHits);
			for (ScoreDoc doc : docs.scoreDocs) {
				System.out.println(doc.toString());
			}
			/**
			 * 	non-negative integers which each name a unique
			 *  document in the index.  These document numbers are ephemeral -- they may change 
			 *  as documents are added to and deleted from an index
			 */
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
