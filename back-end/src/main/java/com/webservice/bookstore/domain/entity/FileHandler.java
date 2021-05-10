package com.webservice.bookstore.domain.entity;

import com.webservice.bookstore.domain.entity.item.ItemPicture;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {
        public List<ItemPicture> parseFile(Long itemId, List<MultipartFile> multipartFiles) throws Exception{
        List<ItemPicture> fileList = new ArrayList<>();
        String path = "D:/back-end/src/main/resources/static/images/";

        if(multipartFiles.isEmpty()) {
            return fileList;
        }


        for(MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                String contentType = multipartFile.getContentType();
                String originalFileExtenstion;

                if(StringUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if(contentType.contains("image/jpeg")){
                        originalFileExtenstion = ".jpg";
                    }
                    else if(contentType.contains("image/png")){
                        originalFileExtenstion = ".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExtenstion = ".gif";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else{
                        break;
                    }
                }

                String newFileName = Long.toString(System.nanoTime()) + originalFileExtenstion;

                ItemPicture itemPicture = ItemPicture.builder()
                                        .itemId(itemId)
                                        .originalFileName(multipartFile.getOriginalFilename())
                                        .storedFilePath(path + "/" + newFileName)
                                        .fileSize(multipartFile.getSize())
                                        .build()
                                        ;
                fileList.add(itemPicture);

                File file = new File(path + "/" + newFileName);
                multipartFile.transferTo(file);
            }


        }

        return fileList;
    }
}
