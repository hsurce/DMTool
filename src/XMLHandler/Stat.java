package XMLHandler;

import java.io.Serializable;

public class Stat implements Serializable {

    static final long serialVersionUID = 106;
    private String name;
    private int value;
    public Stat(String name, int value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static class StatBuilder{
        String nestedName;
        int nestedValue;

        public void name(String nestedName){
            this.nestedName = nestedName;
        }
        public void value(int nestedValue){
            this.nestedValue = nestedValue;
        }
        public Stat BuildStat(){
            Stat stat = new Stat(nestedName,nestedValue);
            return stat;
        }
    }
}
