package by.compit.tereh.service.user.controller;

import by.compit.tereh.service.user.entity.Group;
import by.compit.tereh.service.user.entity.User;
import by.compit.tereh.service.user.service.UserService;
import by.compit.tereh.service.weblogic.exception.WebLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private static final String GET_USERS_URL = "/users";
    private static final String GET_LDAP_USERS = "/ldap-users";
    private static final String CREATE_USER_URL = "/create-user";
    private static final String LOAD_LDAP_USERS_URL = "/load-ldap-users";
    private static final String UPDATE_LDAP_USER_URL = "/update-ldap-user";
    private static final String GET_USER_GROUPS_URL = "/user-roles";
    private static final String UPDATE_USER_GROUPS_MEMBERSHIP_URL = "/update-user-roles";

    private static final String USER_NAME_PARAMETER = "userName";
    private static final String USER_DESCRIPTION_PARAMETER = "userDescription";
    private static final String UPDATE_LDAP_USERS_IF_EXIST_PARAMETER = "updateIfExist";

    private static final String USERS_LIST_PARAMETER = "usersList";

    private final String USERS_JSP_PAGE = "users-list";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping(value = GET_USERS_URL, method = RequestMethod.GET)
    public String getUsersList(Model model) throws WebLogicException {

        model.addAttribute(USERS_LIST_PARAMETER, userService.getUsersList());

        return USERS_JSP_PAGE;
    }



    @RequestMapping(value = CREATE_USER_URL, method = RequestMethod.POST)
    public RedirectView createUser(HttpServletRequest request,
                                   @RequestParam(value = USER_NAME_PARAMETER) String name,
                                   @RequestParam(value = USER_DESCRIPTION_PARAMETER) String description) throws WebLogicException {

        userService.createUser(new User(name, description), "qwerty123qwerty");
        return new RedirectView(request.getContextPath() + GET_USERS_URL);
    }

    @ResponseBody
    @RequestMapping(value = LOAD_LDAP_USERS_URL, method = RequestMethod.POST)
    public List<User> loadLDAPUsers(@RequestBody List<User> ldapUsers,
                                    @RequestParam(value = UPDATE_LDAP_USERS_IF_EXIST_PARAMETER) boolean updateIfExist) throws WebLogicException {

        return userService.loadLDAPUsers(ldapUsers, updateIfExist);
    }

    @ResponseBody
    @RequestMapping(value = UPDATE_LDAP_USER_URL, method = RequestMethod.POST)
    public boolean updateLDAPUser(@RequestBody User user) throws WebLogicException {

        return userService.updateLDAPUser(user);
    }

    @RequestMapping(value = UPDATE_USER_GROUPS_MEMBERSHIP_URL, method = RequestMethod.POST)
    @ResponseBody
    public boolean updateUserGroupsMembership(@RequestParam(value = USER_NAME_PARAMETER) String username,
                                              @RequestBody ArrayList<String> groupNames) throws WebLogicException {


        return userService.updateUserGroupsMembership(username, new HashSet<>(groupNames));
    }

    @RequestMapping(value = GET_USER_GROUPS_URL, method = RequestMethod.GET)
    @ResponseBody
    public Set<Group> getUserGroups(@RequestParam(value = USER_NAME_PARAMETER) String username) throws WebLogicException {

        return userService.getUserGroups(username);
    }
}
