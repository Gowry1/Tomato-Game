package com.perisic.tomato.peripherals;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import com.perisic.tomato.engine.Database;
import com.perisic.tomato.engine.GameEngine;


public class GameGUI extends Frame implements ActionListener {

    private static final long serialVersionUID = -107785653906635L;
    
    JLabel questArea = null;
    GameEngine myGame = null;
    BufferedImage currentGame = null;
    Label infoArea = null;
    JLabel timer1=null;
    private int overallScore = 0;
    private static int currentLevel = 1;
    
    private int id;
    private JLabel livesLabel;
    
    private JLabel levelLabel;
    
    private JToggleButton musicToggleButton;
    
    private Timer timer;
    
    




    private void initGame(String player) {
        setSize(1300, 600);
        setLayout(null);
        
        myGame = new GameEngine(player);
        currentGame = myGame.nextGame();
        
        Panel panel = new Panel();
        panel.setBounds(0, 0, 600, 600);
        panel.setBackground(Color.gray);
        add(panel);

        
        String imagePath ="C:\\Users\\Venus\\Desktop\\Work Space\\TomatoGameFull.zip_expanded\\bin\\image\\tomato1.png";
        File imageFile = new File(imagePath);

        try {
            if (imageFile.exists()) {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
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
        panel1.setBounds(600, 0, 700, 600);
        panel1.setLayout(null);
        Color color1 = new Color(154, 126, 232); // Custom color with RGB values
        panel1.setBackground(color1);
        add(panel1);
        
       
       
      

        Button[] numberButtons = new Button[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new Button(Integer.toString(i));
            numberButtons[i].setBounds(40 + i * 60, 500, 40, 30);
            numberButtons[i].setBackground(Color.CYAN);
            numberButtons[i].addActionListener(this);
            panel1.add(numberButtons[i]);
        }

              
        infoArea = new Label();
        infoArea.setBounds(50, 40, 350, 50);
        panel1.add(infoArea);
        infoArea.setText("value of tomato? Score: 0");
        Font font = new Font("Arial", Font.BOLD, 25);
        infoArea.setFont(font);
        infoArea.setForeground(Color.BLUE);
        infoArea.setBackground(Color.DARK_GRAY);
          

        ImageIcon ii = new ImageIcon(currentGame);
        questArea = new JLabel(ii);
        questArea.setBounds(0, 120, 700, 330);
        questArea.setOpaque(true);
      
        panel1.setLayout(null);
        panel1.add(questArea);

        timer1 = new JLabel();
        timer1.setBounds(430, 40, 50, 30);
        timer1.setForeground(Color.white);
        timer1.setFont(new Font("Arial", Font.PLAIN, 30));

        // Set the background color
        timer1.setOpaque(true);
        timer1.setBackground(Color.blue); 

        panel1.setLayout(null);
        panel1.add(timer1);
        
        livesLabel = new JLabel("Lives: " + myGame.getRemainingLives());
        livesLabel.setBounds(600, 40, 100, 30);
        livesLabel.setForeground(Color.white);
        livesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel1.add(livesLabel);
        
        
        
        levelLabel = new JLabel("Level: " + currentLevel);
        levelLabel.setBounds(600, 70, 100, 30);
        levelLabel.setForeground(Color.white);
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        panel1.add(levelLabel);
        
        musicToggleButton = new JToggleButton("Music OFF");
        musicToggleButton.setBounds(420, 80, 150, 30);
        musicToggleButton.setBackground(Color.CYAN);
        musicToggleButton.addActionListener(this::actionPerformed1);  
        panel1.add(musicToggleButton);
        
        Button logoutButton = new Button("Logout");
        logoutButton.setBounds(270, 550, 100, 30);
        logoutButton.setBackground(Color.orange);
        logoutButton.addActionListener(this::logoutActionPerformed);
        panel1.add(logoutButton);
   

       
        


        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        
        timer = startTimer(15, "Level 1");
    
    }
    
    

   
    private Timer startTimer(int initialTime, String levelMessage) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = initialTime;
            
            

            public void run() {
                timer1.setText(String.valueOf(i--));

                if (i < 0) {
                    int levelScore = myGame.getScore(); 

                    overallScore += levelScore;

                    JOptionPane.showMessageDialog(GameGUI.this,
                            "Time's up for " + levelMessage + ". Your score for this level: " + levelScore);
                    
                    myGame.startNewLevel();
                    livesLabel.setText("Lives: " + myGame.getRemainingLives());
                    infoArea.setText("Good!  Score: " + myGame.getScore());

                    if (currentLevel < 3) {
                        currentLevel++;
                        levelLabel.setText("Level: " + currentLevel);
                        switch (currentLevel) {
                            case 2:
                                levelTwo();
                                break;
                            case 3:
                                levelThree();
                                break;
                        }
                    } else {
                        JOptionPane.showMessageDialog(GameGUI.this,
                                "Congratulations! Game Over!\nYour overall score is: " + overallScore);
                        Database.saveOverallScore(id, overallScore);
                        currentLevel = 1;
                        levelLabel.setText("Level: " + currentLevel);
                        new Leaderboard(id).setVisible(true);
                        
                        

                        dispose();
                        
                        

                    }

                    timer.cancel();
                }

                
            }
        };

        timer.schedule(task, 1000, 1000);
		return timer;
        
        
    }
    
