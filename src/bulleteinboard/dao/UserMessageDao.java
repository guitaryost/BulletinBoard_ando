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

import bulleteinboard.bean.UserMessage;
import bulleteinboard.exception.SQLRuntimeException;

public class UserMessageDao {
	public List<UserMessage> getUserMessages(Connection connection, int num, String category, String fromDate, String toDate){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM user_message where insert_date between ? and ? ");

			if(!StringUtils.isEmpty(category)) {
				sql.append(" and category = ?");
			}

			sql.append(" ORDER BY update_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, fromDate);
			ps.setString(2, toDate);
			if(!StringUtils.isEmpty(category)) {
				ps.setString(3, category);
			}

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs)
			throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("user_id");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				UserMessage message = new UserMessage();
				message.setId(id);
				message.setUserId(userId);
				message.setName(name);
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

}