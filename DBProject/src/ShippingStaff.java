/*This program is to display all the functionalities in the Shipping Staff class.*/

/*import statements*/
import java.sql.*;
import java.util.*;

/* Public class Shipping Staff */
public class ShippingStaff {
   private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
    
   /*Enter Username and Password here*/
   private static final String username = "system";
   private static final String pwd = "varun";
   private Connection connection = null;
   private static Statement statement = null;
   private static ResultSet result = null;
   Scanner scan = new Scanner(System.in);
   
   /* Variable Declarations */
   int sstID,pendingOrderID;
   
   /* Main Method */
    public static void main(String[] args)
        {
        ShippingStaff sst = new ShippingStaff();
        sst.menuCall();

        }
    
    /* Constructor For Shipping Class */
    public ShippingStaff()
    {
        try
       {
           Class.forName("oracle.jdbc.driver.OracleDriver");	//Driver for Oracle
           // Get a connection instance from the first driver in the
           // DriverManager list that recognizes the URL jdbcURL
           connection = DriverManager.getConnection(jdbcURL, username, pwd);
           // Create a statement instance that will be sending
           // your SQL statements to the DBMS
           statement = connection.createStatement();
           Locale.setDefault(Locale.US);
        }
       catch(Exception ex)
       {
           ex.printStackTrace();
       }
  
    }
    /* Menu Call Method. This would display the users a list to choose from for performing various operations for the Shipping Staff Class */
    private void menuCall() {
        boolean quit=true;
        int operation;
        while(quit)
        {
            System.out.println("Welcome Shipping Staff");
            System.out.println("Enter the operation that you wish to perform");
            System.out.println("1. Take up the Shipping Order");
            System.out.println("2. Update the Shipping Order");
            System.out.println("3. Quit");
            operation=scan.nextInt();
           switch(operation)
           {	
           
           		/*  Take up the order that has been assigned to the Shipping Class by the Vendor */
               case 1:
                   System.out.println("Enter the Shipping Staff ID");
                   sstID = scan.nextInt();
                try {
                    result=statement.executeQuery("Select * from PendingOrders where shippingStaffID = "+sstID+" and status='Assigned'");
                    System.out.println("PendingOrdersID VendorID StockerID ShippingStaffID CustomerOrderID  \t \t ISBN \t \t Status");
                    while (result.next())
                    {   
                        System.out.println(result.getInt(1)+"\t \t"+result.getInt(2)+"\t \t"+result.getInt(3)+"\t \t"+result.getInt(4)+"\t \t"+result.getInt(5)+"\t \t"+result.getInt(6)+"\t \t"+result.getString(7));
                        /*statement.executeUpdate("update PendingOrders set status ='Shipped' where  PendingOrdersID = "+result.getInt(1));
                        System.out.println(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getInt(4)+"\t"+result.getInt(5)+"\t"+result.getInt(6)+"\t"+result.getString(7)); */
                    }
                    break;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                
                /* Update the 'Assigned' order after they are shipped and set it to 'Shipped' */
               case 2:
                   System.out.println("Enter the Pending Order ID");
                   pendingOrderID = scan.nextInt();
                try {
                    statement.executeUpdate("update PendingOrders set status ='Shipped' where  PendingOrdersID = "+pendingOrderID+"");
                   
                    result=statement.executeQuery("Select * from PendingOrders where pendingOrdersID = "+pendingOrderID+"");
                    System.out.println("PendingOrdersID VendorID StockerID ShippingStaffID CustomerOrderID  \t \t ISBN \t \t Status");
                    while (result.next())
                    {
                        System.out.println(result.getInt(1)+"\t \t"+result.getInt(2)+"\t \t"+result.getInt(3)+"\t \t"+result.getInt(4)+"\t \t"+result.getInt(5)+"\t \t"+result.getInt(6)+"\t \t"+result.getString(7));
                        /*statement.executeUpdate("update PendingOrders set status ='Shipped' where  PendingOrdersID = "+result.getInt(1));
                        System.out.println(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getInt(4)+"\t"+result.getInt(5)+"\t"+result.getInt(6)+"\t"+result.getString(7));*/
                    }
                    break;
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
               case 3:
               {
                   System.exit(0);
                   break;
               }
           default:
                   System.out.println("Invalid Entry");
                   break;
               
           }
        }
    }

}

