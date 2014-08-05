package Main;

/*
 * To change this license header, choose License Headers in Proframeect Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import GUI.GAFrame;
import GUI.VariablesPane;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JApplet;


/**
 *
 * @author luis
 */
public class NewApplet extends JApplet {

    private GAFrame frame;
    private BufferedImage plotImgs[],expImgs[];
    
    @Override
    public void init() 
    {
        plotImgs= new BufferedImage[1];
        expImgs= new BufferedImage[1];
        // TODO start asynchronous download of heavy resources
        //load images here fuck 
        this.loadImages("Images/plot/", 1,plotImgs);
        this.loadImages("Images/expressions/", 1, expImgs);
 
        VariablesPane v= new VariablesPane(plotImgs,expImgs);

    frame = new GAFrame(v);

    }
    
    public void loadImages(String mainPath, int imgNumber,BufferedImage imgs[])
    {
        
        String path;
        for(int i=0;i<imgNumber;i++)
        {
            if(i<10)
            {
                path=mainPath.concat("0").concat((String.valueOf(i))).concat(".jpg");
            }
            else
            {
                path=mainPath.concat((String.valueOf(i))).concat(".jpg");
            }
            //System.out.println(path);
             try {
             URL url = new URL(getCodeBase(), path);
             imgs[i] = ImageIO.read(url);
             //imgs[i] = ImageIO.read(new File(path));
             }
             catch (IOException e) {    
                 System.out.println("Trouble reading from the file: " + e.getMessage());
             }
        }
    }
 


    // TODO overwrite start(), stop() and destroy() methods
    
   

}
