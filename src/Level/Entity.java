package Level;

import java.util.HashMap;

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
    }

    public double getAttack() {
        Weapon weapon = this.getCurrentWeapon();
        if(Math.random() >= 0.90) {
            System.out.println("Critical Strike! Damage: " + this.baseAttack + weapon.baseDamage + (weapon.baseDamage * 0.5));
            return this.baseAttack + weapon.baseDamage + (weapon.baseDamage * 0.5);
        }
        else{
            System.out.println("Damage: " + this.baseAttack + weapon.baseDamage);
            return this.baseAttack + weapon.baseDamage;
        }
    }
    public double getAttack(Entity entity) {
        return this.getAttack() + this.getCurrentWeapon().bonusDamage(entity);
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
