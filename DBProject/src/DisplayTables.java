/*This program is to display all the tables in the database. It has simple select queries
 * to do the same */

/*import statements*/
import java.sql.*;	
import java.util.*;

/*public class DisplayTables*/
public class DisplayTables 
{

	private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";

	/*Enter Username and Password here*/
	private static final String username = "system"; 
	private static final String pwd = "varun";


	private Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;
	Scanner scan = new Scanner(System.in);
	
	/* Main Class*/
	public static void main(String[] args) {
		DisplayTables dsp = new DisplayTables();
		dsp.menuCall();

	}
	
	/*Constructor for Display Tables*/
	public DisplayTables()
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
	
	/* Menu Call Method. This would display the users a list to choose from for viewing the tables */
	private void menuCall() 
	{
		boolean quit=true;
		int operation;
		
		if (quit)
		{
			System.out.println("Welcome Saar!");
			System.out.println("Please select the Table that you want to display");
			System.out.println("1. Staff ");
			System.out.println("2. Salesperson");
			System.out.println("3. Manager");
			System.out.println("4. MerchandiseStocker");
			System.out.println("5. Shipping Staff");
			System.out.println("6. Billing Staff");
			System.out.println("7. Customer");
			System.out.println("8. Vendor");
			System.out.println("9. VendorMerc");
			System.out.println("10. Warehouse");
			System.out.println("11. Book");
			System.out.println("12. CustomerOrders");
			System.out.println("13. VendorOrders");
			System.out.println("14. PendingOrders");
			System.out.println("15. GenCustBill");
			System.out.println("16. GenVendBill");
			System.out.println("17. WarehouseBooks");
			System.out.println("18. StorBooks");
			System.out.println("19. Stores");
			operation=scan.nextInt(); /*Take Input from the User*/
			switch(operation)
			{
			/* Select all entries from Staff Table*/
			case 1:
					try 
					{
						result = statement.executeQuery("Select * from Staff");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getInt(3)+"\t"+result.getString(4)+"\t"+result.getInt(5)+"\t"+result.getString(6)+"\t"+result.getString(7)+"\t"+result.getLong(8)+"\t"+result.getString(9)+"\t"+result.getInt(10)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					/* Select all entries from Salesperson Table*/
			case 2:
					try 
					{
						result = statement.executeQuery("Select * from Salesperson");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Manager Table*/
				case 3:
					try 
					{
						result = statement.executeQuery("Select * from Manager");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Merchandise Stocker Table*/
				case 4:
					try 
					{
						result = statement.executeQuery("Select * from MerchandiseStocker");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from ShippingStaff Table*/
				case 5:
					try 
					{
						result = statement.executeQuery("Select * from ShippingStaff");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from BillingStaff Table*/
				case 6:
					try 
					{
						result = statement.executeQuery("Select * from BillingStaff");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
				break;
				
				/* Select all entries from Customer Table*/
				case 7:
					try 
					{
						result = statement.executeQuery("Select * from Customer");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getInt(3)+"\t"+result.getString(4)+"\t"+result.getString(5)+"\t"+result.getInt(6)+"\t"+result.getLong(7)+"\t"+result.getString(8)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Vendor Table*/
				case 8:
					try 
					{
						result = statement.executeQuery("Select * from Vendor");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getLong(3)+"\t"+result.getString(4)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from VendorMerc Table*/
				case 9:
					try 
					{
						result = statement.executeQuery("Select * from VendorMerc");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from WareHouse Table*/
				case 10:
					try 
					{
						result = statement.executeQuery("Select * from Warehouse");
						while(result.next())
						{
							System.out.print(result.getString(1)+"\t"+result.getString(2));
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Book Table*/
				case 11:
					try 
					{
						result = statement.executeQuery("Select * from Book");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t"+result.getInt(4)+"\t"+result.getInt(5)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from CustomerOrders Table*/
				case 12:
					try 
					{
						result = statement.executeQuery("Select * from CustomerOrders");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getInt(4)+"\t"+result.getString(5)+"\t"+result.getInt(6)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from VendorOrders Table*/
				case 13:
					try 
					{
						result = statement.executeQuery("Select * from VendorOrders");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getInt(4)+"\t"+result.getString(5)+"\t"+result.getInt(6)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from PendingOrders Table*/
				case 14:
					try 
					{
						result = statement.executeQuery("Select * from PendingOrders");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getInt(4)+"\t"+result.getInt(5)+"\t"+result.getInt(6)+"\t"+result.getString(7)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Generate Customer Bill Table*/
				case 15:
					try 
					{
						result = statement.executeQuery("Select * from GenCustBill");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getString(4)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Generate Vendor Bill Table*/
				case 16:
					try 
					{
						result = statement.executeQuery("Select * from GenVendBill");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t"+result.getInt(3)+"\t"+result.getString(4)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from WarehouseBooks Table*/
				case 17:
					try 
					{
						result = statement.executeQuery("Select * from WareHouseBooks");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from StoreBooks Table*/
				case 18:
					try 
					{
						result = statement.executeQuery("Select * from StoreBooks");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getInt(2)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
					
					/* Select all entries from Stores Table*/
				case 19:
					try 
					{
						result = statement.executeQuery("Select * from Stores");
						while(result.next())
						{
							System.out.print(result.getInt(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t");
							System.out.println();
						}
					} 
					catch (SQLException e) 
					{
						
						e.printStackTrace();
					}
					break;
			}
			
		}
	}

}
