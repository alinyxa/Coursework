import java.io.Serializable;

public class Adress implements Serializable {
    private int numberHome;
    private String street;
    private String city;

    public Adress (int numberHome, String street, String city){
        this.numberHome = numberHome;
        this.street = street;
        this.city = city;
    }

    public boolean isEqualAdress(Adress other){
        if (this == other) return true;
        if (other == null) return false;

        if(this.numberHome != other.numberHome)
            return false;

        return this.street.equals(other.street) && this.city.equals(other.city);
    }

    @Override
    public String toString(){
        return String.format("%-28s %-28s %-30s",
                numberHome,
                street,
                city
        );
    }

    public String toText() {
        return numberHome + "," + street + "," + city;
    }
}
