package br.com.grupodahoraderedes.action;

import static br.com.grupodahoraderedes.ClientApplication.HOST;

import java.net.Socket;

import br.com.grupodahoraderedes.ClientApplication;

public class ActionConnection
	extends
		Thread
{
	private static final int PORT = 42333;

	@Override
	public void run()
	{
		try {
			@SuppressWarnings( "resource" )
			final Socket socket = new Socket( HOST, PORT );
			new ActionHandler( socket.getInputStream(), socket.getOutputStream() ).start();
		} catch (final Exception e) {
			ClientApplication.closeApplication( "Server não me quer." );
		}
	}
}
