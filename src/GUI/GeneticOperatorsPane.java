/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Main.GA;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author luis
 */
public class GeneticOperatorsPane extends JPanel implements PropertyChangeListener{
    
    private JComboBox methodsBox,operatorsBox;
    private JLabel methods,operators,opsRate,mutRate,tournament;
    private JFormattedTextField operatorsField,mutationField,tournamentField;
    private JCheckBox checkMutation;
    private Font titleFont;
    private OperatorsL opListener;
    
    private int mRate=10,opRate=50,tSize=5;
    private NumberFormat amountFormat;
    
    private GA myGA=GA.getInstance();
    
    public GeneticOperatorsPane()
    {
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        //this.setPreferredSize(new Dimension(250,200));
        titleFont = new Font("Serif", Font.BOLD, 18);
        amountFormat = NumberFormat.getNumberInstance();
       
        startComponents();
    }
    
    private void startComponents()
    {
        String[] list= {"Tournament","Roullete Wheel"};
        methodsBox=new JComboBox(list);
        methodsBox.setBounds(10, 30, 200, 30);
        methodsBox.setSelectedIndex(1);
        this.add(methodsBox);
        opListener=new OperatorsL();
        methodsBox.addActionListener(opListener);
        
        Rectangle methodsBounds = methodsBox.getBounds();
        methods= new JLabel("Selection Method");
        methods.setFont(titleFont);
        methods.setBounds(methodsBounds.x, methodsBounds.y-30, methodsBounds.width, 30);
        this.add(methods);
        
        tournament=new JLabel("Size:");
        tournament.setBounds(methodsBounds.x+methodsBounds.width+5, methodsBounds.y, 40, 30);
        tournament.setVisible(false);
        tournament.setLabelFor(tournamentField);
        this.add(tournament);
        
        tournamentField = new JFormattedTextField(amountFormat);
        tournamentField.setValue(new Integer(tSize));
        tournamentField.setBounds(methodsBounds.x+240, methodsBounds.y, 30, 30);
        tournamentField.addPropertyChangeListener(this);
        tournamentField.setVisible(false);
        this.add(tournamentField);
        
        operators= new JLabel("Genetic Operators");
        operators.setFont(titleFont);
        operators.setBounds(methodsBounds.x, methodsBounds.y+30, methodsBounds.width, 30);
        this.add(operators);
        
        String[] list2= {"Overwrite (Lamarck Inheritance)","Crossover"};
        operatorsBox=new JComboBox(list2);
        operatorsBox.setBounds(methodsBounds.x, methodsBounds.y+60, methodsBounds.width, 30);
        operatorsBox.setSelectedIndex(1);
        this.add(operatorsBox);
        operatorsBox.addActionListener(opListener);
        
        opsRate= new JLabel("Rate:");
        opsRate.setBounds(methodsBounds.x+methodsBounds.width+5, methodsBounds.y+60, 40, 30);
        this.add(opsRate);
        
        operatorsField= new JFormattedTextField(amountFormat);
        operatorsField.setValue(new Integer(opRate));
        operatorsField.setBounds(methodsBounds.x+methodsBounds.width+40, methodsBounds.y+60, 30, 30);
        operatorsField.addPropertyChangeListener("value", this);
        this.add(operatorsField);
        
        checkMutation= new JCheckBox("Mutation");
        checkMutation.setBackground(this.getBackground());
        checkMutation.setSelected(true);
        checkMutation.setBounds(methodsBounds.x, methodsBounds.y+100, methodsBounds.width-80, 30);
        checkMutation.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                if(ie.getStateChange()==ItemEvent.DESELECTED)
                {
                    myGA.setMutationEnabled(false);
                    mutRate.setEnabled(false);
                    mutationField.setEnabled(false);
                }
                else
                {
                    myGA.setMutationEnabled(true);
                    mutRate.setEnabled(true);
                    mutationField.setEnabled(true);
                    myGA.setmutationRate(Integer.parseInt(mutationField.getText()));
                }
            }
        });
        this.add(checkMutation);
        
        mutRate= new JLabel("Rate:");
        mutRate.setBounds(methodsBounds.x+135, methodsBounds.y+100, 40, 30);
        this.add(mutRate);
        
        mutationField= new JFormattedTextField(mRate);
        mutationField.setValue(new Integer(mRate));
        mutationField.setBounds(methodsBounds.x+170, methodsBounds.y+100, 30, 30);
        mutationField.addPropertyChangeListener("value", this);
        this.add(mutationField);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) 
    {
       Object source = pce.getSource();
       if(source==operatorsField)
       {
           opRate = ((Number)operatorsField.getValue()).intValue();
           if(opRate<0 || opRate>100)
           {
               opRate=50;
               operatorsField.setValue(opRate);
           }  
           myGA.setOperatorRate(opRate);
       }
       else if(source==mutationField)
       {
           mRate=((Number)mutationField.getValue()).intValue();
           if(mRate<0 || mRate>100)
           {
               mRate=10;
               mutationField.setValue(mRate);
           }
           myGA.setmutationRate(mRate);
       }
       else if(source==tournamentField)
       {
           tSize=((Number)tournamentField.getValue()).intValue();
           if(tSize>myGA.getPopSize() || tSize<1)
           {
               tSize=5;
               tournamentField.setValue(tSize);
           }
           myGA.settournamentSize(tSize);
       }
    }
    
    private class OperatorsL implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            JComboBox activeBox = (JComboBox)ae.getSource();      
            if(activeBox==methodsBox)
            {
                myGA.setSelectionMethod(methodsBox.getSelectedIndex());
                if(methodsBox.getSelectedIndex()==0)
                {
                    tournament.setVisible(true);
                    tournamentField.setVisible(true);
                }
                else
                {
                    tournament.setVisible(false);
                    tournamentField.setVisible(false);
                }
            }
            else if(activeBox==operatorsBox)
            {
                myGA.setGeneticOperator(operatorsBox.getSelectedIndex());
                if(operatorsBox.getSelectedIndex()==0)
                {
                    methodsBox.setEnabled(false);
                    tournamentField.setEnabled(false);
                }
                else
                {
                    methodsBox.setEnabled(true);
                    tournamentField.setEnabled(true);
                }
            }
        }
        
    }
    
    
}
