package bootcamp.shopping_cart_microservice.application.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItemEntity> cartItemEntityList;

    private LocalDate createdAt;
    private LocalDateTime updatedAt;

    public CartEntity() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDateTime.now();
    }

    public CartEntity(Long userId) {
        this.userId = userId;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDateTime.now();
    }
}
