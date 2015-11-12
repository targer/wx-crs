package org.open.crs.ws.lucene;

import org.apache.lucene.queryparser.classic.ParseException;
import org.open.crs.dto.lucene.TxtSearchResultsDTO;
import org.open.crs.service.lucene.IndexFiles;
import org.open.crs.service.lucene.SearchFiles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class HelloController {

    private String indexPath = "indexes";

    /**
     * Index all text files under a directory.
     */
    @RequestMapping("/")
    public String index() throws IOException {
        String docsPath = "data1";
//        String docsPath = "D:\\IdeaProjects\\IncubationProjects\\intellijsearch\\data";

        final Path docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
            System.exit(1);
        }
        System.out.println("Indexing to directory '" + indexPath + "'...");

        IndexFiles indexFiles = new IndexFiles(indexPath);

        try {
            Date start = new Date();

            indexFiles.indexDocs(docDir);

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        } finally {
            indexFiles.close();
        }
        return "Index all text files : Success!";
    }

    @RequestMapping("/search")
    public TxtSearchResultsDTO search(@RequestParam String q) throws IOException {
        String line = q;
        TxtSearchResultsDTO txtSearchResultsDTO = null;

        SearchFiles searchFiles = new SearchFiles(indexPath);
        try {
            Date start = new Date();

            txtSearchResultsDTO = searchFiles.search(line);

            Date end = new Date();

            System.out.println("Time: " + (end.getTime() - start.getTime()) + "ms");

        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            searchFiles.close();
        }

        return txtSearchResultsDTO;
    }
}
