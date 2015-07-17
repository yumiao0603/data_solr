package com.fjsh.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.interfaces.DSAKey;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		addindex();
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
		// queryAll();
		System.out.println("----------------------------------");
	}

	// 增加索引
	public static void addindex() {
		try {
			HttpSolrServer server = new HttpSolrServer(
					"http://localhost:8080/solr/core0");
			//添加后的core0的地址
			server.setSoTimeout(10000); // socket read timeout
			server.setConnectionTimeout(10000);
			server.setDefaultMaxConnectionsPerHost(100000);
			server.setMaxTotalConnections(100000);
			server.setFollowRedirects(false); // defaults to false

			String filename = "D:/dbpedia/dbpedia-mini";
			//本地文件夾的地址，存放將要生成索引的文件
			File file = new File(filename);
			File[] tempList = file.listFiles();
			BufferedReader reader = null;
			ArrayList list = new ArrayList();

			String tempString = null;
			int j = -1;
			for (int aa = 0; aa < tempList.length; aa++) {
				if (tempList[aa].isFile()) {
					reader = new BufferedReader(new FileReader(new File(
							tempList[aa].toString())));
					while ((tempString = reader.readLine()) != null) {
						j++;
						String[] s = tempString.split("> <|> \"");
						String s1 = s[0] + ">";
						String s2 = "<" + s[1] + ">";
						String s3 = s[2];
						if (s3.contains("\""))
							s3 = "\"" + s3;
						else
							s3 = "<" + s3;

						System.out.println(j);
						SolrInputDocument document = new SolrInputDocument();

						document.addField("sub", s1);//索引中添加sub項，之為s1，即三元組中的第一項
						document.addField("pre", s2);//索引中添加pre項，之為s2，即三元組中的第二項
						document.addField("obj", s3);//索引中添加obj項，之為s3，即三元組中的第三項
						server.add(document);
					}
				}
			}

			server.commit();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void queryAll() {
		try {
			SolrServer server = new HttpSolrServer(
					"http://localhost:8080/solr/core0");
			SolrQuery params = new SolrQuery(
					"sub:<http\\://dbpedia.org/resource/Zameni>");// 查询 注意：冒號前一定要加\\
			params.setRows(30);//顯示搜索結果的前30項，去掉默認為1
			SolrDocumentList docs = server.query(params).getResults();

			for (SolrDocument aa : docs) {
				System.out.println(aa);//控制台輸出搜索結果
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
