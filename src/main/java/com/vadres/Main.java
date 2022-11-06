package com.vadres;

import com.vadres.domain.Movie;
import com.vadres.infra.imdb.ImdbApiClient;
import com.vadres.infra.imdb.ImdbMovieJsonParser;
import com.vadres.infra.web.HtmlGenerator;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

	public static void main(String ...args) throws URISyntaxException, IOException, InterruptedException {
		Dotenv dotenv = Dotenv.configure().load();

		ImdbApiClient imdbApiClient = new ImdbApiClient(dotenv.get("IMDB_KEY"));
		String json = imdbApiClient.getBody();

		ImdbMovieJsonParser imdbParser = new ImdbMovieJsonParser();
		List<Movie> movies = imdbParser.parse(json);

		PrintWriter writer = new PrintWriter("content.html");
		new HtmlGenerator(writer).generate(movies);
		writer.close();
	}

}
