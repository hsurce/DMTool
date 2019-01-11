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

    public static class LegendaryActionBuilder{
        String nestedName;
        ArrayList<String> nestedTexts;
        int nestedLegendaryCost;
        public LegendaryActionBuilder(){
            nestedTexts = new ArrayList<>();
        }
        public void name(String nestedName){
            this.nestedName = nestedName;
        }
        public void text(String nestedText){
            this.nestedTexts.add(nestedText);
        }
        public void cost(int nestedLegendaryCost){this.nestedLegendaryCost = nestedLegendaryCost;}
        public LegendaryAction BuildLegendaryAction(){
            LegendaryAction legendaryAction = new LegendaryAction(nestedName,nestedTexts,nestedLegendaryCost);
            return legendaryAction;
        }

    }

}
