import com.sun.deploy.association.utility.WinRegistryWrapper;

public class RegWork {


    public static void main(String[] args) {
        System.out.println(WinRegistryWrapper.WinRegCreateKeyEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg"));
        System.out.println(WinRegistryWrapper.WinRegSetValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg", "MyKey", "MyValue"));
        //System.out.println(WinRegistryWrapper.WinRegDeleteValue(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg","MyKey"));
        //System.out.println(WinRegistryWrapper.WinRegDeleteKey(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg"));
        System.out.println(WinRegistryWrapper.WinRegSubKeyExist(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg"));
        System.out.println(WinRegistryWrapper.WinRegValueExist(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg", "MyKey"));
        System.out.println(WinRegistryWrapper.WinRegQueryValueEx(WinRegistryWrapper.HKEY_CURRENT_USER, "SOFTWARE\\MyJavaProg", "MyKey"));
        //WinRegistryUtil.
        //SearchLauncherConfig.writeConfigToRegistry();
        //SearchLauncherConfig.deleteConfigFromRegistry();
        System.out.println(SearchLauncherConfig.getSearchLicenseServerURL());
        System.out.println(SearchLauncherConfig.getSearchStartCommand());
        System.out.println(SearchLauncherConfig.getLicenseQueryDelay());

    }
}
