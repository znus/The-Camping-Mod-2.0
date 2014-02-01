package rikmuld.camping.misc.guide;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Page {

	Node page;

	public ArrayList<PageTextData> text = new ArrayList<PageTextData>();
	public ArrayList<PageLinkData> link = new ArrayList<PageLinkData>();
	public ArrayList<PageCraftData> crafting = new ArrayList<PageCraftData>();
	public ArrayList<PageImgData> image = new ArrayList<PageImgData>();
	public ArrayList<PageItemImgData> item = new ArrayList<PageItemImgData>();

	public Page(Node pageNode)
	{
		page = pageNode;
		collectInfo();
	}

	private void collectCraft()
	{
		NodeList craft = ((Element)page).getElementsByTagName("craft");
		for(int i = 0; i < craft.getLength(); i++)
		{
			crafting.add(new PageCraftData(craft.item(i)));
		}
	}

	private void collectImg()
	{
		NodeList img = ((Element)page).getElementsByTagName("img");
		for(int i = 0; i < img.getLength(); i++)
		{
			image.add(new PageImgData(img.item(i)));
		}
	}

	private void collectInfo()
	{
		collectText();
		collectImg();
		collectItemImg();
		collectCraft();
		collectLink();
	}

	private void collectItemImg()
	{
		NodeList img = ((Element)page).getElementsByTagName("item");
		for(int i = 0; i < img.getLength(); i++)
		{
			item.add(new PageItemImgData(img.item(i)));
		}
	}

	private void collectLink()
	{
		NodeList links = ((Element)page).getElementsByTagName("link");
		for(int i = 0; i < links.getLength(); i++)
		{
			link.add(new PageLinkData(links.item(i)));
		}
	}

	private void collectText()
	{
		NodeList text = ((Element)page).getElementsByTagName("text");
		for(int i = 0; i < text.getLength(); i++)
		{
			this.text.add(new PageTextData(text.item(i)));
		}

		NodeList version = ((Element)page).getElementsByTagName("version");
		for(int i = 0; i < version.getLength(); i++)
		{
			this.text.add(new PageVersionData(version.item(i)));
		}
	}
}
