package XMLHandler;

import java.io.Serializable;

public class SpellHitDie implements Serializable {

    static final long serialVersionUID = 201;
    private int dieAmount;
    private int dieSize;
    private int bonus;
    public SpellHitDie(int dieAmount, int dieSize, int bonus){
        this.dieAmount = dieAmount;
        this.dieSize = dieSize;
        this.bonus = bonus;
    }

    public int getDieAmount() {
        return dieAmount;
    }

    public void setDieAmount(int dieAmount) {
        this.dieAmount = dieAmount;
    }

    public int getDieSize() {
        return dieSize;
    }

    public void setDieSize(int dieSize) {
        this.dieSize = dieSize;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
