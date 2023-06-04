package fa.training.entities;

public class Product {
    private int productId;
    private String productName;
    private double listPice;

    public Product() {
    }

    public Product(int productId, String productName, double listPice) {
        this.productId = productId;
        this.productName = productName;
        this.listPice = listPice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getListPice() {
        return listPice;
    }

    public void setListPice(double listPice) {
        this.listPice = listPice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", listPice=" + listPice +
                '}';
    }
}
