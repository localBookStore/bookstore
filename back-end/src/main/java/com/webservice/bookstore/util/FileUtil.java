package com.webservice.bookstore.util;

import com.webservice.bookstore.web.dto.ItemDto;
import com.webservice.bookstore.web.dto.MemberDto;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Component
public class FileUtil<T extends ItemDto.ItemAddDto, M extends MemberDto.Modify> {

    public void checkImageType(T itemDto, String contentType, BufferedImage bufferedImage) throws Exception {
        String path = checkImageFilePath(itemDto);
        String isbn = itemDto.getIsbn();
        if (contentType.contains("image/jpeg")) {
            itemDto.setUpload_image_name(isbn + ".jpg");
            ImageIO.write(bufferedImage, "jpg", new File(path + ".jpg"));
        } else if (contentType.contains("image/png")) {
            itemDto.setUpload_image_name(isbn + ".png");
            ImageIO.write(bufferedImage, "png", new File(path + ".png"));
        } else if (contentType.contains("image/gif")) {
            itemDto.setUpload_image_name(isbn + ".gif");
            ImageIO.write(bufferedImage, "gif", new File(path + ".gif"));
        }
    }

    public String checkImageFilePath(T itemDto) {
        String prefixPath = System.getProperty("user.dir");
        String path = null;
        String lastSubString = prefixPath.substring(prefixPath.lastIndexOf("/"));


        if(lastSubString.equals("/back-end")) {
            path = prefixPath + "/src/main/resources/static/" + itemDto.getIsbn();
        } else if (lastSubString.equals("/bookstore")) {
            path = prefixPath + "/back-end/src/main/resources/static/" + itemDto.getIsbn();
        }
        return path;
    }

    public void checkImageType(M memberDto, String contentType, BufferedImage bufferedImage) throws Exception {
        String path = checkImageFilePath(memberDto);
        if (contentType.contains("image/jpeg")) {
            ImageIO.write(bufferedImage, "jpg", new File(path + ".jpg"));
        } else if (contentType.contains("image/png")) {
            ImageIO.write(bufferedImage, "png", new File(path + ".png"));
        } else if (contentType.contains("image/gif")) {
            ImageIO.write(bufferedImage, "gif", new File(path + ".gif"));
        }
    }

    public String checkImageFilePath(M memberDto) {
        String prefixPath = System.getProperty("user.dir");
        String path = null;
        String lastSubString = prefixPath.substring(prefixPath.lastIndexOf("/"));


        if(lastSubString.equals("/back-end")) {
            path = prefixPath + "/src/main/resources/static/" + memberDto.getEmail();
        } else if (lastSubString.equals("/bookstore")) {
            path = prefixPath + "/back-end/src/main/resources/static/" + memberDto.getEmail();
        }
        return path;
    }



}
