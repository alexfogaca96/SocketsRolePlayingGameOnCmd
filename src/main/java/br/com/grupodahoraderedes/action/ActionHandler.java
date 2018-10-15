package br.com.grupodahoraderedes.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import br.com.grupodahoraderedes.ClientApplication;

public class ActionHandler
	extends
		Thread
{
	private final BufferedReader reader;
	private final PrintWriter writer;

	private final BufferedReader consoleReader;

	public ActionHandler(
		final InputStream input,
		final OutputStream output
	)
	{
		this.reader = new BufferedReader( new InputStreamReader( input ) );
		this.writer = new PrintWriter( output, true );
		this.consoleReader = new BufferedReader( new InputStreamReader( System.in ) );
	}

	@Override
	public void run()
	{
		try {
			while (true) {
				readCurrentScene();
				answerServerMessage();
			}
		} catch (final IOException e) {
			ClientApplication.closeApplication( "Server não recebe nem manda nada." );
		}
	}

	private void readCurrentScene()
	{
		try {
			final int numberSceneLines = Integer.parseInt( readServerMessage() );
			for (int i = 0; i < numberSceneLines; i++) {
				System.out.println( readServerMessage() );
			}
		} catch (final Exception e) {
			ClientApplication.closeApplication( "Impossível ler fases." );
		}
	}

	private String readServerMessage()
		throws IOException
	{
		String message = null;
		while (( message = reader.readLine() ) != null) {
			return message;
		}
		throw new IOException();
	}

	private void answerServerMessage()
		throws IOException
	{
		String answer = null;
		while (( answer = consoleReader.readLine() ) != null) {
			if (!isOneOfPlayerActions( answer ) && !isInteger( answer )) {
				System.out.println(
					"Digite somente o número da opção desejada ou uma das ações padrão (inventory, equip <equipment>)!" );
			} else {
				writer.println( answer );
				break;
			}
		}
	}

	private boolean isOneOfPlayerActions(
		final String answer
	)
	{
		final String[] splittedAnswer = answer.split( " " );
		switch (splittedAnswer[ 0 ]) {
			case "inventory":
			case "equip":
				return true;
			default:
				return false;
		}
	}

	public static boolean isInteger(
		final String string
	)
	{
		return isInteger( string, 10 );
	}

	public static boolean isInteger(
		final String string,
		final int radix
	)
	{
		if (string.isEmpty()) {
			return false;
		}
		for (int i = 0; i < string.length(); i++) {
			if (i == 0 && string.charAt( i ) == '-') {
				if (string.length() == 1) {
					return false;
				} else {
					continue;
				}
			}
			if (Character.digit( string.charAt( i ), radix ) < 0) {
				return false;
			}
		}
		return true;
	}
}
