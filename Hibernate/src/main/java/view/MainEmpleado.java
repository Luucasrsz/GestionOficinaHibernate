package view;

import java.util.List;

import controllers.GestionController;
import io.IO;
import model.Empleado;
import repositories.DepartamentosRepository;
import repositories.EmpleadosRepository;
import repositories.ProyectosRepository;

public class MainEmpleado {

	static GestionController controller = new GestionController(new EmpleadosRepository(),
			new DepartamentosRepository(), new ProyectosRepository());

	public static void menu() {

		while (true) {
			System.out.println("""
					\n1.Buscar empleado por codigo
					2.Mostrar empleados
					3.Añadir un empleado
					4.Eliminar un empleado
					5.Buscar empleado por nombre
					6.Modificar empleado
					7.Escribir Json
					0.Salir""");
			switch (IO.readInt()) {
			case 1:
				buscarPorCodigo(controller);
				break;
			case 2:
				mostrarEmpleados();
				break;
			case 3:
				anadirEmpleado(controller);
				break;
			case 4:
				borrarEmpleado(controller);
				break;
			case 5:
				buscarEmpleadoPorNombre(controller);
				break;
			case 6:
				modificarEmpleado(controller);
				break;
			case 0:
				return;
			default:
			}
		}

	}

	private static void buscarPorCodigo(GestionController controller) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		Empleado e = controller.getEmpleadosById(id);
		IO.println(e.toString());
	}

	private static void mostrarEmpleados() {
		List<Empleado> empleados = controller.getEmpleados();
		IO.print(empleados);
	}

	private static void anadirEmpleado(GestionController controller) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		IO.print("Salario ? ");
		Double salario = IO.readDouble();

		boolean anadido = controller.createEmpleado(Empleado.builder().nombre(nombre).salario(salario).build());
		IO.println(anadido ? "Añadido" : "No se ha podido añadir");
	}

	private static void borrarEmpleado(GestionController controller) {
		IO.print("Código ? ");
		Integer id = IO.readInt();
		boolean borrado = controller.deleteEmpleado(id);
		IO.println(borrado ? "Borrado" : "No se ha podido borrar");

	}

	private static void buscarEmpleadoPorNombre(GestionController controller) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();
		List<Empleado> empleados = controller.getEmpleadosByName(nombre);
		IO.println(empleados);
	}

	private static void modificarEmpleado(GestionController controller) {
		Boolean anadido = false;
		IO.print("Código del empleado a modificar ? ");
		Integer id = IO.readInt();

		IO.print("Nombre?");
		String nombre = IO.readString();

		IO.print("Salario empleado?");
		Double salario = IO.readDoubleOrNull();

		IO.print("Código del departamento a introducir ? ");
		Integer idDep = IO.readIntOrNull();

		IO.print("Código del proyecto a introducir ? ");
		Integer idProyecto = IO.readIntOrNull();

		anadido = controller.updateEmpleado(id, salario, nombre, idDep, idProyecto);

		IO.println(anadido ? "Modificado" : "No se ha podido modificar");
	}

}