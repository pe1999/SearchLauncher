import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    static final String DEFAULT_SEARCH_LICENSE_SERVER_URL = "http://searchsrv:8990/";
    static final String DEFAULT_SEARCH_COMMAND =            "C:\\IM\\Search\\s4.exe";
    static final int    DEFAULT_LICENSE_QUERY_DELAY =       10;

    static final String REGISTRY_KEY = /*"SOFTWARE\\*/             "Search Launcher";
    static final String SEARCH_LICENSE_SERVER_URL_REG_PARAM_NAME = "SearchLicenseServerURL";
    static final String SEARCH_COMMAND_REG_PARAM_NAME =            "SearchStartCommand";
    static final String LICENSE_QUERY_DELAY_REG_PARAM_NAME =       "LicenceQueryDelay";

    static final String APPLICATION_NAME = "Search Launcher";

    private static final String APPLICATION_START_MESSAGE = "Search Launcher started!";
    private static final String ICON_STR =                  "/resources/Icon.png";

    private static SearchLicenseMonitorAndStarter searchLicenseMonitorAndStarter;
    private static boolean isSearchLicenseMonitorAndStarterRun = false;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setTrayIcon();
            }
        });
    }

    private static PopupMenu trayMenu;
    private static MenuItem  itemExit;
    private static MenuItem  itemConfig;
    private static MenuItem  itemCheckLicenses;
    private static MenuItem  itemStartStop;

    private static void setTrayIcon() {
        if (!SystemTray.isSupported()) {
            System.exit(1);
        }

        trayMenu = new PopupMenu();
        itemExit = new MenuItem("Exit");
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        trayMenu.add(itemExit);

        itemConfig = new MenuItem("Config");
        itemConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Settings();
            }
        });
        trayMenu.add(itemConfig);

        itemCheckLicenses = new MenuItem("Check Licenses");
        itemCheckLicenses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowSearchLicenses();
            }
        });
        trayMenu.add(itemCheckLicenses);

        itemStartStop = new MenuItem("Start");
        itemStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isSearchLicenseMonitorAndStarterRun) {
                    searchLicenseMonitorAndStarterStart();
                } else {
                    searchLicenseMonitorAndStarterStop();
                }
            }
        });
        trayMenu.add(itemStartStop);

        Image icon = Toolkit.getDefaultToolkit().getImage(Main.class.getResource(ICON_STR));
        TrayIcon trayIcon = new TrayIcon(icon, APPLICATION_NAME, trayMenu);
        trayIcon.setImageAutoSize(true);

        SystemTray tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage(APPLICATION_NAME, APPLICATION_START_MESSAGE, TrayIcon.MessageType.INFO);
    }

    static void searchLicenseMonitorAndStarterStop() {
        searchLicenseMonitorAndStarter.interrupt();
        itemStartStop.setLabel("Start");
        isSearchLicenseMonitorAndStarterRun = false;
    }

    private static void searchLicenseMonitorAndStarterStart() {
        searchLicenseMonitorAndStarter = new SearchLicenseMonitorAndStarter();
        searchLicenseMonitorAndStarter.start();
        itemStartStop.setLabel("Stop");
        isSearchLicenseMonitorAndStarterRun = true;
    }
}
