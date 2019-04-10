import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

class PartidaImpl extends UnicastRemoteObject implements Partida {
    hundir_flota_interface jugadores[] = {null , null};
	int jugador_turno = 0;
	int ID_players [] = {0,0};
	
	PrintWriter log_partida;
	
	//Constructor, que recibe los dos jugadores
    public PartidaImpl(hundir_flota_interface p1, hundir_flota_interface p2,int num) throws RemoteException {
		try{
			//Configuramos el jugador 1
			jugadores[0] = p1;
			ID_players[0] = 14569;
			jugadores[0].empieza_partida(ID_players[0]);
			
			//Configuramos el jugador 2
			jugadores[1] = p2; 
			ID_players[1] = 562194;			
			jugadores[1].empieza_partida(ID_players[1]);

			log_partida = new PrintWriter(new FileWriter("partida"+num));
		}
		catch(RemoteException re){
			System.out.println("No se ha podido realizar conexion");
		}
		
		catch (IOException e) {
            System.err.println(e);
        }
		
		catch(Exception excp){
			System.out.println("Problemas en el servidor");
		}
		
    }
	
	//Método encargado de eliminar la partida, pues un jugador decide salir de ella
	public void salida() throws RemoteException {
		System.out.println("El jugador se ha cansado de esperar");
		jugadores[0] = null;
    }
	
	//Método que gestiona los tiros de un jugador:
	/*	- Recibe la casilla seleccionada
		- Manda la casilla al oponente
		- Recibe el resultado del tiro
		- Lo almacena en el log
		- Lo manda de vuelta al jugador que ha relizado el tiro
	*/
	public int	tiro(int id, int casilla) throws RemoteException{
		String result;
		int jugador_oponente;
		
		//Configuramos el jugador oponente
		if(jugador_turno == 1) jugador_oponente = 0;
		else jugador_oponente = 1;
		
		if(id == ID_players[jugador_turno]){
			try{
				int resultado = jugadores[jugador_oponente].tiro(casilla);
				if(resultado == 0)
					//ESCRIBIR AGUA
					result = new String("Agua");
				else if(resultado == 1)
					//ESCRIBIR TOCADO
					result = new String("Tocado");
				else
					//ESCRIBIR HUNDIDO
					result = new String("Hundido");
				
				log_partida.println("Jugador " + (jugador_turno + 1) + " lanza un disparo a casilla " + casilla + " -> " + result);
				log_partida.flush();
				
				if(resultado == 4)
					fin_partida(jugador_oponente);
				
				else{
					//Indicamos que ahora el turno es del otro jugador
					if(jugador_turno == 1) jugador_turno = 0;
					else jugador_turno = 1;
				
					jugadores[jugador_turno].Turno();
				}
				
				
				return resultado;
			}
			catch(Exception re){
				System.out.println(re.toString());
				return 4;
			}
		}
		
		else
			throw new RemoteException("Este jugador no tiene el turno");
		
	}
	
	//Método que gestiona el "fijar mapa" del jugador
	// Este método, recibe la lista de las casillas donde se encuentran los barcos del jugador, y los almacena en un log
	public void listo(int id, int b1[], int b2[], int b3[], int b4[]) throws RemoteException{
		int jug;
		
		if(id == ID_players[0])
		{
			jugadores[1].listo();
			jug = 1;
		}
		
		else if (id == ID_players[1])
		{
			jugadores[0].listo();
			jug = 2;
		}
		
		else
			throw new RemoteException ("Jugador no valido");
		
		//Imprimimos en el log, la posicion de los barcos
		log_partida.println("Disposicion de los barcos del jugador " + jug);
		log_partida.println("\tSalvavidas " + b1[0]);
		log_partida.println("\tBuque " + b2[0] + "," + b2[1]);
		log_partida.println("\tAcorazado " + b3[0] + "," + b3[1] + "," + b3[2]);
		log_partida.println("\tPortaviones " + b4[0] + "," + b4[1] + "," + b4[2] + "," + b4[3] + "," + b4[4]);
		log_partida.flush();
	}
	
	//Método que devuelve un true si el jugador que la solicita tiene el turno, o false en caso de que sea su contrincante
	public boolean 	getTurno(int ID) throws RemoteException{
		
		if(ID == ID_players[jugador_turno])
			return true;
		
		else
			return false;
	}
	
	//Método que gestiona el fin de partida
	/*	- Recibe el ID del jugador ganador
		- Llama al método en el oponente que le indica que ha ganado
		- Almacena la información en el log y en la base de datos
	*/
	public void fin_partida(int id) throws RemoteException {
		int ganador = 100;
		
		//ESCRIBIR EN LA BDD EL RESULTADO FINAL
		jugadores[id].fin_partida();
		if(id == 0) ganador = 2;
		else ganador = 1;
		
	
		log_partida.println("El ganador ha sido el jugador " + ganador);
		log_partida.flush();
	}
}
