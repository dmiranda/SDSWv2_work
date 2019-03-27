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
				Thread.sleep(1000);
				jugador_espera.empieza_partida(c,true);
				c.empieza_partida(jugador_espera,false);				
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
}
