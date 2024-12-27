package slanglsp;

import com.intellij.lang.Language;

class SlangLanguage extends Language
{
    static SlangLanguage INSTANCE = new SlangLanguage();
    SlangLanguage()
    {
        super("Slang");
    }
}