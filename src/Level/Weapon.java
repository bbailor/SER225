package Level;


/**
 * Definition of a weapon type
 */
public class Weapon extends Item {
    
    protected double baseDamage = 10;

    public Weapon(String name) {
        super(name);
    }
    
    public Weapon(String name, int maxStackSize) {
        super(name, maxStackSize);
    }

    public Weapon(String name, String description, int maxStackSize) {
        super(name, description, maxStackSize);
    }

    public Weapon(String name, double baseDamage) {
        super(name);
        this.baseDamage = baseDamage;
    }
    
    public Weapon(String name, int maxStackSize, double baseDamage) {
        super(name, maxStackSize);
        this.baseDamage = baseDamage;
    }

    public Weapon(String name, String description, int maxStackSize, double baseDamage) {
        super(name, description, maxStackSize);
        this.baseDamage = baseDamage;
    }

    public double getBaseDamage() {
        return this.baseDamage;
    }
}
