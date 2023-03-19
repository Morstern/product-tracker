package pl.zielinski.kamil.producttracker.common.annotation.architecture;

import org.springframework.web.bind.annotation.RestController;
import pl.zielinski.kamil.producttracker.common.aspect.logger.HttpLogger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@HttpLogger
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RestController
public @interface KZRestController {
}
