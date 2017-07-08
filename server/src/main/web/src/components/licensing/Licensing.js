import React, { Component } from 'react';
import './Licensing.css';

export class Licensing extends Component {

	render() {
		return (
			<div className='container content licensing'>
				<div className="row">
					<div className="col-md-offset-2 col-md-8">
						<h3>Names and trademarks</h3>
						<p>
							<i>Star Trek</i>, <i>Star Trek: The Animated Series</i>, <i>Star Trek: The Next Generation</i>,
							<i>Star Trek: Deep Space Nine</i>, <i>Star Trek: Voyager</i>, <i>Star Trek: Enterprise</i>,
							and <i>Star Trek: Discovery</i> are all registered trademarks of CBS Corporation.
							STAPI is in no way affiliated with or endorsed by CBS. STAPI makes no claim
							to own Star Trek or any of the names related to it.
						</p>

						<h3>Content licensing</h3>
						<p>
							STAPI uses content derived from <a href="http://memory-alpha.wikia.com/">Memory Alpha</a>, therefore
							it is safe to assume that most of STAPI'a content is licensed under <a
								href="https://creativecommons.org/licenses/by-nc/4.0/">CC-BY-NC 4.0</a>.
							For details on Memory Alpha licensing see <a
									href="http://memory-alpha.wikia.com/wiki/Memory_Alpha:Copyrights">this link</a>.
						</p>
						<p>
							STAPI also uses content from <a href="http://memory-beta.wikia.com/">Memory Beta</a>, therefore some parts of the
							data is licensed under <a href="https://creativecommons.org/licenses/by-sa/3.0/">CC BY-SA 3.0</a>.
							For details on Memory Beta licensing see <a
								href="http://memory-beta.wikia.com/wiki/Memory_Beta:Copyrights">this link</a>.
						</p>
						<p>
							Trading card sets and trading cards data come from StarTrekCards.com and should be considered copyrighted.
						</p>
						<p>
							Some small parts of the content, those coming from the source code, are licensed unded <a
								href="https://github.com/cezarykluczynski/stapi/blob/master/LICENSE">MIT license</a>.
						</p>

						<h3>Attribution</h3>
						<p>
							For the authors of content the STAPI's data has been derived from, see Memory Alpha's and Memory Beta's
							history pages. For the authors of MIT-licensed data, see GitHub repository history.
						</p>

						<p>
							Machine readable attribution is not provided at the moment. Neither Creative Commons wiki,
							nor a direct contact with Creative Commons, has been able to answer a question on how exactly
							should a CC-derived work by attributed for machine-readable format. Therefore it is only attributed
							in human-readable format. If you an expert on the subject, and can provide some insights on how
							to attribute a CC-derived content in machine-readable format, please go forward and start
							a <a href="https://github.com/cezarykluczynski/stapi/issues">public discussion on GitHub</a>,
							so others can join too.
						</p>
					</div>
				</div>
			</div>
		);
	}

}
