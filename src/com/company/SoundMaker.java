package com.company;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/*
 * Created by Stef on 12/12/2015.
 */
public class SoundMaker {

    private BufferedImage picture;
    private static HashMap<Integer ,String > NoteMap= new HashMap<>();
    private static HashMap<Integer,Integer> harmonicsMap = new HashMap<>() ;
    private static HashMap<Integer,Integer> harmonicsMap2 = new HashMap<>();

    private long time;
    private double length;
    private int note;
    private int octave;

    private int loudness;
    private double volume;


    public int getVolume()
    {
        return loudness;
    }

    public int getOctave(){return octave;}

    public int getNote()
    {
        return note;
    }

    public long getLength()
    {
        return time;
    }

    public void AssignHashMap()
    {
        NoteMap.put(0,"C");
        NoteMap.put(1,"C#");
        NoteMap.put(2,"D");
        NoteMap.put(3,"D#");
        NoteMap.put(4,"E");
        NoteMap.put(5,"F");
        NoteMap.put(6,"F#");
        NoteMap.put(7,"G");
        NoteMap.put(8,"G#");
        NoteMap.put(9,"A");
        NoteMap.put(10,"A#");
        NoteMap.put(11,"B");
    }

    public void makeNote(int pixel)
    {

        int R = (pixel >> 16) & 0xff;
        length = (( R + 1 )/255.0f)*800;
        time = ((long) length );
        if(time == -1){time =1;}

        int G = (pixel >> 8) & 0xff;
        int B = pixel & 0xff;
        int averageGBValue = (G+B)/2;

        note = (B+G)/(255/127);
        if(note>80){note = note/2;}
        if(note>80){note = note/2;}

        volume = ((R+1 / 255.0f) * 0.3 + (G+1 / 255.0f) * 0.59 + (B+1 / 255.0f) * 0.11);
        loudness = (int) volume;

        octave = (note-(note%12))/12;
    }


    public void tostring()
    {
        System.out.println(note+" "+loudness+" "+time+" "+NoteMap.get(note%12)+octave);
    }

    public int getHarmonic(int note)
    {
        for(int i = 0 ;i<124 ; i= i+12)
        {
            if(i<12)
            {
                harmonicsMap.put(0,0);
                harmonicsMap.put(1,1);
                harmonicsMap.put(2,2);
                harmonicsMap.put(3,3);
                harmonicsMap.put(4,4);
                harmonicsMap.put(5,5);
                harmonicsMap.put(6,6);
                harmonicsMap.put(7,7);
                harmonicsMap.put(8,8);
                harmonicsMap.put(9,9);
                harmonicsMap.put(10,10);
                harmonicsMap.put(11,11);
            }
            else
            {
//                harmonicsMap.put(i, i - 12);
//                harmonicsMap.put(i + 1, i - 4 +1);
//                harmonicsMap.put(i + 2,i-7 +2);
//                harmonicsMap.put(i + 3,i-4 +3);
//                harmonicsMap.put(i + 4,i-16 +4);
//                harmonicsMap.put(i + 5,i-12 +5);
//                harmonicsMap.put(i + 6,i-16 +6);
//                harmonicsMap.put(i + 7,i-12 +7);
//                harmonicsMap.put(i + 8,i-9 +8);
//                harmonicsMap.put(i + 9,i-16 +9);
//                harmonicsMap.put(i + 10,i-19 +10);
//                harmonicsMap.put(i + 11,i-16 +11);
                harmonicsMap.put(i, i - 5);
                harmonicsMap.put(i + 1, i - 6 +1);
                harmonicsMap.put(i + 2,i-3 +2);
                harmonicsMap.put(i + 3,i-6 +3);
                harmonicsMap.put(i + 4,i-9 +4);
                harmonicsMap.put(i + 5,i-17 +5);
                harmonicsMap.put(i + 6,i-9 +6);
                harmonicsMap.put(i + 7,i-17 +7);
                harmonicsMap.put(i + 8,i-9 +8);
                harmonicsMap.put(i + 9,i-21 +9);
                harmonicsMap.put(i + 10,i-18 +10);
                harmonicsMap.put(i + 11,i-21 +11);


            }


        }
        System.out.println(harmonicsMap.get(note));
        return harmonicsMap.get(note);

    }

    public int getHarmonic2(int note2)
    {
        for(int i = 0 ;i<124 ; i= i+12)
        {
            if(i<12)
            {
                harmonicsMap.put(0,0);
                harmonicsMap.put(1,1);
                harmonicsMap.put(2,2);
                harmonicsMap.put(3,3);
                harmonicsMap.put(4,4);
                harmonicsMap.put(5,5);
                harmonicsMap.put(6,6);
                harmonicsMap.put(7,7);
                harmonicsMap.put(8,8);
                harmonicsMap.put(9,9);
                harmonicsMap.put(10,10);
                harmonicsMap.put(11,11);
            }
            else
            {
                harmonicsMap.put(i, i - 8);
                harmonicsMap.put(i + 1, i - 9 +1);
                harmonicsMap.put(i + 2,i-7 +2);
                harmonicsMap.put(i + 3,i-4 +3);
                harmonicsMap.put(i + 4,i-17 +4);
                harmonicsMap.put(i + 5,i-8 +5);
                harmonicsMap.put(i + 6,i-16 +6);
                harmonicsMap.put(i + 7,i-8 +7);
                harmonicsMap.put(i + 8,i-16 +8);
                harmonicsMap.put(i + 9,i-15 +9);
                harmonicsMap.put(i + 10,i-21 +10);
                harmonicsMap.put(i + 11,i-16 +11);

            }

        }
        System.out.println(harmonicsMap.get(note2));
        return harmonicsMap.get(note2);
    }
}


