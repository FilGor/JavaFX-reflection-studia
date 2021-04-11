package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private Pane paneForLabels;

    @FXML
    private Pane paneForInputs;

    @FXML
    private TextArea bottomTextArea;

    //pozycje startowe dynamicznie generowany labeli
    private int positionYOfLabelsAndInputs = -30;

    Method classMethods[];
    Object objectOfGivenClass;

    @FXML
    void createObjectButtonClicked(ActionEvent event) throws Exception {

        Class<?> cl = Class.forName(classNameField.getText()); // tworzenie obiektu reprezentujacego klase
        Constructor<?> cons = cl.getDeclaredConstructor();//wyciagniecie ref obiektu reprezentujacego konstruktor
        objectOfGivenClass = cons.newInstance();
        classMethods = cl.getDeclaredMethods(); // wyciagniecie metod
        Field classFields[] = cl.getDeclaredFields(); //wyciagniecie pól

        Stream.of(classFields) // wygenerowanie nazw pól i input textu
                .map(e->e.getName())
                .forEach(e-> {
                    Label label = new Label(e);
                    TextInputControl textInput;


                    if(e.matches(".*text.*")){
                        textInput = new TextArea();
                        textInput.setMaxSize(200,20);
                    }else
                    {
                        textInput = new TextField();
                        textInput.setMaxSize(200,20);

                    }

                    label.setTranslateX(0);
                    label.setTranslateY(positionYOfLabelsAndInputs+=50);
                    textInput.setTranslateX(0);
                    textInput.setTranslateY(positionYOfLabelsAndInputs);
                    paneForLabels.getChildren().add(label);
                    paneForInputs.getChildren().add(textInput);
                });

    }

    @FXML
    void saveChangesButtonClicked(ActionEvent event) {

        Stream.of(classMethods)
                .filter(m->m.getName().matches("set.*"))
                .forEach(m->{
                    try {
                        m.invoke(objectOfGivenClass,paneForInputs
                                .getChildren()
                                .iterator()
                                .next().getTe///??
                        );
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                });

    }

}
