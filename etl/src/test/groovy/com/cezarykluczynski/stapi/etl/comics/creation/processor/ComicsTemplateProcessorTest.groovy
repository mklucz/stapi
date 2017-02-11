package com.cezarykluczynski.stapi.etl.comics.creation.processor

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicsTest
import com.google.common.collect.Sets

class ComicsTemplateProcessorTest extends AbstractComicsTest {

	private GuidGenerator guidGeneratorMock

	private ComicsTemplateProcessor comicsTemplateProcessor

	private final Page page = Mock(Page)

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		comicsTemplateProcessor = new ComicsTemplateProcessor(guidGeneratorMock)
	}

	void "converts ComicsTemplate to Comics"() {
		given:
		ComicSeries comicSeries1 = Mock(ComicSeries)
		ComicSeries comicSeries2 = Mock(ComicSeries)
		Staff writer1 = Mock(Staff)
		Staff writer2 = Mock(Staff)
		Staff artist1 = Mock(Staff)
		Staff artist2 = Mock(Staff)
		Staff editor1 = Mock(Staff)
		Staff editor2 = Mock(Staff)
		Staff staff1 = Mock(Staff)
		Staff staff2 = Mock(Staff)
		Company publisher1 = Mock(Company)
		Company publisher2 = Mock(Company)
		Character character1 = Mock(Character)
		Character character2 = Mock(Character)

		ComicsTemplate comicsTemplate = new ComicsTemplate(
				page: page,
				title: TITLE,
				publishedYear: PUBLISHED_YEAR,
				publishedMonth: PUBLISHED_MONTH,
				publishedDay: PUBLISHED_DAY,
				coverYear: COVER_YEAR,
				coverMonth: COVER_MONTH,
				coverDay: COVER_DAY,
				numberOfPages: NUMBER_OF_PAGES,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				photonovel: PHOTONOVEL,
				comicSeries: Sets.newHashSet(comicSeries1, comicSeries2),
				writers: Sets.newHashSet(writer1, writer2),
				artists: Sets.newHashSet(artist1, artist2),
				editors: Sets.newHashSet(editor1, editor2),
				staff: Sets.newHashSet(staff1, staff2),
				publishers: Sets.newHashSet(publisher1, publisher2),
				characters: Sets.newHashSet(character1, character2))

		when:
		Comics comics = comicsTemplateProcessor.process(comicsTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(page, Comics) >> GUID
		0 * _
		comics.guid == GUID
		comics.page == page
		comics.title == TITLE
		comics.publishedYear == PUBLISHED_YEAR
		comics.publishedMonth == PUBLISHED_MONTH
		comics.publishedDay == PUBLISHED_DAY
		comics.coverYear == COVER_YEAR
		comics.coverMonth == COVER_MONTH
		comics.coverDay == COVER_DAY
		comics.numberOfPages == NUMBER_OF_PAGES
		comics.stardateFrom == STARDATE_FROM
		comics.stardateTo == STARDATE_TO
		comics.yearFrom == YEAR_FROM
		comics.yearTo == YEAR_TO
		comics.photonovel == PHOTONOVEL
		comics.comicSeries.contains comicSeries1
		comics.comicSeries.contains comicSeries2
		comics.writers.contains writer1
		comics.writers.contains writer2
		comics.artists.contains artist1
		comics.artists.contains artist1
		comics.editors.contains editor1
		comics.editors.contains editor2
		comics.staff.contains staff1
		comics.staff.contains staff2
		comics.publishers.contains publisher1
		comics.publishers.contains publisher2
		comics.characters.contains character1
		comics.characters.contains character2
	}

}
