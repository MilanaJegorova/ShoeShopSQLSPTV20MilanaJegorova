package shoeshopsptv20milanajegorova;

import java.util.Date;

public class History {
    private Long id;
    private Long customer_id;
    private Long shoe_id;
    private Date date;

    public History(Long id, Long customer_id, Long shoe_id, Date date) {
        this.id = id;
        this.customer_id = customer_id;
        this.shoe_id = shoe_id;
        this.date = date;
    }
    
    public History() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public Long getShoe_id() {
        return shoe_id;
    }

    public void setShoe_id(Long shoe_id) {
        this.shoe_id = shoe_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "History{" + "id=" + id + ", customer_id=" + customer_id + ", shoe_id=" + shoe_id + ", date=" + date + '}';
    }
    
}
