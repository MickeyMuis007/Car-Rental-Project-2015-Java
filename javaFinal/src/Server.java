import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.io.*;
import java.util.*;


public class Server {
    
    private ServerSocket listener;
    private Socket client;
    
    private Connection conn;
    private Statement state;
    private ResultSet results;
    private PreparedStatement pmst;
    private String sql;
    
    private ObjectInputStream inputCustomer;
    private ObjectInputStream inputRental;
    private ObjectInputStream inputVehicle;
    private ObjectOutputStream output;
    
    private ArrayList<Customer> customerList;
    private ArrayList<Rental> rentalList;
    private ArrayList<Vehicle> vehicleList;
    private Customer cust;
    private Rental rent;
    private Rent rental;
    private Vehicle vehi;
    public Server()
    {
        // Create server socket
        try {
	// STEP 1 b
            listener = new ServerSocket(12345, 10);
            conn =DriverManager.getConnection("jdbc:ucanaccess://CarRental.accdb");
            
            inputCustomer = new ObjectInputStream(new FileInputStream("customer.ser"));
            inputRental = new ObjectInputStream(new FileInputStream("rental.ser"));
            inputVehicle = new ObjectInputStream(new FileInputStream("vehicle.ser"));
            
            customerList = new ArrayList<Customer>();
            rentalList = new ArrayList<Rental>();
            vehicleList = new ArrayList<Vehicle>();
            
            cust = new Customer();
            rent = new Rental();
            vehi = new Vehicle();
        }catch(Exception e)
        {
          System.out.println("Exception: " + e.getMessage());
        }
    }
    
    public void readFileToCustomerList() throws IOException
    {
        try
        {
            while(true)
            {
                    customerList.add((Customer)inputCustomer.readObject());
            }
        }catch(ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundExeption: " + e.getMessage());
            
        }catch(EOFException ioe)
        {
            System.out.println("End of Customer file");
        }
    }
    public void readFileToRentalList() throws IOException
    {
        try
        {
            while(true)
            {
                    rentalList.add((Rental)inputRental.readObject());
            }
        }catch(ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundExeption: " + e.getMessage());
            
        }catch(EOFException ioe)
        {
            System.out.println("End of Rental file");
        }
    }
    public void readFileToVehicleList() throws IOException
    {
        try
        {
            while(true)
            {
                    vehicleList.add((Vehicle)inputVehicle.readObject());
            }
        }catch(ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundExeption: " + e.getMessage());
            
        }catch(EOFException ioe)
        {
            System.out.println("End of Vehicle file");
        }
    }
    public void readFileToArrayList() throws IOException
    {
        readFileToCustomerList();
        readFileToRentalList();
        readFileToVehicleList();
    }
    public void readToCustomerTable()
    {
        try
        {
            //"Update Customer set customerNumber = ?, customerName = ?, customerLastName = ?, rentable = ? where customerNumber = 1"
            //"INSERT into Table2 (user, pass) VALUES (?, ?)"
            for(int i = 0; i < customerList.size();i++)
            {
                sql = "INSERT into Customer (customerNumber,customerName,customerLastName,customerAddress,rentable) VALUES (?,?,?,?,?)";
                pmst = conn.prepareStatement(sql);
                pmst.setInt(1, customerList.get(i).getCustNumber());
                pmst.setString(2, customerList.get(i).getFirstName());
                pmst.setString(3, customerList.get(i).getSurName());
                pmst.setString(4, customerList.get(i).getAddress());
                pmst.setBoolean(5, customerList.get(i).getCanRent());
                pmst.executeUpdate();
                
                
               // System.out.println(customerList.get(i).toString());
            }
            
        }catch(SQLException e)
        {
            System.out.println("SQLExcpetion(For readToCustomer): " + e);
        }
    }
    
    public void readToRentalTable()
    {
        try
        {
            //"Update Customer set customerNumber = ?, customerName = ?, customerLastName = ?, rentable = ? where customerNumber = 1"
            //"INSERT into Table2 (user, pass) VALUES (?, ?)"
            for(int i = 0; i < rentalList.size();i++)
            {
                sql = "INSERT into Rental(rentalNumber,dateRental,dateReturned,pricePerDay) VALUES (?,?,?,?)";
                pmst = conn.prepareStatement(sql);
                pmst.setInt(1, rentalList.get(i).getRentalNumber());
                pmst.setString(2, rentalList.get(i).getDateRented());
                pmst.setString(3, rentalList.get(i).getDateReturned());
                pmst.setDouble(4, rentalList.get(i).getPricePerDay());
                
                pmst.executeUpdate();
                
                
               // System.out.println(customerList.get(i).toString());
            }
            
        }catch(SQLException e)
        {
            System.out.println("SQLExcpetion(For readToCustomer): " + e);
        }
    }
    
