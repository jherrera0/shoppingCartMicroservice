package bootcamp.shopping_cart_microservice.application.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private CartEntity cart;
    private Long productId;
    private Long quantity;
    private String productName;
    private String brandName;
    private Double price;
    @ElementCollection
    private List<String> categories;

    public CartItemEntity() {
    }

    public CartItemEntity(Long productId, Long quantity,CartEntity cart) {
        this.productId = productId;
        this.quantity = quantity;
        this.cart = cart;
    }
}
