package com.justin.deprecated;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author Justin Lee
 */
@Deprecated
public class LiMeTextFieldHintListener implements FocusListener {
    private final String hintText;
    private final JTextField textField;

    public LiMeTextFieldHintListener(JTextField textField, String hint) {
        this.textField = textField;
        this.hintText = hint;

        //默认直接显示
        textField.setText(hint);
        textField.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = textField.getText();
        if (temp.equals(hintText)) {
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = textField.getText();
        if (temp.isEmpty()) {
            textField.setForeground(Color.GRAY);
            textField.setText(hintText);
        }
    }
}
