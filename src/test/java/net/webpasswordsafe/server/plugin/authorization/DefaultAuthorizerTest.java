package net.webpasswordsafe.server.plugin.authorization;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import junit.framework.Assert;
import net.webpasswordsafe.common.model.User;
import net.webpasswordsafe.common.util.Constants.Function;
import net.webpasswordsafe.common.util.Constants.Role;

public class DefaultAuthorizerTest {

	@Test
	public void testIsAuthorized() {
		DefaultAuthorizer defaultAuthorizer = new DefaultAuthorizer();
		
		User user = new User();
		Set<Role> roles = new TreeSet<Role>();
		
		roles.add(Role.ROLE_USER);
		
		user.setRoles(roles);
		
		Assert.assertTrue(defaultAuthorizer.isAuthorized(user, Function.ADD_GROUP.name()));
		Assert.assertTrue(defaultAuthorizer.isAuthorized(user, Function.UPDATE_GROUP.name()));
		Assert.assertFalse(defaultAuthorizer.isAuthorized(user, Function.DELETE_GROUP.name()));
	}

}
