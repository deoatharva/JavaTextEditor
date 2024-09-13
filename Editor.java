import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import javax.swing.undo.UndoManager;


class Editor extends JFrame implements ActionListener {
    // Text component
    JTextArea t;

    // Frame
    JFrame f;

    // Undo manager
    UndoManager undoManager;

    // Constructor
    Editor() {
        // Create a frame
        f = new JFrame("Text Editor By ADEV");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        // Text component
        t = new JTextArea();

        // Add KeyListener to automatically close brackets and handle indentation
        t.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '{') {
                    int caretPosition = t.getCaretPosition();
                    t.insert("}", caretPosition);
                    t.setCaretPosition(caretPosition);
                }
                if (e.getKeyChar() == '[') {
                    int caretPosition = t.getCaretPosition();
                    t.insert("]", caretPosition);
                    t.setCaretPosition(caretPosition);
                }
                if (e.getKeyChar() == '(') {
                    int caretPosition = t.getCaretPosition();
                    t.insert(")", caretPosition);
                    t.setCaretPosition(caretPosition);
                }
                if (e.getKeyChar() == '"') {
                    int caretPosition = t.getCaretPosition();
                    t.insert("\"", caretPosition);
                    t.setCaretPosition(caretPosition);
                }
                if (e.getKeyChar() == '\'') {
                    int caretPosition = t.getCaretPosition();
                    t.insert("'", caretPosition);
                    t.setCaretPosition(caretPosition);
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        int caretPosition = t.getCaretPosition();
                        int start = Utilities.getRowStart(t, caretPosition);
                        String line = t.getText(start, caretPosition - start);
                        String indent = "";
                        for (char c : line.toCharArray()) {
                            if (c == ' ' || c == '\t') {
                                indent += c;
                            } else {
                                break;
                            }
                        }
                        if (line.trim().endsWith("{")) {
                            t.insert("\n" + indent + "\t\n" + indent + "", caretPosition);
                            t.setCaretPosition(caretPosition + indent.length() + 2);
                        } else {
                            t.insert("\n" + indent, caretPosition);
                            t.setCaretPosition(caretPosition + indent.length() + 1);
                        }
                        e.consume();
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
            public void keyReleased(KeyEvent e) {
                String text = t.getText();
                String replacement = null;
                
                if (text.endsWith("@C0")) {
                    replacement = "#include <stdio.h>\nint main() {\n    // Write C code here\n    printf(\"Hi Atharva\");\n\n    return 0;\n}\n";
                } else if (text.endsWith("@C+")) {
                    replacement = "#include <iostream>\n\nint main() {\n    // Write C++ code here\n    std::cout << \"Hi Atharva\";\n\n    return 0;\n}\n";
                } else if (text.endsWith("@C#")) {
                    replacement = "class Program\n{\n    public static void Main(string[] args)\n    {\n        System.Console.WriteLine (\" Hi Atharva \");\n    }\n}\n";
                } else if (text.endsWith("@JV")) {
                    replacement = "class Program {\n    public static void main(String[] args) {\n        System.out.println(\"Hi Atharva\");\n    }\n}\n";
                } else if (text.endsWith("@HTML")) {
                    replacement = "<!DOCTYPE html>\n<html lang=\"en\">\n\n<head>\n  <meta charset=\"UTF-8\" />\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n  <link rel=\"stylesheet\" href=\"style.css\" />\n  <title>Browser</title>\n</head>\n\n<body>\n  <h1>\n    Write, edit and run HTML, CSS and JavaScript code online.\n  </h1>\n  <p>\n    Our HTML editor updates the webview automatically in real-time as you write code.\n  </p>\n  <script src=\"script.js\"></script>\n</body>\n\n</html>\n";
                } else if (text.endsWith("switch(ch")) {
                    replacement = "(ch) \n{\n\tcase 1:\n\t\t          \n\t\tbreak;\n\tcase 2:\n\t\t       \n\t\tbreak;\n\tdefault:\n\t\t          \n\t\tbreak;\n}";
                }
                else if (text.endsWith("for")) {
                    replacement = "for(int i = 0; i < 5; i++) \n{\n\t \n}\n";
                }
                else if (text.endsWith("foreach")) {
                    replacement = "for ( : ) \n{\n\t\n}\n";
                }
                else if (text.endsWith("do{")) {
                    replacement = "";
                }
                else if (text.endsWith("while")) {
                    replacement = "ile(i < 5)\n{\n\t\n}\n";
                }
                else if (text.endsWith("if()")) {
                    replacement = "if()\n{\n\t\n}";
                }
                else if (text.endsWith("else if()")) {
                    replacement = "else if()\n{\n\t\n}";
                }
                else if (text.endsWith("else{}")) {
                    replacement = "else\n{\n\t\n}";
                }
                else if (text.endsWith("ad")) {
                    replacement = "add";
                }
                else if (text.endsWith("abst")) {
                    replacement = "abstract";
                }
                else if (text.endsWith("bre")) {
                    replacement = "break";
                }
                else if (text.endsWith("cha")) {
                    replacement = "char";
                }
                else if (text.endsWith("cont")) {
                    replacement = "continue";
                }
                else if (text.endsWith("cla")) {
                    replacement = "class";
                }               
                else if (text.endsWith("doub")) {
                    replacement = "double";
                }
                else if (text.endsWith("flo")) {
                    replacement = "float";
                }
                else if (text.endsWith("pack")) {
                    replacement = "package";
                }
                else if (text.endsWith("publ")) {
                    replacement = "public";
                }
                else if (text.endsWith("priv")) {
                    replacement = "private";
                }
                else if (text.endsWith("prot")) {
                    replacement = "protected";
                }
                else if (text.endsWith("ret")) {
                    replacement = "return";
                }
                
                else if (text.endsWith("stri")) {
                    replacement = "string";
                }
                
                else if (text.endsWith("S.C.WL")) {
                    replacement = "System.Console.WriteLine()";
                }
                else if (text.endsWith("S.C.T")) {
                    replacement = "System.Convert.To";
                }
                else if (text.endsWith("S.M.S")) {
                    replacement = "System.Math.Sqrt";
                }
                else if (text.endsWith("S.C.RL")) {
                    replacement = "System.Math.Sqrt";
                }
                
                if (replacement != null) {
                    int caretPosition = t.getCaretPosition();
                    int length = 3; // default length for shortcuts like @C0, @C+, etc.
                    if (text.endsWith("@HTML")) {
                        length = 5;
                    }
                    t.replaceRange(replacement, caretPosition - length, caretPosition);
                    t.setCaretPosition(caretPosition - length + replacement.length());
                }
            }
        });

        // Create UndoManager
        undoManager = new UndoManager();
        t.getDocument().addUndoableEditListener(undoManager);

        // Create a menubar
        JMenuBar mb = new JMenuBar();

        // Create a menu for File
        JMenu m1 = new JMenu("File");
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);
        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);

        // Create a menu for Edit
        JMenu m2 = new JMenu("Edit");
        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");
        JMenuItem mi7 = new JMenuItem("Find");
        JMenuItem mi8 = new JMenuItem("Replace");
        JMenuItem miUndo = new JMenuItem("Undo");
        JMenuItem miRedo = new JMenuItem("Redo");
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);
        mi8.addActionListener(this);
        miUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) {
                    undoManager.undo();
                }
            }
        });
        miRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) {
                    undoManager.redo();
                }
            }
        });
        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);
        m2.add(mi7);
        m2.add(mi8);
        m2.addSeparator();
        m2.add(miUndo);
        m2.add(miRedo);

        // Create a menu item for close
        JMenuItem mc = new JMenuItem("close");
        mc.addActionListener(this);

        // Add menus to the menu bar
        mb.add(m1);
        mb.add(m2);
        mb.add(mc);

        // Set the menu bar to the frame
        f.setJMenuBar(mb);
        f.add(t);
        f.setSize(500, 500);
        f.show();
    }

    // If a button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("cut")) {
            t.cut();
        } else if (s.equals("copy")) {
            t.copy();
        } else if (s.equals("paste")) {
            t.paste();
        } else if (s.equals("Save")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    FileWriter wr = new FileWriter(fi, false);
                    BufferedWriter w = new BufferedWriter(wr);
                    w.write(t.getText());
                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            } else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        } else if (s.equals("Print")) {
            try {
                t.print();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        } else if (s.equals("Open")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    String s1 = "", sl = "";
                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);
                    sl = br.readLine();
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }
                    t.setText(sl);
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            } else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        } else if (s.equals("New")) {
            t.setText("");
        } else if (s.equals("close")) {
            f.setVisible(false);
        } else if (s.equals("Find")) {
            String find = JOptionPane.showInputDialog(f, "Enter text to find:");
            String content = t.getText();
            int index = content.indexOf(find);
            if (index != -1) {
                t.setCaretPosition(index);
                t.requestFocusInWindow();
            } else {
                JOptionPane.showMessageDialog(f, "Text not found.");
            }
        } else if (s.equals("Replace")) {
            JDialog replaceDialog = new JDialog(f, "Replace");
            replaceDialog.setLayout(new GridLayout(3, 2));
            JLabel findLabel = new JLabel("Find:");
            JLabel replaceLabel = new JLabel("Replace:");
            JTextField findField = new JTextField();
            JTextField replaceField = new JTextField();
            JButton replaceButton = new JButton("Replace All");
            replaceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String findText = findField.getText();
                    String replaceText = replaceField.getText();
                    t.setText(t.getText().replaceAll(findText, replaceText));
                    replaceDialog.dispose();
                }
            });
            replaceDialog.add(findLabel);
            replaceDialog.add(findField);
            replaceDialog.add(replaceLabel);
            replaceDialog.add(replaceField);
            replaceDialog.add(new JLabel());
            replaceDialog.add(replaceButton);
            replaceDialog.setSize(300, 150);
            replaceDialog.setVisible(true);
        }
    }

    // Main class
    public static void main(String args[]) {
        Editor e = new Editor();
    }
}
