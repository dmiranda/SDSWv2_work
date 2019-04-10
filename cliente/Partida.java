
import java.rmi.*;

interface Partida extends Remote {
    int		tiro(int id, int casilla) throws RemoteException;
	void 	listo(int id, int b1[], int b2[], int b3[], int b4[]) throws RemoteException;
	void 	salida() throws RemoteException;
	boolean	getTurno(int id) throws RemoteException;
	void	fin_partida(int id) throws RemoteException;
}
