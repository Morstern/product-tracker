package pl.zielinski.kamil.producttracker.common.aspect.logger.extractor;

interface RequestExtractor<EXTRACT, FROM> {
    EXTRACT extract(FROM from);
}
