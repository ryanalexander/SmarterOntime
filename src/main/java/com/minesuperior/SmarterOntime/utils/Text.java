/*
 *
 *
 *  © Stelch Software 2020, distribution is strictly prohibited
 *  Blockcade is a company of Stelch Software
 *
 *  Changes to this file must be documented on push.
 *  Unauthorised changes to this file are prohibited.
 *
 *  @author Ryan W
 * @since (DD/MM/YYYY) 18/1/2020
 */

package com.minesuperior.SmarterOntime.utils;

public class Text {

    /**
     * Simple function for formatting chatcolors in messages
     * @param str Input non-formatted string
     * @return Formatted string
     */
    public static String format(String str) { return (str).replaceAll("&","§"); }
}
