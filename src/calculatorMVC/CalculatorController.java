package calculatorMVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CalculatorController  {
    private CalculatorModel model; 
    private CalculatorView view;
    private String currentOperation;
    private double currentOperand;
    private boolean isNewOperandNeeded;
    private JButton lastClickedButton;
    private JButton previousClickedButton;
    
    // Constructor
    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        this.lastClickedButton = null;

        // Add action listeners to number buttons
        for (JButton button : view.numberButtons) {
            button.addActionListener(this::buttonClicked);
        }

        // Add action listeners to operation buttons
        view.addButton.addActionListener(this::buttonClicked);
        view.subtractButton.addActionListener(this::buttonClicked);
        view.multiplyButton.addActionListener(this::buttonClicked);
        view.divideButton.addActionListener(this::buttonClicked);
        view.sqrtButton.addActionListener(this::buttonClicked);
        view.squareButton.addActionListener(this::buttonClicked);
        view.equalsButton.addActionListener(this::buttonClicked);
        view.clearButton.addActionListener(this::buttonClicked);
        view.deleteButton.addActionListener(this::buttonClicked);
        view.decimalButton.addActionListener(this::buttonClicked);

        // Add action listeners to memory buttons
        view.mAddButton.addActionListener(this::buttonClicked);
        view.mSubtractButton.addActionListener(this::buttonClicked);
        view.mRecallButton.addActionListener(this::buttonClicked);
        view.mClearButton.addActionListener(this::buttonClicked);
    }

    private void buttonClicked(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (lastClickedButton != null) {
            lastClickedButton.setBackground(view.defaultButtonColor);  // Reset the last clicked button color
        }
        clickedButton.setBackground(view.activeButtonColor);  // Set the active color for the clicked button
        previousClickedButton = lastClickedButton;
        lastClickedButton = clickedButton;  // Keep track of the last clicked button

        String command = clickedButton.getText();
        switch (command) {
            case "+":
            case "-":
            case "*":
            case "/":
                setOperation(command);
                break;
            case "=":
                computeResult();
                break;
            case "C":
                clearAll();
                break;
            case "Del":
                deleteLastCharacter();
                break;
            case ".":
                appendDecimal();
                break;
            case "sqrt":
            	computeSqrt();
            	break;
            case "x^2":
                computeSquare();
                break;
            case "M+":
            	updateMemory(1);
            	break;
            case "M-":
            	updateMemory(2);
            	break;
            case "MR":
            	recallMemory();
            	break;
            case "MC":
            	clearMemory();
                break;
            default:
                concatNums(command);  // Assume it's a number or similar input
                break;
        }
    }


    private boolean isNumberButton(JButton button) {
        for (JButton numButton : view.numberButtons) {
            if (button == numButton) {
                return true;
            }
        }
        return false;
    }

    private void concatNums(String number) {
        try {
            String currentDisplay = view.displayField.getText();
            // Clear the display if an operation has just been set and the current display is the first operand.
            if (isNewOperandNeeded) {
                currentDisplay = "";  // Reset the display for the new number.
                isNewOperandNeeded = false;  // Reset the flag.
            }
            view.displayField.setText(currentDisplay + number);
        } catch (Exception e) {
            view.displayField.setText("Cannot get input");
        }
    }

    private void setOperation(String operator) {
        try {
            currentOperand = Double.parseDouble(view.displayField.getText());
            currentOperation = operator;
        } catch (NumberFormatException e) {
            view.displayField.setText("Number format error");
        } catch (Exception e) {
        	view.displayField.setText(e.getMessage());
        }
        isNewOperandNeeded = true;  // Set flag to clear the display for next number input.
    }

    private void computeResult() {
        double secondOperand = Double.parseDouble(view.displayField.getText());
        try {
        	if (!isNumberButton(previousClickedButton)) {
        		throw new Exception ("Invalid Operation");
        	}
            double result = model.calculate(currentOperand, secondOperand, currentOperation);
            currentOperand = result;
            view.displayField.setText(String.valueOf(result));
 
        } catch (IllegalArgumentException e) {
            view.displayField.setText(e.getMessage());
        } catch (ArithmeticException e) {
        	view.displayField.setText("Infinity");
        } catch (Exception e) {
        	view.displayField.setText(e.getMessage());
        }
        currentOperation = "";
        isNewOperandNeeded = true;
    }
    
    private void computeSquare() {
    	try {
    		double operand = Double.parseDouble(view.displayField.getText());
    		double result = model.calculateSquare(operand);
    		view.displayField.setText(String.valueOf(result));
    	} catch (Exception e) {
    		view.displayField.setText("Error" + e.getMessage());
    	}
    	isNewOperandNeeded = true;  // Set flag to clear the display for next number input.
    }
    
    private void computeSqrt() {
    	try {
    		double operand = Double.parseDouble(view.displayField.getText());
    		double result = model.calculateSquareRoot(operand);
    		view.displayField.setText(String.valueOf(result));
    	} catch (ArithmeticException e) {
    		view.displayField.setText("Sqrt of negative");
    	} catch (Exception e) {
    		view.displayField.setText("Error" + e.getMessage());
    	}
    	isNewOperandNeeded = true;  // Set flag to clear the display for next number input.
    }


    private void updateMemory(double updateFlag) {
    	
        try {
        	// You can only store the results of executed operations
        	if (previousClickedButton == view.equalsButton || previousClickedButton == view.sqrtButton || previousClickedButton == view.squareButton) {
        		 double value = Double.parseDouble(view.displayField.getText());
                 if (updateFlag == 1) {
                     model.addToMemory(value);
                 } else if (updateFlag == 2) {
                     model.subtractFromMemory(value);
                 }
        	} else {
        		throw new Exception ("Cannot add to memory");
        	}
        } catch (NumberFormatException e) {
            view.displayField.setText("Invalid input");
        } catch (Exception e) {
            view.displayField.setText(e.getMessage());
        }
        isNewOperandNeeded = true;
    }

    private void recallMemory() {
        try {
            double memoryValue = model.getMemoryValue();
            view.displayField.setText(String.valueOf(memoryValue));
        } catch (Exception e) {
            view.displayField.setText("Error Memory Recall");
        }
        isNewOperandNeeded = true;
    }

    private void clearAll() {
        try {
            view.displayField.setText("");
            currentOperation = null;
            currentOperand = 0;
            model.clearMemory();
        } catch (Exception e) {
            view.displayField.setText(e.getMessage());
        }
    }

    private void deleteLastCharacter() {
        try {
            String currentText = view.displayField.getText();
            if (!currentText.isEmpty()) {
                view.displayField.setText(currentText.substring(0, currentText.length() - 1));
            }
        } catch (Exception e) {
            view.displayField.setText("Error deleting character");
        }
    }

    private void appendDecimal() {
        try {
            String currentDisplay = view.displayField.getText();
            if (!currentDisplay.contains(".")) {
                view.displayField.setText(currentDisplay + ".");
            }
        } catch (Exception e) {
            view.displayField.setText("Error appending decimal");
        }
    }
    
    private void clearMemory() {
        try {
            model.clearMemory();
            view.displayField.setText("");  
        } catch (Exception e) {
            view.displayField.setText("Error clearing memory");
        }
    }
}
