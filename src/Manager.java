import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Manager extends Person implements Serializable {
    private String companyName;
    private List <Client> clients = new ArrayList<>();
    private List <Contract> contracts = new ArrayList<>();

    public Manager (String surname, String name, String patronymic, int number, String companyName){
        super(surname, name, patronymic, number);
        this.companyName = companyName;
    }

    public List<Client> getClient() {
        return clients;
    }

    public List<Contract> getContract() {
        return contracts;
    }

    public void addClient (Client client){
        clients.add(client);
    }

    public void addContract (Contract contract){
        contracts.add(contract);
    }

    public void deleteContract(int id){
        for (int i = 0; i < contracts.size(); i++){
            if(contracts.get(i).getId() == id){
                contracts.remove(i);
            }
        }
    }

    public void deleteClientId(int id){
        for(int i = 0; i < clients.size(); i++){
            clients.get(i).deleteId(id);
        }
    }

    public void deleteClient(int number){
        for (int i = 0; i < clients.size(); i++){
            if(clients.get(i).getNumber() == number){
                clients.remove(i);
            }
        }
    }

    public void deleteContract(List<Integer> id){
        for (int i = 0; i < id.size(); i++){
            for(int j = 0; j < contracts.size(); j++){
                if(id.get(i) == contracts.get(j).getId()){
                    contracts.remove(j);
                }
            }
        }
    }

    public boolean checkClientsCounts(){
        return clients.size() < 5;
    }


    @Override
    public String Info(){
        return String.format("%-17s %-15s %-20s %-20s %-40s %-40s %-35s",
                surname,
                name,
                patronymic,
                "+380" + number,
                companyName,
                clients.size() + "/5",
                contracts.size());
    }

    public String toText() {
        StringBuilder builder = new StringBuilder();
        builder.append(surname).append(",")
                .append(name).append(",")
                .append(patronymic).append(",")
                .append(number).append(",")
                .append(companyName).append("\n");

        for (Client client : clients) {
            builder.append(client.toText()).append("\n");
        }

        for (Contract contract : contracts) {
            builder.append("Contract: ").append(contract.toText()).append("\n");
        }

        return builder.toString();
    }

}
