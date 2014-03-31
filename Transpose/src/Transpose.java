import java.util.*;
import java.text.*;

public class Transpose
{
   // -------  main --------------
   public static void main(String[] args)
   {
      Fundamental diatonic = new Fundamental();

      System.out.println("diatonic chords\n" + 
         diatonic.deccodeDiatonicTriads("f#")+ "\n");

      System.out.println("decode scale:\n" + diatonic.decodeMajScale("db") 
            + "\n\n");

      System.out.println("all keys\n" + diatonic.displayAllKeys());

      System.out.println("\n diatonic chords, one key\n" + 
        diatonic.deccodeDiatonicTriads("b"));

      System.out.println("\nAll chords\n" + 
         diatonic.displayAllDiatonicChords());
   }
}

class Fundamental
{
   //Class constants
   private final static int MAX_SIZE = 12;
   String key, transpoKey;
   int k, i, keyNum;
   boolean errorFlag;
   //determines which array to build the scale from
   boolean sharp, flat, dblSharp;

   public Fundamental()
   {
      key = "C";
      transpoKey = "C";
      reset();
   }

   //triads
   public Fundamental(String key)
   {
      this.key = key;
      this.transpoKey = "C";
      reset();
   }

   public Fundamental(String key, String transpoKey)
   {
      setKey(key);
      reset();
   }

   //for C and keys using sharps (through B)
   public static String[] chromaticS = {"C", "C#", "D", "D#", "E", "F",
      "F#", "G", "G#", "A", "A#", "B","C", "C#", "D", "D#", "E", "F",
      "F#", "G", "G#", "A", "A#", "B"};

   //Key of B special case for including E#
   public static String[] fSharp = {"F#", "G#", "A#", "B","C#", "D#", "E#"};

   //for keys using flats
   public static String[] chromaticF = {"C", "Db", "D", "Eb", "E", "F",
      "Gb", "G", "Ab", "A", "Bb", "B","C", "Db", "D", "Eb", "E", "F",
      "Gb", "G", "Ab", "A", "Bb", "B"};

   public static String[] chordType = {"", "min", "dim", "aug", "Maj7", "min7",
      "dim7", "aug7", "7", "9", "sus", "7#9", "min7b5"};

   public static String[] circleFifths = {"C", "G", "D", "A", "E", "B", "F#", 
      "Db", "Ab", "Eb", "Bb", "F"};

   public boolean setKey(String key)
   {
      String upVal;            // for upcasing string

      // convert to uppercase
      if(key.length() > 1)
      {
         upVal = Character.toString(Character.toUpperCase(key.charAt(0))) 
               + Character.toString(key.charAt(1));
         this.key = upVal;
      }
      else
      {
         upVal = key.toUpperCase();
         this.key = upVal;
      }

      if ( !isValidKey(key))
      {
         errorFlag = true;
         return false;
      }
      // else implied
      errorFlag = false;
      //      this.key = upVal;
      return true;
   }

   // helpers
   private boolean isValidKey(String key)
   {
      String upVal;

      // convert to uppercase
      if(key.length() > 1)
         upVal = Character.toString(Character.toUpperCase(key.charAt(0))) 
         + Character.toString(key.charAt(1));
      else
         upVal = key.toUpperCase();

      // check for validity
      if (upVal.equals("C") || upVal.equals("G") || upVal.equals("D") 
            || upVal.equals("A") || upVal.equals("E") || upVal.equals("B"))
      {
         sharp = true;
         return true;
      }
      if (upVal.equals("F") || upVal.equals("Bb") || upVal.equals("Eb") 
            || upVal.equals("Ab") || upVal.equals("Db") || upVal.equals("Gb"))
      {
         flat = true;
         return true;
      }
      if(upVal.equals("F#"))
      {
         dblSharp = true;
         return true;
      }
      else
         return false;
   }

   // accessors
   public String getKey(){ return key; }

   public String getTranspoKey(){ return transpoKey; }//future use

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   private void reset()
   {
      sharp = false;
      flat = false;
      dblSharp = false;
   }

   private int tonicToInt(String key)
   {
      setKey(key);
      if(key.equals("C"))
         return 0;
      if(key.equals("C#"))
         return 1;
      if(key.equals("Db"))
         return 1;
      if(key.equals("D"))
         return 2;
      if(key.equals("D#"))
         return 3;
      if(key.equals("Eb"))
         return 3;
      if(key.equals("E"))
         return 4;
      if(key.equals("F"))
         return 5;
      if(key.equals("F#"))
         return 6;
      if(key.equals("Gb"))
         return 6;
      if(key.equals("G"))
         return 7;
      if(key.equals("G#"))
         return 8;
      if(key.equals("Ab"))
         return 8;
      if(key.equals("A"))
         return 9;
      if(key.equals("A#"))
         return 10;
      if(key.equals("Bb"))
         return 10;
      if(key.equals("B"))
         return 11;
      return 0;
   }

   //Gets key as an int for looping
   public String decodeDiatonicSharps(int tonic)
   {
      String chords = "";
      for(k = 0; k < MAX_SIZE; k++, tonic++)
      {
         if(k == 0 || k == 5 || k ==7)
            chords+= chromaticS[tonic] + chordType[0] + " ";
         if(k == 2 || k == 4 || k ==9)
            chords+= chromaticS[tonic] + chordType[1] + " ";
         if(k == 11)
            chords+= chromaticS[tonic] + chordType[2] + " ";
      }
      return chords;
   }
   public String decodeDiatonicFsharp()
   {
      String chords = "";

      for(k = 0; k < 7; k++)
      {
         if(k == 0 || k == 3 || k == 4)
            chords += fSharp[k] + chordType[0] + " ";
         if(k == 1 || k == 2 || k == 5)
            chords += fSharp[k] + chordType[1] + " ";
         if(k == 6)
            chords += fSharp[k] + chordType[2] + " ";
      }
      return chords;
   }

