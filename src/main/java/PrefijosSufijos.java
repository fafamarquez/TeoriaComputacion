import java.util.Scanner;

public class PrefijosSufijos {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa una cadena: ");
        String cadena = sc.nextLine();

        int n = cadena.length();

        System.out.print("Cantidad de prefijos (0 = todos): ");
        int pref = sc.nextInt();

        System.out.print("Cantidad de sufijos (0 = todos): ");
        int suf = sc.nextInt();

        System.out.println("\nPrefijos:");

        if (pref == 0) {
            for (int i = 1; i <= n; i++) {
                System.out.println(cadena.substring(0, i));
            }
        } else {
            for (int i = 1; i <= pref && i <= n; i++) {
                System.out.println(cadena.substring(0, i));
            }
        }

        System.out.println("\nSufijos:");

        if (suf == 0) {
            for (int i = 0; i < n; i++) {
                System.out.println(cadena.substring(i));
            }
        } else {
            for (int i = n - suf; i < n; i++) {
                if (i >= 0)
                    System.out.println(cadena.substring(i));
            }
        }

        sc.close();
    }
}