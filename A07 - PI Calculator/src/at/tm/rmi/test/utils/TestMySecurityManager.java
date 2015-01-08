package at.tm.rmi.test.utils;

import static org.junit.Assert.*;

import java.security.Permission;

import org.junit.Test;

import at.tm.rmi.utils.MySecurityManager;

public class TestMySecurityManager {

	@Test
	public void test() {
		new MySecurityManager().checkPermission(null);
	}

}
