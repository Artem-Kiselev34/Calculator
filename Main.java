import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Input:");
                String input = scanner.nextLine();

                try {
                    String result = calc(input);
                    System.out.println("Output:\n" + result);
                } catch (Exception e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
    }

    static int parseNumber(String str) throws Exception {
        int num;
        if (isRoman(str)) {
            num = romanToArabic(str);
            if (num < 1 || num > 10) {
                throw new Exception("число должно быть в диапазоне от I до X");
            }
        } else {
            num = Integer.parseInt(str);
            if (num < 1 || num > 10) {
                throw new Exception("число должно быть в диапазоне от 1 до 10");
            }
        }
        return num;
    }

    public static String calc(String input) throws Exception {
        input = prepareInput(input);
        String[] parts = splitInput(input);
        validateInput(parts);
        validateOperands(parts[0], parts[2]);
        boolean isRoman = checkNumberSystem(parts);
        int num1 = parseNumber(parts[0]);
        String operator = parts[1];
        int num2 = parseNumber(parts[2]);
        int result = performOperation(num1, num2, operator);
        return formatNumber(result, isRoman);
    }

    static void validateOperands(String operand1, String operand2) throws Exception {
        if (isNumber(operand1) || isNumber(operand2)) {
            throw new Exception("строка не является математической операцией");
        }
    }

    static boolean isNumber(String str) {
        return !isRoman(str) && !str.matches("\\d+");
    }

    static String prepareInput(String input) {
        return input.replaceAll("([+\\-*/])", " $1 ");
    }

    static String[] splitInput(String input) {
        return input.split(" ");
    }

    static void validateInput(String[] parts) throws Exception {
        if (parts.length > 3) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        if (parts.length != 3) {
            throw new Exception("строка не является математической операцией");
        }
    }
    static boolean checkNumberSystem(String[] parts) throws Exception {
        boolean isRoman1 = isRoman(parts[0]);
        boolean isRoman2 = isRoman(parts[2]);
        if (isRoman1 != isRoman2) {
            throw new Exception("используются одновременно разные системы счисления");
        }
        return isRoman1;
    }
    static int performOperation(int num1, int num2, String operator) throws Exception {
        return switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    throw new Exception("деление на ноль невозможно");
                }
                yield num1 / num2;
            }
            default -> throw new Exception("неизвестный оператор: " + operator);
        };
    }

    static boolean isRoman(String str) {
        return str.matches("^(C|XC|L?X{0,3})(IX|IV|V?I{0,3})$");
    }

    static final List<String> ROMAN_NUMERALS = Arrays.asList(
                "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
                "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
                "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
                "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
                "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
                "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
        );
        static int romanToArabic(String roman) throws Exception {
            int index = ROMAN_NUMERALS.indexOf(roman);
            if (index == -1) {
                throw new Exception("неверное римское число");
            }
            return index + 1;
        }

        static String formatNumber(int num, boolean isRoman) throws Exception {
            if (isRoman) {
                if (num <= 0) {
                    throw new Exception("в римской системе нет отрицательных чисел и нуля");
                }
                return ROMAN_NUMERALS.get(num - 1);
            } else {
                return String.valueOf(num);
            }
        }
}
