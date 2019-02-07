import com.sun.deploy.association.utility.WinRegistryWrapper;

public class SearchLauncherConfig {
    private static String searchLicenseServerURL;
    private static String searchStartCommand;
    private static int licenceQueryDelay; // in seconds
    private static boolean storeConfigInRegister;

    static {
        if (WinRegistryWrapper.WinRegSubKeyExist(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher")) {
            searchLicenseServerURL = WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher", "SearchLicenseServerURL");
            searchStartCommand = WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher", "SearchStartCommand");
            licenceQueryDelay = Integer.parseInt(WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher", "LicenceQueryDelay"));
            storeConfigInRegister = true;
        } else {
            searchLicenseServerURL = "http://searchsrv:8990/";
            searchStartCommand = "C:\\IM\\Search\\s4.exe";
            licenceQueryDelay = 1;
            storeConfigInRegister = false;
        }
    }

    public static void deleteConfigFromRegistry() {
        WinRegistryWrapper.WinRegDeleteKey(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher");
        storeConfigInRegister = false;
    }

    public static void writeConfigToRegistry() {
        WinRegistryWrapper.WinRegCreateKeyEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher");
        WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher", "SearchLicenseServerURL", searchLicenseServerURL);
        WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher", "SearchStartCommand", searchStartCommand);
        WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\Search Launcher", "LicenceQueryDelay", Integer.toString(licenceQueryDelay));
        storeConfigInRegister = true;
    }

    public static String getSearchLicenseServerURL() {
        return searchLicenseServerURL;
    }

    public static String getSearchStartCommand() {
        return searchStartCommand;
    }

    public static int getLicenceQueryDelay() {
        return licenceQueryDelay;
    }

    public static boolean isStoreConfigInRegister() {
        return storeConfigInRegister;
    }
}
