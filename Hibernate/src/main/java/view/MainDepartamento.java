package view;

import java.util.List;

import controllers.GestionController;
import io.IO;
import model.Departamento;
import repositories.DepartamentosRepository;
import repositories.EmpleadosRepository;

public class MainDepartamento {

	public static void menu() {

		var controller = new GestionController(new EmpleadosRepository(), new DepartamentosRepository(), null);

		while (true) {
			System.out.println("""
					\n1.Buscar departamento por codigo
					2.Buscar departamento por nombre
					3.Mostrar departamentos
					4.Añadir un departamento
					5.Eliminar un departamento
					6.Modificar departamento
					0.Salir""");

			switch (IO.readInt()) {

			case 1:
				buscarDepartamentoById(controller);
				break;
			case 2:
				buscarDepartamentoByNombre(controller);
				break;
			case 3:
				mostrarDepartamentos(controller);
				break;
			case 4:
				insertarDepartamento(controller);
				break;
			case 5:
				eliminarDepartamento(controller);
				break;
			case 6:
				modificarDepartamento(controller);
				break;
			case 0:
				return;
			default:

			}
		}
	}

	private static void buscarDepartamentoById(GestionController controller) {
		IO.print("Codigo del departamento ? ");
		int codigo = IO.readInt();
		Departamento departamento = controller.getDepartamentosById(codigo);
		IO.println(departamento.toString());
	}

	private static void buscarDepartamentoByNombre(GestionController controller) {
		IO.print("Nombre del departamento ? ");
		String nombre = IO.readString();
		List<Departamento> departamento = controller.getDepartamentoByName(nombre);
		IO.println(departamento.toString());
	}

	private static void mostrarDepartamentos(GestionController controller) {
		List<Departamento> departamento = controller.getDepartamentos();
		IO.println(departamento.toString());
	}

	private static void modificarDepartamento(GestionController controller) {
		boolean anadido = false;
		IO.print("Codigo del departamento ? ");
		Integer codigo = IO.readInt();

		IO.print("Nombre ?");
		String nombre = IO.readString();

		IO.print("Jefe ? (Tiene que pertenecer al departamento)");
		Integer codigoJefe = IO.readInt();

		anadido = controller.updateDepartamento(codigo, nombre, codigoJefe);
		IO.println(anadido ? "Modificado" : "No se ha podido modificar");
	}

	private static void insertarDepartamento(GestionController controller) {
		IO.print("Nombre ? ");
		String nombre = IO.readString();

		boolean insertado = controller.createDepartamento(Departamento.builder().nombre(nombre).build());
		IO.println(insertado ? "Insertado" : "No se ha podido insertar");
	}

	private static void eliminarDepartamento(GestionController controller) {
		IO.print("Codigo del departamento ? ");
		int codigo = IO.readInt();
		boolean eliminado = controller.deleteDepartamento(codigo);
		IO.println(eliminado ? "Eliminado" : "No se ha podido eliminar");
	}

}