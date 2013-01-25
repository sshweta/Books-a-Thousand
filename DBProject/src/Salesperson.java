

/*This program is to display all the functionalities in the Salesperson class.*/

//Import Statements
import java.sql.*;
import java.util.*;


public class Salesperson
{

	private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";

	/*Enter your Username and Password here */
	private static final String username = "system";
	private static final String pwd = "varun";


	private Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;
	private static ResultSet result1 = null;
	Scanner scan = new Scanner(System.in);

	/* VAriable Declaratiom */
	private static int myStoreId = 0;
	char gender;
	int customerId,customerOrderId, salespersonId, age, ISBN ,ssn, phoneNumber, numberOfCopiesRequested,pendingOrderID,vendorID,stockerID,shippingstaffID;
	String name, address, dateOfBirth, dateOrdered;

	// Main Method
	public static void main(String[] args)
	{
		Salesperson sls = new Salesperson();
		sls.menuCall();
	}

	//Constructor Declaration for Salesperson
	public Salesperson()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");    // Driver for Oracle
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
	public void menuCall()
	{
		boolean quit=true;
		int operation;
		while(quit)
		{
			System.out.println("Welcome!\nPlease enter your staff ID");
			salespersonId = scan.nextInt();
			try {
				result1= statement.executeQuery("Select * from salesperson where salespersonid =  "+salespersonId);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				while(result1.next())
				{
					try
					{    
						result = statement.executeQuery("SELECT storeID FROM Salesperson WHERE salespersonid="+salespersonId);
						while(result.next())
						{
							myStoreId = result.getInt(1);
						}
					}
					catch (SQLException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("Enter the operation that you wish to perform");
					System.out.println("1. Insert a record for a new Customer");
					System.out.println("2. Update an existing Customer's record");
					System.out.println("3. Delete an existing Customer record");
					System.out.println("4. Sell book");
					System.out.println("5. Insert a new book record in store");
					System.out.println("6. Replenish stock in store");
					System.out.println("7. Quit");
					operation=scan.nextInt();
					switch(operation)
					{

					/* Case 1:Insert a record for a new Customer   */
					case 1:
						System.out.println("Enter a Customer Id");
						customerId = scan.nextInt();
						System.out.println("Enter the name");
						name = scan.next();
						System.out.println("Enter the Social Security Number");
						ssn = scan.nextInt();
						System.out.println("Enter the age");
						age = scan.nextInt();
						System.out.println("Enter the gender");
						gender = ((scan.next()).toCharArray())[0];
						System.out.println("Enter the phone number");
						phoneNumber = scan.nextInt();
						System.out.println("Enter address");
						address = scan.next();
						System.out.println("Enter Date Of Birth");
						dateOfBirth = scan.next();
						createCust(customerId, name, ssn, dateOfBirth, gender, age, phoneNumber, address);
						break;


						/* Case 2 : Update an existing Customer's record*/
					case 2:
						System.out.println("Enter the id of the Customer who's record you want to update");
						customerId = scan.nextInt();
						try
						{
							result = statement.executeQuery("SELECT * FROM customer WHERE customerId ="+customerId);
						}
						catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						updateCust(result);
						break;

						/* Case 3: Delete an existing Customer record */
					case 3:
						System.out.println("Enter the id of the customer who's record you want to delete");
						customerId = scan.nextInt();
						deleteCust(customerId);
						break;

						/* Case 4: Sell book */
					case 4:
						sellBook();
						break;

						/* Case 5: Insert a new book record in store */
					case 5:
						ResultSet rs1 = null;
						int copies=0;
						try
						{
							rs1 = statement.executeQuery("select isbn from book where isbn  not in(select isbn from storebooks where storeid="+myStoreId+")");
							System.out.println("Enter an ISBN from the following list of books");
							while(rs1.next())
							{
								System.out.print(rs1.getInt(1)+" | ");
							}
							int isbn = scan.nextInt();
							System.out.println("Enter the quantity");
							copies = scan.nextInt();
							statement.executeUpdate("INSERT INTO storeBooks VALUES("+isbn+", "+copies+", "+myStoreId+")");
						}
						catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;    

						/*  Case 6. Replenish stock in store*/
					case 6:
						ResultSet rs11 = null;
						int copies1=0;
						try
						{
							rs11 = statement.executeQuery("select isbn from storebooks where storeid="+myStoreId);
							System.out.println("Enter an ISBN from the following list of books");
							while(rs11.next())
							{
								System.out.print(rs11.getInt(1)+" | ");
							}
							int isbn = scan.nextInt();
							System.out.println("Enter the quantity");
							copies1 = scan.nextInt();
							statement.executeUpdate("UPDATE storeBooks SET noOfCopies = noOfCopies+"+copies1+"WHERE ISBN ="+isbn+" AND storeId="+myStoreId);
						}
						catch (SQLException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;


					case 7:
						System.exit(0);
						break;


					default:
						System.out.println("Invalid Entry");
						break;    
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/* Method for Creating Customer */
	public void createCust(int customerId, String name, int ssn, String dateOfBirth, char gender, int age, int phoneNumber, String address)
	{
		try
		{
			statement.executeUpdate("INSERT INTO customer VALUES ('"+customerId+"', '"+name+"','"+ssn+"', '"+dateOfBirth+"', '"+gender+"', '"+age+"', '"+phoneNumber+"', '"+address+"')");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/* Method for Update Customer */
	public void updateCust(ResultSet rs)
	{
		try
		{
			while(rs.next())
			{
				int choice;
				System.out.println("Current record");
				System.out.print(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
				System.out.println();
				customerId = rs.getInt(1);
				System.out.println("Enter the column index that you wish to update");
				System.out.println("1. Name");
				System.out.println("2. Age");
				System.out.println("3. Gender");
				System.out.println("4. SSN");
				System.out.println("5. Date Of Birth");
				System.out.println("6. Phone Number");
				System.out.println("7. Address");
				System.out.println("8. Customer ID");
				choice = scan.nextInt();
				switch(choice)
				{
				case 1:
					System.out.println("Enter the name");
					name = scan.next();
					statement.executeUpdate("UPDATE CUSTOMER SET NAME='"+name+"' WHERE CUSTOMERID="+customerId);
					break;
				case 2:
					System.out.println("Enter the age");
					age = scan.nextInt();
					statement.executeUpdate("UPDATE CUSTOMER SET AGE='"+age+"' WHERE CUSTOMERID="+customerId);
					break;
				case 3:
					System.out.println("Enter the gender");
					gender = ((scan.next()).toCharArray())[0];
					statement.executeUpdate("UPDATE CUSTOMER SET GENDER='"+gender+"' WHERE CUSTOMERID="+customerId);
					break;
				case 4:
					System.out.println("Enter the Social Security Number");
					ssn = scan.nextInt();
					statement.executeUpdate("UPDATE CUSTOMER SET SSN='"+ssn+"' WHERE CUSTOMERID="+customerId);
					break;
				case 5:
					System.out.println("Enter the Date Of Birth");
					dateOfBirth = scan.next();
					statement.executeUpdate("UPDATE CUSTOMER SET DateOfBirth='"+dateOfBirth+"' WHERE CUSTOMERID="+customerId);
					break;
				case 6:
					System.out.println("Enter the phone number");
					phoneNumber = scan.nextInt();
					statement.executeUpdate("UPDATE CUSTOMER SET PHONENUMBER='"+phoneNumber+"' WHERE CUSTOMERID="+customerId);
					break;
				case 8:
					System.out.println("Enter address");
					address = scan.next();
					statement.executeUpdate("UPDATE CUSTOMER SET ADDRESS='"+address+"' WHERE CUSTOMERID="+customerId);
					break;
				default:
					System.out.println("Invalid Entry");
					break;    
				}
				rs = statement.executeQuery("SELECT * FROM Customer WHERE CustomerID ="+customerId);
				while(rs.next())
				{
					System.out.print(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8));
					System.out.println();
				}
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method for Deleting a Customer
	public void deleteCust(int customerId)
	{
		try
		{
			statement.executeUpdate("DELETE FROM Customer WHERE CUSTOMERID="+customerId);
			System.out.println("The record was deleted successfully");
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method for Selling Book
	public void sellBook()
	{
		System.out.println("Enter a Customer Order Id");
		customerOrderId = scan.nextInt();
		System.out.println("Enter the customer Id");
		customerId = scan.nextInt();
		System.out.println("Enter the ISBN of the book");
		ISBN = scan.nextInt();
		System.out.println("Enter Number of copies requested");
		numberOfCopiesRequested = scan.nextInt();
		System.out.println("Enter Date Of Order");
		dateOrdered = scan.next();
		try {
			statement.executeUpdate("INSERT INTO customerorders VALUES ('"+customerOrderId+"', '"+salespersonId+"','"+customerId+"', '"+ISBN+"', '"+dateOrdered+"', '"+numberOfCopiesRequested+"')");
			checkAvailability();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* This method is called in sellBook() method  to know whether a not a book is available in the storeHouse or not.
	 * After checking that it performs the next steps to reflect necessary changes*/
	public void checkAvailability()
	{
		try
		{
			result = statement.executeQuery("select title from Book where ISBN in (Select ISBN from StoreBooks where noOfcopies >=(select noofCopiesRequested from CustomerOrders where CustomerOrders.ISBN=StoreBooks.ISBN and customerOrderId = "+customerOrderId+") AND storeBooks.storeId="+myStoreId+")");
			System.out.println("Title\t");
			if(result.next())
			{
				System.out.print(result.getString(1));
				statement.executeUpdate("UPDATE storeBooks SET noOfCopies = noOfCopies-"+numberOfCopiesRequested+"WHERE ISBN = "+ISBN+" AND storeId ="+myStoreId);
				System.out.println();
			}
			else
			{
				ResultSet rs = null;
				System.out.println("Enter Pending Order ID");
				pendingOrderID=scan.nextInt();
				rs = statement.executeQuery("SELECT vendorId FROM vendorMerc WHERE merchandiseSupplied ="+ISBN);
				while(rs.next())
				{
					vendorID =rs.getInt(1);

				}
				if (vendorID==0)
				{
					vendorID=1;    
				}
				rs = statement.executeQuery("SELECT stockerId FROM MERCHANDISESTOCKER");
				while(rs.next())
				{
					stockerID = rs.getInt(1);
				}
				rs = statement.executeQuery("select shippingstaffid from (select shippingstaffid from shippingstaff order by dbms_random.value) where rownum=1");
				while(rs.next())
				{
					shippingstaffID = rs.getInt(1);
				}
				statement.executeUpdate("INSERT INTO pendingorders VALUES ("+pendingOrderID+", "+vendorID+","+stockerID+", "+shippingstaffID+", "+customerOrderId+", "+ISBN+", 'Pending')");
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}