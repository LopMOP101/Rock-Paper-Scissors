import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Scanner;


public class Main {

    private static Moves moveset;
    private static final String HMAC_ALGO = "HmacSHA384";
    private static boolean endofgame = true;
    private static int usermove;

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length*2);
        for(byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static byte[] GenerateKey()
    {
        SecureRandom secureRandom = new SecureRandom();
        byte []bytes = new byte[16];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    private static String GenerateHMAC(byte[] bytes, String move) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac signer = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(bytes, HMAC_ALGO);
        signer.init(keySpec);
        String messageStr = move;
        byte[] digest = signer.doFinal(messageStr.getBytes("utf-8"));
        return bytesToHex(digest);
    }

    private static void beformovework(String hmac)
    {
        System.out.println("HMAC:");
        System.out.println(hmac);
        System.out.println("Available moves:");
        for (int i=0; i<moveset.moves.length;i++)
            System.out.println(Integer.toString(i+1)+"-"+moveset.moves[i]);
        System.out.println("0-exit");
        System.out.println("Enter your move:");
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        moveset = new Moves(args);
        if (moveset.moves != null)
        {
            byte[] key= GenerateKey();
            int computerMove=(int)(Math.random()*moveset.moves.length);
            String hmac = GenerateHMAC(key,moveset.moves[computerMove]);
            while (endofgame) {
                beformovework(hmac);
                try {
                    Scanner sc = new Scanner(System.in);
                    usermove = sc.nextInt() - 1;
                    if (usermove != (-1))
                        System.out.println("Your move:" + moveset.moves[usermove]);
                } catch (Exception e) {
                    System.out.println("Error in the entered data ");
                    usermove=-1;
                }
                if (usermove == (-1)) {
                    endofgame = false;
                } else {
                    System.out.println("Computer move:" + moveset.moves[computerMove]);
                    resultofgame(usermove, computerMove);
                    System.out.println("HMAC key:" + bytesToHex(key));
                    endofgame = false;
                }
            }
        }
    }

    private static void resultofgame(int usermove, int computerMove) {
    if (usermove == computerMove)
    {
     System.out.println("Draw!");
    }
    else
        if (!moveset.iswingame(computerMove,usermove))
        {
            System.out.println("You win!");
        }
        else
            System.out.println("Computer win!");
    }


}
