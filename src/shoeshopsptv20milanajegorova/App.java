package shoeshopsptv20milanajegorova;

import java.util.List;
import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;

public class App {

    public void run() throws SQLException {

        Connection conn = connectToDB();
     
        Statement stmt = conn.createStatement();
        ResultSet rs;
        
        List<Shoe> shoes = getAllShoes(stmt);
        List<Customer> customers = getAllCustomers(stmt);
        List<History> history = getAllHistory(stmt);
        List<History> monthHistory;

        Scanner scanner = new Scanner(System.in); 
        boolean working = true;
        int gain = 0;
        
        while(working){
            System.out.println("\n-- Список функций --");
            System.out.println("0. Выход из программы");
            System.out.println("1. Добавить модель");
            System.out.println("2. Список продоваемых моделей");
            System.out.println("3. Добавить покупателя");
            System.out.println("4. Список зарегистрированных покупателей");
            System.out.println("5. Покупка покупателем обуви");
            System.out.println("6. Доход магазина за определенный месяц");
            System.out.println("7. Добавить денег покупателю");
            System.out.println("8. Изменить обувь");
            System.out.println("9. Изменить покупателя\n");
            
            System.out.print("Выберите функцию: ");          
            String choice = scanner.nextLine();

            String str = "";
            
            switch(choice){
                
                case "0":
                    conn.close();
                    working = false;
                    break;
                    
                case "1":
                    
                    System.out.print("\nВведите название обуви: ");
                    String shoeName = scanner.nextLine();
                    System.out.print("Введите цену: ");
                    int shoePrice = scanner.nextInt();
                   
                    String sql = "INSERT INTO shoes (name, price) VALUES ('" + shoeName + "', " + shoePrice + ")";
                    stmt.executeUpdate(sql);
                    break;    
                    
                case "2":      
                    
                    shoes = getAllShoes(stmt);
                    System.out.println("");
                    for (int i = 0; i < shoes.size(); i++){
                        if (shoes.get(i).getAvailable() != 0) {
                            System.out.println("#" + shoes.get(i).getId() + ". " + shoes.get(i).getName() + "(" + shoes.get(i).getPrice() + "$)");
                        }
                    }
                    break;
                    
                case "3":
                    
                    System.out.print("\nВведите имя: ");
                    String custName = scanner.nextLine();
                    System.out.print("Введите фамилию: ");
                    String custSurname = scanner.nextLine();
                    System.out.print("Введите базовое количество денег: ");
                    int custMoney = scanner.nextInt();
                    
                    sql = "INSERT INTO customers (name, surname, money) VALUES ('" + custName + "', '" + custSurname + "', " + custMoney + ")";
                    stmt.executeUpdate(sql);
                    break;    
                    
                case "4":  
                    
                    customers = getAllCustomers(stmt);
                    System.out.println("");
                    for (int i = 0; i < customers.size(); i++){
                        System.out.println("#" + customers.get(i).getId() + ". " + customers.get(i).getName() + " " + customers.get(i).getSurname() + " (" + customers.get(i).getMoney() + "$)");
                    }   
                    break;
                    
                case "5":
                    
                    System.out.print("\nВведите ID покупателя: ");
                    Long custId = scanner.nextLong();
                   
                    System.out.print("Введите ID обуви: ");
                    Long shoeId = scanner.nextLong();    
                    
                    Customer cust = getCustomerById(stmt, custId);
                    Shoe shoe = getShoeById(stmt, shoeId);
                    
                    if (shoe.getPrice() > cust.getMoney()) {
                        
                        System.out.println("\nУ покупателя недостаточно денег!");
                        
                    } else {              
                        
                        sql = "UPDATE shoes SET available = 0 WHERE id = " + shoeId;
                        stmt.executeUpdate(sql);
                        
                        sql = "UPDATE customers SET money = " + (cust.getMoney() - shoe.getPrice()) + " WHERE id = " + custId;
                        stmt.executeUpdate(sql);

                        sql = "INSERT INTO history (customer_id, shoe_id) VALUES (" + custId + ", " + shoeId + ")";
                        stmt.executeUpdate(sql);
                        
                        System.out.println("\nОбувь успешно куплена!");
                    }
                    break;
                    
                case "6":
                    
                    System.out.print("\nВведите год: ");
                    String year = scanner.nextLine();
                    System.out.print("Введите месяц: ");
                    String month = scanner.nextLine();   
                    
                    monthHistory = getMonthHistory(stmt, year, month);
                    
                    for (int i = 0; i < monthHistory.size(); i++) { 
                        gain += getShoeById(stmt, monthHistory.get(i).getShoe_id()).getPrice();
                    }
                    
                    System.out.println("\nДоход магазина за " + month + "/" + year + ": " + gain + "$");
                    gain = 0;
                    break;
                    
                case "7":        
                    
                    System.out.print("\nВведите ID покупателя: ");
                    custId = scanner.nextLong();
                    
                    System.out.print("Введите количество денег: ");
                    int money = scanner.nextInt();
                    
                    sql = "UPDATE customers SET money = money + " + money + " WHERE id = " + custId;
                    stmt.executeUpdate(sql);
                    break;     
                
                case "8":
                    
                    System.out.print("\nВведите ID обуви: ");   
                    shoeId = scanner.nextLong();    
                    
                    shoe = getShoeById(stmt, shoeId);

                    if (shoe != null) {
                        
                        System.out.println("\n>>> Обувь найдена! (" + shoe.getName() + " - " + shoe.getPrice() + "$)");
                        
                        System.out.print("\nВведите новое название: ");
                        scanner.nextLine();
                        shoeName = scanner.nextLine();
                        System.out.print("Введите новую цену: ");
                        shoePrice = scanner.nextInt(); 
                        
                        sql = "UPDATE shoes SET name = '" + shoeName + "', price = " + shoePrice + " WHERE id = " + shoeId;
                        stmt.executeUpdate(sql); 
                        
                        System.out.println("\nОбувь успешно изменена!");
                        
                    } else {
                        
                        System.out.println("\nТакой обуви нет!");
                        
                    }
                    
                    break;
                
                case "9":
                    
                    System.out.print("\nВведите ID покупателя: ");   
                    custId = scanner.nextLong();    
                    
                    cust = getCustomerById(stmt, custId);

                    if (cust != null) {
                        
                        System.out.println("\n>>> Покупатель найден! (" + cust.getName() + " " + cust.getSurname() + ")");
                        
                        System.out.print("\nВведите новое имя: ");
                        scanner.nextLine();
                        custName = scanner.nextLine();
                        System.out.print("Введите новую фамилию: ");
                        custSurname = scanner.nextLine(); 
                        
                        sql = "UPDATE customers SET name = '" + custName + "', surname = '" + custSurname + "' WHERE id = " + custId;
                        stmt.executeUpdate(sql); 
                        
                        System.out.println("\nПокупатель успешно изменен!");
                        
                    } else {
                        
                        System.out.println("\nТакого покупателя нет!");
                        
                    }
                    
                break;
            }               
        }
    }
    
