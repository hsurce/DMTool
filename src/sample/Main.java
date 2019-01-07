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
    private LoginController loginController;
    private DefaultController defaultController;

    private CombatController combatController;
    private MonsterController monsterController;
    private SpellController spellController;
    private ItemController itemController;
    private MagicItemController magicItemController;
    private NameGeneratorController nameGeneratorController;
    // Creating a double, to record and calculate frame x,y coordinates
    private double x,y;
    private double positionX = 0;
    private double positionY = 0;
    private XMLHandler xmlh;
    private GlobalController globalController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            xmlh = new XMLHandler();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("XMLFiles/LoginLayout.fxml"));
            Parent loginroot = fxmlLoader.load();
            loginController = fxmlLoader.getController();

            //LOAD af alle FXML filer.
            loadXMLFiles();
            globalController = new GlobalController(xmlh,combatController,monsterController,spellController,itemController,magicItemController, nameGeneratorController);
            initializeControllers();
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

    private void initializeControllers() {
        nameGeneratorController.initialize(globalController);
        monsterController.initialize(globalController);
        spellController.initialize(globalController);
        combatController.initialize(globalController);
        itemController.initialize(globalController);
        magicItemController.initialize(globalController);
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
    private void loadXMLFiles() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/NameGeneratorLayout.fxml"));
        Parent namegeneratorroot = loader.load();
        nameGeneratorController = (NameGeneratorController)loader.getController();


        FXMLLoader monsterLoader = new FXMLLoader(getClass().getResource("XMLFiles/MonsterLayout.fxml"));
        Parent monsterRoot = monsterLoader.load();
        monsterController = (MonsterController)monsterLoader.getController();


        FXMLLoader combatLoader = new FXMLLoader(getClass().getResource("XMLFiles/CombatLayout.fxml"));
        Parent combatRoot = combatLoader.load();
        combatController = (CombatController) combatLoader.getController();


        FXMLLoader spellLoader = new FXMLLoader(getClass().getResource("XMLFiles/SpellLayout.fxml"));
        Parent spellRoot = spellLoader.load();
        spellController = (SpellController)spellLoader.getController();


        FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("XMLFiles/ItemLayout.fxml"));
        Parent itemRoot = itemLoader.load();
        itemController = (ItemController)itemLoader.getController();


        FXMLLoader magicItemLoader = new FXMLLoader(getClass().getResource("XMLFiles/MagicItemLayout.fxml"));
        Parent magicItemRoot = magicItemLoader.load();
        magicItemController = (MagicItemController)magicItemLoader.getController();


    }

    public void defaultScreen(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("XMLFiles/DefaultLayout.fxml"));
        Parent defaultroot = loader.load();
        defaultController = (DefaultController)loader.getController();
        defaultController.initialize(globalController);

        primaryStage.setScene(new Scene(defaultroot));
        dragWindow(defaultroot, primaryStage);
        System.out.println("Login Succesful.");
        if (positionY == 0 && positionX == 0) {
            primaryStage.centerOnScreen();
        } else {
            primaryStage.setX(positionX);
            primaryStage.setY(positionY);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}