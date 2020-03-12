package com.dk.etl.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: HarlanW
 * @Date: 2020/1/11 10:41
 * @Version:1.0
 */
@Data
@Component
public class EtlRepository {
    @Value("${etl.repository.username}")
    private String repositoryUsername;
    @Value("${etl.repository.password}")
    private String repositoryPassword;
}
