package methods;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class DB {
	
	//Atributos
	public static String ip = "192.168.1.146:3306";
	public static String user = "remote";
	public static String password = "L4t20_Mysql";
	public static Connection conexion;
	
	//Constructor
	public DB(String newIp, String newUser, String newPassword) {
		ip = newIp;
		user = newUser;
		password = newPassword;		
	}
	
	// METODOS PARA INTERACTUAR CON EL SERVIDOR
	
	//Metodo para conectar al servidor "+ ip +"
	public static void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion=DriverManager.getConnection("jdbc:mysql://"+ ip +"?useTimezone=true&serverTimezone=UTC",""+ user +"",""+ password +"");
			System.out.println("Conexion al servidor: OK");
		}catch(SQLException | ClassNotFoundException ex  ){
			System.out.println("Conexion al servidor: FAILED");
			System.out.println(ex.getMessage());	
		}
	}
	
	//Metodo para desconectar del servidor
	public static void disconnect() {
		try {
			
			conexion.close();
			System.out.println("Desconexion al servidor: OK");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null,"Error cerrando conexion");
		}
	}
	
	//Metodo para crear una base de datos
	public static void crearBD (String BDname) {
		try {
			DB.connect();
			String Query = "CREATE DATABASE " + BDname;
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Base de datos " + BDname + " creada con exito");
			DB.disconnect();
		}catch(SQLException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null,"Error creando base de datos");
		}finally {
			DB.disconnect();
		}
	}
	//Metodo que crea una tabla
	public static void createTable(String db,String name) {
		String campos = JOptionPane.showInputDialog("Introduce los campos y modificadores de la sentencia DDL\n CREATE TABLE TABLE_NAME( >>> CAMPOS <<< )");
		try {
			DB.connect();
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			String Query = "CREATE TABLE "+name+ "(" + campos + ")";
			System.out.println(Query);
			Statement st= conexion.createStatement();
			st.executeUpdate(Query);
			System.out.println("Tabla creada con exito!");
			DB.disconnect();
				
		}catch (SQLException ex){
			System.out.println(ex.getMessage());
			System.out.println("Error crando tabla.");
		}
	}
	//Metodo para insertar datos en una tabla
	public static void insertData(String db, String table_name, String campos, String valores) {
		try {
			DB.connect();
			String Querydb = "USE "+db+";";
			Statement stdb= conexion.createStatement();
			stdb.executeUpdate(Querydb);
			
			StringBuilder script = new StringBuilder("INSERT INTO "+table_name+" ("+campos+") VALUE("); //añade la primera parte de la sentencia DML
			Scanner sc = new Scanner(valores);
			while (sc.hasNext()) { //inserta los  valores a la sentencia
				script.append("\""+sc.next()+"\", ");
			}
			script.delete(script.length()-2, script.length()); //Elimina el ultimo ", "
			script.append(");"); //añade el parentesis final
			System.out.println(script.toString());
			String Query = script.toString();
			Statement st = conexion.createStatement();
			st.executeUpdate(Query);
			
			System.out.println("Datos almacenados correctamente");
			DB.disconnect();
			
		} catch (SQLException ex ) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null, "Error en el almacenamiento");
		}
					
	}
	
}
