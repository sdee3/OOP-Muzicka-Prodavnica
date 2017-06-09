package modeli;

import java.util.Scanner;

public class Administrator extends Osoba{

    public Administrator(String username, String password) {
        super(username, password);
    }

    @Override
    public String unosLozinke(Scanner unos) {
        return unos.nextLine();
    }


    public String getMeni(){
        return "\n1. Unos pesme\n2. Unos izvođača\n3. Unos albuma\n4. Ažuriranje pesme\n5. Ažuriranje izvođača" +
                "\n6. Ažuriranje albuma\7. Brisanje pesme\n8. Brisanje izvođača\n9. Brisanje albuma\n10. Odjava\n";
    }

}
