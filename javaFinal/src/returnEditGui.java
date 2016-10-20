
import javax.swing.JOptionPane;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;

public class returnEditGui extends javax.swing.JFrame {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket server;
    private String response;
    
    private String returnDate;
    private boolean returnDateCheck;
    private int rentalNumber;
    
    public returnEditGui(int r) {
        initComponents();
        rentalNumber = r;
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
    
    private boolean checkedDate()
    {
        for(int i = 0; i < returnDate.length(); i++)
        {
            if(returnDate.charAt(i) < '0' && returnDate.charAt(i) > '9' && returnDate.charAt(i) != '/')
                return false;
        }
        return true;
    }
    private void setValues()
    {
        returnDate = jFormattedTextFieldReturnDate.getText();
        if(returnDate.equalsIgnoreCase("    /  /  "))
            returnDateCheck = false;
        else
            returnDateCheck = true;
    }
    public void sendData()
    {
        setValues();
        if(returnDateCheck)
        {
            try
            {
                server = new Socket("127.0.0.1", 12345);

                out = new ObjectOutputStream(server.getOutputStream());
                out.flush();
                in = new ObjectInputStream(server.getInputStream());
                sendData("Edit Return");
                sendData(returnDate);
                sendData(rentalNumber + "");
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
        }
        else
        {
            JOptionPane.showMessageDialog(rootPane, "Not OK");
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
        jButtonSave = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jFormattedTextFieldReturnDate = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Enter Return Date");

        jLabel1.setText("Return Date:");

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        try {
            jFormattedTextFieldReturnDate.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####/##/##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jFormattedTextFieldReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButtonCancel)
                .addGap(18, 18, 18)
                .addComponent(jButtonSave)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonCancel)
                    .addComponent(jFormattedTextFieldReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
        sendData();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JFormattedTextField jFormattedTextFieldReturnDate;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}