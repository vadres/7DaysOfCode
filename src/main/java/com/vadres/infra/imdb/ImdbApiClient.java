package com.vadres.infra.imdb;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImdbApiClient {
	private static final String URL = "https://imdb-api.com/en/API/Top250Movies/%s";
	private final String key;

	public  ImdbApiClient (String key) {
		this.key = key;
	}

	public String getBody() throws IOException, InterruptedException, URISyntaxException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(String.format(URL, key)))
				.GET()
				.build();

		return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
	}
}
