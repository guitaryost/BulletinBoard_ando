package bulleteinboard.service;

import static bulleteinboard.utils.CloseableUtil.*;
import static bulleteinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulleteinboard.bean.Branch;
import bulleteinboard.dao.BranchDao;

public class BranchService {
	public List<Branch> getBranches() {
		Connection connection = null;
		try {

			connection = getConnection();

			List<Branch> ret = new BranchDao().getBranches(connection);

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

