package net.impjq.autotweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class AutoTweet {
	public static final boolean DEBUG = true;
	public static final String LOGTAG = "AutoTweet";

	public static final String USER = "USER=";
	public static final String PASSWD = "PASSWD=";
	public static final String APIPROXY = "APIPROXY=";
	public static final String HTTPPROXYHOST = "HttpProxy(host)=";
	public static final String HTTPPROXYPORT = "HttpProxy(port)=";
	public static final String CLIENTNAME = "ClientName=";

	public static Twitter twitter;
	public static String user;
	public static String passwd;
	public static String httpProxyHost;
	public static String httpProxyPort;
	public static String apiUrl;
	public static String clientName = "AutoTweet";

	public static String msg;

	public static final String configFile = "autotweet.conf";

	public static void main(String[] args) {
		String status = "";

		if (args.length == 0) {
			log(LOGTAG, "Nothing to Send?");
			// return;
		}

		for (String s : args) {
			status += s + " ";
		}

		log(LOGTAG, "Update Status:" + status);

		int size = status.length();

		readConfig();

		if (null != user && null != passwd) {
			twitter = new Twitter(user, passwd);
			twitter.setSource(clientName);
			if (null != apiUrl) {
				twitter.setBaseURL(apiUrl);
			}

			if (null != httpProxyHost && null != httpProxyPort) {
				twitter.setHttpProxy(httpProxyHost, Integer
						.parseInt(httpProxyPort));
			}

			try {
				twitter.updateStatus(status);
				log(LOGTAG, "Update Status success:" + status);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				log(LOGTAG, "Update Status failed:" + status);
				e.printStackTrace();
			}
		}
	}

	public static void log(String LOGTAG, String msg) {
		if (DEBUG) {
			System.out.println("(" + LOGTAG + ")" + msg);
		}
	}

	public static void help() {
		log(LOGTAG, "Please Edit the Config file first:autotweet.conf");
	}

	public static void readConfig() {

		try {
			FileInputStream fis = new FileInputStream(configFile);
			BufferedReader br = new BufferedReader(new FileReader(configFile));

			String tmp;

			while (null != (tmp = br.readLine())) {
				// log("", tmp);

				if (tmp.startsWith(USER)) {
					user = getConfig(tmp);
				}

				if (tmp.startsWith(PASSWD)) {
					passwd = getConfig(tmp);
				}

				if (tmp.startsWith(HTTPPROXYHOST)) {
					httpProxyHost = getConfig(tmp);
				}

				if (tmp.startsWith(HTTPPROXYPORT)) {
					httpProxyPort = getConfig(tmp);
				}

				if (tmp.startsWith(APIPROXY)) {
					apiUrl = getConfig(tmp);
					apiUrl = getConfig(tmp);
					if (!apiUrl.endsWith("/")) {
						apiUrl += "/";
					}
				}

				if (tmp.startsWith(CLIENTNAME)) {
					clientName = getConfig(tmp);
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			File file = new File(configFile);

			if (!file.exists()) {
				try {
					if (file.createNewFile()) {
						log(LOGTAG, "Create Config file Success:" + configFile);
					}

					if (file.exists()) {
						BufferedWriter bw = new BufferedWriter(new FileWriter(
								configFile));
						bw.write("#The AutoTweet Config file\n");
						bw.write(USER + "\n");
						bw.write(PASSWD + "\n");
						bw.write(APIPROXY + "\n");
						bw.write(HTTPPROXYHOST + "\n");
						bw.write(HTTPPROXYPORT + "\n");
						bw.write(CLIENTNAME + "\n");
						bw.close();
					}
					help();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The real config is after "=" So split the string with "=" and return the
	 * values after "="
	 * 
	 * @param tmp
	 * @return
	 */
	public static String getConfig(String tmp) {
		String[] arg = tmp.split("=");

		return arg.length > 1 ? arg[1] : null;

	}

}
