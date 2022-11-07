package com.vadres.domain.interfaces;

import com.vadres.domain.models.Content;

import java.util.List;

public interface JsonParser {
	List<? extends Content> parse(String json);
}
