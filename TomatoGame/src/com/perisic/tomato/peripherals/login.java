package com.perisic.tomato.peripherals;

import javax.swing.*;

import com.perisic.tomato.engine.Database;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField text;
    private JPasswordField passwordField;
    
    
    public login() {
        setTitle("Login Interface");
        setSize(1100, 600);
        setLayout(null);
    
        
        Panel panel = new Panel();
        panel.setBounds(0, 0, 500, 600);
        add(panel);
        
     // Load an image
        String imagePath = "C:\\Users\\Venus\\Desktop\\Work Space\\TomatoGameFull.zip_expanded\\bin\\image\\tomato1.png";

        File imageFile = new File(imagePath);

        try {
            if (imageFile.exists()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(500, 600, Image.SCALE_DEFAULT);
                ImageIcon scaledImageIcon = new ImageIcon(image);

                JLabel imageLabel = new JLabel(scaledImageIcon);
                imageLabel.setBounds(100, 40, 300, 200);
                panel.add(imageLabel);
            } else {
                System.err.println("Image file not found: " + imagePath);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error loading the image: " + ex.getMessage());
        }

       

        Panel panel1 = new Panel();
        panel1.setBounds(500, 0, 700, 600);
        panel1.setLayout(null);
        Color color1 = new Color(154, 126, 232); 
        panel1.setBackground(color1);
        add(panel1);

        text = new TextField();
        text.setBounds(300, 210, 230, 40);
        panel1.add(text);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 280, 230, 40);
        panel1.add(passwordField);
        
        Label lab1 = new Label();
        lab1.setBounds(200, 20, 200, 120);
        lab1.setText("Quiz game"); 
        lab1.setForeground(Color.BLACK); 
        Font font = new Font("Arial", Font.BOLD, 40); 
        lab1.setFont(font);
        panel1.add(lab1);
        
        

        
        Label lab2 = new Label();
        lab2.setBounds(90, 190, 200, 80);
        lab2.setText("Username"); 
        lab2.setForeground(Color.black); 
        Font font1 = new Font("Arial", Font.BOLD, 25); 
        lab2.setFont(font1);
        panel1.add(lab2);

        Label lab3 = new Label();
        lab3.setBounds(90, 210, 200, 180);
        lab3.setText("Password"); 
        lab3.setForeground(Color.black); 
        Font font2 = new Font("Arial", Font.BOLD, 25); 
        lab3.setFont(font2);
        panel1.add(lab3);
        
        Button btn = new Button("Sign in");
        btn.setBounds(200, 400, 150, 60);
        btn.setBackground(Color.LIGHT_GRAY);
        btn.addActionListener(this);
        panel1.add(btn);
        
        Label lab4 = new Label();
        lab4.setBounds(120, 450, 270, 50);
        lab4.setText("Do you have game account?"); 
        lab4.setForeground(Color.DARK_GRAY); 
        Font font3 = new Font("Arial", Font.BOLD, 20); 
        lab4.setFont(font3);
        panel1.add(lab4);

        
       

        JLabel uri = new JLabel("Click");
        uri.setBounds(400, 415, 120, 120);
        Font font6 = new Font("Arial", Font.BOLD, 20); 
        uri.setFont(font6);
        
        uri.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	 new Signup().setVisible(true);
                 dispose();
            }
        });

        panel1.add(uri);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
    	String username = text.getText();
        char[] passwordChars = passwordField.getPassword();
        String inputPassword = new String(passwordChars);

       
        String hashedInputPassword = hashMD5(inputPassword);

        int id = Database.validateLoginAndGetUserID(username, hashedInputPassword);
        if (id != -1) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            new Start(id).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }

        passwordField.setText("");
        text.setText("");
        
        

    }
    
    
    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    
    
    
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login().setVisible(true));
    }
    
}