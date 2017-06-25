package izuzeci;

public class DuplikatAlbumaException extends Exception{

    public DuplikatAlbumaException() {
        System.err.println("Album je vec unet!");
    }
    public void printStackTrace(){
        System.err.println("Album koji ste pokusali uneti je vec u Vasoj biblioteci.");
    }

}