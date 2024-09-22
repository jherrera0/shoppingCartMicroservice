package bootcamp.shopping_cart_microservice.domain.until;

public class TokenHolder {
    private static final ThreadLocal<String> LOCAL_TOKEN = new ThreadLocal<>();

    private TokenHolder() {
    }

    public static void setToken(String token) {
        LOCAL_TOKEN.set(token);
    }

    public static String getToken() {
        return LOCAL_TOKEN.get();
    }

    public static void clear() {
        LOCAL_TOKEN.remove();
    }
}