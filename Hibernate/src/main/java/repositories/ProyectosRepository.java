package repositories;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.ExceptionEmpleado;
import exceptions.ExceptionProyecto;

import jakarta.persistence.TypedQuery;
import model.Empleado;
import model.Proyecto;

public class ProyectosRepository {

	private final Logger logger = Logger.getLogger(ProyectosRepository.class.getName());

	public List<Proyecto> findAll() {
		logger.info("findAll()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Proyecto> query = hb.getManager().createNamedQuery("Proyecto.findAll", Proyecto.class);
		List<Proyecto> list = query.getResultList();
		hb.close();
		return list;
	}

	public boolean save(Proyecto entity) throws ExceptionEmpleado {
		logger.info("guardando...()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.getTransaction().begin();
		try {
			hb.getManager().persist(entity);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			throw new ExceptionProyecto("Error al guardar proyecto con id: " + entity.getId() + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	public Proyecto findById(Integer id) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		Proyecto proyecto = (hb.getManager().find(Proyecto.class, id));
		hb.close();
		return proyecto;
	}

	public List<Proyecto> findByName(String name) {
		logger.info("findbyName()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Proyecto> query = hb.getManager().createNamedQuery("Proyecto.findByNombre", Proyecto.class);
		query.setParameter("nombre", name + "%");
		List<Proyecto> proyectos = query.getResultList();
		hb.close();
		return proyectos;
	}

	public boolean delete(Integer id) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			Proyecto p = hb.getManager().find(Proyecto.class, id);

			if (p.getEmpleados().size() != 0) {
				Set<Empleado> emps = p.getEmpleados();
				for (Empleado empleado : emps) {
					empleado.getProyectos().remove(p);
				}
			}
			hb.getManager().remove(p);
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

	public boolean update(Integer id, String nombre, Integer idEmp) {
		logger.info("update()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();

			Proyecto proyecto = hb.getManager().find(Proyecto.class, id);

			if (proyecto != null) {

				if (!nombre.isBlank()) {
					proyecto.setNombre(nombre);
				}

				if (idEmp != null) {
					Empleado e = hb.getManager().find(Empleado.class, idEmp);
					if (e != null) {
						Set<Empleado> empleados = proyecto.getEmpleados();
						empleados.add(e);
						proyecto.setEmpleados(empleados);

						Set<Proyecto> proyectos = e.getProyectos();
						proyectos.add(proyecto);
						e.setProyectos(proyectos);
					}
				}

				hb.getTransaction().commit();
				hb.close();
				return true;
			}
		} catch (Exception e) {

			throw new ExceptionProyecto("Error al hacer update al proyecto con id: " + id + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
		return false;

	}
}
