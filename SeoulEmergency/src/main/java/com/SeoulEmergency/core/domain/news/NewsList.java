package com.SeoulEmergency.core.domain.news;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection="news_list")
public class NewsList {
	@Id
	public String id;
	
	public String newsTitle;
	public String newsLink;
	public String newsContent;
	public String newsDate;
	
	@Builder
	public NewsList(String newsTitle, String newsLink, String newsContent) {
		this.newsTitle = newsTitle;
		this.newsLink = newsLink;
		this.newsContent = newsContent;
	}
	
	@Builder
	public NewsList(String newsTitle, String newsLink, String newsContent, String newsDate) {
		this.newsTitle = newsTitle;
		this.newsLink = newsLink;
		this.newsContent = newsContent;
		this.newsDate = newsDate;
	}
	
//	public void update(String newsTitle, String newsLink) {
//		
//	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
