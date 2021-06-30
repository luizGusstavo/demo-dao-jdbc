package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	void insert(Department obj); //funcao responsavel por inserir um department
	void update(Department obj); //funcao responsavel por atualizar o department
	void deletById(Integer id); //funcao responsavel por deletar um department
	Department findById(Integer id); //funcao responsavel por retornar caso um department exista
	List<Department> findAll(); //funcao responsavel por retornar uma lista de departments
}
