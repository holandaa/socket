package holandaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente extends Thread {
	
	private static boolean done = false;
	private Socket conexao;

	public Cliente(Socket s) {
		conexao = s;
	}

	public static void main(String[] args) throws IOException {
		
		Socket conexao = new Socket("localhost", 2000);
		PrintStream saida = new PrintStream(conexao.getOutputStream());
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Escreva seu nome: ");
		String meuNome = teclado.readLine();
		saida.println(meuNome);
		Thread t = new Cliente(conexao);
		t.start();
		String linha;
		while (true) {
			if (done) {
				break;
			}

			System.out.println("> ");
			linha = teclado.readLine();
			saida.println(linha);
		}
	}

	public void run() {
		BufferedReader entrada = null;
		try {
			entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String linha = null;
		while (true) {
			try {
				linha = entrada.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (linha.trim().equals("")) {
				System.out.println("Conexao fechada!");
				break;
			}
			System.out.println();
			System.out.println(linha);
			System.out.print("...> ");
		}
		done = true;
	}

}