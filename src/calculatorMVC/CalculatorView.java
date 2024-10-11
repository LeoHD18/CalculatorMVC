package calculatorMVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class CalculatorView extends JFrame {
	
    protected final Color defaultButtonColor = new Color(240, 240, 240); // Light gray
    protected final Color activeButtonColor = new Color(180, 220, 240); // Light blue
    
    protected JTextField displayField = new JTextField();// Where results and inputs are displayed
    protected JButton[] numberButtons = new JButton[10];  // Buttons 0-9
    protected JButton addButton, subtractButton, multiplyButton, divideButton; // Basic operation buttons
    protected JButton sqrtButton, squareButton; // Advanced operation buttons
    protected JButton mAddButton, mSubtractButton, mRecallButton, mClearButton; // Memory buttons
    protected JButton deleteButton, clearButton, decimalButton, equalsButton; // Control buttons

    public CalculatorView() {
        setTitle("Nguyen's Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,500);
        setLayout(new BorderLayout());

        // Display field configuration
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(displayField, BorderLayout.NORTH);

        // Button panel configuration
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 4, 5, 5)); // 6 rows, 4 columns, spacing

        // Instantiate buttons
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = createButton(String.valueOf(i));
            numberButtons[i].setBackground(defaultButtonColor);
        }
        addButton = createButton("+");
        subtractButton = createButton("-");
        multiplyButton = createButton("*");
        divideButton = createButton("/");
        sqrtButton = createButton("sqrt");
        squareButton = createButton("x^2");
        mAddButton = createButton("M+");
        mSubtractButton = createButton("M-");
        mRecallButton = createButton("MR");
        mClearButton = createButton("MC");
        deleteButton = createButton("Del");
        equalsButton = createButton("=");
        clearButton = createButton("C");
        decimalButton = createButton(".");
        
     

        // Add buttons to the panel
        for (int i = 1; i < 10; i++) {  // Add numeric buttons
            buttonPanel.add(numberButtons[i]);
        }
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(sqrtButton);
        buttonPanel.add(squareButton);

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);

        buttonPanel.add(decimalButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(equalsButton);

        buttonPanel.add(mAddButton);
        buttonPanel.add(mSubtractButton);
        buttonPanel.add(mRecallButton);
        buttonPanel.add(mClearButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(defaultButtonColor);
        button.setOpaque(true);
        button.setBorderPainted(true);
        return button;
    }


}
