package by.compit.tereh.service.user.service;



import by.compit.tereh.service.user.entity.Group;
import by.compit.tereh.service.user.entity.User;
import by.compit.tereh.service.weblogic.exception.WebLogicException;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getUsersList() throws WebLogicException;

    //List<User> getLDAPUsers() throws LDAPException;

    boolean createUser(User user, String password) throws WebLogicException;

    boolean updateLDAPUser(User ldapUser) throws WebLogicException;

    List<User> loadLDAPUsers(List<User> ldapUsers, boolean updateIfExist) throws WebLogicException;

    Set<Group> getUserGroups(String username) throws WebLogicException;

    boolean updateUserGroupsMembership(String username, Set<String> groupNames) throws WebLogicException;
}
