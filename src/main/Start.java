import form.LoginForm;

import javax.swing.*;
import java.awt.*;

public class Start {
    public static void main(String[] args){
        JFrame frame = new JFrame("登录");
        LoginForm loginForm = new LoginForm(frame);
        frame.setContentPane(loginForm.getLoginPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 160));
        frame.setResizable(false);//设置大小不可改变
        frame.setVisible(true);
    }
}
