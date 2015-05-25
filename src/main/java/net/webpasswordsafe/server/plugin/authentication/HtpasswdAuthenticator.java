/*
    Copyright 2015 Alfame Systems Oy

    This file is part of Salasanasiilo.

    WebPasswordSafe is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    WebPasswordSafe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with WebPasswordSafe; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/
package net.webpasswordsafe.server.plugin.authentication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.webpasswordsafe.common.util.MD5Crypt;

import org.apache.log4j.Logger;

/**
 * Authenticator that uses Unix htpasswd files as a backend.
 *
 * @author Karri Kahelin
 *
 */
public class HtpasswdAuthenticator implements Authenticator {
	// injected from webpasswordsafe-service.xml
	private String htpasswdFile;
    private static Logger LOG = Logger.getLogger(HtpasswdAuthenticator.class);

	@Override
	public boolean authenticate(String username, String password)
	{
		LOG.debug("Trying htpasswd authenticate for " + username + " from " + htpasswdFile);

		try(BufferedReader br = new BufferedReader(new FileReader(new File(getHtpasswdFile()))))
		{
			for(String line = br.readLine(); line != null; line = br.readLine())
			{
				int colonpos = line.indexOf(':');
				if(colonpos == -1) {
					LOG.error("Malformed htpasswd file " + htpasswdFile);
					return false;
				}
				String userpart = line.substring(0, colonpos);

				if(username.equals(userpart))
				{
					String passwordpart = line.substring(colonpos + 1);
					boolean success = MD5Crypt.verifyPassword(password, passwordpart);

                    LOG.debug("HtpasswdAuthenticator: login success for " + username + "? " + success);
                    return success;
				}
			}
		}
		catch (FileNotFoundException e)
		{
			LOG.error("Htpasswd file " + htpasswdFile + " not found");
		}
		catch (IOException e)
		{
			LOG.error("Error reading htpasswd file " + htpasswdFile + " " + e.getMessage());
		}
		return false;
	}

	public String getHtpasswdFile() {
		return htpasswdFile;
	}

	public void setHtpasswdFile(String htpasswdFile) {
		this.htpasswdFile = htpasswdFile;
	}

}
