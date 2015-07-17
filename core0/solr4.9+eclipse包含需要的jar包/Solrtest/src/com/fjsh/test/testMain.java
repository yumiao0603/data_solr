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
		System.out.println("��������ʱ�䣺 " + (endTime - startTime) + "ms");
		// queryAll();
		System.out.println("----------------------------------");
	}

	// ��������
	public static void addindex() {
		try {
			HttpSolrServer server = new HttpSolrServer(
					"http://localhost:8080/solr/core0");
			//��Ӻ��core0�ĵ�ַ
			server.setSoTimeout(10000); // socket read timeout
			server.setConnectionTimeout(10000);
			server.setDefaultMaxConnectionsPerHost(100000);
			server.setMaxTotalConnections(100000);
			server.setFollowRedirects(false); // defaults to false

			String filename = "D:/dbpedia/dbpedia-mini";
			//�����ļ��A�ĵ�ַ����Ō�Ҫ�����������ļ�
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

						document.addField("sub", s1);//���������sub헣�֮��s1������Ԫ�M�еĵ�һ�
						document.addField("pre", s2);//���������pre헣�֮��s2������Ԫ�M�еĵڶ��
						document.addField("obj", s3);//���������obj헣�֮��s3������Ԫ�M�еĵ����
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
					"sub:<http\\://dbpedia.org/resource/Zameni>");// ��ѯ ע�⣺ð̖ǰһ��Ҫ��\\
			params.setRows(30);//�@ʾ�����Y����ǰ30헣�ȥ��Ĭ�J��1
			SolrDocumentList docs = server.query(params).getResults();

			for (SolrDocument aa : docs) {
				System.out.println(aa);//����̨ݔ�������Y��
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
