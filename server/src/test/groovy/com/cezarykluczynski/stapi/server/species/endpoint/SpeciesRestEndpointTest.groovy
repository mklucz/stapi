package com.cezarykluczynski.stapi.server.species.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.cezarykluczynski.stapi.server.species.reader.SpeciesRestReader

class SpeciesRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String NAME = 'NAME'

	private SpeciesRestReader speciesRestReaderMock

	private SpeciesRestEndpoint speciesRestEndpoint

	void setup() {
		speciesRestReaderMock = Mock(SpeciesRestReader)
		speciesRestEndpoint = new SpeciesRestEndpoint(speciesRestReaderMock)
	}

	void "passes get call to SpeciesRestReader"() {
		given:
		SpeciesFullResponse speciesFullResponse = Mock(SpeciesFullResponse)

		when:
		SpeciesFullResponse speciesFullResponseOutput = speciesRestEndpoint.getSpecies(GUID)

		then:
		1 * speciesRestReaderMock.readFull(GUID) >> speciesFullResponse
		speciesFullResponseOutput == speciesFullResponse
	}

	void "passes search get call to SpeciesRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		SpeciesBaseResponse speciesResponse = Mock(SpeciesBaseResponse)

		when:
		SpeciesBaseResponse speciesResponseOutput = speciesRestEndpoint.searchSpecies(pageAwareBeanParams)

		then:
		1 * speciesRestReaderMock.readBase(_ as SpeciesRestBeanParams) >> { SpeciesRestBeanParams speciesRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			speciesResponse
		}
		speciesResponseOutput == speciesResponse
	}

	void "passes search post call to SpeciesRestReader"() {
		given:
		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams(name: NAME)
		SpeciesBaseResponse speciesResponse = Mock(SpeciesBaseResponse)

		when:
		SpeciesBaseResponse speciesResponseOutput = speciesRestEndpoint.searchSpecies(speciesRestBeanParams)

		then:
		1 * speciesRestReaderMock.readBase(speciesRestBeanParams as SpeciesRestBeanParams) >> { SpeciesRestBeanParams params ->
			assert params.name == NAME
			speciesResponse
		}
		speciesResponseOutput == speciesResponse
	}

}