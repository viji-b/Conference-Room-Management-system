package packagename;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Fullproject {
    static String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
    static String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
    static String username = "DEMO";
    static String password = "demo";

    public static void main(String[] args) {
        // Create the JFrame
        JFrame Frame = new JFrame("Building");
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel overallPanel = new JPanel(new BorderLayout());      
        JPanel floor = new JPanel(new GridLayout(8, 1));
        for (int i = 1; i <= 8; i++) {
            JPanel room = new JPanel(new GridLayout(1, 7));
            room.add(new JLabel("Floor " + i));
            for (int j = 1; j <= 7; j++) {
                JButton roomButton = new JButton("Room " + j);
                roomButton.addActionListener(new RoomButtonListener(i, j, roomButton));
                floor.add(roomButton);
            }
            floor.add(room);
        }       
        overallPanel.add(floor, BorderLayout.CENTER);
        Frame.add(overallPanel);
        Frame.setSize(400, 300);
        Frame.setVisible(true);
    }

    static class RoomButtonListener implements ActionListener {
        private int floor;
        private int room;
        public JButton roomButton;

        public RoomButtonListener(int floor, int room, JButton roomButton) {
            this.floor = floor;
            this.room = room;
            this.roomButton = roomButton;
        }

        public void actionPerformed(ActionEvent e) {
            try {
             
                Class.forName(jdbcDriver);
                Connection conn = DriverManager.getConnection(dburl, username, password);

                PreparedStatement ps = conn.prepareStatement("SELECT status FROM BUILDING WHERE floor = ? AND room = ?");
                ps.setInt(1, floor);
                ps.setInt(2, room);
                ResultSet resultSet = ps.executeQuery();

                
                if (resultSet.next()) {
                    String status = resultSet.getString("status");
                    System.out.println("Room " + room + " on floor " + floor + " is " + status);
                }

           
                conn.close();
            }
            catch (SQLException ex) {
               System.out.println(ex);
            } 
            catch (ClassNotFoundException ex) {
            	System.out.println(ex);
            }
           
        }
    }
}

