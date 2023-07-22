import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    private static final Map<String, Integer> roman = new HashMap<>();

    static {
        roman.put("I", 1);
        roman.put("II", 2);
        roman.put("III", 3);
        roman.put("IV", 4);
        roman.put("V", 5);
        roman.put("VI", 6);
        roman.put("VII", 7);
        roman.put("VIII", 8);
        roman.put("IX", 9);
        roman.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите арифметическое выражение: ");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (UnsupportedOperationException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String operand1 = tokens[0];
        String operator = tokens[1];
        String operand2 = tokens[2];

        boolean isRomanNumeralOperand1 = isRomanNumeral(operand1);
        boolean isRomanNumeralOperand2 = isRomanNumeral(operand2);

        if (isRomanNumeralOperand1 && isRomanNumeralOperand2) {
            int num1 = parseRomanNumeral(operand1);
            int num2 = parseRomanNumeral(operand2);
            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    if (result <= 0) {
                        throw new UnsupportedOperationException("Результат римской цифры не может быть отрицательным");
                    }
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Деление на ноль невозможно");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция");
            }
            return toRomanNumeral(result);
        } else if (!isRomanNumeralOperand1 && !isRomanNumeralOperand2) {
            int num1 = Integer.parseInt(operand1);
            int num2 = Integer.parseInt(operand2);

            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Недопустимое число, число должно быть от 1 до 10 включительно");
            }

            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Деление на ноль невозможно");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция");
            }
            return String.valueOf(result);
        } else {
            throw new UnsupportedOperationException("Используются одновременно разные системы исчисления");
        }
    }

    private static boolean isRomanNumeral(String operand) {
        return operand.matches("^[IVXLCDM]+$");
    }

    private static int parseRomanNumeral(String operand) {
        if (!roman.containsKey(operand)) {
            throw new IllegalArgumentException("Недопустимое число, число должно быть от I до X включительно");
        }
        return roman.get(operand);
    }

    private static String toRomanNumeral(int number) {
        if (number == 0) {
            return "N";
        }
        if (number < 0) {
            throw new UnsupportedOperationException("Результат римской цифры не может быть отрицательным");
        }

        String[] romanNumerals = {
                "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
        };
        int[] arabicValues = {
                1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
        };

        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (number > 0) {
            while (number >= arabicValues[index]) {
                sb.append(romanNumerals[index]);
                number -= arabicValues[index];
            }
            index++;
        }
        return sb.toString();
    }
}
