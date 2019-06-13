package form;

import ThirdPalette.DatePicker;
import com.sun.deploy.panel.RadioPropertyGroup;
import dao.DatabaseUtils;
import model.People;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;

public class AddForm {
    private JFrame addFrame;
    private JPanel addPanel;
    private JTextField name;
    private JComboBox title;
    private JTextField age;
    private JComboBox poli;
    private JComboBox higest;
    ButtonGroup buttonGroup;
    private JRadioButton boy;
    private JRadioButton girl;
    private DatePicker termtime;
    private JButton submit;
    private DatePicker arrivetime;

    public AddForm(JFrame addFrame)
    {
        this.addFrame  = addFrame;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(boy);
        buttonGroup.add(girl);
        buttonGroup.setSelected(boy.getModel(),true);
        submit.addActionListener(e -> {
            String nameText = name.getText();
            String titleText = (String) title.getSelectedItem();
            String ageText = age.getText();
            String poliText = (String) poli.getSelectedItem();
            String higestText = (String)higest.getSelectedItem();
            Timestamp termtimeTimestamp = termtime.getTimeStamp();
            Timestamp arrivetimeTimestamp = arrivetime.getTimeStamp();
            String sexText="男";
            Enumeration<AbstractButton> radioBtns=buttonGroup.getElements();
            while (radioBtns.hasMoreElements()) {
                AbstractButton btn = radioBtns.nextElement();
                if(btn.isSelected()){
                    sexText=btn.getText();
                    break;
                }
            }

            if(nameText.equals(""))
            {
                JOptionPane.showMessageDialog(addPanel, "请输入姓名", "警告", JOptionPane.WARNING_MESSAGE);
            }
            else if(ageText .equals(""))
            {
                JOptionPane.showMessageDialog(addPanel, "请输入年龄", "警告", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                People people = new People();
                people.setName(nameText);
                people.setSex(sexText);
                people.setAge(Long.parseLong(ageText));
                people.setTitle(titleText);
                people.setPoliticalstatus(poliText);
                people.setHighestdegree(higestText);
                people.setTermtime(termtimeTimestamp);
                people.setArrivetime(arrivetimeTimestamp);
                DatabaseUtils.insertPeople(people);
                new Thread()
                {
                    @Override
                    public void run() {
                        super.run();
                        int result =  DatabaseUtils.commit();
                        System.out.println(result);
                        if(result==1)
                        {
                            JOptionPane.showMessageDialog(addPanel, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                            addFrame.setVisible(false);
                        }
                    }
                }.start();
            }
        });
    }

    public JPanel getAddPanel() {
        return addPanel;
    }
}
