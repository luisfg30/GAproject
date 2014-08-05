package GUI;


import Main.Function;
import Main.GA;
import java.awt.Dimension;
import javax.swing.*;



   public class GAFrame extends JFrame    
   {
     //ALGORITHM CLASSES
       private GA gAlgorithm= GA.getInstance();
       private Function evalFunction= Function.getInstance();
       
     //GUI COMPONENTS
    private JTabbedPane tabs;


    public GAFrame(VariablesPane v)
    {
       //CREATE AND SET COMPONENTS
        tabs= new JTabbedPane();
        
        PopulationPane p= new PopulationPane();
        //GeneticOperatorsPane go= new GeneticOperatorsPane();

        //ADD COMPONENTS TO THE MAIN CONTENT PANEL

        tabs.add("Variables & Function",v);
        //tabs.add("Genetic Options", go);
        tabs.add("Genetic Operators & Population",p);
        

        //SET UP FRAME
     
        this.setTitle("GA test program");
        this.setContentPane(tabs);
        this.setSize(new Dimension(1024,700));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
    
   }
