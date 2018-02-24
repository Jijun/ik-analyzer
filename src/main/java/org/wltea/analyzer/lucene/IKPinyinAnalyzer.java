package org.wltea.analyzer.lucene;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;
/**
 * 实现拼音功能
 * @author ranger
 *
 */
public final class IKPinyinAnalyzer extends Analyzer {

    private final String padding_char;
    private final String first_letter;
    private final boolean useSmart;
    private final Version luceneMatchVersion;
    
    public IKPinyinAnalyzer(String paddingChar, String firstLetter,Version version, boolean useSmart) {
        this.luceneMatchVersion = version;
        if (!version.onOrAfter(Version.LATEST)) {
            throw new IllegalArgumentException("This class only works with Lucene 4.0+.");
        }
        this.useSmart = useSmart;
        this.first_letter = firstLetter;
        this.padding_char = paddingChar;
    }


    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer token = new IKTokenizer( useSmart);
        Map<String, String> args = new HashMap<String, String>();
        args.put("luceneMatchVersion", luceneMatchVersion.toString());
        PinyinFilterFactory factory = new PinyinFilterFactory(args, padding_char, first_letter);
        return new TokenStreamComponents(token, factory.create(token));
    }

}
