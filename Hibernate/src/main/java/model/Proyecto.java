package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Proyectos")
@NamedQueries({
		@NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre LIKE :nombre"),
		@NamedQuery(name = "Proyecto.findAll", query = " FROM Proyecto p"), })

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Proyecto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "proyectos_id")
	private Integer id;
	private String nombre;

	@ManyToMany(mappedBy = "proyectos", fetch = FetchType.EAGER)
	private Set<Empleado> empleados;

	@Override
	public String toString() {
		

		return "\n" + "ID Proyecto:" + id + ", Nombre:" + nombre + ", Empleados:" + mostrarEmpleados(empleados);

	}
	
	private List<String> mostrarEmpleados(Set<Empleado> misEmpleados) {
		List<String> empleados = new ArrayList<String>();
		if (misEmpleados != null) {
			for (Empleado empleado : misEmpleados) {
				empleados.add("(Id: " + empleado.getId() + ", Nombre: " + empleado.getNombre() + ", Salario: "
						+ empleado.getSalario() + ")]");
			}
			return empleados;
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Proyecto))
			return false;
		Proyecto proyectos = (Proyecto) o;
		return id.equals(proyectos.id) && nombre.equals(proyectos.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

}
