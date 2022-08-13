package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Converter {
    public static StringBuilder convert(int from , int to, String num){
        StringBuilder text = new StringBuilder();
        Stack<Character> charrs = convertFromDecimal(convertToDecimal(num,from),to);
        while(!charrs.empty()){
            text.append((char)charrs.pop());
        }
        return text;

    }

    public static BigInteger convertToDecimal(String num, int from) {
        num = num.trim();
        BigInteger result = BigInteger.ZERO;
        int i = num.length() - 1;
        int j = 0;
        num = num.toUpperCase();
        while (i > -1) {
            if (num.charAt(i) >= 65 && num.charAt(i) <= 90 || num.charAt(i) >= 48 && num.charAt(i) <= 57) {
                if ((num.charAt(i) - 64) > from - 10) {
                    return Errors.SYS_SCHISL_BIGGER.getType();
                } else if (num.charAt(i) >= 65 && num.charAt(i) <= 90) {
                    result = result.add( BigInteger.valueOf ((num.charAt(i) - 55)).multiply( BigInteger.valueOf(from).pow(j)));
                    if (i != 0) {
                        i--;
                        j++;
                    } else {
                        return result;
                    }
                } else {
                    result = result.add( BigInteger.valueOf ((num.charAt(i) - 48)).multiply( BigInteger.valueOf(from).pow(j)));
                    i--;
                    j++;

                }
            } else if (num.charAt(i) == '-' && i == 0) {
                return result;
            } else {
                return Errors.INCORRECT_SYMBOL.getType();
            }
        }
        return result;
    }
    public static Stack<Character> convertFromDecimal(BigInteger num, int too){
        Stack<Character> result = new Stack<>();
        BigInteger to = BigInteger.valueOf(too);
        BigInteger nine = BigInteger.valueOf(9);
        while(num.compareTo(to) > -1){
            result.push(num.divideAndRemainder(to)[1].compareTo(nine) != 1 ? ( num.divideAndRemainder(to)[1].toString().charAt(0)) : (char) (Integer.parseInt(num.divideAndRemainder(to)[1].toString()) + 55));
            num = num.divide(to);
        }
        if(num.divideAndRemainder(to)[1].compareTo(BigInteger.ZERO) != 0){result.push(num.divideAndRemainder(to)[1].compareTo(nine) != 1 ? ( num.divideAndRemainder(to)[1].toString().charAt(0)) : (char) (Integer.parseInt(num.divideAndRemainder(to)[1].toString()) + 55));}
        return result;
    }
    public static String convertFloat(int from,int to,String num){
        return convertFromDecimalFloat(convertToDecimalFloat(num, from),to);
    }

    public static String convertFromDecimalFloat(BigDecimal num, int too) {
        List<String> result = new ArrayList<>();
        BigDecimal to = BigDecimal.valueOf(too);
        BigDecimal nine = BigDecimal.valueOf(9);
        while (result.size() <5) {
            BigDecimal n = num.multiply(to).setScale(0,RoundingMode.DOWN);
            result.add(n.compareTo(nine) != 1 ? n.toString() : Character.toString(Integer.parseInt(n.toString()) + 55));
            num = num.multiply(to).subtract(n);
        }
        String out = "";
        for(String r: result){
            out+=r;
        }
        return out;
    }

    public static BigDecimal convertToDecimalFloat(String num, int from){
        num = num.trim();
        BigDecimal result = BigDecimal.ZERO;
        int i = 0;
        int j = 1;
        num = num.toUpperCase();
        while (i < num.length()) {
            if (num.charAt(i) >= 65 && num.charAt(i) <= 90 || num.charAt(i) >= 48 && num.charAt(i) <= 57) {
                if ((num.charAt(i) - 64) > from - 10) {
                    return ErrorsDecimal.SYS_SCHISL_BIGGER.getType();
                } else if (num.charAt(i) >= 65 && num.charAt(i) <= 90) {
                    result = result.add( BigDecimal.valueOf ((num.charAt(i) - 55)).divide( BigDecimal.valueOf(from).pow(j),30,RoundingMode.DOWN));
                    i++;
                    j++;
                } else {
                    result = result.add( BigDecimal.valueOf ((num.charAt(i) - 48)).divide( BigDecimal.valueOf(from).pow(j),30,RoundingMode.DOWN));
                    i++;
                    j++;

                }
            } else if (num.charAt(i) == '-' && i == 0) {
                return result;
            } else {
                return ErrorsDecimal.INCORRECT_SYMBOL.getType();
            }
        }
        return result;
    }
}

enum ErrorsDecimal {
    SYS_SCHISL_BIGGER(BigDecimal.ONE.negate()),
    INCORRECT_SYMBOL(BigDecimal.TEN.negate());


    private BigDecimal type;
    ErrorsDecimal(BigDecimal type){
        this.type = type;
    }

    public BigDecimal getType() {
        return type;
    }
}

enum Errors {
    SYS_SCHISL_BIGGER(BigInteger.TWO.negate()),
    INCORRECT_SYMBOL(BigInteger.TWO.negate());


    private BigInteger type;

    Errors(BigInteger type) {
        this.type = type;
    }

    public BigInteger getType() {
        return type;
    }
}