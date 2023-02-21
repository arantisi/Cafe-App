package com.abe.Backend.constant;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

public interface Constant {


    String UNKNOWN_EXCEPTION = "Something Went Wrong. A unknown Exception occurred.";
    String SECURITY_EXCEPTION = "Something Went Wrong. A Security Exception occurred.";
    String MALFORMEDJWT_EXCEPTION = "Something Went Wrong. A MalformedJwt Exception occurred.";
    String EXPIREDJWT_EXCEPTION = "Something Went Wrong. A ExpiredJwt Exception occurred.";
    String UNSUPPORTEDJWT_EXCEPTION = "Something Went Wrong. A UnsupportedJwt Exception occurred.";
    String ILLEGALARGUMENT_EXCEPTION = "Something Went Wrong. A IllegalArgument Exception occurred.";



}
