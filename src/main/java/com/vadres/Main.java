package com.vadres;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class.getName());
	private static String url = "https://imdb-api.com/en/API/Top250Movies/%s";

	public static void main(String ...args) throws URISyntaxException, IOException, InterruptedException {
		Dotenv dotenv = Dotenv.configure().load();

		String key = dotenv.get("IMDB_KEY");

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(String.format(url, key)))
				.GET()
				.build();

		String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
		logger.info(response);
	}
}
