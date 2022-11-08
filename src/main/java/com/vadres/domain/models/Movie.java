package com.vadres.domain.models;

public record Movie(
		String title,
		String url,
		String rating,
		String year) implements Content {

	@Override
	public int compareTo(Content movie) {
		return rating().compareTo(movie.rating());
	}
}
