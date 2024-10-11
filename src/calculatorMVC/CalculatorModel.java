package calculatorMVC;


public class CalculatorModel {
    private double currentValue = 0;
    private double memory = 0;

    public double calculate(double number1, double number2, String operator) {
    	
        switch (operator) {
            case "+":
                currentValue = number1 + number2;
                return currentValue;
            case "-":
                currentValue = number1 - number2;
                return currentValue;
            case "*":
                currentValue = number1 * number2;
                return currentValue;
            case "/":
                if (number2 == 0) {
                    throw new ArithmeticException("Division by zero");
                } else {
                    currentValue = number1 / number2;
                }
                return currentValue;
            default:
                throw new IllegalArgumentException("Unknown operator");
        }
       
    }

    public void addToMemory(double value) {
        memory += value;
       
    }

    public void subtractFromMemory(double value) {
        memory -= value;
       
    }

    public double getMemoryValue() {
        return memory;
    }

    public void clearMemory() {
        memory = 0;
       
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public double calculateSquare(double number) {
        currentValue = number * number;
        return currentValue;
       
    }

    public double calculateSquareRoot(double number) {
        if (number < 0) {
            throw new ArithmeticException ("Negative input for square root");
        }
        currentValue = Math.sqrt(number);
        return currentValue;
   
    }
}

