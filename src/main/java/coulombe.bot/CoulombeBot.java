package coulombe.bot;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ಠ_ಠ
 */
public class CoulombeBot
{
    private int depth;
    private String url;
    private String saveRep;

    private WebCrawler crawler;

    public static void main(String[] args) {
        CoulombeBot bot = new CoulombeBot();

        if(args.length != 3) {
            ErrorManager.printError(Errors.INVALID_ARGS_LENGTH, "");
        }

        bot.init(args);
    }

    /**
     * Init the crawler using arguments given
     * @param args
     */
    private void init(String[] args){
        depth = setDepth(args[0]);
        url = setUrl(args[1]);
        try {
            saveRep = setRep(args[2]);
        } catch (IOException e) {
            ErrorManager.printError(Errors.INVALID_DIR, saveRep);
        }

        crawler = new WebCrawler(depth, url, saveRep);
        crawler.search();
    }

    private int setDepth(String depth){
        int d = 0;
        try {
            d = Integer.parseInt(depth);

            if(d < 0) {
                ErrorManager.printError(Errors.INVALID_INT_SIZE, depth);
            }
        } catch(Exception e) {
            ErrorManager.printError(Errors.INVALID_INT, depth);
        }

        return d;
    }

    private String setUrl(String url){
        HttpURLConnection  uConn;

        try {
            URL u = new URL(url);
            uConn = (HttpURLConnection) u.openConnection();
            uConn.setConnectTimeout(400);
            uConn.setRequestMethod("GET");
            uConn.connect();

            UrlValidator validator = new UrlValidator(new String[]{"http","https"});

            if(validator.isValid(url)){
                this.url = url;
            } else {
                ErrorManager.printError(Errors.INVALID_URL_FORMAT, url);
            }
        } catch (Exception e) {
            ErrorManager.printError(Errors.INVALID_URL, url);
        }

        return url;
    }

    private String setRep(String saveRep) throws IOException {
        File file = new File(saveRep);

        if(!saveRep.matches("(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\$")) {
            this.saveRep = file.getAbsolutePath() + "\\src\\";
            file = new File(saveRep);

            if(!file.exists())
                file.mkdir();
        }
        else {
            this.saveRep = saveRep;
            file = new File(saveRep);
        }

        return file.toString();
    }
}
