package rocks.zipcode;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

//TextEditor class starts here
class TextEditor extends JFrame implements ActionListener {
    Boolean portait = true;
    JTextArea ta = new JTextArea();
    String title = "Untitled Notepad";
    int i, len1, len, pos1;
    String str = "", s3 = "", s2 = "", s4 = "", s32 = "", s6 = "", s7 = "", s8 = "", s9 = "";
    String months[] = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December" };
    CheckboxMenuItem chkb = new CheckboxMenuItem("Word Wrap", true);
    // i found out this is a C way of making this array but it's handydandy
    final int[] portraitDimensions = {300, 500};

    public TextEditor() {
        MenuBar mb = new MenuBar();
        setLayout(new BorderLayout());
        add("Center", ta);
        setMenuBar(mb);
        Menu m1 = new Menu("File");
        Menu m2 = new Menu("Edit");
        Menu m3 = new Menu("Tools");
        Menu m4 = new Menu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);
        MenuItem mi1[] = {
                new MenuItem("New"), new MenuItem("Open"), new MenuItem("Save"), new MenuItem("Save As"),
                new MenuItem("Page Setup"), new MenuItem("Print"), new MenuItem("Exit")
        };
        MenuItem mi2[] = { new MenuItem("Delete"), new MenuItem("Cut"),
                new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Find"),
                new MenuItem("Find Next"), new MenuItem("Replace"),
                new MenuItem("Go To"), new MenuItem("Select All"),
                new MenuItem("Time Stamp") };
        MenuItem mi3[] = { new MenuItem("Choose Font"), new MenuItem("Compile"),
                new MenuItem("Run") };
        MenuItem mi4[] = { new MenuItem("Help Topics"),
                new MenuItem("About TextEditor") };
        for (int i = 0; i < mi1.length; i++) {
            m1.add(mi1[i]);
            mi1[i].addActionListener(this);
        }
        for (int i = 0; i < mi2.length; i++) {
            m2.add(mi2[i]);
            mi2[i].addActionListener(this);
        }
        m3.add(chkb);
        chkb.addActionListener(this);
        for (int i = 0; i < mi3.length; i++) {
            m3.add(mi3[i]);
            mi3[i].addActionListener(this);
        }
        for (int i = 0; i < mi4.length; i++) {
            m4.add(mi4[i]);
            mi4[i].addActionListener(this);
        }

        MyWindowsAdapter mw = new MyWindowsAdapter(this);
        addWindowListener(mw);

        CheckBoxItemListener cbil = new CheckBoxItemListener(this);
        chkb.addItemListener(cbil);
        actionPerformed(new ActionEvent(this, 0, "Word Wrap"));

