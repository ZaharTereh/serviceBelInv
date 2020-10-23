package by.compit.tereh.service.weblogic.service;

import by.compit.tereh.service.user.entity.Group;
import by.compit.tereh.service.user.entity.User;
import by.compit.tereh.service.weblogic.connection.ConnectionSQLAuthMethod;
import by.compit.tereh.service.weblogic.exception.WebLogicException;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class WebLogicUserService {

    private static final String USERS_LIST_METHOD = "listUsers";
    private static final String GET_USER_DESCRIPTION_METHOD = "getUserDescription";
    private static final String IS_USER_EXIST_METHOD = "userExists";
    private static final String CREATE_USER_METHOD = "createUser";
    private static final String UPDATE_USER_DESCRIPTION_METHOD = "setUserDescription";
    private static final String GET_USER_GROUPS_METHOD = "listMemberGroups";
    private static final String GET_GROUP_DESCRIPTION_METHOD = "getGroupDescription";

    private static final String GET_CURRENT_NAME_CURSOR_METHOD = "getCurrentName";
    private static final String HAVE_CURRENT_CURSOR_METHOD = "haveCurrent";
    private static final String ADVANCE_CURSOR_METHOD = "advance";

    private static final String STRING_CLASS = "java.lang.String";
    private static final String INTEGER_CLASS = "java.lang.Integer";

    public WebLogicUserService() {}

    public List<User> getUsersList() throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            List<User> users = new ArrayList<>();
            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();

            String cursor = (String) connection.invoke(userEditor, USERS_LIST_METHOD,
                    new Object[]{"*", 9999}, new String[]{STRING_CLASS, INTEGER_CLASS});

            boolean haveCurrent = (Boolean) connection.invoke(userEditor,
                    HAVE_CURRENT_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});



            while (haveCurrent) {
                String currentName = (String) connection.invoke(userEditor,
                        GET_CURRENT_NAME_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});

                String currentDescription = (String) connection.invoke(userEditor,
                        GET_USER_DESCRIPTION_METHOD, new Object[]{currentName}, new String[]{STRING_CLASS});

                users.add(
                        new User(currentName,
                                currentDescription,
                                getUserGroups(connection, userEditor, currentName))
                );

                connection.invoke(userEditor, ADVANCE_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});
                haveCurrent = (Boolean) connection.invoke(userEditor, HAVE_CURRENT_CURSOR_METHOD, new Object[]{cursor}, new String[]{STRING_CLASS});
            }
            return users;
        } catch (ReflectionException | IOException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public Set<Group> getUserGroups(String username) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod();) {
            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();
            return getUserGroups(connection, userEditor, username);
        } catch (ReflectionException | IOException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public boolean createUser(User user, String password) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();

            boolean isUserExist = isUserExist(connection, userEditor, user.getName());

            if (!isUserExist) {
                connection.invoke(userEditor,
                        CREATE_USER_METHOD,
                        new Object[]{user.getName(), password, user.getDescription()},
                        new String[]{STRING_CLASS, STRING_CLASS, STRING_CLASS}
                );
                return true;
            } else {
                return false;
            }
        } catch (IOException | ReflectionException | WebLogicException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    public boolean updateUserDescription(String name, String description) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();
            boolean isUserExist = isUserExist(connection, userEditor, name);

            if (isUserExist) {
                connection.invoke(userEditor,
                        UPDATE_USER_DESCRIPTION_METHOD,
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

    public boolean isUserExist(String name) throws WebLogicException {
        try (ConnectionSQLAuthMethod userMethod = new ConnectionSQLAuthMethod()) {

            ObjectName userEditor = userMethod.getUserEditor();
            MBeanServerConnection connection = userMethod.getConnection();
            return (boolean) connection.invoke(
                    userEditor, IS_USER_EXIST_METHOD,
                    new Object[]{name},
                    new String[]{STRING_CLASS}
            );
        } catch (IOException | ReflectionException | WebLogicException | InstanceNotFoundException | MBeanException e) {
            throw new WebLogicException(e);
        }
    }

    static boolean isUserExist(MBeanServerConnection connection, ObjectName userEditor, String name) throws ReflectionException, MBeanException, InstanceNotFoundException, IOException {

        return (boolean) connection.invoke(
                userEditor, IS_USER_EXIST_METHOD,
                new Object[]{name},
                new String[]{STRING_CLASS}
        );

    }

    private Set<Group> getUserGroups(MBeanServerConnection connection, ObjectName userEditor, String username) throws ReflectionException, MBeanException, InstanceNotFoundException, IOException {

        Set<Group> groups = new HashSet<>();

        String cursor = (String) connection.invoke(userEditor, GET_USER_GROUPS_METHOD,
                new Object[]{username}, new String[]{STRING_CLASS});

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
    }
}
