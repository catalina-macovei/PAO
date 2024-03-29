package imutabile;

public class Application {
    public static void main(String[] args) {
        Product product = new Product("Carte", 300);

        ShoppingCart cart = new ShoppingCart(3, product);
        ShoppingCartImmutable cartImmutable = new ShoppingCartImmutable(2, product);

        // schimb numele produsului
        //product.setName("carnet");

        System.out.println("CArt immutable: " + cartImmutable.getProduct() + " " + cartImmutable.getNrProd());
        System.out.println("Cart simplu: " + cart.getProduct());
    }
}
