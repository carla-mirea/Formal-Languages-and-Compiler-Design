//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner();
        String program_1 = "p1.txt";
        String program_2 = "p2.txt";
        String program_3 = "p3.txt";
        String program_1err = "p1err.txt";

        scanner.scan(program_1);
        scanner.scan(program_2);
        scanner.scan(program_3);
        scanner.scan(program_1err);
    }
}