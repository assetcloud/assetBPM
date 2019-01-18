package com.yonyougov.rest.service.api.runtime;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author lichao
 */
@RestController
@RequestMapping("/workflow/api")
public class ProcTaskQueryResource {

    @PostMapping(value = "/query/todotasks", produces = "application/json")
    public List<ProcTaskResponse> getQueryResult(@RequestBody ProcTaskQueryRequest request,
                                                 @RequestParam Map<String, String> requestParams) {
        return null;
    }
}
