package com.webservice.bookstore.domain.entity.image;

import com.webservice.bookstore.domain.entity.board.Board;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Image {

    @Id
    @GeneratedValue()
    private Long id;

    private String uuid;

    private String fileName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
