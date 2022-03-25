package com.SeoulEmergency.core.domain.news;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	
	@Builder
	public NewsList(String newsTitle, String newsLink) {
		this.newsTitle = newsTitle;
		this.newsLink = newsLink;
	}
	
//	public void update(String newsTitle, String newsLink) {
//		
//	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
