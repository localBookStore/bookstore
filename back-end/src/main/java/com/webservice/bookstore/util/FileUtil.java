package com.webservice.bookstore.util;

import com.webservice.bookstore.web.dto.ItemDto;
import com.webservice.bookstore.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtil<T extends ItemDto.ItemAddDto, M extends MemberDto.Modify> {

    @Value("${check.envname}")
    public String env;
    private String prefixPath = System.getProperty("user.dir").replace("\\", "/");
    private String lastSubString = prefixPath.substring(prefixPath.lastIndexOf("/"));
    private String path = null;

    public void deleteImageFile(String uploadImageName, String path) throws IOException {
        if(env.equals("deploy")) {
            prefixPath = "/home/ubuntu/static/".replace("\\", "/");
        }

        File file = new File(path);
        if(file.exists()) {
            File[] files = file.listFiles();
            if(files.length != 0) {
                for (File f : files) {
                    if (f.getName().equals(uploadImageName)) {
                        f.delete();
                        System.out.println(f.getName() + " 삭제 성공");
                    }
                }
            }
        }

    }

    public String checkStaticFilePath() {
        if(env.equals("deploy")) {
            prefixPath = "/home/ubuntu/static/".replace("\\", "/");
            return prefixPath;
        }

        if(lastSubString.equals("/back-end")) {
            return prefixPath + "/src/main/resources/static/";
        } else if (lastSubString.equals("/bookstore")) {
            return prefixPath + "/back-end/src/main/resources/static/" ;
        } else {
            return null;
        }

    }

    public void checkImageType(T itemDto, String contentType, BufferedImage bufferedImage) throws Exception {

        path = checkStaticFilePath() + "item/" + itemDto.getIsbn();
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

    public void checkImageType(String fileName, String contentType, BufferedImage bufferedImage) throws Exception {
        path = checkStaticFilePath() +  "profile/"+ fileName;
        if (contentType.contains("image/jpeg")) {
            ImageIO.write(bufferedImage, "jpg", new File(path + ".jpg"));
        } else if (contentType.contains("image/png")) {
            ImageIO.write(bufferedImage, "png", new File(path + ".png"));
        } else if (contentType.contains("image/gif")) {
            ImageIO.write(bufferedImage, "gif", new File(path + ".gif"));
        }
    }

    public String makeProfileName(Long memberId) {
        int start = (int) (Math.random()*17); // 0 <= x <= 16
        String newFileName = UUID.randomUUID().toString().replace("-", "").substring(start, start + 16);
        StringBuffer sb = new StringBuffer(newFileName);
        sb.insert(start, memberId);
        return sb.toString();
    }

    /*
    회원 프로필 이미지 파일 조회 및 base64 인코딩
    */
    public String encodingImageFile(String fileName) {
        String extenstion = fileName.substring(fileName.indexOf(".") +1);
        if(extenstion.equals("jpg")) {
            extenstion = "jpeg";
        }

        byte[] binary = getFileBinary(checkStaticFilePath() + "profile/" + fileName);

        if(binary == null) {
            return null;
        }
        return "data:image/" + extenstion + ";base64," + Base64.getEncoder().encodeToString(binary);
    }

    private static byte[] getFileBinary(String filepath) {
        File file = new File(filepath);
        byte[] data = new byte[(int) file.length()];
        try (FileInputStream stream = new FileInputStream(file)) {
            stream.read(data, 0, data.length);
        } catch (Throwable e) {
            return null;
        }
        return data;
    }


}
