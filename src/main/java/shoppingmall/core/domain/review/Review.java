package shoppingmall.core.domain.review;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.BaseTimeEntity;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.member.Member;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(length = 200)
    private String imageUrl;

    @Builder
    public Review(Long id, Goods goods, Member member, String content, String imageUrl) {
        this.id = id;
        this.goods = goods;
        this.member = member;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void updateReview(String content) {
        this.content = content;
    }

    public void updateUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
