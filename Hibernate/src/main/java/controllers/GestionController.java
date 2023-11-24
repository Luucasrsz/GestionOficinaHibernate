package controllers;

import java.util.List;
import java.util.logging.Logger;

import lombok.Builder;
import model.Departamento;
import model.Empleado;
import model.Proyecto;
import repositories.DepartamentosRepository;
import repositories.EmpleadosRepository;
import repositories.ProyectosRepository;

@Builder
public class GestionController {
	
    private final Logger logger = Logger.getLogger(GestionController.class.getName());
    
    //Repositorios
    
    private EmpleadosRepository empleadosRepository;
    private DepartamentosRepository departamentosRepository;
    private ProyectosRepository proyectosRepository;
    
    public GestionController(EmpleadosRepository empleadosRepository,DepartamentosRepository departamentosRepository,ProyectosRepository proyectosRepository) {
    	this.departamentosRepository = departamentosRepository;
    	this.empleadosRepository = empleadosRepository;
    	this.proyectosRepository = proyectosRepository;
    }
    
    //Metodos empleados
    
    public List<Empleado> getEmpleados(){
    	logger.info("Obteniendo todos los empleados");
        return empleadosRepository.findAll();
    }
    
    public boolean createEmpleado(Empleado empleado) {
        logger.info("Creando Empleado");
        return empleadosRepository.save(empleado);
    }
    
    public Empleado getEmpleadosById(Integer ID) {
    	logger.info("Obteniendo empleado con ID: "+ID);
    	return empleadosRepository.findById(ID);
    }
    
    public List<Empleado> getEmpleadosByName(String name) {
    	logger.info("Obteniendo empleado con nombre: "+name);
    	return empleadosRepository.findByName(name);
    }
    
    public boolean deleteEmpleado(Integer id) {
    	logger.info("Borrando empleado");
    	return empleadosRepository.delete(id);
    }
    
    public boolean updateEmpleado(Integer idEmp, Double salario, String nombre, Integer idDep, Integer idPro) {
    	logger.info("Actualizando empleado...");
    	return empleadosRepository.update(idEmp, salario, nombre, idDep, idPro);
    }
    

    
    
    //METODOS DEPARTAMENTO
    
    public List<Departamento> getDepartamentos(){
    	logger.info("Obteniendo todos los departamentos");
        return departamentosRepository.findAll();
    }
    
    public boolean createDepartamento(Departamento departamento) {
        logger.info("Creando departamento");
        return departamentosRepository.save(departamento);
    }
    
    public Departamento getDepartamentosById(Integer ID) {
    	logger.info("Obteniendo empleado con ID: "+ID);
    	return departamentosRepository.findById(ID);
    }
    
    public List<Departamento> getDepartamentoByName(String name) {
    	logger.info("Obteniendo empleado con nombre: "+name);
    	return departamentosRepository.findByName(name);
    }
    
    public boolean deleteDepartamento(Integer id) {
    	logger.info("Borrando departamento");
    	return departamentosRepository.delete(id);
    }
    
    public boolean updateDepartamento(Integer id, String nombre, Integer idJefe) {
    	logger.info("Actualizando departamento...");
    	return departamentosRepository.update(id, nombre, idJefe);
    }
    
    
    //METODOS PROYECTOS
    
    public List<Proyecto> getProyectos(){
    	logger.info("Obteniendo todos los departamentos");
        return proyectosRepository.findAll();
    }
    
    public boolean createProyecto(Proyecto proyecto) {
        logger.info("Creando departamento");
        return proyectosRepository.save(proyecto);
    }
    
    public Proyecto getProyectoById(Integer ID) {
    	logger.info("Obteniendo proyecto con ID: "+ID);
    	return proyectosRepository.findById(ID);
    }
    
    public List<Proyecto> getProyectosByName(String name) {
    	logger.info("Obteniendo proyecto con nombre: "+name);
    	return proyectosRepository.findByName(name);
    }
    
    public boolean deleteProyecto(Integer id) {
    	logger.info("Borrando proyecto");
    	return proyectosRepository.delete(id);
    }
    
    public boolean updateProyecto(Integer id, String nombre, Integer idEmp) {
    	logger.info("Actualizando proyecto...");
    	return proyectosRepository.update(id, nombre, idEmp);
    }
    



}
