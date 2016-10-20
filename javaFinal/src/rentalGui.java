import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joindomain
 */
public class rentalGui extends javax.swing.JFrame {

    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String response;
    
    private int rentalNumber;
    private String dateRental;
    private String dateReturned;
    private float pricePerDay;
    private float totalRental;
    private int customerNumber;
    private int vehicleNumber;
    /**
     * Creates new form rentalGui
     */
    public rentalGui() {
        initComponents();
    }
    
    public void displayRentalTable()
    {
        try
        {
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.setRowCount(0);
            int count = 0;
            int no = 0, no1 = 0, no2 = 0;
            String dateRent = "",dateReturned = "";
            float d1 = 0, d2 = 0;
            boolean confirm = false;
            server = new Socket("127.0.0.1", 12345);
            
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
            sendData("Display Rental");
            do
            {
                receiveData();
                if(!response.equalsIgnoreCase("Rental Display successful"))
                {
                    switch(count)
                    {
                        case 0:
                            no = Integer.parseInt(response);
                            count++;
                            break;
                        case 1:
                            dateRent = response;
                            count++;
                            break;
                        case 2:
                            if(response == "")
                                d1 = 0;
                            else
                                d1 = Float.parseFloat(response);
                            model.addRow(new Object[]{no,dateRent,d1});
                            count = 0;
                            break;
                    }
                }
            }while(!response.equalsIgnoreCase("Rental Display successful"));
            
            sendData("Stop");
            server.close();
            in.close();
            out.close();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
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
    public void receiveData()
    {
        try
        {
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButtonRefresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(795, 615));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                Open(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "rentalNumber", "dateRental", "pricePerDay"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, 775, 190));

        jButtonAdd.setBackground(new java.awt.Color(102, 255, 255));
        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/addIconRental.png"))); // NOI18N
        jButtonAdd.setToolTipText("Add Info");
        jButtonAdd.setName(""); // NOI18N
        jButtonAdd.setOpaque(false);
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 120, 120));

        jButtonEdit.setBackground(new java.awt.Color(102, 255, 255));
        jButtonEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/editIconRental.png"))); // NOI18N
        jButtonEdit.setToolTipText("Edit Info");
        jButtonEdit.setName("buttonEdit"); // NOI18N
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 120, 120));

        jButtonDelete.setBackground(new java.awt.Color(102, 255, 255));
        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dltIconRental.png"))); // NOI18N
        jButtonDelete.setToolTipText("Delete Info");
        jButtonDelete.setName("buttonDelete"); // NOI18N
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, 120, 120));

        jButtonExit.setBackground(new java.awt.Color(102, 255, 255));
        jButtonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancelIconRental.png"))); // NOI18N
        jButtonExit.setToolTipText("Cancel");
        jButtonExit.setName("buttonExit"); // NOI18N
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 470, 120, 120));

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 255, 255));
        jLabel1.setText("Rental");
        jLabel1.setName("LabelRentHeading"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        jButtonRefresh.setText("Refresh");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/RenatCover.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 620));

        setSize(new java.awt.Dimension(803, 642));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
        rentalEditGui form = new rentalEditGui();
        form.setVisible(true);
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
        // TODO add your handling code here:
        rentalEditGui form = new rentalEditGui(1,"",12,1,2);
        form.setVisible(true);
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
         try
	{
            // STEP 1 b
            // Create socket TO SERVER
            server = new Socket("127.0.0.1", 12345);
            
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
            sendData("Delete Rental");
            sendData("Rental data to delete");
            receiveData();
            sendData("Stop");
            server.close();
            in.close();
            out.close();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void Open(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Open
        // TODO add your handling code here:
        displayRentalTable();
        
    }//GEN-LAST:event_Open

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        // TODO add your handling code here:
        displayRentalTable();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
