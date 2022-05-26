package shoppingmall.core.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.Goods.GoodsRepository;
import shoppingmall.core.domain.review.Review;
import shoppingmall.core.domain.review.ReviewRepository;
import shoppingmall.core.web.dto.review.ReviewUpdateRequestDto;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @AfterEach
    void cleanup() {
        goodsRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    @DisplayName("리뷰 생성")
    void createReview() throws Exception {
        //given
        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        String author = "writer";
        String content = "asdasdasd";

        //when
//        ## JSON 형식일 때 데이터 보내는 방법
//        String body = mapper.writeValueAsString(reviewCreateRequestDto.builder()
//                .author(author)
//                .content(content)
//                .build());
//
//        mvc.perform(post("/board/" +goods.getId()+ "/review")
//                        .content(body)
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk());

        mvc.perform(post("/goods/" + goods.getId() + "/review")
                        .param("author", author)
                        .param("content", content))
                .andExpect(status().isOk());

        //then
        assertThat(goodsRepository.findAll()).isNotEmpty();
        assertThat(reviewRepository.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteReview() throws Exception {
        //given
        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        String author = "writer";
        String content = "asdasdasd";

        Review review = reviewRepository.save(Review.builder()
                .goods(goods)
                .author(author)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/goods/" +goods.getId()+ "/review/" +review.getId())
                )
                .andExpect(status().isOk());

        //then
        assertThat(reviewRepository.findAll()).isEmpty();
        assertThat(goodsRepository.findAll()).isNotEmpty();
    }

    @Transactional
    @Test
    @DisplayName("리뷰 수정")
    void updateReview() throws Exception {
        //given
        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());


        Review review = reviewRepository.save(Review.builder()
                .goods(goods)
                .author("writer")
                .content("asdasdasd")
                .build());

        //when
        String body = mapper.writeValueAsString(ReviewUpdateRequestDto.builder()
                .content("new_content")
                .build()
        );

        mvc.perform(put("/goods/" + goods.getId() + "/review/" + review.getId())
                        .param("content", "new_content"))
                .andExpect(status().isOk());

        //then
        assertThat(reviewRepository.findAll()).isNotEmpty();
        assertThat(review.getContent()).isEqualTo("new_content");
    }

    @Test
    @DisplayName("리뷰 리스트 조회")
    void findReviewList() throws Exception {
        //given
        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Review review1 = reviewRepository.save(Review.builder()
                .goods(goods)
                .author("author")
                .content("content")
                .build());

        Review review2 = reviewRepository.save(Review.builder()
                .goods(goods)
                .author("author2")
                .content("content2")
                .build());

        mvc.perform(get("/goods/" +goods.getId() +"/review"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", equalTo(2)));
    }

    @Test
    @DisplayName("댓글 조회")
    void findReviewById() throws Exception {
        //given
        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        Review review = reviewRepository.save(Review.builder()
                .goods(goods)
                .author("author")
                .content("content")
                .build());

        mvc.perform(get("/goods/" + goods.getId() + "/review/" + review.getId()))
                .andExpect(status().isOk());

        Assertions.assertThat(reviewRepository.findById(review.getId())).isNotEmpty();
    }

    @Test
    @DisplayName("게시글 삭제시 댓글 삭제 여부")
    void deleteReviewWhenDeletegoods() throws Exception {
        //given
        Goods goods = goodsRepository.save(Goods.builder()
                .category("wine")
                .name("test_wine")
                .price(30000)
                .stock(234)
                .description("Test용")
                .brand("ASD")
                .country("Korea")
                .build());

        String author = "writer";
        String content = "asdasdasd";

        Review review = reviewRepository.save(Review.builder()
                .goods(goods)
                .author(author)
                .content(content)
                .build());

        //when
        mvc.perform(delete("/goods/" +goods.getId())
                )
                .andExpect(status().isOk());

        //then
        assertThat(reviewRepository.findAll()).isEmpty();
        assertThat(goodsRepository.findAll()).isEmpty();

    }

}
