import java.io.IOException;

public class Program {
    public static void main(String[] args)  throws IOException, InterruptedException  {
    	
        String fileName;
        if(args.length!=0){
        	fileName=args[0];
        }
        else {
        	fileName="giris.txt";
        }
        Commissioner commissioner=new Commissioner();
        commissioner.mixedSorter(fileName);
    }
}