     public void readToVehicleTable()
    {
        try
        {
            //"Update Customer set customerNumber = ?, customerName = ?, customerLastName = ?, rentable = ? where customerNumber = 1"
            //"INSERT into Table2 (user, pass) VALUES (?, ?)"
            for(int i = 0; i < vehicleList.size();i++)
            {
                sql = "INSERT into Vehicle (vehicleNumber,vehicleMake,vehicleCatergory, VehicleRentalPrice) VALUES (?,?,?,?)";
                pmst = conn.prepareStatement(sql);
                pmst.setInt(1, vehicleList.get(i).getVehNumber());
                pmst.setString(2, vehicleList.get(i).getMake());
                pmst.setString(3, vehicleList.get(i).getCategory());
                pmst.setDouble(4, vehicleList.get(i).getRentalPrice());
                
                pmst.executeUpdate();
                
                
               // System.out.println(customerList.get(i).toString());
            }
            
        }catch(SQLException e)
        {
            System.out.println("SQLExcpetion(For readToCustomer): " + e);
        }
    }
    public void readFilesToDB()
    {
        try
        {
            readFileToArrayList();
            readToCustomerTable();
            readToRentalTable();
            readToVehicleTable();
        }catch(Exception e)
        {
            System.out.println("Exception: "+ e.getMessage());
        }
    }
    public void processClient()
    {
        // Communicate with the client
        String msg = "";
        Customer cust;
        do
        {
            try {
                System.out.println("Server Listening.......");
                client = listener.accept();

                    //  STEP 3 a
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.flush();

                    // STEP 3 b
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());

                    // STEP 4 PROCESSING PHASE
                

                do
                {

                    msg = (String)in.readObject();                    
                    System.out.println("From the CLIENT>> " + msg);
                    
                    //Customer
                    if(msg.equalsIgnoreCase("Insert Customer"))
                    {
                        try
                        {
                            cust = (Customer)in.readObject();
                            sql = "INSERT into Customer (customerNumber,customerName,customerLastName,customerAddress,rentable) VALUES (?,?,?,?,?)";
                            pmst = conn.prepareStatement(sql);
                            pmst.setInt(1, cust.getCustNumber());
                            pmst.setString(2, cust.getFirstName());
                            pmst.setString(3, cust.getSurName());
                            pmst.setString(4, cust.getAddress());
                            pmst.setBoolean(5, cust.getCanRent());
                            pmst.executeUpdate();
                            out.writeObject("Data inserted successful");
                            out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException(customer:) " + e.getMessage());
                            out.writeObject("Data inserted not successful");
                            out.flush();
                        }
                    }
                    else if(msg.equalsIgnoreCase("Edit Customer"))
                    {
                        try
                        {
                            cust = (Customer)in.readObject();
                            sql = "Update Customer set customerName = ?,customerLastName = ?,customerAddress = ? ,rentable = ? where customerNumber = ?";
                            pmst = conn.prepareStatement(sql);
                            pmst.setString(1, cust.getFirstName());
                            pmst.setString(2, cust.getSurName());
                            pmst.setString(3, cust.getAddress());
                            pmst.setBoolean(4, cust.getCanRent());
                            pmst.setInt(5, cust.getCustNumber());
                            pmst.executeUpdate();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException(customer:) " + e.getMessage());
                        }
                        out.writeObject("Data edited successful");
                        out.flush();
                    }
                    else if(msg.equalsIgnoreCase("Delete Customer"))
                    {
                        try
                        {
                            msg = (String)in.readObject();
                            sql = "Delete from Customer where customerNumber = ?";
                            pmst = conn.prepareStatement(sql);
                            pmst.setInt(1, Integer.parseInt(msg));
                            System.out.println("CLIENT Data to edit>> " + msg);                            
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException(customer:) " + e.getMessage());
                        }
                        out.writeObject("Data Deleted successful");
                        out.flush();
                    }
                    else if(msg.equalsIgnoreCase("Display Customers"))
                    {
                     
                        try
                        {
                            state = conn.createStatement();
                            results = state.executeQuery("Select * from Customer");
                        while(results.next())
                        {
                            out.writeObject(results.getInt("CustomerNumber")+"");
                            out.writeObject(results.getString("CustomerName")+"");
                            out.writeObject(results.getString("CustomerLastName")+"");
                            out.writeObject(results.getString("CustomerAddress")+"");
                            out.writeObject(results.getBoolean("rentable")+"");
                        }
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Customer Display successful");
                        //msg = (String)in.readObject();
                        //out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                        }
                    }
                    
                    //Rental
                    else if(msg.equalsIgnoreCase("Insert Rental"))
                    {
                        try
                        {
                        rental = (Rent)in.readObject();
                        sql = "INSERT into Rental (RentalNumber,DateRental,PricePerDay,CustomerNumber,VehicleNumber,dateReturned) VALUES (?,?,?,?,?,'NA')";               
                        pmst = conn.prepareStatement(sql);
                        pmst.setInt(1, rental.getRentalNumber());
                        pmst.setString(2, rental.getDateRent());
                        pmst.setDouble(3, rental.getPricePerDay());
                        pmst.setInt(4, rental.getCustomerNumber());
                        pmst.setInt(5, rental.getVehicleNumber());
                        pmst.executeUpdate();
                        sql = "Update Customer set rentable = ? where CustomerNumber = ?";
                        pmst = conn.prepareStatement(sql);
                        pmst.setBoolean(1, true);
                        pmst.setInt(2, rental.getCustomerNumber());
                        pmst.executeUpdate();
                        sql = "Update Vehicle set VehicleAvailability = ? where VehicleNumber = ?";
                        pmst = conn.prepareStatement(sql);
                        pmst.setBoolean(1, true);
                        pmst.setInt(2, rental.getVehicleNumber());
                        pmst.executeUpdate();
                        System.out.println("CLIENT Data to insert>> " + msg);
                        out.writeObject("Data inserted successful");
                        out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException(Rental:) " + e.getMessage());
                            out.writeObject("Data inserted not successful");
                            out.flush();
                        }
                    }
                    else if(msg.equalsIgnoreCase("Edit Rental"))
                    {
                        msg = (String)in.readObject();
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Data edited successful");
                        out.flush();
                    }
                    else if(msg.equalsIgnoreCase("Delete Rental"))
                    {
                        msg = (String)in.readObject();
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Data Deleted successful");
                        out.flush();
                    }
                    else if(msg.equalsIgnoreCase("Display Rental"))
                    { 
                        try
                        {
                            state = conn.createStatement();
                            results = state.executeQuery("Select * from Rental");
                        while(results.next())
                        {
                            out.writeObject(results.getInt("RentalNumber")+"");
                            out.writeObject(results.getString("DateRental")+"");
                            out.writeObject(results.getDouble("PricePerDay")+"");
                        }
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Rental Display successful");

                        //msg = (String)in.readObject();
                        //out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                        }
                    }
                    else if(msg.equalsIgnoreCase("Display Customers Rentals"))
                    {
                     
                        try
                        {
                            state = conn.createStatement();
                            results = state.executeQuery("Select * from Customer where rentable = false");
                        while(results.next())
                        {
                            out.writeObject(results.getInt("CustomerNumber")+"");
                            out.writeObject(results.getString("CustomerName")+"");
                            out.writeObject(results.getString("CustomerLastName")+"");
                            out.writeObject(results.getString("CustomerAddress")+"");
                            out.writeObject(results.getBoolean("rentable")+"");
                        }
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Customer Display successful");
                        //msg = (String)in.readObject();
                        //out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                        }
                    }
                    else if(msg.equalsIgnoreCase("Display Vehicle Rentals"))
                    {
                        try
                        {
                            state = conn.createStatement();
                            results = state.executeQuery("Select * from Vehicle where VehicleAvailability = false order by VehicleCatergory");
                        while(results.next())
                        {
                            out.writeObject(results.getInt("VehicleNumber")+"");
                            out.writeObject(results.getString("VehicleMake")+"");
                            out.writeObject(results.getString("VehicleCatergory")+"");
                            out.writeObject(results.getDouble("VehicleRentalPrice")+"");
                            out.writeObject(results.getBoolean("VehicleAvailability")+"");
                        }
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Vehicle Display successful");

                        //msg = (String)in.readObject();
                        //out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                        }
                    }
                    //Vehicle
                    else if(msg.equalsIgnoreCase("Insert Vehicle"))
                    {
                        try
                        {
                        vehi = (Vehicle)in.readObject();
                        sql = "INSERT into Vehicle (VehicleNumber, VehicleMake, VehicleCatergory,VehicleRentalPrice,VehicleAvailability) VALUES (?,?,?,?,?)";
                            pmst = conn.prepareStatement(sql);
                            pmst.setInt(1, vehi.getVehNumber());
                            pmst.setString(2, vehi.getMake());
                            pmst.setString(3, vehi.getCategory());
                            pmst.setDouble(4, vehi.getRentalPrice());
                            pmst.setBoolean(5, vehi.isAvailableForRent());
                            pmst.executeUpdate();
                            out.writeObject("Data inserted successful");
                            out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException(vehicle) " + e.getMessage());
                            out.writeObject("Data inserted not successful");
                            out.flush();
                        }
                        
                    }
                    else if(msg.equalsIgnoreCase("Edit Vehicle"))
                    {
                        try
                        {
                            vehi = (Vehicle)in.readObject();
                            sql = "Update Vehicle set vehicleMake = ?, vehicleCatergory = ?, VehicleRentalPrice = ?, VehicleAvailability = ? where VehicleNumber = ?";
                            pmst = conn.prepareStatement(sql);
                            pmst.setString(1, vehi.getMake());
                            pmst.setString(2, vehi.getCategory());
                            pmst.setDouble(3, vehi.getRentalPrice());
                            pmst.setBoolean(4, vehi.isAvailableForRent());
                            pmst.setInt(5,vehi.getVehNumber());
                            pmst.executeUpdate();
                            out.writeObject("Data edited successful");
                            out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException(vehicle) " + e.getMessage());
                            out.writeObject("Data edit not successful");
                            out.flush();
                        }
                        
                    }
                    else if(msg.equalsIgnoreCase("Delete Vehicle"))
                    {
                        msg = (String)in.readObject();
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Data Deleted successful");
                        out.flush();
                    }
                    else if(msg.equalsIgnoreCase("Display Vehicle"))
                    {
                        try
                        {
                            state = conn.createStatement();
                            results = state.executeQuery("Select * from Vehicle order by VehicleCatergory");
                        while(results.next())
                        {
                            out.writeObject(results.getInt("VehicleNumber")+"");
                            out.writeObject(results.getString("VehicleMake")+"");
                            out.writeObject(results.getString("VehicleCatergory")+"");
                            out.writeObject(results.getDouble("VehicleRentalPrice")+"");
                            out.writeObject(results.getBoolean("VehicleAvailability")+"");
                        }
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Vehicle Display successful");

                        //msg = (String)in.readObject();
                        //out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                        }
                    }
                    else if(msg.equalsIgnoreCase("Display Return"))
                    { 
                        try
                        {
                            state = conn.createStatement();
                            results = state.executeQuery("Select * from Rental");
                        while(results.next())
                        {
                            out.writeObject("Return Display not successful");
                            Rental r = new Rental (results.getInt("RentalNumber"),results.getString("DateRental"),results.getString("dateReturned"), results.getDouble("PricePerDay"),results.getDouble("TotalRental"),0,0);
                            Rent rv = new Rent(results.getInt("CustomerNumber"),results.getInt("VehicleNumber"));
                            out.writeObject(r);
                            out.writeObject(rv);
                            
                            
                        }
                        System.out.println("CLIENT Data to edit>> " + msg);
                        out.writeObject("Return Display successful");

                        //msg = (String)in.readObject();
                        //out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                        }
                    }
                    else if(msg.equalsIgnoreCase("Edit Return"))
                    {
                        msg = (String)in.readObject();
                        System.out.println("CLIENT Data to edit>> " + msg);
                        try
                        {
                            sql = "Update Rental set dateReturned = ? where rentalNumber = ?";
                            pmst = conn.prepareStatement(sql);
                            pmst.setString(1, msg);
                            msg = (String)in.readObject();
                            pmst.setInt(2, Integer.parseInt(msg));
                            pmst.executeUpdate();
                            out.writeObject("Data edited successful");
                            out.flush();
                        }catch(SQLException e)
                        {
                            System.out.println("SQLException: " + e.getMessage());
                            out.writeObject("Data edited not successful");
                            out.flush();
                        }
                    }
                }while(!msg.equalsIgnoreCase("Stop") && !msg.equalsIgnoreCase("Closed"));

                // STEP 5 CLOSE CONNECTION
                System.out.println("Server Listening closing.......");
                    
                client.close();
                out.close();
                in.close();

            }
            catch (IOException ioe)
            {
                System.out.println("IO Exception Server: " + ioe.getMessage());
            }
            catch (ClassNotFoundException cnfe)
            {
                System.out.println("Class not found: " + cnfe.getMessage());
            }
        }while(!msg.equalsIgnoreCase("Closed"));
        System.out.println("Shutting server down.....");
        
    }
    
    public static void main(String[] args)
    {
        // Create application
        Server server = new Server();
        server.readFilesToDB();
        // Start waiting for connections
        server.processClient();
    }
        
}
