package com.wangbo.test.lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

/**lucene索引查询工具类 
 * @author lenovo 
 * 
 */  
public class SearchUtil {  
    /**获取IndexSearcher对象 
     * @param indexPath 
     * @param service 
     * @return 
     * @throws IOException 
     */  
    public static IndexSearcher getIndexSearcherByParentPath(String parentPath,ExecutorService service) throws IOException{  
        MultiReader reader = null;  
        //设置  
        try {  
            File[] files = new File(parentPath).listFiles();  
            IndexReader[] readers = new IndexReader[files.length];  
            for (int i = 0 ; i < files.length ; i ++) {  
                readers[i] = DirectoryReader.open(FSDirectory.open(Paths.get(files[i].getPath(), new String[0])));  
            }  
            reader = new MultiReader(readers);  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return new IndexSearcher(reader,service);  
    }  
    /**多目录多线程查询 
     * @param parentPath 父级索引目录 
     * @param service 多线程查询 
     * @return 
     * @throws IOException 
     */  
    public static IndexSearcher getMultiSearcher(String parentPath,ExecutorService service) throws IOException{  
        File file = new File(parentPath);  
        File[] files = file.listFiles();  
        IndexReader[] readers = new IndexReader[files.length];  
        for (int i = 0 ; i < files.length ; i ++) {  
            readers[i] = DirectoryReader.open(FSDirectory.open(Paths.get(files[i].getPath(), new String[0])));  
        }  
        MultiReader multiReader = new MultiReader(readers);  
        IndexSearcher searcher = new IndexSearcher(multiReader,service);  
        return searcher;  
    }  
    /**根据索引路径获取IndexReader 
     * @param indexPath 
     * @return 
     * @throws IOException 
     */  
    public static DirectoryReader getIndexReader(String indexPath) throws IOException{  
        return DirectoryReader.open(FSDirectory.open(Paths.get(indexPath, new String[0])));  
    }  
    /**根据索引路径获取IndexSearcher 
     * @param indexPath 
     * @param service 
     * @return 
     * @throws IOException 
     */  
    public static IndexSearcher getIndexSearcherByIndexPath(String indexPath,ExecutorService service) throws IOException{  
        IndexReader reader = getIndexReader(indexPath);  
        return new IndexSearcher(reader,service);  
    }  
      
    /**如果索引目录会有变更用此方法获取新的IndexSearcher这种方式会占用较少的资源 
     * @param oldSearcher 
     * @param service 
     * @return 
     * @throws IOException 
     */  
    public static IndexSearcher getIndexSearcherOpenIfChanged(IndexSearcher oldSearcher,ExecutorService service) throws IOException{  
        DirectoryReader reader = (DirectoryReader) oldSearcher.getIndexReader();  
        DirectoryReader newReader = DirectoryReader.openIfChanged(reader);  
        return new IndexSearcher(newReader, service);  
    }  
      
    /**多条件查询类似于sql in 
     * @param querys 
     * @return 
     */  
    public static Query getMultiQueryLikeSqlIn(Query ... querys){  
    	BooleanQuery.Builder bQuery = new BooleanQuery.Builder(); 
        for (Query subQuery : querys) {  
        	bQuery.add(subQuery,Occur.SHOULD);  
        }  
        return bQuery.build(); 
    }  
      
    /**多条件查询类似于sql and 
     * @param querys 
     * @return 
     */  
    public static Query getMultiQueryLikeSqlAnd(Query ... querys){  
    	BooleanQuery.Builder bQuery = new BooleanQuery.Builder(); 
        for (Query subQuery : querys) {  
        	bQuery.add(subQuery,Occur.MUST);  
        }  
        return bQuery.build();  
    }  
    /**从指定配置项中查询 
     * @return 
     * @param analyzer 分词器 
     * @param field 字段 
     * @param fieldType 字段类型 
     * @param queryStr 查询条件 
     * @param range 是否区间查询 
     * @return 
     */  
    public static Query getQuery(String field,String fieldType,String queryStr,boolean range){  
        Query q = null;  
            if(queryStr != null && !"".equals(queryStr)){  
                if(range){  
                    String[] strs = queryStr.split("\\|");  
//                    if("int".equals(fieldType)){  
//                        int min = new Integer(strs[0]);  
//                        int max = new Integer(strs[1]);  
//                        q = NumericRangeQuery.newIntRange(field, min, max, true, true);  
//                    }else if("double".equals(fieldType)){  
//                        Double min = new Double(strs[0]);  
//                        Double max = new Double(strs[1]);  
//                        q = NumericRangeQuery.newDoubleRange(field, min, max, true, true);  
//                    }else if("float".equals(fieldType)){  
//                        Float min = new Float(strs[0]);  
//                        Float max = new Float(strs[1]);  
//                        q = NumericRangeQuery.newFloatRange(field, min, max, true, true);  
//                    }else if("long".equals(fieldType)){  
//                        Long min = new Long(strs[0]);  
//                        Long max = new Long(strs[1]);  
//                        q = NumericRangeQuery.newLongRange(field, min, max, true, true);  
//                    }  
//                }else{  
//                    if("int".equals(fieldType)){  
//                        q = NumericRangeQuery.newIntRange(field, new Integer(queryStr), new Integer(queryStr), true, true);  
//                    }else if("double".equals(fieldType)){  
//                        q = NumericRangeQuery.newDoubleRange(field, new Double(queryStr), new Double(queryStr), true, true);  
//                    }else if("float".equals(fieldType)){  
//                        q = NumericRangeQuery.newFloatRange(field, new Float(queryStr), new Float(queryStr), true, true);  
//                    }else{  
//                        Analyzer analyzer = new StandardAnalyzer();  
//                        q = new QueryParser(field, analyzer).parse(queryStr);  
//                    }  
                }  
            }else{  
                q= new MatchAllDocsQuery();  
            }  
            System.out.println(q);  
        return q;  
    }  
    /**根据field和值获取对应的内容 
     * @param fieldName 
     * @param fieldValue 
     * @return 
     */  
    public static Query getQuery(String fieldName,Object fieldValue){  
        Term term = new Term(fieldName, new BytesRef(fieldValue.toString()));  
        return new TermQuery(term);  
    }  
    /**根据IndexSearcher和docID获取默认的document 
     * @param searcher 
     * @param docID 
     * @return 
     * @throws IOException 
     */  
    public static Document getDefaultFullDocument(IndexSearcher searcher,int docID) throws IOException{  
        return searcher.doc(docID);  
    }  
    /**根据IndexSearcher和docID 
     * @param searcher 
     * @param docID 
     * @param listField 
     * @return 
     * @throws IOException 
     */  
    public static Document getDocumentByListField(IndexSearcher searcher,int docID,Set<String> listField) throws IOException{  
        return searcher.doc(docID, listField);  
    }  
      
    /**分页查询 
     * @param page 当前页数 
     * @param perPage 每页显示条数 
     * @param searcher searcher查询器 
     * @param query 查询条件 
     * @return 
     * @throws IOException 
     */  
    public static TopDocs getScoreDocsByPerPage(int page,int perPage,IndexSearcher searcher,Query query) throws IOException{  
        TopDocs result = null;  
        if(query == null){  
            System.out.println(" Query is null return null ");  
            return null;  
        }  
        ScoreDoc before = null;  
        if(page != 1){  
            TopDocs docsBefore = searcher.search(query, (page-1)*perPage);  
            ScoreDoc[] scoreDocs = docsBefore.scoreDocs;  
            if(scoreDocs.length > 0){  
                before = scoreDocs[scoreDocs.length - 1];  
            }  
        }  
        result = searcher.searchAfter(before, query, perPage);  
        return result;  
    }  
    public static TopDocs getScoreDocs(IndexSearcher searcher,Query query) throws IOException{  
        TopDocs docs = searcher.search(query, getMaxDocId(searcher));  
        return docs;  
    }  
    /**高亮显示字段 
     * @param searcher 
     * @param field 
     * @param keyword 
     * @param preTag 
     * @param postTag 
     * @param fragmentSize 
     * @return 
     * @throws IOException  
     * @throws InvalidTokenOffsetsException  
     */  
    public static String[] highlighter(IndexSearcher searcher,String field,String keyword,String preTag, String postTag,int fragmentSize) throws IOException, InvalidTokenOffsetsException{  
        Term term = new Term("content",new BytesRef("lucene"));  
        TermQuery termQuery = new TermQuery(term);  
        TopDocs docs = getScoreDocs(searcher, termQuery);  
        ScoreDoc[] hits = docs.scoreDocs;  
        QueryScorer scorer = new QueryScorer(termQuery);  
        SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(preTag,postTag);//设定高亮显示的格式<B>keyword</B>,此为默认的格式    
        Highlighter highlighter = new Highlighter(simpleHtmlFormatter,scorer);     
        highlighter.setTextFragmenter(new SimpleFragmenter(fragmentSize));//设置每次返回的字符数  
        Analyzer analyzer = new StandardAnalyzer();  
        String[] result = new String[hits.length];  
        for (int i = 0; i < result.length ; i++) {  
            Document doc = searcher.doc(hits[i].doc);  
            result[i] = highlighter.getBestFragment(analyzer, field, doc.get(field));  
        }  
        return result;  
    }  
    /**统计document的数量,此方法等同于matchAllDocsQuery查询 
     * @param searcher 
     * @return 
     */  
    public static int getMaxDocId(IndexSearcher searcher){  
        return searcher.getIndexReader().maxDoc();  
    }  
      
}
