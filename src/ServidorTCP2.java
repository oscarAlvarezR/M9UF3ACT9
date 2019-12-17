import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ServidorTCP2 implements Runnable {

	Socket client;
	ServerSocket server;
	static int numClient;
	String cadena = "";

	public ServidorTCP2(Socket clientConnectat, ServerSocket server) {
		this.client = clientConnectat;
		this.server = server;
		this.numClient ++;
	}

	public static void main (String[] args) throws IOException {

		// **********SEGONA PART**********
		Scanner teclado = new Scanner(System.in);


		int numPort = 60000;
		ServerSocket servidor = new ServerSocket(numPort);



		// Demanem el numClients per a indicar 
		// el max de clients que rebra el servidor
		System.out.println("Introdueix el num de clients que podra rebre el servidor: ");
		int numClients = teclado.nextInt();


		Runnable[] arrayRunnable = new Runnable[numClients];
		Thread[] arrayThread = new Thread[numClients];

		// Determinem les vegades que es conectaran els clients
		for (int i = 0; i < arrayRunnable.length; i++) {


			Socket clientConnectat = servidor.accept();

			// Runnable
			arrayRunnable[i] = new ServidorTCP2(clientConnectat, servidor);

			// Thread
			arrayThread[i] = new Thread(arrayRunnable[i]);
			arrayThread[i].start();

		}


	}

	@Override
	public void run() {

		try {
			PrintWriter fsortida = null;
			BufferedReader fentrada = null;

			System.out.println("Esperant connexi�... ");
			System.out.println("Client " + this.numClient + " connectat... ");

			//FLUX DE SORTIDA AL CLIENT
			fsortida = new PrintWriter(this.client.getOutputStream(), true);


			//FLUX D'ENTRADA DEL CLIENT
			fentrada = new BufferedReader(new InputStreamReader(this.client.getInputStream()));


			while ((cadena = fentrada.readLine()) != null) {

				fsortida.println(cadena);
				System.out.println("Rebent: "+cadena);
				if (cadena.equals("*")) break;

			}
			fentrada.close();
			fsortida.close();
			this.client.close();
			this.server.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}