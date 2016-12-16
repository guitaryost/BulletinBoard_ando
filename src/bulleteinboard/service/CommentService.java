package bulleteinboard.service;

import static bulleteinboard.utils.CloseableUtil.*;
import static bulleteinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulleteinboard.bean.Comment;
import bulleteinboard.bean.UserComment;
import bulleteinboard.dao.CommentDao;
import bulleteinboard.dao.UserCommentDao;


public class CommentService {
	public void register (Comment comment){
		Connection connection = null;
		try{
			connection = getConnection();
			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);

		}
	}
	private static final int LIMIT_NUM = 1000;

	public List<UserComment> getComments() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserCommentDao commentDao = new UserCommentDao();
			List<UserComment> ret = commentDao.getUserComments(connection, LIMIT_NUM);

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