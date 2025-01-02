package slanglsp;

import java.util.*;

import com.intellij.util.xmlb.Converter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.gson.*;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;

import javax.print.DocFlavor;
import java.io.*;


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
        // TODO: make a cached state which transforms this State into an efficent to compare state (assumes rare to change settings)
        public List<String> additionalIncludePaths = new ArrayList<>();
        public List<String> predefinedMacros = List.of("__EXAMPLE_MACRO1", "__EXAMPLE_MACRO2=VALUE");

        public String explicitSlangdLocation = "";
//        public String traceServer = "off"; // handled by LSP4IJ's (runtime) debug tool
        public String enableCommitCharactersInAutoCompletion = "membersOnly";

        public Boolean enableInlayHintsForDeducedTypes = true;
        public Boolean enableInlayHintsForParameterNames = true;
        public Boolean enableSearchingSubDirectoriesOfWorkspace = true;

        public void copyValues(State otherState)
        {
            additionalIncludePaths = otherState.additionalIncludePaths;
            predefinedMacros = otherState.predefinedMacros;
            explicitSlangdLocation = otherState.explicitSlangdLocation;
            enableCommitCharactersInAutoCompletion = otherState.enableCommitCharactersInAutoCompletion;
            enableInlayHintsForDeducedTypes = otherState.enableInlayHintsForDeducedTypes;
            enableInlayHintsForParameterNames = otherState.enableInlayHintsForParameterNames;
            enableSearchingSubDirectoriesOfWorkspace = otherState.enableSearchingSubDirectoriesOfWorkspace;
        }
        public boolean equals(State other)
        {
            return true
                && additionalIncludePaths.equals(other.additionalIncludePaths)
                && predefinedMacros.equals(other.predefinedMacros)
                && explicitSlangdLocation.equals(other.explicitSlangdLocation)
                && enableCommitCharactersInAutoCompletion.equals(other.enableCommitCharactersInAutoCompletion)
                && enableInlayHintsForDeducedTypes.equals(other.enableInlayHintsForDeducedTypes)
                && enableInlayHintsForParameterNames.equals(other.enableInlayHintsForParameterNames)
                && enableSearchingSubDirectoriesOfWorkspace.equals(other.enableSearchingSubDirectoriesOfWorkspace)
                ;
        }

        Object createJSONFromObject()
        {
            Gson gson = new Gson();
            Map<String, String> stringMap = new HashMap<>();

            String additionalIncludePathsKey = "slang.additionalSearchPaths";
            String additionalIncludePathsJson = gson.toJson(additionalIncludePaths);
            stringMap.put(additionalIncludePathsKey, additionalIncludePathsJson);

            String predefinedMacrosKey = "slang.predefinedMacros";
            String predefinedMacrosJson = gson.toJson(predefinedMacros);
            stringMap.put(predefinedMacrosKey, predefinedMacrosJson);

            String enableCommitCharactersInAutoCompletionKey = "slang.enableCommitCharactersInAutoCompletion";
            String enableCommitCharactersInAutoCompletionJson = gson.toJson(enableCommitCharactersInAutoCompletion);
            stringMap.put(enableCommitCharactersInAutoCompletionKey, enableCommitCharactersInAutoCompletionJson);

            String enableInlayHintsForDeducedTypesKey = "slang.inlayHints.deducedTypes";
            String enableInlayHintsForDeducedTypesJson = gson.toJson(enableInlayHintsForDeducedTypes);
            stringMap.put(enableInlayHintsForDeducedTypesKey, enableInlayHintsForDeducedTypesJson);

            String enableInlayHintsForParameterNamesKey = "slang.inlayHints.parameterNames";
            String enableInlayHintsForParameterNamesJson = gson.toJson(enableInlayHintsForParameterNames);
            stringMap.put(enableInlayHintsForParameterNamesKey, enableInlayHintsForParameterNamesJson);

            String enableSearchingSubDirectoriesOfWorkspaceKey = "slang.searchInAllWorkspaceDirectories";
            String enableSearchingSubDirectoriesOfWorkspaceJson = gson.toJson(enableSearchingSubDirectoriesOfWorkspace);
            stringMap.put(enableSearchingSubDirectoriesOfWorkspaceKey, enableSearchingSubDirectoriesOfWorkspaceJson);

            return gson.toJson(stringMap);
        }
    }

    @NotNull
    private State state = new State();

    Object createJSONFromObject()
    {
        return state.createJSONFromObject();
    }

    String getExplicitSlangdLocation()
    {
        return state.explicitSlangdLocation;
    }

    void setState(State otherState)
    {
        state.copyValues(otherState);
    }

    @NotNull
    @Override
    public State getState()
    {
        return state;
    }

    @Override
    public void loadState(@NotNull State config)
    {
        state = config;
    }


    @Nullable
    public static SlangPersistentStateConfig getInstance(Project project)
    {
        return ServiceManager.getService(project, SlangPersistentStateConfig.class);
    }
}
