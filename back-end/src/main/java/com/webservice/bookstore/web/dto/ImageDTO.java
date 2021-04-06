package com.webservice.bookstore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
	private String uuid;

	private String fileName;

	private String path;

	private Long boardId;

	public String getImageURL(){
		try{
			return URLEncoder.encode(path+"/"+uuid+"_"+ fileName,"UTF-8");
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return "";
	}
	public String getThumbnailURL(){
		try{
			return URLEncoder.encode(path+"/s_"+uuid+"_"+ fileName, "UTF-8");
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return "";
	}

}
