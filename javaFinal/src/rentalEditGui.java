
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class rentalEditGui extends javax.swing.JFrame {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;
    private String response;
    
    private int rentalNumber;
    private String dateRental;
    private float pricePerDay;
    private int customerNumber;
    private int vehicleNumber;
    
    private boolean rentalNumberChecked;
    private boolean dateRentalChecked;
    private boolean pricePerDayChecked;
    private boolean customerNumberChecked;
    private boolean vehicleNumberChecked;
    
    private boolean editOption;
        /**
     * Creates new form rentalEditGui
     */
    public rentalEditGui() {
        initComponents();
        editOption = false;
        try
	{
            // STEP 1 b
            // Create socket TO SERVER
            server = new Socket("127.0.0.1", 12345);
            
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
        }

    }
    public rentalEditGui(int rentalNumber,String dateRental, float pricePerDay, int customerNumber, int vehicleNumber) {
        initComponents();
        editOption = true;
        try
	{
            // STEP 1 b
            // Create socket TO SERVER
            server = new Socket("127.0.0.1", 12345);
            
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
        }
        
    }
    
    public void displayVehicleTable()
    {   
        DefaultTableModel model = (DefaultTableModel)jTableVehicle.getModel();
        model.setRowCount(0);
        int count = 0;
        int no = 0;
        String make = "",category = "";
        boolean confirm = false;
        float price = 0;
        sendData("Display Vehicle Rentals");
        System.out.println("Vehicle send");
        do
        {
            System.out.println("Vehicle send");
            receiveData();
            if(!response.equalsIgnoreCase("Vehicle Display successful"))
            {
                switch(count)
                {
                    case 0:
                        no = Integer.parseInt(response);
                        count++;
                        break;
                    case 1:
                        make = response;
                        count++;
                        break;
                    case 2:
                        category = response;
                        count++;
                        break;
                    case 3:
                        price = Float.parseFloat(response);
                        count++;
                        break;
                    case 4:
                        confirm = Boolean.parseBoolean(response);
                        model.addRow(new Object[]{no,make,category,price,confirm});
                        count = 0;
                        break;
                }
            }
        }while(!response.equalsIgnoreCase("Vehicle Display successful"));         
    }
     public void displayCustomerTable()
    {
            DefaultTableModel model = (DefaultTableModel)jTableCustomer.getModel();
            model.setRowCount(0);
            int count = 0;
            int no = 0;
            String name = "",surname = "",address = "";
            boolean confirm = false;
            sendData("Display Customers Rentals");
            do
            {
                receiveData();
                if(!response.equalsIgnoreCase("Customer Display successful"))
                {
                    switch(count)
                    {
                        case 0:
                            no = Integer.parseInt(response);
                            count++;
                            break;
                        case 1:
                            name = response;
                            count++;
                            break;
                        case 2:
                            surname = response;
                            count++;
                            break;
                        case 3:
                            address = response;
                            count++;
                            break;
                        case 4:
                            confirm = Boolean.parseBoolean(response);
                            model.addRow(new Object[]{no,name,surname,address,confirm});
                            count = 0;
                            break;
                    }
                }
            }while(!response.equalsIgnoreCase("Customer Display successful"));
            
    }
    
    public void receiveData()
    {
        try
        {
            System.out.println("Client listening for server......");
            response = (String)in.readObject();
            System.out.println("SERVER said>> " + response);
          
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        }
    }
    public void sendData(String msg)
    {
        // The connection has been established - now send/receive.
        
        try
        {
            out.writeObject(msg);
            out.flush();
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
    public void sendData(Rent msg)
    {
        // The connection has been established - now send/receive.
        
        try
        {
            out.writeObject(msg);
            out.flush();
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }
    private void sendRentalData()
    {
        setValues();
        if(rentalNumberChecked && dateRentalChecked && pricePerDayChecked && customerNumberChecked && vehicleNumberChecked)
        {   
            Rent rent = new Rent(rentalNumber,dateRental,pricePerDay,customerNumber,vehicleNumber);
            try
            {
                if(!editOption)
                {
                    sendData("Insert Rental");
                    sendData(rent);
                    receiveData();
                    if(response.equalsIgnoreCase("Data inserted successful"))
                        this.dispose();
                    else
                        JOptionPane.showMessageDialog(rootPane, "Primary key already exist");
                    
                }
                else
                {
                    sendData("Edit Rental");
                    sendData("Rental update data");
                    receiveData();                    
                    this.dispose();
                }
            }catch(Exception e)
            {
                System.out.println("Exception: " + e);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Not Ok");
        }
             
    }
    private void setValues()
    {
        jLabelRentalNumber.setText("");
        jLabelDateRental.setText("");
        jLabelVehicleNumber.setText("");
        jLabelCustomerNumber.setText("");
        jLabelPricePerDay.setText("");
        
        dateRentalChecked = true;
        try
        {
            rentalNumber = Integer.parseInt(jTextFieldRentalNumber.getText());
            rentalNumberChecked = true;
            dateRental = jFormattedTextFieldDateRental.getText();
            if(dateRental.length() < 10)
            {
                jLabelDateRental.setText("Error!!! date rental is empty");
                dateRentalChecked = false;
            }
            try
            {
                pricePerDay = Float.parseFloat(jTextFieldPricePerDay.getText());
                pricePerDayChecked = true;
                try
                {
                    customerNumber = Integer.parseInt(jTextFieldCustomerNumber.getText());
                    customerNumberChecked = true; 
                    try
                    {
                        vehicleNumber = Integer.parseInt(jTextFieldVehicleNumber.getText());
                        vehicleNumberChecked = true;
                    }catch(Exception e)
                    {
                        jLabelVehicleNumber.setText("Error!!! Invalid data entered for Vehicle number");
                        vehicleNumberChecked = false;
                    }
                }catch(Exception e)
                {
                    jLabelCustomerNumber.setText("Error!!! Invalid data entered for Customer number");
                    customerNumberChecked = false; 
                }
            }catch(Exception e)
            {
                 jLabelPricePerDay.setText("Error!!! Invalid data entered for price per day");
                 pricePerDayChecked = false;
            }
        }catch(Exception e)
        {
            jLabelRentalNumber.setText("Error invalid data entered for Rental Number");
            rentalNumberChecked = false;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jTextFieldPricePerDay = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButtonCancel = new javax.swing.JButton();
        jTextFieldRentalNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCustomerNumber = new javax.swing.JTextField();
        jTextFieldVehicleNumber = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVehicle = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCustomer = new javax.swing.JTable();
        jFormattedTextFieldDateRental = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabelDateRental = new javax.swing.JLabel();
        jLabelRentalNumber = new javax.swing.JLabel();
        jLabelPricePerDay = new javax.swing.JLabel();
        jLabelCustomerNumber = new javax.swing.JLabel();
        jLabelVehicleNumber = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(795, 615));
        setMinimumSize(new java.awt.Dimension(795, 615));
        setPreferredSize(new java.awt.Dimension(795, 615));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                Closed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Closing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                Open(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 255));
        jLabel4.setText("Price per Day:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, -1, -1));

        jTextFieldPricePerDay.setEnabled(false);
        jTextFieldPricePerDay.setName("textFieldPricePerDay"); // NOI18N
        getContentPane().add(jTextFieldPricePerDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 490, 163, -1));

        jButtonSave.setBackground(new java.awt.Color(255, 51, 0));
        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rentalSave.png"))); // NOI18N
        jButtonSave.setToolTipText("Save");
        jButtonSave.setName(""); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 490, 120, -1));

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 255));
        jLabel1.setText("Rental Number");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, -1, -1));

        jButtonCancel.setBackground(new java.awt.Color(255, 51, 0));
        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rentalExit.png"))); // NOI18N
        jButtonCancel.setToolTipText("Exit");
        jButtonCancel.setName("buttonCancel"); // NOI18N
        getContentPane().add(jButtonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 490, 120, -1));

        jTextFieldRentalNumber.setName("textfieldRentalNumber"); // NOI18N
        getContentPane().add(jTextFieldRentalNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, 163, -1));

        jLabel2.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 255));
        jLabel2.setText("Date Rental");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        jLabel6.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 204));
        jLabel6.setText("Customer Number");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        jLabel7.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 255));
        jLabel7.setText("Vehicle Number");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));

        jTextFieldCustomerNumber.setEnabled(false);
        jTextFieldCustomerNumber.setName("textfieldCustomerNumber"); // NOI18N
        getContentPane().add(jTextFieldCustomerNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 163, -1));

        jTextFieldVehicleNumber.setEnabled(false);
        jTextFieldVehicleNumber.setName("textfieldVehicleNumber"); // NOI18N
        getContentPane().add(jTextFieldVehicleNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 173, -1));

        jLabel8.setFont(new java.awt.Font("Algerian", 1, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Rental");
        jLabel8.setName("EditRentalHeading"); // NOI18N
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jTableVehicle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " Number", " Make", " Category", " Rental Price", " Availability"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableVehicle.setPreferredSize(null);
        jTableVehicle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableVehicleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableVehicle);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 360, 190));

        jTableCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Number", " Name", " Last Name", " Address", "Rentable"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCustomer.setPreferredSize(null);
        jTableCustomer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCustomerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableCustomer);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 360, 190));

        try {
            jFormattedTextFieldDateRental.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####/##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFormattedTextFieldDateRental, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 450, 53, -1));

        jLabel9.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 255));
        jLabel9.setText("yyyy/mm/dd");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, -1, -1));

        jLabelDateRental.setForeground(new java.awt.Color(255, 0, 0));
        jLabelDateRental.setText("Text");
        getContentPane().add(jLabelDateRental, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 474, 286, 10));

        jLabelRentalNumber.setForeground(new java.awt.Color(255, 0, 0));
        jLabelRentalNumber.setText("Text");
        getContentPane().add(jLabelRentalNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 430, 286, -1));

        jLabelPricePerDay.setForeground(new java.awt.Color(255, 0, 0));
        getContentPane().add(jLabelPricePerDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 520, 286, 10));

        jLabelCustomerNumber.setForeground(new java.awt.Color(255, 0, 0));
        jLabelCustomerNumber.setText("Text");
        getContentPane().add(jLabelCustomerNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 286, -1));

        jLabelVehicleNumber.setForeground(new java.awt.Color(255, 0, 0));
        jLabelVehicleNumber.setText("Text");
        getContentPane().add(jLabelVehicleNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 60, 290, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rentalEditCover.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 620));

        getAccessibleContext().setAccessibleDescription("");

        setSize(new java.awt.Dimension(808, 646));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
        sendRentalData();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void Closed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Closed
        // TODO add your handling code here:
        try
        {
            sendData("Stop");           
            server.close();
            out.close();
            in.close();
        }catch(IOException e)
        {
            System.out.println("IOException (Closing Rental connection): " + e.getMessage());
        }
    }//GEN-LAST:event_Closed

    private void Open(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Open
        // TODO add your handling code here:
    jLabelCustomerNumber.setText("");
    jLabelDateRental.setText("");
    jLabelRentalNumber.setText("");
    jLabelVehicleNumber.setText("");
    displayCustomerTable();
    displayVehicleTable();
    }//GEN-LAST:event_Open

    private void Closing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Closing
        // TODO add your handling code here:
        try
        {
            sendData("Stop");           
            server.close();
            out.close();
            in.close();
        }catch(IOException e)
        {
            System.out.println("IOException (Closing Rental connection): " + e.getMessage());
        }
    }//GEN-LAST:event_Closing

    private void jTableCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCustomerMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTableCustomer.getModel();
        jTextFieldCustomerNumber.setText(model.getValueAt(jTableCustomer.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_jTableCustomerMouseClicked

    private void jTableVehicleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableVehicleMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)jTableVehicle.getModel();
        jTextFieldVehicleNumber.setText(model.getValueAt(jTableVehicle.getSelectedRow(), 0).toString());
        if(model.getValueAt(jTableVehicle.getSelectedRow(), 2).toString().equalsIgnoreCase("Sedan"))
            jTextFieldPricePerDay.setText("450");
        else
            jTextFieldPricePerDay.setText("500");
    }//GEN-LAST:event_jTableVehicleMouseClicked

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JFormattedTextField jFormattedTextFieldDateRental;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCustomerNumber;
    private javax.swing.JLabel jLabelDateRental;
    private javax.swing.JLabel jLabelPricePerDay;
    private javax.swing.JLabel jLabelRentalNumber;
    private javax.swing.JLabel jLabelVehicleNumber;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableCustomer;
    private javax.swing.JTable jTableVehicle;
    private javax.swing.JTextField jTextFieldCustomerNumber;
    private javax.swing.JTextField jTextFieldPricePerDay;
    private javax.swing.JTextField jTextFieldRentalNumber;
    private javax.swing.JTextField jTextFieldVehicleNumber;
    // End of variables declaration//GEN-END:variables
}
