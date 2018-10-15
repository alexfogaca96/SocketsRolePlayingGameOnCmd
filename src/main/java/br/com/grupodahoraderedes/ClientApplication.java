package br.com.grupodahoraderedes;

import br.com.grupodahoraderedes.action.ActionConnection;
import br.com.grupodahoraderedes.broadcast.WeatherInformationReceiver;
import br.com.grupodahoraderedes.keepalive.KeepAliveConnection;

public class ClientApplication
{
	public static String HOST;

	public static void main(
		final String[] arguments
	)
	{
		checkArguments( arguments );
		start();
	}

	private static void start()
	{
		new KeepAliveConnection().start();
		new WeatherInformationReceiver().start();
		new ActionConnection().start();
	}

	private static void checkArguments(
		final String[] arguments
	)
	{
		if (arguments.length == 0) {
			System.out.println( "passa o host cacetada" );
			System.exit( 1 );
		}
	}

	public static void closeApplication(
		final String message
	)
	{
		System.out.print( message );
		System.exit( 1 );
	}
}