   public String decodeDiatonicFlats(int tonic)
   {
      String chords = "";
      for(k = 0; k < MAX_SIZE; k++, tonic++)
      {
         if(k == 0 || k == 5 || k ==7)
            chords+= chromaticF[tonic] + chordType[0] + " ";
         if(k == 2 || k == 4 || k == 9)
            chords+= chromaticF[tonic] + chordType[1] + " ";
         if(k == 11)
            chords+= chromaticF[tonic] + chordType[2] + " ";
      }
      return chords;
   }

   //Gets key as an String and generates a list of diatonic triads for a key
   public String deccodeDiatonicTriads(String key)
   {
      if(!setKey(key))
         return "Invalid Key";

      int temp;
      temp = tonicToInt(this.key);
      String chords = "";

      if(sharp)
      {
         for(k = 0; k < MAX_SIZE; k++, temp++)
         {
            if(k == 0 || k == 5 || k ==7)
               chords+= chromaticS[temp] + chordType[0] + " ";
            if(k == 2 || k == 4 || k ==9)
               chords+= chromaticS[temp] + chordType[1] + " ";
            if(k == 11)
               chords+= chromaticS[temp] + chordType[2] + " ";
         }
         return chords;
      }

      if(flat)
      {
         for(k = 0; k < MAX_SIZE; k++, temp++)
         {
            if(k == 0 || k == 5 || k ==7)
               chords+= chromaticF[temp] + chordType[0] + " ";
            if(k == 2 || k == 4 || k == 9)
               chords+= chromaticF[temp] + chordType[1] + " ";
            if(k == 11)
               chords+= chromaticF[temp] + chordType[2] + " ";
         }
         return chords;
      }
      if(dblSharp)
         for(k = 0; k < 7; k++)
         {
            if(k == 0 || k == 4 || k == 5)
               chords += fSharp[k] + chordType[0] + " ";
            if(k == 2 || k == 3 || k == 6)
               chords += fSharp[k] + chordType[1] + " ";
            if(k == 7)
               chords += fSharp[k] + chordType[2] + " ";
         }
      return chords;
   }

   //Gets key as a String from client
   public String decodeMajScale(String key)
   {
      if(!setKey(key))
         return "Invalid Key";

      int temp;
      temp = tonicToInt(this.key);
      String majKey = "";

      if(sharp)
      {
         for(k = 0; k < MAX_SIZE; k++, temp++)

            if(k == 0 || k == 2 || k == 4 || k == 5 || k == 7 || k == 9 
            || k == 11)
               majKey+= chromaticS[temp] + " ";
         return majKey;
      }
      if(flat)
      {
         for(k = 0; k < MAX_SIZE; k++, temp++)
            if(k == 0 || k == 2 || k == 4 || k == 5 || k == 7 || k == 9 
            || k == 11) 
               majKey+= chromaticF[temp] + " ";
         return majKey;
      }
      if(dblSharp)
         for(k = 0; k < 7; k++)
            majKey += fSharp[k] + " ";
      return majKey;
   }

   //Gets key as an int for looping
   public String decodeMajSharps(int tonic)
   {
      String majKey = "";
      for(k = 0; k < MAX_SIZE; k++, tonic++)
      {
         if(k == 0 || k == 2 || k == 4 || k == 5 || k == 7 || k == 9 || k == 11)
            majKey+= chromaticS[tonic] + " ";
      }
      reset();
      return majKey;
   }

   //Gets key as an int for looping
   public String decodeMajFlats(int tonic)
   {
      String majKey = "";
      for(k = 0; k < MAX_SIZE; k++, tonic++)
      {
         if(k == 0 || k == 2 || k == 4 || k == 5 || k == 7 || k == 9 || k == 11)
            majKey+= chromaticF[tonic] + " ";
      }
      reset();
      return majKey;
   }

   public String decodeMajFsharp()
   {
      String majKey = "";
      for(k = 0; k < 7; k++)
         majKey += fSharp[k] + " ";
      reset();
      return majKey;
   }

   public String displayAllDiatonicChords()
   {
      String allChords = "";
      for(int k = 0; k < circleFifths.length; k++)
      {
         if(k < 6)
            allChords += decodeDiatonicSharps(tonicToInt(circleFifths[k])) + 
            "\n\n";
         if (k == 7)
            allChords += decodeDiatonicFsharp() + "\n\n";
         if(k > 6 && k < circleFifths.length)
            allChords += decodeDiatonicFlats(tonicToInt(circleFifths[k])) + 
            "\n\n";
      }
      return allChords;
   }

   public String displayAllKeys()
   {
      String allKeys = "";
      for(int k = 0; k < circleFifths.length; k++)
      {
         if(k < 6)
            allKeys += decodeMajSharps(tonicToInt(circleFifths[k])) + "\n\n";
         if (k == 7)
            allKeys += decodeMajFsharp() + "\n\n";
         if(k > 6 && k < circleFifths.length)
            allKeys += decodeMajFlats(tonicToInt(circleFifths[k])) + "\n\n";
      }
      return allKeys;
   }
}
