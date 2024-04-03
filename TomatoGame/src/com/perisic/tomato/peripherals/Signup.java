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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Signup extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField text;
    private TextField text1;
    private JPasswordField text2;
    private JPasswordField text3;
    

    public Signup() {
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

                // Create a label to display the image
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
        Color color1 = new Color(154, 126, 232); // Custom color with RGB values
        panel1.setBackground(color1);
        add(panel1);

        text = new TextField();
        text.setBounds(300, 120, 230, 40);
        panel1.add(text);
        
        text1 = new TextField();
        text1.setBounds(300, 180, 230, 40);
        panel1.add(text1);
        
        text2 = new JPasswordField();
        text2.setBounds(300, 240, 230, 40);
        panel1.add(text2);

        text3 = new JPasswordField();
        text3.setBounds(300, 300, 230, 40);
        panel1.add(text3);
        
        
        
        Label lab1 = new Label();
        lab1.setBounds(230, 20, 200, 80);
        lab1.setText("Quiz"); // Set the text to "Quiz game"
        lab1.setForeground(Color.BLACK); // Set the forecolor to cyan
        Font font = new Font("Arial", Font.BOLD, 40); // You can adjust the font family, style, and size
        lab1.setFont(font);
        panel1.add(lab1);
        
        

        
        Label lab2 = new Label();
        lab2.setBounds(50, 115, 200, 50);
        lab2.setText("Username"); 
        lab2.setForeground(Color.BLACK); 
        Font font1 = new Font("Arial", Font.BOLD, 25); 
        lab2.setFont(font1);
        panel1.add(lab2);

        Label lab3 = new Label();
        lab3.setBounds(50, 165, 200, 70);
        lab3.setText("Email"); 
        lab3.setForeground(Color.BLACK); 
        Font font2 = new Font("Arial", Font.BOLD, 25); 
        lab3.setFont(font2);
        panel1.add(lab3);
        
        Label lab5 = new Label();
        lab5.setBounds(50, 225, 200, 70);
        lab5.setText("Password"); 
        lab5.setForeground(Color.BLACK); 
        Font font4 = new Font("Arial", Font.BOLD, 25); 
        lab5.setFont(font4);
        panel1.add(lab5);
        
        Label lab6 = new Label();
        lab6.setBounds(50, 285, 270, 70);
        lab6.setText("Confirm password");
        lab6.setForeground(Color.BLACK); 
        Font font5 = new Font("Arial", Font.BOLD, 25); 
        lab6.setFont(font5);
        panel1.add(lab6);
        
        Button btn = new Button("Submit");
        btn.setBounds(200, 400, 150, 60);
        btn.setBackground(Color.LIGHT_GRAY);
        btn.addActionListener(this);
        panel1.add(btn);
        
        Label lab4 = new Label();
        lab4.setBounds(150, 450, 150, 50);
        lab4.setText("Login account?"); 
        lab4.setForeground(Color.DARK_GRAY); 
        Font font3 = new Font("Arial", Font.BOLD, 20); 
        lab4.setFont(font3);
        panel1.add(lab4);

        
        JLabel uri = new JLabel("Click");
        uri.setBounds(320, 440, 80, 70);
        Font font6 = new Font("Arial", Font.BOLD, 20); 
        uri.setFont(font6);

        
        uri.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	new login().setVisible(true);
                dispose();            }
        });

        panel1.add(uri);
    }
    
   

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = text.getText();
        String email = text1.getText();

        char[] passwordChars = text2.getPassword();
        String password = new String(passwordChars);
        char[] confirmPasswordChars = text3.getPassword();
        String confirmPassword = new String(confirmPasswordChars);
        
        String hashedPassword = hashMD5(password);
        
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(this, "Invalid username. Please enter a valid username.");
            return;
        }

        // Validate email
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email. Please enter a valid email address.");
            return;
        }

        // Validate password
        if (!isValidPassword(passwordChars)) {
            JOptionPane.showMessageDialog(this, "Invalid password. Password must be at least 8 characters long.");
            return;
        }
        
        
        
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return;
        }

        if (Database.validateLogin1(username, email, hashedPassword)) {
            JOptionPane.showMessageDialog(this, "Sign up successful!");
            new login().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error signing up. Please try again.");
        }
        text1.setText("");
        text.setText("");
        text2.setText("");
        text3.setText("");
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




	private boolean isValidUsername(String username) {
       
        String regex = "^[a-zA-Z0-9]{3,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isValidEmail(String email) {
     
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(char[] password) {
        String passwordString = new String(password);
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-_=+\\[\\]{}|;:'\",.<>?/]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwordString);
        return matcher.matches();
    }


    


	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup().setVisible(true));
    }
    
}