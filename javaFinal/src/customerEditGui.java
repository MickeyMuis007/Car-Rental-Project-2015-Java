
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;


public class customerEditGui extends javax.swing.JFrame {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;
    private String response;
    
    private int customerNumber;
    private String customerName;
    private String customerLastName;
    private String customerAddress;
    private boolean rentable;
    
    private boolean customerNumberChecked;
    private boolean customerNameChecked;
    private boolean customerLastNameChecked;
    private boolean customerAddressChecked;
    private boolean editOption;
    /**
     * Creates new form customerEditGui
     */
    public customerEditGui() {
        initComponents();
        editOption = false;
        jTextFieldCustomerNumber.setEnabled(true);
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
    
    
    public customerEditGui(int a, String b, String c, String d, boolean e)
    {
        initComponents();
        editOption =true;
        jTextFieldCustomerNumber.setText(a + "");
        jTextFieldCustomerNumber.setEnabled(false);
        jTextFieldCustomerName.setText(b);
        jTextFieldCustomerLastName.setText(c);
        jTextFieldCustomerAddress.setText(d);
        jCheckBoxRentable.setSelected(e);
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
    public void closed()
    {
        sendData("Closed");
    }
    
    //Client
    
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
    
    public void sendData(Customer msg)
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
    private void sendCustomerData()
    {
        setValues();
        if(customerNumberChecked && customerNameChecked && customerLastNameChecked && customerAddressChecked)
        {
            try
            {
                if(!editOption)
                {
                    sendData("Insert Customer");
                    Customer cust = new Customer(customerNumber,customerName, customerLastName,customerAddress,rentable);
                    sendData(cust);
                    receiveData();
                   if(response.equalsIgnoreCase("Data inserted not successful"))
                        JOptionPane.showMessageDialog(rootPane, "Primary key already exist");
                    else
                        this.dispose();
                    
                }
                else
                {
                    Customer cust = new Customer(customerNumber,customerName, customerLastName,customerAddress,rentable);
                    sendData("Edit Customer");
                    sendData(cust);
                    receiveData();
                    if(response.equalsIgnoreCase("Data inserted not successful"))
                        JOptionPane.showMessageDialog(rootPane, "Primary key already exist");
                    else
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
        customerLastNameChecked = true;
        customerNameChecked = true;
        customerAddressChecked = true;
        jLabelCustomerNumberMsg.setText("");
        jLabelCustomerNameMsg.setText("");
       jLabelCustomerLastNameMsg.setText("");
       jLabelCustomerAddressMsg.setText("");
        try
        {
            customerNumber = Integer.parseInt(jTextFieldCustomerNumber.getText());
            if(customerNumber < 100)
            {
                jLabelCustomerNumberMsg.setText("Error customer number must be greater than 99.");
                customerNumberChecked = false;
            }
            else
            {
                customerNumberChecked = true;
                customerName = jTextFieldCustomerName.getText();
                if(customerName.equalsIgnoreCase(""))
                {
                    jLabelCustomerNameMsg.setText("Error customer name is empty.");
                    customerNameChecked = false;
                }
                customerLastName = jTextFieldCustomerLastName.getText();
                if(customerLastName.equalsIgnoreCase(""))
                {
                    jLabelCustomerLastNameMsg.setText("Error customer last name is empty.");
                    customerLastNameChecked = false;
                }
                customerAddress = jTextFieldCustomerAddress.getText();
                if(customerAddress.equalsIgnoreCase(""))
                {
                    jLabelCustomerAddressMsg.setText("Error address is empty");
                    customerAddressChecked = false;
                }
                rentable = jCheckBoxRentable.isSelected();
            }
            
        }catch(Exception e)
        {
            jLabelCustomerNumberMsg.setText("Error customer number invalid data entered.");
            customerNumberChecked = false;
        }
    }
    private void display()
    {
        JOptionPane.showMessageDialog(this, customerNumber + "\n" + customerName + "\n" + customerLastName + "\n" + rentable);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldCustomerNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldCustomerName = new javax.swing.JTextField();
        jTextFieldCustomerLastName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButtonSave = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jCheckBoxRentable = new javax.swing.JCheckBox();
        jLabelCustomerNumberMsg = new javax.swing.JLabel();
        jLabelCustomerNameMsg = new javax.swing.JLabel();
        jLabelCustomerLastNameMsg = new javax.swing.JLabel();
        jLabelCustomerAddressMsg = new javax.swing.JLabel();
        jTextFieldCustomerAddress = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                CloseForm(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Closing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                Opening(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel1.setText("Customer Number:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 105, -1, -1));

        jTextFieldCustomerNumber.setName(""); // NOI18N
        getContentPane().add(jTextFieldCustomerNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 259, -1));

        jLabel2.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel2.setText("Customer Name:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel3.setText("Customer Lastname:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jTextFieldCustomerName.setName(""); // NOI18N
        getContentPane().add(jTextFieldCustomerName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 259, -1));

        jTextFieldCustomerLastName.setName(""); // NOI18N
        getContentPane().add(jTextFieldCustomerLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 259, -1));

        jLabel4.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel4.setText("Rentable:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jLabel5.setFont(new java.awt.Font("Algerian", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 0));
        jLabel5.setText("Customer");
        jLabel5.setName("EditcustHeading"); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        jButtonSave.setBackground(new java.awt.Color(204, 0, 0));
        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/saveCust.png"))); // NOI18N
        jButtonSave.setToolTipText("Save Customer Details");
        jButtonSave.setName("buttonSave"); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 470, 120, 120));

        jButtonCancel.setBackground(new java.awt.Color(204, 0, 0));
        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancelCustEdit.png"))); // NOI18N
        jButtonCancel.setToolTipText("Exit");
        jButtonCancel.setName("buttonCancel"); // NOI18N
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 470, 120, 120));

        jCheckBoxRentable.setSelected(true);
        jCheckBoxRentable.setName(""); // NOI18N
        getContentPane().add(jCheckBoxRentable, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, -1, -1));

        jLabelCustomerNumberMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelCustomerNumberMsg.setText("text");
        getContentPane().add(jLabelCustomerNumberMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 540, 20));

        jLabelCustomerNameMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelCustomerNameMsg.setText("text");
        getContentPane().add(jLabelCustomerNameMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 500, -1));

        jLabelCustomerLastNameMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelCustomerLastNameMsg.setText("text");
        getContentPane().add(jLabelCustomerLastNameMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 240, 490, 10));

