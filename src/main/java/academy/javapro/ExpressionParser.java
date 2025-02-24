package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    // expr → expr + term
    public double parseExpression() {
        double result = parseTerm();
        while (position < input.length() && input.charAt(position) == '+') {
            position++;
            double rightTerm = parseTerm();
            result += rightTerm;
        }
        return result;
    }

    // term → term * factor
    private double parseTerm() {
        double result = parseFactor();
        while (position < input.length() && input.charAt(position) == '*') {
            position++;
            double rightFactor = parseFactor();
            result *= rightFactor;
        }
        return result;
    }

    // factor → ( expr )
    private double parseFactor() {
        if (position < input.length() && input.charAt(position) == '(') {
            position++;
            double value = parseExpression();
            position++;
            return value;
        } else {
            return parseNumber();
        }
    }

    // Parse a numeric value
    private double parseNumber() {
        StringBuilder sb = new StringBuilder();
        boolean hasDecimalPoint = false;
        while (position < input.length()) {
            char c = input.charAt(position);
            if (Character.isDigit(c)) {
                sb.append(c);
                position++;
            } else if (c == '.' && !hasDecimalPoint) {
                sb.append(c);
                hasDecimalPoint = true;
                position++;
            } else {
                break;
            }
        }
        return Double.parseDouble(sb.toString());
    }

    public static void main(String[] args) {
        // Test cases
        String[] testCases = {
                "2 + 3 * (4 + 5)",    // Complex expression with parentheses
                "2 + 3 * 4",          // Basic arithmetic with precedence
                "(2 + 3) * 4",        // Parentheses changing precedence
                "2 * (3 + 4) * (5 + 6)", // Multiple parentheses
                "1.5 + 2.5 * 3"       // Decimal numbers
        };

        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", "")); // Remove spaces
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}