//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner_1 = new Scanner("p1.txt");
        Scanner scanner_2 = new Scanner("p2.txt");
        Scanner scanner_3 = new Scanner("p3.txt");
        Scanner scanner_1err = new Scanner("p1err.txt");

        scanner_1.scan();
        scanner_2.scan();
        scanner_3.scan();
        scanner_1err.scan();
    }
}