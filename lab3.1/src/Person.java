import java.io.Serializable;
abstract class Person implements Serializable  {
    protected String surname, name, patronymic;
    protected int number;

    public Person(String surname, String name, String patronymic, int number){
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.number = number;
    }

    public String getFullName(){
        return surname + " " + name + " " + patronymic;
    }

    public int getNumber() {
        return number;
    }

    public abstract String Info();
}
