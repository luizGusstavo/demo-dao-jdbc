package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	void insert(Seller obj); //funcao responsavel por inserir um department
	void update(Seller obj); //funcao responsavel por atualizar o department
	void deletById(Integer id); //funcao responsavel por deletar um department
	Seller findById(Integer id); //funcao responsavel por retornar caso um department exista
	List<Seller> findAll(); //funcao responsavel por retornar uma lista de departments
	List<Seller> findByDeparment(Department department);
}
	