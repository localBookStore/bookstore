package com.webservice.bookstore.web.controller;

import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.service.BoardService;
import com.webservice.bookstore.web.dto.BoardDTO;
import com.webservice.bookstore.web.dto.PageRequestDTO;
import com.webservice.bookstore.web.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api", produces = MediaTypes.HAL_JSON_VALUE+";charset=utf-8")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public ResponseEntity<PageResultDTO<BoardDTO, Board>> showPageBoardList(PageRequestDTO pageRequestDTO){
        PageResultDTO<BoardDTO,Board> pageResultDTO = boardService.pageCommunityList(pageRequestDTO);

        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }
}
