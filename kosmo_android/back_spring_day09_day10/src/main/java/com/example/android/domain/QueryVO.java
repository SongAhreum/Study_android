package com.example.android.domain;

public class QueryVO {
	private String key;
	private String word;
	private int page;
	private int size;
	private int start;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getStart() { 
		start = (page-1)*size;
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	@Override
	public String toString() {
		return "QueryVO [key=" + key + ", word=" + word + ", page=" + page + ", size=" + size + ", start=" + start
				+ "]";
	}
	
	
}
