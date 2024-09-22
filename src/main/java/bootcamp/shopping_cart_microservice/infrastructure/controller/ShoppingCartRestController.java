package bootcamp.shopping_cart_microservice.infrastructure.controller;

import bootcamp.shopping_cart_microservice.application.http.dto.request.AddArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.dto.request.DeleteArticleRequest;
import bootcamp.shopping_cart_microservice.application.http.handler.interfaces.ICartHandler;
import bootcamp.shopping_cart_microservice.domain.until.Const;
import bootcamp.shopping_cart_microservice.domain.until.JwtConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
@RequiredArgsConstructor
@Tag(name = Const.SHOPPING_CART_REST_CONTROLLER,description = Const.SHOPPING_CART_REST_DESCRIPTION)
public class ShoppingCartRestController {

    private final ICartHandler cartHandler;
    @Operation(summary = Const.SHOPPING_CART_ADD_TO_CART)
    @ApiResponses(value = {
            @ApiResponse(responseCode = Const.CODE_STATUS_201, description = Const.DESCRIPTION_STATUS_201_USER, content = @Content),
            @ApiResponse(responseCode = Const.CODE_STATUS_401, description = Const.DESCRIPTION_STATUS_401_USER, content = @Content),
            @ApiResponse(responseCode = Const.CODE_STATUS_403, description = Const.DESCRIPTION_STATUS_403_USER, content = @Content),
            @ApiResponse(responseCode = Const.CODE_STATUS_404, description = Const.DESCRIPTION_STATUS_404_USER, content = @Content),
            @ApiResponse(responseCode = Const.CODE_STATUS_400, description = Const.DESCRIPTION_STATUS_400_USER, content = @Content)
    })
    @PostMapping(JwtConst.ADD_TO_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public void addToCart(@RequestHeader(JwtConst.AUTHORIZATION) String token, @RequestBody @Valid AddArticleRequest request) {
        cartHandler.addItem(token, request);
    }

    @DeleteMapping(JwtConst.DELETE_FROM_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public void removeFromCart(@RequestHeader(JwtConst.AUTHORIZATION) String token,@RequestBody @Valid DeleteArticleRequest request) {
        cartHandler.removeItem(token,request.getProductId());
    }

    @PostMapping(JwtConst.BUY_CART_RUTE)
    @PreAuthorize(JwtConst.HAS_AUTHORITY_CUSTOMER)
    public ResponseEntity<String> buyCart() {
        return ResponseEntity.ok("Buy cart logic");
    }
}
