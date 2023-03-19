package pl.zielinski.kamil.producttracker.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.zielinski.kamil.producttracker.common.annotation.architecture.KZRestController;
import pl.zielinski.kamil.producttracker.common.log.AbstractLogger;

@KZRestController
@RequestMapping("/api/v1/products")
public class ProductController extends AbstractLogger {

    @GetMapping("/{id}")
    public ResponseEntity<?> getProducts(@PathVariable("id") long id) {
        return ResponseEntity.ok("test");
    }
}
