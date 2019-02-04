public class SearchLauncherConfig {
    private static String searchLicenseServerURL;
    private static String searchStartCommand;
    private static int licenceQueryDelay; // in seconds

    static {
        searchLicenseServerURL = "http://searchsrv:8990/";
        searchStartCommand = "C:\\IM\\Search\\s4.exe";
        licenceQueryDelay = 1;
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
}
