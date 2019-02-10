import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchLicenseMonitorAndStarter extends Thread {
    private static URL searchSRV; //http://searchsrv:8990/

    static {
        try {
            searchSRV = new URL(SearchLauncherConfig.getSearchLicenseServerURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while(getFreeSearchLicenses() == 0) {
                sleep(SearchLauncherConfig.getLicenseQueryDelay() * 1000);
            }
            Runtime.getRuntime().exec(SearchLauncherConfig.getSearchStartCommand());
            Main.searchLicenseMonitorAndStarterStop();

        } catch (InterruptedException e) {
            //System.out.println("In InterruptedException catch");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    synchronized static int getFreeSearchLicenses() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(searchSRV.openStream()));
        String outputLine = in.readLine();
        in.close();
        int index = outputLine.indexOf("Search 12.0");

        for (int i = 0; i < 3; i++) {
            index = outputLine.indexOf("<td>", index) + 4;
        }

        return Integer.parseInt(outputLine.substring(index, outputLine.indexOf("</td>", index)));
    }
}
