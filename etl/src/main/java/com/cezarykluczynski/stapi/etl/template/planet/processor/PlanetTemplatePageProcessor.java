package com.cezarykluczynski.stapi.etl.template.planet.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate;
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PlanetTemplatePageProcessor implements ItemProcessor<Page, PlanetTemplate> {

	private static final String PLANETARY_CLASSIFICATION = "Planetary classification";
	private static final String UNNAMED_PREFIX = "Unnamed";

	private final TemplateFinder templateFinder;

	private final PageBindingService pageBindingService;

	private final AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor;

	private final AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor;

	private final AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor;

	private final AstronomicalObjectCompositeEnrichingProcessor astronomicalObjectCompositeEnrichingProcessor;

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public PlanetTemplatePageProcessor(TemplateFinder templateFinder, PageBindingService pageBindingService,
			AstronomicalObjectTypeProcessor astronomicalObjectTypeProcessor,
			AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor,
			AstronomicalObjectWikitextProcessor astronomicalObjectWikitextProcessor,
			AstronomicalObjectCompositeEnrichingProcessor astronomicalObjectCompositeEnrichingProcessor,
			CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.templateFinder = templateFinder;
		this.pageBindingService = pageBindingService;
		this.astronomicalObjectTypeProcessor = astronomicalObjectTypeProcessor;
		this.astronomicalObjectTypeEnrichingProcessor = astronomicalObjectTypeEnrichingProcessor;
		this.astronomicalObjectWikitextProcessor = astronomicalObjectWikitextProcessor;
		this.astronomicalObjectCompositeEnrichingProcessor = astronomicalObjectCompositeEnrichingProcessor;
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public PlanetTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		PlanetTemplate planetTemplate = new PlanetTemplate();
		planetTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		planetTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		astronomicalObjectTypeEnrichingProcessor.enrich(EnrichablePair.of(item, planetTemplate));

		Optional<Template> templateOptional = templateFinder.findTemplate(item, TemplateTitle.SIDEBAR_PLANET);
		if (!templateOptional.isPresent()) {
			trySetTypeFromWikitext(planetTemplate, item);
			return planetTemplate;
		}
		Template template = templateOptional.get();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			if (PlanetTemplateParameter.CLASS.equals(key)) {
				AstronomicalObjectType astronomicalObjectTypeFromProcessor = astronomicalObjectTypeProcessor.process(value);
				AstronomicalObjectType currentAstronomicalObjectType = planetTemplate.getAstronomicalObjectType();
				astronomicalObjectCompositeEnrichingProcessor.enrich(EnrichablePair.of(Pair
						.of(currentAstronomicalObjectType, astronomicalObjectTypeFromProcessor), planetTemplate));
			}
		}

		trySetTypeFromWikitext(planetTemplate, item);

		if (planetTemplate.getAstronomicalObjectType() == null) {
			log.info("Could not get astronomical object type for {}", item.getTitle());
		}

		return planetTemplate;
	}

	private void trySetTypeFromWikitext(PlanetTemplate planetTemplate, Page item) throws Exception {
		if (planetTemplate.getAstronomicalObjectType() == null || AstronomicalObjectType.PLANET.equals(planetTemplate.getAstronomicalObjectType())) {
			String wikitext = StringUtils.substring(StringUtils.substringAfter(item.getWikitext(), "'''"), 0, 200);
			AstronomicalObjectType astronomicalObjectTypeFromProcessor = astronomicalObjectWikitextProcessor.process(wikitext);

			if (astronomicalObjectTypeFromProcessor != null) {
				planetTemplate.setAstronomicalObjectType(astronomicalObjectTypeFromProcessor);
			}
		}
	}

	private boolean shouldBeFilteredOut(Page item) {
		if (!item.getRedirectPath().isEmpty()) {
			return true;
		}

		String title = item.getTitle();
		if (title.startsWith(UNNAMED_PREFIX) || PLANETARY_CLASSIFICATION.equals(title)) {
			return true;
		}

		List<String> categoryHeaderList = categoryTitlesExtractingProcessor.process(item.getCategories());

		return categoryHeaderList.contains(CategoryTitle.PLANET_LISTS);
	}

}
