package form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrom {
    private JPanel loginPanel;
    private JTextField account;
    private JTextField password;
    private JButton loginButton;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame("LoginFrom");
        LoginFrom  loginFrom =  new LoginFrom();
        frame.setContentPane(loginFrom.loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300,160));
        frame.setResizable(false);//设置大小不可改变
        loginFrom.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountText =loginFrom.account.getText();
                String passwordText=loginFrom.password.getText();
                if(accountText.equals(""))
                {
                    JOptionPane.showMessageDialog(loginFrom.loginPanel, "请输入账号", "警告",JOptionPane.WARNING_MESSAGE);
                }
                else if(passwordText.equals(""))
                {
                    if(!accountText.equals(""))
                    {
                        JOptionPane.showMessageDialog(loginFrom.loginPanel, "请输入密码", "警告",JOptionPane.WARNING_MESSAGE);
                    }
                }
                else
                {
                    JFrame mainFrame  = new JFrame("人事管理程序");
                    MainForm mainForm = new MainForm();
                    mainFrame.setMinimumSize(new Dimension(500,350));
                    mainFrame.setContentPane(mainForm.getMainPanel());
                    mainFrame.setVisible(true);
                    frame.setVisible(false);
                }
            }
        });
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        frame.setVisible(true);
    }
}
