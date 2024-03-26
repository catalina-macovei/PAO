package imutabile;

public class ShoppingCart {
    private int nrProd;
    private Product product;

    public ShoppingCart(int nrProd, Product product) {
        this.nrProd = nrProd;
        this.product = product;
    }

    public int getNrProd() {
        return nrProd;
    }

    public void setNrProd(int nrProd) {
        this.nrProd = nrProd;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
