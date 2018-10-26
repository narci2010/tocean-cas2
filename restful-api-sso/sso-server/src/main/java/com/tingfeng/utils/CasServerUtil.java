package com.tingfeng.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.tingfeng.config.CasConfig;

/**
 * CAS - Server通信服务
 */
public class CasServerUtil {

	public static void main(String[] args) {
		try {
			// String tgt = getTGT("tingfeng", "tingfeng");
			// System.out.println("TGT：" + tgt);
			//
			// String service = "http://app1.com:8181/fire/users.html";
			// String st = getST(tgt, service);
			// System.out.println("ST：" + st);
			//
			// System.out.println(service + "?ticket=" + st);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取TGT
	 */
	public static String getTGT(String username, String password) {
		try {
			CookieStore httpCookieStore = new BasicCookieStore();
			// CloseableHttpClient client = createHttpClientWithNoSsl(httpCookieStore);

			// CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpClient client = CasServerUtil.createHttpClient();

			HttpPost httpPost = new HttpPost(CasConfig.GET_TOKEN_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);

			// System.out.println("\n 获取TGT，Header响应");
			// Header[] allHeaders = response.getAllHeaders();
			// for (int i = 0; i < allHeaders.length; i++) {
			// System.out.println("Key：" + allHeaders[i].getName() + "，Value：" +
			// allHeaders[i].getValue() + "，Elements:" +
			// Arrays.toString(allHeaders[i].getElements()));
			// }

			Header headerLocation = response.getFirstHeader("Location");
			String location = headerLocation == null ? null : headerLocation.getValue();

			System.out.println("Location：" + location);

			if (location != null) {
				return location.substring(location.lastIndexOf("/") + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return null;
	}

	/**
	 * 获取ST
	 */
	public static String getST(String TGT, String service) {
		try {

			// CookieStore httpCookieStore = new BasicCookieStore();
			// CloseableHttpClient client = createHttpClientWithNoSsl(httpCookieStore);

			// CloseableHttpClient client = HttpClients.createDefault();
			CloseableHttpClient client = CasServerUtil.createHttpClient();
			// service 需要encoder编码
			// service = URLEncoder.encode(service, "utf-8");

			HttpPost httpPost = new HttpPost(CasConfig.GET_TOKEN_URL + "/" + TGT);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("service", service));
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);

			// System.out.println("\n 获取ST，Header响应");
			// Header[] allHeaders = response.getAllHeaders();
			// for (int i = 0; i < allHeaders.length; i++) {
			// System.out.println("Key：" + allHeaders[i].getName() + "，Value：" +
			// allHeaders[i].getValue() + "，Elements:" +
			// Arrays.toString(allHeaders[i].getElements()));
			// }
			//
			//
			// List<Cookie> cookies = httpCookieStore.getCookies();
			// System.out.println("Cookie.size：" + cookies.size());
			// for (Cookie cookie : cookies) {
			// System.out.println("Cookie: " + new Gson().toJson(cookie));
			// }

			String st = readResponse(response);
			return st == null ? null : (st == "" ? null : st);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取 response body 内容为字符串
	 *
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private static String readResponse(HttpResponse response) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String result = new String();
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		return result;
	}

	/**
	 * 创建模拟客户端（针对 https 客户端禁用 SSL 验证）
	 *
	 * @param cookieStore
	 *            缓存的 Cookies 信息
	 * @return
	 * @throws Exception
	 */
	private static CloseableHttpClient createHttpClientWithNoSsl(CookieStore cookieStore) throws Exception {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				// don't check
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				// don't check
			}
		} };

		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(null, trustAllCerts, null);
		LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);
		return HttpClients.custom().setSSLSocketFactory(sslSocketFactory)
				.setDefaultCookieStore(cookieStore == null ? new BasicCookieStore() : cookieStore).build();
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	private static CloseableHttpClient createHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = CasServerUtil.createIgnoreVerifySSL();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		// 创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
		// CloseableHttpClient client = HttpClients.createDefault();
		return client;

	}

}
