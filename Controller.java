package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.spi.NumberFormatProvider;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;

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

    Method[] classMethods;
    Object objectOfGivenClass;

    @FXML
    void createObjectButtonClicked(ActionEvent event) throws Exception {

        try{
        Class<?> cl = Class.forName(classNameField.getText()); // tworzenie obiektu reprezentujacego klase

        Constructor<?> cons = cl.getDeclaredConstructor();//wyciagniecie ref obiektu reprezentujacego konstruktor
        objectOfGivenClass = cons.newInstance();
        classMethods = cl.getDeclaredMethods(); // wyciagniecie metod
        Field[] classFields = cl.getDeclaredFields(); //wyciagniecie pól

        //wartosci inicjalizacyjne:
        paneForInputs.getChildren().clear();
        paneForLabels.getChildren().clear();
        positionYOfLabelsAndInputs = -30;

        Stream.of(classFields) // wygenerowanie nazw pól i input textu
                .sorted(Comparator.comparing(Field::getName))
                .forEach(e-> {
                    Label label = new Label(e.getName());
                    TextInputControl textInput;

                    if(e.getName().matches(".*text.*")){
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

        }catch (ClassNotFoundException e){
            Alert a = new Alert(Alert.AlertType.WARNING,"Podaj poprawną nazwe klasy");
            a.show();
        }

    }

    Number numberProvidedByUser;
    @FXML
    void saveChangesButtonClicked(ActionEvent event) {
        bottomTextArea.clear();
        Iterator<Node> textInputsIterator = paneForInputs.getChildren().iterator();
        Stream.of(classMethods)
                .sorted(Comparator.comparing(Method::getName))
                .filter(m->m.getName().matches("set.*"))
                .forEach(m->{
                    try {
                        TextInputControl actualtextField;
                        actualtextField = (TextInputControl)textInputsIterator.next();
                        String actualType = m.getParameters()[0].getType().getName();
                        if (actualType.equals(Boolean.class.getName()) || actualType.equals(boolean.class.getName())) {
                            if (actualtextField.getText().equals("true") || actualtextField.getText().equals("True")) {
                                m.invoke(objectOfGivenClass, true);
                            } else if (actualtextField.getText().equals("false") || actualtextField.getText().equals("False")) {
                                m.invoke(objectOfGivenClass, false);
                            } else {
                                bottomTextArea.appendText("\npole  " + m.getName().substring(3) + " jest niepoprawne" +
                                        " i nie zostanie zmienione \n\n");
                            }
                        }
                        else if(isNumericType(actualType,actualtextField.getText(),m)) {
                            //sprawdzenie regexem czy na pewno wprowadzono cyfry w razie gdyby NumberException nie załapał
                            if(isNumeric(actualtextField.getText())) {
                                m.invoke(objectOfGivenClass, numberProvidedByUser);
                            }else
                            {
                                bottomTextArea.appendText("\npole  "+ m.getName().substring(3) +" jest niepoprawne" +
                                        " i nie zostanie zmienione \n\n");
                            }
                        }else{
                        m.invoke(objectOfGivenClass,actualtextField.getText());
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                });
        wypiszAktualnyStanObiektu();

    }

    boolean isNumericType(String type,String textToCast,Method m){
try {


    if (type.equals(Float.class.getName()) || type.equals(float.class.getName())) {
        numberProvidedByUser = Float.parseFloat(textToCast);
    } else if (type.equals(BigDecimal.class.getName())) {
        numberProvidedByUser = BigDecimal.valueOf(Double.parseDouble(textToCast));
    }else if (type.equals(Integer.class.getName())|| type.equals(int.class.getName())) {
        numberProvidedByUser = Integer.parseInt(textToCast);
    }else if (type.equals(Double.class.getName()) || type.equals(double.class.getName())) {
        numberProvidedByUser = Double.parseDouble(textToCast);
    }else if (type.equals(Short.class.getName())|| type.equals(short.class.getName())) {
        numberProvidedByUser = Short.parseShort(textToCast);
    }else if (type.equals(byte.class.getName())|| type.equals(Byte.class.getName())) {
        numberProvidedByUser = Byte.parseByte(textToCast);
    }else if (type.equals(Long.class.getName())|| type.equals(long.class.getName())) {
        numberProvidedByUser = Long.parseLong(textToCast);
    }
}catch (NumberFormatException e){
    bottomTextArea.appendText("\npole  "+ m.getName().substring(3) +" jest niepoprawne" +
            " i nie zostanie zmienione \n\n");
}
        return type.equals(Float.class.getName())
                || type.equals(BigDecimal.class.getName())
                || type.equals(Integer.class.getName())
                || type.equals(int.class.getName())
                || type.equals(BigInteger.class.getName())
                || type.equals(Short.class.getName())
                || type.equals(Long.class.getName())
                || type.equals(Double.class.getName())
                || type.equals(Byte.class.getName())
                || type.equals(float.class.getName())
                || type.equals(byte.class.getName())
                || type.equals(short.class.getName());
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    void wypiszAktualnyStanObiektu(){
        Stream.of(classMethods)
                .sorted(Comparator.comparing(Method::getName))
                .filter(m->m.getName().matches("get.*"))
                .forEach(m->{
                    try {
                        bottomTextArea.appendText("\n"+
                                m.getName().substring(3) + " = " + m.invoke(objectOfGivenClass) +"\n" );

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
    }

}
