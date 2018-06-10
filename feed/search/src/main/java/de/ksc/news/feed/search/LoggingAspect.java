package de.ksc.news.feed.search;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Aspect
@Configuration
@Profile("trace")
public class LoggingAspect {

    @Around("execution(* de.ksc.news.feed.search..*.*(..))")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {

        final Signature signature = joinPoint.getSignature();
        final Logger log = LoggerFactory.getLogger(signature.getDeclaringType());
        log.trace("--- BEGIN {} ---", signature.toShortString());
        final Object result = joinPoint.proceed();
        log.trace("--- END {} ---", signature.toShortString());
        return result;
    }
}
