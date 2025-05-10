import { Container, type ContainerChild, type ContainerOptions } from 'pixi.js';
import '@pixi/layout';
import { DeckCard } from './DeckCard';

export class DeckContainer extends Container {
	constructor(options: ContainerOptions<ContainerChild>, validate: Function) {
		super(options);

		const deckCards: DeckCard[] = [];
		for (let i = 0; i < 14; i++) {
			const card = new DeckCard(i);
			deckCards.push(card);
			this.addChild(card);

			card.on('pointerdown', () => {
				validate(i);
				for (const dCard of deckCards) {
					dCard.unselect();
				}
				card.select();
			});
		}
	}
}
