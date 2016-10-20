import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class returnGui extends javax.swing.JFrame {

    private Socket server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String response;
    private Rent responseRent;
    private Rental responseRental;
    
    public returnGui() {
        initComponents();
        displayRentalTable();
    }

    
    public void displayRentalTable()
    {
        try
        {
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.setRowCount(0);
            server = new Socket("127.0.0.1", 12345);
            
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());
            sendData("Display Return");
            do
            {
                
                receiveData();
                if(response.equalsIgnoreCase("Return Display not successful"))
                {
                    receiveDataRental();
                    receiveDataRent();
                    model.addRow(new Object[]{responseRental.getRentalNumber(),responseRental.getDateRented(),responseRental.getDateReturned(),responseRental.getPricePerDay(),responseRental.calcNumberOfDays() * responseRental.getPricePerDay(), responseRent.getCustomerNumber(), responseRent.getVehicleNumber()});
                }
            }while(!response.equalsIgnoreCase("Return Display successful"));
            
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
     public void receiveDataRental()
     {
        try
        {
            responseRental = (Rental)in.readObject();
            System.out.println("SERVER said>> " + responseRental.toString());
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
     public void receiveDataRent()
     {
        try
        {
            responseRent = (Rent)in.readObject();
            System.out.println("SERVER said>> " + responseRent.getCustomerNumber() + " " + responseRent.getVehicleNumber());
          
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonClose = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(795, 615));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("returns");
        jLabel1.setName("LabelRentHeading"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "rentalNumber", "dateRental", "dateReturn", "pricePerDay", "totalRental", "custNumber", "vehNumber"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 92, 775, 220));

        jButtonClose.setBackground(new java.awt.Color(153, 0, 0));
        jButtonClose.setText("Close");
        jButtonClose.setToolTipText("Display");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 470, 120, 120));

        jButtonDelete.setBackground(new java.awt.Color(153, 0, 0));
        jButtonDelete.setText("Delete");
        jButtonDelete.setToolTipText("Sort List By Names");
        getContentPane().add(jButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 120, 120));

        jButtonEdit.setBackground(new java.awt.Color(153, 0, 0));
        jButtonEdit.setText("Edit");
        jButtonEdit.setToolTipText("Sort by list Vehicle ");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 120, 120));

        jButtonRefresh.setText("Refresh");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finalCover.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 800, 620));

        setSize(new java.awt.Dimension(803, 642));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        // TODO add your handling code here:
        displayRentalTable();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
        // TODO add your handling code here:
        if(jTable1.getSelectedRow() == -1)
        {
            if(jTable1.getRowCount() == 0)
                JOptionPane.showMessageDialog(rootPane, "Table is empty");
            else
                JOptionPane.showMessageDialog(rootPane, "You must Select a product");
        }
        else 
        {
            int no = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(),0).toString());
            returnEditGui form = new returnEditGui(no);
            form.setVisible(true);
        }
    }//GEN-LAST:event_jButtonEditActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
