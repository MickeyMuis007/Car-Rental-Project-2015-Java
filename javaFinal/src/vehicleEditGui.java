import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

public class vehicleEditGui extends javax.swing.JFrame {

    
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;
    private boolean editOption;
    private int vehicleNumber;
    private String vehicleMake;
    private String vehicleCategory;
    private int category;
    private boolean vehicleAvailability;
    private String response;
    private boolean vehicleNumberChecked;
    private boolean vehicleMakeChecked;
    private boolean vehicleCategoryChecked;
    
    /**
     * Creates new form vehicleEditGui
     */
    public vehicleEditGui() {
        initComponents();
        editOption = false;
        jTextFieldVehicleNumber.setEnabled(true);
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
    public vehicleEditGui(int vehicleNumber, String vehicleMake, String vehicleCategory, boolean vehicleAvailability) {
        initComponents();
        editOption = true;
        jTextFieldVehicleNumber.setText(vehicleNumber+"");
        jTextFieldVehicleNumber.setEnabled(false);
        jTextFieldVehicleMake.setText(vehicleMake);
        if(vehicleCategory.equalsIgnoreCase("Sedan"))
            jComboBoxVehicleCategory.setSelectedIndex(0);
        else
            jComboBoxVehicleCategory.setSelectedIndex(1);
        jCheckBoxVehicleAvailability.setSelected(vehicleAvailability);
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
    public void sendData(Vehicle msg)
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
    
    private void sentVehicleData()
    {
        setValues();
        if(vehicleNumberChecked && vehicleMakeChecked && vehicleCategoryChecked )
        {
            try
            {
                if(!editOption)
                {
                    Vehicle v ;
                    sendData("Insert Vehicle");
                    if(vehicleCategory.equalsIgnoreCase("Sedan"))
                    { 
                        v = new Vehicle(vehicleNumber, vehicleMake, 1, vehicleAvailability);
                    }
                    else
                    {
                        v = new Vehicle(vehicleNumber, vehicleMake, 2, vehicleAvailability);
                    }
                    sendData(v);
                    receiveData();
                    if(response.equalsIgnoreCase("Data inserted not successful"))
                        JOptionPane.showMessageDialog(rootPane, "Primary key already exist");
                    else
                        this.dispose();
                }
                else
                {
                    Vehicle v;
                    sendData("Edit Vehicle");
                    if(vehicleCategory.equalsIgnoreCase("Sedan"))
                    { 
                        v = new Vehicle(vehicleNumber, vehicleMake, 1, vehicleAvailability);
                    }
                    else
                    {
                        v = new Vehicle(vehicleNumber, vehicleMake, 2, vehicleAvailability);
                    }
                    sendData(v);
                    receiveData();
                    this.dispose();
                }
            }catch(Exception e)
            {
                System.out.println("Exception: " + e);
            }
        }
    }
    private void setValues()
    {
        vehicleMakeChecked = true;
        vehicleCategoryChecked = true;
        jLabelVehicleNumberMsg.setText("");
        jLabelVehicleMakeMsg.setText("");
        jLabelVehicleCategoryMsg.setText("");
        try
        {
            vehicleNumber = Integer.parseInt(jTextFieldVehicleNumber.getText());
            if(vehicleNumber < 1000)
            {
                jLabelVehicleNumberMsg.setText("Error invalid data, vehicle number must be more than 999");
                vehicleNumberChecked = false;
            }
            else
            {
                vehicleNumberChecked = true;
                vehicleMake = jTextFieldVehicleMake.getText();
                if(vehicleMake.equals(""))
                {
                    jLabelVehicleMakeMsg.setText("Error!!! vehicle make shouldn't be empty");
                    vehicleMakeChecked = false;
                }
                vehicleCategory = jComboBoxVehicleCategory.getSelectedItem().toString();
                if(vehicleCategory.equals(""))
                {
                    jLabelVehicleCategoryMsg.setText("Error!!! need too Select a category");
                    vehicleCategoryChecked = false;
                }
                vehicleAvailability = jCheckBoxVehicleAvailability.isSelected();
            }
        }catch(Exception e)
        {
            jLabelVehicleNumberMsg.setText("Error invalid data entered for vehicle number");
            vehicleNumberChecked = false;
        }
    }
    
    private void display()
    {
         JOptionPane.showMessageDialog(this, vehicleNumber + "\n" + vehicleMake + "\n" + vehicleCategory + "\n" + vehicleAvailability + "\n");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jButtonSave = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButtonCancel = new javax.swing.JButton();
        jTextFieldVehicleNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldVehicleMake = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCheckBoxVehicleAvailability = new javax.swing.JCheckBox();
        jComboBoxVehicleCategory = new javax.swing.JComboBox();
        jLabelVehicleNumberMsg = new javax.swing.JLabel();
        jLabelVehicleMakeMsg = new javax.swing.JLabel();
        jLabelVehicleCategoryMsg = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        jLabel5.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 255, 0));
        jLabel5.setText("Vehicle Availability:");
        jLabel5.setName(""); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jButtonSave.setBackground(new java.awt.Color(102, 255, 0));
        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vehicleSave.png"))); // NOI18N
        jButtonSave.setToolTipText("Save Vehicle");
        jButtonSave.setName(""); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 120, 120));

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 0));
        jLabel1.setText("Vehicle Number:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jButtonCancel.setBackground(new java.awt.Color(102, 255, 0));
        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vehicleExit.png"))); // NOI18N
        jButtonCancel.setToolTipText("Exit");
        jButtonCancel.setName(""); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 480, 120, 120));

        jTextFieldVehicleNumber.setName(""); // NOI18N
        getContentPane().add(jTextFieldVehicleNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 260, -1));

        jLabel2.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 255, 0));
        jLabel2.setText("Vehicle Make:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 255, 0));
        jLabel3.setText("Vehicle Category:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jTextFieldVehicleMake.setName(""); // NOI18N
        jTextFieldVehicleMake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldVehicleMakeActionPerformed(evt);
            }
        });
        getContentPane().add(jTextFieldVehicleMake, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 260, -1));

        jLabel6.setFont(new java.awt.Font("Algerian", 1, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 255, 0));
        jLabel6.setText("Vehicle");
        jLabel6.setName("EditVehHeading"); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jCheckBoxVehicleAvailability.setName(""); // NOI18N
        getContentPane().add(jCheckBoxVehicleAvailability, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, -1, -1));

        jComboBoxVehicleCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sedan", "SUV" }));
        jComboBoxVehicleCategory.setName(""); // NOI18N
        getContentPane().add(jComboBoxVehicleCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 153, -1));

        jLabelVehicleNumberMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelVehicleNumberMsg.setText("Text");
        getContentPane().add(jLabelVehicleNumberMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 260, -1));

        jLabelVehicleMakeMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelVehicleMakeMsg.setText("Text");
        getContentPane().add(jLabelVehicleMakeMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 260, -1));

        jLabelVehicleCategoryMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelVehicleCategoryMsg.setText("Text");
        getContentPane().add(jLabelVehicleCategoryMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 260, -1));

        jLabel7.setForeground(new java.awt.Color(51, 255, 0));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/VehicleEditCover.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 620));

        setSize(new java.awt.Dimension(812, 642));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        sentVehicleData();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTextFieldVehicleMakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldVehicleMakeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldVehicleMakeActionPerformed

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
            System.out.println("IOException (Closing Vehicle connection): "+e.getMessage());
        }
    }//GEN-LAST:event_Closed

    private void Open(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Open
        // TODO add your handling code here:
    jLabelVehicleCategoryMsg.setText("");
    jLabelVehicleMakeMsg.setText("");
    jLabelVehicleNumberMsg.setText("");
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
            System.out.println("IOException (Closing Vehicle connection): "+e.getMessage());
        }
    }//GEN-LAST:event_Closing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxVehicleAvailability;
    private javax.swing.JComboBox jComboBoxVehicleCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelVehicleCategoryMsg;
    private javax.swing.JLabel jLabelVehicleMakeMsg;
    private javax.swing.JLabel jLabelVehicleNumberMsg;
    private javax.swing.JTextField jTextFieldVehicleMake;
    private javax.swing.JTextField jTextFieldVehicleNumber;
    // End of variables declaration//GEN-END:variables
}
