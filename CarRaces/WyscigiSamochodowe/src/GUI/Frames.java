package GUI;

import Repairs.Car;
import Repairs.CarPart;
import Repairs.Repair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Frames {
    private static Car selectedCar;

    public static Car getSelectedCar() {
        return selectedCar;
    }

    public static void setSelectedCar(Car selectedCar) {
        Frames.selectedCar = selectedCar;
    }

    public static void createMechanicGUI(){
        //Creating the Frame
        JFrame frame = new JFrame("Rejestracja nowej naprawy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        GridLayout layout = new GridLayout(0,1);
        //Creating the panel at bottom and adding components
        JPanel namePanel = new JPanel();
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // the panel is not visible in output
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // the panel is not visible in output
        JPanel okPanel = new JPanel(); // the panel is not visible in output
        JPanel errorPanel = new JPanel();
        JPanel worthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel textAreaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel nameLabel = new JLabel("Rejestracja nowej naprawy");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        nameLabel.setBorder(new EmptyBorder(20,0,0,0));
        JLabel dateLabel = new JLabel("Data naprawy: ");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField loginTf = new JFormattedTextField(format);
        Date today = Calendar.getInstance().getTime();
        loginTf.setText(format.format(today));
        JLabel passwordLabel = new JLabel("Samochód: ");
        java.util.List<Car> cars = Car.getExtent();
        String[] carsArray = new String[cars.size()];
        int ind = 0;
        if(selectedCar!=null)
            ind = cars.indexOf(selectedCar);
        for(int i=0;i<cars.size();i++){
            carsArray[i] = cars.get(i).getBrand() +" "+cars.get(i).getSerialNumber();
        }
        JComboBox carsComboBox = new JComboBox(carsArray);
        carsComboBox.setSelectedIndex(ind);
        selectedCar = cars.get(carsComboBox.getSelectedIndex());
        //Table
        List<CarPart> parts = cars.get(carsComboBox.getSelectedIndex()).getParts();
        Object data[][] = new Object[parts.size()][3];
        String columnNames[]={"Wybór","Nazwa","Cena"};
        for(int i=0;i<parts.size();i++){
            data[i][0] = false;
            data[i][1] = parts.get(i).getName();
            data[i][2] = "" + parts.get(i).getPrice();
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            /*@Override
            public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
            }*/
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        // end table
        Listeners.setComboBoxListener(carsComboBox, model);
        JLabel descLabel = new JLabel("Opis: ");
        JTextArea textArea = new JTextArea(5, 70);
        JScrollPane scrollPaneArea = new JScrollPane(textArea);
        JLabel worthLabel = new JLabel("Wartość: ");
        JTextField worthTF = new JTextField(10);
        JLabel errorLabel = new JLabel();
        JButton okButton = new JButton("OK");
        JButton addButton = new JButton("Dodaj");
        JButton anunujButton = new JButton("Anuluj");

        Listeners.setResetRepairButtonListener(anunujButton,frame);
        Listeners.setCreatePartButtonListener(addButton,frame);
        Listeners.setOkRepairButtonListener(frame,okButton,model,loginTf,worthTF,textArea,errorLabel);

        namePanel.add(nameLabel);
        loginPanel.add(dateLabel);
        loginPanel.add(loginTf);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(carsComboBox);
        okPanel.add(okButton);
        okPanel.add(anunujButton);
        worthPanel.add(worthLabel);
        worthPanel.add(worthTF);
        textAreaPanel.add(descLabel);
        textAreaPanel.add(scrollPaneArea);
        errorPanel.add(errorLabel);
        addPanel.add(addButton);
        frame.setLayout(layout);
        frame.getContentPane().add(namePanel);
        frame.getContentPane().add(loginPanel);
        frame.getContentPane().add(passwordPanel);
        frame.add(scrollPane);
        frame.add(addPanel);
        frame.add(worthPanel);
        frame.add(textAreaPanel);
        frame.getContentPane().add(okPanel);
        frame.getContentPane().add(errorPanel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    public static void createPartGUI(){
        //Creating the Frame
        JFrame frame = new JFrame("Dodanie nowej części");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        GridLayout layout = new GridLayout(0,1);
        //Creating the panel at bottom and adding components
        JPanel namePanel = new JPanel();
        JPanel captionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // the panel is not visible in output
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // the panel is not visible in output
        JPanel okPanel = new JPanel(); // the panel is not visible in output
        JPanel errorPanel = new JPanel();
        JPanel textAreaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel("Dodanie nowej części");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        nameLabel.setBorder(new EmptyBorder(20,0,0,0));
        JLabel captionLabel = new JLabel("Nazwa: ");
        JTextField captionTF = new JTextField(10);
        JLabel priceLabel = new JLabel("Cena:    ");
        JTextField priceTF = new JTextField(10);

        JLabel descLabel = new JLabel("Opis: ");
        JTextArea textArea = new JTextArea(5, 30);
        JScrollPane scrollPaneArea = new JScrollPane(textArea);

        JButton okButton = new JButton("OK");
        JButton anunujButton = new JButton("Anuluj");

        Listeners.setResetPartButtonListener(anunujButton,frame);
        JLabel errorLabel = new JLabel();
        Listeners.setOkPartButtonListener(frame,okButton,captionTF,priceTF,textArea,errorLabel);
        namePanel.add(nameLabel);
        captionPanel.add(captionLabel);
        captionPanel.add(captionTF);
        pricePanel.add(priceLabel);
        pricePanel.add(priceTF);
        okPanel.add(okButton);
        okPanel.add(anunujButton);
        textAreaPanel.add(descLabel);
        textAreaPanel.add(scrollPaneArea);
        errorPanel.add(errorLabel);
        frame.setLayout(layout);
        frame.getContentPane().add(namePanel);
        frame.getContentPane().add(captionPanel);
        frame.getContentPane().add(pricePanel);
        frame.add(textAreaPanel);
        frame.getContentPane().add(okPanel);
        frame.getContentPane().add(errorPanel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    public static void createRepairsListGUI(){
        //Creating the Frame
        JFrame frame = new JFrame("Twoje naprawy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        GridLayout layout = new GridLayout(0,1);
        //Creating the panel at bottom and adding components
        JPanel namePanel = new JPanel();
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel nameLabel = new JLabel("Twoje naprawy");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        nameLabel.setBorder(new EmptyBorder(20,0,0,0));
        namePanel.add(nameLabel);
        frame.setLayout(layout);
        frame.getContentPane().add(namePanel);
        //Table
        List<Repair> repairs = Repair.getRepairsWithMechanic(Listeners.getMechanic());
        if(repairs.size()>0){
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String data[][] = new String[repairs.size()][3];
            String columnNames[]={"Samochód","Data","Wartość"};
            for(int i=0;i<repairs.size();i++){
                data[i][0] = repairs.get(i).getCar().getBrand() + " " + repairs.get(i).getCar().getSerialNumber();
                data[i][1] = format.format(repairs.get(i).getDate());
                data[i][2] = repairs.get(i).getWorth()+"";
            }

            JTable table = new JTable(data,columnNames);
            table.setPreferredScrollableViewportSize(table.getPreferredSize());
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);
        }
        // end table
        JButton addButton = new JButton("Dodaj");
        Listeners.setAddRepairButtonListener(addButton, frame);
        addPanel.add(addButton);
        frame.add(addPanel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }

    public static void createLoginGUI(){
        //Creating the Frame
        JFrame frame = new JFrame("Logowanie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        GridLayout layout = new GridLayout(0,1);
        //Creating the panel at bottom and adding components
        JPanel namePanel = new JPanel();
        JPanel loginPanel = new JPanel(); // the panel is not visible in output
        JPanel passwordPanel = new JPanel(); // the panel is not visible in output
        JPanel okPanel = new JPanel(); // the panel is not visible in output
        JPanel errorPanel = new JPanel();
        JLabel nameLabel = new JLabel("Logowanie");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        nameLabel.setBorder(new EmptyBorder(20,0,0,0));
        JLabel loginLabel = new JLabel("Login: ");
        JTextField loginTf = new JTextField(10);
        JLabel passwordLabel = new JLabel("Hasło: ");
        JPasswordField passwordTf = new JPasswordField(10);
        JButton okButton = new JButton("OK");
        JLabel errorLabel = new JLabel();
        Listeners.setLoginButtonListener(okButton,loginTf,passwordTf,errorLabel, frame);
        namePanel.add(nameLabel);
        loginPanel.add(loginLabel);
        loginPanel.add(loginTf);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTf);
        okPanel.add(okButton);
        errorPanel.add(errorLabel);
        frame.setLayout(layout);
        frame.getContentPane().add(namePanel);
        frame.getContentPane().add(loginPanel);
        frame.getContentPane().add(passwordPanel);
        frame.getContentPane().add(okPanel);
        frame.getContentPane().add(errorPanel);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }
}
