package Utility;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class Product extends JPanel implements ActionListener {
    private EsKrim esKrim = new EsKrim();
    private String newId;
    private Connect conn = new Connect();
    private Connection con;

    private ResultSet rs = null;
    private ResultSetMetaData rsm = null;
    private String newTp="";
    private double newTotal=0;


    private JLabel jcomid;
    private JLabel jcomp1;
    private JLabel jcomp2;
    private JLabel jCatatan;
    private JTextField jcomp3;
    private JLabel jcomp4;
    private JComboBox jcomp5;
    private JLabel jcomp6;
    private JComboBox jcomp7;
    private JLabel jcomp8;
    private JCheckBox jcomp9;
    private JCheckBox jcomp10;
    private JCheckBox jcomp11;
    private JLabel jcomp12;
    private JSpinner jcomp13;
    private JLabel jcomp14;
    private JLabel jcomp15;
    private JLabel jcomp16;
    private JButton jcomp17;
    private JButton jcomp18;
    private JButton jcomp19;
    private SpinnerModel modelQty = new SpinnerNumberModel(0, 0, 100, 1);
    private JScrollPane scrollPane = new JScrollPane();
    private Object[][] jcomp5Items = {
            {"--",0},
            {"Popsicle",7000},
            {"Gelato",10000},
            {"Frozen Yogurt",11000}
    };
    private Object[][] topping={
            {"Nata de Coco",5000},
            {"Oreo",4000},
            {"Almond",5500}
    };
    private JTable table = new JTable(){
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component component = super.prepareRenderer(renderer, row, column);
            int rendererWidth = component.getPreferredSize().width+10;
            TableColumn tableColumn = getColumnModel().getColumn(column);
            tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
            return component;
        }
    };
    private DefaultTableModel dtm = new DefaultTableModel();
    private Vector<String> headerTable = new Vector<String>();

    public Product() throws SQLException {
        String[] jcomp7Items={};
        scrollPane.setViewportView(table);
        table.setModel(dtm);

        headerTable.add("ID");
        headerTable.add("Nama");
        headerTable.add("Jenis Es");
        headerTable.add("Rasa");
        headerTable.add("Toping");
        headerTable.add("Qty");
        headerTable.add("Total");

        dtm.setColumnIdentifiers(headerTable);
        Data();

        //construct components
        jcomid = new JLabel ();
        jcomp1 = new JLabel ("Penjualan");
        jcomp2 = new JLabel ("Nama Pembeli");
        jcomp3 = new JTextField (5);
        jcomp4 = new JLabel ("Jenis Es Krim");
        jcomp5 = new JComboBox ();
        for(int i=0; i < jcomp5Items.length;i++){
            jcomp5.addItem(jcomp5Items[i][0]);
        }
        jcomp5.addItemListener(new ItemListener() {
           @Override
           public void itemStateChanged(ItemEvent e) {
               String s = jcomp5.getSelectedItem().toString();
               switch(s){
                   case "Popsicle": {
                       String[] jcomp7Items = {"Chocolate", "Vanilla", "Cookies"};
                       jcomp7.removeAllItems();
                       for(int i=0; i < jcomp7Items.length;i++){
                           jcomp7.addItem(jcomp7Items[i]);
                       }
                       jcomp9.setEnabled(false);
                       jcomp10.setEnabled(false);
                       jcomp11.setEnabled(false);
                       break;
                   }
                   case "Gelato": {
                       String[] jcomp7Items = {"Hazelnut", "Tiramisu", "Dark Chocolate", "Mint"};
                       jcomp7.removeAllItems();
                       for(int i=0; i < jcomp7Items.length;i++){
                           jcomp7.addItem(jcomp7Items[i]);
                       }
                       jcomp9.setEnabled(true);
                       jcomp10.setEnabled(true);
                       jcomp11.setEnabled(true);
                       break;
                   }
                   case "Frozen Yogurt": {
                       String[] jcomp7Items = {"Strawberry", "Vanilla", "Kiwi", "Taro"};
                       jcomp7.removeAllItems();
                       for(int i=0; i < jcomp7Items.length;i++){
                           jcomp7.addItem(jcomp7Items[i]);
                       }
                       jcomp9.setEnabled(true);
                       jcomp10.setEnabled(true);
                       jcomp11.setEnabled(true);
                       break;
                   }
                   default:{
                       jcomp7.removeAllItems();
                   }
               }
           }
       });

        jcomp6 = new JLabel ("Rasa");
        jcomp7 = new JComboBox(jcomp7Items);
        jcomp8 = new JLabel ("Topping");
        jcomp9 = new JCheckBox ("Nata de Coco");
        jcomp10 = new JCheckBox ("Oreo");
        jcomp11 = new JCheckBox ("Almond");
        jcomp12 = new JLabel ("Qty");
        jcomp13 = new JSpinner (modelQty);
        jcomp14 = new JLabel ("Total");
        jcomp15 = new JLabel ("Rp.");
        jcomp16 = new JLabel ("0");
        jcomp16.setForeground(Color.red);
        jcomp16.setFont(new Font("Verdana", Font.PLAIN, 15));
        jcomp17 = new JButton ("Hitung");
        jcomp18 = new JButton ("Cancel");
        jcomp19 = new JButton ("Submit");
        jcomp18.setVisible(false);
        jcomp19.setVisible(false);
        jCatatan = new JLabel("Catatan: Untuk merubah dan menghapus data, klik pada salah satu baris data");
        jCatatan.setForeground(Color.red);
        jCatatan.setFont(new Font("Verdana", Font.PLAIN, 15));

        //adjust size and set layout
        setPreferredSize (new Dimension (1000, 489));
        setLayout (null);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) {     // to detect doble click events
                    Restart();
                    JTable target = (JTable)me.getSource();
                    int row = target.getSelectedRow(); // select a row
                    int column = target.getSelectedColumn(); // select a column
                    Object[] options = {"Edit","Delete","Cancel"};
                    int input = JOptionPane.showOptionDialog(null, "Edit or Delete data?", "Attention!",
                            JOptionPane.INFORMATION_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null,
                            options, options[0]);
                    switch (input){
                        case 0:{
                            //Edit
                            jcomp3.setText((String) table.getValueAt(row,1));
                            jcomp5.setSelectedItem(table.getValueAt(row,2));
                            jcomp7.setSelectedItem(table.getValueAt(row,3));
                            String[] array = table.getValueAt(row,4).toString().split("\\,");
                            for(int i=0; i<array.length;i++){
                                if(array[i].trim().equals("Nata de Coco")||array[i].trim().equals("Nata De Coco")){
                                    System.out.println(array[i]);
                                    jcomp9.setSelected(true);
                                }
                                if(array[i].trim().equals("Oreo")){
                                    System.out.println(array[i]);
                                    jcomp10.setSelected(true);
                                }
                                if(array[i].trim().equals("Almond")){
                                    System.out.println(array[i]);
                                    jcomp11.setSelected(true);
                                }
                            }
                            modelQty.setValue(table.getValueAt(row,5));
                            jcomp1.setText("Edit Data");
                            jcomid.setText((String) table.getValueAt(row,0));
                            break;
                        }
                        case 1:{
                            rs = conn.executeUpdate("DELETE FROM icecream WHERE ID='"+table.getValueAt(row,0)+"'");
                            try {
                                JOptionPane.showMessageDialog(null, "Hapus data berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                Refresh();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            break;
                        }
                        case 2:{
                            Restart();
                            jcomp1.setText("Penjualan");
                        }
                    }
                }
            }
        });

        jcomp17.addActionListener(this);
        jcomp18.addActionListener(this);
        jcomp19.addActionListener(this);
        //add components
        add (jcomid);
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (jcomp9);
        add (jcomp10);
        add (jcomp11);
        add (jcomp12);
        add (jcomp13);
        add (jcomp14);
        add (jcomp15);
        add (jcomp16);
        add (jcomp17);
        add (jcomp18);
        add (jcomp19);
        add (scrollPane);
        add (jCatatan);

        //set component bounds (only needed by Absolute Positioning)
        scrollPane.setBounds (300, 10, 700, 400);
        jcomp1.setBounds (10, 10, 64, 15);
        jcomp2.setBounds (10, 35, 100, 25);
        jcomp3.setBounds (10, 60, 205, 25);
        jcomp4.setBounds (10, 100, 100, 25);
        jcomp5.setBounds (10, 125, 205, 30);
        jcomp6.setBounds (10, 175, 100, 25);
        jcomp7.setBounds (10, 200, 205, 25);
        jcomp8.setBounds (10, 250, 100, 25);
        jcomp9.setBounds (10, 280, 100, 25);
        jcomp10.setBounds (110, 280, 60, 25);
        jcomp11.setBounds (170, 280, 100, 25);
        jcomp12.setBounds (10, 320, 100, 25);
        jcomp13.setBounds (10, 345, 100, 25);
        jcomp14.setBounds (10, 395, 100, 25);
        jcomp15.setBounds (105, 395, 20, 25);
        jcomp16.setBounds (135, 395, 100, 25);
        jcomp17.setBounds (10, 450, 100, 25);
        jcomp18.setBounds (135, 450, 100, 25);
        jcomp19.setBounds (10, 450, 100, 25);
        jCatatan.setBounds (300, 410, 700, 25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void Data() throws SQLException {
        String SQL = "Select * From icecream";
        rs = conn.executedQuery(SQL);

        while(rs.next()){
            String ID =rs.getString(1);
            String name = rs.getString(2);
            String jenis = rs.getString(3);
            String rasa = rs.getString(4);
            String toping = rs.getString(5);
            int qty = rs.getInt(6);
            double total = rs.getDouble(7);
            dtm.addRow(new Object[]{ID,name,jenis,rasa,toping,qty,total});
        }
    }

    public void Refresh() throws SQLException {
    dtm.setRowCount(0);
    Data();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    String tp="";
    double total=0;

        if(e.getSource()==jcomp17){
            if(jcomp3.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Nama Pembeli Harus Diisi!", "Informasi", JOptionPane.ERROR_MESSAGE);
            }else if(jcomp5.getSelectedItem()=="--"){
                JOptionPane.showMessageDialog(null,"Pilih Jenis Es Krim!", "Informasi", JOptionPane.ERROR_MESSAGE);
            }else if((Integer)modelQty.getValue()<1||(Integer)modelQty.getValue()>10){
                JOptionPane.showMessageDialog(null,"Beli Min: 1, Max: 10", "Informasi", JOptionPane.ERROR_MESSAGE);
            }else{

                esKrim.setName((String) jcomp5.getSelectedItem());
                esKrim.setPrice((Integer) jcomp5Items[jcomp5.getSelectedIndex()][1]);

                double es = esKrim.getPrice();
                String ndc="";
                int ndcp=0;
                if(jcomp9.isSelected()) {
                    ndc = jcomp9.getText();
                        tp = tp + ndc;
                }
                if(jcomp9.isSelected()) ndcp= (Integer) topping[0][1];

                String ore="";
                int orep=0;
                if(jcomp10.isSelected()) {
                    ore = jcomp10.getText();
                        tp = tp + "," + ore;
                }
                if(jcomp10.isSelected()) orep= (Integer) topping[1][1];

                String alm="";
                int almp=0;
                if(jcomp11.isSelected()) {
                    alm = jcomp11.getText();
                        tp = tp + "," + alm;
                }
                if(jcomp11.isSelected()) almp= (Integer) topping[2][1];

                int jum = (int) modelQty.getValue();
                total = (es+ndcp+orep+almp)*jum;

                if(tp.length()==0) {
                    tp = "-";
                }
                this.newTp = tp;
                this.newTotal = total;
                double amount = Double.parseDouble(String.valueOf(total));
                DecimalFormat formatter = new DecimalFormat("#,###.00");

                jcomp16.setText(formatter.format(amount));
                jcomp17.setVisible(false);
                jcomp18.setVisible(true);
                jcomp19.setVisible(true);
            }
        }
        if(e.getSource()==jcomp19) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection("jdbc:mysql://localhost/examoop", "root", "");

                        if(jcomp1.getText()!="Edit Data") {

                            String query = "INSERT INTO icecream (ID,BuyerName,IceCreamType,Flavor,Topping,Qty,TotalPrice) VALUES(?,?,?,?,?,?,?)";
                            PreparedStatement pstmt = con.prepareStatement(query);
                            newId = new ID(esKrim.getName()).getNewId();
                            pstmt.setString(1, newId);
                            pstmt.setString(2, jcomp3.getText());
                            pstmt.setString(3, esKrim.getName());
                            pstmt.setString(4, (String) jcomp7.getSelectedItem());
                            pstmt.setString(5, newTp);
                            pstmt.setInt(6, (Integer) modelQty.getValue());
                            pstmt.setInt(7, (int) newTotal);
                            Statement st = null;
                            st = con.createStatement();
                            pstmt.executeUpdate();
                            st.close();
                            JOptionPane.showMessageDialog(null, "Input data berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Inserting data success!");
                            Refresh();
                            Restart();
                            total = 0;
                            tp = "";
                        }else{
                            String query = "Update icecream SET BuyerName=?,IceCreamType=?,Flavor=?,Topping=?,Qty=?,TotalPrice=? WHERE ID=?";
                            PreparedStatement pstmt = con.prepareStatement(query);
                            pstmt.setString(1, jcomp3.getText());
                            pstmt.setString(2, esKrim.getName());
                            pstmt.setString(3, (String) jcomp7.getSelectedItem());
                            pstmt.setString(4, newTp);
                            pstmt.setInt(5, (Integer) modelQty.getValue());
                            pstmt.setInt(6, (int) newTotal);
                            pstmt.setString(7, jcomid.getText());
                            Statement st = null;
                            st = con.createStatement();
                            pstmt.executeUpdate();
                            st.close();
                            JOptionPane.showMessageDialog(null, "Update data berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Updating data success!");
                            Refresh();
                            Restart();
                            total = 0;
                            tp = "";
                        }
                    }catch (SQLException | ClassNotFoundException a){
                        System.out.println("Updating data error!");
                        JOptionPane.showMessageDialog(null,"Input data gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                        a.printStackTrace();
                    }

        }
        if(e.getSource()==jcomp18){
            Restart();
        }
    }
    public void Restart(){
        newTotal = 0;
        newTp="";
        jcomid.setText("");
        jcomp3.setText("");
        jcomp5.setSelectedIndex(0);
        jcomp9.setSelected(false);
        jcomp10.setSelected(false);
        jcomp11.setSelected(false);
        modelQty.setValue(0);
        jcomp17.setVisible(true);
        jcomp19.setVisible(false);
        jcomp18.setVisible(false);
        jcomp16.setText("0");
    }
}

