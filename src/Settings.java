import javax.swing.*;
import java.awt.event.*;

public class Settings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField intermechLicenseMonitorURLTextField;
    private JTextField searchRunCommandTextField;
    private JSpinner queryIntervalSpinner;
    private JCheckBox storeConfigInRegisterCheckBox;

    Settings() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setResizable(false);
        setTitle(Main.APPLICATION_NAME + " settings");

        intermechLicenseMonitorURLTextField.setText(SearchLauncherConfig.getSearchLicenseServerURL());
        searchRunCommandTextField.setText(SearchLauncherConfig.getSearchStartCommand());
        queryIntervalSpinner.setModel(new SpinnerNumberModel(SearchLauncherConfig.getLicenseQueryDelay(), 1, 180, 1));
        storeConfigInRegisterCheckBox.setSelected(SearchLauncherConfig.isStoreConfigInRegister());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setLocationRelativeTo(null);
        buttonOK.requestFocus();
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        SearchLauncherConfig.setConfig(intermechLicenseMonitorURLTextField.getText(),
                searchRunCommandTextField.getText(),
                Integer.parseInt(queryIntervalSpinner.getValue().toString()),
                storeConfigInRegisterCheckBox.isSelected());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
