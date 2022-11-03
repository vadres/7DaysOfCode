package com.vadres;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	private static String url = "https://imdb-api.com/en/API/Top250Movies/%s";
	private static final String PATTERN_TITLES = "\"title\":\"([^\"]+)\"";
	private static final String PATTERN_IMAGES = "\"image\":\"([^\"]+)\"";

	public static void main(String ...args) throws URISyntaxException, IOException, InterruptedException {
		Dotenv dotenv = Dotenv.configure().load();

		String key = dotenv.get("IMDB_KEY");

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(String.format(url, key)))
				.GET()
				.build();

		String json = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

		List<String> titles = parseJson(json, PATTERN_TITLES);
		List<String> urlImages = parseJson(json, PATTERN_IMAGES);

		logger.info(titles::toString);
		logger.info(urlImages::toString);
	}

	private static List<String> parseJson(String json, String patternString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(json);

		List<String> titles = new ArrayList<>();
		while(matcher.find()) {
			titles.add(matcher.group(1));
		}

		return titles;
	}
}
