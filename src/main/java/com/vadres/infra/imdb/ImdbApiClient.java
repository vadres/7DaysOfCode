package com.vadres.infra.imdb;

import com.vadres.domain.interfaces.ApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImdbApiClient implements ApiClient {
	private static final String URL = "https://imdb-api.com/en/API/Top250Movies/%s";
	private final String key;

	public  ImdbApiClient (String key) {
		this.key = key;
	}

	@Override
	public String getBody() {
		try {
			URI uri = URI.create(String.format(URL, key));
			HttpClient client = HttpClient.newHttpClient();

			HttpRequest request = HttpRequest.newBuilder()
					.uri(uri)
					.GET()
					.build();

			return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException(e);
		}
	}
}
