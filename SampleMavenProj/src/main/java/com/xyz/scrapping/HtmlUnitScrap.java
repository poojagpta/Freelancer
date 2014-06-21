package com.xyz.scrapping;

import java.io.IOException;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitScrap {
	public static void main(String[] args) throws IOException {
		WebClient webClient = new WebClient();
		HtmlPage page = (HtmlPage)webClient.getPage("http://www.ngs.noaa.gov/AERO/uddf/WESTERN-PACIFIC/CALIFORNIA");
		List<HtmlAnchor> stringList=page.getAnchors();
		//String href = getHrefAttribute() + hrefSuffix;
		
		for(HtmlAnchor nameLink:stringList)
		{
			System.out.println(nameLink.getHrefAttribute()+"Rel Att-->"+nameLink.getRelAttribute()+"Rev Att--->"+nameLink.getRevAttribute());
			
			String urltext = nameLink.getHrefAttribute();
			if (!("/").equals(urltext.charAt(urltext.length() -1)+"")) 
			nameLink.click().getWebResponse().getContentAsStream();
			
		}
		
	}

}
