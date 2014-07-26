/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author luis
 */
public class StatsPane extends JPanel{
    
    private JLabel title;
    Font titleFont = new Font("Serif", Font.BOLD, 18);
    
    public StatsPane()
    {
        this.setLayout(null);
        this.setBackground(Color.yellow);
        startComponents();
    }
    public void startComponents()
    {
        title=new JLabel("Statistics");
        title.setFont(titleFont);
        title.setBounds(10, 0, 100, 30);
        this.add(title);
        
    }
    
}
