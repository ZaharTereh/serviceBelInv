package by.compit.tereh.service.user.service.impl;

import by.compit.tereh.service.user.entity.Group;
import by.compit.tereh.service.user.entity.User;
import by.compit.tereh.service.user.service.UserService;
import by.compit.tereh.service.weblogic.exception.WebLogicException;
import by.compit.tereh.service.weblogic.service.WebLogicGroupService;
import by.compit.tereh.service.weblogic.service.WebLogicUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final WebLogicUserService webLogicUserService;

    private final WebLogicGroupService webLogicGroupService;

    @Autowired
    public UserServiceImpl(WebLogicUserService webLogicUserService, WebLogicGroupService webLogicGroupService, WebLogicGroupService webLogicGroupService1) {
        this.webLogicUserService = webLogicUserService;
        this.webLogicGroupService = webLogicGroupService1;
    }

    public List<User> getUsersList() throws WebLogicException {
        return webLogicUserService.getUsersList();
    }


    @Override
    public boolean createUser(User user, String password) throws WebLogicException {
        return webLogicUserService.createUser(user, password);
    }

    @Override
    public boolean updateLDAPUser(User ldapUser) throws WebLogicException {
        webLogicUserService.updateUserDescription(ldapUser.getName(), ldapUser.getDescription());
        return true;
    }

    @Override
    public List<User> loadLDAPUsers(List<User> ldapUsers, boolean updateIfExist) throws WebLogicException {
        if (updateIfExist) {
            for (User ldapUser : ldapUsers) {
                if (!webLogicUserService.createUser(ldapUser, "qwerty123qwerty")) {
                    //TODO
                    webLogicUserService.updateUserDescription(ldapUser.getName(), ldapUser.getDescription());
                }
            }
            return new ArrayList<>();
        } else {
            List<User> existingUsers = new ArrayList<>();
            for (User ldapUser : ldapUsers) {
                if (!webLogicUserService.createUser(ldapUser, "qwerty123qwerty")) {
                    existingUsers.add(ldapUser);
                }
            }
            return existingUsers;
        }
    }

    @Override
    public Set<Group> getUserGroups(String username) throws WebLogicException {
        return webLogicUserService.getUserGroups(username);
    }

    @Override
    public boolean updateUserGroupsMembership(String username, Set<String> groupNames) throws WebLogicException {
        Set<Group> userGroups = webLogicUserService.getUserGroups(username);
        Set<String> groupsAddUser = groupNames.stream().filter(name -> {
                    for (Group group : userGroups) {
                        if (group.getAuthority().equals(name)) {
                            return false;
                        }
                    }
                    return true;
                }
        ).collect(Collectors.toSet());

        Set<Group> groupsRemoveUser = userGroups.stream().filter(group -> {
                    for (String groupName : groupNames) {
                        if (group.getAuthority().equals(groupName)) {
                            return false;
                        }
                    }
                    return true;
                }
        ).collect(Collectors.toSet());

        for (String groupName : groupsAddUser) {
            webLogicGroupService.addUserToGroup(username, groupName);
        }

        for (Group group : groupsRemoveUser) {
            webLogicGroupService.removeUserFromGroup(username, group.getAuthority());
        }

        return true;
    }

}
