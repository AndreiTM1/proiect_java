import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean validInput = false;
        int i = 0;
        while (!validInput) {
            try {
                System.out.println("Alegeti ce joc vreti sa jucati:\n1. Razboi\n2. Makao");
                i = sc.nextInt();

                if (i == 1 || i == 2) {
                    validInput = true;
                } else {
                    System.out.println("Alegeti 1 sau 2.");
                }
            } catch (Exception e) {
                System.out.println("Numarul trebuie sa fie 1 sau 2");
                sc.nextLine();
            }
        }
        if (i == 1){
            new Razboi();
        }
        else{
            new Makao();
        }

    }
}
