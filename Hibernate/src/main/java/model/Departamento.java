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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Departamento")
@NamedQueries({
	@NamedQuery(name = "Departamento.findByNombre", query = "SELECT d FROM Departamento d WHERE d.nombre LIKE :nombre"),
	@NamedQuery(name = "Departamento.findAll", query = "SELECT  d FROM Departamento d") })

@AllArgsConstructor
@NoArgsConstructor
@Data 
@Builder
public class Departamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "departamento_id")	
	private Integer Id;
	String nombre;
	
	@OneToOne
	Empleado jefe;
	
	@OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
    private Set<Empleado> empleados ;
	
	
	@Override
	public String toString() {
		// Construir una cadena que representa el contacto con los atributos.
		if(jefe == null) {
			return "\n" + "ID Departamento:" + Id +", Nombre:" + nombre + ", Empleados: " + mostrarEmpleado(empleados);
		}else {
			return "\n" +  "ID Departamento:" + Id +", Nombre:" +  nombre +", Jefe de Departamento:" + jefe.getNombre() + "Empleados: " + mostrarEmpleado(empleados) ;
		}
		
		
	}
	
	private List<String> mostrarEmpleado(Set<Empleado> empleados) {
		List<String> e = new ArrayList<String>();
		for (Empleado empleado : empleados) {
			e.add("(Id: " + empleado.getId() + ", Nombre: " + empleado.getNombre() + ")");
		}
		return e;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departamento)) return false;
        Departamento departamento = (Departamento) o;
        return Id.equals(departamento.Id) && nombre.equals(departamento.nombre) && jefe.equals(departamento.jefe);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Id,nombre,jefe);
	}

}
