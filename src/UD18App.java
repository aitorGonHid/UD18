import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import methods.DB;
import methods.DB;



public class UD18App {
	
	static String accion;
	
	public static void main(String[] args) {
		
		
		String accion = "continuar";
		String dbName = "";
		String tableName = "";
		String fields = "";
		String values = "";
		//Solicita accion a realizar
		
		while (!accion.equals("salir")) {
			accion = JOptionPane.showInputDialog("Que accion quieres realizar?\n crearBD, creaTabla, insertar o salir");
			switch(accion){
			case ("crearDB"):
				dbName = JOptionPane.showInputDialog("Nombre de la BDD:");
				DB.crearBD(dbName);
				break;
			case ("crearTabla"):
				dbName = JOptionPane.showInputDialog("Nombre de la BDD:");
				int confirm = JOptionPane.showConfirmDialog(null, "Es un base de datos nueva?", "ATENCION", JOptionPane.YES_NO_OPTION);
				if (confirm == 0) DB.crearBD(dbName);
				tableName = JOptionPane.showInputDialog("Introduce el nombre de la tabla:");
				DB.createTable(dbName, tableName);
			case ("insertar"):
				dbName = JOptionPane.showInputDialog("Nombre de la BDD:");
				tableName = JOptionPane.showInputDialog("Introduce el nombre de la tabla:");
				fields = JOptionPane.showInputDialog("Introduce los campos separados por comas\ncampo1, campo2, campo3...:");
				values = JOptionPane.showInputDialog("Introduce los valores de los campos, separados por espacios en el orden:\n"+fields);
				DB.insertData(dbName, tableName, fields, values);
				
			}
		}
		
	}
}
