package com.ds.zxm.util;

import java.io.*;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	public static String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				java.util.Map.Entry<String, String> elem = (java.util.Map.Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            链接地址
	 * @param charset
	 *            字符编码，若为null则默认utf-8
	 * @return
	 */
	public static String doGet(String url, String charset) {
		if (null == charset) {
			charset = "utf-8";
		}
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;

		try {
			httpClient = new SSLClient();
			httpGet = new HttpGet(url);

			HttpResponse response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
			//result = getResponseString(response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String getResponseString(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();//响应实体类
		StringBuilder result = new StringBuilder();//响应正文
		if (entity != null) {
			String charset = EntityUtils.getContentCharSet(entity);
			//String charset = getContentCharSet(entity);
			InputStream instream = entity.getContent();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					instream,"ISO8859-1"));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				result.append(temp+"\n");
			}
		}
		return result.toString();
	}

	public static String getResponseString2(HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();//响应实体类
		StringBuilder result = new StringBuilder();//响应正文
		if (entity != null) {
			InputStream instream = entity.getContent();
			byte[] bytes = new byte[4096];
			int size = 0;
			try {
				while ((size = instream.read(bytes)) > 0) {
					String str = new String(bytes, 0, size, "utf-8");
					result.append(str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result.toString();
	}
	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = dataStr.substring(start + 2, dataStr.length());
			} else {
				charStr = dataStr.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("caipiao", "chongqing");
		String result = HttpUtil.doPost("http://www.ds018.com/caipiao/kline/init", map,"utf-8");
		System.out.println(result);*/

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("p", "[\"backtoxcb8\",\"230679zxm\",3]");

		String result2 = HttpUtil.doPost("http://www.198good.com:88/loginconf.do", map2,"utf-8");


		System.out.println(result2);
	}
}
