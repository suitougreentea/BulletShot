package jp.dip.suitougreentea.BulletShot;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.JSeparator;

public class FrameErrorScreen extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    FrameErrorScreen(String title){
        setTitle(title);
        setBounds(100, 100, 640, 442);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);
        
        final JLabel lblAnUnexceptedError = new JLabel("An unexcepted error has occurred in gameplay and BulletShot has aborted running.");
        springLayout.putConstraint(SpringLayout.NORTH, lblAnUnexceptedError, 10, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblAnUnexceptedError, -10, SpringLayout.EAST, getContentPane());
        lblAnUnexceptedError.setFont(lblAnUnexceptedError.getFont().deriveFont(lblAnUnexceptedError.getFont().getStyle() | Font.BOLD));
        getContentPane().add(lblAnUnexceptedError);
        
        final JLabel lblPleaseSendThis = new JLabel("Please send this error to BulletShot Dev. We will solve the problem.");
        springLayout.putConstraint(SpringLayout.WEST, lblAnUnexceptedError, 0, SpringLayout.WEST, lblPleaseSendThis);
        springLayout.putConstraint(SpringLayout.SOUTH, lblAnUnexceptedError, -6, SpringLayout.NORTH, lblPleaseSendThis);
        springLayout.putConstraint(SpringLayout.SOUTH, lblPleaseSendThis, -340, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, lblPleaseSendThis, 40, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, lblPleaseSendThis, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblPleaseSendThis, -10, SpringLayout.EAST, getContentPane());
        getContentPane().add(lblPleaseSendThis);
        
        final JButton btnShowhide = new JButton("Show/Hide");
        getContentPane().add(btnShowhide);
        
        final JLabel lblTechnicalInformation = new JLabel("Technical Information :");
        springLayout.putConstraint(SpringLayout.NORTH, lblTechnicalInformation, 105, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, btnShowhide, -4, SpringLayout.NORTH, lblTechnicalInformation);
        springLayout.putConstraint(SpringLayout.WEST, btnShowhide, 6, SpringLayout.EAST, lblTechnicalInformation);
        springLayout.putConstraint(SpringLayout.WEST, lblTechnicalInformation, 0, SpringLayout.WEST, lblAnUnexceptedError);
        getContentPane().add(lblTechnicalInformation);
        
        final JPanel panel = new JPanel();
        springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, btnShowhide);
        springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        
        final JTextArea txtrNullpointerexception = new JTextArea();
        panel.add(txtrNullpointerexception);
        springLayout.putConstraint(SpringLayout.NORTH, txtrNullpointerexception, 278, SpringLayout.SOUTH, btnShowhide);
        springLayout.putConstraint(SpringLayout.WEST, txtrNullpointerexception, 10, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, txtrNullpointerexception, -10, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, txtrNullpointerexception, 0, SpringLayout.EAST, lblAnUnexceptedError);
        txtrNullpointerexception.setLineWrap(true);
        txtrNullpointerexception.setEditable(false);
        txtrNullpointerexception.setText("NullPointerException");
        
        final JSeparator separator = new JSeparator();
        springLayout.putConstraint(SpringLayout.SOUTH, separator, -305, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, separator, 0, SpringLayout.WEST, lblAnUnexceptedError);
        springLayout.putConstraint(SpringLayout.EAST, separator, 0, SpringLayout.EAST, lblAnUnexceptedError);
        getContentPane().add(separator);
        
        final JButton btnReportErrorAnd = new JButton("Report error and restart");
        springLayout.putConstraint(SpringLayout.NORTH, separator, 6, SpringLayout.SOUTH, btnReportErrorAnd);
        springLayout.putConstraint(SpringLayout.NORTH, btnReportErrorAnd, 6, SpringLayout.SOUTH, lblPleaseSendThis);
        getContentPane().add(btnReportErrorAnd);
        
        final JButton btnRestart = new JButton("Restart");
        springLayout.putConstraint(SpringLayout.EAST, btnReportErrorAnd, -6, SpringLayout.WEST, btnRestart);
        springLayout.putConstraint(SpringLayout.NORTH, btnRestart, 6, SpringLayout.SOUTH, lblPleaseSendThis);
        getContentPane().add(btnRestart);
        
        final JButton btnReportError = new JButton("Report error and exit");
        springLayout.putConstraint(SpringLayout.EAST, btnRestart, -6, SpringLayout.WEST, btnReportError);
        springLayout.putConstraint(SpringLayout.NORTH, btnReportError, 6, SpringLayout.SOUTH, lblPleaseSendThis);
        getContentPane().add(btnReportError);
        
        final JButton btnExit = new JButton("Exit");
        springLayout.putConstraint(SpringLayout.EAST, btnReportError, -13, SpringLayout.WEST, btnExit);
        springLayout.putConstraint(SpringLayout.NORTH, btnExit, 6, SpringLayout.SOUTH, lblPleaseSendThis);
        springLayout.putConstraint(SpringLayout.EAST, btnExit, 0, SpringLayout.EAST, lblAnUnexceptedError);
        getContentPane().add(btnExit);
      }
}
