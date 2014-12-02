package conexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;



/**
*	CLASE CONEXION: Permite conectarse a un servidor de base de datos tanto en modo "TRADICIONAL" como en modo "POOL".
*
*	1) ELEGIR EL MODO:
*			- MODO TRADICIONAL: se conecta y por cada transaccion que hagamos abre un servicio y luego lo vuelve a cerrar esto consume muchos recursos
*								del sistema. Está bien para hacer pruebas pero "NO SIRVE PARA PRODUCCION", porque iría la maquina muy lenta.
*	
			- MODO POOL:	Lo que hace es que deja las conexiones a la base de datos abiertas y sólo hace un cerrado lógico. Al ponerlo en produccion 
							hay que ponerlo en este modo.

							
*	2) ELEGIR LA "URL":		Para conectarse tan sólo hace falta poner la url a la base de datos a la que vamos a conectar.
*							- El primer "url" (q está comentado) se puede elegir cuando los procesos son lentos. se saca la url del archivo oracle "tsnames.ora"
*							- El tercer "url" es el que utiliza Fernando para conectar normalmente.
*/
public class Conexion {
    public Conexion() {
    }
    public Connection getConexion() throws SQLException,Exception{
     
    	//String url="jdbc:oracle:thin:vs2daw_13/vs2daw_13@10.0.1.12:1521:oradai";
    	//String url="jdbc:oracle:thin:vs2daw_13/vs2daw_13@80.59.249.199:1521:oradai";
        String url="jdbc:oracle:thin:CARROCOMPRA/CARROCOMPRA@192.168.1.201:1521:ORCL"; //CONEXION BDD MAQUINA VIRTUAL
        //String url="jdbc:oracle:thin:CARROCOMPRA/CARROCOMPRA@192.168.15.166:1521:ORCL"; //CONEXION BDD MAQUINA VIRTUAL
    	//String url="jdbc:oracle:thin:vs2daw_13/vs2daw_13@localhost:1521:oradai";
    	
        Connection con = null; 
        OracleDataSource ods;
        DataSource ds;
        
        try{
    	   ods=new OracleDataSource();
           ods.setURL(url);
           con=ods.getConnection();
           con.setAutoCommit(false); //ESTO SIRVE PARA QUE NO HAGA AUTO COMMIT´S LUEGO LOS TENGO QUE HACER YO A MANO

            DatabaseMetaData meta = con.getMetaData();
            // gets driver info:
            System.out.println("JDBC driver version is " + meta.getDriverVersion());
            System.out.println("Data Source definido y conexion establecida   " + meta.toString());
        }
        catch(SQLException sqle){
        	System.out.println(sqle.toString());
            throw sqle;
        }
        catch(Exception e){
        	System.out.println(e.toString());
            throw e;
        }
        return con;
    }   
}
