/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;

/**
 *
 * @author stewarim
 */
public enum Instrument {
        PIANO,
        HARPSICHORD,
        MARIMBA,
        CHURCH_ORGAN,
        ACCORDION,
        GUITAR,
        VIOLIN,
        FRENCH_HORN;

        @Override
        public String toString() {
            switch(this) {
                case PIANO:         return "piano";
                case HARPSICHORD:    return "harpsichord";
                case MARIMBA:       return "marimba";
                case CHURCH_ORGAN:  return "church-organ";
                case ACCORDION:     return "accordion";
                case GUITAR:        return "guitar";
                case VIOLIN:        return "violin";
                case FRENCH_HORN:   return "french-horn";
                default: throw new IllegalArgumentException();
            }
        }
    }
