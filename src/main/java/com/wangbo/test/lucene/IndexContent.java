package com.wangbo.test.lucene;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexContent {
	public static final String INDEX_DIR = "search_index";
	public static final String TITLE = "默认的无参构造创建的FieldType";
	public static final String CONTENT = "﻿<!DOCTYPE html><html><body><div><img src=\"/s//introDetail/item24/1517204576933.jpg\"></div></body>";
	
	public static void main(String[] args) {
		try {
			// 1.创建索引目录对象，如果不存在该目录，则先创建该目录路径；
			URI path = ClassLoader.getSystemResource("").toURI();
			/**
			 * 	JAVA路径中空格问题:URL对于空格还有一些特殊字符进行了编码处理,获取的路径会被处理，其中的空格使用“％20"代替
			 * 	解决办法如下：
			 * 	String Path=this.getClass().getResource("/").toString().replaceAll("%20"," ");
			 *  或者：
			 *  使用URLDecoder.decode(str,"UTF-8")解码，但是只能解决一部分，若路径中含有+，也是不能解决的;
			 *  解决所有问题：
			 *  TestURL().class.getResource("").toURI().getPath()
			 */
//			System.out.println(new File(new File(path).getParentFile().getParentFile(),INDEX_DIR));
			FSDirectory directory = FSDirectory.open(new File(new File(path).getParentFile().getParentFile(),INDEX_DIR).toPath());
			/**
			 * 词典初始化：词典单子实例	private static Dictionary singleton;
			 * 
			 * 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
			 * 只有当Dictionary类被实际调用时，才会开始载入词典，
			 * 这将延长首次分词操作的时间
			 * 该方法提供了一个在应用加载阶段就初始化字典的手段
			 * 
			 * (1)	加载主词典及扩展词典		org/wltea/analyzer/dic/main2012.dic;	ext_dict
			 * (2)	加载用户扩展的停止词词典	ext_stopwords
			 * (3)	加载量词词典			org/wltea/analyzer/dic/quantifier.dic
			 */
			Dictionary.initial(DefaultConfig.getInstance());
			/**	
			 * 	ik分词器IKAnalyzer的功能实际上是由TokenStreamComponents来完成的，初次分词操作时会创建一个分词流组件并且与当前线程
			 * 	通过Analyzer中的CloseableThreadLocal<Object> storedValue进行绑定
			 * 
			 * 	TokenStreamComponents components = reuseStrategy.getReusableComponents(this, fieldName);
			 * 
			 * 	if (components == null) {
			 * 		components = createComponents(fieldName);
			 * 		reuseStrategy.setReusableComponents(this, fieldName, components);
			 * 	}
			 */
			// 2.根据指定索引目录对象和索引写入配置创建写入对象
			IndexWriterConfig writerConfig = new IndexWriterConfig(new IKAnalyzer(true));
			// 设置索引写入对象的创建模式为每次覆写
			writerConfig.setOpenMode(OpenMode.CREATE);
			
			IndexWriter writer = new IndexWriter(directory, writerConfig);
//			
			
			/**
			 * 默认的无参构造创建的FieldType，表示的域field类型为：
			 * 	1.不存储内容
			 * 	2.分词
			 * 	3.不创建索引
			 * 	4.DocValuesType
			 *  5.omitNorms为ture时忽视规范，允许omit norms associated with indexed fields
			 * 	6.frozen 是否冻结域类型对象？如果为true，不允许再修改fieldType的字段值
			 */
			/**
			 * 
			 */
			for (int i = 1; i < 11; i++) {
				Document document = new Document();
				document.add(new StringField("detailId", String.valueOf(i),Field.Store.YES));
				document.add(new TextField("title", "中华人民共和国万岁", Field.Store.YES));
				document.add(new TextField("content", CONTENT, Field.Store.NO));
				
				writer.addDocument(document);
			}
			writer.commit();
			
			for (int i = 11; i < 21; i++) {
				Document document = new Document();
				document.add(new StringField("detailId", String.valueOf(i),Field.Store.YES));
				document.add(new TextField("title", "中华人民共和国万岁", Field.Store.YES));
				document.add(new TextField("content", CONTENT, Field.Store.NO));
				
				writer.addDocument(document);
			}
			writer.commit();
			
			for (int i = 1; i < 21; i++) {
				System.out.println(writer.deleteDocuments(new TermQuery(new Term("detailId", String.valueOf(i)))));
			}
			writer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
