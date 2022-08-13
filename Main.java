package converter;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean exit = false;
        do{
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) > ");
            String input = scan.nextLine();
            if(input.equals( "/exit")){
                exit = true;
            }else{
                int from = Integer.parseInt(input.split(" ")[0]);
                int to = Integer.parseInt(input.split(" ")[1]);
                boolean back = false;
                do {
                    System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) > ",from,to);
                    String inp = scan.nextLine();
                    if(inp.equals("/back")){
                        back = true;
                    }else {
                        String numm = new String("0");
                        String[] num = inp.split("\\.");
                        try {
                            numm = Converter.convertFloat(from,to,num[1]);
                            System.out.printf("Conversion result: %s.%s", Converter.convert(from, to, num[0]),numm);
                            System.out.println();
                        }catch (Exception e){
                            System.out.printf("Conversion result: %s", Converter.convert(from, to, num[0]));
                            System.out.println();
                        }
                    }
                }while (!back);
            }

        }while (!exit);


    }
}

