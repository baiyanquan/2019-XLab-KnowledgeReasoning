package com.tongji.knowledgereasoning.service;

import java.io.IOException;

public interface OntologyReasonerService {
    void readOriginData();
    void outputOriginTriples() throws IOException;
    void OntologyReasoning();
    void outputOntologyTriples();
    void closeModel();
    void write_to_neo4j();
}
