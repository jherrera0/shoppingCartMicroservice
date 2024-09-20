package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartEntity;
import bootcamp.shopping_cart_microservice.application.jpa.entity.CartItemEntity;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartItemEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.repository.ICartRepository;
import bootcamp.shopping_cart_microservice.domain.model.Cart;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import bootcamp.shopping_cart_microservice.domain.spi.ICartPersistencePort;
import bootcamp.shopping_cart_microservice.domain.until.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class CartJpaAdapter implements ICartPersistencePort {
    private final ICartRepository cartRepository;
    private final ICartEntityMapper cartEntityMapper;

    @Override
    @Transactional
    public void updateCart(Cart cart) {
            CartEntity cartEntity = cartEntityMapper.toEntity(cart);
            cartRepository.save(cartEntity);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        if (cartRepository.findByUserId(userId).isPresent()) {
            CartEntity cartEntity = cartRepository.findByUserId(userId).get();
            return cartEntityMapper.toDomain(cartEntity);
        }
        else {
            return null;
        }
    }

    @Override
    public void createCart(Long userId) {
        cartRepository.save(new CartEntity(userId));
    }

}


