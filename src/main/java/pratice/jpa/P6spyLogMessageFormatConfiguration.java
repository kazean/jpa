package pratice.jpa;

import com.p6spy.engine.spy.P6SpyOptions;
import org.springframework.context.annotation.Configuration;
import pratice.jpa.util.CustomP6spySqlFormat;

import javax.annotation.PostConstruct;

@Configuration
public class P6spyLogMessageFormatConfiguration {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomP6spySqlFormat.class.getName());
    }
}
