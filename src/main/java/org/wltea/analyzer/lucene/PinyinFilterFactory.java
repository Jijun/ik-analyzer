package org.wltea.analyzer.lucene;

import java.util.Map;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

/**
 */
public class PinyinFilterFactory extends TokenFilterFactory {

    private String first_letter;
    private String padding_char;

    public PinyinFilterFactory(Map<String, String> args, String paddingChar, String firstLetter) {
        super(args);
        first_letter = firstLetter;
        padding_char = paddingChar;
    }

    @Override
    public TokenStream create(TokenStream input) {
        return new PinyinTokenFilter(input, padding_char, first_letter);
    }
}
