package bulleteinboard.service;

import static bulleteinboard.utils.CloseableUtil.*;
import static bulleteinboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulleteinboard.bean.Message;
import bulleteinboard.bean.UserMessage;
import bulleteinboard.dao.MessageDao;
import bulleteinboard.dao.UserMessageDao;

public class MessageService {
	public void register (Message message){
		Connection connection = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

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

	public List<UserMessage> getMessage(String category, String fromDate, String toDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();

			List<UserMessage> ret = userMessageDao.getUserMessages(connection, LIMIT_NUM, category, fromDate, toDate);

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

	public List<Message> getCategories() {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			List<Message> ret = messageDao.getCategories(connection);

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
