package XMLHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class Trait implements Serializable {

    static final long serialVersionUID = 107;
    private String name;
    private ArrayList<String> texts;

    public Trait(String name, ArrayList<String> texts){
        this.name = name;
        this.texts = texts;
    }

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
}
