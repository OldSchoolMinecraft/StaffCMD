package net.oldschoolminecraft.scmd;

import java.util.ArrayList;

public class ModeStatus
{
    private static final ArrayList<String> inStaffMode = new ArrayList<>();

    public static boolean usingStaffMode(String username)
    {
        return inStaffMode.contains(username);
    }

    public static void enableStaffMode(String username)
    {
        inStaffMode.add(username);
        System.out.println("[StaffCMD] " + username + " has ENABLED staff mode!");
    }

    public static void disableStaffMode(String username)
    {
        inStaffMode.remove(username);
        System.out.println("[StaffCMD] " + username + " has DISABLEWD staff mode!");
    }
}
