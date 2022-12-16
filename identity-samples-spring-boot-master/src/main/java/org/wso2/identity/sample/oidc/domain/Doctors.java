package org.wso2.identity.sample.oidc.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class Doctors {
    private Collection<Doctor> doctors;

}
