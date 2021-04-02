package com.webservice.bookstore.domain.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.enabled = false where m.email = :email")
    void withdraw(@Param("email") String email);

    Optional<Member> findByEmailAndNickName(String email, String nickName);

    List<Member> findAllByRoleNot(MemberRole role);

    @Query("select m.id from Member m")
    List<Long> findAllMemberId();
}
