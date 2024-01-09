package com.wang.business.utils;

import com.wang.business.pojo.JdContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsoupUtil {

    public static List<JdContent> getJDInfo(String keyWord) throws IOException {
        String url = "https://search.jd.com/Search?keyword="+keyWord;
        List<JdContent> targets = new ArrayList<>();
        Document document = Jsoup.parse(new URL(url), 30000);
        Element j_goodsList = document.getElementById("J_goodsList");
        Elements lis = j_goodsList.getElementsByTag("li");
        for (Element li : lis) {
            // System.out.println(li);
            String img = li.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String title = li.getElementsByClass("p-name").eq(0).text();
            String price = li.getElementsByClass("p-price").eq(0).text();
            JdContent target = new JdContent();
            target.setImg(img);
            target.setTitle(title);
            target.setPrice(price);
            targets.add(target);
        }

        targets.forEach(System.out::println);
        return targets;
    }

    public static void main(String[] args) throws IOException {
        getJDInfo("java");
    }

}
