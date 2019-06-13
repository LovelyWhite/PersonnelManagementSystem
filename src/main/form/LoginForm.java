package form;

import dao.DatabaseUtils;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class LoginForm {
    private JPanel loginPanel;
    private JTextField account;
    private JTextField password;
    private JButton loginButton;
    private static boolean loginLock;
    public LoginForm(JFrame frame)
    {
        loginLock = true;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginLock) {
                    loginLock = false;
                    String accountText = account.getText();
                    String passwordText = password.getText();
                    account.setEnabled(false);
                    password.setEnabled(false);
                    if (accountText.equals("")) {
                        JOptionPane.showMessageDialog(loginPanel, "请输入账号", "警告", JOptionPane.WARNING_MESSAGE);
                    } else if (passwordText.equals("")) {
                        if (!accountText.equals("")) {
                            JOptionPane.showMessageDialog(loginPanel, "请输入密码", "警告", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        loginButton.setText("正在登陆");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    DatabaseUtils.linkDataBase();
                                    Admin admin = DatabaseUtils.findAdminByAccountAndPassword(accountText, passwordText);
                                    DatabaseUtils.closeCon();
                                    if (admin != null) {
                                        frame.setVisible(false);
                                        startMain(admin);
                                    } else {
                                        JOptionPane.showMessageDialog(loginPanel, "账户或密码错误", "提示", JOptionPane.INFORMATION_MESSAGE);
                                        account.setEnabled(true);
                                        password.setEnabled(true);
                                        loginButton.setText("登录");
                                        loginLock = true;
                                    }
                                } catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                } else {
                    JOptionPane.showMessageDialog(loginPanel, "正在登录...", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
    private static void startMain(Admin admin)
    {
        JFrame mainFrame  = new JFrame("人事管理程序");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainForm mainForm = new MainForm(mainFrame);
        mainForm.initManPanel(admin);
        mainFrame.setMinimumSize(new Dimension(500,350));
        mainFrame.setContentPane(mainForm.getMainPanel());
        mainFrame.setVisible(true);
    }

    public JPanel getLoginPanel() {
        return loginPanel;
    }
}
