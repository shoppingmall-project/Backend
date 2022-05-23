package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.board.Board;
import shoppingmall.core.domain.board.BoardRepository;
import shoppingmall.core.domain.comment.Comment;
import shoppingmall.core.domain.comment.CommentRepository;
import shoppingmall.core.web.dto.board.BoardCreateRequestDto;
import shoppingmall.core.web.dto.board.BoardUpdateRequestDto;
import shoppingmall.core.web.dto.comment.CommentCreateRequestDto;
import shoppingmall.core.web.dto.comment.CommentUpdateRequestDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    void cleanup() {
        boardRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 생성")
    void createComment() throws Exception {
        //given
        Board board = boardRepository.save(Board.builder()
                .title("board")
                .author("board_author")
                .content("게시글 내용")
                .views(4)
                .build());

        String author = "writer";
        String content = "asdasdasd";

        //when
        String body = mapper.writeValueAsString(CommentCreateRequestDto.builder()
                .author(author)
                .content(content)
                .build());

        mvc.perform(post("/board/" +board.getId()+ "/comment")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        //then
        assertThat(boardRepository.findAll()).isNotEmpty();
        assertThat(commentRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        //given
        Board board = boardRepository.save(Board.builder()
                .title("board")
                .author("board_author")
                .content("게시글 내용")
                .views(4)
                .build());

        String author = "writer";
        String content = "asdasdasd";

        Comment comment = commentRepository.save(Comment.builder()
                .board(board)
                .author(author)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/board/" +board.getId()+ "/comment/" +comment.getId())
                )
                .andExpect(status().isOk());

        //then
        assertThat(commentRepository.findAll()).isEmpty();
        assertThat(boardRepository.findAll()).isNotEmpty();
    }

    @Transactional
    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        //given
        Board board = boardRepository.save(Board.builder()
                .title("board")
                .author("board_author")
                .content("게시글 내용")
                .views(4)
                .build());


        Comment comment = commentRepository.save(Comment.builder()
                .board(board)
                .author("writer")
                .content("asdasdasd")
                .build());

        //when
        String body = mapper.writeValueAsString(CommentUpdateRequestDto.builder()
                .content("new_content")
                .build()
        );

        mvc.perform(put("/board/" + board.getId() +"/comment/" +comment.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        assertThat(commentRepository.findAll()).isNotEmpty();
        assertThat(comment.getContent()).isEqualTo("new_content");
    }


    @Test
    @DisplayName("게시글 리스트 조회")
    void findCommentList() throws Exception {
        //given
        Board board = boardRepository.save(Board.builder()
                .title("board")
                .author("board_author")
                .content("게시글 내용")
                .views(4)
                .build());

        Comment comment1 = commentRepository.save(Comment.builder()
                .board(board)
                .author("author")
                .content("content")
                .build());

        Comment comment2 = commentRepository.save(Comment.builder()
                .board(board)
                .author("author2")
                .content("content2")
                .build());

        mvc.perform(get("/board/" +board.getId() +"/comment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));
    }
    @Test
    @DisplayName("게시글 삭제시 댓글 삭제 여부")
    void deleteCommentWhenDeleteBoard() throws Exception {
        //given
        Board board = boardRepository.save(Board.builder()
                .title("board")
                .author("board_author")
                .content("게시글 내용")
                .views(4)
                .build());

        String author = "writer";
        String content = "asdasdasd";

        Comment comment = commentRepository.save(Comment.builder()
                .board(board)
                .author(author)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/board/" +board.getId())
                )
                .andExpect(status().isOk());

        //then
        assertThat(commentRepository.findAll()).isEmpty();
        assertThat(boardRepository.findAll()).isEmpty();

    }

}
