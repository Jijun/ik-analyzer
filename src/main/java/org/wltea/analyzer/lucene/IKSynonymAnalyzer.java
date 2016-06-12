package org.wltea.analyzer.lucene;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;

/**
 * 实现同义词功能
 * 
 * @author ranger
 * 
 */
public class IKSynonymAnalyzer extends Analyzer {

    private final boolean useSmart;
    private final Version luceneMatchVersion;

    public IKSynonymAnalyzer(Version version) {
        this(version, false);
    }

    public IKSynonymAnalyzer(Version version, boolean useSmart) {
        this.luceneMatchVersion = version;
        if (!version.onOrAfter(Version.LATEST)) {
            throw new IllegalArgumentException("This class only works with Lucene 5.5+.");
        }
        this.useSmart = useSmart;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer token = new IKTokenizer(useSmart);
        Map<String, String> paramsMap = new HashMap<String, String>();
        Configuration cfg = DefaultConfig.getInstance();

        paramsMap.put("luceneMatchVersion", luceneMatchVersion.toString());
        paramsMap.put("synonyms", cfg.getExtSynonymDictionarys());
        paramsMap.put("ignoreCase", "true");
        SynonymFilterFactory factory = new SynonymFilterFactory(paramsMap);
        ResourceLoader loader = new ClasspathResourceLoader();
        try {
            factory.inform(loader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new TokenStreamComponents(token, factory.create(token));
    }

}
