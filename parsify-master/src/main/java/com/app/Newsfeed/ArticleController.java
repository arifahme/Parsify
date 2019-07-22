package com.app.Newsfeed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xml.sax.SAXException;
import java.lang.String;
import com.google.gson.Gson;
import com.app.Newsfeed.parse.Article;
import com.app.Newsfeed.parse.Article_;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.CommonExtractors;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;
import de.l3s.boilerpipe.sax.HTMLDocument;
import de.l3s.boilerpipe.sax.HTMLFetcher;
import de.l3s.boilerpipe.BoilerpipeProcessingException;

@RestController
public class ArticleController {

    public static List<Article_> getArticles(String query) {
      List<Article_> l = new ArrayList<>();
      try {
          URL url = new URL(query);
          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
          BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
          Gson g = new Gson();
          Article result = g.fromJson(br, Article.class);
          l = result.getArticles();

          for (Article_ a : l) {
              final HTMLDocument htmlDoc = HTMLFetcher.fetch(new URL(a.getUrl()));
              final TextDocument doc = new BoilerpipeSAXInput(htmlDoc.toInputSource()).getTextDocument();
              String content = CommonExtractors.ARTICLE_EXTRACTOR.getText(doc);
              content=content.replaceAll("â€œ", "“");
                content=content.replaceAll("â€", "”");
                content=content.replaceAll("â€˜", "‘");
                content=content.replaceAll("â€™", "’");
                content=content.replaceAll("â€”", "–");
                content=content.replaceAll("â€“", "—");
                content=content.replaceAll("â€¢", "-");
                content=content.replaceAll("â€¦", "…");
                content=content.replaceAll("�", "");
                content=content.replaceAll("\\nAdvertisement\\n", " ");
                content=content.replaceAll("”™", "'");
              a.setContent(content);
          }
      } catch (IOException ioe) {
          System.out.println("Error");
          System.out.println(ioe.getMessage());
      } catch(Exception e) {
        e.printStackTrace();
      } finally {
        return l;
      }
    }

    @RequestMapping("/business")
    public List<Article_> getBusiness(){
        String query="https://newsapi.org/v2/everything?q=business&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/entertainment")
    public List<Article_> getEntertainment(){
        String query="https://newsapi.org/v2/everything?q=entertainment&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/health")
    public List<Article_> getHealth(){
        String query="https://newsapi.org/v2/everything?q=health&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/science")
    public List<Article_> getScience(){
        String query="https://newsapi.org/v2/everything?q=science&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/sports")
    public List<Article_> getSports(){
        String query="https://newsapi.org/v2/everything?q=sports&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/technology")
    public List<Article_> getTechnology(){
        String query="https://newsapi.org/v2/everything?q=technology&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/politics")
    public List<Article_> getPolitics(){
        String query="https://newsapi.org/v2/everything?q=politics&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        return getArticles(query);
    }

    @RequestMapping("/all")
    public List<Article_> getAll(){
        List<Article_> l=new ArrayList<>();
        String query="https://newsapi.org/v2/top-headlines?country=us&category=general&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        l.addAll(getArticles(query));
        // query="https://newsapi.org/v2/top-headlines?country=us&category=sports&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        // l.addAll(getArticles(query));
        // String query="https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        // l.addAll(getArticles(query));
        // query="https://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        // l.addAll(getArticles(query));
        // query="https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        // l.addAll(getArticles(query));
        // query="https://newsapi.org/v2/top-headlines?country=us&category=science&apiKey=ae32b8b44bde4ae4871588be957c99ad";
        // l.addAll(getArticles(query));
        // Collections.shuffle(l);
        return l;
    }
}
