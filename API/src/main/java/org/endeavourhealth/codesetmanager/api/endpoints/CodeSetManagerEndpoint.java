package org.endeavourhealth.codesetmanager.api.endpoints;
import com.codahale.metrics.annotation.Timed;
import io.astefanutti.metrics.aspectj.Metrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.endeavourhealth.codesetmanager.api.logic.CodeSetManagerLogic;
import org.endeavourhealth.codesetmanager.models.json.JsonCodeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Path("/codeSetManager")
@Metrics(registry = "codeSetManagerMetricRegistry")
@Api(description = "Api for all calls relating to the Code Set Manager")
public class CodeSetManagerEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(CodeSetManagerEndpoint.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(absolute = true, name="CodeSetManager.CodeSetManagerEndpoint.Get.Get") // metrics name <application>.<endpoint>.<path>.<method>
    @Path("/get")
    @ApiOperation(value = "Returns a list of all Code Sets") // operation description
    public Response get(@Context SecurityContext sc) throws Exception {

        LOG.debug("Get All CodeSets Called");

        List<JsonCodeSet> result = new CodeSetManagerLogic().getAllCodeSets();

        return Response
                .ok(result)
                .build();
    }
}
