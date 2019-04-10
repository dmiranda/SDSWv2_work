import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

class ServicioJuegoImpl extends UnicastRemoteObject implements ServicioJuego {
    hundir_flota_interface jugador_espera = null;
	int numPartida = 0;
	
    ServicioJuegoImpl() throws RemoteException {
    }
	
    public void alta(hundir_flota_interface c) throws RemoteException {
		System.out.println("Nuevo jugador!");
		if(jugador_espera==null){
			jugador_espera = c;
			System.out.println("\tJugador en espera!");
		}
		else{
			try{
				PartidaImpl partida = new PartidaImpl(jugador_espera, c, numPartida);	
				jugador_espera.asigna_partida(partida);
				c.asigna_partida(partida);
				numPartida++;
			}
			catch(RemoteException re){
				System.out.println("No se ha podido realizar conexion");
			}
			catch(Exception excp){
				System.out.println("Problemas en el servidor");
			}
			finally{
				jugador_espera=null;
			}
		}
    }
	
    public void baja(hundir_flota_interface c) throws RemoteException {
		jugador_espera=null;
		System.out.println("\tJugador salio");
    }
	
	public boolean hello () throws RemoteException {
		return true;
	}
}
