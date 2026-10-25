package com.vinay.payment_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("application")
public class ApplicationProperties {

    @NestedConfigurationProperty
    private Cors cors = new Cors();

    public ApplicationProperties() {}

    public Cors getCors() {
        return this.cors;
    }

    public void setCors(final Cors cors) {
        this.cors = cors;
    }

    public static class Cors{
        private String pathPattern = "/api/**";
        private String allowsMethods = "*";
        private String allowedHeader = "*";
        private String allowedOriginPatterns = "*";
        private boolean allowCredentials = true;

        public String getPathPattern() {
            return pathPattern;
        }

        public String getAllowsMethods() {
            return allowsMethods;
        }

        public String getAllowedHeader() {
            return allowedHeader;
        }

        public String getAllowedOriginPatterns() {
            return allowedOriginPatterns;
        }

        public boolean isAllowCredentials() {
            return allowCredentials;
        }

        public void setPathPattern(String pathPattern) {
            this.pathPattern = pathPattern;
        }

        public void setAllowsMethods(String allowsMethods) {
            this.allowsMethods = allowsMethods;
        }

        public void setAllowedHeader(String allowedHeader) {
            this.allowedHeader = allowedHeader;
        }

        public void setAllowedOriginPatterns(String allowedOriginPatterns) {
            this.allowedOriginPatterns = allowedOriginPatterns;
        }

        public void setAllowCredentials(boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
        }

        @Override
        public String toString() {
            return "Application.Cors(pathPattern="
                    + this.getPathPattern()
                    + ", allowsMethods=" + this.getPathPattern()
                    + ", allowedHeader=" + this.getAllowedHeader()
                    + ", allowedOriginPatterns=" + this.getAllowedOriginPatterns()
                    + ", allowCredentials=" + this.isAllowCredentials()
                    + ")";
        }
    }
}
