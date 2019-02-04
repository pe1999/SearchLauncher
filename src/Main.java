import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private static final String APPLICATION_NAME = "Search Launcher";
    private static final String ICON_STR = "/resources/Icon.png";
    private static SearchLicenseMonitorAndStarter searchLicenseMonitorAndStarter;
    private static boolean isSearchLicenseMonitorAndStarterRun = false;

    private static PopupMenu trayMenu;
    private static MenuItem itemExit;
    private static MenuItem itemStartStop;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
        //System.out.println("The end!");
    }

    private static void createGUI() {
//        JFrame frame = new JFrame(APPLICATION_NAME);
//        frame.setMinimumSize(new Dimension(300, 200));
//        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);

        setTrayIcon();
    }

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

        trayIcon.displayMessage(APPLICATION_NAME, "Application started!", TrayIcon.MessageType.INFO);
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
