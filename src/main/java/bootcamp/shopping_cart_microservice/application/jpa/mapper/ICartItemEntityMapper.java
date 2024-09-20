package bootcamp.shopping_cart_microservice.application.jpa.mapper;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartItemEntity;
import bootcamp.shopping_cart_microservice.domain.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ICartItemEntityMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "cart", target = "cart")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "brandName", target = "brandName")
    @Mapping(source = "categories", target = "categories")
    CartItemEntity toEntity(CartItem cartItem);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "cart", target = "cart")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "brandName", target = "brandName")
    @Mapping(source = "categories", target = "categories")
    CartItem toDomain(CartItemEntity cartItemEntity);

    List<CartItemEntity> toEntityList(List<CartItem> items);

    List<CartItem> toDomainList(List<CartItemEntity> items);
}
