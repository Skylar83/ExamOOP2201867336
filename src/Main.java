import Utility.Product;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame ("Penjualan Ice Cream");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new Product());
        frame.pack();
        frame.setVisible (true);
    }
}
