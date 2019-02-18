package org.endeavourhealth.codesetmanager.api.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

public class CodeSetManagerMetricListener extends MetricsServlet.ContextListener {
    public static final MetricRegistry codeSetManagerMetricRegistry = CodeSetManagerInstrumentedFilterContextListener.REGISTRY;

    @Override
    protected MetricRegistry getMetricRegistry() {
        return codeSetManagerMetricRegistry;
    }
}
