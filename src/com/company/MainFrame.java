package com.company;

import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame {
    public String imagePath, musicPath;
    private JFrame frame;
    private JTextField field, musicField;
    private JPanel north, center, south;
    private JButton start, browse, convertToImage, browseMusic;

    public MainFrame()
    {
        imagePath ="";
        musicPath = "";
        frame = new JFrame();frame.setLayout(new BorderLayout());
        convertToImage = new JButton("Convert music");
        musicField = new JTextField(20);
        browse = new JButton("Browse image");
        field = new JTextField(20);

        start = new JButton("Start the music");
        browseMusic = new JButton("Browse Music");


        addNorth();
        addCenter();
       // addSouth();
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    public void addNorth(){
        north = new JPanel(new BorderLayout());

        browse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();
                    field.setText(selectedFile.getAbsolutePath());
                    refresh(frame);
                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SoundPlayer soundPlayer = new SoundPlayer();
                try {
                    BufferedImage p = ImageIO.read(new File(imagePath));
                    soundPlayer.playSound(p,frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(north, BorderLayout.NORTH);
        north.add(browse, BorderLayout.WEST);
        north.add(field, BorderLayout.CENTER);
        north.add(start, BorderLayout.EAST);
    }

    public void addCenter(){
        center = new JPanel();
        center.add(new JLabel(new ImageIcon(imagePath)));
        frame.add(center, BorderLayout.CENTER);
    }

    public void addSouth(){
        south = new JPanel(new BorderLayout());
        browseMusic.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    musicPath = selectedFile.getAbsolutePath();
                    musicField.setText(selectedFile.getAbsolutePath());
                    refresh(frame);
                }
            }
        });

        convertToImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               Reverse r = new Reverse();
               r.playNoteFromMidi(south, frame);
                BufferedImage temp = r.getImg();
                ImageIcon temp3 = new ImageIcon(temp);
               south.add(new JLabel(temp3), BorderLayout.CENTER);
                refresh(frame);
            }
        });

        south.add(browseMusic, BorderLayout.WEST);
        south.add(musicField, BorderLayout.CENTER);
        south.add(convertToImage, BorderLayout.EAST);
        frame.add(south);
    }

    public void refresh(JFrame frame){
        frame.remove(north);
        frame.remove(center);
        //frame.remove(south);
        addNorth();
        addCenter();
      //  addSouth();
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    public JFrame getFrame()
    {
        return frame;
    }
    public String getMusicPath(){return musicPath;}
}