    public Connection connectToDB() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/shoeshopsptv20milanajegorova",
            "shoeshopsptv20milanajegorova",
            "shoeshopsptv20milanajegorova");

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }
            
            return conn;
        } catch (SQLException e) {     
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Shoe> getAllShoes(Statement stmt) throws SQLException {
        ResultSet rs;
        List<Shoe> shoes = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM shoes");
        } catch (Exception e) {
            stmt.executeUpdate("CREATE TABLE shoes (id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(255), price INT, available TINYINT(1) DEFAULT 1)");
            rs = stmt.executeQuery("SELECT * FROM shoes");
        }
        while (rs.next()) {
            shoes.add(new Shoe(rs.getLong("id"), rs.getString("name"), Integer.parseInt(rs.getString("price")), Integer.parseInt(rs.getString("available"))));
        }
        return shoes;
    }
    
    public List<Customer> getAllCustomers(Statement stmt) throws SQLException {
        ResultSet rs;
        List<Customer> customers = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM customers");
        } catch (Exception e) {
            stmt.executeUpdate("CREATE TABLE customers (id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(255), surname VARCHAR(255), money INT)");
            rs = stmt.executeQuery("SELECT * FROM customers");
        }
        while (rs.next()) {
            customers.add(new Customer(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), Integer.parseInt(rs.getString("money"))));
        }
        return customers;
    }
    
    public List<History> getAllHistory(Statement stmt) throws SQLException {
        ResultSet rs;
        List<History> history = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM history");
        } catch (Exception e) {
            stmt.executeUpdate("CREATE TABLE history (id BIGINT(20) PRIMARY KEY NOT NULL AUTO_INCREMENT, customer_id BIGINT(20), shoe_id BIGINT(20), date DATETIME DEFAULT NOW())");
            rs = stmt.executeQuery("SELECT * FROM history");
        }   
        while (rs.next()) {
            history.add(new History(Long.parseLong(rs.getString("id")), Long.parseLong(rs.getString("customer_id")), Long.parseLong(rs.getString("shoe_id")), rs.getDate("date")));
        }
        return history;
    }
    
    public List<History> getMonthHistory(Statement stmt, String year, String month) throws SQLException {
        ResultSet rs;
        List<History> history = new ArrayList<>();
        try {
            rs = stmt.executeQuery("SELECT * FROM history WHERE YEAR(date) = " + year + " AND MONTH(date) = " + month);
        } catch (Exception e) {
            return history;
        }   
        while (rs.next()) {
            history.add(new History(Long.parseLong(rs.getString("id")), Long.parseLong(rs.getString("customer_id")), Long.parseLong(rs.getString("shoe_id")), rs.getDate("date")));
        }
        return history;
    }
    
    public Shoe getShoeById(Statement stmt, Long id) throws SQLException {
        ResultSet rs;
        Shoe shoe = new Shoe();
        try {
            rs = stmt.executeQuery("SELECT * FROM shoes WHERE id = " + id);
        } catch (Exception e) {
            return null;
        }     
        while (rs.next()) {
            shoe = new Shoe(Long.parseLong(rs.getString("id")), rs.getString("name"), Integer.parseInt(rs.getString("price")), Integer.parseInt(rs.getString("available")));
        }
        return shoe;
    } 
    
    public Customer getCustomerById(Statement stmt, Long id) throws SQLException {
        ResultSet rs;
        Customer cust = new Customer();
        try {
            rs = stmt.executeQuery("SELECT * FROM customers WHERE id = " + id);
        } catch (Exception e) {
            return null;
        }     
        while (rs.next()) {
            cust = new Customer(Long.parseLong(rs.getString("id")), rs.getString("name"), rs.getString("surname"), Integer.parseInt(rs.getString("money")));
        }
        return cust;
    } 

}