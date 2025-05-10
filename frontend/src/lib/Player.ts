import { Container, Text } from 'pixi.js';
import '@pixi/layout';
import { Card } from './Card';
import { tw } from '@pixi/layout/tailwind';

export class Player extends Container {
	playerName: string;
	readonly card: Card;

	constructor(name: string, card: number) {
		super();
		this.layout = tw`flex flex-col items-center gap-4`;
		this.playerName = name;

		const text = new Text({
			text: name,
			style: {
				fill: 'white',
				fontFamily: 'Righteous Regular',
				fontSize: 16,
				align: 'center'
			},
			layout: {
				width: 'intrinsic',
				height: 'intrinsic'
			}
		});

		this.card = new Card(card);
		this.addChild(this.card);
		this.addChild(text);
	}

	setCard(card: number) {
		this.card.value = card;
		this.card.animate(card);
	}
}
