package bootcamp.shopping_cart_microservice.domain.until;

public class ExceptionConst {
    public static final String MALFORM_JWT_EXCEPTION = "MalFormed Jwt";
    public static final String STOCK_NOT_ENOUGH = "Stock Not Enough";

    public static final int CODE_400 = 400;
    public static final int CODE_500 = 500;
    public static final int CODE_404 = 404;
    public static final String CATEGORY_LIMIT_EXCEEDED = "Category Limit Exceeded, more than 3 categories are not allowed in ";
    public static final String ARTICLE_NOT_FOUND_ON_CART = "Article not found on cart"; ;

    private ExceptionConst() {
    }
}
