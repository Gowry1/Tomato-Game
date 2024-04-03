package com.perisic.tomato.peripherals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.perisic.tomato.engine.Database;

public class Leaderboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private static int id;

    public Leaderboard(int id) {
    	Leaderboard.id=id;
    	
        setTitle("Leaderboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

       
        panel.setBackground(new Color(51, 153, 255));

        
        Font titleFont = new Font("Segoe UI", Font.BOLD, 32);

      
        ImageIcon icon = new ImageIcon("path/to/your/icon.png");
        JLabel titleLabel = new JLabel("Leaderboard", icon, JLabel.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setIconTextGap(20); 
        titleLabel.setForeground(Color.WHITE); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

     
        String[] columns = {"Username", "Score"};
        DefaultTableModel model = new DefaultTableModel(null, columns);
        table = new JTable(model);

  
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scrollPane);

        // Customize the table styles
        table.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        table.setBackground(new Color(255, 255, 255));
        table.setForeground(Color.BLACK);
        table.setRowHeight(40);


        
        JButton retryButton = new JButton("Retry");
        retryButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        retryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        retryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                dispose(); 
                new GameGUI(id).setVisible(true);

            }
        });
        panel.add(retryButton);
        

       
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                System.exit(0); 
            }
        });
        panel.add(exitButton);

        try {
         
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT username, score FROM user JOIN score ON user.id = score.id ORDER BY score DESC");

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String score = resultSet.getString("score");
                model.addRow(new Object[]{username, score});
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching leaderboard data.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Leaderboard(id).setVisible(true));
    }
}
