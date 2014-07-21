/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Main.Function;
import Main.GA;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author luis
 */
public class VariablesPane extends JPanel implements PropertyChangeListener{
    
    
    private GA myGA=GA.getInstance();
    private Function evalFunction= Function.getInstance();

    private JTextPane geneString;
    private JLabel genLabel,varOptions,xLabel,yLabel,xMin,xMax,yMin,yMax,xp,yp,sizeLabel;
    private JFormattedTextField xMinField,xMaxField,xpField,yMinField,yMaxField,ypField;
    private Font titleFont;
    private Style style;
    private StyledDocument doc;
    
    private NumberFormat amountFormat;
    private float x=-5,X=5,y=-5,Y=5;
    private int xC=4,yC=4;
    public VariablesPane()
    {
        
        evalFunction.setParameters(0, 0, 0);
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        titleFont = new Font("Serif", Font.BOLD, 18);
        amountFormat = NumberFormat.getNumberInstance();
        
        startComponents();
        setGeneStrinng();
//        this.add(geneString);
    }
    
    private void startComponents()    
    {
        varOptions= new JLabel("Variables Options");
        varOptions.setFont(titleFont);
        varOptions.setBounds(10, 10, 200, 30);
        this.add(varOptions);
        
        Rectangle optionsBounds=varOptions.getBounds();
        
        xLabel= new JLabel("X");
        xLabel.setFont(titleFont);
        xLabel.setForeground(Color.GREEN);
        xLabel.setBounds(optionsBounds.x, optionsBounds.y+30, 20, 20);
        this.add(xLabel);
        
        xMin= new JLabel("X min:");
        xMin.setBounds(optionsBounds.x+20, optionsBounds.y+30, 40, 20);
        this.add(xMin);
        
        xMinField= new JFormattedTextField(amountFormat);
        xMinField.setBounds(optionsBounds.x+60, optionsBounds.y+30, 30, 20);
        xMinField.setValue(x);
        xMinField.addPropertyChangeListener(this);
        this.add(xMinField);
        
        xMax=new JLabel("X max:");
        xMax.setBounds(optionsBounds.x+95, optionsBounds.y+30, 40, 20);
        this.add(xMax);
        
        xMaxField= new JFormattedTextField(amountFormat);
        xMaxField.setBounds(optionsBounds.x+135, optionsBounds.y+30, 30, 20);
        xMaxField.setValue(X);
        xMaxField.addPropertyChangeListener(this);
        this.add(xMaxField);
        
        xp= new JLabel("Decimal cases:");
        xp.setBounds(optionsBounds.x+170, optionsBounds.y+30, 100, 20);
        this.add(xp);
        
        xpField= new JFormattedTextField(amountFormat);
        xpField.setBounds(optionsBounds.x+260, optionsBounds.y+30, 30, 20);
        xpField.setValue(xC);
        xpField.addPropertyChangeListener(this);
        this.add(xpField);
        
        yLabel= new JLabel("Y");
        yLabel.setFont(titleFont);
        yLabel.setForeground(Color.RED);
        yLabel.setBounds(optionsBounds.x, optionsBounds.y+60, 20, 20);
        this.add(yLabel);
        
        yMin= new JLabel("X min:");
        yMin.setBounds(optionsBounds.x+20, optionsBounds.y+60, 40, 20);
        this.add(yMin);
        
        yMinField= new JFormattedTextField(amountFormat);
        yMinField.setBounds(optionsBounds.x+60, optionsBounds.y+60, 30, 20);
        yMinField.setValue(y);
        yMinField.addPropertyChangeListener(this);
        this.add(yMinField);
        
        yMax=new JLabel("X max:");
        yMax.setBounds(optionsBounds.x+95, optionsBounds.y+60, 40, 20);
        this.add(yMax);
        
        yMaxField= new JFormattedTextField(amountFormat);
        yMaxField.setBounds(optionsBounds.x+135, optionsBounds.y+60, 30, 20);
        yMaxField.setValue(Y);
        yMaxField.addPropertyChangeListener(this);
        this.add(yMaxField);
        
        yp= new JLabel("Decimal cases:");
        yp.setBounds(optionsBounds.x+170, optionsBounds.y+60, 100, 20);
        this.add(yp);
        
        ypField= new JFormattedTextField(amountFormat);
        ypField.setBounds(optionsBounds.x+260, optionsBounds.y+60, 30, 20);
        ypField.setValue(yC);
        ypField.addPropertyChangeListener(this);
        this.add(ypField);
        
           
        geneString =new JTextPane();
        geneString.setBackground(Color.DARK_GRAY);
        //geneString.setBounds(10, 130, 300, 40);
        geneString.setFont(new Font("Serif", Font.BOLD, 14));
        doc = geneString.getStyledDocument();
        style = geneString.addStyle("I'm a Style", null);
        //this.add(geneString);
        
        JScrollPane scrollOutput = new JScrollPane(geneString);
        scrollOutput.setBounds(10,130,300,50);
        this.add(scrollOutput);
        
        Rectangle stringBounds= scrollOutput.getBounds();
        
        genLabel= new JLabel("Gene String Example");
        genLabel.setFont(titleFont);
        genLabel.setBounds(stringBounds.x, stringBounds.y-30, stringBounds.width, 30);
        this.add(genLabel);
        
        
        sizeLabel= new JLabel ("Gene String size: X("+myGA.getVariables()[0].getBitSize()+") + Y("
        +myGA.getVariables()[1].getBitSize()+") = "+myGA.getGeneLength());
        sizeLabel.setBounds(stringBounds.x,stringBounds.y+50,stringBounds.width,30);
        this.add(sizeLabel);
    }
    
