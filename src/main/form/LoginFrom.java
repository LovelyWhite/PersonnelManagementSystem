package form;

import dao.DatabaseUtils;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;


public class LoginFrom {
    private JPanel loginPanel;
    private JTextField account;
    private JTextField password;
    private JButton loginButton;
    private static JFrame frame;
    private static boolean loginLock;
    LoginFrom()
    {
        loginLock = true;
    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        frame = new JFrame("LoginFrom");
        LoginFrom  loginFrom =  new LoginFrom();
        frame.setContentPane(loginFrom.loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300,160));
        frame.setResizable(false);//设置大小不可改变
        loginFrom.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loginLock) {
                    loginLock = false;
                    String accountText = loginFrom.account.getText();
                    String passwordText = loginFrom.password.getText();
                    loginFrom.account.setEnabled(false);
                    loginFrom.password.setEnabled(false);
                    if (accountText.equals("")) {
                        JOptionPane.showMessageDialog(loginFrom.loginPanel, "请输入账号", "警告", JOptionPane.WARNING_MESSAGE);
                    } else if (passwordText.equals("")) {
                        if (!accountText.equals("")) {
                            JOptionPane.showMessageDialog(loginFrom.loginPanel, "请输入密码", "警告", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        loginFrom.loginButton.setText("正在登陆");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                 try {
                                  DatabaseUtils.linkDataBase();
                                for (int i = 0; i < 11111; i++)
                                    System.out.println("wait");
                                Admin admin = DatabaseUtils.findAdminByAccountAndPassword(accountText, passwordText);
//                                admin  = new Admin();
//                                admin.setAccount("1234");
//                                admin.setLevel("管理员");
                                if (admin != null) {
                                    startMain(admin);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(loginFrom.loginPanel, "账户或密码错误", "提示", JOptionPane.INFORMATION_MESSAGE);
                                    loginFrom.account.setEnabled(true);
                                    loginFrom.password.setEnabled(true);
                                    loginFrom.loginButton.setText("登录");
                                    loginLock = true;
                                }
                            } catch (SQLException | ClassNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            }
                        }.start();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(loginFrom.loginPanel, "正在登录...", "警告", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.setVisible(true);
    }
    private static void startMain(Admin admin)
    {
        JFrame mainFrame  = new JFrame("人事管理程序");
        MainForm mainForm = new MainForm(admin);
        mainFrame.setMinimumSize(new Dimension(500,350));
        mainFrame.setContentPane(mainForm.getMainPanel());
        mainFrame.setVisible(true);
        frame.setVisible(false);
    }
}
