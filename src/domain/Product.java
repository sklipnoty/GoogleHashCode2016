package domain;

public class Product {
    private int type;
    private int units;

    public Product(int type, int units) {
        this.type = type;
        this.units = units;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Product{" + "type=" + type + ", units=" + units + '}';
    }
    
    
}
