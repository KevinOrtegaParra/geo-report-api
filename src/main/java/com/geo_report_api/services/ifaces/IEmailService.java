package com.geo_report_api.services.ifaces;

public interface IEmailService {
    boolean sendMail(String email, String name);
}
