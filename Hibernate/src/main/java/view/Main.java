package view;

import java.util.List;
import java.util.logging.Logger;

import Utils.ApplicationProppperties;
import db.HibernateManager;
import io.IO;

public class Main {
	static Logger logger = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) {

		initDataBase();
		
		List<String> opciones = List.of( 
				"""
						\n1.Empleados
						2.Departamentos
						3.Proyectos
						0.Salir
						""");
		
		while (true) {
			IO.println(opciones);
			switch (IO.readInt()) {
			case 1:
				MainEmpleado.menu();
				break;
			case 2:
				MainDepartamento.menu();
				break;
			case 3:
				MainProyectos.menu();
				break;
				
			case 0:
				//HibernateManager.close();
				return;
			default:
			}
		}		
				
	}
	
    private static void initDataBase() {
        // Leemos el fichero de configuración

        ApplicationProppperties properties = new ApplicationProppperties();
        logger.info("Leyendo fichero de configuración..." + properties.readProperty("app.title"));
        HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.close();
    }


}

