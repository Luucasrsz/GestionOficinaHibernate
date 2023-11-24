package view;

import java.util.List;

import controllers.GestionController;
import io.IO;

import model.Proyecto;
import repositories.ProyectosRepository;

public class MainProyectos {

	public static void menu() {

		GestionController controller = GestionController.builder().proyectosRepository(new ProyectosRepository())
				.build();
		while (true) {
			System.out.println("""
					\n1.Buscar proyecto por Código
					2.Buscar proyecto por Nombre
					3.Mostrar proyectos
					4.Añadir proyecto
					5.ModiFicar proyecto
					6.Eliminar proyecto
					0.Salir
						""");

			switch ((IO.readInt())) {
			case 1:
				buscarPorCodigo(controller);
				break;

			case 2:
				buscarPorNombre(controller);

				break;
			case 3:
				mostrarTodos(controller);

				break;
			case 4:
				crear(controller);

				break;
			case 5:
				modificarProyecto(controller);

				break;
			case 6:
				borrar(controller);

				break;
			case 0:
				return;
			default:
			}
		}

	}

	private static void buscarPorCodigo(GestionController controller) {
		IO.println("CODIGO DE PROYECTO?");
		int id = IO.readInt();
		Proyecto p = controller.getProyectoById(id);
		IO.println(p.toString());
	}

	private static void buscarPorNombre(GestionController controller) {
		IO.println("NOMBRE DE PROYECTO?");
		String nombre = IO.readString();
		List<Proyecto> proyectos = controller.getProyectosByName(nombre);
		IO.println(proyectos.toString());
	}

	private static void mostrarTodos(GestionController controller) {
		List<Proyecto> proyectos = controller.getProyectos();
		IO.println(proyectos.toString());
	}

	private static void crear(GestionController controller) {
		IO.println("NOMBRE DE PROYECTO?");
		String nombre = IO.readString();
		Proyecto p = Proyecto.builder().nombre(nombre).build();
		Boolean anadido = controller.createProyecto(p);
		IO.println(anadido ? "Creado" : "No se ha podido crear");
	}

	private static void modificarProyecto(GestionController controller) {
		Boolean anadido = false;
		IO.print("Código del proyecto a modificar ? ");
		Integer id = IO.readInt();

		IO.print("Nombre ? ");
		String nombre = IO.readString();

		IO.print("Código Empleado a Añadir? ");
		Integer empId = IO.readIntOrNull();

		anadido = controller.updateProyecto(id, nombre, empId);
		IO.println(anadido ? "Modificado" : "No se ha podido modificar");
	}

	private static void borrar(GestionController controller) {
		IO.println("ID DE PROYECTO QUE QUIERA BORRAR?");
		Integer id = IO.readInt();
		Boolean anadido = controller.deleteProyecto(id);
		IO.println(anadido ? "Borrado" : "No se ha podido borrar");
	}

}
