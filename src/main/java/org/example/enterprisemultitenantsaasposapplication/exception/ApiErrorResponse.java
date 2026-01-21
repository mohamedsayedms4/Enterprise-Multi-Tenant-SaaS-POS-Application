package org.example.enterprisemultitenantsaasposapplication.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // field -> error message (for validation)
    private Map<String, String> fieldErrors;

    // helpful for debugging (optional; keep it OFF in prod if you want)
    private String debug;
}