    public void setGeneStrinng()
    {
//        float r,g,b;
        Random rand = new Random();
        String varString[]= new String[myGA.getVariables().length];
        String alphabet="01";
        Color colors[]= new Color[myGA.getVariables().length];
        
        colors[0]= Color.GREEN;
        colors[1]= Color.RED;

        //CLEAR DOC
        geneString.setText("");
            for(int i=0;i<myGA.getVariables().length;i++)
            {
                StyleConstants.setForeground(style, colors[i]);
                StringBuilder sb = new StringBuilder(myGA.getVariables()[i].getBitSize());
                for( int j = 0; j < myGA.getVariables()[i].getBitSize(); j++ ) 
                {
                    sb.append( alphabet.charAt( rand.nextInt(alphabet.length())));
                }


                 try {
                     doc.insertString(doc.getLength(), sb.toString(),style);
                 } catch (BadLocationException ex) {
                     Logger.getLogger(VariablesPane.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) 
    {
        Object source = pce.getSource();
        if(source==xMinField || source==xMaxField || source==xpField)
        {      
            x=((Number)xMinField.getValue()).floatValue();
            X=((Number)xMaxField.getValue()).floatValue();
            xC=((Number)xpField.getValue()).intValue();
//         System.out.println("x: "+x+" X: "+X+"xC: "+xC);
             //VALIDATE
            
            myGA.getVariables()[0].setParameters(x,X, xC);
            setGeneStrinng();
            myGA.upDateVariables();
            sizeLabel.setText("Gene String size: X("+myGA.getVariables()[0].getBitSize()+") + Y("
        +myGA.getVariables()[1].getBitSize()+") = "+myGA.getGeneLength());
        }
        else if(source==yMinField || source==yMaxField || source==ypField)
        {
            y=((Number)yMinField.getValue()).floatValue();
            Y=((Number)yMaxField.getValue()).floatValue();
            yC=((Number)ypField.getValue()).intValue();
             //VALIDATE       
            if(yC<1)
            {
                
            }
//            System.out.println("y: "+y+" Y: "+Y+"yC: "+yC);
            myGA.getVariables()[1].setParameters(y,Y, yC);
            
            setGeneStrinng();
            myGA.upDateVariables();
             sizeLabel.setText("Gene String size: X("+myGA.getVariables()[0].getBitSize()+") + Y("
        +myGA.getVariables()[1].getBitSize()+") = "+myGA.getGeneLength());
        }

    }
    
}
