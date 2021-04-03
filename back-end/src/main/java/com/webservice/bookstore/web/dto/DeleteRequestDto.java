package com.webservice.bookstore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteRequestDto {

    @Builder.Default
    List<Long> ids = new ArrayList<>();
}
