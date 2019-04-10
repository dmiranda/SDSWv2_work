
import java.rmi.*;

interface ServicioJuego extends Remote {
    void 	alta(hundir_flota_interface c) throws RemoteException;
    void 	baja(hundir_flota_interface c) throws RemoteException;
	boolean hello() throws RemoteException;
}
