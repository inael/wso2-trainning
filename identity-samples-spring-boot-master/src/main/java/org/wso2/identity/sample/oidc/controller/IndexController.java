/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.wso2.identity.sample.oidc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.wso2.identity.sample.oidc.domain.MedicalRecord;
import org.wso2.identity.sample.oidc.domain.Patient;
import org.wso2.identity.sample.oidc.service.PatientService;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the redirection after successful authentication from Identity Server
 */
@Controller
public class IndexController {

    private final Logger LOGGER = Logger.getLogger(IndexController.class.getName());
    private String userName;
    private DefaultOidcUser user;
    private final OAuth2AuthorizedClientService authorizedClientService;

    private Patient patient;
    public IndexController(OAuth2AuthorizedClientService authorizedClientService, PatientService patientService) {

        this.authorizedClientService = authorizedClientService;

        this.patient = patient;
    }

    /**
     * Redirects to this method once the user successfully authenticated and this method will redirect to index page.
     *
     * @param model          Model.
     * @param authentication Authentication.
     * @return Index page.
     */
    @GetMapping("/")
    public String currentUserName(Model model, Authentication authentication) {

        extracted(authentication);
        model.addAttribute("userName", userName);
        getTokenDetails(model, authentication);
        return "index";
    }

    private void extracted(Authentication authentication) {
        user = (DefaultOidcUser) authentication.getPrincipal();
        if (user != null) {
            userName = user.getName();
        }
    }

    /**
     * Handles the redirection to /userinfo endpoint and get the user information from authentication object. This
     * method will display the id-token and user information in the UI.
     *
     * @param authentication Authentication
     * @param model          Model.
     * @return userinfo html page.
     */
    @GetMapping("/userinfo")
    public String getUser(Authentication authentication, Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        return "userinfo";
    }

    @GetMapping("/add-patient2")
    public String addPatient2(Authentication authentication, Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        return "addPatient";
    }



//----------forms
    @GetMapping("/form-patient")
    public String formPatient(Authentication authentication,Patient patient, Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        model.addAttribute("patient", new Patient());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        return "addPatient";
    }

    @GetMapping("/form-view-patient-records")
    public String viewMedicalPatientRecords(Authentication authentication,Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        model.addAttribute("patient", new Patient());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        return "viewPatientRecords";
    }
    @GetMapping("/form-delete-patient")
    public String formDeletePatient(Authentication authentication, Patient patient, Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        model.addAttribute("patient", new Patient());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        return "deletePatient";
    }

    @GetMapping("/form-add-medical-patient-records")
    public String addMedicalPatientRecords(Authentication authentication,Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        model.addAttribute("patient", new Patient());
        return "addMedicalPatientRecords";
    }

    @GetMapping("/form-add-meidical-record")
    public String formAddMedicalRecords(Authentication authentication,String patientName, String patientId, String email, Model model) {
        extracted(authentication);
        Patient patient1 = Patient.builder().name(patientName).id(patientId).email(email).build();

        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        model.addAttribute("patient", patient1);
        model.addAttribute("medicalRecord", new MedicalRecord());

        return "addMedicalPatientRecords";
    }

    //-----------------
    @GetMapping("/add-patient")
    public String addPatient(Authentication authentication, Patient patient, Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

/*
    @GetMapping("/search-patient")
    public String viewMedicalPatientRecords(Authentication authentication, Patient patient, Model model) {
        extracted(authentication);
        model.addAttribute("userName", userName);
        model.addAttribute("idtoken", user.getClaims());
        LOGGER.log(Level.INFO, "UserName : " + userName);
        LOGGER.log(Level.INFO, "User Attributes: " + user.getClaims());
        return "viewPartientRecords";
    }

*/
    private void getTokenDetails(Model model, Authentication authentication) {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());

        if (client != null && client.getAccessToken() != null) {
            String accessToken = client.getAccessToken().getTokenValue();
            Set<String> scope = client.getAccessToken().getScopes();
            String tokenType = client.getAccessToken().getTokenType().getValue();
            String accessTokenExp = client.getAccessToken().getExpiresAt().toString();
            LOGGER.log(Level.INFO, "Access token : " + accessToken);
            LOGGER.log(Level.INFO, "Token type : " + tokenType);
            LOGGER.log(Level.INFO, "Scope : " + scope);
            LOGGER.log(Level.INFO, "Access token Exp : " + accessTokenExp);
            model.addAttribute("accessToken", accessToken);
            model.addAttribute("tokenType", tokenType);
            model.addAttribute("accessTokenExp", accessTokenExp);
            model.addAttribute("scope", scope);

        }

        if (client != null && client.getRefreshToken() != null) {
            String refreshToken = client.getRefreshToken().getTokenValue();
            LOGGER.log(Level.INFO, "Refresh token: " + refreshToken);
            model.addAttribute("refreshToken", refreshToken);

        }
    }
}
