package com.webservice.bookstore.domain.entity.reply;

import com.webservice.bookstore.domain.entity.BaseTimeEntity;
import com.webservice.bookstore.domain.entity.board.Board;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; //댓글 내용

    private Long memberId; //작성자 키값

    private String memberEmail; //작성자 아이디

    private String memberNickName;

    @Column(nullable = false,columnDefinition = "int(11) default 0")
    private int depth; //깊이

    @Column(nullable = false)
    private int groupOrder; //댓글그룹 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false,columnDefinition = "bigint(20) default 0")
    private long parent; //부모

    public void setContent(String content) {
        this.content = content;
    }
}
