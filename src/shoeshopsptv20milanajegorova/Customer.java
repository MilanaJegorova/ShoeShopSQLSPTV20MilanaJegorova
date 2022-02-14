package shoeshopsptv20milanajegorova;

public class Customer {
    private Long id;
    private String name;
    private String surname;
    private int money;

    public Customer() {
    }


    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Customer(String name, String surname, int money) {
        this.name = name;
        this.surname = surname;
        this.money = money;
    }

    public Customer(Long id, String name, String surname, int money) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    
    @Override
    public String toString() {
        return "Customer{" + "name=" + name + ", surname=" + surname + ", money=" + money + '}';
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
