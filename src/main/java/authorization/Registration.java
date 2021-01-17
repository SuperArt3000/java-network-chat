package authorization;

import database.SQLService;
import validator.Validator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author Zurbaevi Nika
 */
public class Registration extends JDialog {
    private JTextField textFieldNickname;
    private JPasswordField passwordField;
    private JLabel labelNickname;
    private JLabel labelPassword;
    private JButton buttonRegistration;
    private JButton buttonCancel;
    private boolean succeeded;

    public Registration(Frame parent) {
        super(parent, "Registration", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        labelNickname = new JLabel("Username: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        panel.add(labelNickname, gridBagConstraints);

        textFieldNickname = new JTextField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        panel.add(textFieldNickname, gridBagConstraints);

        labelPassword = new JLabel("Password: ");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        panel.add(labelPassword, gridBagConstraints);

        passwordField = new JPasswordField(20);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panel.add(passwordField, gridBagConstraints);
        panel.setBorder(new LineBorder(Color.GRAY));

        buttonRegistration = new JButton("Registration");

        buttonRegistration.addActionListener(e -> {
            try {
                if (checkUserInputPasswordAndNickname(getNickname(), getPassword()) && authenticate(getNickname(), getPassword())) {
                    JOptionPane.showMessageDialog(Registration.this, "You have successfully registration.", "Registration", JOptionPane.INFORMATION_MESSAGE);
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Registration.this, "Invalid username or password", "Registration", JOptionPane.ERROR_MESSAGE);
                    textFieldNickname.setText("");
                    passwordField.setText("");
                    succeeded = false;
                }
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(Registration.this, sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonRegistration);
        buttonPanel.add(buttonCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean authenticate(String getNickname, String password) throws SQLException {
        return SQLService.registration(getNickname, password);
    }

    public String getNickname() {
        return textFieldNickname.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    private boolean checkUserInputPasswordAndNickname(String nickname, String password) {
        return Validator.isValidNickname(nickname) && Validator.isValidPassword(password);
    }
}
