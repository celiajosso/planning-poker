import { Matrix, Sprite, Text, Ticker } from 'pixi.js';
import '@pixi/layout';
import { Game } from './Game.svelte';

export class Card extends Sprite {
	text: Text;
	value: number;
	public tickerCallback: ((delta: Ticker) => void) | undefined;

	public constructor(i: number) {
		super(Game.textures['card']);

		this.layout = {
			width: this.texture.width / 2.5,
			height: this.texture.height / 2.5
		};

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
	}

	setText(text: string): void {
		this.text.text = text;
	}

	animate(card: number) {
		let ctx = this;
		this.setText('');

		const max = 1000;
		const startTime = performance.now();

		if (ctx.tickerCallback) {
			Game.app.ticker.remove(ctx.tickerCallback);
		}
		this.tickerCallback = (delta: Ticker) => {
			const t = performance.now() - startTime;
			if (t > max) {
				if (ctx.tickerCallback) {
					Game.app.ticker.remove(ctx.tickerCallback);
					ctx.setFromMatrix(new Matrix(1, 0, 0, 1, 0, 0));
					ctx.setText(`${card}`);
				}
				return;
			}

			const progress = t / max;
			const scale = Math.abs(Math.cos(progress * Math.PI));
			if (progress > 0.5) {
				ctx.setText(`${card}`);
			}
			// ctx.setSize(scale, 1);
			ctx.setFromMatrix(new Matrix(scale, 0, 0, 1, (1 - scale) / 2, 0));
		};

		Game.app.ticker.add(this.tickerCallback);
	}
}
