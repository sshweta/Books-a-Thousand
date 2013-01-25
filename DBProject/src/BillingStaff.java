/*This program is to display all the functionalities in the Billing Staff class.*/

/*Import Statements */
import java.sql.*;
import java.util.*;


public class BillingStaff {
private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	/* Enter your Username and Password here */
    
	private static final String username = "system";
    private static final String pwd = "varun";
    
    
    private Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;
    Scanner scan = new Scanner(System.in); 
    
    /* Variable Declarations */
    
    int custID,vendID;
    String startDate,endDate;
    
    
    /* Main Method Declaration */
    
    public static void main(String[] args) 
    {
    BillingStaff bst = new BillingStaff();
    bst.menuCall();
    }
    
    /* Constructor for Billing Staff Class */
    public BillingStaff()
    {
   
    try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
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

/* Menu Call Method. This would display a list for options to choose from. */
    
private void menuCall() {
boolean quit=true;
int operation;
while(quit)
{
System.out.println("Welcome Billing Staff");
System.out.println("Enter the operation that you wish to perform");
System.out.println("1. Display billing accounts for a Customer for a Billing Cycle");
System.out.println("2. Display billing accounts for a Vendor for a Billing Cycle");
System.out.println("3. Display billing accounts for all Customers for all Billing Cycles");
System.out.println("4. Display billing accounts for all Vendors for all Billing Cycles");
System.out.println("5. Quit");
operation=scan.nextInt();		// Take value from The console here

            switch(operation)
            {	
            
            /* case 1: Display billing accounts for a Customer for a Billing Cycle */
            
            	case 1:
                System.out.println("Enter the Customer ID");
                custID = scan.nextInt();
                System.out.print("Enter the start date of the duration");
                    startDate= scan.next();
                    System.out.print("Enter the end date of the duration");
                    endDate = scan.next();
                particularCustBillingRecord(custID,startDate,endDate);
                break;
                
            /* case 2: Display billing accounts for a Vendor for a Billing Cycle */
                case 2:
                System.out.println("Enter the Vendor ID");
                vendID = scan.nextInt();
                System.out.print("Enter the start date of the duration");
                    startDate= scan.next();
                    System.out.print("Enter the end date of the duration");
                    endDate = scan.next();
                particularVendBillingRecord(vendID,startDate,endDate);
                break;
                
                /* Case 3:  Display billing accounts for all Customers for all Billing Cycles */
                case 3:
                displayGenCustBill();
                break;
                
                /* Case 4 : Display billing accounts for all Vendors for all Billing Cycles */
                case 4:
                displayGenVendBill();
                break;
                
                case 5:
                System.exit(0);
                    break;
            default:
                    System.out.println("Invalid Entry");
                    break;    
            }
}
}

/* Method for Displaying billing accounts for all Vendors for all Billing Cycles */
private void displayGenVendBill() 
{
try {
result=statement.executeQuery("Select * from GenVendBill");
System.out.println("VendBillID BillingStaffID VendorID \t BillMonth");
while(result.next())
{
System.out.println(result.getInt(1)+"\t\t"+result.getInt(2)+"\t\t"+result.getInt(3)+"\t"+result.getString(4));
}
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}


/* Method for Displaying billing accounts for all Customers for all Billing Cycles */
private void displayGenCustBill() {
try {
result=statement.executeQuery("Select * from GenCustBill");
System.out.println("CustBillID BillingStaffId CustomerID \t BillMonth");
while(result.next())
{
System.out.println(result.getInt(1)+" \t\t"+result.getInt(2)+" \t\t"+result.getInt(3)+" \t"+result.getString(4));
}
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

/* Method for Displaying billing accounts for a Vendor for a Billing Cycle */
private void particularVendBillingRecord(int vendID2, String startDate2,
String endDate2) 
{
int totalprice=0;
try {
System.out.println("vendorOrderID  VendorID\t  Title\t\t  Total Price");
result=statement.executeQuery("select distinct vendorOrderId, vendorId, title, (price*noOfCopies) AS TotalPrice from (select V.vendorOrderID, V.vendorID, V.ISBN, V.noOfCopies, G.vendBillId,B.title,B.price from vendorOrders V, GenVendBill G, Book B where V.vendorID=G.vendorID and V.ISBN=B.ISBN and V.dateOrdered=G.billmonth and G.billmonth between'"+ startDate2+"'and'"+endDate2+"') where vendorId="+vendID2);
while (result.next())
{
System.out.println(result.getInt(1)+"\t\t "+result.getInt(2)+"\t\t "+result.getString(3)+"\t "+result.getInt(4));
totalprice += result.getInt(4);
}
} catch (SQLException e) {
e.printStackTrace();
}
finally
{
System.out.println("The total bill for this period is $"+ totalprice);
}
}


/* Method for Displaying billing accounts for a Customer for a Billing Cycle */
private void particularCustBillingRecord(int custID2, String startDate2,
String endDate2) {
int totalprice=0;
try {
System.out.println("CustomerOrderID  CustomerID\t  Title\t\t  Total Price");
result = statement.executeQuery("select distinct customerOrderId, customerId, title, (price*noOfCopiesRequested) AS TotalPrice from(select C.customerOrderID, c.customerID, C.ISBN, c.noOfCopiesRequested, G.custBillId,B.title,B.price from customerOrders C, GenCustBill G, Book B where C.customerID=G.customerID and C.ISBN=B.ISBN and C.dateOrdered=G.billmonth and G.billmonth between'"+startDate2+"'and'"+endDate2+"' ) where customerId="+custID2);
while (result.next())
{
System.out.println(result.getInt(1)+"\t\t "+result.getInt(2)+"\t\t "+result.getString(3)+"\t "+result.getInt(4));
totalprice += result.getInt(4);
}
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
finally
{
System.out.println("The total bill for this period is $"+ totalprice);
}
}

}



