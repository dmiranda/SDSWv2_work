
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*;
import java.rmi.server.*;

class juegos_v2 extends JFrame implements ActionListener{

	private JButton b[] = new JButton[2];
	ServicioJuego srv;
	String servidor, puerto;
	
	juegos_v2(String server,String port){
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
				hundir_flota_v6 juego1= new hundir_flota_v6();
				System.out.println("Creado");
				srv.alta(juego1);
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
			//srv.baja(j);
            System.exit(0);
		}
	}
	/************************************************************/

	public static void main(String[] args){
		if (args.length!=2) {
            System.err.println("Uso: juegos_v2 hostregistro numPuertoRegistro");
            return;
        }

       if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
			new juegos_v2(args[0],args[1]);
        }
        catch (Exception e) {
            System.err.println("Excepcion en ClienteChat:");
            e.printStackTrace();
        }

	}
}