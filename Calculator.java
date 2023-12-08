import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите выражение (например, 2 + 2):");
            String input = scanner.nextLine();

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Некорректный формат ввода");
            }

            String firstValue = parts[0];
            String operator = parts[1];
            String secondValue = parts[2];

            boolean isRoman = isRoman(firstValue) && isRoman(secondValue);
            boolean isArabic = isArabic(firstValue) && isArabic(secondValue);

            if (isRoman && isArabic) {
                throw new IllegalArgumentException("Операции с римскими и арабскими числами в одном выражении недопустимы");
            }

            int result;
            if (isRoman) {
                result = calculate(toArabic(firstValue), operator, toArabic(secondValue));
                System.out.println("Результат: " + toRoman(result));
            } else {
                result = calculate(Integer.parseInt(firstValue), operator, Integer.parseInt(secondValue));
                System.out.println("Результат: " + result);
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Некорректный формат чисел");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Проверка, является ли строка римским числом
    private static boolean isRoman(String input) {
        return input.matches("[IVXLCMD]+");
    }

    // Проверка, является ли строка арабским числом
    private static boolean isArabic(String input) {
        return input.matches("\\d+");
    }

    // Преобразование римского числа в арабское
    private static int toArabic(String roman) {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanNumerals.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    private static String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Недопустимое значение для римского числа");
        }

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] romanValues = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder result = new StringBuilder();
        int i = romanSymbols.length - 1;

        while (number > 0) {
            if (number >= romanValues[i]) {
                result.append(romanSymbols[i]);
                number -= romanValues[i];
            } else {
                i--;
            }
        }

        return result.toString();
    }

private static int calculate(int num1, String operator, int num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Недопустимая операция");
        }
    }
}