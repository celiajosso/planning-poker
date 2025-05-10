import { ColorMatrixFilter } from 'pixi.js';
import '@pixi/layout';
import { Card } from './Card';

export class DeckCard extends Card {
	colorMatrix: ColorMatrixFilter;

	constructor(card:string) {
		super(card);
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
