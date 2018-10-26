/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */
package com.toceansoft.cas.config;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HTTPSTrustManager implements X509TrustManager {

	private static TrustManager[] trustManagers;
	private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

	@Override
	public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
			throws java.security.cert.CertificateException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s)
			throws java.security.cert.CertificateException {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return _AcceptedIssuers;
	}

	public static void allowAllSSL() {
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return true;
			}

		});

		SSLContext context = null;
		if (trustManagers == null) {
			trustManagers = new TrustManager[] { new HTTPSTrustManager() };
		}

		try {
			context = SSLContext.getInstance("TLS");
			context.init(null, trustManagers, new SecureRandom());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	}
}