package shoeshopsptv20milanajegorova;

public class Shoe {
    private Long id;
    private String name;
    private int price;
    private int available;

    public Shoe() {
    }  

    public Shoe(String name, int price) {
        this.name = name;
        this.price = price;
    }
    
    public Shoe(Long id, String name, int price, int available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Shoe{" + "name=" + name + ", price=" + price + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

}
