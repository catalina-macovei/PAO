package imutabile;

public final class ShoppingCartImmutable {
    private final int nrProd;
    private final Product product;

    public ShoppingCartImmutable(int nrProd, Product product) {
        this.nrProd = nrProd;
        this.product = new Product(product);
    }

    public int getNrProd() {return nrProd; }

    public Product getProduct() {
        return new Product(product);
    }
}
