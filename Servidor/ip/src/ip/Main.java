package ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) {
		InetAddress IP;

        try {

            IP = InetAddress.getLocalHost();

            System.out.println("Mi ip local es = "+IP.getHostAddress());

       } catch (UnknownHostException e) {

           e.printStackTrace();

       }
	}

}
