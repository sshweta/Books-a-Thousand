import java.sql.*;
import java.util.*;

public class Stocker
{

	private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";


	private static final String username = "system";
	private static final String pwd = "varun";


	private Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;
	Scanner scan = new Scanner(System.in);

	int vendorId, ISBN;
	long phoneNumber;
	String name, address;
	public static void main(String[] args)
	{
		Stocker str = new Stocker();
		try
		{
			str.menuCall();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Stocker()
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

	/* Menu Call Method. This would display the users a list to choose from for performing various operations for the Stocker Class */

	public void menuCall() throws SQLException
	{
		boolean quit=true;
		int operation;

		while(quit)
		{
			System.out.println("Welcome ");
			System.out.println("Enter the operation that you wish to perform");
			System.out.println("1. Create record for a new vendor");
			System.out.println("2. Update the record of an existing vendor");
			System.out.println("3. Delete the record of an existing vendor");
			System.out.println("4. Track pending Orders ");
			System.out.println("5. Replenish the stock in the warehouse");
			System.out.println("6. Modify Warehouse");
			System.out.println("7 Generate information related to all contractual vendors");
			System.out.println("8. Create a new contract");
			System.out.println("9. Quit");
			operation = scan.nextInt();
			switch(operation)
			{
			case 1:
				try
				{
					result = statement.executeQuery("SELECT * FROM VENDOR ORDER BY VENDORID"); 
					/* Displays all the vendors that already have a contract with the warehouse”  */
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					while(result.next())
					{
						System.out.print("VENDORID NAME\t\tPHONE#\t\tADDRESS\n");
						System.out.println();
						System.out.println(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t\t"+result.getString(4)+"\n");
					}
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*Enters all the details of the new vendor */
				System.out.println("Enter a UNIQUE ID# for the vendor");
				vendorId = scan.nextInt();
				System.out.println("Enter the vendor's name");
				name = scan.next();
				System.out.println("Enter the vendor's phone number");
				phoneNumber = scan.nextLong();
				System.out.println("Enter the vendor's address");
				address = scan.next();
				createVendor(vendorId, name, phoneNumber, address);
				break;

			case 2:
				/* This set of operations updates the details of a particular vendor */
				try
				{
					result = statement.executeQuery("SELECT * FROM VENDOR ORDER BY VENDORID");
					while(result.next())
					{
						System.out.print("VENDORID NAME\t\tPHONE#\t\tADDRESS\n");
						System.out.println();
						System.out.println(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t\t"+result.getString(4)+"\n");
					}
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Enter the ID of the vendor who's record you want to update");
				vendorId = scan.nextInt();
				try
				{
					result = statement.executeQuery("SELECT * FROM VENDOR WHERE VENDORID ="+vendorId);
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateVendor(result);
				break;

			case 3:
				/*deletes a particular vendor’s records */

				System.out.println("Enter the ID of the vendor who's record you want to delete");
				vendorId = scan.nextInt();
				try
				{
					result = statement.executeQuery("SELECT * FROM VENDOR WHERE VENDORID ="+vendorId);
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deleteVendor(result);
				break;
			case 4:
				/* processes all the pending orders that are there in the pendingOrders t                                    	able */



				try
				{
					statement.executeUpdate("DELETE FROM PendingOrders WHERE status LIKE('Shipped')");
				}
				catch (SQLException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				processPending();
				break;

			case 5:
				/* replenishes the stock for a particular book in the warehouse */
				replenishWarehouse();
				break;

			case 6:
				/*to insert, update or delete records into the warehouseBooks inventory*/
				int local_op = 0;
				System.out.println("Enter an operation");
				System.out.println("1. Insert records into Warehouse Books");
				System.out.println("2. Update records in Warehouse Books");
				System.out.println("3. Delete records in Warehouse Books");
				local_op = scan.nextInt();
				switch(local_op)
				{
				case 1:
					createWarehouseBook();
					break;
				case 2:
					updateWarehouseBook();
					break;
				case 3:
					deleteWarehouseBook();
					break;
				default:
					System.out.println("Invalid Input");
					break;
				}
				break;
			case 7:
				/*  Generates the list of all the vendors with whom we have a contract   */
				try
				{
					result = statement.executeQuery("SELECT VENDOR.VENDORID, NAME, PHONENUMBER AS PHONE#, ADDRESS, MERCHANDISESUPPLIED FROM VENDOR,VENDORMERC WHERE VENDORMERC.VENDORID=VENDOR.VENDORID ORDER BY VENDORID");
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					while(result.next())
					{
						System.out.print("VENDORID NAME\t\tPHONE#\t\tADDRESS\t\tMERCHANDISE SUPPLIED");
						System.out.println();
						System.out.println(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t\t"+result.getString(4)+"\t\t"+result.getString(5)+"\n");
					}
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 8:
				/* Enter a new vendor with whom we want to make a contractual agreement */
				int book=0, vendorId = 0;
				ResultSet rs1= null;
				boolean newVendor = false;
				result = statement.executeQuery("SELECT ISBN FROM Book WHERE ISBN NOT IN(SELECT ISBN FROM VENDORMERC)");
				System.out.println("Enter an ISBN from the following list");
				while(result.next())
				{
					System.out.print(result.getString(1)+" | ");
				}
				System.out.println();
				book = scan.nextInt();
				System.out.println("Enter a vendorID");
				vendorId = scan.nextInt();
				rs1 = statement.executeQuery("SELECT VendorID FROM Vendor");
				while(rs1.next())
				{
					if(vendorId != rs1.getInt(1))
						newVendor = true;
					else
					{
						newVendor = false;
						break;
					}
				}
				if(newVendor)
				{
					System.out.println("This is a new vendor. Create a record first");
					createVendor(vendorId);
				}
				try
				{
					statement.executeUpdate("INSERT INTO VendorMerc VALUES("+book+", "+vendorId+")");
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Input");
				break;
			}

		}
	}

	public void createVendor(int vendorId)
	{

		/* function is called when a new vendor is to be added*/

		System.out.println("Enter the vendor's name");
		name = scan.next();
		System.out.println("Enter the vendor's phone number");
		phoneNumber = scan.nextLong();
		System.out.println("Enter the vendor's address");
		address = scan.next();
		createVendor(vendorId, name, phoneNumber, address);
	}        
	public void createVendor(int vendorId, String name, long phoneNumber, String address)
	{
		try
		{
			statement.executeQuery("INSERT INTO VENDOR VALUES("+vendorId+", '"+name+"', "+phoneNumber+", '"+address+"')");
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void updateVendor(ResultSet rs)
	{
		/* update a particular vendor’s details  which is retrieved in the resultset object rs*/
		int choice=0;
		try
		{
			while(rs.next())
			{
				System.out.print("VENDORID NAME\t\tPHONE#\t\tADDRESS\n");
				System.out.println();
				System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t\t"+rs.getString(4)+"\n");
				vendorId  = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Enter the field you want to update");
		System.out.println("1.NAME");
		System.out.println("2.PHONE#");
		System.out.println("3.ADDRESS");
		choice = scan.nextInt();
		switch(choice)
		{
		case 1: //updates name field of the vendor
			System.out.println("Enter the new name");
			name = scan.next();
			try
			{
				statement.executeUpdate("UPDATE VENDOR SET NAME ='"+name+"' WHERE VENDORID = "+vendorId);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;


		case 2: /* Updates the phone # of the vendor*/
			System.out.println("Enter the new phone#");
			phoneNumber = scan.nextLong();
			try
			{
				statement.executeUpdate("UPDATE VENDOR SET PHONENUMBER ='"+phoneNumber+"' WHERE VENDORID = "+vendorId);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:  
			/* updates the address of the vendor */
			System.out.println("Enter the new address");
			address = scan.next();
			try
			{
				statement.executeUpdate("UPDATE VENDOR SET ADDRESS ='"+address+"' WHERE VENDORID = "+vendorId);
			}
			catch (SQLException e)
			{
				//      	TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("Invalid Input");
			break;
		}

	}
	public void deleteVendor(ResultSet rs)
	{
		/* deletes the record of a particular vendor  */
		try
		{
			statement.executeUpdate("DELETE FROM VENDOR WHERE VENDORID = "+vendorId);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean checkWarehouse(int ISBN)
	{
		/* this function is used to check if the stock for a particulare ISBN is available in the warehouse */


		ResultSet rs = null;
		try
		{
			rs = statement.executeQuery("SELECT * FROM WarehouseBooks WHERE ISBN="+ISBN);
			if(rs.next())
				return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean checkWarehouse(int ISBN, int copies)
	{
		ResultSet rs = null;
		try
		{
			rs = statement.executeQuery("SELECT * FROM WarehouseBooks WHERE ISBN="+ISBN+" AND qtyInStock>="+copies);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			if(rs.next())
				return true;
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void processPending()
	{

		/* This function is called when the pending orders are to be processed.  */

		ResultSet rs1 = null, rs2 = null;
		String Date = null;
		int copies =0, vendorOrderId=0, stockerId = 0, customerOrderId=0, book =0, poid = 0;
		try
		{
			rs1 = statement.executeQuery("SELECT * FROM PendingOrders WHERE status LIKE('Pending')");
			while(rs1.next())
			{
				poid=rs1.getInt(1);
				System.out.println("Pending order being tracked is"+poid);
				customerOrderId = rs1.getInt(5);
				copies = 0;
				book=0;
				book=rs1.getInt(6);
				rs2 = statement.executeQuery("SELECT noOfCopiesRequested FROM CustomerOrders WHERE customerOrderId="+customerOrderId);
				while(rs2.next())
				{
					copies = rs2.getInt(1);
				}
				if(checkWarehouse(book, copies))
				{
					try
					{
						connection.setAutoCommit(false);
						statement.executeUpdate("UPDATE pendingOrders SET status ='Assigned', shippingStaffId =(select shippingStaffId from (select shippingStaffId from shippingstaff order by dbms_random.value) where rownum=1) WHERE pendingOrdersId = "+poid);
						statement.executeUpdate("UPDATE WarehouseBooks SET qtyInStock=qtyInStock-"+copies+" WHERE ISBN="+book);
						connection.commit();
						System.out.println("Order successfully assigned to shipping staff");
						connection.setAutoCommit(true);
					}
					catch(Exception e)
					{
						e.printStackTrace();
						try
						{
							System.out.println("Rolling back the failed transaction");
							connection.rollback();
						}
						catch(Exception e1)
						{
							e1.printStackTrace();
						}
					}

				}
				else
				{
					rs2 = null;
					rs2 = statement.executeQuery("SELECT vendorId FROM vendorMerc WHERE merchandiseSupplied ="+book);
					while(rs2.next())
					{
						vendorId = rs2.getInt(1);
					}
					rs2 = null;
					rs2 = statement.executeQuery("SELECT stockerId FROM MerchandiseStocker");
					while(rs2.next())
					{
						stockerId = rs2.getInt(1);
					}
					rs2 = null;
					rs2 = statement.executeQuery("SELECT * FROM VENDORORDERS ORDER BY VENDORORDERID");
					while(rs2.next())
					{
						System.out.println("VENDORORDERID\tSTOCKERID\tVENDORID\tISBN\tDATEORDERED\tNOOFCOPIES");
						System.out.print(rs2.getString(1)+"\t"+rs2.getString(2)+"\t"+rs2.getString(3)+"\t"+rs2.getString(4)+"\t"+rs2.getString(5)+"\t"+rs2.getString(6)+"\n");
					}
					System.out.println("Enter a unique Order ID");
					vendorOrderId = scan.nextInt();      
					System.out.println("Enter the date of the order(DD-MMM-YYYY)");
					Date=scan.next();
					statement.executeUpdate("INSERT INTO VendorOrders VALUES("+vendorOrderId+", "+stockerId+", "+vendorId+", "+book+", '"+Date+"', "+copies+")");
					statement.executeUpdate("UPDATE pendingOrders SET status='Ordered', vendorId ="+vendorId+" WHERE pendingOrdersId = "+poid);
					System.out.println("Since the requested book is not available the concerned vendor has been contacted");
				}
			}
			/*statement.executeUpdate("UPDATE pendingOrders SET status='Ordered', vendorId ="+vendorId+" WHERE pendingOrdersId = "+poid);
    System.out.println("Since the requested book is not available the concerned vendor has been contacted");*/
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void replenishWarehouse()
	{
		int pid = 0, cid = 0, book = 0, copies = 0;
		ResultSet rs1 = null, rs2 = null;
		try
		{
			rs1 = statement.executeQuery("SELECT * FROM pendingOrders WHERE STATUS='Ordered'"); //All records for which we placed an order with the vendor
			while(rs1.next())
			{
				pid = rs1.getInt(1);//to store the pending order id of the current record
				book = rs1.getInt(6);//to store the isbn of the current record
				cid= rs1.getInt(5);
				rs2 = statement.executeQuery("SELECT noOfCopiesRequested FROM customerOrders WHERE ISBN ="+book+" AND customerOrderID = "+cid);//selects all such tuples which have the same isbn
				while(rs2.next())//for one pending order it checks all vendor orders for that isbn
				{
					copies = rs2.getInt(1);//no of copies of the isbn(book) asked for in this order
					if(checkWarehouse(book))//if there was such a book in the warehouse before, but not enough copies to dispatch an order
					{
						statement.executeUpdate("UPDATE warehouseBooks SET qtyInStock= qtyInStock+"+copies+" WHERE ISBN ="+book);
					}
					else//if the book never existed in the warehouse when the order had arrived we need to create a record for it
					{
						statement.executeUpdate("INSERT INTO warehouseBooks VALUES("+book+", "+copies+")");
					}
				}
				statement.executeUpdate("UPDATE pendingOrders SET status ='Assigned', shippingStaffId =(select shippingStaffId from (select shippingStaffId from shippingstaff order by dbms_random.value) where rownum=1) WHERE pendingOrdersId = "+pid);          	       	 
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void createWarehouseBook()
	{
		String ISBN=null, qtyInStock=null;
		ResultSet rs1 = null;
		System.out.print("Enter ISBN form the folowing list\t");
		try
		{
			rs1=statement.executeQuery("SELECT Book.ISBN FROM Book WHERE Book.ISBN NOT IN(SELECT ISBN FROM WarehouseBooks)");
			while(rs1.next())
			{
				System.out.print(rs1.getString(1)+" | ");
			}
			System.out.println();
			ISBN = scan.next();
			System.out.println("Enter the quantity");
			qtyInStock = scan.next();
			statement.executeUpdate("INSERT INTO WarehouseBooks VALUES("+ISBN+", "+qtyInStock+")");
			System.out.println("The book was successfully added");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void updateWarehouseBook()
	{
		String ISBN=null, qtyInStock=null;
		ResultSet rs1 = null;
		System.out.print("Enter ISBN form the folowing list\t");
		try
		{
			rs1=statement.executeQuery("SELECT ISBN FROM WarehouseBooks");
			while(rs1.next())
			{
				System.out.print(rs1.getString(1)+" | ");
			}
			System.out.println();
			ISBN = scan.next();
			System.out.println("Enter the new quantity");
			qtyInStock = scan.next();
			statement.executeUpdate("UPDATE WarehouseBooks SET QTYINSTOCK="+qtyInStock+" WHERE ISBN="+ISBN);
			System.out.println("Updation Successful");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void deleteWarehouseBook()
	{
		String ISBN=null;
		ResultSet rs1 = null;
		System.out.print("Enter ISBN form the folowing list\t");
		try
		{
			rs1=statement.executeQuery("SELECT ISBN FROM WarehouseBooks");
			while(rs1.next())
			{
				System.out.print(rs1.getString(1)+" | ");
			}
			System.out.println();
			ISBN = scan.next();
			statement.executeUpdate("DELETE FROM WarehouseBooks WHERE ISBN ="+ISBN);
			System.out.println("The book was successfully deleted");
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}