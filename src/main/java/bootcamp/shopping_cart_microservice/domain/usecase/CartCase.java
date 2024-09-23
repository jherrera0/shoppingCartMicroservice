package bootcamp.shopping_cart_microservice.domain.usecase;

import bootcamp.shopping_cart_microservice.domain.api.ICartServicePort;
import bootcamp.shopping_cart_microservice.domain.exception.ArticleNotFoundOnCart;
import bootcamp.shopping_cart_microservice.domain.exception.CategoriesLimitExceededException;
import bootcamp.shopping_cart_microservice.domain.exception.StockNotEnoughException;
import bootcamp.shopping_cart_microservice.domain.model.Cart;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import bootcamp.shopping_cart_microservice.domain.spi.*;
import bootcamp.shopping_cart_microservice.domain.until.Const;
import bootcamp.shopping_cart_microservice.domain.until.ExceptionConst;
import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import bootcamp.shopping_cart_microservice.domain.until.TokenHolder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartCase implements ICartServicePort {
    private final ICartPersistencePort cartPersistencePort;
    private final IJwtPersistencePort jwtPersistencePort;
    private final IFeignStockPersistencePort feignStockPersistencePort;
    private final IFeignSupplyPersistencePort feignSupplyPersistencePort;
    private final ICartItemPersistencePort cartItemPersistencePort;

    public CartCase(ICartPersistencePort cartPersistencePort, IJwtPersistencePort jwtPersistencePort, IFeignStockPersistencePort feignStockPersistencePort, IFeignSupplyPersistencePort feignSupplyPersistencePort, ICartItemPersistencePort cartItemPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
        this.jwtPersistencePort = jwtPersistencePort;
        this.feignStockPersistencePort = feignStockPersistencePort;
        this.feignSupplyPersistencePort = feignSupplyPersistencePort;
        this.cartItemPersistencePort = cartItemPersistencePort;
    }

    @Override
    public void addItem(Long productId, Long quantity) {
        CartItem itemOnStock = feignStockPersistencePort.findCartItemById(productId);
        String token = TokenHolder.getToken().substring(JwtConst.SUB_STRING_INDEX);
        Long userId = jwtPersistencePort.getUserId(token);
        checkCartExist(userId);
        Cart cart = cartPersistencePort.getCartByUserId(userId);
        cart.setItems(cartItemPersistencePort.getCartItemsOnCart(cart.getId()));
        CartItem cartItem = new CartItem(itemOnStock);
        cartItem.setQuantity(quantity);
        cartItem.setCart(cart);
        checkStock(itemOnStock,cartItem.getQuantity());
        if(!cart.getItems().isEmpty() && findInCart(cart, cartItem) != Const.MINUS_ONE) {
            int index = findInCart(cart, cartItem);
            cartItem = cart.getItems().get(index);
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            checkStock(itemOnStock,cartItem.getQuantity());
            cartItemPersistencePort.updateCartItem(cartItem);
        }
        checkQuantityPerCategory(cart.getItems(), cartItem);
        cartItemPersistencePort.addCartItem(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());
        cartPersistencePort.updateCart(cart);
    }

    @Override
    public void removeItem(Long productId) {
        String token = TokenHolder.getToken().substring(JwtConst.SUB_STRING_INDEX);
        Long userId = jwtPersistencePort.getUserId(token);
        Cart cart = cartPersistencePort.getCartByUserId(userId);
        if(!cartItemPersistencePort.findByProductIdAndCartId(cart.getId(), productId)) {
            throw new ArticleNotFoundOnCart(ExceptionConst.ARTICLE_NOT_FOUND_ON_CART);
        }
        cartItemPersistencePort.deleteCartItem(cart.getId(), productId);
        cart.setUpdatedAt(LocalDateTime.now());
        cartPersistencePort.updateCart(cart);
    }

    private String getNextSupplyDate(Long productId) {
        return feignSupplyPersistencePort.getNextSupplyDate(productId);
    }
    private int findInCart(Cart cart, CartItem cartItem) {
        for(CartItem item : cart.getItems()) {
            if(item.getProductId().equals(cartItem.getProductId())) {
               return cart.getItems().indexOf(item);
            }
        }
        return Const.MINUS_ONE;
    }

    private void checkCartExist(Long userId) {
        if(cartPersistencePort.getCartByUserId(userId) == null) {
            cartPersistencePort.createCart(userId);
        }
    }

    private void checkStock(CartItem itemOnStock,Long quantity) {
        if(itemOnStock.getQuantity()<quantity) {
            String nextSupplyDate = getNextSupplyDate(itemOnStock.getProductId());
            throw new StockNotEnoughException(ExceptionConst.STOCK_NOT_ENOUGH + Const.NEXT_SUPPLY_DATE + nextSupplyDate);
        }
    }

    private void checkQuantityPerCategory(List<CartItem> categoriesInCart, CartItem cartItem) {
          Map<String,Long> categories = new HashMap<>();
          validateTheCategoriesLimitInsideOfCart(categoriesInCart, categories);
          validateTheCategoriesLimitWithTheNewCartItem(cartItem, categories);

    }

    private static void validateTheCategoriesLimitWithTheNewCartItem(CartItem cartItem, Map<String, Long> categories) {
        for(String category : cartItem.getCategories()) {
            if(categories.containsKey(category) &&categories.get(category) + Const.ONE > Const.THREE) {
                throw new CategoriesLimitExceededException(ExceptionConst.CATEGORY_LIMIT_EXCEEDED + category);
            }
        }
    }

    private static void validateTheCategoriesLimitInsideOfCart(List<CartItem> categoriesInCart, Map<String, Long> categories) {
        for(CartItem item : categoriesInCart) {
            for(String category : item.getCategories()) {
                if(categories.containsKey(category)) {
                    categories.put(category, categories.get(category) + Const.ONE);
                } else {
                    categories.put(category, Const.ONE);
                }
            }
        }
    }
}
