package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartItemEntity;
import bootcamp.shopping_cart_microservice.application.jpa.mapper.ICartItemEntityMapper;
import bootcamp.shopping_cart_microservice.application.jpa.repository.ICartItemRepository;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import bootcamp.shopping_cart_microservice.domain.spi.ICartItemPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor

public class CartItemJpaAdapter implements ICartItemPersistencePort{
    private final ICartItemRepository cartItemJpaRepository;
    private final ICartItemEntityMapper cartItemEntityMapper;

    @Override
    @Transactional
    public void updateCartItem(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemEntityMapper.toEntity(cartItem);
        cartItemJpaRepository.updateCartItems(cartItemEntity.getQuantity(), cartItemEntity.getProductId(), cartItemEntity.getCart().getId());
    }

    @Override
    public void addCartItem(CartItem cartItem) {
        CartItemEntity cartItemEntity = cartItemEntityMapper.toEntity(cartItem);
        cartItemJpaRepository.save(cartItemEntity);
    }

    @Override
    public List<CartItem> getCartItemsOnCart(Long cartId) {
        return cartItemEntityMapper.toDomainList(cartItemJpaRepository.findCartItemEntityByCartId(cartId));
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartId, Long productId) {
        cartItemJpaRepository.deleteCartItem(cartId, productId);
    }

    @Override
    public boolean findByProductIdAndCartId(Long cartId, Long productId) {
        return cartItemJpaRepository.findItemEntityByProductIdAndCartId(cartId, productId).isPresent();
    }
}
