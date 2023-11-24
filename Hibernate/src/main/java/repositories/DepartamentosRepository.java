package repositories;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import db.HibernateManager;
import exceptions.ExceptionDepartamento;
import io.IO;
import jakarta.persistence.TypedQuery;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Departamento;
import model.Empleado;

@NoArgsConstructor
@Data
public class DepartamentosRepository {

	private final Logger logger = Logger.getLogger(DepartamentosRepository.class.getName());

	public List<Departamento> findAll() {
		logger.info("findAll()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
		List<Departamento> list = query.getResultList();
		hb.close();
		return list;
	}

	public boolean save(Departamento entity) throws ExceptionDepartamento {
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
			throw new ExceptionDepartamento(
					"Error al guardar departamento con id: " + entity.getId() + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	public Departamento findById(Integer id) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		Departamento departamento = (hb.getManager().find(Departamento.class, id));
		hb.close();
		return departamento;
	}

	public List<Departamento> findByName(String name) {
		logger.info("findbyName()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findByNombre",
				Departamento.class);
		query.setParameter("nombre", name + "%");
		List<Departamento> departamentos = query.getResultList();
		hb.close();
		return departamentos;
	}

	public boolean delete(Integer id) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			Departamento d = hb.getManager().find(Departamento.class, id);

			if (d.getEmpleados().size() != 0) {
				Set<Empleado> emps = d.getEmpleados();
				for (Empleado empleado : emps) {
					empleado.setDepartamento(null);

				}
			}
			hb.getManager().remove(d);

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

	public boolean update(Integer id, String nombre, Integer idJefe) {
		logger.info("update()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();

			Departamento departamento = hb.getManager().find(Departamento.class, id);

			if (departamento != null) {
				if (!nombre.isBlank()) {
					departamento.setNombre(nombre);
				}

				Empleado e = hb.getManager().find(Empleado.class, idJefe);
				if (e != null && (e.getDepartamento().getId() == departamento.getId())) {
					departamento.setJefe(e);
				}

			}

			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			throw new ExceptionDepartamento(
					"Error al hacer update al departamento con id: " + id + "\n" + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}

	}

}
