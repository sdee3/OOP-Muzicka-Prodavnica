package izuzeci;

public class DuplikatAlbumaException extends Exception{

    public DuplikatAlbumaException() {
        System.err.println("Album je već unet!");
    }
    public void printStackTrace(){
        System.err.println("Album koji ste pokušali uneti je već u Vašoj biblioteci.");
    }

}