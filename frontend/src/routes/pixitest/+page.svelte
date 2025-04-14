<script lang="ts">
	import { onMount } from 'svelte';
	import { Icon } from '@steeze-ui/svelte-icon';
	import { ArrowPath, ArrowRightEndOnRectangle } from '@steeze-ui/heroicons';
	import { game } from './socket.js';

	let canvas: HTMLCanvasElement;

	onMount(async () => {
		await game.init(canvas);
		game.join();
	});

</script>

<canvas bind:this={canvas}></canvas>

<div class="absolute left-0 bottom-4 flex justify-center items-center w-full">
	<div class="bg-white rounded-xl flex px-4 py-2 gap-4 flex flex-col">
		<input bind:value={game.playerName} type="text" />
		<div class="flex items-center justify-center">
			<button onclick={() => game.restart()}>
				<Icon class="color-gray-900 size-8" src={ArrowPath} theme="solid" />
			</button>
			<button onclick={() => game.quit()}>
				<Icon class="color-gray-900 size-8" src={ArrowRightEndOnRectangle} theme="solid" />
			</button>
		</div>
	</div>
</div>


