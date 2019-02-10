import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ShowSearchLicenses extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel labelShowLicenses;

    ShowSearchLicenses() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setResizable(false);
        setTitle(Main.APPLICATION_NAME);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        try {
            labelShowLicenses.setText("Free Search licenses: " + SearchLicenseMonitorAndStarter.getFreeSearchLicenses());
        } catch (IOException e) {
            e.printStackTrace();
        }

        pack();
        setLocationRelativeTo(null);
        buttonOK.requestFocus();
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

//    public static void main(String[] args) {
//        ShowSearchLicenses dialog = new ShowSearchLicenses();
////        dialog.pack();
////        dialog.setVisible(true);
//        System.exit(0);
//    }
}
