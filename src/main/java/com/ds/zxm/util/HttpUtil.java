package com.ds.zxm.util;

import java.io.*;
import java.text.ParseException;
import java.util.*;

import com.ds.zxm.model.TCFFCPRIZE;
import com.ds.zxm.model.TcffcPrizeConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	public static String doPost(String url, Map<String, String> map, String charset, Map<String, String> requestHeader) {
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

			Iterator iterator2 = requestHeader.entrySet().iterator();
			while (iterator2.hasNext()) {
				java.util.Map.Entry<String, String> elem = (java.util.Map.Entry<String, String>) iterator2.next();
				httpPost.setHeader(elem.getKey(), elem.getValue());

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
			System.out.println( url + "doPost error");
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

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();//设置请求和传输超时时间
			httpGet.setConfig(requestConfig);

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
		} finally {
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

	static final String preTecentTimeStr = "腾讯时间：";
	static final String postTecentTimeStr = "；采集数据";
	static final String preOnlineStr = "online_resp({\"c\":";
	static final String postOnlineStr = ",\"ec\"";
	public static void main(String[] args) throws UnsupportedEncodingException, ParseException {
		/*Map<String, String> map = new HashMap<String, String>();
		map.put("caipiao", "chongqing");
		String result = HttpUtil.doPost("http://www.ds018.com/caipiao/kline/init", map,"utf-8");
		System.out.println(result);*/

		/*Map<String, String> map = new HashMap<String, String>();
		map.put("page_no", "2900");
		map.put("page_size", "3");
		String result = HttpUtil.doPost("http://tencent-online.com/get_result_list", map, "utf-8", DsUtil.genRequestHeaderMap2(""));*/

		List<TCFFCPRIZE> resultList = new ArrayList<>();
		List<TCFFCPRIZE> resultList2 = new ArrayList<>();

		Map<String, String> map = new HashMap<String, String>();
		map.put("logname", "2018-05-05");
		map.put("__RequestVerificationToken", "CfDJ8JNGqqJAhXVAj6SHbU5yR5Rc6Xc75fjwNXfsj3E_D0QaXYRGpf-z0RIn6XUFGa8JRWqM2HWJq_O_lEB-tTIWXmUcjRhK_D2_XdjiGEEonsuVo7FSRA3OwfkzsWp8Ko7qz1Uvd2-zr6bbvloqcAQ1sHY");
		String result = HttpUtil.doPost("http://www.77tj.org/tencent/download", map, "gb2312", DsUtil.gen77DownloadFileRequestHeaderMap(""));

		String[] itemsArray = result.split("\r\n");

		String curTime = "";
		String curOnline = "";
		int cnt = 0;
		for(String item :itemsArray) {
			if(item.indexOf("online_resp") > 0) {
				String time = item.substring(item.indexOf(preTecentTimeStr) + preTecentTimeStr.length() , item.indexOf(postTecentTimeStr)-3);
				String onlineNum = item.substring(item.indexOf(preOnlineStr)+ preOnlineStr.length(), item.indexOf(postOnlineStr));

				if(!time.equals(curTime)) {
					cnt++;
					if(!onlineNum.equals(curOnline)) {
						//下一期开奖数据已出
						cnt = 0;
						curTime = time;
						curOnline = onlineNum;
						TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
						tcffcprize.setLotteryCode("TCFFC");
						tcffcprize.setTime(DateUtils.String2Date(time,"yyyy/MM/dd HH:mm"));
						tcffcprize.setOnlineNum(Integer.valueOf(onlineNum));
						resultList.add(tcffcprize);
					} else {
						if (cnt > 30) {
							//下一期开奖数据已出且与上期同号
							cnt = 0;
							curTime = time;
							curOnline = onlineNum;

							TCFFCPRIZE tcffcprize = new TCFFCPRIZE();
							tcffcprize.setLotteryCode("TCFFC");
							tcffcprize.setTime(DateUtils.String2Date(time,"yyyy/MM/dd HH:mm"));
							tcffcprize.setOnlineNum(Integer.valueOf(onlineNum));
							resultList.add(tcffcprize);
						}
					}
				}

				System.out.println(time + " --- " + onlineNum);
			}
		}
		Collections.sort(resultList);

		for(int i = 1; i< resultList.size(); i++) {
			TCFFCPRIZE curPrize = resultList.get(i);
			TCFFCPRIZE lastPirze = resultList.get(i-1);
			int adjust = curPrize.getOnlineNum()-lastPirze.getOnlineNum();
			curPrize.setAdjustNum(adjust);
			resultList2.add(TcffcPrizeConverter.convert2TCFFCPrize(curPrize));
		}
		System.out.println(result);
	}
}
