package com.webservice.bookstore.domain.entity.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String userid;

    private String password;

    private String address;

    private String phone;

    private String email;

    private String role;

    private String enabled;

}
