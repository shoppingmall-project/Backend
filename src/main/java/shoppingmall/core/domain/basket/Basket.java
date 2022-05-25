package shoppingmall.core.domain.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.member.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(nullable = false)
    private int count;

    @Builder
    public Basket(Long id, Member member, Goods goods, int count) {
        this.id = id;
        this.member = member;
        this.goods = goods;
        this.count = count;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void update(int count) {
        this.count = count;
    }
}
