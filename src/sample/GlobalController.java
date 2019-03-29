package sample;

import XMLHandler.XMLHandler;
import javafx.stage.Stage;
import sample.Popups.Popup;

import java.util.HashMap;

/**
 * Created by Jakob on 1/7/2019.
 */
public class GlobalController {



    private XMLHandler xmlh;
    private CombatController combatController;
    private MonsterController monsterController;
    private SpellController spellController;
    private ItemController itemController;
    private MagicItemController magicItemController;
    private NameGeneratorController nameGeneratorController;
    private MonsterCreatorController monsterCreatorController;
    private DefaultController defaultController;
    private HashMap<String, Popup> checkPopupMap;
    private Stage primaryStage;

    public GlobalController(XMLHandler xmlh, CombatController combatController, MonsterController monsterController,
                            SpellController spellController, ItemController itemController, MagicItemController magicItemController,
                            NameGeneratorController nameGeneratorController, MonsterCreatorController monsterCreatorController,
                            DefaultController defaultController)
    {
        this.checkPopupMap = new HashMap<>();
        this.xmlh = xmlh;
        this.combatController = combatController;
        this.monsterController = monsterController;
        this.spellController = spellController;
        this.itemController = itemController;
        this.magicItemController = magicItemController;
        this.nameGeneratorController = nameGeneratorController;
        this.monsterCreatorController = monsterCreatorController;
        this.defaultController = defaultController;

    }

    public XMLHandler getXmlh() {
        return xmlh;
    }

    public CombatController getCombatController() {
        return combatController;
    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public SpellController getSpellController() {
        return spellController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public MagicItemController getMagicItemController() {
        return magicItemController;
    }

    public NameGeneratorController getNameGeneratorController() {
        return nameGeneratorController;
    }

    public void checkForDuplicatePopup(Popup popup) {
        if(checkPopupMap.containsKey(popup.getStageTitle())){
            Popup p = checkPopupMap.get(popup.getStageTitle());
            p.closeStage();
            checkPopupMap.remove(popup.getStageTitle());
        }
        checkPopupMap.put(popup.getStageTitle(), popup);
    }

    public MonsterCreatorController getMonsterCreatorController() {
        return monsterCreatorController;
    }

    public DefaultController getDefaultController() {
        return defaultController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
