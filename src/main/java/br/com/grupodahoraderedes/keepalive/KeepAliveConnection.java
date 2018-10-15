package br.com.grupodahoraderedes.keepalive;

import static br.com.grupodahoraderedes.ClientApplication.HOST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import br.com.grupodahoraderedes.ClientApplication;

public class KeepAliveConnection
	extends
		Thread
{
	private static final int PORT = 12312;
	private static final int KEEP_ALIVE_TIME = 5_000;

	private final boolean running = true;

	@Override
	public void run()
	{
		try (Socket socket = new Socket( HOST, PORT );
			PrintWriter writer = new PrintWriter( socket.getOutputStream(), true )) {
			cancelConnectionOnExitMessage( new BufferedReader( new InputStreamReader( socket.getInputStream() ) ) );
			keepAlive( writer, socket.getInetAddress().getHostAddress() );
		} catch (final Exception e) {
			System.out.println( "O consagrado não tá de pé. se vira mané." );
		}
	}

	private void keepAlive(
		final PrintWriter writer,
		final String ip
	)
		throws InterruptedException,
			IOException
	{
		while (running) {
			Thread.sleep( KEEP_ALIVE_TIME );
			writer.println( "1" );
		}
	}

	private void cancelConnectionOnExitMessage(
		final BufferedReader reader
	)
	{
		new Thread() {
			@Override
			public void run()
			{
				try {
					final String message = reader.readLine();
					while (message != null) {
						ClientApplication.closeApplication( message );
					}
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
