package com.webservice.bookstore.service;

import com.querydsl.core.BooleanBuilder;
import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.board.BoardRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.web.dto.BoardDTO;
import com.webservice.bookstore.web.dto.PageRequestDTO;
import com.webservice.bookstore.web.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.IntStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    //게시판 보여주기
    public PageResultDTO<BoardDTO,Board> pageCommunityList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        Function<Board,BoardDTO> fn = (entity->BoardDTO.entityToDTO(entity));

        return new PageResultDTO<>(result,fn);

    }


    //게시판 등록
    public void boardRegister(BoardDTO boardDTO){
        Member member  = memberRepository.getOne(boardDTO.getMemberId());
        Board board = BoardDTO.toEntity(boardDTO,member);
        boardRepository.save(board);
    }

    //상세페이지 댓글은 아직 미완성
    public BoardDTO showBoardDetailPage(Long id){
        Board board = boardRepository.getOne(id);
        BoardDTO dto = BoardDTO.entityToDTO(board);
        return dto;
    }
}
