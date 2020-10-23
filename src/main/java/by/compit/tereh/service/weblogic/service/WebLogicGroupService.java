package by.compit.tereh.service.weblogic.service;

import by.compit.tereh.service.user.entity.Group;
import by.compit.tereh.service.weblogic.connection.ConnectionSQLAuthMethod;
import by.compit.tereh.service.weblogic.exception.WebLogicException;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebLogicGroupService {

    private static final String GROUPS_LIST_METHOD = "listGroups";
    private static final String GET_GROUP_DESCRIPTION_METHOD = "getGroupDescription";
    private static final String IS_GROUP_EXIST_METHOD = "groupExists";
    private static final String CREATE_GROUP_METHOD = "createGroup";
    private static final String REMOVE_GROUP_METHOD = "removeGroup";
    private static final String UPDATE_GROUP_DESCRIPTION_METHOD = "setGroupDescription";

    private static final String ADD_USER_TO_GROUP_METHOD = "addMemberToGroup";
    private static final String REMOVE_USER_FROM_GROUP_METHOD = "removeMemberFromGroup";

    private static final String GET_CURRENT_NAME_CURSOR_METHOD = "getCurrentName";
    private static final String HAVE_CURRENT_CURSOR_METHOD = "haveCurrent";
    private static final String ADVANCE_CURSOR_METHOD = "advance";

    private static final String STRING_CLASS = "java.lang.String";
    private static final String INTEGER_CLASS = "java.lang.Integer";

    public WebLogicGroupService() {}

    public boolean addUserToGroup(String username, String groupName) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();

            boolean isGroupExist = isGroupExist(connection, userEditor, groupName);
            boolean isUserExist = WebLogicUserService.isUserExist(connection, userEditor, username);

            if (isGroupExist && isUserExist) {
                connection.invoke(userEditor,
                        ADD_USER_TO_GROUP_METHOD,
                        new Object[]{groupName, username},
                        new String[]{STRING_CLASS, STRING_CLASS}
                );
                return true;
            } else {
                return false;
            }
        } catch (IOException | ReflectionException | WebLogicException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public boolean removeUserFromGroup(String username, String groupName) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();

            boolean isGroupExist = isGroupExist(connection, userEditor, groupName);
            boolean isUserExist = WebLogicUserService.isUserExist(connection, userEditor, username);

            if (isGroupExist && isUserExist) {
                connection.invoke(userEditor,
                        REMOVE_USER_FROM_GROUP_METHOD,
                        new Object[]{groupName, username},
                        new String[]{STRING_CLASS, STRING_CLASS}
                );
                return true;
            } else {
                return false;
            }
        } catch (IOException | ReflectionException | WebLogicException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public boolean createGroup(Group group) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();

            boolean isGroupExist = isGroupExist(connection, userEditor, group.getAuthority());

            if (!isGroupExist) {
                connection.invoke(userEditor,
                        CREATE_GROUP_METHOD,
                        new Object[]{group.getAuthority(), group.getDescription()},
                        new String[]{STRING_CLASS, STRING_CLASS}
                );
                return true;
            } else {
                return false;
            }
        } catch (IOException | ReflectionException | WebLogicException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    private boolean isGroupExist(MBeanServerConnection connection, ObjectName userEditor, String name) throws ReflectionException, MBeanException, InstanceNotFoundException, IOException {

        return (boolean) connection.invoke(
                userEditor, IS_GROUP_EXIST_METHOD,
                new Object[]{name},
                new String[]{STRING_CLASS}
        );

    }

    public boolean removeGroup(String name) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();
            connection.invoke(
                    userEditor, REMOVE_GROUP_METHOD,
                    new Object[]{name},
                    new String[]{STRING_CLASS}
            );
            return true;
        } catch (IOException | ReflectionException | WebLogicException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public boolean updateGroupDescription(String name, String description) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();
            boolean isGroupExist = isGroupExist(connection, userEditor, name);

            if (isGroupExist) {
                connection.invoke(userEditor,
                        UPDATE_GROUP_DESCRIPTION_METHOD,
                        new Object[]{name, description},
                        new String[]{STRING_CLASS, STRING_CLASS});

                return true;
            } else {
                return false;
            }
        } catch (IOException | ReflectionException | InstanceNotFoundException | WebLogicException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public Set<Group> getGroups() throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            Set<Group> groups = new HashSet<>();
            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();

            String cursor = (String) connection.invoke(userEditor, GROUPS_LIST_METHOD,
                    new Object[]{"*", 9999}, new String[]{STRING_CLASS, INTEGER_CLASS});

            boolean haveCurrent = (Boolean) connection.invoke(userEditor,
                    HAVE_CURRENT_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});

            while (haveCurrent) {
                String currentName = (String) connection.invoke(userEditor,
                        GET_CURRENT_NAME_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});

                String currentDescription = (String) connection.invoke(userEditor,
                        GET_GROUP_DESCRIPTION_METHOD, new Object[]{currentName}, new String[]{STRING_CLASS});

                groups.add(new Group(currentName, currentDescription));

                connection.invoke(userEditor, ADVANCE_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});
                haveCurrent = (Boolean) connection.invoke(userEditor, HAVE_CURRENT_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});
            }
            return groups;
        } catch (ReflectionException | IOException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }
}
