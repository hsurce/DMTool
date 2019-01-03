package XMLHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class Action implements Serializable {

    static final long serialVersionUID = 100;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<String> texts) {
        this.texts = texts;
    }


    private String name;
    private ArrayList<String> texts;
    public Action(String name, ArrayList<String> texts) {
        this.name = name;
        this.texts = texts;
    }

}
