package com.xyz.log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SSHClient {
	public static void main(String[] args) throws JSchException, IOException {
		String endLineStr = "$"; // it is dependent to the server
		String host = "192.168.120.102"; // host IP
		String user = "hduser"; // user name for SSH connection
		String password = "hduser"; // password for SSH connection
		int port = 22; // default SSH port

		JSch shell = new JSch();
		// get a new session
		Session session = shell.getSession(user, host, port);

		// set user password and connect to a channel
		session.setUserInfo(new SSHUserInfo(password));
		session.connect();
		Channel channel = session.openChannel("shell");
		channel.connect();

		DataInputStream dataIn = new DataInputStream(channel.getInputStream());
		DataOutputStream dataOut = new DataOutputStream(
				channel.getOutputStream());

		// send ls command to the server
		dataOut.writeBytes("ls -la\r\n");
		dataOut.flush();

		// and print the response
		String line = dataIn.readLine();
		System.out.println(line);
		while (!line.endsWith(endLineStr)) {
			System.out.println(line);
			line = dataIn.readLine();
		}
		dataIn.close();
		dataOut.close();
		channel.disconnect();
		session.disconnect();
	}

	// this class implements jsch UserInfo interface for passing password to the
	// session
	static class SSHUserInfo implements UserInfo {
		private String password;

		SSHUserInfo(String password) {
			this.password = password;
		}

		public String getPassphrase() {
			return null;
		}

		public String getPassword() {
			return password;
		}

		public boolean promptPassword(String arg0) {
			return true;
		}

		public boolean promptPassphrase(String arg0) {
			return true;
		}

		public boolean promptYesNo(String arg0) {
			return true;
		}

		public void showMessage(String arg0) {
			System.out.println(arg0);
		}
	}

}