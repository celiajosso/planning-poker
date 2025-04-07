<script lang="ts">
	import { Application, Assets, Sprite } from 'pixi.js';
	import { onMount } from 'svelte';

	let canvas: HTMLCanvasElement;

	const app = new Application();

	onMount(async () => {
		// Create a PixiJS application.

		// Intialize the application.
		await app.init({ background: '#1099bb', resizeTo: window, canvas: canvas });

		let textures = [];
		let sprites = [];

		for (let i = 0; i < 14; i++) {
			const texture = await Assets.load({
				src: `/cards/${i}.png`,
				data: {
					parseAsGraphicsContext: true
				}
			});
			textures.push(texture);
			const card = new Sprite(texture);
			sprites.push(card);
			app.stage.addChild(card);
			// Center the sprite's anchor point.
			card.anchor.set(0.5);

			// Move the sprite to the center of the screen.
			card.x = app.screen.width / 2 + 50 * (i - 7);
			card.y = app.screen.height / 2;
            card.zIndex = i

			card.on('pointerover', (event) => {
				card.scale = 1.1;
                card.zIndex = 10000
			});
			card.on('pointerout', (event) => {
				card.scale = 1;
                card.zIndex = i
			});
			card.eventMode = 'static';
		}

		// // Add an animation loop callback to the application's ticker.
		// app.ticker.add((time) => {
		// 	/**
		// 	 * Just for fun, let's rotate mr rabbit a little.
		// 	 * Time is a Ticker object which holds time related data.
		// 	 * Here we use deltaTime, which is the time elapsed between the frame callbacks
		// 	 * to create frame-independent transformation. Keeping the speed consistent.
		// 	 */
		// 	bunny.rotation += 0.1 * time.deltaTime;
		// });
	});
</script>

<canvas bind:this={canvas}></canvas>
