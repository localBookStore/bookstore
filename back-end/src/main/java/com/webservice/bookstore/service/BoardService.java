package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void ShowCommunityList(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        result.stream().forEach(board -> {
            System.out.println(board);
        });

    }
}
