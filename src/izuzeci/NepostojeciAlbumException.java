package izuzeci;

public class NepostojeciAlbumException extends Exception{

    public NepostojeciAlbumException() {
        System.err.println("Album ciji ste ID uneli ne postoji!");
    }
    public void printStackTrace(){
        System.err.println("Album ne postoji u bazi podataka.");
    }

}