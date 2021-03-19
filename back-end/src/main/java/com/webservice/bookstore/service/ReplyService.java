package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.board.BoardRepository;
import com.webservice.bookstore.domain.entity.reply.Reply;
import com.webservice.bookstore.domain.entity.reply.ReplyRepository;
import com.webservice.bookstore.web.dto.BoardDTO;
import com.webservice.bookstore.web.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    //댓글등록
    public void registerReply(ReplyDTO replyDTO){
        Board board = boardRepository.getOne(replyDTO.getBoardId());
        Reply reply =  ReplyDTO.toEntity(replyDTO,board);
        replyRepository.save(reply);
    }

    //댓글 정보들 보여주기
    public List<ReplyDTO> getBoardReplylist(BoardDTO boardDTO){
        List<Reply> list=replyRepository.getBoardReplyList(boardDTO.getId());
        List<ReplyDTO> dtoList=list.stream().map(reply -> ReplyDTO.entityToDTO(reply)).collect(Collectors.toList());
        return dtoList;
    }

    @Modifying
    @Transactional
    public void changeReply(ReplyDTO replyDTO){
        Reply reply = replyRepository.getOne(replyDTO.getId());

        reply.setContent(replyDTO.getContent());
        replyRepository.save(reply);
    }

}
