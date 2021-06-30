package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery(); //executa sql, e o resultado fica armazenado em rs
				if(rs.next()) {
					Department dep = new Department();
					dep.setId(rs.getInt("DepartmentId"));//pega o id do department
					dep.setName(rs.getString("DepName"));//pega o nome do department
					
					Seller obj = new Seller();
					obj.setId(rs.getInt("Id"));//pega o que contem no campo Id no BD
					obj.setName(rs.getString("Name"));//pega o nome que contem no campo name no BD
					obj.setEmail(rs.getString("Email"));//pega o email que contem no campo email no BD
					obj.setBaseSalary(rs.getDouble("BaseSalary"));//pega o salario que contem no campo BaseSalary no BD
					obj.setBirthDate(rs.getDate("BirthDate"));//pega a data de aniversatio que contem no BD
					obj.setDepartment(dep);//pega o Departamento do vendedor(No caso o que encontramos acima)
					
					return obj;
				}
				else {
					return null; //caso n encontre ngm com o Id fornecido
				}
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
