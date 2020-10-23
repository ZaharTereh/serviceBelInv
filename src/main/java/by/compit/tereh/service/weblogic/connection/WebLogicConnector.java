package by.compit.tereh.service.weblogic.connection;

import by.compit.tereh.service.weblogic.exception.WebLogicException;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import java.io.Closeable;
import java.io.IOException;
import java.util.Hashtable;

class WebLogicConnector implements Closeable, AutoCloseable {

    private static final String DOMAIN_RUNTIME_SERVICE_MBEAN_CLASS = "com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean";
    private static final String MBEAN_TYPE_SERVICE_CLASS = "com.bea:Name=MBeanTypeService,Type=weblogic.management.mbeanservers.MBeanTypeService";

    private static final String DOMAIN_CONFIGURATION_ATTRIBUTE = "DomainConfiguration";
    private static final String SECURITY_CONFIGURATION_ATTRIBUTE = "SecurityConfiguration";
    private static final String DEFAULT_REALM_ATTRIBUTE = "DefaultRealm";
    private static final String AUTHENTICATION_PROVIDERS_ATTRIBUTE = "AuthenticationProviders";

    private static final String CONNECTION_PROTOCOL = "t3";
    private static final String CONNECTION_JNDI_ROOT = "/jndi/";
    private static final String CONNECTION_MSERVER = "weblogic.management.mbeanservers.domainruntime";
    private static final String CONNECTION_PROTOCOL_PROVIDER_PACKAGES = "weblogic.management.remote";

    private static final String HOSTNAME_PROPERTIES_PARAMETER = "hostname";
    private static final String PORT__PROPERTIES_PARAMETER = "port";
    private static final String USERNAME_PROPERTIES_PARAMETER = "username";
    private static final String PASSWORD_PROPERTIES_PARAMETER = "password";

    private JMXConnector connector = null;
    private ObjectName mBeanTypeService = null;
    private ObjectName[] atnProviders = null;

    private MBeanServerConnection connection = null;
    private boolean isConnectorClosed = true;

    WebLogicConnector() throws WebLogicException {
        try {
            initConnection(WebLogicPropertiesReader.getInstance().getKey(HOSTNAME_PROPERTIES_PARAMETER),
                    Integer.valueOf(WebLogicPropertiesReader.getInstance().getKey(PORT__PROPERTIES_PARAMETER)),
                    WebLogicPropertiesReader.getInstance().getKey(USERNAME_PROPERTIES_PARAMETER),
                    WebLogicPropertiesReader.getInstance().getKey(PASSWORD_PROPERTIES_PARAMETER));
            initAuthProvider();
            isConnectorClosed = false;
        } catch (IOException | ReflectionException | InstanceNotFoundException | AttributeNotFoundException | MBeanException | MalformedObjectNameException e) {
            throw new WebLogicException(e);
        }
    }

    private void initAuthProvider() throws MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, IOException {

        ObjectName service = new ObjectName(DOMAIN_RUNTIME_SERVICE_MBEAN_CLASS);
        mBeanTypeService = new ObjectName(MBEAN_TYPE_SERVICE_CLASS);
        ObjectName domainMBean = (ObjectName) connection.getAttribute(service, DOMAIN_CONFIGURATION_ATTRIBUTE);
        ObjectName securityConfiguration = (ObjectName) connection.getAttribute(domainMBean, SECURITY_CONFIGURATION_ATTRIBUTE);
        ObjectName defaultRealm = (ObjectName) connection.getAttribute(securityConfiguration, DEFAULT_REALM_ATTRIBUTE);
        atnProviders = (ObjectName[]) connection.getAttribute(defaultRealm, AUTHENTICATION_PROVIDERS_ATTRIBUTE);

    }

    private void initConnection(String hostname, int port, String username, String password) throws IOException {
        JMXServiceURL serviceURL = new JMXServiceURL(CONNECTION_PROTOCOL, hostname,
                port, CONNECTION_JNDI_ROOT + CONNECTION_MSERVER);
        Hashtable<String, String> h = new Hashtable<>();
        h.put(Context.SECURITY_PRINCIPAL, username);
        h.put(Context.SECURITY_CREDENTIALS, password);
        h.put(JMXConnectorFactory.PROTOCOL_PROVIDER_PACKAGES, CONNECTION_PROTOCOL_PROVIDER_PACKAGES);
        connector = JMXConnectorFactory.connect(serviceURL, h);
        connection = connector.getMBeanServerConnection();
    }

    boolean isClosed() {
        return isConnectorClosed;
    }

    @Override
    public void close() throws IOException {
        connector.close();
        isConnectorClosed = true;
    }

    ObjectName getMBeanTypeService() {
        return mBeanTypeService;
    }

    ObjectName[] getAtnProviders() {
        return atnProviders;
    }

    MBeanServerConnection getConnection() {
        return connection;
    }

}
