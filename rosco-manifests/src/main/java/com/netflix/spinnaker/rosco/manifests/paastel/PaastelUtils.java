package com.netflix.spinnaker.rosco.manifests.paastel;

import com.netflix.spinnaker.kork.artifacts.model.Artifact;
import com.netflix.spinnaker.rosco.jobs.BakeRecipe;
import com.netflix.spinnaker.rosco.manifests.BakeManifestEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaastelUtils {
  public BakeRecipe buildBakeRecipe(BakeManifestEnvironment env, PaastelBakeManifestRequest request)
      throws IOException {
    Artifact artifact = request.getInputArtifact();
    log.info(env.resolvePath(".").getFileName().toString());
    //        Path templatePath = env.resolvePath(artifact.getName());

    List<String> command = new ArrayList<>();
    command.add("paastel");
    command.add("process");
    //      command.add(templatePath.getParent().toString());

    BakeRecipe result = new BakeRecipe();
    result.setCommand(command);
    return result;
  }
}
