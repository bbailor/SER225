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
        this(id, name, 1);
    }
    
    public Weapon(String id, String name, int maxStackSize) {
        this(id, name, maxStackSize, 10d);
    }

    public Weapon(String id, String name, String description, int maxStackSize) {
        this(id, name, description, maxStackSize, 10d);
    }

    public Weapon(String id, String name, double baseDamage) {
        this(id, name, "", baseDamage);
    }

    public Weapon(String id, String name, String description, double baseDamage) {
        this(id, name, description, 1, baseDamage);
    }

    public Weapon(String id, String name, String description, int maxStackSize, double baseDamage) {
        super(id, name, description, maxStackSize);
        this.baseDamage = baseDamage;
        this.weaponSkillDamage = this.baseDamage * 1.5;
    }
    
    public Weapon(String name, int maxStackSize, double baseDamage) {
        this(name, name, maxStackSize, baseDamage);
    }

    public Weapon(String name, String description, int maxStackSize, double baseDamage) {
        this(name.replace(' ', '_').toLowerCase(), name, description, maxStackSize, baseDamage);
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

    /**
     * Returns the critical chance multiplier for this weapon.
     * Default is 1.0 (normal chance). Override to increase/decrease crit chance.
     * @return multiplier for critical chance (2.0 = double chance, 0.5 = half chance)
     */
    public double getCriticalChanceMultiplier() {
        return 1.0;
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
