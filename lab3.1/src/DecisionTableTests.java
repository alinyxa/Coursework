import java.time.LocalDate;

public class DecisionTableTests {

    private static int passed = 0;
    private static int failed = 0;
    private static CompanySystem system = new CompanySystem();

    public static void main(String[] args) {
        setupTestData();

        testNoManager();
        testNoClient();
        testInvalidAddress();
        testInvalidArea();
        testInvalidFloors();
        testInvalidCost();
        testInvalidDates();
        testNegativeValues();
        testSuccess();

        System.out.println("Пройдено: " + passed);
        System.out.println("Провалено: " + failed);
        System.out.println("Всього тестів: " + (passed + failed));
    }

    private static void setupTestData() {
        Manager manager = new Manager("Іванов", "Петро", "Сергійович", 991122333, "BuildCorp");
        Adress address = new Adress(10, "Грушевського", "Одеса");
        Client client = new Client("Сидоренко", "Марія", "Андріївна", 671234567, address, manager);
        system.listManagers.add(manager);
        system.listClients.add(client);
    }

    static void testNoManager() {
        String result = createContract(null, system.listClients.get(0),
                new Adress(5, "Соборна", "Київ"), "Житловий будинок", 200, 150, 100000, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: не обрано менеджера", result, "testNoManager");
    }

    static void testNoClient() {
        String result = createContract(system.listManagers.get(0), null,
                new Adress(5, "Соборна", "Київ"), "Житловий будинок", 200, 150, 100000, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: не обрано клієнта", result, "testNoClient");
    }

    static void testInvalidAddress() {
        Adress adr = new Adress(0, "", "");
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                adr, "Житловий будинок", 200, 150, 100000, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: некоректна адреса", result, "testInvalidAddress");
    }

    static void testInvalidArea() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"), "Житловий будинок", 0, 0, 100000, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: площа некоректна", result, "testInvalidArea");
    }

    static void testInvalidFloors() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"), "Офісна будівля", 150, 100, 100000, 0,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: поверхи некоректні", result, "testInvalidFloors");
    }

    static void testInvalidCost() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"), "Офісна будівля", 150, 100, 0, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: вартість некоректна", result, "testInvalidCost");
    }

    static void testInvalidDates() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"), "Офісна будівля", 150, 100, 100000, 3,
                LocalDate.now().plusDays(30), LocalDate.now());
        assertResult("Помилка: дати некоректні", result, "testInvalidDates");
    }

    static void testNegativeValues() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"), "Офісна будівля", -100, -50, -100000, -2,
                LocalDate.now(), LocalDate.now().plusDays(30));
        assertResult("Помилка: значення не можуть бути від’ємними", result, "testNegativeValues");
    }

    static void testSuccess() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"), "Офісна будівля", 150, 100, 250000, 3,
                LocalDate.now(), LocalDate.now().plusDays(60));
        assertResult("Контракт створено успішно", result, "testSuccess");
    }

    static void assertResult(String expected, String actual, String testName) {
        if (expected.equals(actual)) {
            System.out.println(testName + " — пройдено (" + actual + ")");
            passed++;
        } else {
            System.out.println(testName + " — провалено");
            System.out.println("   Очікувано: " + expected);
            System.out.println("   Отримано:  " + actual);
            failed++;
        }
    }

    static String createContract(Manager manager, Client client, Adress address,
                                 String typeBuilding, double plotArea, double areaBuilding,
                                 double cost, int floors, LocalDate start, LocalDate end) {

        if (manager == null) return "Помилка: не обрано менеджера";
        if (client == null) return "Помилка: не обрано клієнта";
        if (address == null || address.toString().contains("null") || address.toString().trim().isEmpty())
            return "Помилка: некоректна адреса";
        if (plotArea <= 0 || areaBuilding <= 0) return "Помилка: площа некоректна";
        if (floors <= 0) return "Помилка: поверхи некоректні";
        if (cost <= 0) return "Помилка: вартість некоректна";
        if (start.isAfter(end)) return "Помилка: дати некоректні";
        if (plotArea < 0 || areaBuilding < 0 || cost < 0 || floors < 0)
            return "Помилка: значення не можуть бути від’ємними";

        Contract contract = new Contract(manager, typeBuilding, address, plotArea,
                areaBuilding, cost, floors, start, end);
        system.listContracts.add(contract);
        return "Контракт створено успішно";
    }
}
