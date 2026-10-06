import java.awt.*;
import javax.swing.*;
/**
 * A class to store things that might be needed repeatedly.
 * 
 */
public class Extras extends familialDatabase
{
/**
 * A method to generate the default frame.
 * @return The frame.
 */
static public JFrame frameMethod()
{
       JFrame frame = new JFrame("My JFrame"); // Creates a frame for the background.
       frame.setSize(1920,1080);
       frame.getContentPane().setBackground(Color.cyan);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       JPanel top = new JPanel(); // Creates a frame for giving detail to the top of the background.
       top.setBackground(Color.gray);
       frame.getContentPane().add(top,BorderLayout.NORTH);

       JPanel left = new JPanel(); // Creates a frame for giving detail to the left of the background.
       left.setBackground(Color.gray);
       frame.getContentPane().add(left,BorderLayout.WEST);

       JPanel right = new JPanel(); // Creates a frame for giving detail to the right of the background.
       right.setBackground(Color.gray);
       frame.getContentPane().add(right,BorderLayout.EAST);

       JPanel sous = new JPanel(); // Creates a frame for giving detail to the bottom of the background.
       sous.setBackground(Color.gray);
       frame.getContentPane().add(sous,BorderLayout.SOUTH);

       return frame;
}
/**
 * A method to store the default bag constraints.
 * @return The default bag constraints.
 */
static public GridBagConstraints gbc()
{
    GridBagConstraints gbc = new GridBagConstraints(); // Adds constraints.
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.insets = new Insets(10,10,10,10);
    return gbc;
}
/**
 * A method to allow the user to centre data in the middle of the screen.
 * @return The centred data template.
 */
public static JPanel centre()
{
    JPanel centre = new JPanel();
    centre.setOpaque(false);
    centre.setBackground(Color.gray);
    centre.setLayout(new BoxLayout(centre,BoxLayout.Y_AXIS));
    return centre;
}
}
