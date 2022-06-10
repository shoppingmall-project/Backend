package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.member.Member;
import shoppingmall.core.domain.member.MemberRepository;
import shoppingmall.core.web.dto.LoginRequestDto;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void session() {
    }

    @AfterEach
    void cleanup() {
        memberRepository.deleteAll();
        boardRepository.deleteAll();
    }

    private Member getMember(){
        return memberRepository.save(Member.builder()
                .account("test")
                .password(passwordEncoder.encode("1234"))
                .gender("M")
                .email("test@naver.com")
                .name("test")
                .role("M")
                .address("주소주소")
                .phoneNum("01025123123")
                .build());

    }
    private String getAccessToken() throws Exception {
        String username = "test";
        String password = "1234";

        String body = mapper.writeValueAsString(LoginRequestDto.builder()
                .account(username)
                .password(password)
                .build()
        );

        ResultActions perform = this.mvc.perform(post("/auth/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        System.out.println("responseBody = " + responseBody);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject)jsonParser.parse(responseBody);
        JSONObject jsonArr = (JSONObject) jsonObj.get("data");
        return (String) jsonArr.get("token");
    }

    @Test
    @DisplayName("게시글 추가")
    void crateBoard() throws Exception {
        //given
        String title = "test";
        String content = "test 내용내용";
        Member member = getMember();

        //when
        String body = mapper.writeValueAsString(BoardCreateRequestDto.builder()
                .title(title)
                .content(content)
                .build()
        );

        //then
        mvc.perform(post("/board")
                        .header("X-AUTH-TOKEN", getAccessToken())
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
        Member member = getMember();

        Board board = boardRepository.save(Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/board/" + board.getId())
                        .header("X-AUTH-TOKEN", getAccessToken()))
                .andExpect(status().isOk());

        //then
        assertThat(boardRepository.findAll()).isEmpty();
    }


    @Transactional
    @Test
    @DisplayName("게시글 수정")
    void updateBoard() throws Exception {
        //given
        Member member = getMember();

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
                        .header("X-AUTH-TOKEN", getAccessToken())
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
        //given
        Member member = getMember();

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

        //when
        mvc.perform(get("/board"))
                .andExpect(status().isOk())

        //then
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }

    @Test
    @DisplayName("게시글조회")
    void findBoardById() throws Exception {
        //given
        Member member = getMember();
        Board board = boardRepository.save(Board.builder()
                .member(member)
                .title("test1")
                .content("내용")
                .build());

        //when
        mvc.perform(get("/board/"+board.getId()))
                .andExpect(status().isOk());

        //then
        Assertions.assertThat(boardRepository.findById(board.getId())).isNotEmpty();

    }

    @Test
    @DisplayName("게시글 제목 검색")
    void findBoardByTitle() throws Exception {
        //given
        Member member = getMember();

        String title = "test1";

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

        //when
        mvc.perform(get("/board/title/" + title))
                .andExpect(status().isOk())

                //then
                .andExpect(jsonPath("$.data.length()", equalTo(1)));

    }

    @Test
    @DisplayName("게시글 작성자 검색")
    void findBoardByWriter() throws Exception {
        //given
        Member member = getMember();

        String writer = "test";

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

        //when
        mvc.perform(get("/board/writer/" + writer))
                .andExpect(status().isOk())

                //then
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }
}
