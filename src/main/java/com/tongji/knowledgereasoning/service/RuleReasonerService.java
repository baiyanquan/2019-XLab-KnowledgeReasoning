package com.tongji.knowledgereasoning.service;

public interface RuleReasonerService {
    String neo4jReasoning(String rule);

    String fusekiReasoning(String rule);
}
