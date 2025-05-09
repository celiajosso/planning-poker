import { BlurFilter, ColorMatrixFilter, Container, Sprite, Text } from 'pixi.js';

export class Card extends Sprite {
	text: Text;
	value: number;

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

		this.value = i;

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

	select() {
		this.colorMatrix.matrix = [
			0,
			0,
			0,
			0,
			0, // R
			0,
			0.8,
			0,
			0,
			0, // G
			0,
			0,
			0,
			0,
			0, // B
			0,
			0,
			0,
			1,
			0 // A
		];
		this.colorMatrix.contrast(0, true);
	}

	unselect() {
		this.colorMatrix.contrast(0, false);
	}
}

export class Player extends Sprite {
	playerName: string;
	readonly card: Card;

	constructor(name: string, card: number) {
		super();

		this.playerName = name;

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
		this.card.value = card;
	}
}

export class GameContainer extends Container {
	private blur = new BlurFilter();
	players: {[id:string] :Player} = {};

	hidden(bool: boolean) {
		this.blur.blur = bool ? 8 : 0;
	}

	constructor() {
		super();
		this.filters = [this.blur];
	}

	layout() {
		let i = 0;
		for (const key of Object.keys(this.players)) {
			const c = 50 + 100 * (i++);
			this.players[key].position.set(c, 100);
		}
	}

	setSize(width: number, height: number) {
		this.width = width;
		this.height = height;
	}

	addPlayer(playerInfo:any): void {
		const p = new Player(playerInfo.name, playerInfo.card)
		this.players[playerInfo.id] = p;
		this.addChild(p);
		this.layout();
	}

	removePlayer(playerInfo: any): void {
		this.removeChild(this.players[playerInfo.id]);
		delete this.players[playerInfo.id];
		this.layout();
	}


	updateHidden(bool: boolean): void {
		this.hidden(bool);
	}

}
