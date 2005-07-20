package com.intellij.lang.html;

import com.intellij.ide.highlighter.HtmlFileHighlighter;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.PsiBasedFormattingModel;
import com.intellij.psi.formatter.FormattingDocumentModelImpl;
import com.intellij.psi.formatter.xml.HtmlPolicy;
import com.intellij.psi.formatter.xml.XmlBlock;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.xml.XmlPsiPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: max
 * Date: Jan 24, 2005
 * Time: 11:00:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class HTMLLanguage extends XMLLanguage {
  private final FormattingModelBuilder myFormattingModelBuilder;
  public HTMLLanguage() {
    super("HTML", "text/html","text/htmlh");
    myFormattingModelBuilder = new FormattingModelBuilder() {
      public FormattingModel createModel(final PsiElement element, final CodeStyleSettings settings) {
        final FormattingDocumentModelImpl documentModel = FormattingDocumentModelImpl.createOn(element.getContainingFile());
        return new PsiBasedFormattingModel(element.getContainingFile(),
                                           new XmlBlock(SourceTreeToPsiMap.psiElementToTree(element),
                                                        null, null, new HtmlPolicy(settings, documentModel), null, null),
                                           documentModel);
      }
    };

  }

  public SyntaxHighlighter getSyntaxHighlighter(Project project) {
    return new HtmlFileHighlighter();
  }

  public XmlPsiPolicy getPsiPolicy() {
    return ENCODE_EACH_SYMBOL_POLICY;
  }

  public ParserDefinition getParserDefinition() {
    return new HTMLParserDefinition();
  }

  public FormattingModelBuilder getFormattingModelBuilder() {
    return myFormattingModelBuilder;
  }
}
