package com.webservice.bookstore.web.dto;


import com.webservice.bookstore.domain.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;

    private String title;

    private String content;

    private String memberUserid;//작성자 아이디

    private String replyCount; //게시글수

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
    
}
