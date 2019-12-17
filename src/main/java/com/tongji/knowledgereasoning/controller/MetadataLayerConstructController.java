package com.tongji.knowledgereasoning.controller;

/**
 * @program: knowledgereasoning
 * @description:
 * @author: Zhe Zhang
 * @create: 2019/12/17
 **/
import com.tongji.knowledgereasoning.service.MetadataLayerConstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/metadata-layer-construct"})
public class MetadataLayerConstructController {
    @Autowired
    private MetadataLayerConstructService metadataLayerConstructService;

    @RequestMapping(method = RequestMethod.GET)
    public void MetadataLayerConstruct(){
        metadataLayerConstructService.MetadataLayerConstruct();
    }
}
