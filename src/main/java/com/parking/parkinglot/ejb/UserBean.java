package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.UserDto;;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UserBean {
    private static final Logger LOG = Logger.getLogger(UserBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;


    public List<UserDto> copyUsersToDto(List<User> users){
        List<UserDto> usersDto = new ArrayList<>();
        for(User user : users){
            Long id = user.getId();
            String username = user.getUsername();
            String email = user.getEmail();
            UserDto userDto = new UserDto(id, username, email);
            usersDto.add(userDto);
        }
        return usersDto;
    }

    public List<UserDto> findAllCars(){
        try {
            LOG.info("findAllUsers");
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        } catch (Exception ex){
            throw new EJBException(ex);
        }
    }
}
