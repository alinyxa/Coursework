import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person  implements Serializable {
    private List<Integer> idContract;
    private Adress adress;
    private Manager manager;

    public Client (String surname, String name, String patronymic, int number, Adress adress, Manager manager){
        super(surname, name, patronymic, number);
        idContract = new ArrayList<>();
        this.adress = adress;
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public List<Integer> getId(){
        return idContract;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setIdContract(int id) {
        idContract.add(id);
    }

    public boolean isEqualId (int id){
        System.out.println("Количество ID у клиента: " + idContract.size());
        for (int i = 0; i < idContract.size(); i++){
            System.out.println("Проверка ID клиента: " + idContract.get(i) + " == " + id);
            if(idContract.get(i) == id)
                return true;
        }
        return false;
    }

    public void deleteId(int id){
        for (int i = 0; i < idContract.size(); i++){
            if(idContract.get(i) == id)
                idContract.remove(i);
        }
    }

    public String ShowId (){
        StringBuilder ID = new StringBuilder();
        for(int i = 0; i < idContract.size(); i++){
            ID.append(idContract.get(i));
            if (i < idContract.size() - 1) {
                ID.append(", ");
            }
        }
        return ID.toString();
    }

    @Override
    public String Info(){
        String contractInfo = idContract.isEmpty() ? "Немає id контракту" : "Id контракту: " + ShowId();
        return String.format(
                "%-37s %-18s %-15s %-20s %-20s %-10s %-35s",
                manager.getFullName(),
                surname,
                name,
                patronymic,
                "+380" + number,
                adress.toString(),
                contractInfo );
    }

    public String toText() {
        String managerPhone = (manager != null) ? String.valueOf(manager.getNumber()) : "null"; // Сохраняем номер телефона менеджера
        StringBuilder builder = new StringBuilder();
        builder.append(surname).append(",")
                .append(name).append(",")
                .append(patronymic).append(",")
                .append(number).append(",")
                .append(adress.toText()).append(",")
                .append(managerPhone).append("\n");

        for (Integer contractId : idContract) {
            builder.append("ContractId: ").append(contractId).append("\n");
        }
        return builder.toString();
    }
}
