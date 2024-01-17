package com.parking.parkinglot.servlets.users;

import com.parking.parkinglot.common.EditUserDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.UserGroupBean;
import com.parking.parkinglot.ejb.UsersBean;
import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@DeclareRoles({"WRITE_USERS"})
@ServletSecurity(value=@HttpConstraint(rolesAllowed = {"WRITE_USERS"}))
@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {
    @Inject
    UsersBean usersBean;

    @Inject
    UserGroupBean userGroupBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("id"));
        EditUserDto user = usersBean.findUserById(userId);
        request.setAttribute("user", user);

        Collection<String> currentUserGroups = userGroupBean.findUserGroupsByUsername(user.getUsername());
        request.setAttribute("currentUserGroups", currentUserGroups);

        String[] allUserGroups = new String[] {"READ_CARS", "WRITE_CARS", "READ_USERS",
                "WRITE_USERS", "INVOICING"};

        String[] availableUserGroups = Arrays.stream(allUserGroups).filter(userGroup -> !currentUserGroups.contains(userGroup)).toArray(String[]::new);
        request.setAttribute("availableUserGroups", availableUserGroups);

        request.getRequestDispatcher("/WEB-INF/pages/users/editUser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String[] userGroups = request.getParameterValues("user_groups");
        if (userGroups == null) {
            userGroups = new String[0];
        }
        Long userId = Long.parseLong(request.getParameter("user_id"));
        usersBean.updateUser(userId, username, email, password, List.of(userGroups));

        response.sendRedirect(request.getContextPath() + "/Users");
    }
}
