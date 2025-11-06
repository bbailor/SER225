package Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.gson.annotations.Expose;

import Engine.Inventory;
import Engine.Inventory.NamedSlot;
import GameObject.Frame;

public class Entity {
    
    @Expose protected double health = 10;
    @Expose protected double maxHealth = 10;
    @Expose protected double mana = 30;
    @Expose protected double maxMana = 30;
    @Expose protected double baseAttack = 1;
    @Expose protected double resistance = 0;
    @Expose protected double tempResistance = 0;
    // @Expose protected Weapon currentWeapon = Item.ItemList.fist;
    @Expose protected Inventory inventory = new Inventory(90);
    protected boolean isEnemy;
    protected java.util.Map<String, Frame[]> animations = new HashMap<>(); 

    // Lines 29-77 for enemy multiple attacks system
    protected List<EnemyAttack> availableAttacks = new ArrayList<>();
    protected Random random = new Random();

    public static class EnemyAttack {
        public String attackName;
        public int weight; // Higher weight = more likely to be selected
        public String animationType;
        public double damage;
        
        public EnemyAttack(String attackName, int weight, String animationType, double damage) {
            this.attackName = attackName;
            this.weight = weight;
            this.animationType = animationType;
            this.damage = damage;
        }
    }

    // Add an attack to this entity's attack pool
    public void addAttack(String attackName, int weight, String animationType, double damage) {
        availableAttacks.add(new EnemyAttack(attackName, weight, animationType, damage));
    }

    // Select a random attack based on weights
    public EnemyAttack selectAttack() {
        if (availableAttacks.isEmpty()) {
            currentSelectedAttack = null;
            return null; // No attacks configured, use default behavior
        }

        // Calculate total weight
        int totalWeight = 0;
        for (EnemyAttack attack : availableAttacks) {
            totalWeight += attack.weight;
        }

        // Select random value between 0 and totalWeight
        int randomValue = random.nextInt(totalWeight);

        // Find which attack corresponds to this value
        int currentWeight = 0;
        for (EnemyAttack attack : availableAttacks) {
            currentWeight += attack.weight;
            if (randomValue < currentWeight) {
                currentSelectedAttack = attack;
                return attack;
            }
        }

        // Fallback (should never reach here)
        currentSelectedAttack = availableAttacks.get(0);
        return availableAttacks.get(0);
    }

    public double getEnemyAttackDamage() {
        // If enemy has attacks configured, use the selected attack's damage
        if (currentSelectedAttack != null) {
            return this.baseAttack + currentSelectedAttack.damage;
        }
        // Fallback to baseAttack only
        return this.baseAttack;
    }

    // Add field to store currently selected attack
    private EnemyAttack currentSelectedAttack = null;

    // Get all available attacks
    public List<EnemyAttack> getAvailableAttacks() {
        return availableAttacks;
    }

    public double getMana() {
        return this.mana;
    }

    public void setMana(double mana) {
        this.mana = Math.min(mana, this.maxMana);
    }

    public double getMaxMana() {
        return this.maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
    }

    public double getBaseAttack() {
        return this.baseAttack;
    }

    public void setBaseAttack(double baseAttack) {
        this.baseAttack = baseAttack;
    }

    public double getResistance() {
        return this.resistance;
    }

    public double getTempResistance() {
        return this.resistance;
    }

    public void setTempResistance(double resistance) {
        this.tempResistance = resistance;
    }

    public Weapon getCurrentWeapon() {
        return (Weapon) this.inventory.getStack(NamedSlot.Weapon).getItem();
    }

    public double heal(double amount) {
        // return might change if anti healed or any other section from child classes
        this.health = Math.min(this.health + amount, this.maxHealth);
        return amount;
    }
    
    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public double getAttack() {
        Weapon weapon = this.getCurrentWeapon();
        System.out.println(getCurrentWeapon());
        if(Math.random() >= 0.90) {
            System.out.println("Critical Strike! Damage: " + (this.baseAttack + weapon.baseDamage + (weapon.baseDamage * 0.5)));
            return this.baseAttack + weapon.baseDamage + (weapon.baseDamage * 0.5);
        }
        else{
            System.out.println("Damage: " + (this.baseAttack + weapon.baseDamage));
            return this.baseAttack + weapon.baseDamage;
        }
    }

    public double getAttack(Entity entity) {
    // If this is an enemy (has attacks configured), use enemy attack damage
    if (this.isEnemy && !availableAttacks.isEmpty()) {
        if (Math.random() >= 0.90) {
            double critDamage = getEnemyAttackDamage() * 1.5;
            System.out.println("Critical Strike! Damage: " + critDamage);
            return critDamage;
        } else {
            System.out.println("Damage: " + getEnemyAttackDamage());
            return getEnemyAttackDamage();
        }
    }
    
    // Player damage uses weapon
    Weapon weapon = this.getCurrentWeapon();
    System.out.println(getCurrentWeapon());
    if (Math.random() >= 0.90) {
        System.out.println("Critical Strike! Damage: " + this.baseAttack + weapon.baseDamage + (weapon.baseDamage * 0.5));
        return this.baseAttack + weapon.baseDamage + (weapon.baseDamage * 0.5);
    } else {
        System.out.println("Damage: " + this.baseAttack + weapon.baseDamage);
        return this.baseAttack + weapon.baseDamage;
    }
}

    public double getSkillAttack() {
        Weapon weapon = this.getCurrentWeapon();
        if (Math.random() >= 0.90) {
            System.out.println("Critical Strike! Damage: " + this.baseAttack + weapon.getWeaponSkillDamage() + (0.5 * weapon.getWeaponSkillDamage()));
            return this.baseAttack + weapon.getWeaponSkillDamage() + (0.5 * weapon.getWeaponSkillDamage());
        }
        else{
            return this.baseAttack + weapon.getWeaponSkillDamage();
        }
    }

    public double getSkillAttack(Entity entity) {
        return this.getSkillAttack() + this.getCurrentWeapon().bonusDamage(entity);
    }

    public double handleDamage(Entity from, boolean fromSkill) {
        var totalResistance = this.resistance + this.tempResistance;
        this.tempResistance = 0;
        if (fromSkill) {
            double remainingHealth = this.health -= Math.max(from.getSkillAttack(this) - totalResistance, 0);
            from.mana -= this.getCurrentWeapon().getWeaponSkillCost();
            return remainingHealth;
        }
        return this.health -= Math.max(from.getAttack(this) - totalResistance, 0);
    }

    public boolean isAlive() {
        return this.health <= 0;
    }

    public void kill() {
        this.health = 0;
    }

    public Frame[] getAnimations(String name) {
        return this.animations.get(name);
    }

    public java.util.Map<String, Frame[]> getAllAnimations() {
        return this.animations;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Entity() {}

    public Entity(double maxHealth, double maxMana) {
        this.maxHealth = this.health = maxHealth;
        this.maxMana = this.mana = maxMana;
        this.isEnemy = false;
    }
    public Entity(double maxHealth, double maxMana, boolean isEnemy) {
        this.maxHealth = this.health = maxHealth;
        this.maxMana = this.mana = maxMana;
        this.isEnemy = isEnemy;
    }
    public Entity(double maxHealth, double maxMana, double resistance) {
        this(maxHealth, maxMana);
        this.resistance = resistance;
    }
}
