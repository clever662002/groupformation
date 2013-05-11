package domain.model.group.mappers;

import java.sql.SQLException;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.mapper.GenericOutputMapper;
import org.dsrg.soenea.domain.mapper.LostUpdateException;

import services.group.GroupTDG;

import domain.model.group.Group;

public class GroupOutputMapper extends GenericOutputMapper<Long, Group> {

	@Override
	public void delete(Group group) throws MapperException {
		try {
			int count = GroupTDG.delete(group.getId(), group.getVersion());
			if (count == 0)
				throw new LostUpdateException("GroupTDG: id " + group.getId()
						+ " version " + group.getVersion());
			group.setVersion(group.getVersion() + 1);
		} catch (SQLException e) {
			throw new MapperException(
					"Could not delete Group " + group.getId(), e);
		}
	}

	@Override
	public void insert(Group group) throws MapperException {
		try {
			GroupTDG.insert(group.getId(), group.getVersion(), group.getName());
		} catch (SQLException e) {
			throw new MapperException(
					"Could not insert Group " + group.getId(), e);
		}
	}

	@Override
	public void update(Group group) throws MapperException {
		try {
			int count = GroupTDG.update(group.getId(), group.getVersion(),
					group.getName());
			if (count == 0)
				throw new LostUpdateException("GroupTDG: id " + group.getId()
						+ " version " + group.getVersion());
			group.setVersion(group.getVersion() + 1);
		} catch (SQLException e) {
			throw new MapperException("Could not update Sponsor "
					+ group.getId(), e);
		}
	}

	@Override
	public void cascadeInsert(Group g) throws MapperException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cascadeUpdate(Group g) throws MapperException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cascadeDelete(Group g) throws MapperException {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateInsert(Group g) throws MapperException {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateUpdate(Group g) throws MapperException {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateDelete(Group g) throws MapperException {
		// TODO Auto-generated method stub

	}

}
