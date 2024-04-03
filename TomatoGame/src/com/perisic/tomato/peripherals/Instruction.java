package com.perisic.tomato.peripherals;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;




public class Instruction extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int id;


    public Instruction(int id) {
    	Instruction.id=id;
        setTitle("Login Interface");
        setSize(950, 600);
        setLayout(null);
    
        Panel panel = new Panel();
        panel.setBounds(0, 0, 400, 600);
        panel.setBackground(Color.gray);
        add(panel);

        Panel panel1 = new Panel();
        panel1.setBounds(400, 0, 650, 600);
        panel1.setLayout(null);
        Color color1 = new Color(154, 126, 232);
        panel1.setBackground(color1);
        add(panel1);
        
        
        Panel panel3 = new Panel();
        panel3.setBounds(100, 80, 350, 400);
        panel3.setBackground(Color.black); 
        panel3.setLayout(null);
        panel1.add(panel3);
        
        JLabel headingLabel = new JLabel("Instructions");
        headingLabel.setBounds(120, 10, 150, 30);
        headingLabel.setForeground(Color.white);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel3.add(headingLabel);

        // Create a label for the instruction text
        String instructionText = "This is a Quiz Game where players need to find the hidden number behind the tomato. "
                + "If the player finds the correct answer, They will earn points and advance to the next level. "
                + "If the player answers incorrectly, They can keep trying until the time runs out. "
                + "Upon finishing the game, players can view their scores on the scoreboard.";
        JTextArea instructionArea = new JTextArea(instructionText);
        instructionArea.setBounds(20, 50, 300, 300);
        instructionArea.setForeground(Color.white);
        instructionArea.setBackground(Color.black);  // Set background color to match the panel
        instructionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionArea.setLineWrap(true);
        instructionArea.setWrapStyleWord(true);
        instructionArea.setEditable(false);
        panel3.add(instructionArea);
        
        
        Button logoutButton = new Button("Logout");
        logoutButton.setBounds(380, 500, 100, 30);
        logoutButton.setBackground(Color.orange);
        logoutButton.addActionListener(this::logoutActionPerformed);
        panel1.add(logoutButton);
        
        
   
       

        
        
        JButton btn9 = new JButton("Back");
        Font font1 = new Font("Arial", Font.BOLD, 22);
        btn9.setFont(font1);
        btn9.setBounds(180, 490, 150, 50); 
        btn9.setBackground(Color.CYAN); 
        btn9.addActionListener(this);
        panel1.add(btn9);
        
     		
        
        
        String imagePath1 ="C:\\Users\\Venus\\Desktop\\Work Space\\TomatoGameFull.zip_expanded\\bin\\image\\tomato1.png";
        File imageFile1 = new File(imagePath1);

        try {
            if (imageFile1.exists()) {
                ImageIcon imageIcon = new ImageIcon(imagePath1);
                Image image = imageIcon.getImage().getScaledInstance(400, 600, Image.SCALE_DEFAULT);
                ImageIcon scaledImageIcon = new ImageIcon(image);

                JLabel imageLabel = new JLabel(scaledImageIcon);
                imageLabel.setBounds(0, 0, 300, 600);
                panel.add(imageLabel);
            } else {
                System.err.println("Image file not found: " + imagePath1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Error loading the image: " + ex.getMessage());
        }
		
    }
    
    
    
    
    
    private void logoutActionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(Instruction.this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
           
            dispose(); 
            
            new login().setVisible(true); 
        }
    }
    
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Instruction(id).setVisible(true));
    }






	@Override
	 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            if (clickedButton.getText().equals("Back")) {
            	new Start(id).setVisible(true);
           	       dispose();
            } 
        
    }
        
        
}
    
  
   
        
        
        
    }


	
	

    
