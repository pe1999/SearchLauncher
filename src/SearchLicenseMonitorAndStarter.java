import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class SearchLicenseMonitorAndStarter extends Thread {
    @Override
    public void run() {
        try {
            URL searchSRV = new URL(SearchLauncherConfig.getSearchLicenseServerURL()); //http://searchsrv:8990/
            BufferedReader in;
            String outputLine;
            int a;
            do {
                in = new BufferedReader(new InputStreamReader(searchSRV.openStream()));
                outputLine = in.readLine();
                in.close();

                a = outputLine.indexOf("Search 12.0");
                for (int i = 0; i < 3; i++) {
                    a = outputLine.indexOf("<td>", a) + 4;
                }

                sleep(SearchLauncherConfig.getLicenceQueryDelay() * 1000);

                //System.out.println(Integer.parseInt(outputLine.substring(a, outputLine.indexOf("</td>", a))));

            } while (Integer.parseInt(outputLine.substring(a, outputLine.indexOf("</td>", a))) == 0);

            Runtime.getRuntime().exec(SearchLauncherConfig.getSearchStartCommand());
            Main.searchLicenseMonitorAndStarterStop();

        } catch (InterruptedException e) {
            //System.out.println("In InterruptedException catch");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        //System.out.println("Thread ended");
    }
}
