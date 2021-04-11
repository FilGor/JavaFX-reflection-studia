package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class Controller {

    @FXML
    private Button createObjectButton;

    @FXML
    private Button saveChangesButton;

    @FXML
    private TextField classNameField;

    @FXML
    private Pane paneForClassInfo;

    @FXML
    private TextArea bottomTextArea;

    //pozycje startowe dynamicznie generowany labeli
    private int positionYOfLabel = -30;
    private int positionXOfLabel = 320;

    Method classMethods[];

    @FXML
    void createObjectButtonClicked(ActionEvent event) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cl = Class.forName(classNameField.getText()); // tworzenie obiektu reprezentujacego klase
        Constructor<?> cons = cl.getDeclaredConstructor();//wyciagniecie ref obiektu reprezentujacego konstruktor
        Object objectOfGivenClass = cons.newInstance();
        classMethods = cl.getDeclaredMethods(); // wyciagniecie metod
        Field classFields[] = cl.getDeclaredFields(); //wyciagniecie pól

        Stream.of(classFields) // wygenerowanie nazw pól
                .map(e->e.getName())
                .forEach(e-> {
                    Label label = new Label(e);
                    label.setTranslateX(positionXOfLabel);
                    label.setTranslateY(positionYOfLabel+=40);
                    paneForClassInfo.getChildren().add(label);

                });
        /* List <String> namesOfFields = Stream.of(classFields)
                .map(e->e.getName())
                .collect(Collectors.toList());
        */

    }

    @FXML
    void saveChangesButtonClicked(ActionEvent event) {


    }

}
