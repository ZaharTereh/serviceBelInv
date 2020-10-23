package by.compit.tereh.service.weblogic.connection;

import java.util.ResourceBundle;

class WebLogicPropertiesReader {

    private static final String RESOURCE_BUNDLE_FILE = "weblogic";

    private static final WebLogicPropertiesReader instance = new WebLogicPropertiesReader();

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE);

    private WebLogicPropertiesReader() {}

    static WebLogicPropertiesReader getInstance() {
        return instance;
    }

    String getKey(String key) {
        return resourceBundle.getString(key);
    }
}
