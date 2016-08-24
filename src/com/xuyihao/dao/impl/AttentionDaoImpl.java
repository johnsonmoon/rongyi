package com.xuyihao.dao.impl;

import com.xuyihao.dao.AttentionDao;
import com.xuyihao.tools.ConnDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xuyh at 16-8-13 下午3:38
 */
public class AttentionDaoImpl implements AttentionDao {
	private ConnDB connDB = new ConnDB();

	public void setConnDB(ConnDB connDB) {
		this.connDB = connDB;
	}

	@Override
	public boolean saveAttention(String Acc_Id, String Att_Id, String Att_addTime) {
		boolean flag = false;
		String sql = "insert into Attention values(null, '" + Acc_Id + "', '" + Att_Id + "', '" + Att_addTime + "')";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteAttention(String Acc_Id, String Att_Id) {
		boolean flag = false;
		String sql = "delete from Attention where Acc_ID = '" + Acc_Id + "' and Att_ID = '" + Att_Id + "'";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteAttentionAll(String Acc_Id) {
		boolean flag = false;
		String sql = "delete from Attention where Acc_ID = '" + Acc_Id + "'";
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public boolean deleteAttentionBySql(String sql) {
		boolean flag = false;
		if (this.connDB.executeUpdate(sql) != 0) {
			flag = true;
		}
		this.connDB.close();
		return flag;
	}

	@Override
	public List<String> queryByAtn(String Acc_Id) {
		String sql = "select * from Attention where Acc_ID = '" + Acc_Id + "'";
		ResultSet resultSet = this.connDB.executeQuery(sql);
		List<String> attenedAccountIdList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				attenedAccountIdList.add(resultSet.getString("Att_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connDB.close();
		}
		return attenedAccountIdList;
	}

	@Override
	public List<String> queryByAtned(String Att_Id) {
		String sql = "select * from Attention where Att_ID = '" + Att_Id + "'";
		ResultSet resultSet = this.connDB.executeQuery(sql);
		List<String> attenAccountIdList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				attenAccountIdList.add(resultSet.getString("Att_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.connDB.close();
		}
		return attenAccountIdList;
	}

	@Override
	public void closeDBConnection() {
		this.connDB.close();
	}
}