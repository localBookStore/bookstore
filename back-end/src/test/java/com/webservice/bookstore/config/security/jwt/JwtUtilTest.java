package com.webservice.bookstore.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.webservice.bookstore.domain.entity.member.Member;
import com.webservice.bookstore.domain.entity.member.MemberRepository;
import com.webservice.bookstore.domain.entity.member.MemberRole;
import com.webservice.bookstore.util.RedisUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class JwtUtilTest {

    @Autowired
    MockMvc mockMvck;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RedisUtil redisUtil;

    private Member member1;

    @BeforeEach
    void setUp() {
        this.memberRepository.deleteAll();
        this.redisUtil.deleteData(member1.getEmail());
        Member member = Member.builder()
                .name("커리")
               .email("email@email.com")
                .password(passwordEncoder.encode("1234"))
                .role(MemberRole.USER)
                .enabled(true)
                .build();
        member1 = this.memberRepository.save(member);
    }

//    @DisplayName("로그인 성공해서 토큰 생성")
//    @Test
//    public void test1() throws Exception {
//        //given
//
//        //when
//
//        //then
//    }

    @DisplayName("로그인 성공시 토큰 생")
    @Test
    public void getAccessToken() throws Exception {


        Member member = Member.builder()
                .email("email@email.com")
                .password("1234")
                .build();


        //.header(HttpHeaders.AUTHORIZATION, getBearerToken(true))
        ResultActions perform = this.mockMvck.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(member))
        );
        String responseBody = perform.andReturn().getResponse().getContentAsString();
        String token = perform.andReturn().getResponse().getHeader("Authorization");
        System.out.println(token);
    }


}