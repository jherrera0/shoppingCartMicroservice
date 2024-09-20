package bootcamp.shopping_cart_microservice.domain.until;

public class JwtConst {

    public static final String CART_REST_CONTROLLER_RUTE = "/shopping-cart";
    public static final String ADD_TO_CART_RUTE = "/add-to-cart";
    public static final String DELETE_FROM_CART_RUTE = "/delete-from-cart";
    public static final String BUY_CART_RUTE = "/buy-cart";

    public static final String HAS_AUTHORITY_CUSTOMER = "hasAuthority('CUSTOMER')";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER ="Bearer";
    public static final Integer SUB_STRING_INDEX = 7;
    public static final String USER_ID = "Id";
    public static final String ROLE = "Role";
    public static final String EMPTYSTRING = "";


    private JwtConst() {
    }
}
