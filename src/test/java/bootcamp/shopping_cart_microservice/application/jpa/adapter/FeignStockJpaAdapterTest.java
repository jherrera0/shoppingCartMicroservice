package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.feign.IFeignStockClient;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FeignStockJpaAdapterTest {

    @Mock
    private IFeignStockClient feignStockClient;

    @InjectMocks
    private FeignStockJpaAdapter feignStockJpaAdapter;

    public FeignStockJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Find cart item by ID with valid ID")
    @Test
    void findCartItemByIdWithValidId() {
        Long id = 1L;
        CartItem expectedCartItem = new CartItem();
        when(feignStockClient.getArticle(id)).thenReturn(expectedCartItem);

        CartItem result = feignStockJpaAdapter.findCartItemById(id);

        assertEquals(expectedCartItem, result);
    }

    @DisplayName("Find cart item by ID with null ID")
    @Test
    void findCartItemByIdWithNullId() {
        Long id = null;

        CartItem result = feignStockJpaAdapter.findCartItemById(id);

        assertEquals(null, result);
    }

    @DisplayName("Find cart item by ID with non-existing ID")
    @Test
    void findCartItemByIdWithNonExistingId() {
        Long id = 999L;
        when(feignStockClient.getArticle(id)).thenReturn(null);

        CartItem result = feignStockJpaAdapter.findCartItemById(id);

        assertEquals(null, result);
    }
}