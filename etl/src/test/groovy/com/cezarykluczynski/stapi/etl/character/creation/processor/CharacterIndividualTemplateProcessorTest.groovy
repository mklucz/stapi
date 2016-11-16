package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender as EtlGender
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.entity.BloodType
import com.cezarykluczynski.stapi.model.common.entity.Gender as EntityGender
import com.cezarykluczynski.stapi.model.common.entity.MaritalStatus
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import com.google.common.collect.Sets
import spock.lang.Specification

class CharacterIndividualTemplateProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final Page PAGE = new Page(id: 1L)
	private static final EtlGender ETL_GENDER = EtlGender.F
	private static final EntityGender ENTITY_GENDER = EntityGender.F
	private static final Integer YEAR_OF_BIRTH = 1965
	private static final Integer MONTH_OF_BIRTH = 2
	private static final Integer DAY_OF_BIRTH = 17
	private static final String PLACE_OF_BIRTH = 'PLACE_OF_BIRTH'
	private static final Integer YEAR_OF_DEATH = 1967
	private static final Integer MONTH_OF_DEATH = 7
	private static final Integer DAY_OF_DEATH = 19
	private static final String PLACE_OF_DEATH = 'PLACE_OF_DEATH'
	private static final Integer HEIGHT = 175
	private static final Integer WEIGHT = 80
	private static final Boolean DECEASED = LogicUtil.nextBoolean()
	private static final BloodType BLOOD_TYPE = BloodType.B_NEGATIVE
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED
	private static final String SERIAL_NUMBER = 'SERIAL_NUMBER'
	private static final Performer PERFORMER_1 = new Performer(id: 11L)
	private static final Performer PERFORMER_2 = new Performer(id: 12L)

	private CharacterIndividualTemplateProcessor characterIndividualTemplateProcessor

	def setup() {
		characterIndividualTemplateProcessor = new CharacterIndividualTemplateProcessor()
	}

	def "converts IndividualTemplate to Character"() {
		given:
		IndividualTemplate individualTemplate = new IndividualTemplate(
				page: PAGE,
				name: NAME,
				gender: ETL_GENDER,
				yearOfBirth: YEAR_OF_BIRTH,
				monthOfBirth: MONTH_OF_BIRTH,
				dayOfBirth: DAY_OF_BIRTH,
				placeOfBirth: PLACE_OF_BIRTH,
				yearOfDeath: YEAR_OF_DEATH,
				monthOfDeath: MONTH_OF_DEATH,
				dayOfDeath: DAY_OF_DEATH,
				placeOfDeath: PLACE_OF_DEATH,
				height: HEIGHT,
				weight: WEIGHT,
				deceased: DECEASED,
				bloodType: BLOOD_TYPE,
				maritalStatus: MARITAL_STATUS,
				serialNumber: SERIAL_NUMBER,
				performers: Sets.newHashSet(PERFORMER_1, PERFORMER_2)
		)
		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

		then:
		character.page == PAGE
		character.name == NAME
		character.gender == ENTITY_GENDER
		character.yearOfBirth == YEAR_OF_BIRTH
		character.monthOfBirth == MONTH_OF_BIRTH
		character.dayOfBirth == DAY_OF_BIRTH
		character.placeOfBirth == PLACE_OF_BIRTH
		character.yearOfDeath == YEAR_OF_DEATH
		character.monthOfDeath == MONTH_OF_DEATH
		character.dayOfDeath == DAY_OF_DEATH
		character.placeOfDeath == PLACE_OF_DEATH
		character.height == HEIGHT
		character.weight == WEIGHT
		character.deceased == DECEASED
		character.bloodType == BLOOD_TYPE
		character.maritalStatus == MARITAL_STATUS
		character.serialNumber == SERIAL_NUMBER
		character.performers.size() == 2
		character.performers.contains PERFORMER_1
		character.performers.contains PERFORMER_2
		PERFORMER_1.characters.contains character
		PERFORMER_2.characters.contains character
	}

}
