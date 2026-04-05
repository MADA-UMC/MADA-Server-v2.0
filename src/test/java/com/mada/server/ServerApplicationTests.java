package com.mada.server;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ServerApplicationTests {

    @Test
    void writeDocumentationSnippets() {

        var modules = ApplicationModules.of(ServerApplication.class).verify();

        new Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
            .writeDocumentation();
    }
}
