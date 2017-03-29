package com.cezarykluczynski.stapi.server.species.mapper

import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.util.AbstractSpeciesTest
import com.google.common.collect.Sets

abstract class AbstractSpeciesMapperTest extends AbstractSpeciesTest {

	protected final AstronomicalObject homeworld = Mock(AstronomicalObject)
	protected final AstronomicalObject quadrant = Mock(AstronomicalObject)

	protected Species createSpecies() {
		new Species(
				name: NAME,
				guid: GUID,
				homeworld: homeworld,
				quadrant: quadrant,
				extinctSpecies: EXTINCT_SPECIES,
				warpCapableSpecies: WARP_CAPABLE_SPECIES,
				extraGalacticSpecies: EXTRA_GALACTIC_SPECIES,
				humanoidSpecies: HUMANOID_SPECIES,
				reptilianSpecies: REPTILIAN_SPECIES,
				nonCorporealSpecies: NON_CORPOREAL_SPECIES,
				shapeshiftingSpecies: SHAPESHIFTING_SPECIES,
				spaceborneSpecies: SPACEBORNE_SPECIES,
				telepathicSpecies: TELEPATHIC_SPECIES,
				transDimensionalSpecies: TRANS_DIMENSIONAL_SPECIES,
				unnamedSpecies: UNNAMED_SPECIES,
				alternateReality: ALTERNATE_REALITY,
				characters: Sets.newHashSet(Mock(Character)))
	}

}