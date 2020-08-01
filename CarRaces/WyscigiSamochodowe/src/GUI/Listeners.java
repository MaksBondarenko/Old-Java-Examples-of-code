package GUI;

import Login.Worker;
import Repairs.Car;
import Repairs.CarPart;
import Repairs.Mechanic;
import Repairs.Repair;
import Sponsorships.Sponsor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Listeners {

    public static void setAddRepairButtonListener(JButton button, JFrame frame){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Frames.createMechanicGUI();
            }
        });
    }

    public static void setResetRepairButtonListener(JButton button, JFrame frame){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Frames.createRepairsListGUI();
            }
        });
    }

    public static void setResetPartButtonListener(JButton button, JFrame frame){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Frames.createMechanicGUI();
            }
        });
    }

    public static void setCreatePartButtonListener(JButton button, JFrame frame){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Frames.createPartGUI();
            }
        });
    }

    private static Mechanic mechanic;

    public static Mechanic getMechanic() {
        return mechanic;
    }

    public static void setLoginButtonListener(JButton button, JTextField login, JTextField password, JLabel errorLabel, JFrame frame){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Worker.login(login.getText(),password.getText())==2)
                    errorLabel.setText("Nie podano wszystkich parametrów do logowania");
                else if(Worker.login(login.getText(),password.getText())==0){
                    //frame.dispose();
                    errorLabel.setText("Pracownik");
                }
                else if(Sponsor.login(login.getText(),password.getText())==0){
                    //frame.dispose();
                    errorLabel.setText("Sponsor");
                }
                else {
                    try {
                        mechanic = Mechanic.login(login.getText(),password.getText());
                        frame.dispose();
                        Frames.createRepairsListGUI();
                    } catch (Exception ex) {
                        errorLabel.setText(ex.getMessage());
                    }
                }
            }
        });
    }

    public static void setOkPartButtonListener(JFrame frame, JButton button, JTextField name, JTextField price, JTextArea desc, JLabel error){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        CarPart.createPart(Frames.getSelectedCar(),name.getText(),Double.parseDouble(price.getText()),desc.getText());
                        frame.dispose();
                        Frames.createMechanicGUI();
                        CarPart.writeExtent();
                        Car.writeExtent();
                    } catch (NumberFormatException ex) {
                        error.setText("Podaj liczbę ze zmiennym przycinkiem");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
            }
        });
    }

    public static void setOkRepairButtonListener(JFrame frame, JButton button, DefaultTableModel model, JFormattedTextField dateTF, JTextField worth, JTextArea desc, JLabel error){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date date =new SimpleDateFormat("yyyy-MM-dd").parse(dateTF.getText());
                    Car car = Frames.getSelectedCar();
                    List<CarPart> carParts = new ArrayList<>();
                    for(int i=0;i<model.getRowCount();i++)
                        if((boolean)model.getValueAt(i,0)){
                            carParts.add(car.getParts().get(i));
                        }
                    Repair.createRepair(carParts,car,mechanic,Double.parseDouble(worth.getText()),date,desc.getText());
                    frame.dispose();
                    Frames.createRepairsListGUI();
                    Repair.writeExtent();
                    CarPart.writeExtent();
                    Car.writeExtent();
                    Mechanic.writeExtent();
                } catch (NumberFormatException ex) {
                    error.setText("Podaj liczbę ze zmiennym przycinkiem");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void setComboBoxListener(JComboBox comboBox, DefaultTableModel model){
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                List<Car> cars = Car.getExtent();
                List<CarPart> parts = cars.get(comboBox.getSelectedIndex()).getParts();
                Object data[][] = new Object[parts.size()][3];
                for(int i=0;i<parts.size();i++){
                    data[i][0] = false;
                    data[i][1] = parts.get(i).getName();
                    data[i][2] = "" + parts.get(i).getPrice();
                    model.addRow(data[i]);
                }
                model.fireTableDataChanged();
                Frames.setSelectedCar(cars.get(comboBox.getSelectedIndex()));
            }
        });
    }
}
