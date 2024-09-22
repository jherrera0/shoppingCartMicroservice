package bootcamp.shopping_cart_microservice.application.jpa.mapper;

import bootcamp.shopping_cart_microservice.application.jpa.entity.CartEntity;
import bootcamp.shopping_cart_microservice.domain.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ICartEntityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "cartItemEntityList", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CartEntity toEntity(Cart cart);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    Cart toDomain(CartEntity cartEntity);
}
