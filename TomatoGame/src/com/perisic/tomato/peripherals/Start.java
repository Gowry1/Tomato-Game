package com.perisic.tomato.peripherals;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import com.perisic.tomato.engine.GameServer;






public class Start extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int id;
	
	 private static Clip backgroundMusic;
	 
	 


    public Start(int id) {
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
       
        
        JButton btn8 = new JButton("Start Game");
        Font font = new Font("Arial", Font.BOLD, 22);
        btn8.setFont(font);
        btn8.setBounds(100, 50, 150, 60); 
        btn8.setBackground(Color.CYAN); 
        btn8.addActionListener(this);
        panel3.add(btn8);
        
        JButton btn9 = new JButton("Instruction");
        Font font1 = new Font("Arial", Font.BOLD, 22);
        btn9.setFont(font1);
        btn9.setBounds(100, 150, 150, 60); 
        btn9.setBackground(Color.CYAN); 
        btn9.addActionListener(this);
        panel3.add(btn9);
        
        JButton btn3 = new JButton("Exit Game");
        Font font3 = new Font("Arial", Font.BOLD, 22);
        btn3.setFont(font3);
        btn3.setBounds(100, 250, 150, 60); 
        btn3.setBackground(Color.CYAN); 
        btn3.addActionListener(this);
        panel3.add(btn3);		
        
        Button logoutButton = new Button("Logout");
        logoutButton.setBounds(125, 340, 100, 30);
        logoutButton.setBackground(Color.orange);
        logoutButton.addActionListener(this::logoutActionPerformed);
        panel3.add(logoutButton);
        
        
        
        JLabel ipLabel = new JLabel();
        ipLabel.setBounds(100, 500, 300, 30);
        ipLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ipLabel.setForeground(Color.WHITE);

        String ipAddress = GameServer.getPublicIPAddress();
        ipLabel.setText("Your IP: " + ipAddress);

        panel1.add(ipLabel);

        JLabel worldTimeLabel = new JLabel();
        worldTimeLabel.setBounds(100, 530, 500, 30);
        worldTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        worldTimeLabel.setForeground(Color.WHITE);

        String worldTimeInfo = GameServer.getWorldTimeInfo("Asia/Colombo");
        worldTimeLabel.setText("World Time (Sri Lanka): " + worldTimeInfo);

        panel1.add(worldTimeLabel);
        
        
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
        
        Start.id = id;
		
    }
    
   

   

    


    private void logoutActionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(Start.this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            
            dispose(); 
            new login().setVisible(true); 
        }
    }




	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Start(id).setVisible(true));
    }
    
    private void confirmExit() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
   
    
   
        
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton clickedButton = (JButton) e.getSource();
                if (clickedButton.getText().equals("Start Game")) {
                	 playBackgroundMusic(); 
                	new GameGUI(id).setVisible(true);
               	       dispose();
                } else if (clickedButton.getText().equals("Instruction")) {
                	 new Instruction(id).setVisible(true);
            	       dispose();
                }else if (clickedButton.getText().equals("Exit Game")) {
                	confirmExit();
                }
            
        }
    }
        
        static void stopBackgroundMusic() {
            if (backgroundMusic != null && backgroundMusic.isRunning()) {
                backgroundMusic.stop();
                backgroundMusic.close();
            }
        }
        
        static void playBackgroundMusic() {
            try {
                String musicFilePath = "C:\\Users\\Venus\\Desktop\\Work Space\\TomatoGameFull.zip_expanded\\bin\\music.wav";
                File musicFile = new File(musicFilePath);

                if (musicFile.exists()) {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                    backgroundMusic = AudioSystem.getClip();
                    backgroundMusic.open(audioInputStream);

                    backgroundMusic.start();
                } else {
                    System.err.println("Music file not found: " + musicFilePath);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Error playing the background music: " + ex.getMessage());
            }
        }


	
	

    
}