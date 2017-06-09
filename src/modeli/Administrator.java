package modeli;

import java.util.Scanner;

public class Administrator extends Osoba{

    private Administrator(String username, String password) {
        super(username, password);
    }

    public String getMeni(){
        return "\n1. Unos pesme\n2. Unos izvođača\n3. Unos albuma\n4. Ažuriranje pesme\n5. Ažuriranje izvođača" +
                "\n6. Ažuriranje albuma\7. Brisanje pesme\n8. Brisanje izvođača\n9. Brisanje albuma\n10. Odjava\n";
    }

    public static Administrator adminCheck(String username) {
        System.out.println("Lozinka: ");
        String tmpPassword = new Scanner(System.in).nextLine();
        return new Administrator(username, tmpPassword);
    }

}