package at.tm.rmi.utils;

import java.security.Permission;

/**
 * @author Patrieg Malick
 * @version 08.01.2015
 * 
 * The only purpose of this class is to fool the original SecurityManager by returning nothing and let us do our job.
 *
 */
public class MySecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        return;
    }
}