        jLabelCustomerAddressMsg.setForeground(new java.awt.Color(255, 0, 0));
        jLabelCustomerAddressMsg.setText("jLabel7");
        getContentPane().add(jLabelCustomerAddressMsg, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 490, -1));
        getContentPane().add(jTextFieldCustomerAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 260, -1));

        jLabel8.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        jLabel8.setText("Customer Address: ");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/custEditCover.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 0, 860, 620));

        setSize(new java.awt.Dimension(801, 642));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        sendCustomerData();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
       this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void CloseForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_CloseForm
        // TODO add your handling code here:
        try
        {
            sendData("Stop");           
            server.close();
            out.close();
            in.close();
        }catch(IOException e)
        {
            System.out.println("IOException(Customer cancel): " + e.getMessage());
        }
    }//GEN-LAST:event_CloseForm

    private void Opening(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Opening
        // TODO add your handling code here:
        jLabelCustomerNumberMsg.setText("");
        jLabelCustomerNameMsg.setText("");
        jLabelCustomerLastNameMsg.setText("");
        jLabelCustomerAddressMsg.setText("");
        
        
        
    }//GEN-LAST:event_Opening

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
            System.out.println("IOException(Customer cancel): " + e.getMessage());
        }
    }//GEN-LAST:event_Closing

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxRentable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCustomerAddressMsg;
    private javax.swing.JLabel jLabelCustomerLastNameMsg;
    private javax.swing.JLabel jLabelCustomerNameMsg;
    private javax.swing.JLabel jLabelCustomerNumberMsg;
    private javax.swing.JTextField jTextFieldCustomerAddress;
    private javax.swing.JTextField jTextFieldCustomerLastName;
    private javax.swing.JTextField jTextFieldCustomerName;
    private javax.swing.JTextField jTextFieldCustomerNumber;
    // End of variables declaration//GEN-END:variables
}
