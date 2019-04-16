/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

import javafx.scene.paint.Color;
import javax.sound.midi.ShortMessage;

/**
 * Enumerates the instruments playable in the TuneComposer application
 * @author Ian Hawkins, Ian Stewart, Melissa Kohl, Angie Mead
 */
public enum Instrument {
        PIANO         (1,  0, "Piano",       "piano",        Color.CRIMSON),
        HARPSICHORD   (7,  1, "Harpsichord", "harpsichord",  Color.CRIMSON),
        MARIMBA       (13, 2, "Marimba",     "marimba",      Color.DARKORANGE),
        CHURCH_ORGAN  (20, 3, "Organ" ,      "church-organ", Color.GOLD),
        ACCORDION     (22, 4, "Accordian",   "accordion",    Color.GREEN),
        GUITAR        (25, 5, "Guitar",      "guitar",       Color.BLUE),
        VIOLIN        (41, 6, "Violin",      "violin",       Color.DARKVIOLET), 
        FRENCH_HORN   (61, 7, "French Horn", "french-horn",  Color.PURPLE);
        
        private final int midiProgram;
        private final int channel;
        private final String displayName;
        private final String styleClassName;
        private final Color displayColor;
        
        Instrument(int midiProgram, int channel, String displayName,
                     String styleClassName, Color displayColor){
            this.midiProgram = midiProgram;
            this.channel = channel;
            this.displayName = displayName;
            this.styleClassName = styleClassName;
            this.displayColor = displayColor;
        }
        
        public int getMidiProgram() {
            return midiProgram;
        }

        public int getChannel() {
            return channel;
        }

        public String getDisplayName() {
            return displayName;
        }
        
        public String getStyleClassName() {
            return styleClassName;
        }       

        public Color getDisplayColor() {
            return displayColor;
        }
        
        public static void addAll(MidiPlayer player) {
            for (Instrument inst : Instrument.values()) {
                player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + inst.getChannel(), 
                                    inst.getMidiProgram()-1, 
                                    0, 0, 0);
            }
        }

//        /**
//         * Override the built-in method from the Enum class
//         * @return Lower case string of instrument name with dashes as spaces
//         */
//        @Override
//        public String toString() {
//            switch(this) {
//                case PIANO:         return "piano";
//                case HARPSICHORD:   return "harpsichord";
//                case MARIMBA:       return "marimba";
//                case CHURCH_ORGAN:  return "church-organ";
//                case ACCORDION:     return "accordion";
//                case GUITAR:        return "guitar";
//                case VIOLIN:        return "violin";
//                case FRENCH_HORN:   return "french-horn";
//                default: throw new IllegalArgumentException();
//            }
//        }
    }
