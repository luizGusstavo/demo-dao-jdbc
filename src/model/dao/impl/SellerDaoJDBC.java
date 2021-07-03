package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
			try {
				st = conn.prepareStatement(
						"INSERT INTO seller "
						+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
						+ "VALUES "
						+ "(?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				
				st.setString(1, obj.getName());
				st.setString(2, obj.getEmail());
				st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
				st.setDouble(4, obj.getBaseSalary());
				st.setInt(5, obj.getDepartment().getId());
				
				int rowsAffected = st.executeUpdate();
				
				if(rowsAffected > 0){
					ResultSet rs = st.getGeneratedKeys();
						if(rs.next()) {
							int id = rs.getInt(1);//pega o id gerado
							obj.setId(id);//atribui o id gerado para o objeto
						}
						DB.closeResultSet(rs);
				}
				else {
					throw new DbException("Unexpeted error! No rows affected.");
				}
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
			finally {
				DB.closeStatement(st);
			}
		
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
					Department dep = instantiateDepartment(rs);
					Seller obj = instantiateSeller(rs, dep);	
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

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));//pega o que contem no campo Id no BD
		obj.setName(rs.getString("Name"));//pega o nome que contem no campo name no BD
		obj.setEmail(rs.getString("Email"));//pega o email que contem no campo email no BD
		obj.setBaseSalary(rs.getDouble("BaseSalary"));//pega o salario que contem no campo BaseSalary no BD
		obj.setBirthDate(rs.getDate("BirthDate"));//pega a data de aniversatio que contem no BD
		obj.setDepartment(dep);//pega o Departamento do vendedor(No caso o que encontramos acima)
		return obj;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));//pega o id do department
		dep.setName(rs.getString("DepName"));//pega o nome do department
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			rs = st.executeQuery(); //executa sql, e o resultado fica armazenado em rs
			
				while (rs.next()) {
					
					Department dep = map.get(rs.getInt("DepartmentId"));
					
					if(dep == null) {
						dep = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					
					Seller obj = instantiateSeller(rs, dep);	
					list.add(obj);
				}
				return list;
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
	public List<Seller> findByDeparment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();
			
			rs = st.executeQuery(); //executa sql, e o resultado fica armazenado em rs
			
				while (rs.next()) {
					
					Department dep = map.get(rs.getInt("DepartmentId"));
					
					if(dep == null) {
						dep = instantiateDepartment(rs);
						map.put(rs.getInt("DepartmentId"), dep);
					}
					
					Seller obj = instantiateSeller(rs, dep);	
					list.add(obj);
				}
				return list;
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
