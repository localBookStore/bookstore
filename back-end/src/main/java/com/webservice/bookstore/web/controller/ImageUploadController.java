package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.web.dto.UploadResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/board/image")
@Log4j2
public class ImageUploadController {


    @Value("${bookstore.upload.path}")//application.properties의 변수 즉 img가 upload경로
    private String uploadPath;

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("//", File.separator);

        //make folder ------
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(uploadPathFolder.exists() == false){
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }


    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();
        for(MultipartFile uploadFile : uploadFiles) {
            //이미지 파일만업로드가능
            if (uploadFile.getContentType().startsWith("image") == false) {
                log.warn("이미지 타입이 아님");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        for(MultipartFile uploadFile : uploadFiles){
            //실제 파일 이름 IE나 Edge는 전체경로가들어옴
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);

            log.info("FileName: "+ fileName);

            //날짜폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할파일이름중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator+folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);
            try{
                uploadFile.transferTo(savePath);//원본 파일 저장

                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator
                        +"s_" + uuid + "_" + fileName;
                //섬네일 파일 이름은 중간에 s_로 시작하도록
                File thumbnailFile = new File(thumbnailSaveName);

                //섬네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,200,200);

                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            } catch (IOException e)	{
                e.printStackTrace();
            }
        }//end for

        return new ResponseEntity<>(resultDTOList,HttpStatus.OK);//여기서 html로 resultDTOList를 보냄
    }

    @GetMapping("/display/{fileName}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName){
        ResponseEntity<byte[]> result = null;
        try{
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("file: "+file);

            HttpHeaders header = new HttpHeaders();

            //MIME타입처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result; //업로드된파일 데이터 html로 result 보냄
    }

    @PostMapping("/removeimage/{fileName}")
    public ResponseEntity<Boolean> removeFile(@PathVariable String fileName){
        String srcFileName = null;
        try{
            srcFileName = URLDecoder.decode(fileName, "UTF-8");
            File file = new File(uploadPath + File.separator +srcFileName);
            boolean result = file.delete();

            File thumbnail =new File(file.getParent(), "s_" + file.getName());
            result = thumbnail.delete();
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
