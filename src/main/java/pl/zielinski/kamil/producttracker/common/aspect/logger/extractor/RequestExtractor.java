package pl.zielinski.kamil.producttracker.common.aspect.logger.method;

public interface RequestExtractor<EXTRACT, FROM> {
    EXTRACT extract(FROM from);
}
