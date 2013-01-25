/*This program is to display all the functionalities in the manager class. */

/*import statements*/
import java.sql.*;
import java.util.*;

/* Public Class Manager*/
public class Manager
{

    private static final String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";

    /*Enter Username and Password here*/
    private static final String username = "system";
    private static final String pwd = "varun";


    private Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;
    Scanner scan = new Scanner(System.in);
   
    /*variable decarations*/
    char gender;
    int staffId, age, storeId;
    long phoneNumber;
    long salary;
    String name, jobTitle, department, address;
   
    /* Main Class*/
    public static void main(String[] args)
    {
        Manager mgr = new Manager();
        mgr.menuCall();
    }
    
    /*Constructor for Manager Class */
    public Manager()
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
    
    /* Menu Call Method. This would display the users a list to choose from for performing various operations for the Manager Class */
    public void menuCall()
    {
        boolean quit=true;
        int operation;
       
        while(quit)
        {
            System.out.println("Welcome Saar!");
            System.out.println("Enter the operation that you wish to perform");
            System.out.println("1. Insert a record for a new staff member");
            System.out.println("2. Update an existing staff member's record");
            System.out.println("3. Delete an existing staff member's record");
            System.out.println("4. Generate the report for all the customers assisted by a particular salesperson for a given time period");
            System.out.println("5. Generate a report of the staff members of Books-a-Thousand grouped by their role");
            System.out.println("6. Quit");
            operation=scan.nextInt();
            switch(operation)
            {
            /* Case 1:Insert a record for a new staff member*/
                case 1:
                        System.out.println("Enter a staff id");
                        staffId = scan.nextInt();
                        System.out.println("Enter the name");
                        name = scan.next();
                        System.out.println("Enter the age");
                        age = scan.nextInt();
                        System.out.println("Enter the gender");
                        gender = ((scan.next()).toCharArray())[0];
                        System.out.println("Enter the salary");
                        salary = scan.nextLong();
                        System.out.println("Enter the job title");
                        jobTitle = scan.next();
                        System.out.println("Enter the department");
                        department = scan.next();
                        System.out.println("Enter the phone number");
                        phoneNumber = scan.nextLong();
                        System.out.println("Enter address");
                        address = scan.next();
                        System.out.println("Enter a store id");
                        storeId = scan.nextInt();
                        createStaff(staffId, name, age, gender, salary, jobTitle, department, phoneNumber, address, storeId);
                        break;
                        
                        
                        /* Case 2:  Update an existing staff member's record  */
                case 2:
                        System.out.println("Enter the id of the staff member who's record you want to update");
                        staffId = scan.nextInt();
                        try
                        {
                            result = statement.executeQuery("SELECT * FROM Staff WHERE StaffId ="+staffId);
                        }
                        catch (SQLException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        updateStaff(result);
                        break;
                        
                        /* Case 3: Delete a Staff Member*/
                case 3:
                        System.out.println("Enter the id of the staff member who's record you want to delete");
                        staffId = scan.nextInt();
                        deleteStaff(staffId);
                        break;
                        
                        
                 /* Case 4: Generate the report for all the customers assisted by a particular salesperson for a given time period */
                case 4:
                        String startDate , endDate;
                        System.out.println("Enter the id of the staff member who's purchase usage history");
                        staffId = scan.nextInt();
                        System.out.print("Enter the start date of the duration");
                        startDate= scan.next();
                        System.out.print("Enter the end date of the duration");
                        endDate = scan.next();
                        genCustInfo(staffId, startDate, endDate);
                        break;
                        
                 /* Case 5: Generate a report of the staff members of Books-a-Thousand grouped by their role*/
                case 5:
                        genInfoBasedOnRole();
                        break;
                        
                 /* Case 6: Quit */       
                case 6:
                        System.exit(0);
                        break;
                        
                        /* Default Case */
                default:
                        System.out.println("Invalid Entry");
                        break;               
           
            }
        }
    }
    
    /* Generate the report for all the customers assisted by a particular salesperson for a given time period */
    private void genCustInfo(int staffId, String df, String dt)
    {
        try
        {
            result = statement.executeQuery("SELECT CUSTOMER.CUSTOMERID, NAME, CUSTOMERORDERID AS ORDERID, ISBN, DATEORDERED, NOOFCOPIESREQUESTED AS COPIES FROM CUSTOMER,CUSTOMERORDERS WHERE CUSTOMER.CUSTOMERID=CUSTOMERORDERS.CUSTOMERID AND SALESPERSONID="+staffId+" AND DATEORDERED BETWEEN'"+df+"' AND '"+dt+"'");
            while(result.next())
            {
                System.out.print(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4)+"\t"+result.getString(5)+"\t"+result.getString(6)+"\t");
                System.out.println();
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    
    /* Method to Create a New Staff Member by the Manager */
    public void createStaff(int staffId, String name, int age, char gender, long salary2, String jobTitle, String department, long phoneNumber2, String address, int storeId)
    {
        try
        {   
            connection.setAutoCommit(false);
            statement.executeUpdate("INSERT INTO Staff VALUES ('"+staffId+"', '"+name+"', '"+age+"', '"+gender+"', '"+salary2+"', '"+jobTitle+"', '"+department+"', '"+phoneNumber2+"', '"+address+"', '"+storeId+"')");
            if(jobTitle.equals("salesperson"))
            {   
                statement.executeUpdate("INSERT INTO salesperson values("+staffId+", "+storeId+")");
                connection.commit();
                System.out.println("Commit successful salesperson");
                connection.setAutoCommit(true);
            }
            else if(jobTitle.equals("manager"))
            {
                statement.executeUpdate("INSERT INTO manager values("+staffId+")");
                connection.commit();
                System.out.println("Commit successful into manager");
                connection.setAutoCommit(true);
            }
            else if(jobTitle.equals("shippingstaff"))
            {
                statement.executeUpdate("INSERT INTO shippingstaff values("+staffId+")");
                connection.commit();
                System.out.println("Commit successful shipping");
                connection.setAutoCommit(true);
            }
            else if(jobTitle.equals("billingstaff"))
            {
                statement.executeUpdate("INSERT INTO billingstaff values("+staffId+")");
                connection.commit();
                System.out.println("Commit successful billing");
                connection.setAutoCommit(true);
            }
            else if(jobTitle.equals("merchandisestocker"))
            {
                statement.executeUpdate("INSERT INTO merchandisestocker values("+staffId+")");
                connection.commit();
                System.out.println("Commit successful stocker");
                connection.setAutoCommit(true);
            }
            else
            {
                throw new SQLException("Invalid Job Title");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            try{
                System.out.println("Rolling back the failed transaction");
                connection.rollback();
                    }
                catch(Exception Ex1)
                {
                    Ex1.printStackTrace();
                }
        }
       
    }
    
    /* Method to Update a Staff Member based on the Manager's Choice */
    public void updateStaff(ResultSet rs)
    {
        try
        {
            while(rs.next())
            {
                int choice;
               
                System.out.println("Current record");
                System.out.print(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10));
                System.out.println();
                staffId = rs.getInt(1);
               
                System.out.println("Enter the column index that you wish to update");
                System.out.println("1. Name");
                System.out.println("2. Age");
                System.out.println("3. Gender");
                System.out.println("4. Salary");
                System.out.println("5. Phone Number");
                System.out.println("6. Address");
                System.out.println("7. Store ID");
                choice = scan.nextInt();
               
                switch(choice)
                {
                    case 1:
                            System.out.println("Enter the name");
                            name = scan.next();
                            statement.executeUpdate("UPDATE Staff SET NAME='"+name+"' WHERE STAFFID="+staffId);
                            break;
                    case 2:
                            System.out.println("Enter the age");
                            age = scan.nextInt();
                            statement.executeUpdate("UPDATE Staff SET AGE='"+age+"' WHERE STAFFID="+staffId);
                            break;
                    case 3:
                            System.out.println("Enter the gender");
                            gender = ((scan.next()).toCharArray())[0];
                            statement.executeUpdate("UPDATE Staff SET GENDER='"+gender+"' WHERE STAFFID="+staffId);
                            break;
                    case 4:
                            System.out.println("Enter the salary");
                            salary = scan.nextInt();
                            statement.executeUpdate("UPDATE Staff SET SALARY='"+salary+"' WHERE STAFFID="+staffId);
                            break;
                    case 5:
                            System.out.println("Enter the phone number");
                            phoneNumber = scan.nextLong();
                            statement.executeUpdate("UPDATE Staff SET PHONENUMBER='"+phoneNumber+"' WHERE STAFFID="+staffId);
                            break;
                    case 6:
                            System.out.println("Enter address");
                            address = scan.next();
                            statement.executeUpdate("UPDATE Staff SET ADDRESS='"+address+"' WHERE STAFFID="+staffId);
                            break;
                    case 7:
                            System.out.println("Enter a store id");
                            storeId = scan.nextInt();
                            statement.executeUpdate("UPDATE Staff SET STOREID='"+storeId+"' WHERE STAFFID="+staffId);
                            break;
                    default:
                            System.out.println("Invalid Entry");
                            break;                       
                }
                rs = statement.executeQuery("SELECT * FROM Staff WHERE StaffId ="+staffId);
                while(rs.next())
                {
                    System.out.print(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7)+" "+rs.getString(8)+" "+rs.getString(9)+" "+rs.getString(10));
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
    
    /* Method to delete a Staff Table */
    public void deleteStaff(int staffId)
    {
        try
        {
            statement.executeUpdate("DELETE FROM Staff WHERE STAFFID="+staffId);
            System.out.println("The record was deleted successfully");
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /* Method to get Staff Report Based on their Role */
    public void genInfoBasedOnRole()
    {
        try
        {
            result = statement.executeQuery("select * from staff where department in(select distinct department from staff)");
            System.out.println("STAFFID\tNAME\tAGE\tGENDER\tSALARY\tJOB TITLE\tDEPARTMENT\tPHONE NUMBER\tADDRESS\tSTORE ID");
            while(result.next())
            {
                System.out.print(result.getString(1)+"\t"+result.getString(2)+"\t"+result.getString(3)+"\t"+result.getString(4)+"\t"+result.getString(5)+"\t"+result.getString(6)+"\t"+result.getString(7)+"\t"+result.getString(8)+"\t"+result.getString(9)+"\t"+result.getString(10));
                System.out.println();
            }
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