    private void logoutActionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(GameGUI.this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            
          
            dispose();
           
            new login().setVisible(true);
        }
    }



    
    @Override
    public void actionPerformed(ActionEvent e) {
        int solution = Integer.parseInt(e.getActionCommand());
        boolean correct = myGame.checkSolution(solution);
        int score = myGame.getScore();
        
        if (correct) {
            System.out.println("Correct solution entered!");
            currentGame = myGame.nextGame();            
            ImageIcon ii = new ImageIcon(currentGame);
            questArea.setIcon(ii);
            infoArea.setText("Good!  Score: " + score);
        } else {
            System.out.println("Not Correct");
            infoArea.setText("Oops. Try again!  Score: " + score);

            
            myGame.decreaseLife();
            int remainingLives = myGame.getRemainingLives();
            livesLabel.setText("Lives: " + myGame.getRemainingLives());
            
            
          

            if (myGame.getRemainingLives() <= 0) {
            	
            	 overallScore += score; 
                JOptionPane.showMessageDialog(GameGUI.this, "Game Over! Your overall score is: " + overallScore);
                Database.saveOverallScore(id, overallScore);
                
                currentLevel = 1;
                levelLabel.setText("Level: " + currentLevel);
                new Leaderboard(id).setVisible(true);
                dispose();
                
                
                if (timer != null) {
                    timer.cancel();
                }
                timer1.setText("0");
                
                
                
                
            }else {
                JOptionPane.showMessageDialog(GameGUI.this, "Incorrect answer. Lives remaining: " + remainingLives);
            }
            
        
        }
        
        
        
    }
    
    public void actionPerformed1(ActionEvent e) {
        if (e.getSource() == musicToggleButton) {
            if (musicToggleButton.isSelected()) {
            	 Start.stopBackgroundMusic();
                 musicToggleButton.setText("Music ON");
            } else {
               
                Start.playBackgroundMusic();
                musicToggleButton.setText("Music OFF");
            }
        } else {
            if (e.getSource() instanceof Button) {
            }
        }
    }

    public GameGUI(int id) {
        super();
        initGame(null);
        this.id=id;
    }

    public GameGUI(String player) {
        super();
        initGame(player);
    }

    public GameGUI() {
        super();
        initGame(null);
	}


	public static void main(String[] args) {
        new GameGUI(currentLevel);
    }
   
    public void startGame() {
    	
        levelOne();
    }
    
    

    private void levelOne() {

    
        JOptionPane.showMessageDialog(this, "Level 1: Get ready!");

        startTimer(15, "Level 1");

    }

    private void levelTwo() {
    	

        JOptionPane.showMessageDialog(this, "Level 2: Keep going!");

        timer = startTimer(10, "Level 2");

    }

    private void levelThree() {
    	

        JOptionPane.showMessageDialog(this, "Level 3: Keep going!");

        timer = startTimer(5, "Level 3");

    }
}