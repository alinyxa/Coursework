import org.junit.jupiter.api.*;
import java.time.LocalDate;

class Lab4TestJUnit {

    private static CompanySystem system;

    @BeforeAll
    static void setup() {
        system = new CompanySystem();

        Manager manager = new Manager("Іванов", "Петро", "Сергійович",
                991122333, "BuildCorp");
        Adress address = new Adress(10, "Грушевського", "Одеса");
        Client client = new Client("Сидоренко", "Марія", "Андріївна",
                671234567, address, manager);

        system.listManagers.add(manager);
        system.listClients.add(client);
    }

    @Test
    @DisplayName("21 — не обрано менеджера")
    void testNoManager() {
        String result = createContract(null, system.listClients.get(0),
                new Adress(5, "Соборна", "Київ"),
                "Житловий будинок", 200, 150, 100000, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));

        Assertions.assertEquals("Помилка: не обрано менеджера", result);
    }

    @Test
    @DisplayName("22 — не обрано клієнта")
    void testNoClient() {
        String result = createContract(system.listManagers.get(0), null,
                new Adress(5, "Соборна", "Київ"),
                "Житловий будинок", 200, 150, 100000, 3,
                LocalDate.now(), LocalDate.now().plusDays(30));

        Assertions.assertEquals("Помилка: не обрано клієнта", result);
    }

    @Test
    @DisplayName("24 — площа або поверхи некоректні")
    void testInvalidAreaOrFloors() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"),
                "Офісна будівля", 0, 150, 100000, 0,
                LocalDate.now(), LocalDate.now().plusDays(30));

        Assertions.assertEquals("Помилка: площа або поверхи некоректні", result);
    }

    @Test
    @DisplayName("27 — контракт створено успішно")
    void testSuccess() {
        String result = createContract(system.listManagers.get(0), system.listClients.get(0),
                new Adress(3, "Київська", "Львів"),
                "Офісна будівля", 150, 100, 250000, 3,
                LocalDate.now(), LocalDate.now().plusDays(60));

        Assertions.assertEquals("Контракт створено успішно", result);
    }

    static String createContract(Manager manager, Client client, Adress address,
                                 String typeBuilding, double plotArea, double areaBuilding,
                                 double cost, int floors, LocalDate start, LocalDate end) {

        if (manager == null) return "Помилка: не обрано менеджера";
        if (client == null) return "Помилка: не обрано клієнта";


        if (plotArea <= 0 || floors <= 0)
            return "Помилка: площа або поверхи некоректні";
        if (cost <= 0)
            return "Помилка: вартість некоректна";
        if (start.isAfter(end))
            return "Помилка: дати некоректні";

        Contract contract = new Contract(manager, typeBuilding, address,
                plotArea, areaBuilding, cost, floors, start, end);

        system.listContracts.add(contract);
        return "Контракт створено успішно";
    }
}
