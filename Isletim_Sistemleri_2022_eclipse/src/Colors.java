import java.util.ArrayList;

public class Colors {


    public  String Renkler(int id){
        ArrayList<String> renkler = new ArrayList<>();
        renkler.add("\u001B[35m");
        renkler.add("\u001B[31m");
        renkler.add("\u001B[32m");
        renkler.add("\u001B[33m");
        renkler.add("\u001B[34m");
        renkler.add("\u001B[35m");
        renkler.add("\u001B[36m");
        renkler.add("\u001B[37m");

        return renkler.get(id%8);
    }
}
