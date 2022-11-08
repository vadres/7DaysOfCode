package com.vadres;

import com.vadres.domain.interfaces.ApiClient;
import com.vadres.domain.interfaces.JsonParser;
import com.vadres.domain.models.Content;
import com.vadres.infra.imdb.ImdbApiClient;
import com.vadres.infra.imdb.ImdbMovieJsonParser;
import com.vadres.infra.web.HtmlGenerator;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

public class Main {

	public static void main(String ...args) throws IOException {
		Dotenv dotenv = Dotenv.configure().load();

		ApiClient imdbApiClient = new ImdbApiClient(dotenv.get("IMDB_KEY"));
		String json = imdbApiClient.getBody();

		JsonParser imdbParser = new ImdbMovieJsonParser();
		List<? extends Content> contents = imdbParser.parse(json);
		contents.sort(Comparator.comparing(Content::rating));

		PrintWriter writer = new PrintWriter("content.html");
		new HtmlGenerator(writer).generate(contents);
		writer.close();
	}

}
