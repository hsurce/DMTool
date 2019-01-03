package XMLHandler;

import java.io.Serializable;

public class HitDie implements Serializable {

    static final long serialVersionUID = 102;
    public int getDieSize() {
        return dieSize;
    }

    public void setDieSize(int dieSize) {
        this.dieSize = dieSize;
    }


    public int getDieAmount() {
        return dieAmount;
    }

    public void setDieAmount(int dieAmount) {
        this.dieAmount = dieAmount;
    }

    private int dieAmount;
    private int dieSize;
    public HitDie(int dieAmount, int dieSize){
        this.dieAmount = dieAmount;
        this.dieSize = dieSize;
    }
}
