package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
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
        //wartosci inicjalizacyjne:
        paneForInputs.getChildren().clear();
        paneForLabels.getChildren().clear();
        positionYOfLabelsAndInputs = -30;

        Stream.of(classFields) // wygenerowanie nazw pól i input textu
                .sorted(Comparator.comparing(Field::getName))
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

                    label.setTranslateY(positionYOfLabelsAndInputs+=50);
                    textInput.setTranslateY(positionYOfLabelsAndInputs);
                    paneForLabels.getChildren().add(label);
                    paneForInputs.getChildren().add(textInput);
                });

    }

    @FXML
    void saveChangesButtonClicked(ActionEvent event) {
        Iterator<Node> textInputsIterator = paneForInputs.getChildren().iterator();
        Stream.of(classMethods)
                .sorted(Comparator.comparing(Method::getName))
                .filter(m->m.getName().matches("set.*"))
                .forEach(m->{
                    try {
                        TextInputControl actualtextField;
                        actualtextField = (TextInputControl)textInputsIterator.next();
                        String actualType = m.getParameters()[0].getType().getName();
                        if(isNumericType(actualType)){
                            try {
                                Float number =Float.parseFloat(actualtextField.getText());
                                m.invoke(objectOfGivenClass,number);
                            }catch (NumberFormatException e){
                                Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
                                alert.show();
                            }

                        }else{
                        m.invoke(objectOfGivenClass,actualtextField.getText());}
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                });

        //Wypisanie w "konsoli" w okienku aplikacji
        Stream.of(classMethods)
                .sorted(Comparator.comparing(Method::getName))
                .filter(m->m.getName().matches("get.*|is.*"))
                .forEach(m->{
                    try {
                        System.out.println(m.getName().substring(3));
                        bottomTextArea.appendText(m.getName().substring(3)+" "+
                                (m.invoke(objectOfGivenClass)).toString()+"\n");



                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });

    }

    boolean isNumericType(String type){

        return type.equals(Float.class.getName())
                || type.equals(Integer.class.getName())
                || type.equals(BigDecimal.class.getName())
                || type.equals(BigInteger.class.getName())
                || type.equals(int.class.getName());
    }

}
