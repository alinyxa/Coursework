import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanyForm {
    private JPanel panelMain;
    private JTabbedPane tabbedPane1;
    private JPanel Manager;
    private JPanel ManagerPanel;
    private JTextField nameCompMField;
    private JTextField surnameMField;
    private JTextField nameMField;
    private JTextField patronymicmMField;
    private JTextField numberMField;
    private JButton createMButton;
    private JPanel ClientPanel;
    private JTextField surnameCField;
    private JTextField nameCField;
    private JTextField patronymicmCField;
    private JTextField numberHometСField;
    private JTextField streetCField;
    private JTextField cityCField;
    private JTextField numberCField;
    private JButton CreateClientButton;
    private JPanel ContractPanel;
    private JTextField numberHouseContrField;
    private JTextField streetContrField;
    private JTextField cityContrField;
    private JComboBox typeBuildCombo;
    private JTextField plotAreaField;
    private JTextField numberFloorsField;
    private JTextField dateStartField;
    private JTextField dateStopField;
    private JButton CreateContractButton;
    private JComboBox comboManager;
    private JComboBox comboClient;
    private JPanel ActionPanel;
    private JButton ManagerListBut;
    private JButton ClientListBut;
    private JButton ContractListBut;
    private JButton popularBuildButton;
    private JButton FindClientsBut;
    private JButton ArayBut;
    private JButton AverageTermBut;
    private JButton ValuableContractBut;
    private JComboBox Type2combo;
    private JTextArea resultArea;
    private JTextField areaBuildingField;
    private JTextField costField;
    private JButton DeleteContractBut;
    private JButton DeleteClient;
    private JButton deleteManager;
    private JComboBox popularCombo;
    private JButton loadBut;
    private JComboBox averageCombo;
    private JComboBox costCombo;
    private JComboBox Type2TimeCombo;
    private JComboBox squareCombo;
    private JButton chooseBut;
    private JComboBox comboManagerClient;
    private JScrollPane scrollPane;
    private JButton saveClient;
    private JButton saveManager;
    private JButton saveContracts;
    private Manager managerInCombo;


    CompanySystem companySystem = new CompanySystem();

    public void clearManagerForm(){
        nameCompMField.setText("");
        surnameMField.setText("");
        nameMField.setText("");
        patronymicmMField.setText("");
        numberMField.setText("");
    }

    public void clearClientForm(){
        comboManagerClient.setSelectedIndex(0);
        surnameCField.setText("");
        nameCField.setText("");
        patronymicmCField.setText("");
        numberHometСField.setText("");
        streetCField.setText("");
        cityCField.setText("");
        numberCField.setText("");
    }

    public void clearContractForm(){
        comboManager.setSelectedIndex(0);
        comboClient.removeAllItems();
        numberHouseContrField.setText("");
        streetContrField.setText("");
        cityContrField.setText("");
        typeBuildCombo.setSelectedIndex(0);
        plotAreaField.setText("");
        areaBuildingField.setText("");
        numberFloorsField.setText("");
        dateStartField.setText("");
        dateStopField.setText("");
        costField.setText("");
    }

    public void Update(){

        System.out.println("Количество менеджеров в системе: " + companySystem.listManagers.size());
        if(companySystem.listManagers.isEmpty() || !companySystem.managersClients()){
            tabbedPane1.setSelectedIndex(0);
            tabbedPane1.setEnabledAt(1, false);
            JOptionPane.showMessageDialog(panelMain, "Немає менеджера для створення клієнта", "Помилка", JOptionPane.ERROR_MESSAGE);
            comboManager.removeAllItems();
            comboManagerClient.removeAllItems();
            for(int i = 0; i < companySystem.listManagers.size(); i++){
                Manager manager = companySystem.listManagers.get(i);
                System.out.println("Проверка менеджера: " + manager.getFullName());
                if(companySystem.listManagers.get(i).checkClientsCounts()){
                    comboManagerClient.addItem(companySystem.listManagers.get(i).getFullName() + " +380" + companySystem.listManagers.get(i).getNumber());
                }
                comboManager.addItem(companySystem.listManagers.get(i).getFullName() + " +380" + companySystem.listManagers.get(i).getNumber());
            }
        }
        else {
            comboManager.removeAllItems();
            comboManagerClient.removeAllItems();
            for(int i = 0; i < companySystem.listManagers.size(); i++){
                Manager manager = companySystem.listManagers.get(i);
                System.out.println("Проверка менеджера: " + manager.getFullName());
                if(companySystem.listManagers.get(i).checkClientsCounts()){
                    comboManagerClient.addItem(companySystem.listManagers.get(i).getFullName() + " +380" + companySystem.listManagers.get(i).getNumber());
                }
                comboManager.addItem(companySystem.listManagers.get(i).getFullName() + " +380" + companySystem.listManagers.get(i).getNumber());
            }
        }
    }

    public void UpdateClients(){
        if(companySystem.listClients.isEmpty()){
            tabbedPane1.setSelectedIndex(0);
            tabbedPane1.setEnabledAt(2, false);
            JOptionPane.showMessageDialog(panelMain, "Немає клієнта для створення контракта", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Adress checkInputAdress(String street, String city, String number){
        int numberHome;
        if (street.isEmpty() || city.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Будь ласка, заповніть всі поля.");
            return null;
        }
        try {
            numberHome = Integer.parseInt(number.trim());
            if(numberHome <= 0){
                JOptionPane.showMessageDialog(panelMain, "Номер будинку не може бути менше або дорівнювати 0.", "Помилка", JOptionPane.WARNING_MESSAGE);
                return null;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panelMain, "Введіть номер будинку.", "Помилка", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return new Adress(numberHome, street, city);
    }

    public CompanyForm() {
        tabbedPane1.setEnabledAt(1, false);
        tabbedPane1.setEnabledAt(2, false);
        saveManager.setEnabled(false);
        saveClient.setEnabled(false);
        saveContracts.setEnabled(false);

        //додати менеджера
        createMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String companyName = nameCompMField.getText();
                String surname = surnameMField.getText();
                String name = nameMField.getText();
                String patronymic = patronymicmMField.getText();
                if (companyName.isEmpty() || surname.isEmpty() || name.isEmpty() || patronymic.isEmpty()) {
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, заповніть всі поля.");
                    return;
                }

                int number;
                try {
                    number = Integer.parseInt(numberMField.getText().trim());

                    Boolean isNumber = companySystem.CheckNumber(number);
                    if (numberMField.getText().length() != 9) {
                        JOptionPane.showMessageDialog(panelMain, "Номер телефону повинен містити 9 цифр.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(number <= 0){
                        JOptionPane.showMessageDialog(panelMain, "Номер телефону не може бути менше 0 або дорівнювати 0.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(isNumber){
                        JOptionPane.showMessageDialog(panelMain, "Вже існує користувач з таким номером телефону.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Введіть правильний номер телефону.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                companySystem.listNumbPhones.add(number);
                companySystem.listManagers.add(new Manager(surname, name, patronymic, number, companyName));
                Update();
                saveManager.setEnabled(true);
                saveClient.setEnabled(true);
                saveContracts.setEnabled(true);
                tabbedPane1.setEnabledAt(1, true);
                JOptionPane.showMessageDialog(panelMain, "Менеджер успішно доданий.", "Успіх", JOptionPane.INFORMATION_MESSAGE);
                clearManagerForm();
            }
        });
        //додати клієнта
        CreateClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String FIO = (String) comboManagerClient.getSelectedItem();
                String phoneNumber = null;
                if (FIO.contains("+380")) {
                    int index = FIO.indexOf("+380") + 4;
                    phoneNumber = FIO.substring(index).trim();
                }
                Manager selectedManager = null;
                if (phoneNumber != null) {
                    int phone = Integer.parseInt(phoneNumber);
                    selectedManager = companySystem.findManagerByPhone(phone);
                }
                else {
                    JOptionPane.showMessageDialog(panelMain, "Помилка!");
                    return;
                }
                String surname = surnameCField.getText();
                String name = nameCField.getText();
                String patronymic = patronymicmCField.getText();
                if(surname.isEmpty() || name.isEmpty() || patronymic.isEmpty()){
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, заповніть всі поля.");
                    return;
                }
                String street = streetCField.getText();
                String city = cityCField.getText();
                String numberHome = numberHometСField.getText();
                Adress adress = null;
                try {
                    adress = checkInputAdress(street, city, numberHome);
                    if (adress == null){
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelMain, "Помилка при створенні контракту.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
                int number;
                try {
                    number = Integer.parseInt(numberCField.getText().trim());
                    boolean isNumber = companySystem.CheckNumber(number);
                    if (numberCField.getText().length() != 9) {
                        JOptionPane.showMessageDialog(panelMain, "Номер телефону повинен містити 9 цифр.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(number <= 0){
                        JOptionPane.showMessageDialog(panelMain, "Номер телефону не може бути менше 0 або дорівнювати 0.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if(isNumber){
                        JOptionPane.showMessageDialog(panelMain, "Вже існує користувач з таким номером телефону.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Введіть правильний номер телефону.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                companySystem.listNumbPhones.add(number);
                companySystem.listClients.add(new Client(surname, name, patronymic, number, adress, selectedManager));
                selectedManager.addClient(companySystem.listClients.getLast());

                tabbedPane1.setEnabledAt(2, true);
                Update();
                clearClientForm();
                saveManager.setEnabled(true);
                saveClient.setEnabled(true);
                saveContracts.setEnabled(true);

                JOptionPane.showMessageDialog(panelMain, "Клієнт успішно доданий.", "Успіх", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        chooseBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboClient.removeAllItems();
                String FIO = (String) comboManager.getSelectedItem();
                String phoneNumber = null;
                if (FIO.contains("+380")) {
                    int index = FIO.indexOf("+380") + 4;
                    phoneNumber = FIO.substring(index).trim();
                }
                if (phoneNumber != null) {
                    int phone = Integer.parseInt(phoneNumber);
                    managerInCombo = companySystem.findManagerByPhone(phone);
                }
                else {
                    JOptionPane.showMessageDialog(panelMain, "Помилка!");
                    return;
                }

                if(managerInCombo.getClient().isEmpty()){
                    JOptionPane.showMessageDialog(panelMain, "Менеджер немає клієнтів.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Client> clients = managerInCombo.getClient();
                for(int i = 0; i < clients.size(); i++){
                    comboClient.addItem(clients.get(i).getFullName() + " +380" + clients.get(i).getNumber());
                }

            }
        });

        //створити контракт
        CreateContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (managerInCombo == null) {
                    JOptionPane.showMessageDialog(panelMain, "Виберіть спочатку менеджера.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Client selectedClient = null;
                String FIO2 = (String) comboClient.getSelectedItem();
                if(FIO2 == null){
                    JOptionPane.showMessageDialog(panelMain, "Виберіть клієнта.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                comboClient.removeAllItems();
                String phoneNumber = null;
                if (FIO2.contains("+380")) {
                    int index = FIO2.indexOf("+380") + 4;
                    phoneNumber = FIO2.substring(index).trim();
                }
                if (phoneNumber != null) {
                    int phone = Integer.parseInt(phoneNumber);
                    selectedClient = companySystem.findClientByPhone(phone);
                }
                else {
                    JOptionPane.showMessageDialog(panelMain, "Помилка!");
                    return;
                }
                if(selectedClient == null){
                    JOptionPane.showMessageDialog(panelMain, "Оберіть клієнта", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String typeBuild = (String) typeBuildCombo.getSelectedItem();

                double plotArea;
                try {
                    plotArea = Double.parseDouble(plotAreaField.getText().trim());
                    if(plotArea < 100){
                        JOptionPane.showMessageDialog(panelMain, "Площа ділянки не може бути менше за 100 м^2.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Введіть правильну площу ділянки.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double areaBuilding;
                try {
                    areaBuilding = Double.parseDouble(areaBuildingField.getText().trim());
                    if(areaBuilding < 100){
                        JOptionPane.showMessageDialog(panelMain, "Площа будівлі не може бути менше за 100 м^2.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (areaBuilding >= plotArea){
                        JOptionPane.showMessageDialog(panelMain, "Площа будівлі не може бути більшою або рівною площі ділянки.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Введіть правильну площу будівлі.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int numberFloors;
                try {
                    numberFloors = Integer.parseInt(numberFloorsField.getText().trim());
                    if(numberFloors <= 0){
                        JOptionPane.showMessageDialog(panelMain, "Кількість поверхів не може бути менше або дорівнювати 0.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Введіть правильну кількість поверхів.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                LocalDate dateStart = null;
                LocalDate dateStop = null;

                try {
                    String dateStartF = dateStartField.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    dateStart = LocalDate.parse(dateStartF, formatter);
                    LocalDate today = LocalDate.now();
                    if (dateStart.isBefore(today)) {
                        JOptionPane.showMessageDialog(panelMain, "Дата початку не може бути минулою.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Невірний формат дати. Введіть дату в форматі dd-MM-yyyy.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    String dateStopF = dateStopField.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    dateStop = LocalDate.parse(dateStopF, formatter);
                    if (dateStop.isBefore(dateStart)) {
                        JOptionPane.showMessageDialog(panelMain, "Дата закінчення не може бути раніше дати початку.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (dateStop.isBefore(dateStart.plusWeeks(1))) {
                        JOptionPane.showMessageDialog(panelMain, "Різниця між датами повинна бути не менше ніж одна неділя.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Невірний формат дати. Введіть дату в форматі dd-MM-yyyy.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double cost;
                try {
                    cost = Double.parseDouble(costField.getText().trim());
                    if(cost < 1000){
                        JOptionPane.showMessageDialog(panelMain, "Вартість не може бути менше ніж 1000 грн", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panelMain, "Введіть правильну вартість.", "Помилка", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String street = streetContrField.getText();
                String city = cityContrField.getText();
                String numberHome = numberHouseContrField.getText();
                Adress adress = null;
                try {
                    adress = checkInputAdress(street, city, numberHome);
                    if (adress == null){
                        return;
                    }
                    if(!companySystem.checkAdress(adress)){
                        companySystem.listContracts.add(new Contract(managerInCombo, typeBuild, adress, plotArea, areaBuilding, cost, numberFloors, dateStart, dateStop));
                    }
                    else{
                        JOptionPane.showMessageDialog(panelMain, "Вже існує контракт з такою адресою.", "Помилка", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panelMain, "Помилка при створенні контракту.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
                managerInCombo.addContract(companySystem.listContracts.getLast());
                selectedClient.setIdContract(companySystem.listContracts.getLast().getId());
                Update();
                UpdateClients();
                saveManager.setEnabled(true);
                saveClient.setEnabled(true);
                saveContracts.setEnabled(true);
                JOptionPane.showMessageDialog(panelMain, "Контракт успішно створений.", "Успіх", JOptionPane.INFORMATION_MESSAGE);
                clearContractForm();

            }
        });

        //інфо менеджер
        ManagerListBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!companySystem.listManagers.isEmpty()){
                    StringBuilder resultText = new StringBuilder();
                    resultText.append("Список менеджерів:\n");
                    resultText.append(String.format("%-5s %-20s %-15s %-20s %-20s %-23s %-25s %-20s\n",
                            "№", "Прізвище", "Ім'я", "По батькові", "Номер телефону",
                            "Назва компанії", "Кількість клієнтів", "Кількість контрактів"));

                    for(int i = 0; i < companySystem.listManagers.size(); i++){
                        resultText.append(String.format("%-7d %s\n", i + 1, companySystem.listManagers.get(i).Info()));
                    }
                    resultArea.setText(resultText.toString());
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, додайте менеджера.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        //інфо клієнт
        ClientListBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listClients.isEmpty() && !companySystem.listManagers.isEmpty()){
                    resultText.append("Список клієнтів:\n");
                    resultText.append(String.format("%-5s %-45s %-20s %-15s %-20s %-20s %-20s %-30s %-30s %-10s\n",
                            "№", "Менеджер клієнта", "Прізвище", "Ім'я", "По батькові", "Номер телефону",
                            "Номер дому", "Вулиця", "Місто", "Id контракту"));
                    for(int i = 0; i < companySystem.listClients.size(); i++){
                        resultText.append(String.format("%-7d %s\n", i + 1, companySystem.listClients.get(i).Info()));
                    }
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, додайте клієнта.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //список контрактів
        ContractListBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    resultText.append("Список контрактів:\n");
                    resultText.append(String.format("%-5s %-40s %-20s %-20s %-30s %-30s %-30s %-20s %-20s %-30s %-30s %-30s %-30s %-10s\n",
                            "№", "Менеджер контракта", "Дата укладання", "Номер дому", "Вулиця", "Місто","Тип будівлі", "Площа ділянки", "Площа будівлі",
                            "Кількість поверхів", "Дата початку робіт", "Дата закінчення робіт", "Вартість", "Id контракту"));
                    for(int i = 0; i < companySystem.listContracts.size(); i++){
                        resultText.append(String.format("%-7d %s\n", i + 1, companySystem.listContracts.get(i).toString()));
                    }
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //популярна будівля
        popularBuildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    String timePeriod = "За весь час";
                    if (popularCombo.getSelectedItem().equals("За кварталами")) {
                        String[] options = {"1-й квартал", "2-й квартал", "3-й квартал", "4-й квартал"};
                        timePeriod = (String) JOptionPane.showInputDialog(
                                null,
                                "Виберіть проміжок часу:",
                                "Проміжок часу",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (timePeriod == null) {
                            return;
                        }
                    }
                    LocalDate dateBoundary = companySystem.getDateBoundary(timePeriod);
                    LocalDate endDate = companySystem.getEndDate(dateBoundary, timePeriod);
                    AtomicInteger buildingCount = new AtomicInteger();
                    AtomicInteger popularCount = new AtomicInteger();

                    List<String> popular = companySystem.FoundPopularBuild(dateBoundary, endDate, buildingCount, popularCount);
                    if(popular.isEmpty()){
                        JOptionPane.showMessageDialog(panelMain, "Найбільш популярної будівлі за обраний проміжок не знайдено.", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    resultText.append("Найбільш популярна будівля.");
                    if(!dateBoundary.equals(LocalDate.MIN)){
                        resultText.append("\n\nПроміжок: від " + dateBoundary + "  до  " + endDate + "  (" + buildingCount.get());
                        if(popular.size() == 1){
                            resultText.append(" будівлі, " + popularCount + " з них " + popular + " )");
                        }
                        else {
                            resultText.append(" будівлі, " + popularCount + " з них " + popular + " )");
                        }
                    }
                    else{
                        resultText.append("\n\nПроміжок: " + timePeriod + "  (" + buildingCount.get());
                        if(popular.size() == 1){
                            resultText.append(" будівлі, " + popularCount + " з них " + popular + " )");
                        }
                        else {
                            resultText.append(" будівлі, " + popularCount + " з них " + popular + " )");
                        }
                    }
                    resultText.append("\n\nТип будівлі: " + popular);
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //середня площа
        ArayBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    String timePeriod = "За весь час";
                    if (squareCombo.getSelectedItem().equals("За кварталами")) {
                        String[] options = {"1-й квартал", "2-й квартал", "3-й квартал", "4-й квартал"};
                        timePeriod = (String) JOptionPane.showInputDialog(
                                null,
                                "Виберіть проміжок часу:",
                                "Проміжок часу",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (timePeriod == null) {
                            return;
                        }
                    }
                    LocalDate dateBoundary = companySystem.getDateBoundary(timePeriod);
                    LocalDate endDate = companySystem.getEndDate(dateBoundary, timePeriod);
                    AtomicInteger buildingCount = new AtomicInteger();

                    double averageAray = companySystem.AverageArea(dateBoundary, endDate, buildingCount);
                    if(averageAray == 0){
                        JOptionPane.showMessageDialog(panelMain, "Не знайдено середньої площі за вказаний проміжок.", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    resultText.append("Середня площа будівлі.");
                    if(!dateBoundary.equals(LocalDate.MIN)){
                        resultText.append("\n\nПроміжок від " + dateBoundary + "  до  " + endDate + " (" + buildingCount.get() + " будівлі.)");
                    }
                    else
                        resultText.append("\n\nПроміжок: " + timePeriod + " (" + buildingCount.get() + " будівлі.)");
                    resultText.append("\n\nПлоща: " + averageAray +  " м^2.");
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //середній термін виконання контракту
        AverageTermBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    String timePeriod = "За весь час";
                    if (averageCombo.getSelectedItem().equals("За кварталами")) {
                        String[] options = {"1-й квартал", "2-й квартал", "3-й квартал", "4-й квартал"};
                        timePeriod = (String) JOptionPane.showInputDialog(
                                null,
                                "Виберіть проміжок часу:",
                                "Проміжок часу",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (timePeriod == null) {
                            return;
                        }
                    }

                    LocalDate dateBoundary = companySystem.getDateBoundary(timePeriod);
                    LocalDate endDate = companySystem.getEndDate(dateBoundary, timePeriod);
                    AtomicInteger buildingCount = new AtomicInteger();

                    String days = companySystem.AverageTerm(dateBoundary, endDate, buildingCount);
                    if(days.isEmpty()){
                        JOptionPane.showMessageDialog(panelMain, "Контрактів за обраний проміжок не знайдено.", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    resultText.append("Cередній термін виконання контракту.");
                    if(!dateBoundary.equals(LocalDate.MIN)){
                        resultText.append("\n\nПроміжок: від " + dateBoundary + "  до  " + endDate + "  (" + buildingCount.get() + " контрактів.)");
                    }
                    else
                        resultText.append("\n\nПроміжок: " + timePeriod + "  (" + buildingCount.get() + " контрактів.)");
                    resultText.append("\n\nТермін: " + days);
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //найбільш коштовний контракт
        ValuableContractBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    String timePeriod = "За весь час";
                    if (costCombo.getSelectedItem().equals("За кварталами")) {
                        String[] options = {"1-й квартал", "2-й квартал", "3-й квартал", "4-й квартал"};
                        timePeriod = (String) JOptionPane.showInputDialog(
                                null,
                                "Виберіть проміжок часу:",
                                "Проміжок часу",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (timePeriod == null) {
                            return;
                        }
                    }
                    LocalDate dateBoundary = companySystem.getDateBoundary(timePeriod);
                    LocalDate endDate = companySystem.getEndDate(dateBoundary, timePeriod);
                    AtomicInteger buildingCount = new AtomicInteger();

                    String contr = companySystem.ValuableContract(dateBoundary, endDate, buildingCount);
                    if(contr.isEmpty()){
                        JOptionPane.showMessageDialog(panelMain, "Контрактів за обраний проміжок не знайдено.", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    resultText.append("Найбільш коштовний контракт.");
                    if(!dateBoundary.equals(LocalDate.MIN)){
                        resultText.append("\n\nПроміжок: від " + dateBoundary + "  до  " + endDate + "  (" + buildingCount.get() + " контрактів)\n\n");;
                    }
                    else
                        resultText.append("\n\nПроміжок: " + timePeriod + "  (" + buildingCount.get() + " контрактів)\n\n");
                    resultText.append(String.format("%-10s %-25s %-40s %-20s %-30s\n",
                            "№", "Id контракту", "Менеджер контракта", "Ціна", "Дата створення"));

                    resultText.append(contr);
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //Список клієнтів, які вибрали певний тип будівлі.
        FindClientsBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder resultText = new StringBuilder();
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    String timePeriod = "За весь час";

                    if (Type2TimeCombo.getSelectedItem().equals("За кварталами")) {
                        String[] options = {"1-й квартал", "2-й квартал", "3-й квартал", "4-й квартал"};
                        timePeriod = (String) JOptionPane.showInputDialog(
                                null,
                                "Виберіть проміжок часу:",
                                "Проміжок часу",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                        if (timePeriod == null) {
                            return;
                        }
                    }
                    LocalDate dateBoundary = companySystem.getDateBoundary(timePeriod);
                    LocalDate endDate = companySystem.getEndDate(dateBoundary, timePeriod);
                    AtomicInteger buildingCount = new AtomicInteger();

                    String typeBuild = (String) Type2combo.getSelectedItem();
                    ArrayList<String> clients = companySystem.FoundClients(typeBuild, dateBoundary, endDate, buildingCount);

                    if(clients.isEmpty()){
                        JOptionPane.showMessageDialog(panelMain, "Не знайдено клієнтів.", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    resultText.append("Список клієнтів, які вибрали тип будівлі " + typeBuild.toLowerCase());
                    if(!dateBoundary.equals(LocalDate.MIN)){
                        resultText.append("\n\nПроміжок: від " + dateBoundary + "  до  " + endDate + "  (" + buildingCount.get() + " контрактів, " + clients.size() + " із них " + typeBuild.toLowerCase() + ")\n\n");
                    }
                    else
                        resultText.append("\n\nПроміжок: " + timePeriod + "  (" + buildingCount.get() + " контрактів, " + clients.size() + " із них " + typeBuild.toLowerCase() + ")\n\n");
                    resultText.append(String.format("%-10s %-30s %-40s %-20s %-30s\n",
                            "№", "Id контракту", "ПІБ клієнта", "Номер телефону", "Дата створення"));
                    for (int i = 0; i < clients.size(); i++){
                        resultText.append(String.format("%-20d", i + 1));
                        resultText.append(clients.get(i));
                    }
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
                resultArea.setText(resultText.toString());
            }
        });

        //видалити контракт
        DeleteContractBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!companySystem.listContracts.isEmpty() && !companySystem.listManagers.isEmpty()){
                    StringBuilder contractList = new StringBuilder("Список контрактів:\n");
                    for (int i = 0; i < companySystem.listContracts.size(); i++) {
                        contractList.append((i + 1)).append(". ")
                                .append("Id контракту: ")
                                .append(companySystem.listContracts.get(i).getId())
                                .append("\n");
                    }

                    String input = JOptionPane.showInputDialog(null, contractList.toString() +
                                    "\nВведіть номер контракту для видалення:", "Видалити контракт",
                            JOptionPane.QUESTION_MESSAGE);

                    /*UpdateClients();*/
                    if (input != null) {
                        try {
                            int contractNumber = Integer.parseInt(input.trim());
                            if (contractNumber > 0 && contractNumber <= companySystem.listContracts.size()) {
                                contractNumber--;


                                Client client = companySystem.FoundClientsId(companySystem.listContracts.get(contractNumber).getId()); //знаходимо клієнта контракта
                                if(client == null){
                                    JOptionPane.showMessageDialog(null, "Помилка.",
                                            "Помилка", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                System.out.println(client);
                                client.deleteId(companySystem.listContracts.get(contractNumber).getId()); //видаляємо id у списку клієнтів

                                Manager manager = companySystem.listContracts.get(contractNumber).getManager();  //отримуємо менеджера контракта
                                manager.deleteContract(companySystem.listContracts.get(contractNumber).getId()); //видаляємо контракт у менеджері
                                manager.deleteClientId(companySystem.listContracts.get(contractNumber).getId()); //видаляємо id клієнта у менеджері

                                companySystem.listContracts.remove(contractNumber); //видаляємо контракт
                                Update();
                                UpdateClients();
                                saveManager.setEnabled(true);
                                saveClient.setEnabled(true);
                                saveContracts.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Контракт успішно видалено.",
                                        "Успіх", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Невірний номер контракта.",
                                        "Помилка", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Будь ласка, введіть коректний номер.",
                                    "Помилка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, створіть контракт.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        //видалити клієнта
        DeleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!companySystem.listClients.isEmpty() && !companySystem.listManagers.isEmpty()){
                    JOptionPane.showMessageDialog(panelMain, "Якщо видалите клієнта, то видалиться його контракт.", "Увага", JOptionPane.WARNING_MESSAGE);
                    StringBuilder listClients = new StringBuilder("Список клієнтів:\n");
                    for (int i = 0; i < companySystem.listClients.size(); i++) {
                        listClients.append((i + 1)).append(". ")
                                .append(companySystem.listClients.get(i).getFullName())
                                .append("\n");
                    }

                    String input = JOptionPane.showInputDialog(null, listClients.toString() +
                                    "\nВведіть номер клієнта для видалення:", "Видалити клієнта",
                            JOptionPane.QUESTION_MESSAGE);
                    if (input != null) {
                        try {
                            int clientNumber = Integer.parseInt(input.trim());
                            if (clientNumber > 0 && clientNumber <= companySystem.listClients.size()) {
                                clientNumber--;

                                Client client = companySystem.listClients.get(clientNumber);                  //отримуємо клієнта
                                List<Integer> idClient = companySystem.listClients.get(clientNumber).getId(); //отримуємо список всіх id клієнта
                                client.getManager().deleteContract(idClient);                                 //видаляємо контракти клієнта у менеджері
                                client.getManager().deleteClient(client.number);                              //видаляємо клієнтів у менеджері
                                for (int i = 0; i < idClient.size(); i++){                                    //видаляємо контракти у головному списку
                                    for (int j = companySystem.listContracts.size() - 1; j >= 0; j--){
                                        if(idClient.get(i) == companySystem.listContracts.get(j).getId()){
                                            companySystem.listContracts.remove(j);
                                        }
                                    }
                                }
                                companySystem.deleteNumber(companySystem.listClients.get(clientNumber).getNumber()); //видаляємо номер
                                companySystem.listClients.remove(clientNumber);                                      //видаляємо клієнта у головному списку
                                Update();
                                UpdateClients();
                                saveManager.setEnabled(true);
                                saveClient.setEnabled(true);
                                saveContracts.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Клієнт успішно видалений.",
                                        "Успіх", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Невірний номер клієнта.",
                                        "Помилка", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Будь ласка, введіть коректний номер.",
                                    "Помилка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, додайте клієнта.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        //завантажити
        loadBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "C:\\JAVA\\coursework 2\\save\\saveManagers.txt";
                companySystem.listManagers = companySystem.loadManagers(filename);
                String filename2 = "C:\\JAVA\\coursework 2\\save\\saveClients.txt";
                companySystem.listClients = companySystem.loadClients(filename2);
                String filename3 = "C:\\JAVA\\coursework 2\\save\\saveContracts.txt";
                companySystem.listContracts = companySystem.loadContracts(filename3);
                String filename4 = "C:\\JAVA\\coursework 2\\save\\savePhones.txt";
                companySystem.listNumbPhones = companySystem.loadPhoneNumbers(filename4);
                JOptionPane.showMessageDialog(panelMain, "Успішне завантаження.", "Успіх", JOptionPane.INFORMATION_MESSAGE);
                saveManager.setEnabled(false);
                saveClient.setEnabled(false);
                saveContracts.setEnabled(false);
                if (!companySystem.listContracts.isEmpty()){
                    companySystem.setIdCounter();
                    Update();
                    UpdateClients();
                }
                if(!companySystem.listManagers.isEmpty()){
                    tabbedPane1.setEnabledAt(1, true);
                    Update();
                    UpdateClients();
                }
                if(!companySystem.listManagers.isEmpty() && !companySystem.listClients.isEmpty()){
                    tabbedPane1.setEnabledAt(2, true);
                    Update();
                    UpdateClients();
                }
            }
        });

        //видалити менеджера
        deleteManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!companySystem.listManagers.isEmpty()){
                    JOptionPane.showMessageDialog(panelMain, "Якщо видалите менеджера, то видаляться його клієнти та контракти.", "Увага", JOptionPane.WARNING_MESSAGE);
                    StringBuilder listManagers = new StringBuilder("Список менеджерів:\n");
                    for (int i = 0; i < companySystem.listManagers.size(); i++) {
                        listManagers.append((i + 1)).append(". ")
                                .append(companySystem.listManagers.get(i).getFullName())
                                .append("\n");
                    }

                    String input = JOptionPane.showInputDialog(null, listManagers.toString() +
                                    "\nВведіть номер менеджера для видалення:", "Видалити менеджера",
                            JOptionPane.QUESTION_MESSAGE);
                    if (input != null) {
                        try {
                            int managerNumber = Integer.parseInt(input.trim());
                            if (managerNumber > 0 && managerNumber <= companySystem.listManagers.size()) {
                                managerNumber--;

                                Manager manager = companySystem.listManagers.get(managerNumber); //отримуємо менеджера
                                System.out.println(manager.getClient());
                                System.out.println(manager.getContract());
                                System.out.println(companySystem.listClients);
                                System.out.println(companySystem.listContracts);
                                List<Client> clients = manager.getClient();                      //отримуємо список всіх клієнтів менеджера

                                System.out.println(clients);

                                for(int i = clients.size() - 1; i >= 0; i--){
                                    for (int j = companySystem.listClients.size() - 1; j >= 0; j--){
                                        if(clients.get(i).getNumber() == companySystem.listClients.get(j).getNumber()){
                                            companySystem.deleteNumber(companySystem.listClients.get(i).getNumber()); //видаляємо номери клієнтів
                                            companySystem.listClients.remove(j);                                      //видаляємо клієнтів у головному списку
                                        }
                                    }
                                }
                                System.out.println(manager.getClient());
                                System.out.println(manager.getContract());
                                System.out.println(companySystem.listClients);
                                System.out.println(companySystem.listContracts);
                                List<Contract> contracts = manager.getContract();                                      //отримуємо список всіх контрактів менеджера
                                for(int i = 0; i < contracts.size(); i++){
                                    for (int j = companySystem.listContracts.size() - 1; j >= 0; j--){
                                        if(contracts.get(i).getId() == companySystem.listContracts.get(j).getId()){
                                            companySystem.listContracts.remove(j);                                     //видаляємо контракти у головному списку
                                        }
                                    }
                                }
                                System.out.println(manager.getClient());
                                System.out.println(manager.getContract());
                                System.out.println(companySystem.listClients);
                                System.out.println(companySystem.listContracts);

                                companySystem.deleteNumber(companySystem.listManagers.get(managerNumber).getNumber());
                                manager.getContract().clear();
                                manager.getClient().clear();
                                System.out.println(manager.getClient());
                                System.out.println(manager.getContract());
                                System.out.println(companySystem.listClients);
                                System.out.println(companySystem.listContracts);
                                companySystem.listManagers.remove(managerNumber);                                      //видаляємо менеджера з головного списку
                                Update();
                                UpdateClients();
                                saveManager.setEnabled(true);
                                saveClient.setEnabled(true);
                                saveContracts.setEnabled(true);
                                JOptionPane.showMessageDialog(null, "Менеджер успішно видалений.",
                                        "Успіх", JOptionPane.INFORMATION_MESSAGE);

                            } else {
                                JOptionPane.showMessageDialog(null, "Невірний номер менеджера.",
                                        "Помилка", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Будь ласка, введіть коректний номер.",
                                    "Помилка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(panelMain, "Будь ласка, додайте менеджера.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        //зберігти менеджера
        saveManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "C:\\JAVA\\coursework 2\\save\\saveManagers.txt";
                companySystem.saveManagers(filename);
                String filename2 = "C:\\JAVA\\coursework 2\\save\\saveClients.txt";
                companySystem.saveClient(filename2);
                String filename3 = "C:\\JAVA\\coursework 2\\save\\saveContracts.txt";
                companySystem.saveContract(filename3);
                String filename4 = "C:\\JAVA\\coursework 2\\save\\savePhones.txt";
                companySystem.saveNumbPhones(filename4);
                saveManager.setEnabled(false);
                saveClient.setEnabled(false);
                saveContracts.setEnabled(false);
                JOptionPane.showMessageDialog(panelMain, "Успішно збережено.", "Успіх", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        //зберегти клієнта
        saveClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "C:\\JAVA\\coursework 2\\save\\saveManagers.txt";
                companySystem.saveManagers(filename);
                String filename2 = "C:\\JAVA\\coursework 2\\save\\saveClients.txt";
                companySystem.saveClient(filename2);
                String filename3 = "C:\\JAVA\\coursework 2\\save\\saveContracts.txt";
                companySystem.saveContract(filename3);
                String filename4 = "C:\\JAVA\\coursework 2\\save\\savePhones.txt";
                companySystem.saveNumbPhones(filename4);
                saveManager.setEnabled(false);
                saveClient.setEnabled(false);
                saveContracts.setEnabled(false);
                JOptionPane.showMessageDialog(panelMain, "Успішно збережено.", "Успіх", JOptionPane.INFORMATION_MESSAGE);

            }
        });


        //зберегти контракт
        saveContracts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "C:\\JAVA\\coursework 2\\save\\saveManagers.txt";
                companySystem.saveManagers(filename);
                String filename2 = "C:\\JAVA\\coursework 2\\save\\saveClients.txt";
                companySystem.saveClient(filename2);
                String filename3 = "C:\\JAVA\\coursework 2\\save\\saveContracts.txt";
                companySystem.saveContract(filename3);
                String filename4 = "C:\\JAVA\\coursework 2\\save\\savePhones.txt";
                companySystem.saveNumbPhones(filename4);
                saveManager.setEnabled(false);
                saveClient.setEnabled(false);
                saveContracts.setEnabled(false);
                JOptionPane.showMessageDialog(panelMain, "Успішно збережено.", "Успіх", JOptionPane.INFORMATION_MESSAGE);

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CompanyForm");
        frame.setContentPane(new CompanyForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
