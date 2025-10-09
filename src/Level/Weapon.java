package Level;


/**
 * Definition of a weapon type
 */
public class Weapon extends Item {
    
    protected double baseDamage = 10;
    protected double weaponSkillDamage;
    protected double weaponSkillCost = 5;

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

    public Weapon(String name, String description, double baseDamage) {
        super(name);
        this.description = description;
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

    public double bonusDamage(Entity entity) {
        return 0;
    }

    public double getWeaponSkillDamage() {
        return this.weaponSkillDamage;
    }

    public double getWeaponSkillCost() {
        return this.weaponSkillCost;
    }

    
}
