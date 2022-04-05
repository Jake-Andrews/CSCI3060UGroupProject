package com.ot;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;


public class UserTest 
{
    //Create a temporary folder, used to create temporary useraccounts/rentalsfiles for testing
    //See: https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html 
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    
    //The method being tested formats the unit objects into strings that are written to files
    @Test
    public void checkWriteToRentalsFile() throws IOException {
        //Coverage for all loops not running
        //Username is 14 character, loop doesn't run. City is 25 characters, loop doesn't run. rental price is 5, loop doesn't run.
        Unit unit  = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.90f, "t", 0);
        //coverage for the loop on line 369 (username)
        Unit unit1 = new Unit("12345678", "ABCDEFGHIJKLM", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.90f, "t", 0);
        Unit unit2 = new Unit("12345678", "ABCDEFGHIJKL",  "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.90f, "t", 0);
        Unit unit3 = new Unit("12345678", "ABCDEFGHIJK",   "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.90f, "t", 0);
        //coverage for the loop on line 375 (city)
        Unit unit4 = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWX", 1, 999.90f, "t", 0);
        Unit unit5 = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVW",  1, 999.90f, "t", 0);
        Unit unit6 = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUV",   1, 999.90f, "t", 0);
        //coverage for the loop on line 393 (rentalprice) 
        Unit unit7 = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 99.90f, "t", 0);
        Unit unit8 = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 9.90f, "t", 0);
        Unit unit9 = new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, .90f, "t", 0);
        //if statement on line 385, every other test triggers true, remove trailingh numbers to make it false 
        Unit unit10= new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.9f, "t", 0);
        //if statement on line 398, t triggers it true. Trigger false (f)
        Unit unit11= new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.90f, "f", 0);
        //if statement on line 403, remaining nights (>9 and <100 = true). <9 = false. Trigger true (10)  
        Unit unit12= new Unit("12345678", "ABCDEFGHIJKLMN", "ABCDEFGHIJKLMNOPQRSTUVWXY", 1, 999.90f, "t", 10);

        //create temp files in tempFolder
        final File accountsFile = tempFolder.newFile("useraccounts.txt");
        final File rentalsFile = tempFolder.newFile("availablerentalsfile.txt");

        //creating a blank useraccounts.txt file and a blank rentals file
        List<String> lines = Arrays.asList("");
        Files.write(Paths.get(accountsFile.getName()), lines, StandardCharsets.UTF_8);
        List<String> lines1 = Arrays.asList("");
        Files.write(Paths.get(rentalsFile.getName()), lines1, StandardCharsets.UTF_8);

        User user = new User("USER", "AA", rentalsFile.getAbsolutePath(), rentalsFile.getAbsolutePath());
        System.out.println("Assert equals user.writeToRentalsFile.");

        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_t_00", user.writeToRentalsFile(unit));
        assertEquals("12345678_ABCDEFGHIJKLM__ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_t_00", user.writeToRentalsFile(unit1));
        assertEquals("12345678_ABCDEFGHIJKL___ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_t_00", user.writeToRentalsFile(unit2));
        assertEquals("12345678_ABCDEFGHIJK____ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_t_00", user.writeToRentalsFile(unit3));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWX__1_999.90_t_00", user.writeToRentalsFile(unit4));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVW___1_999.90_t_00", user.writeToRentalsFile(unit5));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUV____1_999.90_t_00", user.writeToRentalsFile(unit6));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_99.90__t_00", user.writeToRentalsFile(unit7));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_9.90___t_00", user.writeToRentalsFile(unit8));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_0.90___t_00", user.writeToRentalsFile(unit9));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_t_00", user.writeToRentalsFile(unit10));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_f_00", user.writeToRentalsFile(unit11));
        assertEquals("12345678_ABCDEFGHIJKLMN_ABCDEFGHIJKLMNOPQRSTUVWXY_1_999.90_t_10", user.writeToRentalsFile(unit12));
    }
}