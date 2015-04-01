
package cz.hartrik.ntg;

import javafx.scene.control.TextField;

/**
 * Jednoduchý validátor.
 * 
 * @version 2014-06-04
 * @author Patrik Harag
 */
public class TextFieldValidator {
    
    protected final TextField textField;
    protected final int min, max, def;

    public TextFieldValidator(TextField textField, int min, int max, int def) {
        this.textField = textField;
        this.min = min;
        this.max = max;
        this.def = def;

        textField.setOnKeyReleased(event -> validate());
    }
    
    private void validate() {
        changed(getValue());
    }
    
    public int getValue() {
        if (textField.getText().isEmpty()) return def;
        try {
            int integer = Integer.parseInt(textField.getText());
            return (integer >= min && integer <= max)
                    ? integer : validate(integer);
        } catch (NumberFormatException e) {
            return validate(null);
        }
    }
    
    private int validate(Integer parsed) {
        if (parsed == null) {
            StringBuilder builder = new StringBuilder();
            for (char character : textField.getText().toCharArray())
                if (Character.isDigit(character)) builder.append(character);
            String string = builder.toString();
            
            if (string.length() == 0) {
                textField.setText(null);
                return min;
            } else if (string.length() > 10)
                string = string.substring(0, 9);
            
            textField.setText(string);
            int integer = Integer.parseInt(textField.getText());
            return integer >= min ? integer : min;
        } else {
            int newInt = def;
            if      (parsed < min) newInt = min;
            else if (parsed > max) newInt = max;
            
            String newString = "" + newInt;
            if (!newString.equals(textField.getText()))
                    textField.setText(newString);
            
            return newInt;
        }
    }
    
    public void changed(int newValue) {
        
    }
    
}