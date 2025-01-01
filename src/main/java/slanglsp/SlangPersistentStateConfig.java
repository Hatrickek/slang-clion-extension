package slanglsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;


@State(
    name="SlangPersistentStateComponentConfig",
    storages = {
        @Storage("SlangPluginSettings.xml")
    }
)
class SlangPersistentStateConfig implements PersistentStateComponent<SlangPersistentStateConfig.State>
{
    static class State
    {
        public Vector<String> additionalIncludePaths = new Vector<>();
        public Vector<String> predefinedMacros = new Vector<>(List.of("__EXAMPLE_MACRO1", "__EXAMPLE_MACRO2=VALUE"));
        public String explicitSlangdLocation = "";
        public Boolean enableInlayHintsForDeducedTypes = true;
        public Boolean enableInlayHintsForParameterNames = true;
        public Boolean enableSearchingSubDirectoriesOfWorkspace = true;

        public boolean equals(State other)
        {
            return true
                && additionalIncludePaths.equals(other.additionalIncludePaths)
                && predefinedMacros.equals(other.predefinedMacros)
                && explicitSlangdLocation.equals(other.explicitSlangdLocation)
                && enableInlayHintsForDeducedTypes.equals(other.enableInlayHintsForDeducedTypes)
                && enableInlayHintsForParameterNames.equals(other.enableInlayHintsForParameterNames)
                && enableSearchingSubDirectoriesOfWorkspace.equals(other.enableSearchingSubDirectoriesOfWorkspace)
                ;
        }
    }

    private State state = new State();

    void setState(State otherState)
    {
        state.additionalIncludePaths = otherState.additionalIncludePaths;
        state.predefinedMacros = otherState.predefinedMacros;
        state.explicitSlangdLocation = otherState.explicitSlangdLocation;
        state.enableInlayHintsForDeducedTypes = otherState.enableInlayHintsForDeducedTypes;
        state.enableInlayHintsForParameterNames = otherState.enableInlayHintsForParameterNames;
        state.enableSearchingSubDirectoriesOfWorkspace = otherState.enableSearchingSubDirectoriesOfWorkspace;
    }

    @Nullable
    @Override
    public State getState()
    {
        return this.state;
    }

    @Override
    public void loadState(State config)
    {
        state = config;
    }


    @Nullable
    public static SlangPersistentStateConfig getInstance(Project project)
    {
        return ServiceManager.getService(project, SlangPersistentStateConfig.class);
    }
}
