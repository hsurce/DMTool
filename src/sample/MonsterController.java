package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MonsterController {
    @FXML
    public AnchorPane content;

    @FXML
    private ChoiceBox<?> MonsterChoiceBox;

    @FXML
    private TextField MonsterSearch;

    @FXML
    private Button ButtonAddInitiative;

    @FXML
    private Button ButtonGenerateStatBlock;

    @FXML
    private Button ButtonShowImage;

    @FXML
    private Label LabelMonsterName;

    @FXML
    private Text TextSizeTypeAlignment;

    @FXML
    private Text STRScore;

    @FXML
    private Text DEXScore;

    @FXML
    private Text CONScore;

    @FXML
    private Text INTScore;

    @FXML
    private Text WISScore;

    @FXML
    private Text CHAScore;

}
