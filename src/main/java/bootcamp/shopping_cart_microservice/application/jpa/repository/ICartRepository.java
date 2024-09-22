package bootcamp.shopping_cart_microservice.application.jpa.repository;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(Long userId);
}
