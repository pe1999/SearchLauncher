import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SearchLicenseMonitorAndStarter extends Thread {
    @Override
    public void run() {
        try {
            while (getFreeSearchLicenses() == 0) {
                sleep(SearchLauncherConfig.getLicenseQueryDelay() * 1000);
            }
            Runtime.getRuntime().exec("cmd /c \"" + SearchLauncherConfig.getSearchStartCommand() + "\"");

        } catch (InterruptedException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Main.searchLicenseMonitorAndStarterStop();
        }
    }

    synchronized static int getFreeSearchLicenses() throws IOException {
        URL searchLicenseSRV = new URL(SearchLauncherConfig.getSearchLicenseServerURL());
        BufferedReader in = new BufferedReader(new InputStreamReader(searchLicenseSRV.openStream()));
        String outputLine = in.readLine();
        in.close();
        int index = outputLine.indexOf("Search 12.0");

        for (int i = 0; i < 3; i++) {
            index = outputLine.indexOf("<td>", index) + 4;
        }

        return Integer.parseInt(outputLine.substring(index, outputLine.indexOf("</td>", index)));
    }
}
