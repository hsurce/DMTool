package sample;

// Imports

import XMLHandler.XMLHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main extends Application {
    // Controllers
    LoginController loginController;
    DefaultController defaultController;
    CombatController combatController;
    MonsterController monsterController;
    SpellController spellController;
    ItemController itemController;
    MagicItemController magicItemController;
    NameGeneratorController nameGeneratorController;
    boolean hasCombat = false;
    // Creating a double, to record and calculate frame x,y coordinates
    private double x,y;
    private double positionX = 0;
    private double positionY = 0;
    XMLHandler xmlh;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            xmlh = new XMLHandler();
            /**
            System.out.println(xmlh.getMonsterHashMap());
            System.out.println(xmlh.getSpellHashMap());
             */
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("XMLFiles/LoginLayout.fxml"));
            Parent loginroot = fxmlLoader.load();
            loginController = fxmlLoader.getController();
            primaryStage.setScene(new Scene(loginroot));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            dragWindow(loginroot, primaryStage);

            Button proceedButton = loginController.proceedButton;
            proceedButton.setOnAction(e -> {
                try {
                    proceedButtonClicked(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            // Show primaryStage
            primaryStage.show();
        }
        catch (Exception e){

            CodeSource src = Main.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while(true) {
                    ZipEntry el = zip.getNextEntry();
                    if (el == null)
                        break;
                    System.out.println(el.getName());
                    if (el.getName().startsWith("path/to/your/dir/")) {
                        /* Do something with this entry. */
                    }
                }
            }
            else {
                /* Fail... */
            }
        }
    }

    public void defaultScreen(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/DefaultLayout.fxml"));
        Parent defaultroot = loader.load();
        defaultController = (DefaultController)loader.getController();
        Button monsterButton = defaultController.MonsterButton;
        monsterButton.setOnAction(e -> {
            try{
                monsterButtonClicked(primaryStage);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });
        Button combatButton = defaultController.CombatButton;
        combatButton.setOnAction(e -> {
            try{
                CombatButtonClicked(primaryStage);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });
        primaryStage.setScene(new Scene(defaultroot));
        dragWindow(defaultroot, primaryStage);
        System.out.println("Login Succesful.");
        if(positionY == 0 && positionX == 0){
            primaryStage.centerOnScreen();
        }
        else{
            primaryStage.setX(positionX);
            primaryStage.setY(positionY);
        }
// SPELL BUTTON
        Button spellButton = defaultController.SpellButton;
        spellButton.setOnAction(e -> {
            try{
                spellButtonClicked(primaryStage);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });

        // ITEM BUTTON
        Button itemButton = defaultController.ItemsButton;
        itemButton.setOnAction(e -> {
            try{
                itemButtonClicked(primaryStage);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });

        // MAGIC ITEM BUTTON
        Button magicItemButton = defaultController.MagicItemsButton;
        magicItemButton.setOnAction(e -> {
            try{
                magicItemButtonClicked(primaryStage);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });

        // NAME GENERATOR BUTTON
        Button nameGeneratorButton = defaultController.NameGeneratorButton;
        nameGeneratorButton.setOnAction(e -> {
            try{
                nameGeneratorButtonClicked(primaryStage);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void CombatButtonClicked(Stage primaryStage) throws Exception {
        combat(primaryStage);
    }

    private void combat(Stage primaryStage) throws IOException {
        if(hasCombat == false) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/CombatLayout.fxml"));
            Parent combatRoot = loader.load();
            combatController = (CombatController) loader.getController();
            hasCombat = true;
        }
        defaultController.splitPane.getItems().set(1,combatController.content);
    }

    public void monster(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/MonsterLayout.fxml"));
        Parent monsterRoot = loader.load();
        monsterController = (MonsterController)loader.getController();
        monsterController.Initialize(xmlh);
        defaultController.splitPane.getItems().set(1,monsterController.content);
    }




    public void spell(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/SpellLayout.fxml"));
        Parent spellRoot = loader.load();
        spellController = (SpellController)loader.getController();
        spellController.Initialize(xmlh);
        defaultController.splitPane.getItems().set(1,spellController.content);
    }

    public void items(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/ItemLayout.fxml"));
        Parent itemRoot = loader.load();
        itemController = (ItemController)loader.getController();
        defaultController.splitPane.getItems().set(1,itemController.content);
    }

    public void magicItems(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/MagicItemLayout.fxml"));
        Parent magicItemRoot = loader.load();
        magicItemController = (MagicItemController)loader.getController();
        defaultController.splitPane.getItems().set(1,magicItemController.content);

    }

    public void nameGenerator(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/NameGeneratorLayout.fxml"));
        Parent namegeneratorroot = loader.load();
        nameGeneratorController = (NameGeneratorController)loader.getController();
        defaultController.splitPane.getItems().set(1,nameGeneratorController.content);
    }

    public void proceedButtonClicked(Stage combatStage) throws Exception {
        String checkuser = "";
        String checkpw = "";
        String username = loginController.txtUserName.getText();
        String password = loginController.passwordField.getText();
        if(checkuser.equals(username) && checkpw.equals(password)) {
            defaultScreen(combatStage);
        }
        else System.out.println("Login Failed - Wrong username/password.");
    }

    public void monsterButtonClicked(Stage monsterStage) throws Exception {
        monster(monsterStage);
    }
    public void spellButtonClicked(Stage spellStage) throws Exception {
        spell(spellStage);
    }
    public void itemButtonClicked(Stage itemStage) throws Exception {
        items(itemStage);
    }
    public void magicItemButtonClicked(Stage magicItemStage) throws Exception {
        magicItems(magicItemStage);
    }
    public void nameGeneratorButtonClicked(Stage nameGeneratorStage) throws Exception {
        nameGenerator(nameGeneratorStage);
    }

    public void dragWindow(Parent p, Stage primaryStage){
        // Get x,y coordinates based on MousePressed
        p.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        // Set x,y coordinate values to primaryStage on screen
        p.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x );
            primaryStage.setY(event.getScreenY() - y );
            positionX = primaryStage.getX();
            positionY = primaryStage.getY();

        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}