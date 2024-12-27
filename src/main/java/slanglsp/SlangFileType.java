package slanglsp;

/* unused, may be used later */

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.fileTypes.LanguageFileType;
import javax.swing.Icon;

public class SlangFileType extends LanguageFileType {
    static SlangFileType INSTANCE = new SlangFileType();
    
    SlangFileType()
    {
        super(SlangLanguage.INSTANCE);
    }

    @NotNull
    public String getName()
    {
        return "Slang";
    }

    @NotNull
    public String getDescription()
    {
        return "Slang language file";
    }

    @NotNull
    public String getDefaultExtension()
    {
        return "slang";
    }

    public Icon getIcon()
    {
        return SlangIcons.Slang;
    }
}