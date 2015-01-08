package at.tm.rmi.utils;

import java.security.Permission;

public class MySecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
        return;
    }
}
