
package tunecomposer;

import javafx.scene.paint.Color;
import javax.sound.midi.ShortMessage;

/**
 * Enumerates the instruments playable in the TuneComposer application
 * @author Ian Stewart, Gavin James/Beckham, Nathaniel Larson, Paul Milloy
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
        
        /**
         * Create an Instrument enumeration.
         * @param midiProgram Midi Instrument value
         * @param channel Channel to be added to based on Instrument
         * @param displayName Name of Instrument
         * @param styleClassName Lower case with '-' to apply styleclass
         * @param displayColor Not currently working
         */
        Instrument(int midiProgram, int channel, String displayName,
                     String styleClassName, Color displayColor){
            this.midiProgram = midiProgram;
            this.channel = channel;
            this.displayName = displayName;
            this.styleClassName = styleClassName;
            this.displayColor = displayColor;
        }
        
        /**
         * Returns midiProgram value.
         * @return midiProgram
         */
        public int getMidiProgram() {
            return midiProgram;
        }
        
        /**
         * Returns channel value.
         * @return channel
         */
        public int getChannel() {
            return channel;
        }
        
        /**
         * Returns display name value.
         * @return displayName
         */
        public String getDisplayName() {
            return displayName;
        }
        
        /**
         * Returns style class name value.
         * @return styleClassName
         */
        public String getStyleClassName() {
            return styleClassName;
        }       
        
        /**
         * Returns display color value.
         * @return displayColor
         */
        public Color getDisplayColor() {
            return displayColor;
        }
        
        /**
         * Adds all of the instrument tracks to the MidiPlayer.
         */
        public static void addAll(MidiPlayer player) {
            for (Instrument inst : Instrument.values()) {
                player.addMidiEvent(ShortMessage.PROGRAM_CHANGE + inst.getChannel(), 
                                    inst.getMidiProgram()-1, 
                                    0, 0, 0);
            }
        }

    }
