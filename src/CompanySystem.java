import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompanySystem {
    List<Contract> listContracts = new ArrayList<>();
    List<Manager> listManagers = new ArrayList<>();
    List<Client> listClients = new ArrayList<>();
    List<Integer> listNumbPhones = new ArrayList<>();

    //Найбільш популярна будівля
    public List<String> FoundPopularBuild(LocalDate dateBoundary, LocalDate endDate, AtomicInteger totalCount, AtomicInteger popularCount){
        int maxCount = 0;

        Map<String, Integer> buildingCountMap = new HashMap<>();
        totalCount.set(0);
        popularCount.set(0);

        for (Contract contract : listContracts) {
            LocalDate contractDate = contract.getDateConclusion();
            if ((contractDate.isEqual(dateBoundary) || contractDate.isAfter(dateBoundary)) && contractDate.isBefore(endDate)) {
                String build = contract.getTypeBuilding();
                buildingCountMap.put(build, buildingCountMap.getOrDefault(build, 0) + 1);
                totalCount.incrementAndGet();
            }
        }

        List<String> popularBuildings = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : buildingCountMap.entrySet()) {
            int count = entry.getValue();
            String buildingType = entry.getKey();

            if (count > maxCount) {
                maxCount = count;
                popularBuildings.clear();
                popularBuildings.add(buildingType);
                popularCount.set(count);
            } else if (count == maxCount) {
                popularBuildings.add(buildingType);
                popularCount.addAndGet(count);
            }
        }

        return popularBuildings;
    }

    //Список клієнтів, які вибрали певний тип будів
    public ArrayList<String> FoundClients(String typeBuild, LocalDate dateBoundary, LocalDate endDate, AtomicInteger totalCount){
        ArrayList<String> clients = new ArrayList<>();
        Set<Integer> addedClientPhones = new HashSet<>();
        totalCount.set(0);

        for (Contract contract : listContracts) {
            LocalDate contractDate = contract.getDateConclusion();
            if ((contractDate.isEqual(dateBoundary) || contractDate.isAfter(dateBoundary)) && contractDate.isBefore(endDate)){
                totalCount.incrementAndGet();
                if(contract.getTypeBuilding().equals(typeBuild)) {
                    Client client = FoundClientsId(contract.getId());
                    if (client != null) {
                        int phone = client.getNumber();
                        if (!addedClientPhones.contains(phone)) {
                            String clientInfo = String.format(
                                    "%-20s %-40s %-22s %-30s\n",
                                    client.getId(),
                                    client.getFullName(),
                                    "+380" + client.getNumber(),
                                    contract.getDateConclusion()
                            );
                            clients.add(clientInfo);
                            addedClientPhones.add(phone);
                        }
                    }
                }
            }
        }
        return clients;
    }

    public Client FoundClientsId (int id){
        for (int i = 0; i < listClients.size(); i++){
            System.out.println("Ищем клиента с ID контракта: " + id);
            if(listClients.get(i).isEqualId(id)){
                return listClients.get(i);
            }
        }
        return null;
    }

    public List<LocalDate> FoundDateConclusion(List <Integer> id){
        List<LocalDate> dateConclusion = new ArrayList<>();
        for (int i = 0; i < id.size(); i++){
            for(int j = 0; j < listContracts.size(); j++){
                if(id.get(i) == listContracts.get(j).getId()){
                    dateConclusion.add(listContracts.get(j).getDateConclusion());
                }
            }
        }
        return dateConclusion;
    }

    //Середня площа будівлі
    public double AverageArea(LocalDate dateBoundary, LocalDate endDate, AtomicInteger totalCount){
        double sum = 0;
        totalCount.set(0);

        for (Contract contract : listContracts){
            LocalDate contractDate = contract.getDateConclusion();
            if ((contractDate.isEqual(dateBoundary) || contractDate.isAfter(dateBoundary)) && contractDate.isBefore(endDate)) {
                sum += contract.getAreaBuilding();
                totalCount.incrementAndGet();
            }
        }
        if (totalCount.get() == 0)
            return 0;

        return Math.round((sum / totalCount.get()) * 10.0) / 10.0;
    }

    //Середній термін виконання контракту
    public String  AverageTerm(LocalDate dateBoundary, LocalDate endDate, AtomicInteger totalCount){
        long totalDays = 0;
        int count = 0;
        totalCount.set(0);

        for (Contract contract : listContracts) {
            LocalDate contractDate = contract.getDateConclusion();
            if ((contractDate.isEqual(dateBoundary) || contractDate.isAfter(dateBoundary)) && contractDate.isBefore(endDate)) {
                long daysBetween = ChronoUnit.DAYS.between(contract.getDateStart(), contract.getDateStop());
                totalDays += daysBetween;
                count++;
                totalCount.incrementAndGet();
            }
        }

        if (count == 0) {
            return "";
        }

        long averageDays = totalDays / count;

        LocalDate startDate = LocalDate.ofEpochDay(0);
        LocalDate endDates = startDate.plusDays(averageDays);
        Period period = Period.between(startDate, endDates);

        StringBuilder result = new StringBuilder();
        if (period.getYears() > 0) {
            result.append(period.getYears()).append(" роки ");
        }
        if (period.getMonths() > 0) {
            result.append(period.getMonths()).append(" місяців ");
        }
        if (period.getDays() > 0) {
            result.append(period.getDays()).append(" днів");
        }

        return result.toString().trim();
    }

    //Найбільш коштовний контракт
    public String ValuableContract(LocalDate dateBoundary, LocalDate endDate, AtomicInteger totalCount){
        Contract contr = null;
        totalCount.set(0);

        List<Contract> contracts = new ArrayList<>();

        for (int i = 0; i < listContracts.size(); i++) {
            LocalDate contractDate = listContracts.get(i).getDateConclusion();
            Contract contract = listContracts.get(i);
            if ((contractDate.isEqual(dateBoundary) || contractDate.isAfter(dateBoundary)) && contractDate.isBefore(endDate)) {
                if (contr == null || contract.getCost() > contr.getCost()) {
                    contr = contract;
                }
                totalCount.incrementAndGet();
            }
        }

        if (contr != null) {
            for (Contract contract : listContracts) {
                LocalDate contractDate = contract.getDateConclusion();
                if ((contractDate.isEqual(dateBoundary) || contractDate.isAfter(dateBoundary)) && contractDate.isBefore(endDate) &&
                        contract.getCost() == contr.getCost()) {
                    contracts.add(contract);
                }
            }
        }
        StringBuilder result = new StringBuilder();
        int counter = 1;
        for (Contract contract : contracts) {
            result.append(String.format(
                    "%-13d %-30s %-33s %-28.1f %-25s\n",
                    counter++,
                    contract.getId(),
                    contract.getManager().getFullName(),
                    contract.getCost(),
                    contract.getDateConclusion()
            ));
        }
        return result.toString();
    }

    public LocalDate getDateBoundary(String timePeriod) {
        LocalDate currentDate = LocalDate.now();
        return switch (timePeriod) {
            case "1-й квартал" -> LocalDate.of(currentDate.getYear(), 1, 1);
            case "2-й квартал" -> LocalDate.of(currentDate.getYear(), 4, 1);
            case "3-й квартал" -> LocalDate.of(currentDate.getYear(), 7, 1);
            case "4-й квартал" -> LocalDate.of(currentDate.getYear(), 10, 1);
            default -> LocalDate.MIN;
        };
    }

    public LocalDate getEndDate(LocalDate dateBoundary, String timePeriod){
        LocalDate endDate;
        endDate = switch (timePeriod) {
            case "1-й квартал" -> dateBoundary.plusMonths(3);
            case "2-й квартал" -> dateBoundary.plusMonths(3);
            case "3-й квартал" -> dateBoundary.plusMonths(3);
            case "4-й квартал" -> dateBoundary.plusMonths(3);
            default -> LocalDate.now().plusDays(1);
        };
        return endDate;
    }

    public Manager findManagerByPhone (int phone) {
        for (int i = 0; i < listManagers.size(); i++) {
            if (listManagers.get(i).getNumber() == phone) {
                return listManagers.get(i);
            }
        }
        return null;
    }

    public Client findClientByPhone (int phone) {
        for (int i = 0; i < listClients.size(); i++) {
            if (listClients.get(i).getNumber() == phone) {
                return listClients.get(i);
            }
        }
        return null;
    }

    public boolean managersClients(){
        boolean isCount = false;
        for(int i = 0; i < listManagers.size(); i++){
            if(listManagers.get(i).checkClientsCounts()){
                isCount = true;
                return isCount;
            }
        }
        return isCount;
    }

    public boolean CheckNumber(int numb) {
        return listNumbPhones.contains(numb);
    }

    public void deleteNumber(int number){
        for (int i = 0; i < listNumbPhones.size(); i++){
            if(listNumbPhones.get(i) == number)
                listNumbPhones.remove(i);
        }
    }

    public boolean checkAdress(Adress adress){
        for (int i = 0; i < listContracts.size(); i++){
            if(listContracts.get(i).getAdress().isEqualAdress(adress)){
                return true;
            }
        }
        return false;
    }

    public void setIdCounter(){
        int maxCounter = 0;
        for (int i = 0; i < listContracts.size(); i++) {
            if (listContracts.get(i).getId() > maxCounter) {
                maxCounter = listContracts.get(i).getId();
            }
        }
        Contract.setIdCounter(maxCounter);
    }

    public void saveManagers(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Manager manager : listManagers) {
                writer.write(manager.toText());
                writer.write("=====\n");
            }
        } catch (IOException e) {
            System.err.println("Помилка при збереженні " + e.getMessage());
        }
    }

    public void saveClient(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {  // true для добавления в конец файла
            for(int i = 0; i < listClients.size(); i++){
                writer.write(listClients.get(i).toText());
                writer.write("=====\n");
            }
            System.out.println("Успішно збережено список клієнтів у файл " + filename);
        } catch (IOException e) {
            System.err.println("Помилка " + e.getMessage());
        }
    }

    public void saveContract(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contract contract : listContracts) {

                // Сохраняем данные контракта
                writer.write(contract.toText());
                writer.write("=====\n");
            }
            System.out.println("Контракты успешно сохранены в файл " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении контракта: " + e.getMessage());
        }
    }

    public void saveNumbPhones(String filename){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Integer phoneNumber : listNumbPhones) {
                writer.write(phoneNumber.toString());
                writer.newLine();
            }
            System.out.println("Список номеров телефонов сохранен в файл " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении номеров телефонов: " + e.getMessage());
        }
    }

    private static Contract findContractById(int contractId, Manager manager) {
        for (Contract contract : manager.getContract()) {
            if (contract.getId() == contractId) {
                return contract;
            }
        }
        return null;
    }

    public List<Manager> loadManagers(String filename){
        List<Manager> managers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Manager currentManager = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Розділювач між менеджерами
                if (line.equals("=====")) {
                    if (currentManager != null) {
                        managers.add(currentManager);
                    }
                    currentManager = null;
                    continue;
                }

                // Розподіл даних
                String[] parts = line.split(",");
                if (parts.length >= 5 && currentManager == null) {
                    // Парсинг даних менеджера
                    String surname = parts[0].trim();
                    String name = parts[1].trim();
                    String patronymic = parts[2].trim();

                    int number = parseIntSafely(parts[3].trim());
                    if (number == -1) {
                        System.err.println("Невірний формат номера телефону менеджера: " + parts[3].trim());
                        continue;
                    }

                    String companyName = parts[4].trim();
                    currentManager = new Manager(surname, name, patronymic, number, companyName);
                    continue;
                }

                // Додавання контрактів до менеджера
                if (line.startsWith("ContractId:") && currentManager != null) {
                    try {
                        int contractId = Integer.parseInt(line.split(":")[1].trim());
                        Contract contract = findContractById(contractId, currentManager);
                        if (contract != null) {
                            currentManager.addContract(contract);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Помилка при ContractId: " + line);
                    }
                    continue;
                }


                if (currentManager != null && parts.length >= 7 && line.startsWith("Client:")) {
                    line = line.replaceFirst("Client:", "").trim();
                    String[] clientParts = line.split(",");
                    try {
                        String clientSurname = clientParts[0].trim();
                        String clientName = clientParts[1].trim();
                        String clientPatronymic = clientParts[2].trim();

                        int clientNumber = parseIntSafely(clientParts[3].trim());
                        if (clientNumber == -1) {
                            System.err.println("Невірний формат номера телефону клієнта: " + clientParts[3].trim());
                            continue;
                        }

                        int numberHome = parseIntSafely(clientParts[4].trim());
                        if (numberHome == -1) {
                            System.err.println("Невірний формат номера будинку: " + clientParts[4].trim());
                            continue;
                        }

                        String street = clientParts[5].trim();
                        String city = clientParts[6].trim();

                        Adress adress = new Adress(numberHome, street, city);
                        Client client = new Client(clientSurname, clientName, clientPatronymic, clientNumber, adress, currentManager);
                        currentManager.addClient(client);
                    } catch (Exception e) {
                        System.err.println("Помилка при розборі клієнта: " + e.getMessage());
                    }
                }
            }

            // Додавання останнього менеджера
            if (currentManager != null) {
                managers.add(currentManager);
            }

        } catch (IOException e) {
            System.err.println("Помилка під час читання файлу: " + e.getMessage());
        }

        return managers;
    }

    public List<Client> loadClients(String filename){
        List<Client> listClients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Client currentClient = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.equals("=====")) {
                    if (currentClient != null) {
                        listClients.add(currentClient);
                    }
                    currentClient = null;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String surname = parts[0];
                    String name = parts[1];
                    String patronymic = parts[2];

                    int number = 0;
                    try {
                        number = Integer.parseInt(parts[3].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Неправильний формат телефону: " + parts[3]);
                        continue;
                    }

                    Adress adress = null;
                    try {
                        adress = new Adress(Integer.parseInt(parts[4].trim()), parts[5], parts[6]);
                    } catch (NumberFormatException e) {
                        System.err.println("Невірний формат даних адреси: " + parts[4] + ", " + parts[5] + ", " + parts[6]);
                        continue;
                    }

                    currentClient = new Client(surname, name, patronymic, number, adress, null);


                    String managerPhoneString = parts[7]; // Получаем номер телефона менеджера
                    if (!managerPhoneString.equals("null")) {
                        int managerPhone = Integer.parseInt(managerPhoneString);
                        for (Manager manager : listManagers) {
                            if (manager.getNumber() == managerPhone) {
                                currentClient.setManager(manager);
                                manager.addClient(currentClient);
                                break;
                            }
                        }
                    }
                } else if (line.startsWith("ContractId:") && currentClient != null) {
                    try {
                        int contractId = Integer.parseInt(line.split(":")[1].trim());
                        currentClient.setIdContract(contractId);
                    } catch (NumberFormatException e) {
                        System.err.println("Невірний формат contractId: " + line.split(":")[1]);
                    }
                }
            }

            if (currentClient != null) {
                listClients.add(currentClient);
            }

        } catch (IOException e) {
            System.err.println("Помилка під час завантаження списку клієнтів " + e.getMessage());
        }
        return listClients;
    }

    public List<Contract> loadContracts(String filename) {
        List<Contract> listContracts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Contract currentContract = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("=====")) { // Проверяем наличие разделителя
                    if (currentContract != null) {
                        listContracts.add(currentContract);
                    }
                    currentContract = null;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 12) {
                    try {
                        int managerPhoneNumber = parseInt(parts[1].trim());
                        if (managerPhoneNumber == -1) {
                            System.err.println("Невірний формат телефонного номера менеджера: " + parts[1].trim());
                            continue;
                        }

                        Manager manager = findManagerByPhone(managerPhoneNumber);
                        if (manager == null) {
                            System.err.println("Менеджер з номером телефону " + managerPhoneNumber + " не знайден.");
                            continue;
                        }

                        String typeBuilding = parts[2].trim();

                        Adress adress = null;
                        try {
                            int numberHome = parseInt(parts[3].trim());
                            if (numberHome == -1) {
                                System.err.println("Невірний формат номера будинку: " + parts[3].trim());
                                continue;
                            }
                            String street = parts[4].trim();
                            String city = parts[5].trim();

                            adress = new Adress(numberHome, street, city);
                        } catch (Exception e) {
                            System.err.println("Помилка при розборі адреси: " + e.getMessage());
                            continue;
                        }

                        double plotArea = parseDouble(parts[6].trim());
                        if (plotArea == -1) {
                            System.err.println("Невірний формат площі ділянки: " + parts[6].trim());
                            continue;
                        }

                        double areaBuilding = parseDouble(parts[7].trim());
                        if (areaBuilding == -1) {
                            System.err.println("Невірний формат площі будівлі: " + parts[7].trim());
                            continue;
                        }

                        double cost = parseDouble(parts[8].trim());
                        if (cost == -1) {
                            System.err.println("Невірний формат вартості: " + parts[8].trim());
                            continue;
                        }

                        int numberFloors = parseInt(parts[9].trim());
                        if (numberFloors == -1) {
                            System.err.println("Неправильний формат кількості поверхів: " + parts[9].trim());
                            continue;
                        }

                        LocalDate dateStart = parseDate(parts[10].trim());
                        if (dateStart == null) {
                            System.err.println("Невірний формат дати початку: " + parts[10].trim());
                            continue;
                        }

                        LocalDate dateStop = parseDate(parts[11].trim());
                        if (dateStop == null) {
                            System.err.println("Неправильний формат дати закінчення: " + parts[11].trim());
                            continue;
                        }

                        currentContract = new Contract(manager, typeBuilding, adress, plotArea, areaBuilding, cost, numberFloors, dateStart, dateStop);
                        manager.addContract(currentContract);
                    } catch (Exception e) {
                        System.err.println("Помилка при розборі рядка контракту: " + e.getMessage());
                        continue;
                    }
                }
            }

            if (currentContract != null) {
                listContracts.add(currentContract);
            }

        } catch (IOException e) {
            System.err.println("Помилка під час завантаження контракту: " + e.getMessage());
        }

        return listContracts;
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            System.err.println("Помилка при розборі дати: " + value);
            return null;
        }
    }

    public List<Integer> loadPhoneNumbers(String filename) {
        List<Integer> listNumbPhones = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int phoneNumber = Integer.parseInt(line.trim());
                    listNumbPhones.add(phoneNumber);
                } catch (NumberFormatException e) {
                    System.err.println("Помилка при розборі номера телефону: " + line);
                }
            }
            System.out.println("Список номерів телефонів завантажено з файлу " + filename);
        } catch (IOException e) {
            System.err.println("Помилка при завантаженні телефонних номерів: " + e.getMessage());
        }

        return listNumbPhones;
    }

    private int parseIntSafely(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.err.println("Неправильний формат числа: " + str);
            return -1;
        }
    }
}