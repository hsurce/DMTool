package XMLHandler;

import java.io.Serializable;

public class AttackAction implements Serializable {

    static final long serialVersionUID = 101;
    public int getToHit() {
        return toHit;
    }

    public void setToHit(int toHit) {
        this.toHit = toHit;
    }

    public HitDie getHitDie() {
        return hitDie;
    }

    public void setHitDie(HitDie hitDie) {
        this.hitDie = hitDie;
    }


    public int getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(int damageBonus) {
        this.damageBonus = damageBonus;
    }


    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    private Action action;
    private int toHit;
    private HitDie hitDie;
    private int damageBonus;

    public AttackAction(Action action, int toHit, HitDie hitDie, int damageBonus){
        this.action = action;
        this.toHit = toHit;
        this.hitDie = hitDie;
        this.damageBonus = damageBonus;
    }

}
