/*
 * Copyright 2019 Armory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.rosco.manifests.paastel;

import com.netflix.spinnaker.kork.artifacts.model.Artifact;
import com.netflix.spinnaker.rosco.jobs.BakeRecipe;
import com.netflix.spinnaker.rosco.jobs.JobExecutor;
import com.netflix.spinnaker.rosco.manifests.BakeManifestEnvironment;
import com.netflix.spinnaker.rosco.manifests.BakeManifestService;
import java.io.IOException;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class PaastelBakeManifestService extends BakeManifestService<PaastelBakeManifestRequest> {
  private final PaastelUtils paastelUtils;

  private static final String PAASTEL = "PAASTEL";

  public PaastelBakeManifestService(PaastelUtils paastelUtils, JobExecutor jobExecutor) {
    super(jobExecutor);
    this.paastelUtils = paastelUtils;
  }

  @Override
  public Class<PaastelBakeManifestRequest> requestType() {
    return PaastelBakeManifestRequest.class;
  }

  @Override
  public boolean handles(String type) {
    return type.toUpperCase().equals(PAASTEL);
  }

  public Artifact bake(PaastelBakeManifestRequest paastelBakeManifestRequest) throws IOException {
    try (BakeManifestEnvironment env = BakeManifestEnvironment.create()) {
      BakeRecipe recipe = paastelUtils.buildBakeRecipe(env, paastelBakeManifestRequest);

      String bakeResult = doBake(recipe);
      return Artifact.builder()
          .type("embedded/base64")
          .name(paastelBakeManifestRequest.getOutputArtifactName())
          .reference(Base64.getEncoder().encodeToString(bakeResult.getBytes()))
          .build();
    }
  }
}
