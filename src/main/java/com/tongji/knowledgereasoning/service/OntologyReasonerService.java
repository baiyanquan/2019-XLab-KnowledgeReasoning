package com.tongji.knowledgereasoning.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OntologyReasonerService {
    void readOriginData();

    void outputOriginTriples() throws IOException;

    void outputOntologyTriples();

    void closeModel();

    void write_ontology_reasoning_data_to_neo4j();

    void write_origin_data_to_neo4j();

    void OntologyReasoning() throws FileNotFoundException;
}
