package slanglsp;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class SlangConfigurable implements SearchableConfigurable
{
    private SlangConfigurableGUI mGUI;

    @SuppressWarnings("FieldCanBeLocal")
    private final Project mProject;

    public SlangConfigurable(@NotNull Project project)
    {
        mProject = project;
    }

    @Nls
    @Override
    public String getDisplayName()
    {
        return "Slang";
    }

    @Nullable
    @Override
    public String getHelpTopic()
    {
        return "preference.SlangConfigurable";
    }

    @NotNull
    @Override
    public String getId()
    {
        return "preference.SlangConfigurable";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String s)
    {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent()
    {
        mGUI = new SlangConfigurableGUI();
        mGUI.createUI(mProject);
        return mGUI.getRootPanel();
    }

    @Override
    public boolean isModified()
    {
        return mGUI.isModified();
    }

    @Override
    public void apply() throws ConfigurationException
    {
        mGUI.apply();
    }

    @Override
    public void reset()
    {
        mGUI.reset();
    }

    @Override
    public void disposeUIResources()
    {
        mGUI = null;
    }
}