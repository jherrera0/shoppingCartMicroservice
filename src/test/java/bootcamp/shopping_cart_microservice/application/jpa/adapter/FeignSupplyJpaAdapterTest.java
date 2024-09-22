package bootcamp.shopping_cart_microservice.application.jpa.adapter;

import bootcamp.shopping_cart_microservice.application.feign.IFeignSupplyClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FeignSupplyJpaAdapterTest {

    @Mock
    private IFeignSupplyClient feignSupplyClient;

    @InjectMocks
    private FeignSupplyJpaAdapter feignSupplyJpaAdapter;

    public FeignSupplyJpaAdapterTest() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Get next supply date with valid product ID")
    @Test
    void getNextSupplyDateWithValidProductId() {
        Long productId = 1L;
        String expectedDate = "2023-10-01";
        when(feignSupplyClient.getNextSupplyDate(productId)).thenReturn(expectedDate);

        String result = feignSupplyJpaAdapter.getNextSupplyDate(productId);

        assertEquals(expectedDate, result);
    }

    @DisplayName("Get next supply date with null product ID")
    @Test
    void getNextSupplyDateWithNullProductId() {
        Long productId = null;

        String result = feignSupplyJpaAdapter.getNextSupplyDate(productId);

        assertEquals(null, result);
    }

    @DisplayName("Get next supply date with non-existing product ID")
    @Test
    void getNextSupplyDateWithNonExistingProductId() {
        Long productId = 999L;
        when(feignSupplyClient.getNextSupplyDate(productId)).thenReturn(null);

        String result = feignSupplyJpaAdapter.getNextSupplyDate(productId);

        assertEquals(null, result);
    }
}