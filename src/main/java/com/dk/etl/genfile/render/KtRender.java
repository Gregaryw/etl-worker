package com.dk.etl.genfile.render;

import com.dk.etl.extra.exception.RendException;
import com.dk.etl.genfile.template.KtTemplate;

/**
 * @Author: HarlanW
 * @Date: 2020/1/13 21:45
 * @Version:1.0
 */

public interface KtRender {
    void toFile(KtTemplate ktTemplate) throws RendException;
}