        setSize(300, 500);
        setTitle(title);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        String arg = (String) ae.getActionCommand();
        if (arg.equals("New")) {
            dispose();
            TextEditor t11 = new TextEditor();
            t11.setSize(500, 500);
            t11.setVisible(true);
        }
        try {
            if (arg.equals("Open")) {
                FileDialog fd1 = new FileDialog(this, "Select File", FileDialog.LOAD);
                fd1.setVisible(true);
                String s4 = "";
                s2 = fd1.getFile();
                s3 = fd1.getDirectory();
                s32 = s3 + s2;
                File f = new File(s32);
                FileInputStream fii = new FileInputStream(f);
                len = (int) f.length();
                for (int j = 0; j < len; j++) {
                    char s5 = (char) fii.read();
                    s4 = s4 + s5;
                }
                ta.setText(s4);
                fii.close();
            }
        } catch (IOException e) {
        }
        try {
            if (arg.equals("Save")) {
                String fileName = null;
                if(!s9.equals("")){
                    // save as file
                    fileName = s9;
                }
                else if(!s32.equals("")){
                    fileName = s32;
                }

                if(fileName == null){
                    arg = "Save As";
                }
                else {
                    s6 = ta.getText();
                    len1 = s6.length();
                    byte buf[] = s6.getBytes();

                    File f = new File(fileName);
                    FileOutputStream fobj = new FileOutputStream(f);
                    for (int k = 0; k < len1; k++) {
                        fobj.write(buf[k]);
                    }
                    fobj.close();
                }
            }
        } catch (IOException e) {
        }
        try {
            if (arg.equals("Save As")) {
                FileDialog dialog1 = new FileDialog(this, "Save As", FileDialog.SAVE);
                dialog1.setVisible(true);
                s7 = dialog1.getDirectory();
                s8 = dialog1.getFile();
                s9 = s7 + s8 + ".txt";
                s6 = ta.getText();
                len1 = s6.length();
                byte buf[] = s6.getBytes();
                File f1 = new File(s9);
                FileOutputStream fobj1 = new FileOutputStream(f1);
                for (int k = 0; k < len1; k++) {
                    fobj1.write(buf[k]);
                }
                fobj1.close();
            }
            this.setTitle(s8 + " TextEditor File");
        } catch (IOException e) {
        }
        if (arg.equals("Exit")) {
            System.exit(0);
        }
        if(arg.equals("Page Setup")) {
            JPanel panel = new JPanel();
            JRadioButton jrb1 = new JRadioButton("Landscape");
            JRadioButton jrb2 = new JRadioButton("Portrait");
            ButtonGroup bg = new ButtonGroup();
            bg.add(jrb1);bg.add(jrb2);
            panel.add(jrb2);panel.add(jrb1);
            JOptionPane.showMessageDialog(null, panel, "Select Layout", JOptionPane.QUESTION_MESSAGE);
            if(jrb1.isSelected() && portait){
                portait = false;
                setSize(getSize().height, getSize().width);
            }else if(jrb2.isSelected() && !portait){
                portait = true;
                setSize(getSize().height, getSize().width);
            }

//                setSize(getSize().height, getSize().width);

        }
        if (arg.equals("Cut")) {
            str = ta.getSelectedText();
            i = ta.getText().indexOf(str);
            ta.replaceRange("", i, i + str.length());
        }
        if (arg.equals("Copy")) {
            str = ta.getSelectedText();
        }
        if (arg.equals("Paste")) {
            pos1 = ta.getCaretPosition();
            ta.insert(str, pos1);
        }
        if (arg.equals("Delete")) {
            String msg = ta.getSelectedText();
            i = ta.getText().indexOf(msg);
            ta.replaceRange("", i, i + msg.length());
//            msg = "";
        }
        if (arg.equals("Select All")) {
            String strText = ta.getText();
            int strLen = strText.length();
            ta.select(0, strLen);
        }
        if (arg.equals("Time Stamp")) {
            GregorianCalendar gcalendar = new GregorianCalendar();
            String h = String.valueOf(gcalendar.get(Calendar.HOUR));
            String m = String.valueOf(gcalendar.get(Calendar.MINUTE));
            String s = String.valueOf(gcalendar.get(Calendar.SECOND));
            String date = String.valueOf(gcalendar.get(Calendar.DATE));
            String mon = months[gcalendar.get(Calendar.MONTH)];
            String year = String.valueOf(gcalendar.get(Calendar.YEAR));
            String hms = "Time" + " - " + h + ":" + m + ":" + s + " Date" + " - " + date + " " + mon + " " + year + " ";
            int loc = ta.getCaretPosition();
            ta.insert(hms, loc);
        }
        if (arg.equals("About TextEditor")) {
            AboutDialog d1 = new AboutDialog(this, "About TextEditor", true);
            d1.setVisible(true);
        }
        if(arg.equals("Find")){
            String stringToFind = JOptionPane.showInputDialog(null, "What would you like to find?",
                    "Find", JOptionPane.INFORMATION_MESSAGE);
            // i'll get to you one day
//            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );
            stringToFind = (stringToFind == null) ? "" : stringToFind;
            int offset = ta.getText().indexOf(stringToFind);
            int length = stringToFind.length();
            ta.select(offset, offset + length);

        }
        if(arg.equals("Find Next")){
            String stringToFind = JOptionPane.showInputDialog(null,
                    "What would you like to find next occurrence of?", "Find Next", JOptionPane.INFORMATION_MESSAGE);
            int loc = ta.getCaretPosition();
            int offset = ta.getText().indexOf(stringToFind, loc);
            ta.select(offset, offset + stringToFind.length());
        }
        if(arg.equals("Replace")) {
            JTextField stringToFind = new JTextField();
            JTextField stringToReplaceWith = new JTextField();
            Object[] message = {
                    "Find:", stringToFind,
                    "Replace:", stringToReplaceWith};

            int option = JOptionPane.showConfirmDialog(null, message, "Find and Replace", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION){
                int offset = ta.getText().indexOf(stringToFind.getText());
                ta.replaceRange(stringToReplaceWith.getText(), offset, offset + stringToFind.getText().length());
                ta.setCaretPosition(offset + stringToReplaceWith.getText().length());
            }
        }
        if(arg.equals("Help Topics")){
            AboutDialog d2 = new AboutDialog(this, "Get Help from TextEditor", false);
            d2.setVisible(true);
        }
        if(arg.equals("Word Wrap")){
            ta.setLineWrap(chkb.getState());
            ta.setWrapStyleWord(chkb.getState());
        }
        if(arg.equals("Choose Font")){
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font[] fonts = ge.getAllFonts();
            String[] fontNames = Arrays.stream(fonts).map(Font::getFontName).toArray(String[]::new);
            JComboBox<String> comboBox = new JComboBox<>(fontNames);
            JOptionPane.showMessageDialog(null, comboBox, "Select Font", JOptionPane.QUESTION_MESSAGE);
            // Question message shows our lil mascot dude :)
            ta.setFont(new Font((String)comboBox.getSelectedItem(), Font.PLAIN, 14));
        }
        setTitle(title);
    }

    public static void main(String args[]) {
        TextEditor to = new TextEditor();
    }
}

