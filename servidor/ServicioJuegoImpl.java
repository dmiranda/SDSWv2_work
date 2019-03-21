import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

class ServicioJuegoImpl extends UnicastRemoteObject implements ServicioJuego {
    hundir_flota_interface jugador_espera = null;
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
				jugador_espera.empieza_partida(c);
				c.empieza_partida(jugador_espera);				
			}
			catch(RemoteException re){
				System.out.println("No se ha podido realizar conexion");
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
}
