package com.example.demo.Response;

import java.util.List;


public class TablesResponse {

	private String title;
	private List<String> colmuns;
	private List<?> data;

	public TablesResponse() {
		super();
	}

	public TablesResponse(String title, List<String> colmuns, List<?> data) {
		super();
		this.title = title;
		this.colmuns = colmuns;
		this.data = data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getColmuns() {
		return colmuns;
	}

	public void setColmuns(List<String> colmuns) {
		this.colmuns = colmuns;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseUsers [title=" + title + ", colmuns=" + colmuns + ", data=" + data + "]";
	}

}
