package com.rec.zadanie3;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class SendGET implements Callable<JSONObject> {
	private final String ip;

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "https://api.ip2country.info/ip?";

	public SendGET(String ip) {
		this.ip = ip;
	}

	public JSONObject call() throws Exception {
		URL url = new URL(GET_URL + ip);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			try (InputStream in = con.getInputStream()) {
				JSONObject json = new JSONObject(IOUtils.toString(in, "UTF-8"));
				json.put("ip", ip);
				System.out.println("Received json object: " + json);
				return json;
			}

		} else {
			System.out.println("GET request failed");
		}
		return null;
	}
}
