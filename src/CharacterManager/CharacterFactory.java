package CharacterManager;

import java.util.ArrayList;

public class CharacterFactory implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    public static Lycanthrope createLycanthrope(String name, int age, int height) {
        return new Lycanthrope(
            name,
            age,
            height,
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            100,               
            10,                
            5,                 
            new ArrayList<>(),
            new ArrayList<>()  
        );
    }

    public static Hunter createHunter(String name, int goodness) {
        return new Hunter(
            name,
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(), 
            new ArrayList<>(),
            100,              
            15,            
            8,               
            new ArrayList<>(), 
            new ArrayList<>(), 
            goodness           
        );
    }

    public static Vampire createVampire(String name, int bloodPoints, int age) {
        return new Vampire(
            name,
            new ArrayList<>(), 
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(), 
            new ArrayList<>(),
            new ArrayList<>(), 
            100,             
            12,                
            7,             
            new ArrayList<>(), 
            new ArrayList<>(),
            bloodPoints,      
            age              
        );
    }
}