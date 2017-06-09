import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        String tmpUsername;
        short brojLogovanja = 0;

        while (brojLogovanja < 3){
            try {
                tmpUsername = new Scanner(System.in).nextLine();

                if (korisnikCheck(tmpUsername)) {


                } else if(adminCheck(tmpUsername)){

                }else throw new Exception("Jedan od unetih podataka je neispravan!");

            } catch (Exception e) {
                System.err.println(e.getMessage());
                brojLogovanja++;
            }
        }
    }

    private static boolean adminCheck(String username) {
        if(username.startsWith("k")) {
            System.out.println("Lozinka: ");
            String tmpPassword = new Scanner(System.in).nextLine();
            return (proveraBaze(username, tmpPassword));
        }else return false;
    }



    private static boolean korisnikCheck(String username) {
        if(username.startsWith("k")) {
            System.out.println("Lozinka: ");
            String tmpPassword = new Scanner(System.in).nextLine();
            return (proveraBaze(username, tmpPassword));
        }else return false;
    }

    private static boolean proveraBaze(String username, String tmpPassword) {

        return true;
    }
}
