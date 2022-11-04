package com.vadres.domain;

public record Movie(
		String titulo,
		String url,
		String nota,
		String ano) {

	@Override
	public String toString() {
		return """
   titulo=%s
   url=%s
   nota=%s
   ano=%s
    """.formatted(titulo, url, nota, ano);
	}
}