class CheckBoxItemListener implements ItemListener {
    TextEditor tt;

    public CheckBoxItemListener(TextEditor ttt) {
        tt = ttt;
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        tt.actionPerformed(new ActionEvent(tt, 0, "Word Wrap"));
    }
}

class MyWindowsAdapter extends WindowAdapter {
    TextEditor tt;

    public MyWindowsAdapter(TextEditor ttt) {
        tt = ttt;
    }

    public void windowClosing(WindowEvent we) {
        if(!tt.ta.getText().isEmpty()) {
            tt.actionPerformed(new ActionEvent(tt, 0, "Save"));
        }
        tt.dispose();
    }
}

class AboutDialog extends JDialog implements ActionListener {
    String about_msg = "\n\n\t\t  Welcome to TextEditor!\n\n" +
            "   Developed by a bunch of talented cats who are passionate about destroying things.";
    String help_msg = "Too dumb for our program? Maybe you're new here? TextEditor is here to help!\n\n" +
           "Most of the buttons are pretty self explanatory.\n\n" +
            "We will save your progress when you click that red close window button...\n" +
            "... unless you CHOOSE to cancel during \"Save As\"... then... well... you know.\n\n" +
            "This help window will stay here as long as you need it to. But please, just play around with the features." +
            " It's not like you have many more important things to do...\n\n" +
            "But if you REALLY must know, this help message will list some interesting features.\n\n\n\n\n\n\n\n\n" +
            "\t\t\tARE YOU READY KIDS???\n\n\n" +
            "There's the four main menus at the top: File, Edit, Tools, and Help\n\n" +
            "File contains all the tools necessary to create a file, save a file, open your file... you get the idea\n\n" +
            "Edit contains all the tools necessary to edit the text of the file, " +
            "and even has a handy dandy little timestamp function\n\n" +
            "Tools contains tools that'll help make your life fun with fonts and word wrapping!\n\n" +
            "Help is self explanatory. Otherwise, how did you GET here???\n" +
            "Actually, don't answer that one... I'll give you the benefit of the doubt";
    JTextArea jta;
    JScrollPane scrollPane;

    AboutDialog(Frame parent, String title, boolean isAbout) {
        super(parent, title, false);
        this.setResizable(!isAbout);

        if (isAbout) {
            setBounds(100, 100, 555, 150);
        } else {
            setBounds(100, 100, 515, 225);
        }
        getContentPane().setLayout(new BorderLayout(0, 0));

        setUpTextArea(isAbout);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(jta);
    }

    private void setUpTextArea(boolean isAbout) {
        jta = isAbout ? new JTextArea(about_msg) : new JTextArea(help_msg);
        scrollPane = new JScrollPane(jta);
        jta.setBackground(getBackground());
        jta.setEditable(false);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        jta.setSize(490, 500);
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }
}

