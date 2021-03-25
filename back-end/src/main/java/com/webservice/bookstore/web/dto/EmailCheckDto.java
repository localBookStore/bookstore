package com.webservice.bookstore.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckDto {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 잘못되었습니다.")
    private String Email;
}
