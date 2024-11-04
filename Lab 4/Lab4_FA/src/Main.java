import fa.FA;

import java.util.Scanner;

public class Main {
    public static void runScanner() {
        MyScanner scanner_1 = new MyScanner("p1.txt");
        MyScanner scanner_2 = new MyScanner("p2.txt");
        MyScanner scanner_3 = new MyScanner("p3.txt");
        MyScanner scanner_1err = new MyScanner("p1err.txt");

        scanner_1.scan();
        scanner_2.scan();
        scanner_3.scan();
        scanner_1err.scan();
    }

    public static void printMenuFA() {
        System.out.println("Menu: ");
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print initial state");
        System.out.println("4. Print final states");
        System.out.println("5. Print transitions");
        System.out.println("6. Verify if the word is accepted by the DFA");
        System.out.println("7. Check if FA is deterministic");
        System.out.println("0. Exit");
        System.out.print("> ");
    }

    private static void FA() {
        FA fa = new FA("././fa.in");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenuFA();

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    fa.printStates();
                    break;
                case 2:
                    fa.printAlphabet();
                    break;
                case 3:
                    fa.printInitialState();
                    break;
                case 4:
                    fa.printFinalStates();
                    break;
                case 5:
                    fa.printTransitions();
                    break;
                case 6:
                    System.out.print("Enter word: ");
                    String word = scanner.nextLine();
                    boolean isAccepted = fa.isAcceptedByFA(word);
                    System.out.println("The word " + word + " is " + (isAccepted ? "accepted" : "not accepted") + " by the FA");
                    break;
                case 7:
                    System.out.println("Fa is deterministic: " + fa.isDeterministic());
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome!");
        System.out.println("1. Scanner");
        System.out.println("2. FA");
        System.out.println("Enter your option: ");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        switch (option) {
            case 1:
                runScanner();
                break;
            case 2:
                FA();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }
}