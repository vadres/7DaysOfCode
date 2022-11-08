package com.vadres.domain.models;

public interface Content extends Comparable<Content> {
	String title();
	String url();
	String rating();
	String year();
}
