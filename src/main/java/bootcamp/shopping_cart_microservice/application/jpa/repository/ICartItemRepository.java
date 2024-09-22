package bootcamp.shopping_cart_microservice.application.jpa.repository;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICartItemRepository extends JpaRepository<CartItemEntity, Long> {

    @Modifying
    @Query("update CartItemEntity c set c.quantity= :quantity where c.productId = :productId AND c.cart.id = :cartId")
    void updateCartItems(@Param("quantity") Long quantity, @Param("productId") Long productId, @Param("cartId") Long cartId);

    @Modifying
    @Query("select c from CartItemEntity c where c.cart.id = :cartId")
    List<CartItemEntity> findCartItemEntityByCartId(@Param("cartId") Long cartId);

    @Modifying
    @Query("select c from CartItemEntity c where c.productId = :productId AND c.cart.id = :cartId")
    Optional<CartItemEntity> findItemEntityByProductIdAndCartId(Long cartId, Long productId);

    @Modifying
    @Query("delete from CartItemEntity c where c.productId = :productId AND c.cart.id = :cartId")
    void deleteCartItem(Long cartId, Long productId);
}
