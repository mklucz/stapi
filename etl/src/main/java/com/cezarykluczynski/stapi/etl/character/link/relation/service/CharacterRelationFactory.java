package com.cezarykluczynski.stapi.etl.character.link.relation.service;

import com.cezarykluczynski.stapi.etl.character.common.dto.CharacterRelationCacheKey;
import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterPageLinkWithRelationName;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class CharacterRelationFactory {

	private final CharacterRepository characterRepository;

	private final PageApi pageApi;

	private final CharacterRelationsSidebarTemplateMappingsProvider characterRelationsSidebarTemplateMappingsProvider;

	private final CharacterRelationNormalizationService characterRelationNormalizationService;

	@Inject
	public CharacterRelationFactory(CharacterRepository characterRepository, PageApi pageApi,
			CharacterRelationsSidebarTemplateMappingsProvider characterRelationsSidebarTemplateMappingsProvider,
			CharacterRelationNormalizationService characterRelationNormalizationService) {
		this.characterRepository = characterRepository;
		this.pageApi = pageApi;
		this.characterRelationsSidebarTemplateMappingsProvider = characterRelationsSidebarTemplateMappingsProvider;
		this.characterRelationNormalizationService = characterRelationNormalizationService;
	}

	public CharacterRelation create(Character subject, CharacterPageLinkWithRelationName characterPageLinkWithRelationName,
			CharacterRelationCacheKey characterRelationCacheKey) {
		PageLink pageLink = characterPageLinkWithRelationName.getPageLink();
		Optional<Character> targetOptional = characterRepository
				.findByPageTitleAndPageMediaWikiSource(pageLink.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);

		if (!targetOptional.isPresent()) {
			Page page = pageApi.getPage(pageLink.getTitle(), com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN);
			if (page != null && !page.getRedirectPath().isEmpty()) {
				targetOptional = characterRepository.findByPageTitleAndPageMediaWikiSource(page.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
			}
		}

		if (targetOptional.isPresent()) {
			CharacterRelation characterRelation = new CharacterRelation();
			characterRelation.setSubject(subject);
			characterRelation.setTarget(targetOptional.get());

			if (characterPageLinkWithRelationName.getRelationName() != null) {
				characterRelation.setType(characterRelationNormalizationService.normalize(characterPageLinkWithRelationName.getRelationName()));
			}

			if (characterRelation.getType() == null) {
				characterRelation.setType(characterRelationsSidebarTemplateMappingsProvider.provideFor(characterRelationCacheKey));
			}

			return characterRelation;
		}

		return null;
	}

}
