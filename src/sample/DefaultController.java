package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import XMLHandler.XMLHandler;

import java.io.IOException;


public class DefaultController {
    GlobalController globalController;
    XMLHandler xmlh;

    public void initialize(GlobalController globalController) {
        this.globalController = globalController;
        this.xmlh = globalController.getXmlh();

        MonsterButton.setOnAction(e -> {
            try {
                monsterButtonClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        CombatButton.setOnAction(e -> {
            try {
                CombatButtonClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        // SPELL BUTTON

        SpellButton.setOnAction(e -> {
            try {
                spellButtonClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // ITEM BUTTON

        ItemsButton.setOnAction(e -> {
            try {
                itemButtonClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // MAGIC ITEM BUTTON

        MagicItemsButton.setOnAction(e -> {
            try {
                magicItemButtonClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // NAME GENERATOR BUTTON

        NameGeneratorButton.setOnAction(e -> {
            try {
                nameGeneratorButtonClicked();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    public void monsterButtonClicked() throws Exception {
        monster();
    }
    public void spellButtonClicked() throws Exception {
        spell();
    }
    public void itemButtonClicked() throws Exception {
        items();
    }
    public void magicItemButtonClicked() throws Exception {
        magicItems();
    }
    public void nameGeneratorButtonClicked() throws Exception {
        nameGenerator();
    }

    private void CombatButtonClicked() throws Exception {
        combat();
    }

    public void nameGenerator() throws Exception {

        this.splitPane.getItems().set(1, globalController.getNameGeneratorController().content);
    }

    private void combat() throws IOException {

        this.splitPane.getItems().set(1, globalController.getCombatController().content);
    }

    public void monster() throws Exception {

        this.splitPane.getItems().set(1, globalController.getMonsterController().content);
    }


    public void spell() throws Exception{

        this.splitPane.getItems().set(1, globalController.getSpellController().content);
    }

    public void items() throws Exception{

        this.splitPane.getItems().set(1, globalController.getItemController().content);
    }

    public void magicItems() throws Exception{

        this.splitPane.getItems().set(1, globalController.getMagicItemController().content);

    }
    @FXML
    public Button ItemsButton;

    @FXML
    public Button MonsterButton;

    @FXML
    public Button SpellButton;

    @FXML
    public Button MagicItemsButton;

    @FXML
    public SplitPane splitPane;

    @FXML
    public Button NameGeneratorButton;

    @FXML
    public Button CombatButton;

    @FXML
    public AnchorPane content;

}
