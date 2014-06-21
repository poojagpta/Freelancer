package com.xyz.scrapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ListLinks {
	public static void main(String[] args) throws IOException {
		// Validate.isTrue(args.length == 1, "usage: supply url to fetch");
		String url = "http://www.ngs.noaa.gov/AERO/uddf/WESTERN-PACIFIC/CALIFORNIA";
		String fileDirectory = "C:/dev/freelancer/log/";
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), link.text());
			String urltext = link.attr("abs:href");

			if (!("/").equals(urltext.charAt(urltext.length() - 1) + "")) {
				URL urlObj = new URL(urltext);
				File fileObj = new File(fileDirectory + link.text());
				Boolean testB = fileObj.createNewFile();

				InputStream webIS = urlObj.openConnection().getInputStream();

				FileOutputStream fo = new FileOutputStream(fileObj);
				int c = 0;
				do {
					c = webIS.read();
					System.out.println("==============> " + c);
					if (c != -1)
						fo.write((byte) c);
				} while (c != -1);

				webIS.close();
				fo.close();

				// print("Value of link------>" + outputText);
			}
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
}