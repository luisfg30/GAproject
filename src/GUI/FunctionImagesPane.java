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
    
    
//    public void loadImages(String mainPath, int imgNumber)
//    {
//        images= new JLabel[imgNumber];
//        String path;
//        for(int i=0;i<imgNumber;i++)
//        {
//            if(i<10)
//            {
//                path=mainPath.concat("0").concat((String.valueOf(i))).concat(".jpg");
//            }
//            else
//            {
//                path=mainPath.concat((String.valueOf(i))).concat(".jpg");
//            }
//            System.out.println(path);
//            images[i] = new JLabel();
//            images[i].setIcon(new ImageIcon(getClass().getResource(path)));
//            images[i].setBounds(0,0,this.getBounds().width,this.getBounds().height);
//            this.add(images[i]);
//        }
//    }
    
//    public void setImage(int index)
//    {
//        hideImages();
//        images[index].setVisible(true);
//    }
//    
//    private void hideImages()
//    {
//        for(int i= images.length;i<images.length;i++)
//        {
//            images[i].setVisible(false);
//        }
//    }
}
