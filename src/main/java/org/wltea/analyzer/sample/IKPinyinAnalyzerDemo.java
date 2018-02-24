package org.wltea.analyzer.sample;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKPinyinAnalyzer;

public class IKPinyinAnalyzerDemo {

    public static void main(String[] args){
        //构建IK分词器，使用smart分词模式
        Analyzer analyzer = new IKPinyinAnalyzer("","none", Version.LATEST, false);
        
        //获取Lucene的TokenStream对象
        TokenStream ts = null;
        try {
            ts = analyzer.tokenStream("myfield", new StringReader("yingxiong提莫是《英雄联盟》（简称LOL）中一个英雄角色"));
            //获取词元位置属性
            OffsetAttribute  offset = ts.addAttribute(OffsetAttribute.class); 
            //获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            //获取词元文本属性
            TypeAttribute type = ts.addAttribute(TypeAttribute.class);
            
            //重置TokenStream（重置StringReader）
            ts.reset(); 
            //迭代获取分词结果
            while (ts.incrementToken()) {
              System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
            }
            //关闭TokenStream（关闭StringReader）
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放TokenStream的所有资源
            if(ts != null){
              try {
                ts.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
        }
        
    }


}
