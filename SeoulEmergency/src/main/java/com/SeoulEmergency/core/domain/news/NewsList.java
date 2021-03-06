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
	public String newsImage;
	
	@Builder
	public NewsList(String newsTitle, String newsLink, String newsContent) {
		this.newsTitle = newsTitle;
		this.newsLink = newsLink;
		this.newsContent = newsContent;
	}
	
	@Builder
	public NewsList(String newsTitle, String newsLink, String newsContent, String newsDate, String newsImage) {
		this.newsTitle = newsTitle;
		this.newsLink = newsLink;
		this.newsContent = newsContent;
		this.newsDate = newsDate;
		this.newsImage = newsImage;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
