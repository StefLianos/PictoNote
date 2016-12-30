package com.company;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.midi.*;
import javax.swing.*;

public class Reverse
{

    private static final int NOTE_ON = 0x90;
    private static final int NOTE_OFF = 0x80;
    private static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private HashMap<Integer,Integer> noteMap;
    private HashMap<Integer,Integer> octaveMap;
    private int currentWidth = 0;
    private int currentHeidth = 0;

    public BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

    public void playNoteFromMidi(JPanel jp, JFrame frame)
    {
        noteMap = new HashMap<Integer,Integer>();
        octaveMap = new HashMap<Integer,Integer>();
        noteMap.put(0,21);
        noteMap.put(1,42);
        noteMap.put(2,64);
        noteMap.put(3,86);
        noteMap.put(4,107);
        noteMap.put(5,128);
        noteMap.put(6,149);
        noteMap.put(7,171);
        noteMap.put(8,192);
        noteMap.put(9,213);
        noteMap.put(10,234);
        noteMap.put(11,255);

        octaveMap.put(0,25);
        octaveMap.put(1,50);
        octaveMap.put(2,75);
        octaveMap.put(3,100);
        octaveMap.put(4,125);
        octaveMap.put(5,150);
        octaveMap.put(6,175);
        octaveMap.put(7,200);
        octaveMap.put(8,225);
        octaveMap.put(9,250);




        Sequence sequence = null;
        try {
            MainFrame mf = new MainFrame();
            sequence = MidiSystem.getSequence(new File(mf.getMusicPath()));
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        jp.add(new JLabel(new ImageIcon(img)));


        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();


            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();


                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");


                    if (sm.getCommand() == NOTE_ON) {

                        int key = sm.getData1();

                        int octave = (key / 12)-1;
                        int note = key % 12;

                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);

                        int pixelB = noteMap.get(note);
                        int pixelG = octaveMap.get(octave);

                        double tempG = (pixelG+1)*0.59;
                        double tempB = (pixelB+1)*0.11;

                        double tempR = (((velocity-tempG-tempB)/0.3)*255)+1;
                        int pixelR = (int) tempR;

                        int col = (pixelR << 16) | (pixelG << 8) | pixelB;

                        if(currentWidth<256)
                        {
                            img.setRGB(currentWidth,currentHeidth,col);
                            currentWidth+=1;

                        }
                        else
                        {
                            currentWidth = 0;
                            currentHeidth +=1;
                            img.setRGB(currentWidth,currentHeidth,col);
                        }


                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }

    }
    public BufferedImage getImg()
    {
        return img;
    }
}