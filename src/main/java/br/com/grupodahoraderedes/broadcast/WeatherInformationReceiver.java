package br.com.grupodahoraderedes.broadcast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class WeatherInformationReceiver
	extends
		Thread
{
	private static final int PORT = 23413;
	private static final String INET_ADDRESS = "230.0.0.0";

	@Override
	public void run()
	{
		try (final MulticastSocket socket = new MulticastSocket( PORT )) {
			final InetAddress group = InetAddress.getByName( INET_ADDRESS );
			socket.joinGroup( group );

			final byte[] buf = new byte[ 256 ];
			while (true) {
				final DatagramPacket packet = new DatagramPacket( buf, buf.length );
				socket.receive( packet );
				final String receivedMessage = new String( packet.getData(), 0, packet.getLength() );
				System.out.println( receivedMessage );
			}
		} catch (final Exception e) {
			System.out.println( "Broadcast do clima tá zuado." );
		}
	}
}
