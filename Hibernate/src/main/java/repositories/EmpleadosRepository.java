package repositories;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.ExceptionEmpleado;

import jakarta.persistence.TypedQuery;

import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class EmpleadosRepository {

	private final Logger logger = Logger.getLogger(EmpleadosRepository.class.getName());

	public List<Empleado> findAll() {
		logger.info("findAll()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findAll", Empleado.class);
		List<Empleado> list = query.getResultList();
		hb.close();
		return list;
	}

	public boolean save(Empleado entity) throws ExceptionEmpleado {
		logger.info("guardando...()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.getTransaction().begin();
		try {
			hb.getManager().persist(entity);
			// Departamento departamento = hb.getManager().find(Empleado.class,
			// entity.getDepartamento());
			// departamento.setEmpleados().add(entity);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			throw new ExceptionEmpleado("Error al guardar empleado con id: " + entity.getId() + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	public Empleado findById(Integer id) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		// Optional<Empleado> empleado =
		// Optional.ofNullable(hb.getManager().find(Empleado.class, id));
		Empleado empleado = (hb.getManager().find(Empleado.class, id));
		hb.close();
		return empleado;
	}

	public List<Empleado> findByName(String name) {
		logger.info("findbyName()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Empleado> query = hb.getManager().createNamedQuery("Empleado.findByNombre", Empleado.class);
		query.setParameter("nombre", name + "%");
		List<Empleado> empleados = query.getResultList();
		hb.close();
		return empleados;
	}

	public boolean delete(Integer id) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			Empleado e = hb.getManager().find(Empleado.class, id);

			if (e.getDepartamento() != null) {
				Departamento d = hb.getManager().find(Departamento.class, e.getDepartamento().getId());
				Set<Empleado> emps = d.getEmpleados();
				emps.remove(e);
				d.setEmpleados(emps);
			}

			if (e.getProyectos().size() != 0) {
				Set<Proyecto> proyectos = e.getProyectos();
				for (Proyecto proyecto : proyectos) {
					proyecto.getEmpleados().remove(e);
				}
			}

			hb.getManager().remove(e);

			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			return false;

		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	public boolean update(Integer idEmp, Double salario, String nombre, Integer idDep, Integer idPro) {
		logger.info("update()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();

			Empleado empleado = hb.getManager().find(Empleado.class, idEmp);

			if (empleado != null) {
				if (!nombre.isBlank()) {
					empleado.setNombre(nombre);
				}
				if (salario != null) {
					empleado.setSalario(salario);
				}

				if (idDep != null) {
					Departamento d = hb.getManager().find(Departamento.class, idDep);
					if (d != null) {
						Set<Empleado> emps = d.getEmpleados();
						emps.add(empleado);
						d.setEmpleados(emps);
						empleado.setDepartamento(d);
					}
				}

				if (idPro != null) {
					Proyecto p = hb.getManager().find(Proyecto.class, idPro);
					if (p != null) {
						Set<Proyecto> proyectos = empleado.getProyectos();
						proyectos.add(p);
						empleado.setProyectos(proyectos);

						Set<Empleado> empleados = p.getEmpleados();
						empleados.add(empleado);
						p.setEmpleados(empleados);
					}
				}

			}
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}

	}

}
