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

    public CartItemEntity(CartEntity cart, Long productId, Long quantity, String productName, String brandName, Double price, List<String> categories) {
        this.cart = cart;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        this.categories = categories;
    }
}
