import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lab4 extends JFrame {
    Rectangle background;
    int maxX;
    int maxY;
    BoxPanel boxPanel;
    GridBagPanel gridBagPanel;
    ScrollPanel scrollPanel;
    TabPanel tabPanel;

    public Lab4(){
        maxX = 900;
        maxY = 680;
        background = new Rectangle(0,0,maxX,maxY);
        setSize(maxX,maxY);
        setLocation(200,50);

        boxPanel = new BoxPanel();
        gridBagPanel = new GridBagPanel();
        scrollPanel = new ScrollPanel();
        tabPanel = new TabPanel();

        setLayout(new GridLayout(2,2));
        add(boxPanel);
        add(gridBagPanel);
        add(scrollPanel);
        add(tabPanel);
        }

    public static void main (String[] args){
		Lab4 thisLab = new Lab4();
		thisLab.setVisible(true);
                thisLab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

   public class TabPanel extends JPanel{
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel("Label in first tab");
        JTextField textField1 = new JTextField("TextField in first Tab");

        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("Label in second tab");
        JTextField textField2 = new JTextField("TextField in second Tab");

        JPanel panel3 = new JPanel();
        JLabel label3 = new JLabel("Label in third tab");
        JTextField textField3 = new JTextField("TextField in third Tab");

        public TabPanel(){
           panel1.setLayout(new GridLayout(1,0));
           panel1.add(label1);
           panel1.add(textField1);

           panel2.setLayout(new GridLayout(1,0));
           panel2.add(label2);
           panel2.add(textField2);

           panel3.setLayout(new GridLayout(1,0));
           panel3.add(label3);
           panel3.add(textField3);



           tabbedPane.addTab("Tab 1", null, panel1,"Does nothing");

           tabbedPane.addTab("Tab 2", null, panel2,"Does nothing");

           tabbedPane.addTab("Tab 3", null, panel3,"Does nothing");

           setLayout(new GridLayout(1,0));
           add(tabbedPane);
        }

   }

   public class BoxPanel extends JPanel{
       JRadioButton radioButton1;
       JRadioButton radioButton2; 
       JRadioButton radioButton3;
 
       public BoxPanel(){
       	  radioButton1 = new JRadioButton("Radio Button 1",false);
          radioButton2 = new JRadioButton("Radio Button 2",false);
          radioButton3 = new JRadioButton("Radio Button 3",true);
          
          ButtonGroup bg = new ButtonGroup();

          setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
         
  
          add(radioButton1);
          add(radioButton2);
          add(radioButton3);
          }
   }

   public class ScrollPanel extends JPanel{
       ImageIcon image = new ImageIcon("KH.jpg");
       JScrollPane  scrollPane;

       public ScrollPanel(){
           JLabel imageLabel = new JLabel(image);
           setLayout(new GridLayout(1,0));
 
           scrollPane = new JScrollPane(imageLabel);
           add(scrollPane);
       }
   }

   public class GridBagPanel extends JPanel{
       JTextField textField1;
       JTextField textField2;
       JTextField textField3;
       JLabel label1;
       JLabel label2;
       JLabel label3;
       JButton button1;
       JButton button2;
       JButton button3;

       public GridBagPanel(){
           setLayout(new GridBagLayout());
           GridBagConstraints c = new GridBagConstraints();

           label1 = new JLabel("Label 1");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.1;
           c.gridx = 0;
           c.gridy = 0;
           add(label1, c);

           textField1 = new JTextField("TextField 1");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.8;
           c.gridx = 0;
           c.gridy = 1;
           add(textField1, c);

           button1 = new JButton("Button 1");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.8;
           c.gridx = 0;
           c.gridy = 2;
           add(button1, c);

           label2 = new JLabel("Label 2");
           c.fill = GridBagConstraints.HORIZONTAL;
           //c.weightx = 0.1;
           c.gridx = 1;
           c.gridy = 0;
           add(label2, c);

           textField2 = new JTextField("TextField 2");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.1;
           c.gridx = 1;
           c.gridy = 1;
           add(textField2,c);


           button2 = new JButton("Button 2");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.1;
           c.gridx = 1;
           c.gridy = 2;
           add(button2, c);

           label3 = new JLabel("Label 3");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.1;
           c.gridx = 2;
           c.gridy = 0;
           add(label3, c);

           textField3 = new JTextField("TextField 3");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.1;
           c.gridx = 2;
           c.gridy = 1;
           add(textField3, c);

           button3 = new JButton("Button 3");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.weightx = 0.1;
           c.gridx = 2;
           c.gridy = 2;
           add(button3, c);
      }
   }
 
}
