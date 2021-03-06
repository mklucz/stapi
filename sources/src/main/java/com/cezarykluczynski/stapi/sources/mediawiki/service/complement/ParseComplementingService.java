package com.cezarykluczynski.stapi.sources.mediawiki.service.complement;

import com.cezarykluczynski.stapi.sources.mediawiki.api.ParseApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.parser.JsonTemplateParser;
import com.cezarykluczynski.stapi.sources.mediawiki.service.wikia.WikiaWikisDetector;
import org.springframework.stereotype.Service;

@Service
public class ParseComplementingService {

	private final WikiaWikisDetector wikiaWikisDetector;

	private final ParseApi parseApi;

	private final JsonTemplateParser jsonTemplateParser = new JsonTemplateParser();

	ParseComplementingService(WikiaWikisDetector wikiaWikisDetector, ParseApi parseApi) {
		this.wikiaWikisDetector = wikiaWikisDetector;
		this.parseApi = parseApi;
	}

	public void complement(Page page) {
		if (wikiaWikisDetector.isWikiaWiki(page.getMediaWikiSource())) {
			String xmlParseTreeContent = parseApi.parseWikitextToXmlTree(page.getWikitext());
			page.setTemplates(jsonTemplateParser.parse(xmlParseTreeContent));
		}
	}

}
