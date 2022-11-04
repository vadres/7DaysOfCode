package com.vadres;

import com.vadres.domain.Movie;
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
	private static final String PATTERN_MOVIE = "[{]([^}]+)}";
	private static final String PATTERN_TITLE = "\"title\":\"([^\"]+)\"";
	private static final String PATTERN_ANO = "\"year\":\"([^\"]+)\"";
	private static final String PATTERN_URL = "\"image\":\"([^\"]+)\"";
	private static final String PATTERN_NOTA = "\"imDbRating\":\"([^\"]+)\"";

	public static void main(String ...args) throws URISyntaxException, IOException, InterruptedException {
		String url = "https://imdb-api.com/en/API/Top250Movies/%s";
		Dotenv dotenv = Dotenv.configure().load();

		String key = dotenv.get("IMDB_KEY");

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(new URI(String.format(url, key)))
				.GET()
				.build();

		String json = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

		parseMovie(json).forEach(movie -> logger.info(movie::toString));
	}

	private static List<Movie> parseMovie(String json) {
		Pattern pattern = Pattern.compile(PATTERN_MOVIE);
		Matcher matcher = pattern.matcher(json);

		List<Movie> movies = new ArrayList<>();
		while(matcher.find()) {
			String movieJson = matcher.group(1);
			String titulo = parseAttribute(movieJson, PATTERN_TITLE);
			String nota = parseAttribute(movieJson, PATTERN_NOTA);
			String url = parseAttribute(movieJson, PATTERN_URL);
			String ano = parseAttribute(movieJson, PATTERN_ANO);

			movies.add(new Movie(titulo, url, nota, ano));
		}

		return movies;
	}

	private static String parseAttribute(String json, String patternString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(json);

		return matcher.find()? matcher.group(1): "";
	}
}
