package bulleteinboard.service;

import static bulleteinboard.utils.CloseableUtil.*;
import static bulleteinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulleteinboard.bean.Department;
import bulleteinboard.dao.DepartmentDao;

public class DepartmentService {
	public List<Department> getDepatments() {
		Connection connection = null;
		try {

			connection = getConnection();

			List<Department> ret = new DepartmentDao().getDepartments(connection);

			commit(connection);

			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}

