package bulleteinboard.dao;

import static bulleteinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulleteinboard.bean.User;
import bulleteinboard.exception.NoRowsUpdatedRuntimeException;
import bulleteinboard.exception.SQLRuntimeException;


public class UserDao {
	public User getUser(Connection connection, String loginId, String password){
		PreparedStatement ps = null;
		try{
			//仮のSQL文を作成。？はパラメータの仮の値
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ? AND account = 1";
			//仮のSQL文をConnectionクラスのprepareStatementメソッドに与えて、PreparedStatementオブジェクトの取得
				ps = connection.prepareStatement(sql);
			//？を入れたパラメータに実際の値を入れ込む
				ps.setString(1, loginId);
				ps.setString(2, password);

				ResultSet rs = ps.executeQuery();
				List<User> userList = toUserList(rs);
				if (userList.isEmpty()) {
					return null;
				}
				if (2 <= userList.size()) {
					throw new IllegalStateException("2 <= userList.size()");
				}
				return userList.get(0);
			} catch (SQLException e) {
				throw new SQLRuntimeException(e);
			} finally {
				close(ps);
		}
	}

	public List<User> getUsers(Connection connection){
		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users";
				ps = connection.prepareStatement(sql);

				ResultSet rs = ps.executeQuery();
				List<User> userList = toUserList(rs);
				return userList;
			} catch (SQLException e) {
				throw new SQLRuntimeException(e);
			} finally {
				close(ps);
		}
	}


	private List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				boolean account = rs.getBoolean("account");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setAccount(account);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}



	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append(" login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", department_id");
			sql.append(", account");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", true");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getDepartmentId());

			//sql文を表示させたいとき
			//System.out.println(ps.toString());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getUser(Connection connection, int id){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	public void stop(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET account = ? WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());
			ps.setBoolean(1, user.isAccount());
			ps.setInt(2, user.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void update(Connection connection, User editUser) {

		PreparedStatement ps = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", department_id = ?");
			sql.append(", account = ?");
			//空だったらパスワードをセット
			if(!StringUtils.isEmpty(editUser.getPassword())){
				sql.append(", password = ?");
			}
			sql.append(", insert_date = CURRENT_TIMESTAMP");
			sql.append(", update_date = CURRENT_TIMESTAMP");

			sql.append(" WHERE");
			sql.append(" id = ?");
			sql.append(" AND");
			sql.append(" update_date = ?");


			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, editUser.getLoginId());
			ps.setString(2, editUser.getName());
			ps.setInt(3, editUser.getBranchId());
			ps.setInt(4, editUser.getDepartmentId());
			ps.setBoolean(5, editUser.isAccount());
			//空じゃなかったら、パスワードを取得
			if(!StringUtils.isEmpty(editUser.getPassword())){
				 ps.setString(6, editUser.getPassword());
				 ps.setInt(7, editUser.getId());
				ps.setTimestamp(8, new Timestamp(editUser.getUpdateDate().getTime()));
			}else{//空だったらセットの順番が変える
				ps.setInt(6, editUser.getId());
				ps.setTimestamp(7, new Timestamp(editUser.getUpdateDate().getTime()));
			}


			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public User getUser(Connection connection, String loginId){

		PreparedStatement ps = null;
		try{
			String sql = "SELECT * FROM users WHERE login_id = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
