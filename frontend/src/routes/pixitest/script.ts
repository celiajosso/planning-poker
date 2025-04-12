import {BlurFilter, ColorMatrixFilter, Sprite, Text} from 'pixi.js';

export class Card extends Sprite {
	text: Text;

	public constructor(i: number) {
		super(Sprite.from(`/cards/Squircle.png`));

		this.text = new Text({
			text: `${i}`,
			style: {
				fontSize: 64,
				fill: 'gray',
				fontFamily: 'righteous'
			}
		});

		this.text.anchor.set(0.5);
		this.text.position.set(0.5);

		this.addChild(this.text);

		this.anchor.set(0.5);

		this.scale.set(0.5);
	}

	setText(text: string): void {
		this.text.text = text;
	}
}

export class DeckCard extends Card {
	colorMatrix: ColorMatrixFilter;
	constructor(i: number) {
		super(i);
		const ctx = this;
		ctx.eventMode = 'static';

		this.colorMatrix = new ColorMatrixFilter();
		this.filters = [this.colorMatrix];
		this.colorMatrix.contrast(0, false);
	}
	select(){
		this.colorMatrix.matrix = [
			0, 0, 0, 0, 0, // R
			0, 0.8, 0, 0, 0, // G
			0, 0, 0, 0, 0, // B
			0, 0, 0, 1, 0  // A
		];
		this.colorMatrix.contrast(0, true);
	}
	unselect(){
		this.colorMatrix.contrast(0, false);
	}
}

export class Player extends Sprite {
	private playerName: string;
	private blur = new BlurFilter();
	private card: Card;

	constructor(name: string, card: number) {
		super();

		this.filters = [this.blur];

		const text = new Text({
			text: name,
			style: {
				fill: 'white',
				fontFamily: 'righteous'
			}
		});

		this.addChild(text);
		text.anchor.set(0.5);
		text.position.set(0.5, 60);

		this.card = new Card(card);

		this.addChild(this.card);
	}

	setCard(card: number) {
		this.card.setText(`${card}`);
	}

	hidden(bool: boolean) {
		this.blur.blur = bool ? 8 : 0;
	}
}
