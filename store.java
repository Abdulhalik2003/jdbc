package org.example;
import java.util.Scanner;
import java.sql.*;
import static org.example.constant.*;
public class store {
    public static void main(String[]args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu");
        System.out.println("Enter your 1: To view products details");
        System.out.println("Enter your 2: To add products details");
        System.out.println("Enter your 3: To delete product");
        System.out.println("Enter your 4: To Update to details");
        System.out.println("Enter your 5: To Exit");

        while (true) {
            System.out.println("Enter your choice");
             int choice = sc.nextInt();
             switch(choice){
                 case 1:
                     viewproducts();
                     break;
                 case 2:
                     System.out.println("Enter product_id ");
                     String product_id = sc.next();
                     System.out.println("Enter product_name ");
                     String product_name = sc.next();
                     System.out.println("Enter product_price ");
                     int price = sc.nextInt();
                     System.out.println("Enter product_quantity ");
                     int quantity = sc.nextInt();
                     addProducts(product_id,product_name,price,quantity);
                     break;
                 case 3:
                 System.out.println("Enter product_id to delete");
                 String id = sc.next();

                 while (id.equals("0")) {
                     System.out.println("Enter a valid product_id (not 0)");
                     id = sc.next();
                 }

                 deleteProduct(id);
                 break;
                 case 4:
                     System.out.println("enter the product_id to search and upadate");
                     String productid = sc.next();
                     System.out.println("enter the price to upadate");
                     int updatePrice = sc.nextInt();
                     System.out.println("enter the quantity to upadate");
                     int updateQuantity = sc.nextInt();
                     updateProducts(updatePrice,updateQuantity,productid);
                     break;

                 case 5:
                     System.out.println( "exiting");
                     System.exit(0);
                     break;
                 default:
                     System.out.println("Invalid choice. Please enter a valid option.");
             }
        }
    }
    public static void addProducts( String product_id, String product_name, int price, int quantity) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO store.product(product_id,product_name,price,quantity)values (?,?,?,?)";
         try (   PreparedStatement state = connection.prepareStatement(query)){
            state.setString(1,product_id);
            state.setString(2,product_name);
            state.setInt(3,price);
            state.setInt(4,quantity);
            state.executeUpdate();
         }
         System.out.println("product added sucessfully");

           // connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewproducts(){
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String query = "select*from product";
            PreparedStatement state = connection.prepareStatement(query);
            ResultSet rs = state.executeQuery();
            while(rs.next()){
                System.out.println("Product's id "+"----" +rs.getString("product_id"));
                System.out.println("Name of the product"+"----"+rs.getString("product_name"));
                System.out.println("Price"+"----"+rs.getInt("price"));
                System.out.println("Quantity"+"----"+rs.getInt("quantity"));

            }
            connection.close();
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }
    public static void deleteProduct(String product_id){
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            String deletequery = "delete from store.product where product_id = (?)";
            PreparedStatement state = connection.prepareStatement(deletequery);
            state.setString(1,product_id);

            int rowsAffected = state.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully");
            } else {
                System.out.println("No product found with the given ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateProducts(int price,int quantity,String product_id){
        try{
            Connection connection = DriverManager.getConnection(url,user,password);
            String updateQuery = " update product  set price = ? , quantity = ? where product_id = ?" ;
            PreparedStatement state = connection.prepareStatement(updateQuery);
            state.setInt(1,price);
            state.setInt(2,quantity);
            state.setString(3,product_id);
            state.executeUpdate();
            System.out.println("Updated Sucessfully");
        } catch (SQLException e){
         e.printStackTrace();
        }
    }
}

