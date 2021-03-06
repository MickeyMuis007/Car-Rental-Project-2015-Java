
import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joindomain
 */
public class clientGui extends javax.swing.JFrame {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;
    /**
     * Creates new form clientGui
     */
    public clientGui() {
        initComponents();
        
    }

        
    public void receiveData()
    {
        try
        {
            String response = (String)in.readObject();
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonCustomer = new javax.swing.JButton();
        jButtonVehicle = new javax.swing.JButton();
        jButtonRental = new javax.swing.JButton();
        jButtonReturn = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(795, 615));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                closed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Closing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonCustomer.setBackground(new java.awt.Color(255, 102, 0));
        jButtonCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clientCover.png"))); // NOI18N
        jButtonCustomer.setToolTipText("AddCustomer");
        jButtonCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCustomerActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonCustomer, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 39, 120, 120));

        jButtonVehicle.setBackground(new java.awt.Color(255, 102, 0));
        jButtonVehicle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vehicleFront.jpg"))); // NOI18N
        jButtonVehicle.setToolTipText("Add Vehicle");
        jButtonVehicle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVehicleActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonVehicle, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 120, 120));

        jButtonRental.setBackground(new java.awt.Color(255, 102, 0));
        jButtonRental.setIcon(new javax.swing.ImageIcon(getClass().getResource("/returnsCover.png"))); // NOI18N
        jButtonRental.setToolTipText("Rentals");
        jButtonRental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRentalActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonRental, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 40, 120, 120));

        jButtonReturn.setBackground(new java.awt.Color(255, 102, 0));
        jButtonReturn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rentalCover.png"))); // NOI18N
        jButtonReturn.setToolTipText("Returns");
        jButtonReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReturnActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 120, 120));

        jButton5.setBackground(new java.awt.Color(255, 102, 0));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reportsCover.png"))); // NOI18N
        jButton5.setToolTipText("View Reports");
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 120, 120));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/finalCover.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 940, 600));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(938, 642));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCustomerActionPerformed
        // TODO add your handling code here:
        customerGui form = new customerGui();
        form.setVisible(true);
        
    }//GEN-LAST:event_jButtonCustomerActionPerformed

    private void jButtonVehicleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVehicleActionPerformed
        // TODO add your handling code here:
        vehicleGui form = new vehicleGui();
        form.setVisible(true);
    }//GEN-LAST:event_jButtonVehicleActionPerformed

    private void jButtonRentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRentalActionPerformed
        // TODO add your handling code here:
        rentalGui form = new rentalGui();
        form.setVisible(true);
    }//GEN-LAST:event_jButtonRentalActionPerformed

    private void closed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_closed

    private void Closing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Closing
        // TODO add your handling code here:
        try
        {
            // STEP 1 b
            // Create socket TO SERVER
            server = new Socket("127.0.0.1", 12345);
            
            out = new ObjectOutputStream(server.getOutputStream());
            out.flush();
            in = new ObjectInputStream(server.getInputStream());         
            sendData("Closed");
            server.close();
            in.close();
            out.close();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException: " + ioe.getMessage());
        }
        
    }//GEN-LAST:event_Closing

    private void jButtonReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReturnActionPerformed
        // TODO add your handling code here:
        returnGui form = new returnGui();
        form.setVisible(true);
    }//GEN-LAST:event_jButtonReturnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(clientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(clientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(clientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(clientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new clientGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonCustomer;
    private javax.swing.JButton jButtonRental;
    private javax.swing.JButton jButtonReturn;
    private javax.swing.JButton jButtonVehicle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
