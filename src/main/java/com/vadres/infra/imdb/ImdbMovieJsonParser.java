package com.vadres.infra.imdb;

import com.vadres.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImdbMovieJsonParser {
	private static final String PATTERN_MOVIE = "[{]([^}]+)}";
	private static final String PATTERN_TITLE = "\"title\":\"([^\"]+)\"";
	private static final String PATTERN_ANO = "\"year\":\"([^\"]+)\"";
	private static final String PATTERN_URL = "\"image\":\"([^\"]+)\"";
	private static final String PATTERN_NOTA = "\"imDbRating\":\"([^\"]+)\"";

	public List<Movie> parse(String json) {
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

	private String parseAttribute(String json, String patternString) {
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(json);

		return matcher.find()? matcher.group(1): "";
	}
}
