import java.util.Scanner;
public class lab2 {
        public static void sortare(int num1, int num2, int num3) {
            int aux;
            // presupunem num1 este minim, num2 intermediar, num3 maxim
            if (num2 < num1) {
                aux = num1;
                num1 = num2;
                num2 = aux;
            }

            if (num3 < num1) {
                aux = num1;
                num1 = num3;
                num3 = aux;
            }

            if (num3 < num2) {
                aux = num2;
                num2 = num3;
                num3 = aux;
            }

            System.out.println("\nNumerele: " + num1 + " < " + num2 +" < " + num3);
        }
        public static long fibonacciElement(int n) {
            if (n <= 0)
            {
                return -1; // caz invlaid
            }
            else if (n == 1 || n == 2)
            {
                return 1; // pentru elem 1 si 2
            }
            else
            {
                long a = 1, b = 1, aux;

                for (int i = 3; i <= n; i++)
                {
                    aux = a + b;
                    a = b;
                    b = aux;
                }
                return b;
            }
        }
        public static void main(String[] args) {

            // nr divizibile cu 3:
            System.out.println("Nr divizibile cu 3 intre 1 - 99 : ");
            for (int i = 1; i <= 99; i++) {
                if (i % 3 == 0) {
                    System.out.print(i + " ");
                }
            }

            Scanner scanner = new Scanner(System.in);

            // Citeste cele 3 numere de la tastatura
            System.out.print("\n\nIntrodu primul numar: ");
            int numar1 = scanner.nextInt();

            System.out.print("\nIntrodu al doilea numar: ");
            int numar2 = scanner.nextInt();

            System.out.print("\nIntrodu al treilea numar: ");
            int numar3 = scanner.nextInt();

            sortare(numar1, numar2, numar3);


            // nr fibonaci: 1, 1, 2, 3, 5, 8, 13, 21
            System.out.print("\n\nIntroduceți valoarea N pentru numarul fibonaci: ");

            int n = scanner.nextInt();
            long result = fibonacciElement(n);

            System.out.println("Elementul " + n + " din secventa fibonaci este: " + result);

            scanner.close();
        }
}
