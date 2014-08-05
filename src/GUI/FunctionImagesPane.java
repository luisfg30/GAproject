/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author luis
 */
public class FunctionImagesPane extends JPanel 
{
    private BufferedImage imgs[], currentImage;
    
     FunctionImagesPane()
    {
        this.setLayout(null);
        this.setBackground(Color.yellow);
    }        
    
     @Override
     protected void paintComponent(Graphics g) 
     {
      super.paintComponent(g);
      g.drawImage(currentImage, 0, 0, null);
     }
     
    public void setImages(BufferedImage im[])
    {
        imgs=new BufferedImage[im.length];
        imgs=im;
    }
    
    public void setCurrentImage(int index)
    {
        currentImage=imgs[index];
         repaint();
    }
}
