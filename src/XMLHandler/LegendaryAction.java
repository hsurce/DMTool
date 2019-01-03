package XMLHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class LegendaryAction extends Action implements Serializable {

    static final long serialVersionUID = 104;
    public int getLegendaryCost() {
        return legendaryCost;
    }

    public void setLegendaryCost(int legendaryCost) {
        this.legendaryCost = legendaryCost;
    }

    private int legendaryCost;
    public LegendaryAction(String name, ArrayList<String> texts, int legendaryCost){
        super(name, texts);
        super.setName(name);
        super.setTexts(texts);
        this.legendaryCost = legendaryCost;
    }

}
