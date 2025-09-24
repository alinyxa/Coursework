import java.io.Serializable;
import java.time.LocalDate;
public class Contract implements Serializable {
    private static int idCounter = 0;
    private int id;  /*id контракту*/
    private Manager manager;
    private String typeBuilding;  /*тип будівлі*/
    private Adress adress;  /*адреса*/
    private double plotArea, areaBuilding, cost;  /*площа ділянки, площа будівлі, вартість*/
    private int numberFloors;  /*кількість поверхів*/
    private LocalDate dateStart;  /*дата початку робіт*/
    private LocalDate dateStop;  /*дата закінчення робіт*/
    private LocalDate dateConclusion; /*дата складання контракту*/

    public Contract (Manager manager, String typeBuilding, Adress adress, double plotArea, double areaBuilding, double cost, int numberFloors, LocalDate dateStart, LocalDate dateStop){
        this.id = ++idCounter;
        this.manager = manager;
        this.typeBuilding = typeBuilding;
        this.adress = adress;
        this.plotArea = plotArea;
        this.areaBuilding = areaBuilding;
        this.cost = cost;
        this.numberFloors = numberFloors;
        this.dateStart = dateStart;
        this.dateStop = dateStop;
        this.dateConclusion = LocalDate.now();
    }

    public String getTypeBuilding() {
        return typeBuilding;
    }

    public Manager getManager() {
        return manager;
    }

    public Adress getAdress() {
        return adress;
    }

    public int getId() {
        return id;
    }

    public double getAreaBuilding() {
        return areaBuilding;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateStop() {
        return dateStop;
    }

    public LocalDate getDateConclusion() {
        return dateConclusion;
    }

    public double getCost() {
        return cost;
    }

    public static void setIdCounter(int counter){
        idCounter = counter;
    }

    @Override
    public String toString(){
        return String.format(
                "%-33s %-25s %-15s %-30s %-25s %-48s %-30s %-40s %-20s %-45s %-30s",
                manager.getFullName(),
                dateConclusion,
                adress.toString(),
                typeBuilding,
                plotArea + " (м^2)",
                areaBuilding + " (м^2)",
                numberFloors,
                dateStart,
                dateStop,
                cost + " грн",
                id
        );

    }

    public String toText() {
        return  id + "," +
                manager.getNumber() + "," +
                typeBuilding + "," +
                adress.toText() + "," +
                plotArea + "," +
                areaBuilding + "," +
                cost + "," +
                numberFloors + "," +
                dateStart + "," +
                dateStop + "," +
                dateConclusion;
    }
}
