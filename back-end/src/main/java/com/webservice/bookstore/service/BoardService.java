package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.board.BoardRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.reply.ReplyRepository;
import com.webservice.bookstore.web.dto.BoardDTO;
import com.webservice.bookstore.web.dto.PageRequestDTO;
import com.webservice.bookstore.web.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    //게시판 보여주기
    public PageResultDTO<BoardDTO,Board> pageCommunityList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        Function<Board,BoardDTO> fn = (entity->BoardDTO.entityToDTO(entity));

        return new PageResultDTO<>(result,fn);

    }
    public List<BoardDTO> getMemberBoardList(Long memberId){
        List<Object[]> list = boardRepository.getMemberBoardList(memberId);
        List<BoardDTO> result = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Board board =(Board)list.get(i)[0];
            BoardDTO dto = BoardDTO.entityToDTO(board);
            result.add(dto);
        }
        return result;
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

    public boolean modifyBoard(BoardDTO boardDTO,String email){
        if(email==null||email.length()==0)
            return false;
        if(boardDTO.getMemberEmail()!=email)
            return false;
        boardRepository.modifyBoard(boardDTO.getContent(),
                boardDTO.getCategory(),
                boardDTO.getTitle(),
                boardDTO.getId()
        );
        return true;
    }

    @Transactional
    public boolean deleteBoard(BoardDTO boardDTO, String email){

        if(email==null||email.length()==0)//접속중이지 않거나
            return false;
        if(boardDTO.getMemberEmail()!=email) //작성자와 로그인한사람이 다르면면
           return false;
        replyRepository.deleteReplyByBoard(boardDTO.getId());
        boardRepository.deleteBoard(boardDTO.getId());
        return true;
    }


}
