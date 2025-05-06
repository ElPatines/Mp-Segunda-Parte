package CharacterManager;


public class Armor implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int power;
    private int price;

    public Armor(int power, int price, String name){
        this.power = power;
        this.price = price;
        this.name = name;
    }

    public int getPower(){
        return power;
    }

     public int getPrice(){
        return price;
    }

    public String getName(){
        return name;
    }
    
    public void setPower(int power){
        this.power = power;
    }
    public void setPrice(int price){
        this.price = price;
    }

    public void setName(String name){
        this.name = name;
    }
    
}
