package Level;

import Engine.Inventory.NamedSlot;

/**
 * Definition of a weapon type
 */
public class Weapon extends Item {
    
    protected double baseDamage = 10;
    protected double weaponSkillDamage;
    protected double weaponSkillCost = 5;

    public Weapon(String id, String name) {
        super(id, name, 1);
    }
    
    public Weapon(String id, String name, int maxStackSize) {
        super(id, name, maxStackSize);
    }

    public Weapon(String id, String name, String description, int maxStackSize) {
        super(id, name, description, maxStackSize);
    }

    public Weapon(String id, String name, double baseDamage) {
        super(id, name);
        this.baseDamage = baseDamage;
    }

    public Weapon(String id, String name, String description, double baseDamage) {
        super(id, name, description, 1);
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

    public String getAttackAnimationName() {
        return "fist"; // Default animation
    }

    @Override
    public boolean canUse(ItemStack stack, Entity targetedEntity) {
        return true;
    }

    @Override
    public void use(ItemStack stack, Entity targetedEntity) {
        System.out.println("Use from weapon class" + this);
        targetedEntity.getInventory().setStack(NamedSlot.Weapon, stack.copy());
        stack.removeItem();
    }
}
