package shoppingmall.core.domain.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.core.domain.Goods.Goods;
import shoppingmall.core.domain.member.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(length = 30)
    private String request;

    @Column(nullable = false)
    private int payment; //1: 신용카드 2:네이버페이 3:카카오페이 ...


    @Builder
    public Order(Long id, Member member, Goods goods, String request, int payment) {
        this.id = id;
        this.member = member;
        this.goods = goods;
        this.request = request;
        this.payment = payment;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void update(String request, int payment) {
        this.request = request;
        this.payment = payment;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
