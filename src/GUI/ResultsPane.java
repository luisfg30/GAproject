/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Main.Stats;
import Main.Stats.Solution;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author luis
 */
public class ResultsPane extends JPanel {
    
    private Stats myStats=Stats.getInstance();
    private JTable table;
    private JScrollPane scroll;
    private ResultsTableModel model;
    private JTextArea outputArea;
    private JScrollPane scrollOutput;
    private DecimalFormat df = new DecimalFormat("#000.0000000");
    
    public ResultsPane()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.red);
        this.startComponents();
    }
    
    private void startComponents()
    {
        outputArea= new JTextArea();
        outputArea.setBackground(Color.LIGHT_GRAY);
        outputArea.setEditable(false);
        outputArea.setRows(18);
        
        scrollOutput = new JScrollPane(outputArea);
        this.add(scrollOutput,BorderLayout.CENTER);
        
        outputArea.append("Execution\tEval.Function\tX\tY\tGeneLenght\tGenetic Operator(%)\tMutation(%)\tElite"
                + "\tOptimality\t\tAccuracy\t\tSensivity\n"
        );
       
        model = new ResultsTableModel(myStats.getArraySolutions());
        table= new JTable();
        table.setModel(model);
        table.setFillsViewportHeight(true);
        scroll= new JScrollPane(table);
        scroll.setBounds(10, 10,900 ,600 );
        this.add(scroll,BorderLayout.PAGE_END);
        
    }
    
    public void updateData()
    {
        table.updateUI();
    }
    
    public void addExecutionDetails(int index)
    {
        outputArea.append("\n["+index+
        "]\t"+myStats.getArraySolutions().get(index).evaluationFunction+
        "\t["+myStats.getArraySolutions().get(index).xMin+
             ","+myStats.getArraySolutions().get(index).xMax+"] "+
                myStats.getArraySolutions().get(index).xCases+
        "\t["+myStats.getArraySolutions().get(index).yMin+
             " , "+myStats.getArraySolutions().get(index).yMax+"] "+
                myStats.getArraySolutions().get(index).yCases+     
        "\t"+myStats.getArraySolutions().get(index).geneLenght+        
        "\t"+myStats.getArraySolutions().get(index).geneticOperator+
        "("+myStats.getArraySolutions().get(index).operatorRate+        
        ")\t"+myStats.getArraySolutions().get(index).usingMutation+
        "("+myStats.getArraySolutions().get(index).mutationRate+
        ")\t"+myStats.getArraySolutions().get(index).usingElite+
        "\t"+myStats.getArraySolutions().get(index).optimality+
        "\t"+myStats.getArraySolutions().get(index).accuracy+
        "\t"+myStats.getArraySolutions().get(index).sensivity        
        );
        
    }
    
    
    private class ResultsTableModel implements TableModel
    {
        private ArrayList<Solution> results;
        private String[] columnNames={"Execution","Genereations","Pop. Size","Best Fitness","X","Y",
            "Z","Fitness Convergence","Elite Convergence","Avg Convergence","Time(10^-9 s)"};

        public ResultsTableModel(ArrayList<Solution> array)
        {
            results=array;
        }
        @Override
        public int getRowCount() {
            return results.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int i) {
            return columnNames[i];
        }

        @Override
        public Class<?> getColumnClass(int i) {
                return String.class;
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            Solution s= results.get(i);
            
            if(i1==0){
                return i;
            }
            else if(i1==1){
                return s.totalGen;
            }
            else if(i1==2){
                return s.popSize;
            }
            else if(i1==3){
                return s.fitness;
            }
            else if(i1==4){
                return s.x;
            }
            else if(i1==5){
                return s.y;
            }
            else if(i1==6){
                return s.z;
            }
            else if(i1==7){
                return s.Fconvergence;
            }
            else if(i1==8){
                return s.eliteConvergence;
            }
            else if(i1==9){
                return s.avgconvergence;
            }
            else{
                return s.time;
            }
        }

        @Override
        public void setValueAt(Object o, int i, int i1) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addTableModelListener(TableModelListener tl) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeTableModelListener(TableModelListener tl) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
