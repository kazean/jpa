package pratice.jpa.test.jpql.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class InitController {
    private final InitRepository initRepository;

    @PostConstruct
    public void init(){
        initRepository.init();
    }

}
