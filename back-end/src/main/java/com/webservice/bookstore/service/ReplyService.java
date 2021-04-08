package com.webservice.bookstore.service;

import com.webservice.bookstore.domain.entity.board.Board;
import com.webservice.bookstore.domain.entity.board.BoardRepository;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.reply.Reply;
import com.webservice.bookstore.domain.entity.reply.ReplyRepository;
import com.webservice.bookstore.web.dto.BoardDTO;
import com.webservice.bookstore.web.dto.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.font.OpenType;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    //댓글등록
    public void registerReply(ReplyDTO replyDTO){
        if(replyDTO.getDepth()==0)
            replyDTO.setGroupOrder(replyRepository.getReplyOrder(replyDTO.getBoardId())+1);
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

    //댓글 정보들 보여주기
    public List<ReplyDTO> getBoardReplyList(Long boardId){
        List<Reply> list=replyRepository.getBoardReplyList(boardId);
        List<ReplyDTO> dtoList=list.stream().map(reply -> ReplyDTO.entityToDTO(reply)).collect(Collectors.toList());
        return dtoList;
    }


    @Modifying
    @Transactional
    public boolean changeReply(ReplyDTO replyDTO,String loginEmail){

        Reply reply = replyRepository.getOne(replyDTO.getId());
        if(loginEmail==null || loginEmail.length()==0)
            return false;
        if(!reply.getMemberEmail().equals(loginEmail))
            return false;

        reply.setContent(replyDTO.getContent());
        replyRepository.save(reply);
        return true;
    }

    @Transactional
    @Modifying
    public boolean deleteReply(ReplyDTO replyDTO,String loginEmail) {
        Reply reply = replyRepository.getOne(replyDTO.getId());
        if (loginEmail == null || loginEmail.length() == 0)
            return false;
        if (!reply.getMemberEmail().equals(loginEmail))
            return false;
        replyRepository.deleteById(replyDTO.getId());
        return true;
    }
}
