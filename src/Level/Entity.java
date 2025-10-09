package Level;

import java.util.HashMap;

import GameObject.Frame;

public class Entity {
    
    protected double health = 10;
    protected double maxHealth = 10;
    protected double mana = 30;
    protected double maxMana = 30;
    protected double baseAttack = 1;
    protected double resistance = 0;
    protected double tempResistance = 0;
    protected Weapon currentWeapon = Item.ItemList.fist;
    protected java.util.Map<String, Frame[]> animations = new HashMap<>(); 

    public double getMana() {
        return this.mana;
    }

    public void setMana(double mana) {
        this.mana = mana;
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
        return this.currentWeapon;
    }

    public void setCurrentWeapon(Weapon current_weapon) {
        this.currentWeapon = current_weapon;
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
        return this.baseAttack + this.currentWeapon.baseDamage;
    }
    public double getAttack(Entity entity) {
        return this.getAttack() + this.currentWeapon.bonusDamage(entity);
    }

    public double getSkillAttack() {
        return this.baseAttack + this.currentWeapon.getWeaponSkillDamage();
    }

    public double getSkillAttack(Entity entity) {
        return this.getSkillAttack() + this.currentWeapon.bonusDamage(entity);
    }

    public double handleDamage(Entity from, boolean fromSkill) {
        var totalResistance = this.resistance + this.tempResistance;
        this.tempResistance = 0;
        if (fromSkill) {
            double remainingHealth = this.health -= Math.max(from.getSkillAttack(this) - totalResistance, 0);
            from.mana -= this.currentWeapon.getWeaponSkillCost();
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
}
