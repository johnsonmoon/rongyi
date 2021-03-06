package xuyihao.filerelate.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import xuyihao.common.DatabaseConnector;
import xuyihao.filerelate.dao.VedioPathDao;
import xuyihao.filerelate.entity.VedioPath;

/**
 * 
 * @author Xuyh at 2016年8月30日 上午11:18:44.
 *
 */
@Component("VedioPathDao")
public class VedioPathDaoImpl implements VedioPathDao {
	private DatabaseConnector databaseConnector = new DatabaseConnector();

	public boolean saveVedioPath(VedioPath vedioPath) {
		String sql = "insert into " + VedioPath.BASE_TABLE_NAME + " values(null, '" + vedioPath.getVedio_ID() + "', '"
				+ vedioPath.getVedio_pathName() + "', '" + vedioPath.getFirstPhoto_ID() + "', '" + vedioPath.getVedio_addTime()
				+ "')";
		if (this.databaseConnector.executeUpdate(sql) != 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteVedioPath(String Vedio_ID) {
		String sql = "delete from " + VedioPath.BASE_TABLE_NAME + " where " + VedioPath.BASE_VEDIOPATH_ID + " = '"
				+ Vedio_ID + "'";
		if (this.databaseConnector.executeUpdate(sql) != 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateVedioPath(VedioPath vedioPath) {
		String sql = "update " + VedioPath.BASE_TABLE_NAME + " set " + VedioPath.BASE_VEDIOPATH_PATHNAME + " = '"
				+ vedioPath.getVedio_pathName() + "', " + VedioPath.BASE_VEDIOPATH_FIRSTPHOTO_ID + " = '"
				+ vedioPath.getFirstPhoto_ID() + "' where " + VedioPath.BASE_VEDIOPATH_ID + " = '" + vedioPath.getVedio_ID()
				+ "'";
		if (this.databaseConnector.executeUpdate(sql) != 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateVedioPath(String update) {
		if (this.databaseConnector.executeUpdate(update) != 0) {
			return true;
		} else {
			return false;
		}
	}

	public VedioPath queryById(String Vedio_ID) {
		String sql = "select * from " + VedioPath.BASE_TABLE_NAME + " where " + VedioPath.BASE_VEDIOPATH_ID + " = '"
				+ Vedio_ID + "'";
		ResultSet rs = this.databaseConnector.executeQuery(sql);
		return this.getVedioPathByResultSet(rs);
	}

	public VedioPath QueryBySql(String query) {
		ResultSet rs = this.databaseConnector.executeQuery(query);
		return this.getVedioPathByResultSet(rs);
	}

	private VedioPath getVedioPathByResultSet(ResultSet resultSet) {
		VedioPath vedioPath = new VedioPath();
		try {
			if (resultSet.next()) {
				vedioPath.set_id(resultSet.getLong(VedioPath.BASE_VEDIOPATH_PHYSICAL_ID));
				vedioPath.setVedio_ID(resultSet.getString(VedioPath.BASE_VEDIOPATH_ID));
				vedioPath.setVedio_pathName(resultSet.getString(VedioPath.BASE_VEDIOPATH_PATHNAME));
				vedioPath.setFirstPhoto_ID(resultSet.getString(VedioPath.BASE_VEDIOPATH_FIRSTPHOTO_ID));
				vedioPath.setVedio_addTime(resultSet.getString(VedioPath.BASE_VEDIOPATH_ADD_TIME));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vedioPath;
	}

	public void closeDBConnection() {
		this.databaseConnector.close();
	}
}