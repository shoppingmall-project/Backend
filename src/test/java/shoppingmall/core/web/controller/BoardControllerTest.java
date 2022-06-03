package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {


    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    protected MockHttpSession session;

    @BeforeEach
    public void session() {
    }

    @AfterEach
    void cleanup() {
        memberRepository.deleteAll();
        boardRepository.deleteAll();
        session.clearAttributes();
    }

    @Test
    @DisplayName("게시글 추가")
    void crateBoard() throws Exception {
        //given
        String title = "test";
        String content = "test 내용내용";

        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        session = new MockHttpSession();

        session.setAttribute("memberId", member.getId());

        //when
        String body = mapper.writeValueAsString(BoardCreateRequestDto.builder()
                .title(title)
                .content(content)
                .build()
        );

        //then
        mvc.perform(post("/board")
                        .session(session)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertThat(boardRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteBoard() throws Exception {
        //given
        String title = "test";
        String content = "test 내용내용";

        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        session = new MockHttpSession();

        session.setAttribute("memberId", member.getId());

        Board board = boardRepository.save(Board.builder()
                .title(title)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/board/" + board.getId())
                        .session(session))
                .andExpect(status().isOk());

        //then
        assertThat(boardRepository.findAll()).isEmpty();
    }

    @Transactional
    @Test
    @DisplayName("게시글 수정")
    void updateBoard() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        session = new MockHttpSession();

        session.setAttribute("memberId", member.getId());

        String title = "test";
        String content = "test 내용내용";

        Board board = boardRepository.save(Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .build());


        //when
        String body = mapper.writeValueAsString(BoardUpdateRequestDto.builder()
                .title("change title")
                .content(content)
                .build()
        );

        mvc.perform(put("/board/" + board.getId())
                        .session(session)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when
        assertThat(boardRepository.findAll()).isNotEmpty();
        assertThat(board.getTitle()).isEqualTo("change title");

    }

    @Test
    @DisplayName("게시글 리스트 조회")
    void findAllBoard() throws Exception {

        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        session = new MockHttpSession();

        session.setAttribute("memberId", member.getId());
        boardRepository.save(Board.builder()
                .member(member)
                .title("test1")
                .content("내용")
                .build());

        boardRepository.save(Board.builder()
                .member(member)
                .title("test2")
                .content("내용2")
                .build());

        mvc.perform(get("/board")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }

    @Test
    @DisplayName("게시글조회")
    void findBoardById() throws Exception {

        Member member = memberRepository.save(Member.builder()
                .account("test")
                .password("1234")
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("Manager")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

        session = new MockHttpSession();

        session.setAttribute("memberId", member.getId());
        Board board = boardRepository.save(Board.builder()
                .member(member)
                .title("test1")
                .content("내용")
                .build());

        mvc.perform(get("/board/"+board.getId())
                        .session(session))
                .andExpect(status().isOk());

        Assertions.assertThat(boardRepository.findById(board.getId())).isNotEmpty();

    }
}
