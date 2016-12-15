package bulleteinboard.dao;

import static bulleteinboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulleteinboard.bean.Message;
import bulleteinboard.exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append(" user_id");
			sql.append(", title");
			sql.append(", text");
			sql.append(", category");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, message.getUserId());
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getText());
			ps.setString(4, message.getCategory());

			System.out.println(ps);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Message> getCategories(Connection connection) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM messages GROUP BY category");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Message> ret = toMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private static List<Message> toMessageList(ResultSet rs)throws SQLException {
		List<Message> ret = new ArrayList<Message>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				Message message = new Message();
				message.setId(id);
				message.setId(userId);
				message.setTitle(title);
				message.setText(text);
				message.setCategory(category);
				message.setInsertDate(insertDate);
				message.setUpdateDate(updateDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public Message getOldDate(Connection connection) {

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_message ORDER BY insert_date ASC limit 1");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Message> ret = toMessageList(rs);
			return ret.get(0);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
