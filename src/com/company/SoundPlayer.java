package com.company;

import javafx.scene.paint.Color;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class SoundPlayer
{

    private static BufferedImage picture;

    public static void main(String []args)
    {
        MainFrame frame = new MainFrame();
    }

    public void playSound(BufferedImage p, JFrame jf)
    {
        BufferedImage before = p;
        BufferedImage picture = Scalr.resize(before, Scalr.Method.BALANCED, 75, 75);

        int piano = 0; // 0 is a piano, 9 is percussion, other channels are for other instruments
        int drums = 9;
        int guitarJazz = 5;
        SoundMaker soundMaker = new SoundMaker();
        soundMaker.AssignHashMap();


        int volume = 100;
        int duration = 200; // in milliseconds



        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();

            // --------------------------------------
            // Play a few notes.
            // The two arguments to the noteOn() method are:
            // "MIDI note number" (pitch of the note),
            // and "velocity" (i.e., volume, or intensity).
            // Each of these arguments is between 0 and 127.

            //System.out.println(picture.getHeight());

            for (int i= 0 ; i < picture.getWidth() ;i++)
            {
                for(int j = 0; j < picture.getHeight(); j++) {



                    int pixel = picture.getRGB(i, j);
                    soundMaker.makeNote(pixel);

                    picture.setRGB(i,j,0X00FF00);
                    jf.validate();

                    soundMaker.tostring();

                    channels[piano].noteOn(soundMaker.getNote(), soundMaker.getVolume());
                    channels[piano].noteOn(soundMaker.getHarmonic(soundMaker.getNote()),soundMaker.getVolume());
                    channels[piano].noteOn(soundMaker.getHarmonic2(soundMaker.getNote()),soundMaker.getVolume());

                    Thread.sleep(soundMaker.getLength());

                    channels[piano].noteOff(soundMaker.getNote());
                    channels[piano].noteOff(soundMaker.getHarmonic(soundMaker.getNote()));
                    channels[piano].noteOff(soundMaker.getHarmonic2(soundMaker.getNote()));


                    //picture.setRGB(i,j, );


                }

            }

            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

