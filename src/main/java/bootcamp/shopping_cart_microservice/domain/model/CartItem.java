package bootcamp.shopping_cart_microservice.domain.model;

import java.util.List;

public class CartItem {
    private Long id;
    private Long productId;
    private Long quantity;
    private String productName;
    private String brandName;
    private Double price;
    private List<String> categories;
    private Cart cart;

    public CartItem(Long id, Long productId, Long quantity, String productName, String brandName, Double price, List<String> categories) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.brandName = brandName;
        this.price = price;
        this.categories = categories;
    }

    public CartItem(CartItem cartItem) {
        this.id = cartItem.getId();
        this.productId = cartItem.getProductId();
        this.quantity = cartItem.getQuantity();
        this.productName = cartItem.getProductName();
        this.brandName = cartItem.getBrandName();
        this.price = cartItem.getPrice();
        this.categories = cartItem.getCategories();
        this.cart = cartItem.getCart();
    }

    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
