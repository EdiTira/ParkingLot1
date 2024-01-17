package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.UserGroupDto;
import com.parking.parkinglot.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserGroupBean {
    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    public Collection<String> findUserGroupsByUsername(String username){
        try {
            LOG.info("findUserGroupsByUsername");
            TypedQuery<UserGroup> query = entityManager.createQuery("SELECT ug FROM UserGroup ug WHERE ug.username = :username", UserGroup.class);
            query.setParameter("username", username);
            List<UserGroup> userGroups = query.getResultList();
            List<UserGroupDto> userGroupsDto = copyUserGroupsToDto(userGroups);

            return userGroupsDto.stream().map(UserGroupDto::getUserGroup).toList();
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }

    public List<UserGroupDto> copyUserGroupsToDto(List<UserGroup> userGroups){
        List<UserGroupDto> userGroupsDto = new ArrayList<>();
        userGroups.forEach(userGroup -> userGroupsDto.add(new UserGroupDto(userGroup.getId(), userGroup.getUsername(), userGroup.getUserGroup())));
        return userGroupsDto;
    }
}
