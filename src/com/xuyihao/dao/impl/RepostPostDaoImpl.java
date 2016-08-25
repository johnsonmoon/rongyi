package com.xuyihao.dao.impl;

import com.xuyihao.common.ConnDB;
import com.xuyihao.dao.RepostPostDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xuyh at 16-8-13 下午3:23
 */
public class RepostPostDaoImpl implements RepostPostDao {
	private ConnDB connDB = new ConnDB();

	public void setConnDB(ConnDB connDB) {
		this.connDB = connDB;
	}

	@Override
	public boolean saveRepostPost(String Acc_Id, String Rep_Id, String Post_Id, String Rep_addTime) {
		boolean flag = false;
		String sql = "insert into RepostPost values(null, '" + Acc_Id + "', '" + Rep_Id + "', '" + Post_Id + "', '"
				+ Rep_addTime + "')";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteRepostPost(String Acc_Id, String Rep_Id, String Post_Id) {
		boolean flag = false;
		String sql = "delete from RepostPost where Acc_ID = '" + Acc_Id + "' and Rep_ID = '" + Rep_Id + "' and Post_ID = '"
				+ Post_Id + "'";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteRepostPost(String Acc_Id, String Rep_Id) {
		boolean flag = false;
		String sql = "delete from RepostPost where Acc_ID = '" + Acc_Id + "' and Rep_ID = '" + Rep_Id + "'";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteRepostPostAll(String Acc_Id) {
		boolean flag = false;
		String sql = "delete from RepostPost where Acc_ID = '" + Acc_Id + "'";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteBySql(String sql) {
		boolean flag = false;
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public List<String> queryByAccountId(String Acc_Id) {
		String sql = "select * from RepostPost where Acc_ID = '" + Acc_Id + "'";
		ResultSet resultSet = this.connDB.executeQuery(sql);
		List<String> postIdList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				postIdList.add(resultSet.getString("Post_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.connDB.close();
		return postIdList;
	}

	@Override
	public List<String> query(String Acc_Id, String Rep_Id) {
		String sql = "select * from RepostPost where Acc_ID = '" + Acc_Id + "' and Rep_ID = '" + Rep_Id + "'";
		ResultSet resultSet = this.connDB.executeQuery(sql);
		List<String> postIdList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				postIdList.add(resultSet.getString("Post_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.connDB.close();
		return postIdList;
	}

	@Override
	public ResultSet queryBySql(String sql) {
		ResultSet resultSet = this.connDB.executeQuery(sql);
		return resultSet;
	}

	@Override
	public void closeDBConnection() {
		this.connDB.close();
	}
}
