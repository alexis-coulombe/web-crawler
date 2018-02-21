package coulombe.bot;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 1436023 on 2018-01-26.
 */
public class WebCrawler {
    public final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private Set<String> emails = new HashSet<String>();
    private List<String> links = new LinkedList<String>();
    private Set<String> visited = new HashSet<String>();
    private List<String> toVisit = new LinkedList<String>();

    private int depth;
    private String url, saveRep;
    private Document currentDoc;
    private Connection conn;

    public WebCrawler(int depth, String url, String saveRep){
        this.depth = depth;
        this.url = url;
        this.saveRep = saveRep;
    }

    private void crawl(String url)  {
        try {
            conn = Jsoup.connect(url).userAgent(USER_AGENT);
            currentDoc = conn.get();

            for(Element link : currentDoc.select("a[href]")){
                UrlValidator validator = new UrlValidator(new String[]{"http","https"});

                if(validator.isValid(link.absUrl("href"))) {
                    links.add(link.absUrl("href"));
                }
            }

            Pattern p = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
            Matcher matcher = p.matcher(currentDoc.text());
            while (matcher.find()) {
                emails.add(matcher.group());
            }

            System.out.println("Site visité: " + url);

            saveHTMLDoc();

        } catch (IOException e) {
            System.out.println("Erreur dans l\'url fournit: " + url + "(probablement une page introuvable)");
        }
    }

    private void saveHTMLDoc(){
            try {
                String[] path = url.split("/");
                String title = path[path.length - 1];

                if(!title.endsWith(".html"))
                    title += ".html";

                if(title.equals("3.html")){
                    Files.createDirectories(Paths.get(saveRep + "\\" + "/sub"));
                    title = "/sub/3.html";
                }

                String txtDoc = currentDoc.toString();

                txtDoc = txtDoc.replaceAll("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", "alexiscoulombe@hotmail.com");

                File file = new File(saveRep + "\\" + title);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(txtDoc);
                writer.close();

            } catch (Exception e) {
                ErrorManager.printError(Errors.INVALID_DIR_PERM, saveRep);
            }
    }

    public void search() {
        while(visited.size() <= depth) {
            if (toVisit.isEmpty()) {
                visited.add(url);
            } else {
                url = nextUrl();

                if(url == null)
                    break;
            }
            crawl(url);

            toVisit.addAll(links);
        }

        List<String> liste = new ArrayList<String>(emails);
        Collections.sort(liste, String.CASE_INSENSITIVE_ORDER);

        System.out.println("Courriel trouvé: " + liste.toString());
        System.out.println("La recherche s'est terminé. Contenu sauvegarder sous: " + saveRep);
        System.exit(1);
    }

    private String nextUrl(){
        String nextUrl;

        do{
            if(toVisit.size() > 0)
                nextUrl = toVisit.remove(0);
            else
                return null;
        } while(visited.contains(nextUrl));

        visited.add(nextUrl);

        return nextUrl;
    }
}
