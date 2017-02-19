package com.cezarykluczynski.stapi.server.comicSeries.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeries as RESTComicSeries
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries as DBComicSeries
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ComicSeriesRestMapperTest extends AbstractComicSeriesMapperTest {

	private ComicSeriesRestMapper comicSeriesRestMapper

	void setup() {
		comicSeriesRestMapper = Mappers.getMapper(ComicSeriesRestMapper)
	}

	void "maps ComicSeriesRestBeanParams to ComicSeriesRequestDTO"() {
		given:
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams(
				guid: GUID,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfIssuesFrom: NUMBER_OF_ISSUES_FROM,
				numberOfIssuesTo: NUMBER_OF_ISSUES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				miniseries: MINISERIES,
				photonovelSeries: PHOTONOVEL_SERIES)

		when:
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesRestMapper.map comicSeriesRestBeanParams

		then:
		comicSeriesRequestDTO.guid == GUID
		comicSeriesRequestDTO.title == TITLE
		comicSeriesRequestDTO.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicSeriesRequestDTO.publishedYearTo == PUBLISHED_YEAR_TO
		comicSeriesRequestDTO.numberOfIssuesFrom == NUMBER_OF_ISSUES_FROM
		comicSeriesRequestDTO.numberOfIssuesTo == NUMBER_OF_ISSUES_TO
		comicSeriesRequestDTO.stardateFrom == STARDATE_FROM
		comicSeriesRequestDTO.stardateTo == STARDATE_TO
		comicSeriesRequestDTO.yearFrom == YEAR_FROM
		comicSeriesRequestDTO.yearTo == YEAR_TO
		comicSeriesRequestDTO.miniseries == MINISERIES
		comicSeriesRequestDTO.photonovelSeries == PHOTONOVEL_SERIES
	}

	void "maps DB entity to REST entity"() {
		given:
		DBComicSeries dBComicSeries = createComicSeries()

		when:
		RESTComicSeries restComicSeries = comicSeriesRestMapper.map(Lists.newArrayList(dBComicSeries))[0]

		then:
		restComicSeries.guid == GUID
		restComicSeries.title == TITLE
		restComicSeries.publishedYearFrom == PUBLISHED_YEAR_FROM
		restComicSeries.publishedMonthFrom == PUBLISHED_MONTH_FROM
		restComicSeries.publishedDayFrom == PUBLISHED_DAY_FROM
		restComicSeries.publishedYearTo == PUBLISHED_YEAR_TO
		restComicSeries.publishedMonthTo == PUBLISHED_MONTH_TO
		restComicSeries.publishedDayTo == PUBLISHED_DAY_TO
		restComicSeries.numberOfIssues == NUMBER_OF_ISSUES
		restComicSeries.stardateFrom == STARDATE_FROM
		restComicSeries.stardateTo == STARDATE_TO
		restComicSeries.yearFrom == YEAR_FROM
		restComicSeries.yearTo == YEAR_TO
		restComicSeries.miniseries == MINISERIES
		restComicSeries.photonovelSeries == PHOTONOVEL_SERIES
		restComicSeries.parentSeriesHeaders.size() == dBComicSeries.parentSeries.size()
		restComicSeries.childSeriesHeaders.size() == dBComicSeries.childSeries.size()
		restComicSeries.publisherHeaders.size() == dBComicSeries.publishers.size()
		restComicSeries.comicsHeaders.size() == dBComicSeries.comics.size()
	}

}
