import com.sun.deploy.association.utility.WinRegistryWrapper;

public class SearchLauncherConfig {
    private static String searchLicenseServerURL;
    private static String searchStartCommand;
    private static int licenseQueryDelay; // in seconds
    private static boolean storeConfigInRegister;

    static {
        if (WinRegistryWrapper.WinRegSubKeyExist(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY)) {
            searchLicenseServerURL = WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY, Main.SEARCH_LICENSE_SERVER_URL_REG_PARAM_NAME);
            searchStartCommand = WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY, Main.SEARCH_COMMAND_REG_PARAM_NAME);
            licenseQueryDelay = Integer.parseInt(WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY, Main.LICENSE_QUERY_DELAY_REG_PARAM_NAME));
            storeConfigInRegister = true;
        } else {
            searchLicenseServerURL = Main.DEFAULT_SEARCH_LICENSE_SERVER_URL; // "http://searchsrv:8990/";
            searchStartCommand = Main.DEFAULT_SEARCH_COMMAND; // "C:\\IM\\Search\\s4.exe";
            licenseQueryDelay = Main.DEFAULT_LICENSE_QUERY_DELAY; // 1
            storeConfigInRegister = false;
        }
    }

    public static void setConfig(String searchLicenseServerURL, String searchStartCommand, int licenceQueryDelay, boolean storeConfigInRegister) {
        SearchLauncherConfig.searchLicenseServerURL = searchLicenseServerURL;
        SearchLauncherConfig.searchStartCommand = searchStartCommand;
        SearchLauncherConfig.licenseQueryDelay = licenceQueryDelay;
        if (SearchLauncherConfig.storeConfigInRegister = storeConfigInRegister)
            writeConfigToRegistry();
        else
            deleteConfigFromRegistry();
    }

    public static void deleteConfigFromRegistry() {
        WinRegistryWrapper.WinRegDeleteKey(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY);
        storeConfigInRegister = false;
    }

    public static void writeConfigToRegistry() {
        WinRegistryWrapper.WinRegCreateKeyEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY);
        WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY, Main.SEARCH_LICENSE_SERVER_URL_REG_PARAM_NAME, searchLicenseServerURL);
        WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY, Main.SEARCH_COMMAND_REG_PARAM_NAME, searchStartCommand);
        WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, Main.REGISTRY_KEY, Main.LICENSE_QUERY_DELAY_REG_PARAM_NAME, Integer.toString(licenseQueryDelay));
        storeConfigInRegister = true;
    }

    public static String getSearchLicenseServerURL() {
        return searchLicenseServerURL;
    }

    public static String getSearchStartCommand() {
        return searchStartCommand;
    }

    public static int getLicenseQueryDelay() {
        return licenseQueryDelay;
    }

    public static boolean isStoreConfigInRegister() {
        return storeConfigInRegister;
    }
}
