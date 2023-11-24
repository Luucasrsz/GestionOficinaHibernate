package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mpleados")
@NamedQueries({
		@NamedQuery(name = "Empleado.findByNombre", query = "SELECT e FROM Empleado e WHERE e.nombre LIKE :nombre"),
		@NamedQuery(name = "Empleado.findAll", query = "FROM Empleado e") })

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empleado_id")
	private Integer Id;
	String nombre;
	Double salario;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "empleado_proyectos", joinColumns = @JoinColumn(name = "empleado_id"), inverseJoinColumns = @JoinColumn(name = "proyectos_id"))
	private Set<Proyecto> proyectos  ;

	@ManyToOne
	@JoinColumn(name = "departamento_id")
	private Departamento departamento;

	@Override
	public String toString() {
		if (departamento == null) {
			return "\n" + "ID Empleado:"+ Id + ", Nombre:"+nombre+", Salario:"+salario + ", Proyectos:" + mostrarProyectos(proyectos);
		}else {
			return "\n" + "ID Empleado:"+Id+", Nombre:"+ nombre+", Departamento:"+ departamento.getNombre()+", Salario:"+ salario + ", Proyectos:" + mostrarProyectos(proyectos);
		}
	}

	private List<String> mostrarProyectos(Set<Proyecto> misProyectos) {
		List<String> proyectos = new ArrayList<String>();
		for (Proyecto proyecto : misProyectos) {
			proyectos.add("(Id: " + proyecto.getId() + ", Nombre: " + proyecto.getNombre() + ")");
		}
		return proyectos;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Empleado))
			return false;
		Empleado empleado = (Empleado) o;
		return Id.equals(empleado.Id) && nombre.equals(empleado.nombre) && salario.equals(empleado.salario);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Objects.hash(Id, nombre, salario);
	}

}
