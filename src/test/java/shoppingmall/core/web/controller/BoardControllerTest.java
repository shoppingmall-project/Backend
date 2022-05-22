package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.Board;
import shoppingmall.core.domain.BoardRepository;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.service.goods.GoodsService;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsCreateRequestDto;
import shoppingmall.core.web.dto.goods.GoodsUpdateRequestDto;

import javax.transaction.Transactional;

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

    @AfterEach
    void cleanup() {
        boardRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 추가")
    void crateGoods() throws Exception {
        //given
        String title = "test";
        String author = "박민우";
        String content = "test 내용내용";
        Integer views = 50;


        //when
        String body = mapper.writeValueAsString(BoardCreateRequestDto.builder()
                .title(title)
                .author(author)
                .content(content)
                .views(views)
                .build()
        );

        //then
        mvc.perform(post("/board")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        assertThat(!boardRepository.findAll().isEmpty());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteGoods() throws Exception {
        //given
        String title = "test";
        String author = "박민우";
        String content = "test 내용내용";
        Integer views = 50;


        Board board = boardRepository.save(Board.builder()
                .title(title)
                .author(author)
                .content(content)
                .views(views)
                .build());

        //when
        mvc.perform(delete("/board/" + board.getId()))
                .andExpect(status().isOk());

        //then
        assertThat(boardRepository.findAll().isEmpty());
    }

    @Transactional
    @Test
    @DisplayName("상품 수정")
    void updateGoods() throws Exception {
        //given
        String title = "test";
        String author = "박민우";
        String content = "test 내용내용";
        Integer views = 50;


        Board board = boardRepository.save(Board.builder()
                .title(title)
                .author(author)
                .content(content)
                .views(views)
                .build());


        //when
        String body = mapper.writeValueAsString(BoardUpdateRequestDto.builder()
                .title("change title")
                .content(content)
                .build()
        );

        mvc.perform(put("/board/" + board.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when
        assertThat(!boardRepository.findAll().isEmpty());
        assertThat(board.getTitle()).isEqualTo("change title");

    }

    @Test
    @DisplayName("상품 리스트 조회")
    void findAllGoods() throws Exception {

        boardRepository.save(Board.builder()
                .title("test1")
                .author("박민우")
                .content("내용")
                .views(123)
                .build());

        boardRepository.save(Board.builder()
                .title("test2")
                .author("최영원")
                .content("내용2")
                .views(123)
                .build());

        mvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));

    }
}
