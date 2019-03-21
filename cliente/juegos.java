
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*;
import java.rmi.server.*;

class juegos extends JFrame implements ActionListener{

	private JButton b[] = new JButton[2];
	ServicioJuego srv;
	String servidor, puerto;
	hundir_flota user;
	
	juegos(String server,String port){
		super("Centro de actividades by davmirrom");

		b[0]=new JButton("Jugar al hundir la flota");
		b[0].addActionListener(this);
		add(b[0]);
		
		b[1]=new JButton("Salir");
		b[1].addActionListener(this);
		add(b[1]);
		
		servidor = server;
		puerto = port;
		
		setLayout(new FlowLayout());
		setSize(400,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
		/*************************************************************/
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == b[0]){
			//JOptionPane.showMessageDialog(this,"Espera al jugador");
			try{
				srv = (ServicioJuego) Naming.lookup("//" + servidor + ":" + puerto + "/Juegos");
				System.out.println("Conectado");
				user = new hundir_flota();
				System.out.println("Creado");
				srv.alta(user);
				System.out.println("Alta");
			}
			
			catch(Exception re)
			{	
				System.out.println(re.toString());
				JOptionPane.showMessageDialog(this,"No se puede conectar con el servidor");
			}
		}
		
		else
		{
			try{
				srv.baja(user);
				System.exit(0);
			}
			
			catch (Exception ra){}
		}
	}
	/************************************************************/

	public static void main(String[] args){
		if (args.length!=2) {
            System.err.println("Uso: juegos hostregistro numPuertoRegistro");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
			new juegos(args[0],args[1]);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteChat:");
            e.printStackTrace();
        }

	}
}