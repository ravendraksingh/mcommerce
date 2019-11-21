package com.rks.catalog.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthResponse extends BaseResponse {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthResponse.class);

    private String program;
    private String version;
    private String release;
    private String datetime = LocalDateTime.now().toString();
    private String status = "success";
    private int code = 200;
    private String message = "OK";
    private String ip;
    private String hostname;
    private String branch;
    private float duration = 0.0F;
    private String port;

    //private List<HealthResponse.DependenciesHealthStatus> dependentservices = null;
    //private HealthResponse.HealthData data = new HealthResponse.HealthData();

    public HealthResponse() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            this.ip = inetAddress.getHostAddress();
            this.hostname = inetAddress.getHostName();
        } catch (UnknownHostException var2) {
            LOGGER.error(var2.getLocalizedMessage(), var2);
        }
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
